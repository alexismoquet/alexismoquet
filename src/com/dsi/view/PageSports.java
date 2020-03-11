package com.dsi.view;

import com.dsi.controller.tableModel.TableModelSport;
import com.dsi.model.beans.Sport;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.dsi.controller.Sports.remplirJTableWithSports;

public class PageSports extends JFrame {

    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnModifierSport = new JButton("Modifier Sport");
    private JButton btnSupprimerSport = new JButton("Supprimer Sport");
    private JButton btnAnnuler = new JButton("Annuler");

    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");

    private JTable tableauSport = new JTable();
    List<Sport> sports = new ArrayList<>();
    List <Sport> listRechercheSports = new ArrayList<>();


    /************************************************************/
    /******************** Constructeur par defaut****************/
    /************************************************************/
    public PageSports() {
        initialiserComposants();
    }


    public void initialiserComposants() {
        setTitle("Sports");
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
        txtRechercher.setText("     Rechercher par sport     ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauSport.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauSport, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauSport), BorderLayout.CENTER);

        panBas.setSize(500, 200);
        panBas.add(btnModifierSport);
        panBas.add(btnSupprimerSport);
        panBas.add(btnAnnuler);
        setContentPane(panPrincipal);

        afficheJTableSports();


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
            listRechercheSports = new ArrayList<>();
            UtilisateurManager um = UtilisateurManager.getInstance();
            try {
                um.SelectAll();  //retourne une list d'utilisateurs = utilisateurs
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            for (Sport sport : sports) {
                String sp = sport.getSport_libelle().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (sp.startsWith(recherche)) {
                    listRechercheSports.add(sport);
                    TableModelSport model = new TableModelSport(listRechercheSports);
                    tableauSport.setModel(model);
                }
            }
            if (listRechercheSports.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucun sport trouvé", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText("");
            afficheJTableSports();
        });

        btnModifierSport.addActionListener(e -> {
        });

        btnSupprimerSport.addActionListener(e -> {
            tableauSport.clearSelection();

        });
    }//fin initialiserComposants


    private void afficheJTableSports() {
        try {
            sports = remplirJTableWithSports();
            TableModelSport model = new TableModelSport(sports);
            tableauSport.setModel(model);

        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

}//fin class
