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
    private JButton btnVisuels = new JButton("visuels");


    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");
    private JButton bMateriels = new JButton("Materiels");

    private JTable tableauAnnonce = new JTable();
    List<Annonce> annonces = new ArrayList<>();
    List<Visuel> visuels = new ArrayList<>();
    List <Annonce> listRechercheAnnonces = new ArrayList<>();
    List <Visuel> listRechercheVisuels = new ArrayList<>();
    Annonce annonce;
    Adresse adresse;
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
        txtRechercher.setText("     Rechercher     ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauAnnonce.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauAnnonce, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauAnnonce), BorderLayout.CENTER);

        panBas.setSize(500, 200);
        panBas.add(btnModifierAnnonce);
        panBas.add(btnSupprimerAnnonce);
        panBas.add(btnAnnuler);
        panBas.add(bCommentaires);
        panBas.add(bMateriels);
        panBas.add(btnVisuels);
        bMateriels.setVisible(true);

        setContentPane(panPrincipal);

        afficheJTableAnnoncesIdUtilisateur(utilisateur.getIdUtilisateur());

/**************************************************************************************************************************************/
/*************************************************************** Les listenners des boutons *******************************************************/
/**************************************************************************************************************************************/

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

        btnVisuels.addActionListener(e -> {
            listRechercheVisuels = new ArrayList<>();
            VisuelManager vm = VisuelManager.getInstance();
            try {
                visuels = vm.SelectAll();
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            for (Visuel visuel : visuels) {
                    listRechercheVisuels.add(visuel);
                    TableModelVisuel model = new TableModelVisuel(listRechercheVisuels);
                    tableauAnnonce.setModel(model);
                }

            if (listRechercheVisuels.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucun visuel trouvé", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText("");
            annonce = null;
            afficheJTableAnnoncesIdUtilisateur(utilisateur.getIdUtilisateur());

        });

        btnModifierAnnonce.addActionListener(e -> {
        });

        btnSupprimerAnnonce.addActionListener(e -> {
            AnnonceManager am = AnnonceManager.getInstance();

            int i= JOptionPane.showConfirmDialog(btnSupprimerAnnonce, "La suppression est irréversible. Êtes-vous sûr de vouloir continuer ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,icone);
            if (i==0) //user a dit oui
            {
                try {
                    am.delete(annonce);
                    afficheJTableAnnoncesIdUtilisateur(utilisateur.getIdUtilisateur());
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
                tableauAnnonce.clearSelection();
            }
        });

        /**
         * Mouse listenner sur le tableau annonce
         */
        tableauAnnonce.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idAnnonceSelected = (int) tableauAnnonce.getValueAt(tableauAnnonce.getSelectedRow(), 3);

                JOptionPane.showMessageDialog( bMateriels, "L'annonce " + idAnnonceSelected + " est sélectionnée");
                try {
                    annonce = AnnonceManager.getInstance().SelectById(idAnnonceSelected);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /**
         * Action listenner sur le bouton materiel
         */
        bMateriels.setSize(100,50);
        bMateriels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (annonce == null){
                    JOptionPane.showMessageDialog( bMateriels, "veuillez sélectionner une annonce");
                } else {
                    PageMateriels pm = new PageMateriels(annonce);
                }
            }
        });


        /**
         * Action listenner sur le bouton commentaire
         */
        bCommentaires.setSize(100,50);
        bCommentaires.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {PageCommentaires pc = new PageCommentaires(annonce); }
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

    }//fin afficheJTable

}//fin class
