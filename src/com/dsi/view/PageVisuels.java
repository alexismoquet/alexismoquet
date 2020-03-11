package com.dsi.view;

import com.dsi.controller.tableModel.TableModelVisuel;
import com.dsi.model.beans.Visuel;
import com.dsi.model.bll.VisuelManager;
import com.dsi.model.bll.BLLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.dsi.controller.Visuels.remplirJTableWithVisuels;

public class PageVisuels extends JFrame {
    
        private JPanel panPrincipal = new JPanel();
        private JPanel panHaut = new JPanel();
        private JPanel panCentre = new JPanel();
        private JPanel panBas = new JPanel();

        private JButton btnModifierVisuel = new JButton("Modifier visuel");
        private JButton btnSupprimerVisuel = new JButton("Supprimer visuel");
        private JButton btnAnnuler = new JButton("Annuler");

        private JTextField txtRechercher = new JTextField();
        private JButton btnRechercher = new JButton("Rechercher");

        private JTable tableauVisuel = new JTable();
        List<Visuel> visuels = new ArrayList<>();
        List <Visuel> listRechercheVisuels = new ArrayList<>();
        Visuel visuel;
        ImageIcon icone = new ImageIcon("LogoIconeDSI.png");


        /************************************************************/
        /******************** Constructeur par defaut****************/
        /************************************************************/
        public PageVisuels() {
            initialiserComposants();
        }


        public void initialiserComposants() {
            setTitle("Visuels");
            setIconImage(Toolkit.getDefaultToolkit().getImage("LogoIconeDSI.png"));
            setSize(900, 500);
            setVisible(true);
            setResizable(true);

            panPrincipal.setLayout(new BorderLayout());
            panPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
            panPrincipal.setBackground(Color.decode("#11417d"));

            panPrincipal.add(panHaut, BorderLayout.NORTH);
            panPrincipal.add(panCentre, BorderLayout.CENTER);
            panPrincipal.add(panBas, BorderLayout.SOUTH);

            panHaut.setPreferredSize(new Dimension(900, 100));
            txtRechercher.setText("     Rechercher     ");
            panHaut.add(txtRechercher);
            panHaut.add(btnRechercher);

            //Panel centre
            panCentre.setPreferredSize(new Dimension(900, 250));
            panCentre.setLayout(new BorderLayout());
            panCentre.add(tableauVisuel.getTableHeader(), BorderLayout.NORTH);
            panCentre.add(tableauVisuel, BorderLayout.CENTER);
            panCentre.add(new JScrollPane(tableauVisuel), BorderLayout.CENTER);

            panBas.setSize(500, 200);
            panBas.add(btnModifierVisuel);
            panBas.add(btnSupprimerVisuel);
            panBas.add(btnAnnuler);

            setContentPane(panPrincipal);

            afficheJTableVisuels();

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
                listRechercheVisuels = new ArrayList<>();
                VisuelManager um = VisuelManager.getInstance();
                try {
                    um.SelectAll();
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
                for (Visuel visuel : visuels) {
                    String sp = visuel.getVisuel_nom_fichier().toLowerCase();
                    String recherche = txtRechercher.getText().toLowerCase();

                    if (sp.contains(recherche)) {
                        listRechercheVisuels.add(visuel);
                        TableModelVisuel model = new TableModelVisuel(listRechercheVisuels);
                        tableauVisuel.setModel(model);
                    }
                }
                if (listRechercheVisuels.size() == 0) {
                    JOptionPane.showMessageDialog(panPrincipal, "Aucun visuel trouvé", "warning", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            btnAnnuler.addActionListener(e -> {
                txtRechercher.setText("                 ");
                afficheJTableVisuels();

            });


            /**
             * Bouton Modifier le visuel
             */
            btnModifierVisuel.addActionListener(e -> {
            });

            /**
             * Bouton supprimer le visuel
             */
            btnSupprimerVisuel.addActionListener(e -> {
                VisuelManager am = VisuelManager.getInstance();

                int i= JOptionPane.showConfirmDialog(btnSupprimerVisuel, "La suppression est irréversible. Êtes-vous sûr de vouloir continuer ?",
                        "Veuillez confirmer votre choix",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,icone);
                if (i==0) //user a dit oui
                {
                    try {
                        am.delete(visuel);
                        afficheJTableVisuels();
                    } catch (BLLException ex) {
                        ex.printStackTrace();
                    }
                    tableauVisuel.clearSelection();
                }

            });

            /**
             * Mouse listenner sur le tableau Visuel
             */
            tableauVisuel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int id = (int) tableauVisuel.getValueAt(tableauVisuel.getSelectedRow(), 1);
                    try {
                        visuel = VisuelManager.getInstance().SelectById(id);
                    } catch (BLLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }//fin initialiserComposants


        private void afficheJTableVisuels() {
            try {
                visuels = remplirJTableWithVisuels();
                TableModelVisuel model = new TableModelVisuel(visuels);
                tableauVisuel.setModel(model);

            } catch (BLLException ex) {
                ex.printStackTrace();
            }

        } //fin afficheJTable

    }//fin class
