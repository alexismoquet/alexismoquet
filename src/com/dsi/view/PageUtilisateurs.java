package com.dsi.view;

import com.dsi.controller.Utilisateurs;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.BLLException;
import com.dsi.model.dal.DAO_Utilisateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;



public class PageUtilisateurs extends JFrame {

    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton bModifierUtil = new JButton("Modifier Utilisateur");
    private JButton bSupprimerUtil = new JButton("Supprimer Utilisateur");
    private JButton bAnnuler = new JButton("Annuler");

    private JTable tableauUtilisateur;
    List <Utilisateur> listUtilisateurs = new ArrayList<>();


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

        panHaut.setPreferredSize(new Dimension(900, 70));

        //Panel centre
        panCentre.setPreferredSize(new Dimension(620, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauUtilisateur.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauUtilisateur, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauUtilisateur), BorderLayout.CENTER);

        panBas.setSize(500,20);
        panBas.add(bModifierUtil);
        panBas.add(bSupprimerUtil);
        panBas.add(bAnnuler);

        setContentPane(panPrincipal);

        // Remplissage du JTable
        Object [][] donnees = {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
        };
        String[] entetes = {"Nom", "Prénom", "Email", "Mot de passe", "Téléphone fixe", "Téléphone mobile", "Adresse", "Date d'inscription"};
        // Fin de remplissage du JTable
        tableauUtilisateur = new JTable (donnees, entetes);  // Création d'un objet de Type JTable avec les parametres que l'on a défini au dessus

        try {
            Utilisateurs u = new Utilisateurs();
            listUtilisateurs = u.remplirTable();

            for (Utilisateur utilisateur : listUtilisateurs) {
            utilisateur.getNom();
            }
        } catch (BLLException ex) {
            ex.printStackTrace();
        }

    }

}// fin class


