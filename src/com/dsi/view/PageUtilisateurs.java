package com.dsi.view;

import com.dsi.controller.TableModel;
import com.dsi.controller.Utilisateurs;
import com.dsi.model.beans.Adresse;
import com.dsi.model.beans.Utilisateur;
import com.dsi.model.bll.BLLException;
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

    private JTable tableauUtilisateur = new JTable();
    List <Utilisateur> utilisateurs = new ArrayList<>();
    List <Adresse> adresses = new ArrayList<>();



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

        panHaut.setPreferredSize(new Dimension(900, 50));

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauUtilisateur.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauUtilisateur, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauUtilisateur), BorderLayout.CENTER);

        panBas.setSize(500, 200);
        panBas.add(bModifierUtil);
        panBas.add(bSupprimerUtil);
        panBas.add(bAnnuler);

        setContentPane(panPrincipal);

        afficheJtable();

    } //fin initialiserComposants


        private void afficheJtable() {

            try {
                Utilisateurs u = new Utilisateurs();
                utilisateurs = u.remplirTableUtilisateur();
                System.out.println(utilisateurs);
                TableModel model = new TableModel(utilisateurs);
                tableauUtilisateur.setModel(model);

            } catch (BLLException ex) {
                ex.printStackTrace();
            }

        } //fin afficheJTable

}// fin class


