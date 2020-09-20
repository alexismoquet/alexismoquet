package com.dsi.view;

import com.dsi.controller.tableModel.TableModelCategorie;
import com.dsi.model.beans.Categorie;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.CategorieManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.dsi.controller.Categories.remplirJTableWithAllCategories;

/**
 * Class PageCategories
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageCategories extends JFrame {

    private final JPanel panPrincipal = new JPanel();
    private final JPanel panHaut = new JPanel();
    private final JPanel panCentre = new JPanel();
    private final JPanel panBas = new JPanel();

    private final JButton btnSupprimerCategorie = new JButton("Supprimer");
    private final JButton btnAnnuler = new JButton("Annuler");
    private final JButton btnMateriels = new JButton("Matériels");
    private final JButton btnAjouterCategorie = new JButton("Ajouter");
    private final JButton btnEnrModifs = new JButton("Enregistrer");

    private final JTextField txtRechercher = new JTextField();
    private final JButton btnRechercher = new JButton("Rechercher");

    private final JTable tableauCategorie = new JTable();
    List<Categorie> categories = new ArrayList<>();
    List<Categorie> listRechercheCategories = new ArrayList<>();

    Categorie categorie, blankCategorie;
    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
    boolean verifSiAjout = false;
    /**
     * Constructeur par defaut
     */
    public PageCategories() {
        initialiserComposants();
    }


    public void initialiserComposants() {
        setTitle("Categories");
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

        panHaut.setPreferredSize(new Dimension(900, 40));
        txtRechercher.setText(" Rechercher par nom");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauCategorie.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauCategorie, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauCategorie), BorderLayout.CENTER);
        tableauCategorie.setRowHeight(30);  //hauteur cellule ds JTable

        panBas.setSize(600, 250);
        panBas.add(btnEnrModifs);
        panBas.add(btnAjouterCategorie);
        panBas.add(btnSupprimerCategorie);
        panBas.add(btnAnnuler);
        panBas.add(btnMateriels);

        setContentPane(panPrincipal);

        afficheJTableCategories();

        /*
         * Mouse listenner du champ de recherche
         **/
        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField txtRechercher = ((JTextField) e.getSource());
                txtRechercher.setText("");
            }
        });

        /*
         * Mouse listenner du bouton de recherche
         **/
        btnRechercher.addActionListener(e -> {
            listRechercheCategories = new ArrayList<>();
            try {
                CategorieManager.getInstance().SelectAll();  //retourne une list d'utilisateurs = utilisateurs
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            /*ON PARCOURS LA LISTE DES CATEGORIES */
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

        /*
         * listenner sur le btnAnnuler
         */
        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par nom");
            categorie = null;
            blankCategorie = null;
            afficheJTableCategories();
        });

        /*
         * listenner sur le btnAjouterCategorie pour ajouter une ligne vierge
         */
        btnAjouterCategorie.setSize(140, 50);
        btnAjouterCategorie.addActionListener(e -> {
            verifSiAjout = true;
            List<Categorie> allCategories = null;
            try {
                allCategories = CategorieManager.getInstance().SelectAll();
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }
            blankCategorie = new Categorie();
            categories.add(blankCategorie);

            //////  On récupére la plus haute id du tableu pour assigner blankCategorie à 1 au dessus ////////////////
            if (categorie != null) {
                assert allCategories != null;
                int idMax = allCategories.get(0).getCategorie_id();

                for (Categorie allCategory : allCategories) {
                    int categorieId = allCategory.getCategorie_id();
                    if (categorieId > idMax) {
                        idMax = categorieId;
                    }
                }
                blankCategorie.setCategorie_id(idMax + 1);
            }
            blankCategorie.setCategorie_libelle("");

            try {
                CategorieManager.getInstance().insert(blankCategorie);
                //      JOptionPane.showMessageDialog(btnAjouterCategorie, "Catégorie ajoutée");
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }

            TableModelCategorie model = new TableModelCategorie(categories);
            model.fireTableDataChanged();
            tableauCategorie.revalidate();
            tableauCategorie.setModel(model);

            blankCategorie = null;
            afficheJTableCategories();
        });

        /*
         * listenner sur le bouton Enregistrer qui enregistre les modifications dans la base
         */
        btnEnrModifs.addActionListener(e -> {
            /* Récupérer les valeurs du tableauUtilisateur, on boucle pour chaque ligne */
            for (int i = 0; i < tableauCategorie.getRowCount(); i++) {
                try {
                    categorie = CategorieManager.getInstance().SelectById((Integer) tableauCategorie.getValueAt(i, 1));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                String libelleCategorieModifie = String.valueOf(tableauCategorie.getValueAt(i, 0));

                tableauCategorie.setValueAt(libelleCategorieModifie, i, 0);
//              tableauCategorie.setValueAt(idCategorieModifie, i, 1);

                if (categorie == null) {
                    JOptionPane.showMessageDialog(btnEnrModifs, "Veuillez sélectionner une categorie");
                } else {
                    /* ENREGISTRER LES VALEURS DS LA BASE SI BESOIN ***/
                    if (!categorie.getCategorie_libelle().equals(libelleCategorieModifie)) {
                        categorie.setCategorie_libelle(libelleCategorieModifie);

                        int j;
                        if (verifSiAjout) {
                            j = JOptionPane.showConfirmDialog(btnEnrModifs, "Êtes-vous sûr de vouloir enregistrer la catégorie " + categorie.getCategorie_id() + " ?",
                                    "Veuillez confirmer votre choix",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                                    verifSiAjout = false;
                        } else {
                            j = JOptionPane.showConfirmDialog(btnEnrModifs, "La modification est irréversible. Êtes-vous sûr de vouloir enregistrer la catégorie " + categorie.getCategorie_id() + " ?",
                                    "Veuillez confirmer votre choix",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                        }

                        if (j == 0)  /*user a dit oui*/ {
                            try {
                                CategorieManager.getInstance().update(categorie);
                                JOptionPane.showMessageDialog(null, "Catégorie " + categorie.getCategorie_id() + " enregistrée");
                            } catch (BLLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }//fin for
            afficheJTableCategories();
        });

        /*
         * Listenner sur le bouton supprimer
         */
        btnSupprimerCategorie.addActionListener(e -> {
            if (categorie == null) {
                JOptionPane.showMessageDialog(btnSupprimerCategorie, "Merci de sélectionner un categorie");
                return;
            }
            int i = JOptionPane.showConfirmDialog(btnSupprimerCategorie, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer la categorie " + categorie.getCategorie_id() + " ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
            if (i == 0) //user a dit oui
            {
                try {
                    CategorieManager.getInstance().delete(categorie);
                    JOptionPane.showMessageDialog(btnSupprimerCategorie, "Categorie " + categorie.getCategorie_id() + " supprimée");
                    afficheJTableCategories();
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
                tableauCategorie.clearSelection();
            }
        });


        /*
         * listenner sur le bouton materiel qui dirige sur la page matériel en fonction de la catégorie sélectionnée
         */
        btnMateriels.setSize(100, 50);
        btnMateriels.addActionListener(e -> {
            if (categorie == null) {
                JOptionPane.showMessageDialog(btnMateriels, "Veuillez sélectionner une categorie");
            } else {
                new PageMateriels(categorie);
            }
        });

        /*
         * Mouse listenner sur le tableau categorie
         */
        tableauCategorie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idCategorie = (int) tableauCategorie.getValueAt(tableauCategorie.getSelectedRow(), 1);
                try {
                    categorie = CategorieManager.getInstance().SelectById(idCategorie);
                    //gene pour double-clics sur cellule du tableauCategorie   //   JOptionPane.showMessageDialog(null, "Le categorie " + idCategorie + " est sélectionnée");
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }//fin initialiserComposants

    /**
     * Méthode qui affiche toutes les catégories
     */
    private void afficheJTableCategories() {
        try {
            categories = remplirJTableWithAllCategories();
            TableModelCategorie model = new TableModelCategorie(categories);
            tableauCategorie.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable


}//fin class
