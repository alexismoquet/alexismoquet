package com.dsi.view;

import com.dsi.controller.tableModel.TableModelUtilisateur;
import com.dsi.model.beans.Adresse;
import com.dsi.model.beans.Materiel;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.AdresseManager;
import com.dsi.model.bll.AnnonceManager;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static com.dsi.controller.Utilisateurs.remplirJTableWithAllUtilisateurs;

/**
 * Classe PageUtilisateurs
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageUtilisateurs extends JFrame {

    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnEnrModifUtil = new JButton("Enregistrer");
    private JButton btnSupprimerUtil = new JButton("Supprimer");
    private JButton btnAnnuler = new JButton("Annuler");
    private JButton btnAnnonce = new JButton("Annonces");
    private JButton btnMateriel = new JButton("Materiels");
    private JButton btnCommentaire = new JButton("Commentaires");

    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauUtilisateur = new JTable();

    List<Utilisateur> utilisateurs = new ArrayList<>();
    List<Adresse> adresses = new ArrayList<>();
    List<Utilisateur> listRechercheUtilisateurs = new ArrayList<>();

    Utilisateur utilisateur;

    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");


    //************************************************************
    // Constructeur par defaut
    //************************************************************
    public PageUtilisateurs() {
        initialiserComposants();
    }

    //************************************************************
    // Methode qui va charger les composants de la fenetre
    //************************************************************
    public void initialiserComposants() {
        setTitle("Utilisateurs");
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
        txtRechercher.setText("Rechercher par Nom");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauUtilisateur.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauUtilisateur, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauUtilisateur), BorderLayout.CENTER);

        panBas.setSize(900, 100);
        panBas.add(btnEnrModifUtil);
        panBas.add(btnSupprimerUtil);
        panBas.add(btnAnnuler);
        panBas.add(btnAnnonce);
        panBas.add(btnMateriel);
        panBas.add(btnCommentaire);

        btnAnnonce.setVisible(true);
        btnMateriel.setVisible(true);

        setContentPane(panPrincipal);

        afficheJTableUtilisateurs();

        /**************************************************************************************************************************************/
        /*************************************************************** Les listenners *******************************************************/
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
            listRechercheUtilisateurs = new ArrayList<>();
            UtilisateurManager um = UtilisateurManager.getInstance();
            try {
                um.SelectAll();  //retourne une list d'utilisateurs = utilisateurs
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            for (Utilisateur utilisateur : utilisateurs) {
                String user = utilisateur.getNom().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (user.startsWith(recherche)) {
                    listRechercheUtilisateurs.add(utilisateur);
                    TableModelUtilisateur model = new TableModelUtilisateur(listRechercheUtilisateurs);
                    tableauUtilisateur.setModel(model);
                }
            }
            if (listRechercheUtilisateurs.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucun utilisateur trouvé", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        /**
         * Listener bouton annuler
         */
        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText("");
            utilisateur = null;
            afficheJTableUtilisateurs();
        });


        /**
         * Listener bouton Modifier
         */
        btnEnrModifUtil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilisateurManager um = UtilisateurManager.getInstance();

                /** Récupérer les valeurs du tableauUtilisateur, on boucle pour chaque ligne */
                for (int i = 0; i < tableauUtilisateur.getRowCount(); i++) {

                    try {
                        utilisateur = UtilisateurManager.getInstance().SelectById((Integer) tableauUtilisateur.getValueAt(i, 11));
                    } catch (BLLException bllException) {
                        bllException.printStackTrace();
                    }
                    String nomUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 0));
                    String prenomUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 1));
                    String emailUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 2));
                    String telMobUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 3));
                    String telFixUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 4));
                    String adresseUtilisateurModifiee = String.valueOf(tableauUtilisateur.getValueAt(i, 5));
                    String villeUtilisateurModifiee = String.valueOf(tableauUtilisateur.getValueAt(i, 6));
                    String cpUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 7));
                    String complementUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 8));
                    String departementUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 9));
                    String paysUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 10));

                    tableauUtilisateur.setValueAt(nomUtilisateurModifie, i, 0);
                    tableauUtilisateur.setValueAt(prenomUtilisateurModifie, i, 1);
                    tableauUtilisateur.setValueAt(emailUtilisateurModifie, i, 2);
                    tableauUtilisateur.setValueAt(telMobUtilisateurModifie, i, 3);
                    tableauUtilisateur.setValueAt(telFixUtilisateurModifie, i, 4);
                    tableauUtilisateur.setValueAt(adresseUtilisateurModifiee, i, 5);
                    tableauUtilisateur.setValueAt(villeUtilisateurModifiee, i, 6);
                    tableauUtilisateur.setValueAt(cpUtilisateurModifie, i, 7);
                    tableauUtilisateur.setValueAt(complementUtilisateurModifie, i, 8);
                    tableauUtilisateur.setValueAt(departementUtilisateurModifie, i, 9);
                    tableauUtilisateur.setValueAt(paysUtilisateurModifie, i, 10);

                    if (utilisateur == null) {
                        JOptionPane.showMessageDialog(btnEnrModifUtil, "Veuillez sélectionner un utilisateur");
                    } else {
                        /*** ENREGISTRER LES VALEURS DS LA BASE ***/
                        if (!utilisateur.getNom().equalsIgnoreCase(nomUtilisateurModifie) ||
                                !utilisateur.getPrenom().equalsIgnoreCase(prenomUtilisateurModifie) ||
                                !utilisateur.getEmail().equalsIgnoreCase(emailUtilisateurModifie) ||
                                !utilisateur.getTelMob().equalsIgnoreCase(telMobUtilisateurModifie) ||
                                !utilisateur.getTelFix().equalsIgnoreCase(telFixUtilisateurModifie)
                        ) {
                            utilisateur.setNom(nomUtilisateurModifie);
                            utilisateur.setPrenom(prenomUtilisateurModifie);
                            utilisateur.setEmail(emailUtilisateurModifie);
                            utilisateur.setTelMob(telMobUtilisateurModifie);
                            utilisateur.setTelFix(telFixUtilisateurModifie);

                            int j = JOptionPane.showConfirmDialog(btnEnrModifUtil, "La modification est irréversible. Êtes-vous sûr de vouloir continuer ?",
                                    "Veuillez confirmer votre choix",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                            if (j == 0)  /**user a dit oui*/ {
                                try {
                                    um.update(utilisateur);
                                    afficheJTableUtilisateurs();
                                } catch (BLLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
     });


        /**
         *Listener bouton Supprimer
         */
        btnSupprimerUtil.addActionListener(e -> {
            if (utilisateur == null) {
                JOptionPane.showMessageDialog(btnAnnonce, "Veuillez sélectionner un utilisateur");
                return;
            } else {
                UtilisateurManager um = UtilisateurManager.getInstance();

                int i = JOptionPane.showConfirmDialog(btnSupprimerUtil, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer l'utilisateur "+utilisateur.getIdUtilisateur()+" ?",
                        "Veuillez confirmer votre choix",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                if (i == 0)  /**user a dit oui*/ {
                    try {
                        um.delete(utilisateur);
                        JOptionPane.showMessageDialog(btnSupprimerUtil, "Utilisateur " + utilisateur.getIdUtilisateur() + " supprimé");
                        afficheJTableUtilisateurs();
                    } catch (BLLException ex) {
                        ex.printStackTrace();
                    }
                    tableauUtilisateur.clearSelection();
                }
            }
        });

        /**
         * Mouse listenner sur le tableau utilisateur
         */
        tableauUtilisateur.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idUserSelected = (int) tableauUtilisateur.getValueAt(tableauUtilisateur.getSelectedRow(), 11);
                JOptionPane.showMessageDialog( null, "L'utilisateur " + idUserSelected + " est sélectionné");

                try {
                    utilisateur = UtilisateurManager.getInstance().SelectById(idUserSelected);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        /**
         * listenner sur le bouton annonce
         */
        btnAnnonce.setSize(100, 50);
        btnAnnonce.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (utilisateur == null) {
                    JOptionPane.showMessageDialog(btnAnnonce, "Veuillez sélectionner un utilisateur");
                } else {
                    new PageAnnonces(utilisateur);
                }
            }
        });

        /**
         * listenner sur le bouton materiel
         */
        btnMateriel.setSize(100, 50);
        btnMateriel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (utilisateur == null) {
                    JOptionPane.showMessageDialog(btnMateriel, "Veuillez sélectionner un utilisateur");
                } else {
                    new PageMateriels(utilisateur);
                }
            }
        });

        /**
         * listenner sur le bouton commentaire
         */
        btnCommentaire.setSize(100, 50);
        btnCommentaire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (utilisateur == null) {
                    JOptionPane.showMessageDialog(btnMateriel, "Veuillez sélectionner un utilisateur");
                } else {
                    new PageCommentaires(utilisateur);
                }
            }
        });
    } //fin initialiserComposants


    private void afficheJTableUtilisateurs() {
        try {
            utilisateurs = remplirJTableWithAllUtilisateurs();
            TableModelUtilisateur model = new TableModelUtilisateur(utilisateurs);
            tableauUtilisateur.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable


}// fin class


