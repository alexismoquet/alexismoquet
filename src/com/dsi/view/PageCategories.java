package com.dsi.view;

import com.dsi.controller.tableModel.TableModelCategorie;
import com.dsi.model.beans.Categorie;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.CategorieManager;
import com.dsi.model.bll.UtilisateurManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import static com.dsi.controller.Categories.remplirJTableWithAllCategories;

/**
 * Classe PageCategories
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
    private JButton btnAnnuler = new JButton("Annuler");
    private JButton btnMateriels = new JButton("Matériels");
    private JButton btnAjouterCategorie = new JButton("Creer une ligne");
    private JButton btnEnrModifs = new JButton("Enregistrer les modifications");

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
        txtRechercher.setText("     Rechercher par categorie     ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauCategorie.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauCategorie, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauCategorie), BorderLayout.CENTER);

        panBas.setSize(600, 250);
        panBas.add(btnAjouterCategorie);
        panBas.add(btnEnrModifs);
        panBas.add(btnSupprimerCategorie);
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
                //       txtRechercher.removeMouseListener(this);
            }
        });

        btnRechercher.addActionListener(e -> {
            listRechercheCategories = new ArrayList<>();
            CategorieManager sm = CategorieManager.getInstance();
            try {
                sm.SelectAll();  //retourne une list d'utilisateurs = utilisateurs
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            /** ON PARCOURS LA LISTE DES SPORTS **/
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
                JOptionPane.showMessageDialog(panPrincipal, "Aucun categorie trouvé", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText("");
            categorie = null;
            afficheJTableCategories();
        });

        /**
         * listenner sur le btnajouterCategorie pour ajouter une ligne vierge
         */
        btnAjouterCategorie.setSize(140, 50);
        btnAjouterCategorie.addActionListener(e -> {
            blankCategorie = new Categorie();
            categories.add(blankCategorie);

            //////  On récupére la plus haute id du tableu pour assigner blankCategorie à 1 au dessus ////////////////
            int idMax = categories.get(0).getCategorie_id();

            for (int i = 0; i < categories.size(); i++) {
                int categorieId = categories.get(i).getCategorie_id();
                if (categorieId > idMax) {
                    idMax = categorieId;
                }
            }
            blankCategorie.setCategorie_id(idMax + 1);
            blankCategorie.setCategorie_libelle("");
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            TableModelCategorie model = new TableModelCategorie(categories);
            model.fireTableDataChanged();
            tableauCategorie.revalidate();
            tableauCategorie.setModel(model);
        });


        /**
         * listenner sur le bouton Enregistrer les modifications dans la base
         */
        btnEnrModifs.setSize(140, 50);
        btnEnrModifs.addActionListener(e -> {
            CategorieManager sm = CategorieManager.getInstance();

            /** Récupérer les valeurs du tableauUtilisateur, on boucle pour chaque ligne */
            for (int i = 0; i < tableauCategorie.getRowCount(); i++) {
                try {
                    categorie = CategorieManager.getInstance().SelectById((Integer) tableauCategorie.getValueAt(i, 1));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                String libelleCategorieModifie = String.valueOf(tableauCategorie.getValueAt(i, 0));
                int idCategorieModifie = (int) tableauCategorie.getValueAt(i, 1);

                if (categorie == null) {
                    return;
                    //JOptionPane.showMessageDialog(btnEnrModifs, "Veuillez sélectionner un categorie");
                } else {
                    /*** ENREGISTRER LES VALEURS DS LA BASE ***/
                    if (!categorie.getCategorie_libelle().equalsIgnoreCase(libelleCategorieModifie) || !(categorie.getCategorie_id() == idCategorieModifie)) {
                        categorie.setCategorie_libelle(libelleCategorieModifie);
                        categorie.setCategorie_id(idCategorieModifie);

                        int j = JOptionPane.showConfirmDialog(btnEnrModifs, "La modification est irréversible. Êtes-vous sûr de vouloir continuer ?",
                                "Veuillez confirmer votre choix",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                        if (j == 0)  /**user a dit oui*/ {
                            try {
                                if (blankCategorie != null) {
                                    sm.insert(blankCategorie);
                                    JOptionPane.showMessageDialog(btnEnrModifs, "Categorie " + blankCategorie.getCategorie_id() + " ajouté");
                                    blankCategorie = null;
                                    break;
                                } else {
                                    sm.update(categorie);
                                    JOptionPane.showMessageDialog(btnEnrModifs, "Categorie " + categorie.getCategorie_id() + " modifié");
                                    break;
                                }
                                //   afficheJTableCategories();
                            } catch (BLLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }//fin for
        });


        btnSupprimerCategorie.addActionListener(e -> {
            if (categorie == null) {
                JOptionPane.showMessageDialog(btnSupprimerCategorie, "Merci de sélectionner un categorie");
                return;
            }

            CategorieManager sm = CategorieManager.getInstance();
            int i = JOptionPane.showConfirmDialog(btnSupprimerCategorie, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer le categorie " + categorie.getCategorie_id() + " ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
            if (i == 0) //user a dit oui
            {
                try {
                    sm.delete(categorie);
                    JOptionPane.showMessageDialog(btnSupprimerCategorie, "Categorie " + categorie.getCategorie_id() + " supprimé");
                    afficheJTableCategories();
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
                tableauCategorie.clearSelection();
            }
        });

        /**
         * listenner sur le bouton materiel
         */
        btnMateriels.setSize(100, 50);
        btnMateriels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (categorie == null) {
                    JOptionPane.showMessageDialog(btnMateriels, "Veuillez sélectionner une categorie");
                } else {
                    new PageMateriels(categorie);
                }
            }
        });


        /**
         *
         * Mouse listenner sur le tableau categorie
         */
        tableauCategorie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idCategorie = (int) tableauCategorie.getValueAt(tableauCategorie.getSelectedRow(), 1);
                //      JOptionPane.showMessageDialog(null, "Le categorie " + idCategorie + " est sélectionné");
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
            categories = remplirJTableWithAllCategories();
            TableModelCategorie model = new TableModelCategorie(categories);
            tableauCategorie.setModel(model);
        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable


}//fin class
