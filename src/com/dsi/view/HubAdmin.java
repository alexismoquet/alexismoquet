package com.dsi.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HubAdmin extends JFrame {
    private JPanel panPrincipal = new JPanel();
    private JPanel panhaut = new JPanel();
    private JPanel panBas = new JPanel();
    private JButton bIntervention = new JButton("Intervention");
    private JButton bstock = new JButton("Gestion des Stocks");
    private JButton bUtilisateur = new JButton("Gestion des Utilisateurs");
    private JButton bVelo = new JButton("Gestion des Velos");
    private JButton bRecap = new JButton("Suivi interventions");


    //************************************************************
    // Constructeur par defaut
    //************************************************************
    public HubAdmin() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("LogoIconeDSI.png"));
        initialiserComposants();
    }

    //************************************************************
    // Methode qui va charger les composants de la fenetre
    //************************************************************
    public void initialiserComposants(){

        setTitle("HUB Admin");
        setSize(400, 420);
        setLocation(10, 10);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        panPrincipal.setBackground(Color.decode("#11417d"));

        bIntervention.setSize(100,50);
        bIntervention.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        bstock.setSize(100,50);
        bstock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        bUtilisateur.setSize(100,50);
        bUtilisateur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        bVelo.setSize(100,50);
        bVelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });


        bRecap.setSize(100,50);
        bRecap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        // Panel Principal
        panPrincipal.setLayout(new BorderLayout());
        panPrincipal.add(panhaut, BorderLayout.NORTH);
        panPrincipal.add(panBas, BorderLayout.SOUTH);

        // Panel Haut
        panhaut.setPreferredSize(new Dimension(380, 110 ));
        panhaut.setBorder(new EmptyBorder(10, 10, 5, 10));
        panhaut.add(bIntervention);
        panhaut.add(bstock);
        panhaut.add(bUtilisateur);
        panhaut.add(bVelo);
        panhaut.add(bRecap);


        // Panel Bas
        panBas.setPreferredSize(new Dimension(380, 220 ));
        // 1--remplissage du JTable
        Object[][] donnees = {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
        };

        panBas.setLayout(new BorderLayout());
        setContentPane(panPrincipal);
    }

}//fin class
