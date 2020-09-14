package com.dsi.view;

import com.dsi.controller.tableModel.TableModelUtilisateur;
import com.dsi.librairies.UMdp;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.dsi.controller.Utilisateurs.remplirJTableWithAllUtilisateurs;

/**
 * Classe PageUtilisateurs
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageUtilisateurs extends JFrame {

    private final JPanel panPrincipal = new JPanel();
    private final JPanel panHaut = new JPanel();
    private final JPanel panCentre = new JPanel();
    private final JPanel panBas = new JPanel();

    private final JButton btnAjouterUtilisateur = new JButton("Ajouter");
    private final JButton btnEnrModifUtil = new JButton("Enregistrer");
    private final JButton btnSupprimerUtil = new JButton("Supprimer");
    private final JButton btnAnnuler = new JButton("Annuler");
    // private JButton btnAnnonce = new JButton("Annonces");
    private final JButton btnAdresse = new JButton("Adresses");
    private final JButton btnMateriel = new JButton("Materiels");
    private final JButton btnCommentaire = new JButton("Commentaires");

    private final JTextField txtRechercher = new JTextField();
    private final JButton btnRechercher = new JButton("Rechercher");

    private final JTable tableauUtilisateur = new JTable();

    private final JLabel notice = new JLabel();

    List<Utilisateur> utilisateurs = new ArrayList<>();
    List<Utilisateur> listRechercheUtilisateurs = new ArrayList<>();

    Utilisateur utilisateur, blankUtilisateur;

    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");


    /**
     * Constructeur par defaut
     */
    public PageUtilisateurs() {
        initialiserComposants();
    }

    /**
     * Méthode qui affiche le graphisme de la page
     */
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

        panHaut.setPreferredSize(new Dimension(900, 75));
        panHaut.setLayout(new BorderLayout());
        txtRechercher.setText(" Rechercher un utilisateur par Nom ");
        panHaut.add(txtRechercher, BorderLayout.CENTER);
        txtRechercher.setSize(900, 35);
        panHaut.add(btnRechercher, BorderLayout.NORTH);
        btnRechercher.setSize(700, 20);
        panHaut.add(notice, BorderLayout.SOUTH);
        notice.setSize(900, 40);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauUtilisateur.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauUtilisateur, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauUtilisateur), BorderLayout.CENTER);
        tableauUtilisateur.setRowHeight(30);

        notice.setSize(500, 40);
        notice.setBackground(Color.white);
        notice.setText("CREATION UTILISATEUR :  1- créer une adresse  " +
                "     2- créer un matériel      3- créer une annonce" + "   " +
                "------  Attention, si création d'un nouveau mot de passe," +
                " créer un nouvel utilisateur");
      //  notice.setToolTipText(notice.getText());

        panBas.setSize(900, 100);
        panBas.add(btnEnrModifUtil);
        panBas.add(btnAjouterUtilisateur);
        panBas.add(btnSupprimerUtil);
        panBas.add(btnAnnuler);
        // panBas.add(btnAnnonce);
        panBas.add(btnMateriel);
        panBas.add(btnCommentaire);
        panBas.add(btnAdresse);

        setContentPane(panPrincipal);

        afficheJTableUtilisateurs();

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
            listRechercheUtilisateurs = new ArrayList<>();
            try {
                UtilisateurManager.getInstance().SelectAll();  //retourne une list d'utilisateurs = utilisateurs
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            for (Utilisateur utilisateur : utilisateurs) {
                String user = utilisateur.getNom().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (user.startsWith(recherche)) {
                    listRechercheUtilisateurs.add(utilisateur);
                    TableModelUtilisateur model;
                    model = new TableModelUtilisateur(listRechercheUtilisateurs);
                    tableauUtilisateur.setModel(model);
                }
            }
            if (listRechercheUtilisateurs.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucun utilisateur trouvé", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        /*
         * Listener bouton annuler
         */
        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher un utilisateur par Nom ");
            utilisateur = null;
            blankUtilisateur = null;
            afficheJTableUtilisateurs();
        });

        /*
         * listenner sur le btnAjouterUtilisateur pour ajouter une ligne vierge
         * @param: blankUtilisateur
         **/
        btnAjouterUtilisateur.setSize(140, 50);
        btnAjouterUtilisateur.addActionListener(e -> {
            List<Utilisateur> allUtilisateurs = null;
            try {
                allUtilisateurs = UtilisateurManager.getInstance().SelectAll();
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }
            blankUtilisateur = new Utilisateur();
            utilisateurs.add(blankUtilisateur);

            //////  On récupére la plus haute id du tableu pour assigner blankSport à 1 au dessus ////////////////

            if (utilisateurs != null) {

                assert allUtilisateurs != null;
                int idMax = allUtilisateurs.get(0).getIdUtilisateur();

                for (Utilisateur allUtilisateur : allUtilisateurs) {
                    int utilisateurId = allUtilisateur.getIdUtilisateur();
                    if (utilisateurId > idMax) {
                        idMax = utilisateurId;
                    }
                }
            }
            else{ blankUtilisateur.setIdUtilisateur(1);
            }
            blankUtilisateur.setNom("");
            blankUtilisateur.setPrenom("");
            blankUtilisateur.setEmail("");
            blankUtilisateur.setTelMob("");
            blankUtilisateur.setTelFix("");
            blankUtilisateur.setMotDePasse("");
            blankUtilisateur.setDateInscription(new Date());

            try {
                String input = JOptionPane.showInputDialog(null, "Merci de saisir le  MOT DE PASSE qui sera crypté");
                blankUtilisateur.setMotDePasse(input);

                UtilisateurManager.getInstance().insert(blankUtilisateur);
                //   JOptionPane.showMessageDialog(btnAjouterUtilisateur, "Utilisateur " + blankUtilisateur.getIdUtilisateur() + " ajouté");
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }

            TableModelUtilisateur model;
            model = new TableModelUtilisateur(utilisateurs);
            model.fireTableDataChanged();
            tableauUtilisateur.revalidate();
            tableauUtilisateur.setModel(model);

            blankUtilisateur = null;
            afficheJTableUtilisateurs();
        });

        /*
         * Listener bouton Modifier
         */
        btnEnrModifUtil.addActionListener(e -> {
            /* Récupérer les valeurs du tableauUtilisateur, on boucle pour chaque ligne */
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

//                tableauUtilisateur.setValueAt(nomUtilisateurModifie, i, 0);
//                tableauUtilisateur.setValueAt(prenomUtilisateurModifie, i, 1);
//                tableauUtilisateur.setValueAt(emailUtilisateurModifie, i, 2);
//                tableauUtilisateur.setValueAt(telMobUtilisateurModifie, i, 3);
//                tableauUtilisateur.setValueAt(telFixUtilisateurModifie, i, 4);
//                tableauUtilisateur.setValueAt(mdpUtilisateurModifie, i, 5);
//                tableauUtilisateur.setValueAt(dateInscUtilisateurModifie, i, 6);

                if (utilisateur == null) {
                    JOptionPane.showMessageDialog(btnEnrModifUtil, "Veuillez sélectionner un utilisateur");
                } else {
                    /* ENREGISTRE LES VALEURS DS LA BASE SI DIFFERENCE */
                    if (!utilisateur.getNom().equalsIgnoreCase(nomUtilisateurModifie) ||
                            !utilisateur.getPrenom().equalsIgnoreCase(prenomUtilisateurModifie) ||
                            !utilisateur.getEmail().equalsIgnoreCase(emailUtilisateurModifie) ||
                            !utilisateur.getTelMob().equalsIgnoreCase(telMobUtilisateurModifie) ||
                            !utilisateur.getTelFix().equalsIgnoreCase(telFixUtilisateurModifie) ||
                             !utilisateur.getMotDePasse().equalsIgnoreCase(mdpUtilisateurModifie)||
                           !(utilisateur.getDateInscription().equals(dateInscUtilisateurModifie))
                    ) {
                        utilisateur.setNom(nomUtilisateurModifie);
                        utilisateur.setPrenom(prenomUtilisateurModifie);
                        utilisateur.setEmail(emailUtilisateurModifie);
                        utilisateur.setTelMob(telMobUtilisateurModifie);
                        utilisateur.setTelFix(telFixUtilisateurModifie);
                        utilisateur.setMotDePasse(mdpUtilisateurModifie);
                        //     utilisateur.setMotDePasse(mdpUtilisateurModifie);
                        utilisateur.setDateInscription(dateInscUtilisateurModifie);

                        int j = JOptionPane.showConfirmDialog(btnEnrModifUtil, "La modification est irréversible. Êtes-vous sûr de vouloir enregistrer l'utilisateur " + utilisateur.getIdUtilisateur() + " ?",
                                "Veuillez confirmer votre choix",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                        if (j == 0)  /*user a dit oui*/ {
                            try {
                                UtilisateurManager.getInstance().update(utilisateur);
                                JOptionPane.showMessageDialog(btnEnrModifUtil, "Utilisateur " + utilisateur.getIdUtilisateur() + " enregistré");
                            } catch (BLLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }//fin for
            afficheJTableUtilisateurs();
        });

        /*
         *Listener bouton Supprimer
         */
        btnSupprimerUtil.addActionListener(e -> {
            if (utilisateur == null) {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un utilisateur");
            } else {
                int i = JOptionPane.showConfirmDialog(btnSupprimerUtil, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer l'utilisateur " + utilisateur.getIdUtilisateur() + " ?",
                        "Veuillez confirmer votre choix",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                ///////Joue un son qd suppression/////////////
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

                if (i == 0)  /*user a dit oui*/ {
                    try {
                        UtilisateurManager.getInstance().delete(utilisateur);
                        JOptionPane.showMessageDialog(btnSupprimerUtil, "Utilisateur " + utilisateur.getIdUtilisateur() + " supprimé");
                        afficheJTableUtilisateurs();

                    } catch (BLLException ex) {
                        ex.printStackTrace();
                    }
                    tableauUtilisateur.clearSelection();
                }
            }
        });

        /*
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

//        /**
//         * listenner sur le bouton annonce
//         * @param utilisateur
//         */
//        btnAnnonce.setSize(100, 50);
//        btnAnnonce.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                afficheJTableUtilisateurs();
//                if (utilisateur == null) {
//                    JOptionPane.showMessageDialog(btnAnnonce, "Veuillez sélectionner un utilisateur");
//                } else {
//                    new PageAnnonces(utilisateur);
//                }
//            }
//        });

        /*
         * listenner sur le bouton adresse
         */
        btnAdresse.setSize(100, 50);
        btnAdresse.addActionListener(e -> {
            if (utilisateur == null) {
                JOptionPane.showMessageDialog(btnAdresse, "Veuillez sélectionner un utilisateur");
            } else {
                new PageAdresses(utilisateur);
            }
        });

        /*
         * listenner sur le bouton materiel
         */
        btnMateriel.setSize(100, 50);
        btnMateriel.addActionListener(e -> {
            if (utilisateur == null) {
                JOptionPane.showMessageDialog(btnMateriel, "Veuillez sélectionner un utilisateur");
            } else {
                new PageMateriels(utilisateur);
            }
        });

        /*
         * listenner sur le bouton commentaire
         */
        btnCommentaire.setSize(100, 50);
        btnCommentaire.addActionListener(e -> {
            if (utilisateur == null) {
                JOptionPane.showMessageDialog(btnMateriel, "Veuillez sélectionner un utilisateur");
            } else {
                new PageCommentaires(utilisateur);
            }
        });

    } //fin initialiserComposants


    /**
     * Méthode qui affiche tous les utilisateurs
     */
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


