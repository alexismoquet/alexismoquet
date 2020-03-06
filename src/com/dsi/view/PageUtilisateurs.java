package com.dsi.view;

import com.dsi.controller.tableModel.TableModelUtilisateur;
import com.dsi.model.beans.Adresse;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static com.dsi.controller.Utilisateurs.remplirJTableWithAllUtilisateurs;


public class PageUtilisateurs extends JFrame {

    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnModifierUtil = new JButton("Modifier Utilisateur");
    private JButton btnSupprimerUtil = new JButton("Supprimer Utilisateur");
    private JButton btnAnnuler = new JButton("Annuler");

    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauUtilisateur = new JTable();
    List<Utilisateur> utilisateurs = new ArrayList<>();
    List<Adresse> adresses = new ArrayList<>();
    List <Utilisateur> listRechercheUtilisateurs = new ArrayList<>();
    int idSelected;


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
        txtRechercher.setText("     Rechercher par Nom      ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauUtilisateur.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauUtilisateur, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauUtilisateur), BorderLayout.CENTER);

        panBas.setSize(500, 200);
        panBas.add(btnModifierUtil);
        panBas.add(btnSupprimerUtil);
        panBas.add(btnAnnuler);

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
            UtilisateurManager um = new UtilisateurManager();
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

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText("");
            afficheJTableUtilisateurs();
        });

        btnModifierUtil.addActionListener(e -> {
        });

        btnSupprimerUtil.addActionListener(e -> {
        UtilisateurManager um = new UtilisateurManager();
            try {
                um.delete(idSelected);
                afficheJTableUtilisateurs();
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            tableauUtilisateur.clearSelection();
        });

        tableauUtilisateur.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                 idSelected = (int) tableauUtilisateur.getValueAt(tableauUtilisateur.getSelectedRow(), 11);
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


