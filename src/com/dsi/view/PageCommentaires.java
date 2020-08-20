package com.dsi.view;

import com.dsi.controller.tableModel.TableModelCommentaire;
import com.dsi.controller.tableModel.TableModelSortie;
import com.dsi.model.beans.Annonce;
import com.dsi.model.beans.Commentaire;
import com.dsi.model.beans.Sortie;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class PageCommentaires extends JFrame {

    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnModifierCommentaire = new JButton("Enregistrer");
    private JButton btnSupprimerCommentaire = new JButton("Supprimer");
    private JButton btnAnnuler = new JButton("Annuler");
    private JButton btnAjouterCommentaire = new JButton("Ajouter commentaire");

    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauCommentaire = new JTable();
    List<Commentaire> commentaires = new ArrayList<>();
    List <Commentaire> listRechercheCommentaires = new ArrayList<>();

    Annonce annonce;
    Utilisateur utilisateur;
    Commentaire commentaire, blankCommentaire;
    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");


    /************************************************************/
    /******************** Constructeur par defaut****************/
    /************************************************************/
    public PageCommentaires() {
        initialiserComposants();
    }

    public PageCommentaires(Annonce annonce) {
        this.annonce = annonce;
        initialiserComposants();
    }

    public PageCommentaires(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        initialiserComposants();
    }

    public void initialiserComposants() {
        setTitle("Commentaires");
        setIconImage(Toolkit.getDefaultToolkit().getImage("LogoIconeDSI.png"));
        setSize(1100, 700);
        setVisible(true);
        setResizable(true);

        /*********************Panel Principal******************************************/
        panPrincipal.setLayout(new BorderLayout());
        panPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        panPrincipal.setBackground(Color.decode("#11417d"));
        panPrincipal.add(panHaut, BorderLayout.NORTH);
        panPrincipal.add(panCentre, BorderLayout.CENTER);
        panPrincipal.add(panBas, BorderLayout.SOUTH);

        /*********************Panel haut******************************************/
        panHaut.setPreferredSize(new Dimension(900, 100));
        txtRechercher.setText(" Rechercher par mot(s) clé(s) ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        /*********************Panel centre******************************************/
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauCommentaire.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauCommentaire, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauCommentaire), BorderLayout.CENTER);
        tableauCommentaire.setRowHeight(30);

        /*********************Panel Bas******************************************/
        panBas.setSize(500, 200);
        panBas.add(btnAjouterCommentaire);
        panBas.add(btnModifierCommentaire);
        panBas.add(btnSupprimerCommentaire);
        panBas.add(btnAnnuler);

        setContentPane(panPrincipal);

        displayRightTable();


        /**************************************************************************************************************************************/
        /*************************************************************** Les listenners *******************************************************/
        /**************************************************************************************************************************************/

        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtRechercher.setText("");
                afficheJTableCommentaires();
            }
        });

        btnRechercher.addActionListener(e -> {
            listRechercheCommentaires = new ArrayList<>();
            CommentaireManager um = CommentaireManager.getInstance();
            try {
                um.SelectAll();
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            for (Commentaire commentaire : commentaires) {
                String cm = commentaire.getCommentaire_message().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (cm.contains(recherche)) {
                    listRechercheCommentaires.add(commentaire);
                    TableModelCommentaire model = new TableModelCommentaire(listRechercheCommentaires);
                    tableauCommentaire.setModel(model);
                }
            }
            if (listRechercheCommentaires.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucun commentaire trouvé contenant ce(s) mot(s)", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par mot(s) clé(s) ");
            blankCommentaire = null;
            commentaire = null;
            displayRightTable();
        });

        btnSupprimerCommentaire.addActionListener(e -> {
            if (commentaire == null) {
                JOptionPane.showMessageDialog(btnSupprimerCommentaire, "Veuillez sélectionner un commentaire");
                return;
            }
            CommentaireManager cm = CommentaireManager.getInstance();

            int i= JOptionPane.showConfirmDialog(btnSupprimerCommentaire, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer le commentaire "+commentaire.getCommentaire_id()+" ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,icone);
            if (i==0) //user a dit oui
            {
                try {
                    cm.delete(commentaire);
                    afficheJTableCommentaires();
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
                tableauCommentaire.clearSelection();
            }
            displayRightTable();
        });

        /**
         * listenner sur le btnajouterSortie pour ajouter une ligne vierge
         */
        btnAjouterCommentaire.setSize(140, 50);
        btnAjouterCommentaire.addActionListener(e -> {
            blankCommentaire = new Commentaire();

            commentaires.add(blankCommentaire);

            //////  On récupére la plus haute id du tableau pour assigner blankCommentaire à 1 au dessus ////////////////
            int idMax = commentaires.get(0).getCommentaire_id();

            for (int i = 0; i < commentaires.size(); i++) {
                int commentaireId = commentaires.get(i).getCommentaire_id();
                if (commentaireId > idMax) {
                    idMax = commentaireId;
                }
            }
            blankCommentaire.setCommentaire_id(idMax + 1);

            if (utilisateur != null){
                blankCommentaire.setCommentaire_utilisateur_id(utilisateur.getIdUtilisateur());
            }
            if (annonce != null){
                blankCommentaire.setCommentaire_annonce_id(annonce.getAnnonce_id());
            }
            blankCommentaire.setCommentaire_note(0);
            blankCommentaire.setCommentaire_date_parution(new Date());

            try {
                CommentaireManager.getInstance().insert(blankCommentaire);
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            TableModelCommentaire model = new TableModelCommentaire(commentaires);
            model.fireTableDataChanged();
            tableauCommentaire.revalidate();
            tableauCommentaire.setModel(model);
        });

        /**
         * listenner sur le bouton Enregistrer les modifications dans la base
         */
        btnModifierCommentaire.setSize(140, 50);
        btnModifierCommentaire.addActionListener(e -> {
            CommentaireManager cm = CommentaireManager.getInstance();

            /** Récupérer les valeurs du tableauSortie, on boucle pour chaque ligne */
            for (int i = 0; i < tableauCommentaire.getRowCount(); i++) {
                try {
                    commentaire = CommentaireManager.getInstance().SelectById((Integer) tableauCommentaire.getValueAt(i, 3));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }

                String commentaireModifie = String.valueOf(tableauCommentaire.getValueAt(i, 0));
                int idCommentaireModifie = (int) tableauCommentaire.getValueAt(i, 3);
                int commentaireNoteModifie = (int) tableauCommentaire.getValueAt(i, 2);

                if (commentaire == null) {
                    //JOptionPane.showMessageDialog(btnModifierCommentaire, "Merci d'ajouter le message");
                    return;
                } else {
                    /*** ENREGISTRER LES VALEURS DS LA BASE ***/
                    if (commentaire.getCommentaire_note() != (commentaireNoteModifie) || !commentaire.getCommentaire_message().equalsIgnoreCase(commentaireModifie) || !(commentaire.getCommentaire_id() == idCommentaireModifie)) {
                        commentaire.setCommentaire_message(commentaireModifie);
                        commentaire.setCommentaire_id(idCommentaireModifie);
                        commentaire.setCommentaire_note(commentaireNoteModifie);

                        int j = JOptionPane.showConfirmDialog(btnModifierCommentaire, "La modification est irréversible. Êtes-vous sûr de vouloir continuer ?",
                                "Veuillez confirmer votre choix",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                        if (j == 0)  /**user a dit oui*/ {
                            try {
                                if (blankCommentaire != null) {
                                    CommentaireManager.getInstance().insert(commentaire);
                                    JOptionPane.showMessageDialog(btnModifierCommentaire, "Commentaire " + blankCommentaire.getCommentaire_id() + " ajouté");
                                    blankCommentaire = null;
                                    break;
                                } else {
                                    CommentaireManager.getInstance().update(commentaire);
                                    JOptionPane.showMessageDialog(btnModifierCommentaire, "Commentaire " + commentaire.getCommentaire_id() + " modifié");
                                   // displayRightTable();
                                    break;
                                }
                            } catch (BLLException ex) {
                                ex.printStackTrace();
                            }
                        } else {break;}
                    }
                }
            }//fin for
        });


        /**
         * Mouse listenner sur le tableau commentaire
         */
        tableauCommentaire.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int id = (int) tableauCommentaire.getValueAt(tableauCommentaire.getSelectedRow(), 3);
            //    JOptionPane.showMessageDialog(null, "Le commentaire " + id + " est sélectionné");

                try {
                    commentaire = CommentaireManager.getInstance().SelectById(id);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }//fin initialiserComposants

    private void displayRightTable(){
        if (annonce ==  null && utilisateur == null){
            afficheJTableCommentaires();
        } else if (utilisateur == null){
            afficheJTableCommentairesWithIdAnnonce();
        } else if (annonce == null){
            afficheJTableCommentairesWithIdUtilisateur();
        }
    }

    private void afficheJTableCommentaires() {
        try {
            commentaires = remplirJTableWithCommentaires();
            TableModelCommentaire model = new TableModelCommentaire(commentaires);
            tableauCommentaire.setModel(model);

        } catch (BLLException ex) {
            ex.printStackTrace();
        }

    } //fin afficheJTable

    private void afficheJTableCommentairesWithIdAnnonce() {
        try {
            commentaires = remplirJTableWithCommentairesIdAnnonce(annonce.getAnnonce_id());
            TableModelCommentaire model = new TableModelCommentaire(commentaires);
            tableauCommentaire.setModel(model);

        } catch (BLLException ex) {
            ex.printStackTrace();
        }

    } //fin afficheJTable


    private void afficheJTableCommentairesWithIdUtilisateur() {
        try {
            commentaires = remplirJTableWithCommentairesIdUtilisateur(utilisateur.getIdUtilisateur());
            TableModelCommentaire model = new TableModelCommentaire(commentaires);
            tableauCommentaire.setModel(model);

        } catch (BLLException ex) {
            ex.printStackTrace();
        }

    } //fin afficheJTable

}//fin class

