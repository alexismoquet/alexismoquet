package com.dsi.view;

import com.dsi.controller.tableModel.TableModelAnnonce;
import com.dsi.model.beans.*;
import com.dsi.model.bll.AnnonceManager;
import com.dsi.model.bll.BLLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnModifierAnnonce = new JButton("Enregistrer");
    private JButton btnAjouterLigne = new JButton("Ajouter une ligne");
    private JButton btnSupprimerAnnonce = new JButton("Supprimer");
    private JButton btnAnnuler = new JButton("Annuler");
    private JButton bCommentaires = new JButton("Commentaires");
    private JButton bMateriels = new JButton("Materiels");

    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauAnnonce = new JTable();
    List<Annonce> annonces = new ArrayList<>();

    List<Annonce> listRechercheAnnonces = new ArrayList<>();
    Categorie categorie;
    Annonce annonce, blankAnnonce;
    Materiel materiel;
    ImageIcon icone = new ImageIcon ("LogoIconeDSI.png");
    Utilisateur utilisateur;

    List<Integer>annonceIdsMateriel;

    /************************************************************/
    /******************** Constructeurs**************************/
    /************************************************************/
    public PageAnnonces() {
        initialiserComposants();
    }

    public PageAnnonces(Utilisateur pUtilisateur) {
        this.utilisateur = pUtilisateur;
        initialiserComposants();
    }

    public PageAnnonces(Materiel pMateriel) {
        this.materiel = pMateriel;
        initialiserComposants();
    }

    /************************************************************/

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
        panBas.add(btnModifierAnnonce);
        panBas.add(btnAjouterLigne);
        panBas.add(btnSupprimerAnnonce);
        panBas.add(btnAnnuler);
        panBas.add(bCommentaires);
        panBas.add(bMateriels);

        diplayRightTable();

        setContentPane(panPrincipal);

/**************************************************************************************************************************************/
/*************************************************************** Les listenners des boutons *******************************************************/
/**************************************************************************************************************************************/


        /****Mouse listenner du champ de recherche  ***********************/
        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField txtRechercher = ((JTextField) e.getSource());
                txtRechercher.setText("");
            }
        });

        btnRechercher.addActionListener(e -> {
            listRechercheAnnonces = new ArrayList<>();
            AnnonceManager um = AnnonceManager.getInstance();
            try {
                um.SelectAll();
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


        /**
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



        /*************************************************************************************************/
        /***************************  ACTION LISTENNERS DES BOUTONS DU PANEL BAS  ************************/
        /*************************************************************************************************/

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par mot(s) clé(s) ");
            annonce = null;
            blankAnnonce = null;
           diplayRightTable();
        });

        /**
         * listenner sur le btnAjouterAnnonce pour ajouter une ligne vierge
         * @param blankAnnonce
         */
        btnAjouterLigne.setSize(140, 50);
        btnAjouterLigne.addActionListener(e -> {
            blankAnnonce = new Annonce();
            annonces.add(blankAnnonce);

            //////  On récupére la plus haute id du tableu pour assigner blankSport à 1 au dessus ////////////////
            int idMax = annonces.get(0).getAnnonce_id();

            for (int i = 0; i < annonces.size(); i++) {
                int annonceId = annonces.get(i).getAnnonce_id();
                if (annonceId > idMax) {
                    idMax = annonceId;
                }
            }
            blankAnnonce.setAnnonce_id(idMax + 1);
            blankAnnonce.setAnnonce_titre("");
            blankAnnonce.setAnnonce_date_parution(new Date());
            blankAnnonce.setAnnonce_materiel_id((annonces.get(annonces.size()-2).getAnnonce_materiel_id())+1);
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            if (utilisateur != null){
              blankAnnonce.setAnnonce_utilisateur_id(utilisateur.getIdUtilisateur());
            }
            if (materiel != null){
                blankAnnonce.setAnnonce_materiel_id(materiel.getMateriel_id());
            }
            if (materiel != null && utilisateur != null){
                blankAnnonce.setAnnonce_utilisateur_id(utilisateur.getIdUtilisateur());
                blankAnnonce.setAnnonce_materiel_id(materiel.getMateriel_id());
            }

            TableModelAnnonce model = new TableModelAnnonce(annonces);
            model.fireTableDataChanged();
            tableauAnnonce.revalidate();
            tableauAnnonce.setModel(model);
        });


        /**
         * listenner sur le btnModifierAnnonce pour enregistrer les modifications
         */
        btnModifierAnnonce.addActionListener(e -> {

            /** Récupérer les valeurs du tableauAnomalies, on vérifie pour chaque ligne */
            for (int i = 0; i < tableauAnnonce.getRowCount(); i++) {

                try {
                    annonce = AnnonceManager.getInstance().SelectById((Integer) tableauAnnonce.getValueAt(i, 3));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                String titreAnnonceModifie = String.valueOf(tableauAnnonce.getValueAt(i, 0));
                String descriptionAnnonceModifie = String.valueOf(tableauAnnonce.getValueAt(i, 1));
                int idUtilisateur = (int) tableauAnnonce.getValueAt(i, 2);

                tableauAnnonce.setValueAt(titreAnnonceModifie, i, 0);
                tableauAnnonce.setValueAt(descriptionAnnonceModifie, i, 1);
                tableauAnnonce.setValueAt(idUtilisateur, i, 2);


                /*** ENREGISTRER LES VALEURS DS LA BASE ***/
                if (!annonce.getAnnonce_titre().equals(titreAnnonceModifie) || !annonce.getAnnonce_description().equals(descriptionAnnonceModifie)) {
                    try {
                        annonce.setAnnonce_description(descriptionAnnonceModifie);
                        annonce.setAnnonce_titre(titreAnnonceModifie);

                        int j = JOptionPane.showConfirmDialog(btnModifierAnnonce, "La modification est irréversible. Êtes-vous sûr de vouloir continuer ?",
                                "Veuillez confirmer votre choix",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                        if (j == 0)  /**user a dit oui*/ {
                            if (blankAnnonce != null) {
                                AnnonceManager.getInstance().insert(blankAnnonce);
                                JOptionPane.showMessageDialog(btnModifierAnnonce, "Annonce " + blankAnnonce.getAnnonce_id() + " ajoutée");
                                blankAnnonce = null;
                                break;
                            } else {
                                AnnonceManager.getInstance().update(annonce);
                                JOptionPane.showMessageDialog(null, "Annonce " + tableauAnnonce.getValueAt(i, 3) + " modifiée");
                                diplayRightTable();
                            }
                        }
                    } catch (BLLException bllException) {
                        bllException.printStackTrace();
                    }
                }
            }//fin boucle for
            tableauAnnonce.clearSelection();
        });


        /**
         * listenner sur le btnSupprimerAnnonce pour supprimer une annonce
         */
        btnSupprimerAnnonce.addActionListener(e -> {
            if (annonce == null) {
                JOptionPane.showMessageDialog(btnSupprimerAnnonce, "Veuillez sélectionner une annonce");
                return;
            }
            AnnonceManager am = AnnonceManager.getInstance();

            int i = JOptionPane.showConfirmDialog(btnSupprimerAnnonce, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer l'annonce " + annonce.getAnnonce_id() + " ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
            if (i == 0) //user a dit oui
            {
                try {
                    am.delete(annonce);
                    JOptionPane.showMessageDialog(btnSupprimerAnnonce, "Annonce " + annonce.getAnnonce_id() + " supprimée");
                   diplayRightTable();
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        /**
         * Action listenner sur le bouton commentaire
         */
        bCommentaires.setSize(100, 50);
        bCommentaires.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (annonce == null) {
               //     JOptionPane.showMessageDialog(bCommentaires, "Veuillez sélectionner une annonce");
                } else {
                    new PageCommentaires(annonce);
                }
            }
        });

        /**
         * Action listenner sur le bouton materiel
         */
        bMateriels.setSize(100, 50);
        bMateriels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (annonce == null) {
                    JOptionPane.showMessageDialog(bMateriels, "Veuillez sélectionner une annonce");
                } else {
                    try {
                        new PageMateriels(annonce.getAnnonce_materiel_id());
                    } catch (BLLException bllException) {
                        bllException.printStackTrace();
                    }
                }
            }
        });
    }//fin initialiserComposants


    private void afficheJTableWithAllAnnonces() {
        try {
            annonces = remplirJTableWithAllAnnonces();
            TableModelAnnonce model = new TableModelAnnonce(annonces);
            tableauAnnonce.setModel(model);

        } catch (BLLException e) {
            e.printStackTrace();
        }
    }


    private void afficheJTableAnnoncesIdUtilisateur() {
        try {
            annonces = remplirJTableWithAnnoncesIdUser(utilisateur.getIdUtilisateur());
            TableModelAnnonce model = new TableModelAnnonce(annonces);
            tableauAnnonce.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    }

    private void afficheJTableAnnoncesIdMateriel() {
        try {
            annonces = remplirJTableWithAnnoncesIdMateriel(materiel.getMateriel_id());
            TableModelAnnonce model = new TableModelAnnonce(annonces);
            tableauAnnonce.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    }

    public void diplayRightTable() {
        if (utilisateur == null && materiel == null) {
            afficheJTableWithAllAnnonces();
        } else if (materiel == null){
            afficheJTableAnnoncesIdUtilisateur();
        } else if (utilisateur == null){
            afficheJTableAnnoncesIdMateriel();
        }
    }

}//fin class
