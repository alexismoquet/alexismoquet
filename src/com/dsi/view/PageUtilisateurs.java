package com.dsi.view;

import com.dsi.controller.tableModel.TableModelUtilisateur;
import com.dsi.model.beans.Adresse;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurManager;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    private JButton btnAjouterUtilisateur = new JButton("Ajouter une ligne");
    private JButton btnEnrModifUtil = new JButton("Enregistrer");
    private JButton btnSupprimerUtil = new JButton("Supprimer");
    private JButton btnAnnuler = new JButton("Annuler");
    private JButton btnAnnonce = new JButton("Annonces");
    private JButton btnAdresse = new JButton("Adresses");
    private JButton btnMateriel = new JButton("Materiels");
    private JButton btnCommentaire = new JButton("Commentaires");

    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauUtilisateur = new JTable();

    List<Utilisateur> utilisateurs = new ArrayList<>();
    List<Adresse> adresses = new ArrayList<>();
    List<Utilisateur> listRechercheUtilisateurs = new ArrayList<>();

    Utilisateur utilisateur, blankUtilisateur;

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
        txtRechercher.setText(" Rechercher par Nom ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauUtilisateur.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauUtilisateur, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauUtilisateur), BorderLayout.CENTER);
        tableauUtilisateur.setRowHeight(30);

        panBas.setSize(900, 100);
        panBas.add(btnEnrModifUtil);
        panBas.add(btnAjouterUtilisateur);
        panBas.add(btnSupprimerUtil);
        panBas.add(btnAnnuler);
        panBas.add(btnAnnonce);
        panBas.add(btnMateriel);
        panBas.add(btnCommentaire);
        panBas.add(btnAdresse);

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
            txtRechercher.setText(" Rechercher par Nom ");
            utilisateur = null;
            blankUtilisateur = null;
            afficheJTableUtilisateurs();
        });
        /**
         * listenner sur le btnajouterUtilisateur pour ajouter une ligne vierge
         */
        btnAjouterUtilisateur.setSize(140, 50);
        btnAjouterUtilisateur.addActionListener(e -> {
            blankUtilisateur = new Utilisateur();
            utilisateurs.add(blankUtilisateur);

            //////  On récupére la plus haute id du tableu pour assigner blankSport à 1 au dessus ////////////////
            int idMax = utilisateurs.get(0).getIdUtilisateur();

            for (Utilisateur value : utilisateurs) {
                int utilisateurId = value.getIdUtilisateur();
                if (utilisateurId > idMax) {
                    idMax = utilisateurId;
                }
            }
            blankUtilisateur.setIdUtilisateur(idMax + 1);
            blankUtilisateur.setNom("");
            blankUtilisateur.setPrenom("");
            blankUtilisateur.setEmail("");
            blankUtilisateur.setTelMob("");
            blankUtilisateur.setTelFix("");
            blankUtilisateur.setMotDePasse("");
            blankUtilisateur.setDateInscription(new Date());

            //////////////////////////////////////////////////////////////////////////////////////////////////////

            TableModelUtilisateur model = new TableModelUtilisateur(utilisateurs);
            model.fireTableDataChanged();
            tableauUtilisateur.revalidate();
            tableauUtilisateur.setModel(model);
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
                        utilisateur = UtilisateurManager.getInstance().SelectById((Integer) tableauUtilisateur.getValueAt(i, 7));
                    } catch (BLLException bllException) {
                        bllException.printStackTrace();
                    }
                    String nomUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 0));
                    String prenomUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 1));
                    String emailUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 2));
                    String telMobUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 3));
                    String telFixUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 4));
                    String mdpUtilisateurModifie = String.valueOf(tableauUtilisateur.getValueAt(i, 5));
                    Date dateInscUtilisateurModifie = (Date) tableauUtilisateur.getValueAt(i, 6);

                    if (utilisateur == null) {
                        JOptionPane.showMessageDialog(btnEnrModifUtil, "Veuillez sélectionner un utilisateur");
                    } else {
                        /*** ENREGISTRER LES VALEURS DS LA BASE ***/
                        if (    !utilisateur.getNom().equalsIgnoreCase(nomUtilisateurModifie) ||
                                !utilisateur.getPrenom().equalsIgnoreCase(prenomUtilisateurModifie) ||
                                !utilisateur.getEmail().equalsIgnoreCase(emailUtilisateurModifie) ||
                                !utilisateur.getTelMob().equalsIgnoreCase(telMobUtilisateurModifie) ||
                                !utilisateur.getTelFix().equalsIgnoreCase(telFixUtilisateurModifie) ||
                                !(utilisateur.getDateInscription() == dateInscUtilisateurModifie)
                        ) {
                            utilisateur.setNom(nomUtilisateurModifie);
                            utilisateur.setPrenom(prenomUtilisateurModifie);
                            utilisateur.setEmail(emailUtilisateurModifie);
                            utilisateur.setTelMob(telMobUtilisateurModifie);
                            utilisateur.setTelFix(telFixUtilisateurModifie);
                            utilisateur.setMotDePasse(mdpUtilisateurModifie);
                            utilisateur.setDateInscription(dateInscUtilisateurModifie);


                            int j = JOptionPane.showConfirmDialog(btnEnrModifUtil, "La modification est irréversible. Êtes-vous sûr de vouloir continuer ?",
                                    "Veuillez confirmer votre choix",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                            if (j == 0)  /**user a dit oui*/ {
                                try {
                                    if (blankUtilisateur != null) {
                                        um.insert(blankUtilisateur);
                                        JOptionPane.showMessageDialog(btnEnrModifUtil, "Utilisateur " + blankUtilisateur.getIdUtilisateur() + " ajouté. Retenez bien votre mot de passe, nous allons le crypter");
                                        blankUtilisateur = null;
                                        afficheJTableUtilisateurs();
                                        break;
                                    } else {
                                        um.update(utilisateur);
                                        afficheJTableUtilisateurs();
                                    }
                                }
                                catch(BLLException ex){
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
         * @param utilisateur
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

                ///////Jouer un son qd suppression/////////////
//                InputStream in = null;
//                try {
//                    in = new FileInputStream("0200.mp3");
//                } catch (FileNotFoundException fileNotFoundException) {
//                    fileNotFoundException.printStackTrace();
//                }
////                AudioStream as = null;
////                try {
////                    as = new AudioStream(in);
////                } catch (IOException ioException) {
////                    ioException.printStackTrace();
////                }
//                AudioPlayer.player.start(in);
//                AudioPlayer.player.stop(in);
                ////////////////////////////////

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
                int idUserSelected = (int) tableauUtilisateur.getValueAt(tableauUtilisateur.getSelectedRow(), 7);
             //   JOptionPane.showMessageDialog( null, "L'utilisateur " + idUserSelected + " est sélectionné");
                try {
                    utilisateur = UtilisateurManager.getInstance().SelectById(idUserSelected);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        /**
         * listenner sur le bouton annonce
         * @param utilisateur
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
         * listenner sur le bouton adresse
         * @param utilisateur
         */
        btnAdresse.setSize(100, 50);
        btnAdresse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (utilisateur == null) {
                    JOptionPane.showMessageDialog(btnAdresse, "Veuillez sélectionner un utilisateur");
                } else {
                    new PageAdresses(utilisateur);
                }
            }
        });

        /**
         * listenner sur le bouton materiel
         * @param utilisateur
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
         * @param utilisateur
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


