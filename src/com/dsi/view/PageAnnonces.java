package com.dsi.view;

import com.dsi.controller.tableModel.TableModelAnnonce;
import com.dsi.model.beans.*;
import com.dsi.model.bll.AnnonceManager;
import com.dsi.model.bll.BLLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.dsi.controller.Annonces.*;

/**
 * Classe PageAnnonces
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageAnnonces extends JFrame {

    private final JPanel panPrincipal = new JPanel();
    private final JPanel panHaut = new JPanel();
    private final JPanel panCentre = new JPanel();
    private final JPanel panBas = new JPanel();

    private final JButton btnEnrAnnonce = new JButton("Enregistrer");
    private final JButton btnAjouterLigne = new JButton("Ajouter");
    private final JButton btnSupprimerAnnonce = new JButton("Supprimer");
    private final JButton btnAnnuler = new JButton("Annuler");
    private final JButton btnCommentaires = new JButton("Commentaires");
    //private final JButton btnMateriels = new JButton("Materiels");

    private final JTextField txtRechercher = new JTextField();
    private final JButton btnRechercher = new JButton("Rechercher");

    private final JTable tableauAnnonce = new JTable();
    List<Annonce> annonces = new ArrayList<>();

    List<Annonce> listRechercheAnnonces = new ArrayList<>();
    Annonce annonce, blankAnnonce;
    Materiel materiel;
    Commentaire commentaire;
    ImageIcon icone = new ImageIcon ("LogoIconeDSI.png");
    Utilisateur utilisateur;
    boolean verifSiAJout = false;

    /**
     * Constructeur par defaut
     */
    public PageAnnonces() {
        initialiserComposants();
    }

    /**
     * Constructeur
     * @param: pUtilisateur
     */
    public PageAnnonces (Utilisateur pUtilisateur) {
        this.utilisateur = pUtilisateur;
        initialiserComposants();
    }

    /**
     * Constructeur
     * @param: pMateriel, pUtilisateur
     */
    public PageAnnonces(Materiel pMateriel, Utilisateur pUtilisateur) {
        this.materiel = pMateriel;
        this.utilisateur = pUtilisateur;
        initialiserComposants();
    }

    /**
     * Méthode qui affiche le graphisme de la page
     */
    public void initialiserComposants() {
        setTitle("Annonces");
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
        panCentre.add(tableauAnnonce.getTableHeader(), BorderLayout.NORTH);
        tableauAnnonce.setRowHeight(30);

        //ScrollPane
        JScrollPane scrollPane = new JScrollPane(tableauAnnonce);
        panCentre.add(scrollPane, BorderLayout.CENTER);
        panCentre.add(tableauAnnonce, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauAnnonce), BorderLayout.CENTER);

        panBas.setSize(500, 200);
        panBas.add(btnEnrAnnonce);
        if (materiel != null || utilisateur != null){
            panBas.add(btnAjouterLigne);
        }
        panBas.add(btnSupprimerAnnonce);
        panBas.add(btnAnnuler);
        panBas.add(btnCommentaires);

//        if (materiel != null) {
//            panBas.add(btnMateriels);
//        }

        tableauAnnonce.setAutoCreateRowSorter(true);

        diplayRightTable();

        setContentPane(panPrincipal);

        /*
         * Mouse listenner du champ de recherche qui efface le contenu du champ
         **/
        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField txtRechercher = ((JTextField) e.getSource());
                txtRechercher.setText("");
            }
        });

        /*
         * Mouse listenner du bouton de recherche
         **/
        btnRechercher.addActionListener(e -> {
            listRechercheAnnonces = new ArrayList<>();
            try {
               annonces = AnnonceManager.getInstance().SelectAll();
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            for (Annonce annonce : annonces) {
                String titreAnnonce = annonce.getAnnonce_titre().toLowerCase();
                String descriptionAnnonce = annonce.getAnnonce_description().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (titreAnnonce.contains(recherche) || descriptionAnnonce.contains(recherche)) {
                    listRechercheAnnonces.add(annonce);
                    TableModelAnnonce model = new TableModelAnnonce(listRechercheAnnonces);
                    tableauAnnonce.setModel(model);
                }
            }
            if (listRechercheAnnonces.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucune annonce trouvée", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        /*
         * Mouse listenner sur le tableau annonce
         */
        tableauAnnonce.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idAnnonceSelected = (int) tableauAnnonce.getValueAt(tableauAnnonce.getSelectedRow(), 3);
          //      JOptionPane.showMessageDialog(null, "L'annonce " + idAnnonceSelected + " est sélectionnée");
                try {
                    annonce = AnnonceManager.getInstance().SelectById(idAnnonceSelected);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
                //Gêne pour modifier une ligne du tableauAnnonce//  JOptionPane.showMessageDialog(null, "L'annonce " + idAnnonceSelected + " est sélectionnée");
            }
        });

        /*
         * Mouse listenner sur le bouton annuler
         */
        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par mot(s) clé(s) ");
            annonce = null;
            blankAnnonce = null;
            diplayRightTable();
        });

        /*
         * listenner sur le btnAjouterAnnonce pour ajouter une ligne vierge
         */
        btnAjouterLigne.setSize(140, 50);
        btnAjouterLigne.addActionListener(e -> {
            verifSiAJout = true;
            blankAnnonce = new Annonce();

            //////  On récupére la plus haute id du tableau pour assigner blankSport à 1 au dessus ////////////////
            if (annonces.size() != 0){
                List<Annonce>allAnnonces = null;
                try {
                    allAnnonces = AnnonceManager.getInstance().SelectAll();
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }

            assert allAnnonces != null;
            int idMax = allAnnonces.get(0).getAnnonce_id();

            for (Annonce allAnnonce : allAnnonces) {
                int annonceId = allAnnonce.getAnnonce_id();
                if (annonceId > idMax) {
                    idMax = annonceId;
                    }
                }
                blankAnnonce.setAnnonce_id(idMax);
            } else {
                blankAnnonce.setAnnonce_id(1);
            }

            blankAnnonce.setAnnonce_titre("");
            blankAnnonce.setAnnonce_description("");
            blankAnnonce.setAnnonce_date_parution(new Date());

            if (annonces.size() > 1){
                JOptionPane.showMessageDialog(null, "Merci de créer un nouveau matériel");
                return;
            }

            if (materiel == null){
                JOptionPane.showMessageDialog(null, "Merci de créer une annonce avec le bouton Matériels de la page Utilisateurs");
                return;
                } else {
                blankAnnonce.setAnnonce_materiel_id(materiel.getMateriel_id()); }

            if (utilisateur == null){
                    blankAnnonce.setAnnonce_utilisateur_id(1);
                } else{ blankAnnonce.setAnnonce_utilisateur_id(utilisateur.getIdUtilisateur()); }

            annonces.add(blankAnnonce);

            try {
                AnnonceManager.getInstance().insert(blankAnnonce);
              //  JOptionPane.showMessageDialog(btnAjouterLigne, "Annonce ajoutée");
            } catch (BLLException bllException) {
                bllException.printStackTrace();
                JOptionPane.showMessageDialog(btnAjouterLigne, "Merci de créer un nouveau matériel");
            }

            TableModelAnnonce model = new TableModelAnnonce(annonces);
            model.fireTableDataChanged();
            tableauAnnonce.revalidate();
            tableauAnnonce.setModel(model);

            blankAnnonce = null;
            diplayRightTable();
        });

        /*
         * listenner sur le btnEnrAnnonce pour enregistrer les modifications
         */
        btnEnrAnnonce.addActionListener(e -> {
            if (annonce != null) {

                /* Récupérer les valeurs du tableauAnomalies, on vérifie pour chaque ligne */
                for (int i = 0; i < tableauAnnonce.getRowCount(); i++) {

                    try {
                        annonce = AnnonceManager.getInstance().SelectById((Integer) tableauAnnonce.getValueAt(i, 3));
                    } catch (BLLException bllException) {
                        bllException.printStackTrace();
                    }
                    String titreAnnonceModifie = String.valueOf(tableauAnnonce.getValueAt(i, 0));
                    String descriptionAnnonceModifie = String.valueOf(tableauAnnonce.getValueAt(i, 1));
                    int annonceIdUtilisateurModifie = (int) tableauAnnonce.getValueAt(i, 2);
                    int annonceIdMaterielModifie = (int) tableauAnnonce.getValueAt(i, 4);
                    Date annonceDateParutionModifie = (Date) tableauAnnonce.getValueAt(i, 5);

//                tableauAnnonce.setValueAt(titreAnnonceModifie, i, 0);
//                tableauAnnonce.setValueAt(descriptionAnnonceModifie, i, 1);
//                tableauAnnonce.setValueAt(annonceIdUtilisateurModifie, i, 2);
//                tableauAnnonce.setValueAt(annonceIdMaterielModifie, i, 4);
//                tableauAnnonce.setValueAt(annonceDateParutionModifie, i, 5);

                    /* ENREGISTRER LES VALEURS DS LA BASE ***/
                    if (!annonce.getAnnonce_titre().equals(titreAnnonceModifie)
                            || !annonce.getAnnonce_description().equals(descriptionAnnonceModifie)
                            || annonce.getAnnonce_utilisateur_id() != annonceIdUtilisateurModifie
                            || annonce.getAnnonce_materiel_id() != annonceIdMaterielModifie
                            || !annonce.getAnnonce_date_parution().equals(annonceDateParutionModifie)) {
                        try {
                            annonce.setAnnonce_description(descriptionAnnonceModifie);
                            annonce.setAnnonce_titre(titreAnnonceModifie);
                            annonce.setAnnonce_utilisateur_id(annonceIdUtilisateurModifie);
                            annonce.setAnnonce_materiel_id(annonceIdMaterielModifie);
                            annonce.setAnnonce_date_parution(annonceDateParutionModifie);

                            int j;
                            if (verifSiAJout) {
                                j = JOptionPane.showConfirmDialog(btnEnrAnnonce, "Êtes-vous sûr de vouloir enregistrer l'annonce " + annonce.getAnnonce_id() + " ?",
                                        "Veuillez confirmer votre choix",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                                verifSiAJout = false;
                            } else {
                                j = JOptionPane.showConfirmDialog(btnEnrAnnonce, "La modification est irréversible. Êtes-vous sûr de vouloir enregistrer l'annonce " + annonce.getAnnonce_id() + " ?",
                                        "Veuillez confirmer votre choix",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                            }

                            if (j == 0)  /*user a dit oui*/ {
                                AnnonceManager.getInstance().update(annonce);
                                JOptionPane.showMessageDialog(null, "Annonce " + tableauAnnonce.getValueAt(i, 3) + " enregistrée");
                            }
                        } catch (BLLException bllException) {
                            bllException.printStackTrace();
                        }
                    }
                }//fin boucle for
                diplayRightTable();
            }
        });

        /*
         * listenner sur le btnSupprimerAnnonce pour supprimer l'annonce sélectionnée
         */
        btnSupprimerAnnonce.addActionListener(e -> {
            if (annonce == null) {
                JOptionPane.showMessageDialog(btnSupprimerAnnonce, "Veuillez sélectionner une annonce");
                return;
            }
                    ///Supprime tous les annonces sélectionnées
                    int[] selection = tableauAnnonce.getSelectedRows();
                    for (int j : selection) {
                        annonce = annonces.get(j);
                        try {
                            annonce = AnnonceManager.getInstance().SelectById(annonce.getAnnonce_id());
                        } catch (BLLException bllException) {
                            bllException.printStackTrace();
                        }
                        int i = JOptionPane.showConfirmDialog(btnSupprimerAnnonce, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer l'annonce " + annonce.getAnnonce_id() + " ?",
                                "Veuillez confirmer votre choix",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                        if (i == 0) //user a dit oui
                        {
                            try {
                                AnnonceManager.getInstance().delete(annonce);
                                JOptionPane.showMessageDialog(btnSupprimerAnnonce, "Annonce " + annonce.getAnnonce_id() + " supprimée");
                            } catch (BLLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }//fin for
            diplayRightTable();
        });

        /*
         * Action listenner sur le bouton commentaire
         */
        btnCommentaires.setSize(100, 50);
        btnCommentaires.addActionListener(e -> {
            if (annonce == null) {
                JOptionPane.showMessageDialog(btnCommentaires, "Veuillez sélectionner une annonce");
            } else if  (utilisateur == null){
                new PageCommentaires(annonce, commentaire);
            } else {
                new PageCommentaires(utilisateur, annonce, commentaire);
            }
        });

//        /*
//         * Action listenner sur le bouton materiel
//         */
//        btnMateriels.setSize(100, 50);
//        btnMateriels.addActionListener(e -> {
//            if (annonce == null) {
//                JOptionPane.showMessageDialog(btnMateriels, "Veuillez sélectionner une annonce");
//            } else {
//                new PageMateriels(annonce.getAnnonce_materiel_id());
//            }
//        });

    }//fin initialiserComposants

    /**
     * Méthode qui affiche toutes les annonces
     */
    private void afficheJTableWithAllAnnonces() {
        try {
            annonces = remplirJTableWithAllAnnonces();
            TableModelAnnonce model = new TableModelAnnonce(annonces);
            tableauAnnonce.setModel(model);

        } catch (BLLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui affiche l'annonce de l'utilisateur sélectionnée
     */
    private void afficheJTableAnnoncesIdUtilisateur() {
        try {
            annonces = remplirJTableWithAnnoncesIdUser(utilisateur.getIdUtilisateur());
            TableModelAnnonce model = new TableModelAnnonce(annonces);
            tableauAnnonce.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Méthode qui affiche l'annonce du matériel sélectionné
     */
    private void afficheJTableAnnoncesIdMateriel() {
        try {
            annonces = remplirJTableWithAnnoncesIdMateriel(materiel.getMateriel_id());
            TableModelAnnonce model = new TableModelAnnonce(annonces);
            tableauAnnonce.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Méthode qui affiche le bon tableau selon la page de provenance
     */
    public void diplayRightTable() {
        if (utilisateur == null && materiel == null) {
            afficheJTableWithAllAnnonces();
        } else if (materiel == null){
            afficheJTableAnnoncesIdUtilisateur();
        } else {
            afficheJTableAnnoncesIdMateriel();
        }
    }

}//fin class
