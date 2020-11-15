package com.dsi.view;

import com.dsi.controller.tableModels.TableModelAdresse;
import com.dsi.model.beans.Adresse;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.AdresseManager;
import com.dsi.model.bll.BLLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.dsi.controller.Adresses.remplirJTableWithAdressesIdUser;
import static com.dsi.controller.Adresses.remplirJTableWithAllAdresses;

/**
 * Classe PageAdresses
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageAdresses extends JFrame {

    private final JPanel panPrincipal = new JPanel();
    private final JPanel panHaut = new JPanel();
    private final JPanel panCentre = new JPanel();
    private final JPanel panBas = new JPanel();

    private final JButton btnEnrAdresse = new JButton("Enregistrer");
    private final JButton btnAjouterLigne = new JButton("Ajouter");
    private final JButton btnSupprimerAdresse = new JButton("Supprimer");
    private final JButton btnAnnuler = new JButton("Annuler");

    private final JTextField txtRechercher = new JTextField();
    private final JButton btnRechercher = new JButton("Rechercher");

    private final JTable tableauAdresse = new JTable();
    private List<Adresse> adresses = new ArrayList<>();

    private List<Adresse> listRechercheAdresses = new ArrayList<>();
    private Adresse adresse;
    private Adresse blankAdresse;
    private final ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
    private Utilisateur utilisateur;
    boolean verifSiAjout = false;

    /**
     * Constructeur par defaut
     */
    public PageAdresses() {
        initialiserComposants();
    }

    /**
     * Constructeur
     *
     * @param: pUtilisateur
     */
    public PageAdresses(Utilisateur pUtilisateur) {
        this.utilisateur = pUtilisateur;
        initialiserComposants();
    }



    public void initialiserComposants() {
        setTitle("Adresses");
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
        panCentre.add(tableauAdresse.getTableHeader(), BorderLayout.NORTH);
        tableauAdresse.setRowHeight(30);

        //ScrollPane
        JScrollPane scrollPane = new JScrollPane(tableauAdresse);
        panCentre.add(scrollPane, BorderLayout.CENTER);

        panCentre.add(tableauAdresse, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauAdresse), BorderLayout.CENTER);

        panBas.setSize(500, 200);
        panBas.add(btnEnrAdresse);
        if (utilisateur != null) {
            panBas.add(btnAjouterLigne);
        }
        panBas.add(btnSupprimerAdresse);
        panBas.add(btnAnnuler);

        tableauAdresse.setAutoCreateRowSorter(true);

        setContentPane(panPrincipal);

        displayRightTable();

        /*
        Mouse listenner du champ de recherche
        */
        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtRechercher.setText("");
            }
        });

        /*
         * Mouse listenner du bouton de recherche
         */
        btnRechercher.addActionListener(e -> {
            listRechercheAdresses = new ArrayList<>();
            try {
                AdresseManager.getInstance().SelectAll();
            } catch (BLLException ex) {
                ex.printStackTrace();
            }

            for (Adresse adresseRech : adresses) {
                String titreAdresse = adresseRech.getAdresse().toLowerCase();
                String ville = adresseRech.getVille().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (titreAdresse.contains(recherche) || ville.contains(recherche)) {
                    listRechercheAdresses.add(adresseRech);
                    TableModelAdresse model = new TableModelAdresse(listRechercheAdresses);
                    tableauAdresse.setModel(model);
                }
            }//fin for

            if (listRechercheAdresses.isEmpty()) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucune adresse trouvée", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        /*
         * Mouse listenner sur le tableau adresse
         */
        tableauAdresse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idAdresseSelected = (int) tableauAdresse.getValueAt(tableauAdresse.getSelectedRow(), 6);
                try {
                    adresse = AdresseManager.getInstance().SelectById(idAdresseSelected);

                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par mot(s) clé(s) ");
            adresse = null;
            blankAdresse = null;
            if (utilisateur == null) {
                afficheJTableWithAllAdresses();
            } else {
                afficheJTableAdressesIdUtilisateur();
            }
        });

        btnAjouterLigne.setSize(140, 50);
        btnAjouterLigne.addActionListener(e -> {
            verifSiAjout = true;

            List<Adresse> allAdresses = null;
            try {
                allAdresses = AdresseManager.getInstance().SelectAll();
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }

            blankAdresse = new Adresse();
            adresses.add(blankAdresse);

            /* On récupére la plus haute id du tableau pour assigner blankSport à 1 au dessus */
            if (adresse != null) {
                assert allAdresses != null;
                int idMax = allAdresses.get(0).getIdAdresse();

                for (Adresse allAdress : allAdresses) {
                    int adresseId = allAdress.getIdAdresse();
                    if (adresseId > idMax) {
                        idMax = adresseId;
                    }
                }
                blankAdresse.setIdAdresse(idMax + 1);
            }

            blankAdresse.setAdresse("");
            blankAdresse.setVille("");
            blankAdresse.setCodePostal("");
            blankAdresse.setComplement("");
            blankAdresse.setDepartement("");
            blankAdresse.setPays("");

            //////////////////////////////////////////////////////////////////////////////////////////////////////
            if (utilisateur != null) {
                blankAdresse.setIdUtilisateur(utilisateur.getIdUtilisateur());
            } else {
                blankAdresse.setIdUtilisateur(5);
            }
            try {
                AdresseManager.getInstance().insert(blankAdresse);
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }

            TableModelAdresse model = new TableModelAdresse(adresses);
            tableauAdresse.setModel(model);

            blankAdresse = null;
            displayRightTable();
        });

        btnEnrAdresse.addActionListener(e -> {
            /* On récupére les valeurs du tableauAdresse, on boucle pour chaque ligne pour
             * détecter si changement
             * */
            for (int i = 0; i < tableauAdresse.getRowCount(); i++) {

                try {
                    adresse = AdresseManager.getInstance().SelectById((Integer) tableauAdresse.getValueAt(i, 6));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                String adresseModifiee = String.valueOf(tableauAdresse.getValueAt(i, 0));
                String villeModifiee = String.valueOf(tableauAdresse.getValueAt(i, 1));
                String cpModifie = String.valueOf(tableauAdresse.getValueAt(i, 2));
                String complementModifie = String.valueOf(tableauAdresse.getValueAt(i, 3));
                String departementModifie = String.valueOf(tableauAdresse.getValueAt(i, 4));
                String paysModifie = String.valueOf(tableauAdresse.getValueAt(i, 5));
                int idUtilisateurModifie = (int) (tableauAdresse.getValueAt(i, 7));


                if (!adresse.getAdresse().equals(adresseModifiee) || !adresse.getVille().equals(villeModifiee)
                        || !adresse.getCodePostal().equals(cpModifie) || !adresse.getComplement().equals(complementModifie)
                        || !adresse.getDepartement().equals(departementModifie) || !adresse.getPays().equals(paysModifie)
                        || adresse.getIdUtilisateur() != (idUtilisateurModifie)) {
                    try {
                        adresse.setAdresse(adresseModifiee);
                        adresse.setVille(villeModifiee);
                        adresse.setCodePostal(cpModifie);
                        adresse.setComplement(complementModifie);
                        adresse.setDepartement(departementModifie);
                        adresse.setPays(paysModifie);
                        adresse.setIdUtilisateur(idUtilisateurModifie);

                        int j;
                        String confirmChoix = "Veuillez confirmer votre choix";
                        if (verifSiAjout) {
                            j = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir enregistrer l'adresse " + adresse.getIdAdresse() + " ?",
                                    confirmChoix,
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                        } else {
                            j = JOptionPane.showConfirmDialog(null, "La modification est irréversible. Êtes-vous sûr de vouloir enregistrer l'adresse " + adresse.getIdAdresse() + " ?",
                                    confirmChoix,
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                        }
                        if (j == 0)  /*user a dit oui*/ {
                            AdresseManager.getInstance().update(adresse);
                            JOptionPane.showMessageDialog(null, "Adresse " + adresse.getIdAdresse() + " enregistrée");
                        }
                    } catch (BLLException bllException) {
                        bllException.printStackTrace();
                    }
                }
            }//fin boucle for

            displayRightTable();
            verifSiAjout = false;
        });


        btnSupprimerAdresse.addActionListener(e -> {
            if (adresse == null) {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner une adresse");
                return;
            }
            ///Supprime tous les adresses sélectionnées
            int[] selection = tableauAdresse.getSelectedRows();
            for (int j : selection) {
                adresse = adresses.get(j);
                try {
                    adresse = AdresseManager.getInstance().SelectById(adresse.getIdAdresse());
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                int i = JOptionPane.showConfirmDialog(null, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer l'adresse " + adresse.getIdAdresse() + " ?",
                        "Veuillez confirmer votre choix",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                if (i == 0) //user a dit oui
                {
                    try {
                        AdresseManager.getInstance().delete(adresse);
                        JOptionPane.showMessageDialog(null, "Adresse " + adresse.getIdAdresse() + " supprimée");
                    } catch (BLLException ex) {
                        ex.printStackTrace();
                    }
                }
            }//fin for
            if (utilisateur == null) {
                afficheJTableWithAllAdresses();
            } else {
                afficheJTableAdressesIdUtilisateur();
            }
        });

    }//fin initialiserComposants


    /**
     * Méthode qui affiche l'adresse de l'utilisateur sélectionné
     */
    private void afficheJTableAdressesIdUtilisateur() {
        try {
            adresses = remplirJTableWithAdressesIdUser(utilisateur.getIdUtilisateur());
            TableModelAdresse model = new TableModelAdresse(adresses);
            tableauAdresse.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Méthode qui affiche toutes les adresses
     */
    private void afficheJTableWithAllAdresses() {
        try {
            adresses = remplirJTableWithAllAdresses();
            TableModelAdresse model = new TableModelAdresse(adresses);
            tableauAdresse.setModel(model);
        } catch (BLLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui affiche le bon tableau selon la page de provenance
     */
    private void displayRightTable() {
        if (utilisateur == null) {
            afficheJTableWithAllAdresses();
        } else {
            afficheJTableAdressesIdUtilisateur();
        }
    }

}//fin class
