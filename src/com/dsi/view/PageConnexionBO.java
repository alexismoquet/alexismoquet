package com.dsi.view;

import com.dsi.controller.BOConnexion;
import com.dsi.model.bll.BLLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class PageConnexionBO extends JFrame {
    private JPanel pan = new JPanel(new BorderLayout());
    private JPanel panBas = new JPanel();
    private JPanel panelBouton = new JPanel();
    private JLabel login = new JLabel("Login :");
    private JLabel motDePasse = new JLabel("Mot de passe :");
    private JButton bIdentification = new JButton("S'identifier");
    private JTextField texteLogin = new JTextField();
    private JPasswordField texteMotDePasse = new JPasswordField();

    GridLayout position = new GridLayout(4, 3, 10, 10);


    //************************************************************
    // Methode qui va charger les composants de la fenetre
    //************************************************************
    public void initialiserComposants() {
        setTitle("Identification");
        setIconImage(Toolkit.getDefaultToolkit().getImage("LogoIconeDSI.png"));
        setSize(400, 210);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        pan.setLayout(position);
        pan.setBorder(new EmptyBorder(30, 40, 0, 40));
        pan.add(login);
        login.setForeground(Color.white);
        pan.add(texteLogin);
        pan.add(motDePasse);
        motDePasse.setForeground(Color.white);
        pan.add(texteMotDePasse);
        pan.add(panBas);
        panBas.setBackground(Color.decode("#11417d"));
        pan.add(panelBouton);
        pan.setBackground(Color.decode("#11417d"));
        panelBouton.add(bIdentification);
        panelBouton.setBackground(Color.decode("#11417d"));
        bIdentification.setPreferredSize(new Dimension(100, 25));


        bIdentification.addActionListener(e -> {
            BOConnexion ac = new BOConnexion();
            try {
                ac.actionIdentification("admin", "P@ssw0rd");
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
        });

        texteMotDePasse.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent event) {

                int key = event.getKeyCode();

                if (key == KeyEvent.VK_ENTER) {
                    BOConnexion ac = new BOConnexion();
                    try {
                        ac.actionIdentification("admin", "P@ssw0rd");
                    } catch (BLLException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

}//fin class
