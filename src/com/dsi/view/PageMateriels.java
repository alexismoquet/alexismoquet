package com.dsi.view;

import com.dsi.controller.Materiels;
import com.dsi.controller.tableModel.TableModelCommentaire;
import com.dsi.controller.tableModel.TableModelMateriel;
import com.dsi.controller.tableModel.TableModelSortie;
import com.dsi.controller.tableModel.TableModelVisuel;
import com.dsi.model.beans.*;
import com.dsi.model.beans.Materiel;
import com.dsi.model.bll.AnnonceManager;
import com.dsi.model.bll.MaterielManager;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.VisuelManager;
import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import static com.dsi.controller.Materiels.*;
import static com.dsi.controller.Materiels.remplirJTableWithIdMateriels;


public class PageMateriels extends JFrame {


    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnEnregistrerMateriel = new JButton("Enregistrer");
    private JButton btnSupprimerMateriel = new JButton("Supprimer");
    private JButton btnAnnuler = new JButton("Annuler");
    private JButton btnVisuels = new JButton("visuels");
    private JButton btnRechercher = new JButton("Rechercher");
    private JButton btnSorties = new JButton("Sortie");
    private JButton btnAnnonces = new JButton("Annonce(s)");
    private JButton btnAjouterMateriel = new JButton("Ajouter Materiel");

    private JTextField txtRechercher = new JTextField();
    private JTable tableauMateriel = new JTable();
    List<Materiel> materiels = new ArrayList<>();
    List<Materiel> listRechercheMateriels = new ArrayList<>();

    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
    Categorie categorie;
    Sport sport;
    Utilisateur utilisateur;
    Materiel materiel, blankMateriel;


    /************************************************************/
    /*********************** Constructeurs **********************/
    /************************************************************/

    public PageMateriels() {
        initialiserComposants();
    }

    public PageMateriels(Utilisateur pUtilisateur) {
        this.utilisateur = pUtilisateur;
        initialiserComposants();
    }

    public PageMateriels(Categorie pCategorie) {
        this.categorie = pCategorie;
        initialiserComposants();
    }

    public PageMateriels(Sport pSport) {
        this.sport = pSport;
        initialiserComposants();
    }

    public PageMateriels(int annonce_materiel_id) throws BLLException {
        MaterielManager mm = new MaterielManager();
        materiel = mm.SelectById(annonce_materiel_id);
        materiels.add(materiel);
        initialiserComposants();
        TableModelMateriel model = new TableModelMateriel(materiels);
        tableauMateriel.setModel(model);
    }

    /*************************************************************/
    /******************* Fin Constructeurs ***********************/
    /*************************************************************/


    /******************************************************************/
    /*********initialiser les Composants graphiques de la page********/
    /*****************************************************************/

    public void initialiserComposants() {
        setTitle("Materiels");
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
        txtRechercher.setText(" Rechercher par mot(s) clé(s) ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauMateriel.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauMateriel, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauMateriel), BorderLayout.CENTER);
        tableauMateriel.setRowHeight(30);

        panBas.setSize(500, 200);
        panBas.add(btnAjouterMateriel);
        panBas.add(btnEnregistrerMateriel);
        panBas.add(btnSupprimerMateriel);
        panBas.add(btnAnnuler);
        panBas.add(btnVisuels);
        panBas.add(btnSorties);
        panBas.add(btnAnnonces);

        setContentPane(panPrincipal);

        if (materiel == null) {
            displayRightTable();
        }

        /**************************************************************************************************************************************/
        /*************************************************************** Les listenners *******************************************************/
        /**************************************************************************************************************************************/

        /**
         * MouseListenner sur JTextField rechercher
         **/
        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField txtRechercher = ((JTextField) e.getSource());
                txtRechercher.setText("");
            }
        });


        /**
         * Listenner btnRechercher
         *
         **/
        btnRechercher.addActionListener(e -> {
            listRechercheMateriels = new ArrayList<>();
            MaterielManager um = MaterielManager.getInstance();
            try {
                um.SelectAll();
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            for (Materiel materiel : materiels) {
                String nomMateriel = materiel.getMateriel_nom().toLowerCase();
                String descriptionMateriel = materiel.getMateriel_description().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (nomMateriel.contains(recherche) || descriptionMateriel.contains(recherche)) {
                    listRechercheMateriels.add(materiel);
                    TableModelMateriel model = new TableModelMateriel(listRechercheMateriels);
                    tableauMateriel.setModel(model);
                }
            }
            if (listRechercheMateriels.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucun materiel trouvé", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        /**
         * Listenner btnAnnuler
         *
         **/
        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par mot(s) clé(s) ");
            materiel = null;
            blankMateriel = null;
            displayRightTable();
        });

        /**
         * listenner sur le btnajouterMateriel pour ajouter une ligne vierge
         * @param blankMateriel
         */
        btnAjouterMateriel.setSize(140, 50);
        btnAjouterMateriel.addActionListener(e -> {
            blankMateriel = new Materiel();
            materiels.add(blankMateriel);

            //////////////On récupére la plus haute id du tableau pour assigner blankMateriel à 1 au dessus ////////////////
            int idMax = materiels.get(0).getMateriel_id();

            for (int i = 0; i < materiels.size(); i++) {
                int materielId = materiels.get(i).getMateriel_id();
                if (materielId > idMax) {
                    idMax = materielId;
                }
            }

            //////////////////On Set les valeurs des colonnes du tableauMateriel //////////////////////
            blankMateriel.setMateriel_id(idMax + 1);
            blankMateriel.setMateriel_nom("");
            blankMateriel.setMateriel_description("");
            if (utilisateur == null){
                blankMateriel.setMateriel_adresse_id(0);
            }else{
            blankMateriel.setMateriel_adresse_id(utilisateur.getAdresses().get(0).getIdAdresse());}

            if (categorie == null){
                blankMateriel.setMateriel_categorie_id(0);
            }else{
            blankMateriel.setMateriel_categorie_id((Integer) tableauMateriel.getValueAt(0,3));
            }

            if (sport == null){ blankMateriel.setMateriel_sport_id(0);
            }else{
                blankMateriel.setMateriel_sport_id((Integer) tableauMateriel.getValueAt(0,4));
            }

            blankMateriel.setMateriel_prix(0);


//            if (materiel != null){
//                blankMateriel.setSortie_materiel_id(materiel.getMateriel_id());
//            }

            //////////////////////////////////////////////////////////////////////////////////////////////////////

            TableModelMateriel model = new TableModelMateriel(materiels);
            model.fireTableDataChanged();
            tableauMateriel.revalidate();
            tableauMateriel.setModel(model);
        });


        /**
         * Listenner du bouton enregistrer les modifications
         * @param materiel
         **/
        btnEnregistrerMateriel.addActionListener(e -> {
            MaterielManager mm = MaterielManager.getInstance();

            /** Récupérer les valeurs du tableauAnomalies, on vérifie pour chaque ligne */
            for (int i = 0; i < tableauMateriel.getRowCount(); i++) {

                try {
                    materiel = MaterielManager.getInstance().SelectById((Integer) tableauMateriel.getValueAt(i, 5));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                String nomMaterielModifie = String.valueOf(tableauMateriel.getValueAt(i, 0));
                String descriptionMaterielModifie = String.valueOf(tableauMateriel.getValueAt(i, 1));
                double prixMaterielModifie = (double) tableauMateriel.getValueAt(i, 6);

                tableauMateriel.setValueAt(descriptionMaterielModifie, i, 1);
                tableauMateriel.setValueAt(nomMaterielModifie, i, 0);
                tableauMateriel.setValueAt(prixMaterielModifie, i, 6);


                /*** ENREGISTRER LES VALEURS DS LA BASE ***/
                if (!materiel.getMateriel_nom().equals(nomMaterielModifie) || !materiel.getMateriel_description().equals(descriptionMaterielModifie) || !(materiel.getMateriel_prix() == prixMaterielModifie)) {
                    try {
                        materiel.setMateriel_description(descriptionMaterielModifie);
                        materiel.setMateriel_nom(nomMaterielModifie);
                        materiel.setMateriel_prix(prixMaterielModifie);

                        int j = JOptionPane.showConfirmDialog(btnEnregistrerMateriel, "La modification est irréversible. Êtes-vous sûr de vouloir continuer ?",
                                "Veuillez confirmer votre choix",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                        if (j == 0)  /**user a dit oui*/ {
                            mm.update(materiel);
                            JOptionPane.showMessageDialog(btnEnregistrerMateriel, "Matériel " + tableauMateriel.getValueAt(i, 5) + " modifié");
                        }
                    } catch (BLLException bllException) {
                        bllException.printStackTrace();
                    }
                }
            }//fin boucle for
            //  tableauMateriel.clearSelection();

        });


        /**
         * Listenner du bouton supprimer le materiel sélectionné
         * @param materiel
         **/
        btnSupprimerMateriel.addActionListener(e -> {
            if (materiel == null) {
                JOptionPane.showMessageDialog(btnSupprimerMateriel, "Merci de sélectionner un matériel");
                return;
            }
            MaterielManager am = MaterielManager.getInstance();

            int i = JOptionPane.showConfirmDialog(btnSupprimerMateriel, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer le matériel " + materiel.getMateriel_id() + " ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
            if (i == 0) //user a dit oui
            {
                try {
                    am.delete(materiel);
                    JOptionPane.showMessageDialog(btnSupprimerMateriel, "Matériel " + materiel.getMateriel_id() + " supprimé");
                    displayRightTable();
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        /**
         * Mouse listenner sur le tableau materiel
         */
        tableauMateriel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idMaterielSelected = (int) tableauMateriel.getValueAt(tableauMateriel.getSelectedRow(), 5);
          //      JOptionPane.showMessageDialog(null, "Le materiel " + idMaterielSelected + " est sélectionné");
                try {

                    materiel = MaterielManager.getInstance().SelectById(idMaterielSelected);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        /**
         * listenner sur le bouton Visuels
         */
        btnVisuels.setSize(100, 50);
        btnVisuels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (materiel == null) {
                    JOptionPane.showMessageDialog(btnVisuels, "Veuillez sélectionner un materiel");
                } else {
                    new PageVisuels(materiel);
                }
            }
        });

        /**
         * listenner sur le bouton Sorties
         */
        btnSorties.setSize(100, 50);
        btnSorties.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (materiel == null) {
                    JOptionPane.showMessageDialog(btnSorties, "Veuillez sélectionner un materiel");
                } else {
                    new PageSorties(materiel);
                }
            }
        });


        /**
         * listenner sur le bouton Annonces
         */
        btnAnnonces.setSize(100, 50);
        btnAnnonces.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (materiel == null) {
                    JOptionPane.showMessageDialog(btnSorties, "Veuillez sélectionner un materiel");
                } else {
                    new PageAnnonces(materiel);
                }
            }
        });

    }//fin initialiserComposants


    private void afficheJTableMaterielsWithIdSport() {
        try {
            materiels = remplirJTableWithMaterielsIdSport(sport.getSport_id());
            TableModelMateriel model = new TableModelMateriel(materiels);
            tableauMateriel.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

    private void afficheJTableMaterielsWithIdCategorie(int idCategorie) {
        try {
            materiels = remplirJTableWithMaterielsIdCategorie(categorie.getCategorie_id());
            TableModelMateriel model = new TableModelMateriel(materiels);
            tableauMateriel.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

    private void afficheJTableMaterielsWithIdAdresse() {

        try {
            materiels = remplirJTableWithMaterielsIdAdresse(utilisateur.getAdresses().get(0).getIdAdresse());
            TableModelMateriel model = new TableModelMateriel(materiels);
            tableauMateriel.setModel(model);

        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable


    private void afficheJTableWithAllMateriels() {
        try {
            materiels = remplirJTableWithAllMateriels();
            TableModelMateriel model = new TableModelMateriel(materiels);
            tableauMateriel.setModel(model);

        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

    /**
     ** Affichage du bon tableauMateriel de la pageMateriel selon la page de provenance
     **/
    public void displayRightTable() {
        if (utilisateur == null && categorie == null && sport == null) {
            afficheJTableWithAllMateriels();
        } else if (utilisateur == null && sport == null) {
            afficheJTableMaterielsWithIdCategorie(categorie.getCategorie_id());
        } else if (categorie == null && sport == null) {
            afficheJTableMaterielsWithIdAdresse();
        } else if (utilisateur == null && categorie == null) {
            afficheJTableMaterielsWithIdSport();
        }
    }

}//fin class
