package com.dsi.view;


import com.dsi.controller.tableModel.TableModelMateriel;
import com.dsi.model.beans.Categorie;
import com.dsi.model.beans.Materiel;
import com.dsi.model.beans.Sport;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.MaterielManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.dsi.controller.Materiels.*;


public class PageMateriels extends JFrame {


    private final JPanel panPrincipal = new JPanel();
    private final JPanel panHaut = new JPanel();
    private final JPanel panCentre = new JPanel();
    private final JPanel panBas = new JPanel();

    private final JButton btnEnregistrerMateriel = new JButton("Enregistrer");
    private final JButton btnSupprimerMateriel = new JButton("Supprimer");
    private final JButton btnAnnuler = new JButton("Annuler");
    private final JButton btnVisuels = new JButton("visuels");
    private final JButton btnRechercher = new JButton("Rechercher");
    private final JButton btnSorties = new JButton("Sortie");
    private final JButton btnAnnonces = new JButton("Annonce(s)");
    private final JButton btnAjouterMateriel = new JButton("Ajouter");

    private final JTextField txtRechercher = new JTextField();
    private final JTable tableauMateriel = new JTable();
    List<Materiel> materiels = new ArrayList<>();
    List<Materiel> listRechercheMateriels = new ArrayList<>();

    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
    Categorie categorie;
    Sport sport;
    Utilisateur utilisateur;
    Materiel materiel, blankMateriel;
    Integer annonce_materiel_id;


    /**
     * Constructeur par defaut
     */
    public PageMateriels() {
        initialiserComposants();
    }

    /**
     * Constructeur
     *
     * @param: pUtilisateur
     */
    public PageMateriels(Utilisateur pUtilisateur) {
        this.utilisateur = pUtilisateur;
        if (utilisateur.getAdresses().size() == 0) {
            JOptionPane.showMessageDialog(null, "Merci de créer une adresse");
            return;
        }
        initialiserComposants();
    }

    /**
     * Constructeur
     *
     * @param: pCategorie
     */
    public PageMateriels(Categorie pCategorie) {
        this.categorie = pCategorie;
        initialiserComposants();
    }

    /**
     * Constructeur
     *
     * @param: pSport
     */
    public PageMateriels(Sport pSport) {
        this.sport = pSport;
        initialiserComposants();
    }

    /**
     * Constructeur
     *
     * @param: annonce_materiel_id
     */
    public PageMateriels(int annonce_materiel_id) throws BLLException {
        this.annonce_materiel_id = annonce_materiel_id;
        materiel = MaterielManager.getInstance().SelectById(annonce_materiel_id);
        materiels.add(materiel);
        initialiserComposants();
        TableModelMateriel model = new TableModelMateriel(materiels);
        tableauMateriel.setModel(model);
    }

    /**
     * Méthode qui affiche le graphisme de la page
     */
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
        if (annonce_materiel_id == null) {
            panBas.add(btnAjouterMateriel);
        }
        panBas.add(btnEnregistrerMateriel);
        panBas.add(btnSupprimerMateriel);
        panBas.add(btnAnnuler);
        panBas.add(btnVisuels);
        panBas.add(btnSorties);

        if (annonce_materiel_id == null) {
            panBas.add(btnAnnonces);
        }

        setContentPane(panPrincipal);

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
            listRechercheMateriels = new ArrayList<>();
            try {
                MaterielManager.getInstance().SelectAll();
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


        /*
         * Listenner btnAnnuler
         **/
        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par mot(s) clé(s) ");
            materiel = null;
            blankMateriel = null;
            displayRightTable();
        });

        /*
         * listenner sur le btnajouterMateriel pour ajouter une ligne vierge
         */
        btnAjouterMateriel.setSize(140, 50);
        btnAjouterMateriel.addActionListener(e -> {
            List<Materiel> allMateriels = null;
            try {
                allMateriels = MaterielManager.getInstance().SelectAll();
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }
            blankMateriel = new Materiel();
            materiels.add(blankMateriel);

            //////////////On récupére la plus haute id du tableau pour assigner blankMateriel à 1 au dessus ////////////////
            assert allMateriels != null;
            int idMax = allMateriels.get(0).getMateriel_id();

            for (Materiel allMateriel : allMateriels) {
                int materielId = allMateriel.getMateriel_id();
                if (materielId > idMax) {
                    idMax = materielId;
                }
            }
            //////////////////On Set les valeurs de blankMateriel pour insertion dans la base //////////////////////
            blankMateriel.setMateriel_id(idMax + 1);
            blankMateriel.setMateriel_nom("");
            blankMateriel.setMateriel_description("");

            if (utilisateur == null) {
                blankMateriel.setMateriel_adresse_id(1);
            } else {
                blankMateriel.setMateriel_adresse_id(utilisateur.getAdresses().get(0).getIdAdresse());
            }

            if (categorie == null) {
                blankMateriel.setMateriel_categorie_id(1);
            } else {
                blankMateriel.setMateriel_categorie_id(categorie.getCategorie_id());
            }

            if (sport == null) {
                blankMateriel.setMateriel_sport_id(1);
            } else {
                blankMateriel.setMateriel_sport_id(sport.getSport_id());
            }

            blankMateriel.setMateriel_prix(0.0);

            try {
                MaterielManager.getInstance().insert(blankMateriel);
                //   JOptionPane.showMessageDialog(btnAjouterMateriel, "Matériel ajouté");
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }

            TableModelMateriel model = new TableModelMateriel(materiels);
            model.fireTableDataChanged();
            tableauMateriel.revalidate();
            tableauMateriel.setModel(model);

            blankMateriel = null;
            displayRightTable();
        });

        /*
         * Listenner du bouton enregistrer les modifications
         **/
        btnEnregistrerMateriel.addActionListener(e -> {

            /* Récupérer les valeurs du tableauAnomalies, on vérifie pour chaque ligne */
            for (int i = 0; i < tableauMateriel.getRowCount(); i++) {

                try {
                    materiel = MaterielManager.getInstance().SelectById((Integer) tableauMateriel.getValueAt(i, 5));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }

                String nomMaterielModifie = String.valueOf(tableauMateriel.getValueAt(i, 0));
                String descriptionMaterielModifie = String.valueOf(tableauMateriel.getValueAt(i, 1));
                double prixMaterielModifie = (double) tableauMateriel.getValueAt(i, 6);
                double cautionPrixMaterielModifie = (double) tableauMateriel.getValueAt(i, 7);
                int idCategorieMaterielModifie = (int) tableauMateriel.getValueAt(i, 3);
                int idSportMaterielModifie = (int) tableauMateriel.getValueAt(i, 4);
                int idAdresseMaterielModifie = (int) tableauMateriel.getValueAt(i, 2);

//                tableauMateriel.setValueAt(nomMaterielModifie, i, 0);
//                tableauMateriel.setValueAt(descriptionMaterielModifie, i, 1);
//                tableauMateriel.setValueAt(idAdresseMaterielModifie, i, 2);
//                tableauMateriel.setValueAt(idCategorieMaterielModifie, i, 3);
//                tableauMateriel.setValueAt(idSportMaterielModifie, i, 4);
//                tableauMateriel.setValueAt(prixMaterielModifie, i, 6);
//                tableauMateriel.setValueAt(cautionPrixMaterielModifie, i, 7);

                /* ENREGISTRER LES VALEURS DS LA BASE ***/
                if (!materiel.getMateriel_nom().equals(nomMaterielModifie) || !materiel.getMateriel_description().equals(descriptionMaterielModifie) || materiel.getMateriel_prix() != prixMaterielModifie ||
                        materiel.getMateriel_categorie_id() != idCategorieMaterielModifie || materiel.getMateriel_adresse_id() != idAdresseMaterielModifie || materiel.getMateriel_sport_id() != idSportMaterielModifie ||
                        materiel.getMateriel_caution_prix() != cautionPrixMaterielModifie) {
                    materiel.setMateriel_description(descriptionMaterielModifie);
                    materiel.setMateriel_nom(nomMaterielModifie);
                    materiel.setMateriel_prix(prixMaterielModifie);
                    materiel.setMateriel_adresse_id(idAdresseMaterielModifie);
                    materiel.setMateriel_categorie_id(idCategorieMaterielModifie);
                    materiel.setMateriel_sport_id(idSportMaterielModifie);
                    materiel.setMateriel_caution_prix(cautionPrixMaterielModifie);

                    int j = JOptionPane.showConfirmDialog(btnEnregistrerMateriel, "La modification est irréversible. Êtes-vous sûr de vouloir enregistrer le matériel " + materiel.getMateriel_id() + " ?",
                            "Veuillez confirmer votre choix",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                    if (j == 0)  /*user a dit oui*/ {
                        try {
                            MaterielManager.getInstance().update(materiel);
                            JOptionPane.showMessageDialog(btnEnregistrerMateriel, "Matériel " + materiel.getMateriel_id() + " enregistré");
                            displayRightTable();
                            break;
                        } catch (BLLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }//fin if

            }//fin for
        });

        /*
         * Listenner du bouton supprimer qui supprime le materiel sélectionné
         **/
        btnSupprimerMateriel.addActionListener(e -> {
            if (materiel == null) {
                JOptionPane.showMessageDialog(btnSupprimerMateriel, "Merci de sélectionner un matériel");
                return;
            }
            int i = JOptionPane.showConfirmDialog(btnSupprimerMateriel, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer le matériel " + materiel.getMateriel_id() + " ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
            if (i == 0) //user a dit oui
            {
                try {
                    MaterielManager.getInstance().delete(materiel);
                    JOptionPane.showMessageDialog(btnSupprimerMateriel, "Matériel " + materiel.getMateriel_id() + " supprimé");
                    displayRightTable();
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /*
         * Mouse listenner sur le tableau materiel
         */
        tableauMateriel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idMaterielSelected = (int) tableauMateriel.getValueAt(tableauMateriel.getSelectedRow(), 5);
                //     JOptionPane.showMessageDialog(null, "Le materiel " + idMaterielSelected + " est sélectionné", null, JOptionPane.WARNING_MESSAGE);
                try {
                    materiel = MaterielManager.getInstance().SelectById(idMaterielSelected);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /*
         * listenner sur le btnVisuels
         */
        btnVisuels.setSize(100, 50);
        btnVisuels.addActionListener(e -> {
            if (materiel == null) {
                JOptionPane.showMessageDialog(btnVisuels, "Veuillez sélectionner un matériel");
            } else {
                new PageVisuels(materiel);
            }
        });

        /*
         * listenner sur le btnSorties
         */
        btnSorties.setSize(100, 50);
        btnSorties.addActionListener(e -> {
            if (materiel == null) {
                JOptionPane.showMessageDialog(btnSorties, "Veuillez sélectionner un matériel");
            } else {
                new PageSorties(materiel);
            }
        });

        /*
         * listenner sur le btnAnnonces
         */
        btnAnnonces.setSize(100, 50);
        btnAnnonces.addActionListener(e -> {
            if (materiel == null) {
                JOptionPane.showMessageDialog(btnSorties, "Veuillez sélectionner un matériel");
            } else {
                new PageAnnonces(materiel);
            }
        });

    }//fin initialiserComposants

    /**
     * Méthode qui affiche les materiel selon
     * le sport sélectionné
     */
    private void afficheJTableMaterielsWithIdSport() {
        try {
            materiels = remplirJTableWithMaterielsIdSport(sport.getSport_id());
            TableModelMateriel model = new TableModelMateriel(materiels);
            tableauMateriel.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

    /**
     * Méthode qui affiche les materiel de
     * la catégorie sélectionnée
     */
    private void afficheJTableMaterielsWithIdCategorie() {
        try {
            materiels = remplirJTableWithMaterielsIdCategorie(categorie.getCategorie_id());
            TableModelMateriel model = new TableModelMateriel(materiels);
            tableauMateriel.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

    /**
     * Méthode qui affiche les materiels de
     * l'adresse sélectionnée
     */
    private void afficheJTableMaterielsWithIdAdresse() {
        try {
            materiels = remplirJTableWithMaterielsIdAdresse(utilisateur.getAdresses().get(0).getIdAdresse());
            TableModelMateriel model = new TableModelMateriel(materiels);
            tableauMateriel.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

    /**
     * Méthode qui affiche tous les materiels
     */
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
     * méthode qui affiche du bon tableauMateriel selon la page de provenance
     **/
    public void displayRightTable() {
        if (utilisateur == null && categorie == null && sport == null && annonce_materiel_id == null) {
            afficheJTableWithAllMateriels();
        } else if (utilisateur == null && sport == null && annonce_materiel_id == null) {
            afficheJTableMaterielsWithIdCategorie();
        } else if (categorie == null && sport == null && annonce_materiel_id == null) {
            afficheJTableMaterielsWithIdAdresse();
        } else if (utilisateur == null && categorie == null && annonce_materiel_id == null) {
            afficheJTableMaterielsWithIdSport();
        }
    }

}//fin class
