package com.dsi.view;

import com.dsi.controller.tableModel.TableModelSortie;
import com.dsi.model.beans.Materiel;
import com.dsi.model.beans.Sortie;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.SortieManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnSupprimerSortie = new JButton("Supprimer");
    private JButton btnAnnuler = new JButton("Annuler");
    private JButton btnAjouterSortie = new JButton("Ajouter une ligne");
    private JButton btnEnrModifs = new JButton("Enregistrer");

    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauSortie = new JTable();
    List<Sortie> sorties = new ArrayList<>();
    List<Sortie> listRechercheSorties = new ArrayList<>();

    Sortie sortie, blankSortie;
    Materiel materiel;
    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");


    /************************************************************/
    /******************** Constructeur par defaut****************/
    /************************************************************/
    public PageSorties() {
        initialiserComposants();
    }

    public PageSorties(Materiel pMateriel) {
        this.materiel = pMateriel;
        initialiserComposants();
    }

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
        txtRechercher.setText(" Rechercher par date YYYY-MM-dd");
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
        panBas.add(btnAjouterSortie);
        panBas.add(btnSupprimerSortie);
        panBas.add(btnAnnuler);

        setContentPane(panPrincipal);

        displayRightTable();

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
            listRechercheSorties = new ArrayList<>();
            SortieManager sm = SortieManager.getInstance();
            try {
                sm.SelectAll();  //retourne une list d'utilisateurs = utilisateurs
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            /** ON PARCOURS LA LISTE DES sorties **/
            for (Sortie sortie : sorties) {
                String sp = String.valueOf(sortie.getSortie_date_sortie());
                String recherche =  txtRechercher.getText().toLowerCase();

                if (sp.equals(recherche)) {
                    listRechercheSorties.add(sortie);
                    TableModelSortie model = new TableModelSortie(listRechercheSorties);
                    tableauSortie.setModel(model);
                }
            }
            if (listRechercheSorties.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucune sortie trouvée", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par date YYYY-MM-dd");
            sortie = null;
            blankSortie = null;
            displayRightTable();
        });

        /**
         * listenner sur le btnajouterSortie pour ajouter une ligne vierge
         */
        btnAjouterSortie.setSize(140, 50);
        btnAjouterSortie.addActionListener(e -> {
            blankSortie = new Sortie();

            sorties.add(blankSortie);

            //////  On récupére la plus haute id du tableu pour assigner blankSortie à 1 au dessus ////////////////
            int idMax = sorties.get(0).getSortie_id();

            for (int i = 0; i < sorties.size(); i++) {
                int sortieId = sorties.get(i).getSortie_id();
                if (sortieId > idMax) {
                    idMax = sortieId;
                }
            }
            blankSortie.setSortie_id(idMax + 1);
            blankSortie.setSortie_etat("");
            blankSortie.setSortie_date_sortie(new Date());
            blankSortie.setSortie_date_retour(new Date());

            if (materiel != null){
                blankSortie.setSortie_materiel_id(materiel.getMateriel_id());
            }

            //////////////////////////////////////////////////////////////////////////////////////////////////////

            TableModelSortie model = new TableModelSortie(sorties);
            model.fireTableDataChanged();
            tableauSortie.revalidate();
            tableauSortie.setModel(model);
        });


        /**
         * listenner sur le bouton Enregistrer les modifications dans la base
         */
        btnEnrModifs.setSize(140, 50);
        btnEnrModifs.addActionListener(e -> {
            SortieManager sm = SortieManager.getInstance();

            /** Récupérer les valeurs du tableauSortie, on boucle pour chaque ligne */
            for (int i = 0; i < tableauSortie.getRowCount(); i++) {
                try {
                    sortie = SortieManager.getInstance().SelectById((Integer) tableauSortie.getValueAt(i, 4));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                String etatSortieModifie = String.valueOf(tableauSortie.getValueAt(i, 2));
                int idSortieModifie = (int) tableauSortie.getValueAt(i, 4);

                if (sortie == null) {
                    return;
                    //JOptionPane.showMessageDialog(btnEnrModifs, "Veuillez sélectionner un sortie");
                } else {
                    /*** ENREGISTRER LES VALEURS DS LA BASE ***/
                    if (!sortie.getSortie_etat().equalsIgnoreCase(etatSortieModifie) || !(sortie.getSortie_id() == idSortieModifie)) {
                        sortie.setSortie_etat(etatSortieModifie);
                        sortie.setSortie_id(idSortieModifie);

                        int j = JOptionPane.showConfirmDialog(btnEnrModifs, "La modification est irréversible. Êtes-vous sûr de vouloir continuer ?",
                                "Veuillez confirmer votre choix",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                        if (j == 0)  /**user a dit oui*/ {
                            try {
                                if (blankSortie != null) {
                                    sm.insert(blankSortie);
                                    JOptionPane.showMessageDialog(btnEnrModifs, "Sortie " + blankSortie.getSortie_id() + " ajoutée");
                                    blankSortie = null;
                                    break;
                                } else {
                                    sm.update(sortie);
                                    JOptionPane.showMessageDialog(btnEnrModifs, "Sortie " + sortie.getSortie_id() + " modifiée");
                                    displayRightTable();
                                    break;
                                }
                                //   afficheJTableSorties();
                            } catch (BLLException ex) {
                                ex.printStackTrace();
                            }
                        } else {break;}
                    }
                }
            }//fin for
        });


        btnSupprimerSortie.addActionListener(e -> {
            if (sortie == null) {
                JOptionPane.showMessageDialog(btnSupprimerSortie, "Merci de sélectionner un sortie");
                return;
            }

            SortieManager sm = SortieManager.getInstance();
            int i = JOptionPane.showConfirmDialog(btnSupprimerSortie, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer le sortie " + sortie.getSortie_id() + " ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
            if (i == 0) //user a dit oui
            {
                try {
                    sm.delete(sortie);
                    JOptionPane.showMessageDialog(btnSupprimerSortie, "Sortie " + sortie.getSortie_id() + " supprimée");
                    displayRightTable();
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
                tableauSortie.clearSelection();
            }
        });


        /**
         *
         * Mouse listenner sur le tableau sortie
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

    private void afficheJTableSorties() {
        try {
            sorties = remplirJTableWithAllSorties();
            TableModelSortie model = new TableModelSortie(sorties);
            tableauSortie.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable


    private void afficheJTableSortiesWithIdMateriel() {
        try {
            sorties = remplirJTableSortiesWithIdMateriel(materiel.getMateriel_id());
            TableModelSortie model = new TableModelSortie(sorties);
            tableauSortie.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable


    private void displayRightTable(){
        if (materiel == null){
            afficheJTableSorties();
        } else {
            afficheJTableSortiesWithIdMateriel();
        }
    }

}//fin class
