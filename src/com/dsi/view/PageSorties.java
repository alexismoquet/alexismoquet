package com.dsi.view;

import com.dsi.controller.tableModels.TableModelSortie;
import com.dsi.model.beans.Materiel;
import com.dsi.model.beans.Sortie;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.SortieManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.dsi.controller.Sorties.remplirJTableSortiesWithIdMateriel;
import static com.dsi.controller.Sorties.remplirJTableWithAllSorties;

/**
 * Classe PageSorties
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageSorties extends JFrame {

    private final JPanel panPrincipal = new JPanel();
    private final JPanel panHaut = new JPanel();
    private final JPanel panCentre = new JPanel();
    private final JPanel panBas = new JPanel();

    private final JButton btnSupprimerSortie = new JButton("Supprimer");
    private final JButton btnAnnuler = new JButton("Annuler");
    private final JButton btnAjouterSortie = new JButton("Ajouter");
    private final JButton btnEnrModifs = new JButton("Enregistrer");

    private final JTextField txtRechercher = new JTextField();
    private final JButton btnRechercher = new JButton("Rechercher");

    private final JTable tableauSortie = new JTable();
    List<Sortie> sorties = new ArrayList<>();
    List<Sortie> listRechercheSorties = new ArrayList<>();

    Sortie sortie, blankSortie;
    Materiel materiel;
    boolean verifSiAjout = false;
    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");


    /**
     * Constructeur par defaut
     */
    public PageSorties() {
        initialiserComposants();
    }

    /**
     * Constructeur
     *
     * @param: pMateriel
     */
    public PageSorties(Materiel pMateriel) {
        this.materiel = pMateriel;
        initialiserComposants();
    }

    /**
     * Méthode qui affiche le graphisme de la page
     */
    public void initialiserComposants() {
        setTitle("Sorties");
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
        txtRechercher.setText(" Rechercher par état ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauSortie.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauSortie, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauSortie), BorderLayout.CENTER);
        tableauSortie.setRowHeight(30);

        panBas.setSize(600, 250);
        panBas.add(btnEnrModifs);
        if (materiel != null) {
            panBas.add(btnAjouterSortie);
        }
        panBas.add(btnSupprimerSortie);
        panBas.add(btnAnnuler);

        setContentPane(panPrincipal);

        tableauSortie.setAutoCreateRowSorter(true);

        displayRightTable();

        /*
         * MouseListenner sur JTextField rechercher
         **/
        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField txtRechercher = ((JTextField) e.getSource());
                txtRechercher.setText("");
            }
        });

        /*
         * Listenner sur le bouton btnRechercher
         **/
        btnRechercher.addActionListener(e -> {
            listRechercheSorties = new ArrayList<>();
            try {
                SortieManager.getInstance().SelectAll();  //retourne une list d'utilisateurs = utilisateurs
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            /* ON PARCOURS LA LISTE DES sorties **/
            for (Sortie sortie : sorties) {
                String sp = String.valueOf(sortie.getSortie_etat()).toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (sp.contains(recherche)) {
                    listRechercheSorties.add(sortie);
                    TableModelSortie model = new TableModelSortie(listRechercheSorties);
                    tableauSortie.setModel(model);
                }
            }
            if (listRechercheSorties.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucune sortie trouvée", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        /*
         * Listenner du btnAnnuler
         **/
        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par état ");
            sortie = null;
            blankSortie = null;
            displayRightTable();
        });

        /*
         * listenner sur le btnajouterSortie pour ajouter une ligne vierge
         */
        btnAjouterSortie.setSize(140, 50);
        btnAjouterSortie.addActionListener(e -> {
            if (materiel != null && sorties.size() == 0) {

                verifSiAjout = true;

                List<Sortie> allSorties = null;
                try {
                    allSorties = SortieManager.getInstance().SelectAll();
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }

                blankSortie = new Sortie();

                //////  On récupére la plus haute id du tableu pour assigner blankSortie à 1 au dessus ////////////////

                if (sortie != null) {
                    assert allSorties != null;
                    int idMax = allSorties.get(0).getSortie_id();

                    for (Sortie allSorty : allSorties) {
                        int sortieId = allSorty.getSortie_id();
                        if (sortieId > idMax) {
                            idMax = sortieId;
                        }
                    }
                    blankSortie.setSortie_id(idMax + 1);
                } else {
                    blankSortie.setSortie_id(1);
                }

                blankSortie.setSortie_etat("");
                blankSortie.setSortie_date_sortie(new Date());
                blankSortie.setSortie_date_retour(new Date());
                blankSortie.setSortie_materiel_id(materiel.getMateriel_id());

                try {
                    SortieManager.getInstance().insert(blankSortie);
                    //   JOptionPane.showMessageDialog(btnAjouterSortie, "Sortie ajoutée");
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                //////////////////////////////////////////////////////////////////////////////////////////////////////

                sorties.add(blankSortie);
                TableModelSortie model = new TableModelSortie(sorties);
                model.fireTableDataChanged();
                tableauSortie.revalidate();
                tableauSortie.setModel(model);

                blankSortie = null;
                displayRightTable();
            }//fin if
            else {
                JOptionPane.showMessageDialog(null, "Merci de saisir un nouveau matériel");
            }
        });


        /*
         * listenner sur le bouton Enregistrer les modifications dans la base
         */
        btnEnrModifs.setSize(140, 50);
        btnEnrModifs.addActionListener(e -> {
            /* Récupérer les valeurs du tableauSortie, on boucle pour chaque ligne */
            for (int i = 0; i < tableauSortie.getRowCount(); i++) {
                try {
                    sortie = SortieManager.getInstance().SelectById((Integer) tableauSortie.getValueAt(i, 4));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                String etatSortieModifie = String.valueOf(tableauSortie.getValueAt(i, 2));
                int idSortieModifie = (int) tableauSortie.getValueAt(i, 4);
                int idMaterielSortieModifie = (int) tableauSortie.getValueAt(i, 3);
                Date dateRetourSortieModifiee = (Date) tableauSortie.getValueAt(i, 0);
                Date dateSortieSortieModifiee = (Date) tableauSortie.getValueAt(i, 1);

//                tableauSortie.setValueAt(etatSortieModifie, i, 2);
//                tableauSortie.setValueAt(idMaterielSortieModifie, i, 3);
//                tableauSortie.setValueAt(dateRetourSortieModifiee, i, 0);
//                tableauSortie.setValueAt(dateSortieSortieModifiee, i, 1);

                if (sortie == null) {
                    return;
                    //JOptionPane.showMessageDialog(btnEnrModifs, "Veuillez sélectionner un sortie");
                } else {
                    /* ENREGISTRER LES VALEURS DS LA BASE ***/
                    if (!sortie.getSortie_etat().equalsIgnoreCase(etatSortieModifie)
                            || (sortie.getSortie_id() != idSortieModifie)
                            || (sortie.getSortie_materiel_id() != idMaterielSortieModifie)
                            || !(sortie.getSortie_date_retour().equals(dateRetourSortieModifiee))
                            || !(sortie.getSortie_date_sortie().equals(dateSortieSortieModifiee))
                    ) {
                        sortie.setSortie_etat(etatSortieModifie);
                        sortie.setSortie_id(idSortieModifie);
                        sortie.setSortie_materiel_id(idMaterielSortieModifie);
                        sortie.setSortie_date_retour(dateRetourSortieModifiee);
                        sortie.setSortie_date_sortie(dateSortieSortieModifiee);

                        int j;
                        String confirmChoix = "Veuillez confirmer votre choix";
                        if (verifSiAjout) {
                            j = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir enregistrer la sortie " + sortie.getSortie_id() + " ?",
                                    confirmChoix,
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                            verifSiAjout = false;
                        } else {
                            j = JOptionPane.showConfirmDialog(null, "La modification est irréversible. Êtes-vous sûr de vouloir enregistrer la sortie " + sortie.getSortie_id() + " ?",
                                    confirmChoix,
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                        }
                        if (j == 0)  /*user a dit oui*/ {
                            try {
                                SortieManager.getInstance().update(sortie);
                                JOptionPane.showMessageDialog(null, "Sortie " + sortie.getSortie_id() + " enregistrée");
                            } catch (BLLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }//fin for
            displayRightTable();
        });

        /*
         *Listener bouton Supprimer
         */
        btnSupprimerSortie.addActionListener(e -> {
            if (sortie == null) {
                JOptionPane.showMessageDialog(null, "Merci de sélectionner un sortie");
                return;
            }
            ///Supprime tous les sorties sélectionnées
            int[] selection = tableauSortie.getSelectedRows();
            for (int j : selection) {
                sortie = sorties.get(j);
                try {
                    sortie = SortieManager.getInstance().SelectById(sortie.getSortie_id());
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                int i = JOptionPane.showConfirmDialog(null, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer le sortie " + sortie.getSortie_id() + " ?",
                        "Veuillez confirmer votre choix",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                if (i == 0) //user a dit oui
                {
                    try {
                        SortieManager.getInstance().delete(sortie);
                        JOptionPane.showMessageDialog(null, "Sortie " + sortie.getSortie_id() + " supprimée");
                    } catch (BLLException ex) {
                        ex.printStackTrace();
                    }
                    // tableauSortie.clearSelection();
                }
            }
            displayRightTable();
        });

        /*
         * Mouse Listener sur le tableau sortie
         */
        tableauSortie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idSortie = (int) tableauSortie.getValueAt(tableauSortie.getSelectedRow(), 4);
                //       JOptionPane.showMessageDialog(tableauSortie, "Le sortie " + idSortie + " est sélectionnée");
                try {
                    sortie = SortieManager.getInstance().SelectById(idSortie);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }//fin initialiserComposants

    /**
     * Méthode qui affiche toutes les sorties
     */
    private void afficheJTableSorties() {
        try {
            sorties = remplirJTableWithAllSorties();
            TableModelSortie model = new TableModelSortie(sorties);
            tableauSortie.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

    /**
     * Méthode qui affiche les sorties du
     * matériel sélectionné
     */
    private void afficheJTableSortiesWithIdMateriel() {
        try {
            sorties = remplirJTableSortiesWithIdMateriel(materiel.getMateriel_id());
            TableModelSortie model = new TableModelSortie(sorties);
            tableauSortie.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable


    private void displayRightTable() {
        if (materiel == null) {
            afficheJTableSorties();
        } else {
            afficheJTableSortiesWithIdMateriel();
        }
    }

}//fin class
