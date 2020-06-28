package com.dsi.view;

import com.dsi.controller.tableModel.TableModelCategorie;
import com.dsi.controller.tableModel.TableModelSport;
import com.dsi.model.beans.Annonce;
import com.dsi.model.beans.Categorie;
import com.dsi.model.beans.Sport;
import com.dsi.model.bll.CategorieManager;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.SportManager;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.dsi.controller.Categories.remplirJTableWithCategories;

/**
 * Classe PageCategorie
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageCategories extends JFrame {

    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnSupprimerCategorie = new JButton("Supprimer");
    private JButton btnAjouterCategorie = new JButton("Créer une ligne");
    private JButton btnEnregistrer = new JButton("Ajouter à la base");
    private JButton btnEnregistrerModifs = new JButton("Enregistrer les mofifications");
    private JButton btnAnnuler = new JButton("Annuler");
    private JButton btnMateriels = new JButton("Matériels");

    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauCategorie = new JTable();
    List<Categorie> categories = new ArrayList<>();
    List<Categorie> listRechercheCategories = new ArrayList<>();
    Categorie categorie, blankCategorie;

    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");


    /************************************************************/
    /******************** Constructeur par defaut****************/
    /************************************************************/
    public PageCategories() {
        initialiserComposants();
    }


    public void initialiserComposants() {
        setTitle("Categories");
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
        txtRechercher.setText("     Rechercher par titre de catégorie     ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauCategorie.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauCategorie, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauCategorie), BorderLayout.CENTER);

        panBas.setSize(500, 200);
        panBas.add(btnSupprimerCategorie);
        panBas.add(btnAjouterCategorie);
        panBas.add(btnEnregistrerModifs);
        panBas.add(btnEnregistrer);
        panBas.add(btnAnnuler);
        panBas.add(btnMateriels);

        setContentPane(panPrincipal);

        afficheJTableCategories();

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
            listRechercheCategories = new ArrayList<>();
            CategorieManager um = CategorieManager.getInstance();
            try {
                um.SelectAll();
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            for (Categorie categorie : categories) {
                String sp = categorie.getCategorie_libelle().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (sp.startsWith(recherche)) {
                    listRechercheCategories.add(categorie);
                    TableModelCategorie model = new TableModelCategorie(listRechercheCategories);
                    tableauCategorie.setModel(model);
                }
            }
            if (listRechercheCategories.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucune categorie trouvée", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText("");
            categorie = null;
            afficheJTableCategories();

        });


        btnSupprimerCategorie.addActionListener(e -> {
            if (categorie == null) {
                JOptionPane.showMessageDialog(btnSupprimerCategorie, "Merci de sélectionner une catégorie");
                return;
            }
            CategorieManager sm = CategorieManager.getInstance();

            int i = JOptionPane.showConfirmDialog(btnSupprimerCategorie, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer la catégorie " + categorie.getCategorie_id() + " ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
            if (i == 0) //user a dit oui
            {
                try {
                    sm.delete(categorie);
                    JOptionPane.showMessageDialog(btnSupprimerCategorie, "Catégorie " + categorie.getCategorie_id() + " supprimée");
                    afficheJTableCategories();
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
                tableauCategorie.clearSelection();
            }
        });
        /**
         * listenner sur le bouton ajouterCategorie pour ajouter une ligne categorie vierge
         */
        btnAjouterCategorie.setSize(140, 50);
        btnAjouterCategorie.addActionListener(e -> {

            blankCategorie = new Categorie();
            categories.add(blankCategorie);
            blankCategorie.setCategorie_id(categories.size());
          //  categories.add(blankCategorie);
            TableModelCategorie model = new TableModelCategorie(categories);
            //model.addSport(blankSport);
            tableauCategorie.setModel(model);
        });

        /** listenner sur le bouton enregistrer = ajouter dans la base
         */
        btnEnregistrer.setSize(100, 50);
        btnEnregistrer.addActionListener(e -> {

            //Récupérer les valeurs du sport ajouté ds le tableauSport*/
            String libelleCategorieAjoute = (String) tableauCategorie.getValueAt(categories.size()-1, 0);
            int idCategorieAjoute = (int) tableauCategorie.getValueAt(categories.size()-1, 1);

            blankCategorie.setCategorie_libelle(libelleCategorieAjoute);
            blankCategorie.setCategorie_id(idCategorieAjoute);

            CategorieManager cm = CategorieManager.getInstance();
            try {
                cm.insert(blankCategorie);
                afficheJTableCategories();
                JOptionPane.showMessageDialog(btnEnregistrer, "Catégorie " + blankCategorie.getCategorie_libelle() + " ajoutée");

            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }
        });


        /**
         * listenner sur le bouton Enregistrer les modifications
         */
        btnEnregistrerModifs.setSize(140, 50);
        btnEnregistrerModifs.addActionListener(e -> {
            CategorieManager um = CategorieManager.getInstance();

            /** Récupérer les valeurs du tableauUtilisateur, on boucle pour chaque ligne */
            for (int i = 0; i < tableauCategorie.getRowCount(); i++) {
                try {
                    categorie = CategorieManager.getInstance().SelectById((Integer) tableauCategorie.getValueAt(i, 1));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                String libelleCategorieModifie = String.valueOf(tableauCategorie.getValueAt(i, 0));
         //       tableauCategorie.setValueAt(libelleCategorieModifie, i, 0);

                if (categorie == null) {
                    JOptionPane.showMessageDialog(btnEnregistrer, "Veuillez sélectionner une catégorie");
                } else {
                    /*** ENREGISTRER LES VALEURS DS LA BASE ***/
                    if (!categorie.getCategorie_libelle().equalsIgnoreCase(libelleCategorieModifie) ) {
                        categorie.setCategorie_libelle(libelleCategorieModifie);

                        int j = JOptionPane.showConfirmDialog(btnEnregistrer, "La modification est irréversible. Êtes-vous sûr de vouloir continuer ?",
                                "Veuillez confirmer votre choix",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                        if (j == 0)  /**user a dit oui*/ {
                            try {
                                um.update(categorie);
                                JOptionPane.showMessageDialog(btnEnregistrer, "Catégorie " + categorie.getCategorie_id() + " modifié");
                                tableauCategorie.setValueAt(libelleCategorieModifie, i, 0);
                                afficheJTableCategories();
                            } catch (BLLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }//fin for
        });



        /**
         * listenner sur le bouton materiel
         */
        btnMateriels.setSize(100, 50);
        btnMateriels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (categorie == null) {
                    JOptionPane.showMessageDialog(btnMateriels, "Veuillez sélectionner une catégorie");
                } else {
                    new PageMateriels(categorie);
                }
            }
        });

        /**
         * Mouse listenner sur le tableau catégorie
         */
        tableauCategorie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idCategorie = (int) tableauCategorie.getValueAt(tableauCategorie.getSelectedRow(), 1);
           //     JOptionPane.showMessageDialog(null, "La catégorie " + idCategorie + " est sélectionnée");

                try {
                    categorie = CategorieManager.getInstance().SelectById(idCategorie);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });


    }//fin initialiserComposants


    private void afficheJTableCategories() {
        try {
            categories = remplirJTableWithCategories();
            TableModelCategorie model = new TableModelCategorie(categories);
            tableauCategorie.setModel(model);

        } catch (BLLException ex) {
            ex.printStackTrace();
        }

    } //fin afficheJTable
}
