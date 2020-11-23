package com.dsi.view;

import com.dsi.controller.tableModels.TableModelCommentaire;
import com.dsi.model.beans.*;
import com.dsi.model.bll.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.dsi.controller.Commentaires.*;

/**
 * Classe PageCommentaires
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageCommentaires extends JFrame implements Serializable {

    private final JPanel panPrincipal = new JPanel();
    private final JPanel panHaut = new JPanel();
    private final JPanel panCentre = new JPanel();
    private final JPanel panBas = new JPanel();

    private final JButton btnModifierCommentaire = new JButton("Enregistrer");
    private final JButton btnSupprimerCommentaire = new JButton("Supprimer");
    private final JButton btnAnnuler = new JButton("Annuler");
    private final JButton btnAjouterCommentaire = new JButton("Ajouter");

    private final JTextField txtRechercher = new JTextField();
    private final JButton btnRechercher = new JButton("Rechercher");

    private final JTable tableauCommentaire = new JTable();
    private List<Commentaire> commentaires = new ArrayList<>();
    private List <Commentaire> listRechercheCommentaires = new ArrayList<>();

    private Annonce annonce;
    private Utilisateur utilisateur;
    private Commentaire commentaire;
    private Commentaire blankCommentaire;
    private final ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
    boolean verifSiAjout = false;


    /**
     * Constructeur par defaut
     */
    public PageCommentaires() {
        initialiserComposants();
    }

    /**
     * Constructeur
     * @param: pAnnonce
     */

    public PageCommentaires(Utilisateur pUtilisateur) {
        this.utilisateur = pUtilisateur;
        initialiserComposants();
    }

    public PageCommentaires( Annonce pAnnonce, Commentaire pCommentaire) {
        this.annonce = pAnnonce;
        this.commentaire = pCommentaire;
        initialiserComposants();
    }

    public PageCommentaires(Utilisateur pUtilisateur, Annonce pAnnonce, Commentaire pCommentaire) {
        this.utilisateur = pUtilisateur;
        this.annonce = pAnnonce;
        this.commentaire = pCommentaire;
        initialiserComposants();
    }

    public void initialiserComposants() {
        setTitle("Commentaires");
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

        panHaut.setPreferredSize(new Dimension(900, 75));
        txtRechercher.setText(" Rechercher par mot(s) clé(s) ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauCommentaire.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauCommentaire, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauCommentaire), BorderLayout.CENTER);
        tableauCommentaire.setRowHeight(30);

        panBas.setSize(500, 200);
        panBas.add(btnAjouterCommentaire);
        panBas.add(btnModifierCommentaire);
        panBas.add(btnSupprimerCommentaire);
        panBas.add(btnAnnuler);

        tableauCommentaire.setAutoCreateRowSorter(true);

        setContentPane(panPrincipal);

        if (annonce ==  null && utilisateur == null){
            panBas.remove(btnAjouterCommentaire);
        }
        displayRightTable();

        /*
         * Mouse listenner du champ de recherche qui efface le contenu du champ
         **/
        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtRechercher.setText("");
            }
        });

        /*
         * Mouse listenner du bouton de recherche
         **/
        btnRechercher.addActionListener(e -> {
            listRechercheCommentaires = new ArrayList<>();
            String recherche = txtRechercher.getText().toLowerCase();

            try {
                CommentaireManager.getInstance().SelectAll();
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            //boucle pour affhicher les commentaires contenant les lettre de la recherche
            for (Commentaire commentaireRech : commentaires) {
                String messComm = commentaireRech.getCommentaire_message().toLowerCase();
                if (messComm.contains(recherche)) {
                    listRechercheCommentaires.add(commentaireRech);
                    TableModelCommentaire model = new TableModelCommentaire(listRechercheCommentaires);
                    tableauCommentaire.setModel(model);
                }
            }
            if (listRechercheCommentaires.isEmpty()) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucun commentaire trouvé contenant les lettres : "+ recherche, "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        /*
         * Mouse listenner sur le bouton annuler
         */
        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par mot(s) clé(s) ");
            blankCommentaire = null;
            commentaire = null;
            displayRightTable();
        });

        /*
         * Mouse listenner sur le bouton supprimer
         */
        btnSupprimerCommentaire.addActionListener(e -> {
            if (commentaire == null) {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un commentaire");
                return;
            }
                ///On supprime tous les commentaires sélectionnés
            int[] selection = tableauCommentaire.getSelectedRows();
            for (int j : selection) {
                commentaire = commentaires.get(j);
                try {
                    commentaire = CommentaireManager.getInstance().SelectById(commentaire.getCommentaire_id());
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }

                int i = JOptionPane.showConfirmDialog(null, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer le commentaire " + commentaire.getCommentaire_id() + " ?",
                        "Veuillez confirmer votre choix",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                    if (i == 0) {//user a dit oui
                        try {
                        CommentaireManager.getInstance().delete(commentaire);
                        JOptionPane.showMessageDialog(null, "Commentaire " + commentaire.getCommentaire_id() + " supprimé");
                    } catch (BLLException ex) {
                        ex.printStackTrace();
                    }
               tableauCommentaire.clearSelection();
                }//fin if
            }//fin for
            displayRightTable();
        });

        /*
         * listenner sur le btnajouterSortie pour ajouter une ligne vierge
         */
        btnAjouterCommentaire.setSize(140, 50);
        btnAjouterCommentaire.addActionListener(e -> {
            verifSiAjout = true;
            blankCommentaire = new Commentaire();
            //////  On récupére la plus haute id du tableau pour assigner blankCommentaire à 1 au dessus ////////////////

           if (!(commentaires.isEmpty())){
               List<Commentaire>allCommentaires = null;
               try {
                   allCommentaires = CommentaireManager.getInstance().SelectAll();
               } catch (BLLException bllException) {
                   bllException.printStackTrace();
               }
            assert allCommentaires != null;
            int idMax = allCommentaires.get(0).getCommentaire_id();

            for (Commentaire allCommentaire : allCommentaires) {
                int commentaireId = allCommentaire.getCommentaire_id();
                if (commentaireId > idMax) {
                    idMax = commentaireId;
                     }
                }
               blankCommentaire.setCommentaire_id(idMax);
           } else {
               blankCommentaire.setCommentaire_id(1);
           }
            blankCommentaire.setCommentaire_note(0);
            blankCommentaire.setCommentaire_message("");
            blankCommentaire.setCommentaire_date_parution(new Date());

            //On affecte les valeurs à blankCommentaire selon la page de provenance
            if (utilisateur != null){
                blankCommentaire.setCommentaire_utilisateur_id(utilisateur.getIdUtilisateur());
            } else {
                JOptionPane.showMessageDialog(null, "merci de remplir l'IdUtilisateur initialisé à 1 par défaut");
                blankCommentaire.setCommentaire_utilisateur_id(1);
            }
            if (annonce != null){
                blankCommentaire.setCommentaire_annonce_id(annonce.getAnnonce_id());
            }else{
                JOptionPane.showMessageDialog(null, "merci de remplir l'IdAnnonce initialisé à 1 par défaut");
                blankCommentaire.setCommentaire_annonce_id(1);
            }
            try {
                CommentaireManager.getInstance().insert(blankCommentaire);
              //  JOptionPane.showMessageDialog(btnAjouterCommentaire, "Commentaire ajouté");
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }

            commentaires.add(blankCommentaire);
            TableModelCommentaire model = new TableModelCommentaire(commentaires);
            model.fireTableDataChanged();
            tableauCommentaire.revalidate();
            tableauCommentaire.setModel(model);

            blankCommentaire = null;
            displayRightTable();
        });

        /*
         * listenner sur le bouton btnModifierCommentaire qui enregistre les modifications dans la base
         */
        btnModifierCommentaire.setSize(140, 50);
        btnModifierCommentaire.addActionListener(e -> {

            /* Récupérer les valeurs du tableauSortie, on boucle pour chaque ligne */
            for (int i = 0; i < tableauCommentaire.getRowCount(); i++) {
                try {
                    commentaire = CommentaireManager.getInstance().SelectById((Integer) tableauCommentaire.getValueAt(i, 3));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                String commentaireMessageModifie = String.valueOf(tableauCommentaire.getValueAt(i, 0));
                int commentaireNoteModifie = (int) tableauCommentaire.getValueAt(i, 2);
                int idCommentaireModifie = (int) tableauCommentaire.getValueAt(i, 3);
                int commentaireIdUtilisateurModifie = (int) tableauCommentaire.getValueAt(i, 4);
                int commentaireIdAnnonceModifie = (int) tableauCommentaire.getValueAt(i, 5);
                Date dateParutionAnnonceModifie = (Date) tableauCommentaire.getValueAt(i, 1);

                if (commentaireIdAnnonceModifie ==0 || commentaireIdUtilisateurModifie ==0){
                    JOptionPane.showMessageDialog(null, "Merci de corriger les champs IidUtilisateur et/ou IdAnnonce");
                    return;
                }

                if (commentaire == null) {
                    return;
                } else {
                    /* ENREGISTRER LES VALEURS DS LA BASE si une d'entre elles est différente ***/
                    if (commentaire.getCommentaire_note() != (commentaireNoteModifie) || !commentaire.getCommentaire_message().equalsIgnoreCase(commentaireMessageModifie)
                            || (commentaire.getCommentaire_id() != idCommentaireModifie)
                            || (commentaire.getCommentaire_annonce_id() != commentaireIdAnnonceModifie)
                            || (commentaire.getCommentaire_utilisateur_id() != commentaireIdUtilisateurModifie)
                            || !(commentaire.getCommentaire_date_parution().equals(dateParutionAnnonceModifie))
                    ) {
                        commentaire.setCommentaire_message(commentaireMessageModifie);
                        commentaire.setCommentaire_id(idCommentaireModifie);
                        commentaire.setCommentaire_note(commentaireNoteModifie);
                        commentaire.setCommentaire_annonce_id(commentaireIdAnnonceModifie);
                        commentaire.setCommentaire_utilisateur_id(commentaireIdUtilisateurModifie);
                        commentaire.setCommentaire_date_parution(dateParutionAnnonceModifie);

                        int j;
                        String confirmChoix = "Veuillez confirmer votre choix";
                        if (verifSiAjout) {
                            j = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir enregistrer le commentaire " + commentaire.getCommentaire_id() + " ?",
                                    confirmChoix, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                            verifSiAjout = false;
                        } else {
                            j = JOptionPane.showConfirmDialog(null, "La modification est irréversible. Êtes-vous sûr de vouloir enregistrer le commentaire " + commentaire.getCommentaire_id() + " ?",
                                    confirmChoix, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                        }
                        if (j == 0)  /*user a dit oui*/ {
                            try {
                                    CommentaireManager.getInstance().update(commentaire);
                                    JOptionPane.showMessageDialog(btnModifierCommentaire, "Commentaire " + commentaire.getCommentaire_id() + " enregistré");
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
         * Mouse listenner sur le tableau commentaire
         */
        tableauCommentaire.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int id = (int) tableauCommentaire.getValueAt(tableauCommentaire.getSelectedRow(), 3);
                try {
                    commentaire = CommentaireManager.getInstance().SelectById(id);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }//fin initialiserComposants

    /**
     * Méthode qui affiche le tableau de tous les commentaires
     */
    private void afficheJTableCommentaires() {
        try {
            commentaires = remplirJTableWithCommentaires();
            TableModelCommentaire model = new TableModelCommentaire(commentaires);
            tableauCommentaire.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

    /**
     * Méthode qui affiche les commentaires selon l'annonce sélectionnée
     */
    private void afficheJTableCommentairesWithIdAnnonce() {
        try {
            commentaires = remplirJTableWithCommentairesIdAnnonce(annonce.getAnnonce_id());
            TableModelCommentaire model = new TableModelCommentaire(commentaires);
            tableauCommentaire.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }

    } //fin afficheJTable

    /**
     * Méthode qui affiche les commentaires selon l'utilisateur sélectionné
     */
    private void afficheJTableCommentairesWithIdUtilisateur() {
        try {
            commentaires = remplirJTableWithCommentairesIdUtilisateur(utilisateur.getIdUtilisateur());
            TableModelCommentaire model = new TableModelCommentaire(commentaires);
            tableauCommentaire.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

    /**
     * Méthode qui affiche le bon tableau selon la page de provenance
     */
    private void displayRightTable(){
        if (annonce ==  null && utilisateur == null){
            afficheJTableCommentaires();
        }
        if (annonce != null){
            afficheJTableCommentairesWithIdAnnonce();
        }
        if (utilisateur != null){
            afficheJTableCommentairesWithIdUtilisateur();
        }
    }

}//fin class

