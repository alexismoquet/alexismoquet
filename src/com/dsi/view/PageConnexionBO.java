package com.dsi.view;

import com.dsi.controller.BOConnexion;
import com.dsi.model.bll.BLLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class PageConnexionBO extends JFrame {
    private final JPanel pan = new JPanel(new BorderLayout());
    private final JPanel panBas = new JPanel();
    private final JPanel panelBouton = new JPanel();
    private final JLabel login = new JLabel("Login :");
    private final JLabel motDePasse = new JLabel("Mot de passe :");
    private final JTextField texteLogin = new JTextField();
    private final JPasswordField texteMotDePasse = new JPasswordField();
    private final JButton bIdentification = new JButton("S'identifier");
    private final BOConnexion BOC = new BOConnexion();

    GridLayout position = new GridLayout(4, 3, 10, 10);

    /*
    *Methode qui va charger les composants de la fenetre
    */
    public void initialiserComposants() {
        setTitle("Identification");
        setIconImage(Toolkit.getDefaultToolkit().getImage("LogoIconeDSI.png"));
        setSize(400, 210);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        pan.setLayout(position);
        pan.setBorder(new EmptyBorder(30, 10, 0, 10));
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

        /*
         * listenner sur le bIdentification
         */
        bIdentification.addActionListener(e -> {
            try {
                actionConnexion();
            } catch (BLLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(bIdentification,"Login inconnu");
            }
        });

        /*
         * listenner sur le champ texteMotDePasse
         */
        texteMotDePasse.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent event) {

                int key = event.getKeyCode();

                if (key == KeyEvent.VK_ENTER) {
                    try {
                        actionConnexion();
                    } catch (BLLException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        setContentPane(pan);
    }

        private void actionConnexion() throws BLLException {
        boolean rep = BOC.actionIdentification(texteLogin.getText(), String.valueOf(texteMotDePasse.getPassword()));

        if (rep){
            PageHubAdmin ha = new PageHubAdmin();
            ha.setVisible(true);
        } else {JOptionPane.showMessageDialog(null, "Mot de passe incorrect");

            setVisible(false);
            PageConnexionBO pcbo = new PageConnexionBO();
            pcbo.initialiserComposants();
            pcbo.setVisible(true);
        }
    }

}//fin class
