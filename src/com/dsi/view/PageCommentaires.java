package com.dsi.view;

import com.dsi.controller.tableModel.TableModelCommentaire;
import com.dsi.model.beans.Annonce;
import com.dsi.model.beans.Commentaire;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.AnnonceManager;
import com.dsi.model.bll.CommentaireManager;
import com.dsi.model.bll.BLLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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

    private JButton btnModifierCommentaire = new JButton("Modifier commentaire");
    private JButton btnSupprimerCommentaire = new JButton("Supprimer commentaire");
    private JButton btnAnnuler = new JButton("Annuler");

    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauCommentaire = new JTable();
    List<Commentaire> commentaires = new ArrayList<>();
    List <Commentaire> listRechercheCommentaires = new ArrayList<>();

    Annonce annonce;
    Utilisateur utilisateur;
    Commentaire commentaire;
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
        setSize(900, 500);
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
        txtRechercher.setText("     Rechercher    ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        /*********************Panel centre******************************************/
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauCommentaire.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauCommentaire, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauCommentaire), BorderLayout.CENTER);

        /*********************Panel Bas******************************************/
        panBas.setSize(500, 200);
        panBas.add(btnModifierCommentaire);
        panBas.add(btnSupprimerCommentaire);
        panBas.add(btnAnnuler);


        setContentPane(panPrincipal);

        if (annonce ==  null && utilisateur == null){
            afficheJTableCommentaires();
        } else if (utilisateur == null){
            afficheJTableCommentairesWithIdAnnonce();
        } else if (annonce == null){
            afficheJTableCommentairesWithIdUtilisateur();
        }



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
            txtRechercher.setText("");
            commentaire = null;
            afficheJTableCommentaires();

        });

        btnModifierCommentaire.addActionListener(e -> {
        });

        btnSupprimerCommentaire.addActionListener(e -> {
            if (commentaire == null) {
                JOptionPane.showMessageDialog(btnSupprimerCommentaire, "Veuillez sélectionner une annonce");
                return;
            }
            CommentaireManager cm = CommentaireManager.getInstance();

            int i= JOptionPane.showConfirmDialog(btnSupprimerCommentaire, "La suppression est irréversible. Êtes-vous sûr de vouloir continuer ?",
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
        });

        /**
         * Mouse listenner sur le tableau commentaire
         */
        tableauCommentaire.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int id = (int) tableauCommentaire.getValueAt(tableauCommentaire.getSelectedRow(), 3);
                JOptionPane.showMessageDialog(null, "Le commentaire " + id + " est sélectionné");

                try {
                    commentaire = CommentaireManager.getInstance().SelectById(id);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }//fin initialiserComposants


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

