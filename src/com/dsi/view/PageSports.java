package com.dsi.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PageSports extends JFrame {

    private JPanel panPrincipal = new JPanel();
    private JPanel panGauche = new JPanel();
    private JPanel panDroit = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panBas = new JPanel();


    private JButton bModifierUtil = new JButton("Modifier Utilisateur");
    private JButton bSupprimerUtil = new JButton("Supprimer Utilisateur");
    private JButton bAnnuler = new JButton("Annuler");

    private JLabel infoNom = new JLabel("Nom :");
    private JLabel infoMDP = new JLabel("Mot de passe :");
    private JLabel infoPrenom = new JLabel("Prénom :");
    private JTextField texteNom = new JTextField();
    private JTextField textePrenom = new JTextField();
    private JPasswordField texteMdp = new JPasswordField ();
    private JLabel infoAdresse = new JLabel("Adresse :");
    private JTextField texteAdresse = new JTextField();
    private JLabel infoEmail = new JLabel("Email :");
    private JTextField texteEmail = new JTextField("");

    private JTable tableauUtilisateur;



    //************************************************************
    // Methode qui va charger les composants de la fenetre
    //************************************************************
    public void initialiserComposants() {
        setTitle("Utilisateurs");
        setIconImage(Toolkit.getDefaultToolkit().getImage("LogoIconeDSI.png"));
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);

        panPrincipal.setLayout(new BorderLayout());
        panPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        panPrincipal.setBackground(Color.decode("#11417d"));

        panPrincipal.add(panHaut, BorderLayout.NORTH);
        panPrincipal.add(panGauche, BorderLayout.WEST);
        panPrincipal.add(panDroit, BorderLayout.EAST);
        panPrincipal.add(panBas, BorderLayout.SOUTH);

        panGauche.setPreferredSize(new Dimension(200,200));
        panGauche.setLayout(new GridLayout(13,1,5,5));
        panGauche.add(infoNom);
        panGauche.add(texteNom);
        infoNom.setForeground(Color.black);

        panGauche.add(infoPrenom);
        infoPrenom.setForeground(Color.black);
        panGauche.add(textePrenom);

        panGauche.add(infoEmail);
        panGauche.add(texteEmail);
        infoEmail.setForeground(Color.black);

        panGauche.add(infoMDP);
        infoMDP.setForeground(Color.black);
        panGauche.add(texteMdp);

        panGauche.add(infoAdresse);
        infoAdresse.setForeground(Color.black);
        panGauche.add(texteAdresse);

        panBas.setSize(500, 20);
        panBas.add(bModifierUtil);
        panBas.add(bSupprimerUtil);
        panBas.add(bAnnuler);

        setContentPane(panPrincipal);
    }


}//fin class
