package com.dsi.view;

import com.dsi.controller.tableModel.TableModelCategorie;
import com.dsi.model.beans.Categorie;
import com.dsi.model.bll.CategorieManager;
import com.dsi.model.bll.BLLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.dsi.controller.Categories.remplirJTableWithCategories;


public class PageCategories extends JFrame {

    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnModifierCategorie = new JButton("Modifier categorie");
    private JButton btnSupprimerCategorie = new JButton("Supprimer categorie");
    private JButton btnAnnuler = new JButton("Annuler");

    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauCategorie = new JTable();
    List<Categorie> categories = new ArrayList<>();
    List <Categorie> listRechercheCategories = new ArrayList<>();


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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        panBas.add(btnModifierCategorie);
        panBas.add(btnSupprimerCategorie);
        panBas.add(btnAnnuler);

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
            txtRechercher.setText("                 ");
            afficheJTableCategories();

        });

        btnModifierCategorie.addActionListener(e -> {
        });

        btnSupprimerCategorie.addActionListener(e -> {
            tableauCategorie.clearSelection();

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
