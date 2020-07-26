package com.dsi.view;

import com.dsi.controller.tableModel.TableModelAdresse;
import com.dsi.controller.tableModel.TableModelSport;
import com.dsi.controller.tableModel.TableModelVisuel;
import com.dsi.model.beans.*;
import com.dsi.model.bll.AdresseManager;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.MaterielManager;
import com.dsi.model.bll.VisuelManager;

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

import static com.dsi.controller.Adresses.*;

/**
 * Classe PageAdresses
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageAdresses extends JFrame {

    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnModifierAdresse = new JButton("Enregistrer");
    private JButton btnAjouterLigne = new JButton("Ajouter une ligne");
    private JButton btnSupprimerAdresse = new JButton("Supprimer");
    private JButton btnAnnuler = new JButton("Annuler");


    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauAdresse = new JTable();
    List<Adresse> adresses = new ArrayList<>();

    List<Adresse> listRechercheAdresses = new ArrayList<>();
    Categorie categorie;
    Adresse adresse, blankAdresse;
    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
    Utilisateur utilisateur;

    /************************************************************/
    /******************** Constructeurs**************************/
    /************************************************************/
    public PageAdresses() {
        initialiserComposants();
    }

    public PageAdresses(Utilisateur pUtilisateur) {
        this.utilisateur = pUtilisateur;
        initialiserComposants();
    }

    /************************************************************/

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
        panBas.add(btnModifierAdresse);
        panBas.add(btnAjouterLigne);
        panBas.add(btnSupprimerAdresse);
        panBas.add(btnAnnuler);

        setContentPane(panPrincipal);

        if (utilisateur == null) {
            afficheJTableWithAllAdresses();
        } else {
            afficheJTableAdressesIdUtilisateur(utilisateur.getIdUtilisateur());
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
            }
        });

        btnRechercher.addActionListener(e -> {
            listRechercheAdresses = new ArrayList<>();
            AdresseManager um = AdresseManager.getInstance();
            try {
                um.SelectAll();
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            for (Adresse adresse : adresses) {
                String titreAdresse = adresse.getAdresse().toLowerCase();
                String ville = adresse.getVille().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (titreAdresse.contains(recherche) || ville.contains(recherche)) {
                    listRechercheAdresses.add(adresse);
                    TableModelAdresse model = new TableModelAdresse(listRechercheAdresses);
                    tableauAdresse.setModel(model);
                }
            }
            if (listRechercheAdresses.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucune adresse trouvée", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        /**
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
                //Gêne pour modifier une ligne du tableauAdresse//  JOptionPane.showMessageDialog(null, "L'adresse " + idAdresseSelected + " est sélectionnée");
            }
        });


        /*************************************************************************************************/
        /***************************  ACTION LISTENNERS DES BOUTONS DU PANEL BAS  ************************/
        /*************************************************************************************************/

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par mot(s) clé(s) ");
            adresse = null;
            blankAdresse = null;
            if (utilisateur == null) {
                afficheJTableWithAllAdresses();
            } else {
                afficheJTableAdressesIdUtilisateur(utilisateur.getIdUtilisateur());
            }
        });

        /**
         * listenner sur le btnajouterAdresse pour ajouter une ligne vierge
         */
        btnAjouterLigne.setSize(140, 50);
        btnAjouterLigne.addActionListener(e -> {
            blankAdresse = new Adresse();
            adresses.add(blankAdresse);

            //////  On récupére la plus haute id du tableu pour assigner blankSport à 1 au dessus ////////////////
            int idMax = adresses.get(0).getIdAdresse();

            for (int i = 0; i < adresses.size(); i++) {
                int adresseId = adresses.get(i).getIdAdresse();
                if (adresseId > idMax) {
                    idMax = adresseId;
                }
            }
            blankAdresse.setIdAdresse(idMax + 1);
            blankAdresse.setAdresse("");
            blankAdresse.setVille("");
            blankAdresse.setCodePostal("");
            blankAdresse.setComplement("");
            blankAdresse.setDepartement("");
            blankAdresse.setPays("");

            //////////////////////////////////////////////////////////////////////////////////////////////////////
            if (utilisateur != null){
                blankAdresse.setIdUtilisateur(utilisateur.getIdUtilisateur());
            }

            TableModelAdresse model = new TableModelAdresse(adresses);
            model.fireTableDataChanged();
            tableauAdresse.revalidate();
            tableauAdresse.setModel(model);
        });


        /**
         * listenner sur le btnModifierAdresse pour enregistrer modifications
         */
        btnModifierAdresse.addActionListener(e -> {
            AdresseManager am = AdresseManager.getInstance();
            /** Récupérer les valeurs du tableauAnomalies, on vérifie pour chaque ligne */
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

                tableauAdresse.setValueAt(adresseModifiee, i, 0);
                tableauAdresse.setValueAt(villeModifiee, i, 1);
                tableauAdresse.setValueAt(cpModifie, i, 2);
                tableauAdresse.setValueAt(complementModifie, i, 3);
                tableauAdresse.setValueAt(departementModifie, i, 4);
                tableauAdresse.setValueAt(paysModifie, i,5 );


                /*** ENREGISTRER LES VALEURS DS LA BASE ***/
                if (!adresse.getAdresse().equals(adresseModifiee) || !adresse.getVille().equals(villeModifiee)
                        || !adresse.getCodePostal().equals(cpModifie)|| !adresse.getComplement().equals(complementModifie)
                        || !adresse.getDepartement().equals(departementModifie)|| !adresse.getPays().equals(paysModifie)) {
                    try {
                        adresse.setAdresse(adresseModifiee);
                        adresse.setVille(villeModifiee);
                        adresse.setCodePostal(cpModifie);
                        adresse.setComplement(complementModifie);
                        adresse.setDepartement(departementModifie);
                        adresse.setPays(paysModifie);

                        int j = JOptionPane.showConfirmDialog(btnModifierAdresse, "La modification est irréversible. Êtes-vous sûr de vouloir continuer ?",
                                "Veuillez confirmer votre choix",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                        if (j == 0)  /**user a dit oui*/ {
                            if (blankAdresse != null) {
                                am.insert(blankAdresse);
                                JOptionPane.showMessageDialog(btnModifierAdresse, "Adresse " + blankAdresse.getIdAdresse() + " ajoutée");
                                blankAdresse = null;
                                break;
                            } else {
                                am.update(adresse);
                                JOptionPane.showMessageDialog(null, "Adresse " + tableauAdresse.getValueAt(i, 6) + " modifiée");
                                afficheJTableAdressesIdUtilisateur(utilisateur.getIdUtilisateur());
                            }
                        }
                    } catch (BLLException bllException) {
                        bllException.printStackTrace();
                    }
                }
            }//fin boucle for
            tableauAdresse.clearSelection();
        });


        /**
         * listenner sur le btnSupprimerAdresse
         * @param adresse
         */
        btnSupprimerAdresse.addActionListener(e -> {
            if (adresse == null) {
                JOptionPane.showMessageDialog(btnSupprimerAdresse, "Veuillez sélectionner une adresse");
                return;
            }
            AdresseManager am = AdresseManager.getInstance();

            int i = JOptionPane.showConfirmDialog(btnSupprimerAdresse, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer l'adresse "+adresse.getIdAdresse()+" ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
            if (i == 0) //user a dit oui
            {
                try {
                    am.delete(adresse);
                    JOptionPane.showMessageDialog(btnSupprimerAdresse, "Adresse " + adresse.getIdAdresse() + " supprimée");
                    if (utilisateur == null) {
                        afficheJTableWithAllAdresses();
                        //    tableauAdresse.clearSelection();
                    } else {
                        afficheJTableAdressesIdUtilisateur(utilisateur.getIdUtilisateur());
                        tableauAdresse.clearSelection();
                    }
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }//fin initialiserComposants


    private void afficheJTableAdressesIdUtilisateur(int pIdUtilisateur) {
        try {
            adresses = remplirJTableWithAdressesIdUser(utilisateur.getIdUtilisateur());
            TableModelAdresse model = new TableModelAdresse(adresses);
            tableauAdresse.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    }

    private void afficheJTableWithAllAdresses() {
        try {
            adresses = remplirJTableWithAllAdresses();
            TableModelAdresse model = new TableModelAdresse(adresses);
            tableauAdresse.setModel(model);
        } catch (BLLException e) {
            e.printStackTrace();
        }
    }

}//fin class
