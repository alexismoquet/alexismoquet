package com.dsi.view;

import com.dsi.controller.tableModel.TableModelSport;
import com.dsi.model.beans.Sport;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.SportManager;
import com.dsi.model.bll.UtilisateurManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnSupprimerSport = new JButton("Supprimer");
    private JButton btnAnnuler = new JButton("Annuler");
    private JButton btnMateriels = new JButton("Matériels");
    private JButton btnAjouterSport = new JButton("Creer une ligne");
    private JButton btnEnrModifs = new JButton("Enregistrer les modifications");

    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauSport = new JTable();
    List<Sport> sports = new ArrayList<>();
    List<Sport> listRechercheSports = new ArrayList<>();

    Sport sport, blankSport;
    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");


    /************************************************************/
    /******************** Constructeur par defaut****************/
    /************************************************************/
    public PageSports() {
        initialiserComposants();
    }


    public void initialiserComposants() {
        setTitle("Sports");
        setIconImage(Toolkit.getDefaultToolkit().getImage("LogoIconeDSI.png"));
        setSize(900, 500);
        setVisible(true);
        setResizable(true);

        panPrincipal.setLayout(new BorderLayout());
        panPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        panPrincipal.setBackground(Color.decode("#11417d"));

        panPrincipal.add(panHaut, BorderLayout.NORTH);
        panPrincipal.add(panCentre, BorderLayout.CENTER);
        panPrincipal.add(panBas, BorderLayout.SOUTH);

        panHaut.setPreferredSize(new Dimension(900, 100));
        txtRechercher.setText("     Rechercher par sport     ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauSport.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauSport, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauSport), BorderLayout.CENTER);

        panBas.setSize(600, 250);
        panBas.add(btnAjouterSport);
        panBas.add(btnEnrModifs);
        panBas.add(btnSupprimerSport);
        panBas.add(btnAnnuler);
        panBas.add(btnMateriels);

        setContentPane(panPrincipal);

        afficheJTableSports();

        /**************************************************************************************************************************************/
        /*************************************************************** Les listenners *******************************************************/
        /**************************************************************************************************************************************/
        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField txtRechercher = ((JTextField) e.getSource());
                txtRechercher.setText("");
                //       txtRechercher.removeMouseListener(this);
            }
        });

        btnRechercher.addActionListener(e -> {
            listRechercheSports = new ArrayList<>();
            SportManager sm = SportManager.getInstance();
            try {
                sm.SelectAll();  //retourne une list d'utilisateurs = utilisateurs
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            /** ON PARCOURS LA LISTE DES SPORTS **/
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

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText("");
            sport = null;
            afficheJTableSports();
        });

        /**
         * listenner sur le btnajouterSport pour ajouter une ligne vierge
         */
        btnAjouterSport.setSize(140, 50);
        btnAjouterSport.addActionListener(e -> {
            blankSport = new Sport();
            sports.add(blankSport);

            //////  On récupére la plus haute id du tableu pour assigner blankSport à 1 au dessus ////////////////
            int idMax = sports.get(0).getSport_id();

            for (int i = 0; i < sports.size(); i++) {
                int sportId = sports.get(i).getSport_id();
                if (sportId > idMax) {
                    idMax = sportId;
                }
            }
            blankSport.setSport_id(idMax + 1);
            blankSport.setSport_libelle("");
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            TableModelSport model = new TableModelSport(sports);
            model.fireTableDataChanged();
            tableauSport.revalidate();
            tableauSport.setModel(model);
        });


        /**
         * listenner sur le bouton Enregistrer les modifications dans la base
         */
        btnEnrModifs.setSize(140, 50);
        btnEnrModifs.addActionListener(e -> {
            SportManager sm = SportManager.getInstance();

            /** Récupérer les valeurs du tableauUtilisateur, on boucle pour chaque ligne */
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
                    /*** ENREGISTRER LES VALEURS DS LA BASE ***/
                    if (!sport.getSport_libelle().equalsIgnoreCase(libelleSportModifie) || !(sport.getSport_id() == idSportModifie)) {
                        sport.setSport_libelle(libelleSportModifie);
                        sport.setSport_id(idSportModifie);

                        int j = JOptionPane.showConfirmDialog(btnEnrModifs, "La modification est irréversible. Êtes-vous sûr de vouloir continuer ?",
                                "Veuillez confirmer votre choix",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                        if (j == 0)  /**user a dit oui*/ {
                            try {
                                if (blankSport != null) {
                                    sm.insert(blankSport);
                                    JOptionPane.showMessageDialog(btnEnrModifs, "Sport " + blankSport.getSport_id() + " ajouté");
                                    blankSport = null;
                                    break;
                                } else {
                                    sm.update(sport);
                                    JOptionPane.showMessageDialog(btnEnrModifs, "Sport " + sport.getSport_id() + " modifié");
                                    break;
                                }
                                //   afficheJTableSports();
                            } catch (BLLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }//fin for
        });


        btnSupprimerSport.addActionListener(e -> {
            if (sport == null) {
                JOptionPane.showMessageDialog(btnSupprimerSport, "Merci de sélectionner un sport");
                return;
            }

            SportManager sm = SportManager.getInstance();
            int i = JOptionPane.showConfirmDialog(btnSupprimerSport, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer le sport " + sport.getSport_id() + " ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
            if (i == 0) //user a dit oui
            {
                try {
                    sm.delete(sport);
                    JOptionPane.showMessageDialog(btnSupprimerSport, "Sport " + sport.getSport_id() + " supprimé");
                    afficheJTableSports();
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
                tableauSport.clearSelection();
            }
        });

        /**
         * listenner sur le bouton materiel
         */
        btnMateriels.setSize(100, 50);
        btnMateriels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sport == null) {
                    JOptionPane.showMessageDialog(btnMateriels, "Veuillez sélectionner un sport");
                } else {
                    new PageMateriels(sport);
                }
            }
        });


        /**
         *
         * Mouse listenner sur le tableau sport
         */
        tableauSport.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idSport = (int) tableauSport.getValueAt(tableauSport.getSelectedRow(), 1);
                //      JOptionPane.showMessageDialog(null, "Le sport " + idSport + " est sélectionné");
                try {
                    sport = SportManager.getInstance().SelectById(idSport);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }//fin initialiserComposants

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
