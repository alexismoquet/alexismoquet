package com.dsi.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe HubAdmin
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageHubAdmin extends JFrame {
    private JPanel panPrincipal = new JPanel();
    private JPanel panSecondaire = new JPanel();

    private JButton bUtilisateurs = new JButton("Utilisateurs");
    private JButton bCategories = new JButton("Catégories");
    private JButton bSports = new JButton("Sports");
    private JButton bCommentaires = new JButton("Commentaires");



    //************************************************************
    // Constructeur par defaut
    //************************************************************
    public PageHubAdmin() {
        initialiserComposants();
    }

    //************************************************************
    // Methode qui va charger les composants de la fenetre
    //************************************************************
    public void initialiserComposants(){

        setTitle("HUB Admin");
        setIconImage(Toolkit.getDefaultToolkit().getImage("LogoIconeDSI.png"));
        setSize(300, 150);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);

        panPrincipal.setBackground(Color.decode("#11417d"));

        bUtilisateurs.setSize(100,50);
        bUtilisateurs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageUtilisateurs pu = new PageUtilisateurs();
            }
        });

        bCategories.setSize(100,50);
        bCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { PageCategories pu = new PageCategories();
                 }
        });

        bSports.setSize(100,50);
        bSports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {PageSports ps = new PageSports(); }
        });

        bCommentaires.setSize(100,50);
        bCommentaires.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { PageCommentaires pc = new PageCommentaires(); }
        });



        // Panel Principal
        panPrincipal.setLayout(new BorderLayout());
        panSecondaire.setBackground(Color.decode("#11417d"));

        panPrincipal.add(panSecondaire, BorderLayout.CENTER);

        // Panel Secondaire
        panSecondaire.setPreferredSize(new Dimension(380, 110 ));
        panSecondaire.setBorder(new EmptyBorder(10, 10, 5, 10));
        panSecondaire.add(bUtilisateurs);
        panSecondaire.add(bCategories);
        panSecondaire.add(bSports);


        setContentPane(panPrincipal);
    }

}//fin class
