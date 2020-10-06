package com.dsi.view;

import com.dsi.controller.tableModel.TableModelSport;
import com.dsi.model.beans.Sport;
import com.dsi.model.bll.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import static com.dsi.controller.Sports.remplirJTableWithAllSports;

/**
 * Classe PageSports
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageSports extends JFrame {

    private final JPanel panPrincipal = new JPanel();
    private final JPanel panHaut = new JPanel();
    private final JPanel panCentre = new JPanel();
    private final JPanel panBas = new JPanel();

    private final JButton btnSupprimerSport = new JButton("Supprimer");
    private final JButton btnAnnuler = new JButton("Annuler");
    private final JButton btnMateriels = new JButton("Matériels");
    private final JButton btnAjouterSport = new JButton("Ajouter");
    private final JButton btnEnrModifs = new JButton("Enregistrer");

    private final JTextField txtRechercher = new JTextField();
    private final JButton btnRechercher = new JButton("Rechercher");

    private final JTable tableauSport = new JTable();
    List<Sport> sports = new ArrayList<>();
    List<Sport> listRechercheSports = new ArrayList<>();

    Sport sport, blankSport;
    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
    boolean verifSiAjout = false;

    /**
     * Constructeur par defaut
     */
    public PageSports() {
        initialiserComposants();
    }

    /**
     * Méthode qui affiche le graphisme de la page
     */
    public void initialiserComposants() {
        setTitle("Sports");
        setIconImage(Toolkit.getDefaultToolkit().getImage("LogoIconeDSI.png"));
        setSize(1100, 700);
        setVisible(true);
        setResizable(true);

        panPrincipal.setLayout(new BorderLayout());
        panPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        panPrincipal.setBackground(Color.decode("#11417d"));

        panPrincipal.add(panHaut, BorderLayout.NORTH);
        panPrincipal.add(panCentre, BorderLayout.CENTER);
        panPrincipal.add(panBas, BorderLayout.SOUTH);

        panHaut.setPreferredSize(new Dimension(900, 100));
        txtRechercher.setText(" Rechercher par nom ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauSport.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauSport, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauSport), BorderLayout.CENTER);
        tableauSport.setRowHeight(30);

        panBas.setSize(600, 250);
        panBas.add(btnEnrModifs);
        panBas.add(btnAjouterSport);
        panBas.add(btnSupprimerSport);
        panBas.add(btnAnnuler);
        panBas.add(btnMateriels);

        tableauSport.setAutoCreateRowSorter(true);
        setContentPane(panPrincipal);

        afficheJTableSports();

        /*
         * MouseListenner sur JTextField rechercher
         **/
        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField txtRechercher = ((JTextField) e.getSource());
                txtRechercher.setText("");
                //       txtRechercher.removeMouseListener(this);
            }
        });

        /*
         * Listenner sur le bouton btnRechercher
         **/
        btnRechercher.addActionListener(e -> {
            listRechercheSports = new ArrayList<>();
            SportManager sm = SportManager.getInstance();
            try {
                sm.SelectAll();  //retourne une list d'utilisateurs = utilisateurs
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            /*ON PARCOURS LA LISTE DES SPORTS **/
            for (Sport sport : sports) {
                String sp = sport.getSport_libelle().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (sp.startsWith(recherche)) {
                    listRechercheSports.add(sport);
                    TableModelSport model = new TableModelSport(listRechercheSports);
                    tableauSport.setModel(model);
                }
            }
            if (listRechercheSports.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucun sport trouvé", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        /*
         * Listenner du btnAnnuler
         **/
        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par nom ");
            sport = null;
            blankSport = null;
            afficheJTableSports();
        });

        /*
         * listenner sur le btnajouterSport pour ajouter une ligne vierge
         */
        btnAjouterSport.setSize(140, 50);
        btnAjouterSport.addActionListener(e -> {
            verifSiAjout = true;
            List<Sport> allSports = null;
            SportManager sm = new SportManager();
            try {
                allSports = sm.SelectAll();
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }
            blankSport = new Sport();
            sports.add(blankSport);

            //////  On récupére la plus haute id du tableu pour assigner blankSport à 1 au dessus ////////////////
            if (sport != null) {
                assert allSports != null;
                int idMax = allSports.get(0).getSport_id();

                for (Sport allSport : allSports) {
                    int sportId = allSport.getSport_id();
                    if (sportId > idMax) {
                        idMax = sportId;
                    }
                }
                blankSport.setSport_id(idMax + 1);
            }
            blankSport.setSport_libelle("");

            try {
                SportManager.getInstance().insert(blankSport);
            //    JOptionPane.showMessageDialog(btnAjouterSport, "Sport ajouté");

            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }

            TableModelSport model = new TableModelSport(sports);
            model.fireTableDataChanged();
            tableauSport.revalidate();
            tableauSport.setModel(model);

            blankSport = null;
            afficheJTableSports();
        });


        /*
         * listenner sur le bouton btnEnrModifs qui
         * enregistre les modifications dans la base
         */
        btnEnrModifs.setSize(140, 50);
        btnEnrModifs.addActionListener(e -> {
            /* Récupérer les valeurs du tableauUtilisateur, on boucle pour chaque ligne */
            for (int i = 0; i < tableauSport.getRowCount(); i++) {
                try {
                    sport = SportManager.getInstance().SelectById((Integer) tableauSport.getValueAt(i, 1));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                String libelleSportModifie = String.valueOf(tableauSport.getValueAt(i, 0));
                int idSportModifie = (int) tableauSport.getValueAt(i, 1);

                if (sport == null) {
                    return;
                    //JOptionPane.showMessageDialog(btnEnrModifs, "Veuillez sélectionner un sport");
                } else {
                    /* ENREGISTRER LES VALEURS DS LA BASE ***/
                    if (!sport.getSport_libelle().equalsIgnoreCase(libelleSportModifie) || !(sport.getSport_id() == idSportModifie)) {
                        sport.setSport_libelle(libelleSportModifie);
                        sport.setSport_id(idSportModifie);

                        int j;
                        if (verifSiAjout) {
                           j = JOptionPane.showConfirmDialog(btnEnrModifs, "Êtes-vous sûr de vouloir enregistrer le sport " + sport.getSport_id() + " ?",
                                    "Veuillez confirmer votre choix",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                                    verifSiAjout = false;
                        } else {
                            j = JOptionPane.showConfirmDialog(btnEnrModifs, "La modification est irréversible. Êtes-vous sûr de vouloir enregistrer le sport " + sport.getSport_id() + " ?",
                                    "Veuillez confirmer votre choix",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                        }
                        if (j == 0)  /*user a dit oui*/ {
                            try {
                                    SportManager.getInstance().update(sport);
                                    JOptionPane.showMessageDialog(btnEnrModifs, "Sport " + sport.getSport_id() + " enregistré");
                            } catch (BLLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }//fin for
            afficheJTableSports();
        });

        /*
         *Listener bouton btnSupprimerSport
         */
        btnSupprimerSport.addActionListener(e -> {
            if (sport == null) {
                JOptionPane.showMessageDialog(btnSupprimerSport, "Merci de sélectionner un sport");
                return;
            }
                    ///Supprime tous les sports sélectionnés
                    int[] selection = tableauSport.getSelectedRows();
                    for (int j : selection) {
                        sport = sports.get(j);
                        try {
                                sport = SportManager.getInstance().SelectById(sport.getSport_id());
                        } catch (BLLException bllException) {
                            bllException.printStackTrace();
                        }
                        int i = JOptionPane.showConfirmDialog(btnSupprimerSport, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer le sport " + sport.getSport_id() + " ?",
                                "Veuillez confirmer votre choix",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                        if (i == 0) //user a dit oui
                        {
                            try {
                                SportManager.getInstance().delete(sport);
                                JOptionPane.showMessageDialog(btnSupprimerSport, "Sport " + sport.getSport_id() + " supprimé");
                            } catch (BLLException ex) {
                                ex.printStackTrace();
                            }
                        //    tableauSport.clearSelection();
                        }
                    }//fin for
            afficheJTableSports();
        });

        /*
         * listenner sur le bouton btnMateriels
         */
        btnMateriels.setSize(100, 50);
        btnMateriels.addActionListener(e -> {
            if (sport == null) {
                JOptionPane.showMessageDialog(btnMateriels, "Veuillez sélectionner un sport");
            } else {
                new PageMateriels(sport);
            }
        });


        /*
          Mouse listenner sur le tableau sport
         */
        tableauSport.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idSport = (int) tableauSport.getValueAt(tableauSport.getSelectedRow(), 1);
                try {
                    sport = SportManager.getInstance().SelectById(idSport);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }//fin initialiserComposants

    /**
     * Méthode qui affiche tous les sports
     */
    private void afficheJTableSports() {
        try {
            sports = remplirJTableWithAllSports();
            TableModelSport model = new TableModelSport(sports);
            tableauSport.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable


}//fin class
