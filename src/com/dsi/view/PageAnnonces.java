package com.dsi.view;

import com.dsi.controller.tableModel.TableModelAnnonce;
import com.dsi.controller.tableModel.TableModelVisuel;
import com.dsi.model.beans.*;
import com.dsi.model.bll.AnnonceManager;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.VisuelManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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

    private JButton btnModifierAnnonce = new JButton("Modifier");
    private JButton btnSupprimerAnnonce = new JButton("Supprimer");
    private JButton btnAnnuler = new JButton("Annuler");
    private JButton bCommentaires = new JButton("Commentaires");


    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauAnnonce = new JTable();
    List<Annonce> annonces = new ArrayList<>();

    List<Annonce> listRechercheAnnonces = new ArrayList<>();

    Annonce annonce;
    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
    Utilisateur utilisateur;

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

    public PageAnnonces(Annonce pAnnonce) {
        this.annonce = pAnnonce;
        initialiserComposants();
    }

    /************************************************************/

    public void initialiserComposants() {
        setTitle("Annonces");
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
        txtRechercher.setText("     Rechercher par mot(s)    ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauAnnonce.getTableHeader(), BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(tableauAnnonce);
        panCentre.add(scrollPane, BorderLayout.CENTER);
        panCentre.add(tableauAnnonce, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauAnnonce), BorderLayout.CENTER);

        panBas.setSize(500, 200);
        panBas.add(btnModifierAnnonce);
        panBas.add(btnSupprimerAnnonce);
        panBas.add(btnAnnuler);
        panBas.add(bCommentaires);

        setContentPane(panPrincipal);

        if (utilisateur == null) {
            afficheJTableWithAllAnnonces();
        } else {
            afficheJTableAnnoncesIdUtilisateur(utilisateur.getIdUtilisateur());
        }


/**************************************************************************************************************************************/
/*************************************************************** Les listenners des boutons *******************************************************/
/**************************************************************************************************************************************/


/****Mouse listenner du champ de recherche  ***********************/
        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField txtRechercher = ((JTextField) e.getSource());
                txtRechercher.setText("");
                txtRechercher.removeMouseListener(this);
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
                String sp = annonce.getAnnonce_titre().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (sp.contains(recherche)) {
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

                //  JOptionPane.showMessageDialog( bCommentaires, "L'annonce " + idAnnonceSelected + " est sélectionnée");
                try {
                    annonce = AnnonceManager.getInstance().SelectById(idAnnonceSelected);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        /*************************************************************************************************/
        /***************************  ACTION LISTENNERS DES BOUTONS DU PANEL BAS  ************************/
        /*************************************************************************************************/

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText("");
            annonce = null;
            afficheJTableAnnoncesIdUtilisateur(utilisateur.getIdUtilisateur());
        });

        btnModifierAnnonce.addActionListener(e -> {
        });

        btnSupprimerAnnonce.addActionListener(e -> {
            AnnonceManager am = AnnonceManager.getInstance();

            int i = JOptionPane.showConfirmDialog(btnSupprimerAnnonce, "La suppression est irréversible. Êtes-vous sûr de vouloir continuer ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
            if (i == 0) //user a dit oui
            {
                try {
                    am.delete(annonce);
                    JOptionPane.showMessageDialog(btnSupprimerAnnonce, "Annonce " + annonce.getAnnonce_id() + " supprimée");
                    if (utilisateur == null) {
                        afficheJTableWithAllAnnonces();
                        tableauAnnonce.clearSelection();
                    } else {
                        afficheJTableAnnoncesIdUtilisateur(utilisateur.getIdUtilisateur());
                        tableauAnnonce.clearSelection();
                    }
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
                    JOptionPane.showMessageDialog(bCommentaires, "veuillez sélectionner une annonce");
                } else {
                    new PageCommentaires(annonce);
                }
            }
        });
    }//fin initialiserComposants


    private void afficheJTableAnnoncesIdUtilisateur(int pIdUtilisateur) {
        try {
            annonces = remplirJTableWithAnnoncesIdUser(utilisateur.getIdUtilisateur());
            TableModelAnnonce model = new TableModelAnnonce(annonces);
            tableauAnnonce.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    }

    private void afficheJTableWithAllAnnonces() {
        try {
            annonces = remplirJTableWithAllAnnonces();
            TableModelAnnonce model = new TableModelAnnonce(annonces);
            tableauAnnonce.setModel(model);
        } catch (BLLException e) {
            e.printStackTrace();
        }
    }

}//fin class
