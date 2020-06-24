package com.dsi.view;

import com.dsi.controller.tableModel.TableModelVisuel;
import com.dsi.model.beans.Materiel;
import com.dsi.model.beans.Utilisateur;
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

import static com.dsi.controller.Visuels.remplirJTableWithAllVisuels;
import static com.dsi.controller.Visuels.remplirJTableWithVisuelsIdMateriel;



/**
 * Classe PageVisuels
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageVisuels extends JFrame {
    
        private JPanel panPrincipal = new JPanel();
        private JPanel panHaut = new JPanel();
        private JPanel panCentre = new JPanel();
        private JPanel panBas = new JPanel();

        private JButton btnModifierVisuel = new JButton("Modifier visuel");
        private JButton btnSupprimerVisuel = new JButton("Supprimer visuel");
        private JButton btnAnnuler = new JButton("Annuler");

   //     private JTextField txtRechercher = new JTextField();
   //     private JButton btnRechercher = new JButton("Rechercher");

        private JTable tableauVisuel = new JTable();
        List<Visuel> visuels = new ArrayList<>();
      //  List <Visuel> listRechercheVisuels = new ArrayList<>();
        Visuel visuel;
        ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
        Materiel materiel;


        /************************************************************/
        /******************** Constructeur par defaut****************/
        /************************************************************/
        public PageVisuels() {
            initialiserComposants();
        }

        public PageVisuels(Materiel pMateriel) {
            this.materiel = pMateriel;
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
          //  txtRechercher.setText("     Rechercher par id visuel   ");
          //  panHaut.add(txtRechercher);
           // panHaut.add(btnRechercher);

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

            if (materiel == null){
                afficheJTableWithAllVisuels();
            }else {
                afficheJTableVisuels(materiel.getMateriel_id());
            }


            /**************************************************************************************************************************************/
            /*************************************************************** Les listenners *******************************************************/
            /**************************************************************************************************************************************/

//            txtRechercher.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    JTextField txtRechercher = ((JTextField) e.getSource());
//                    txtRechercher.setText("");
//                    txtRechercher.removeMouseListener(this);
//                }
//            });

//            btnRechercher.addActionListener(e -> {
//                listRechercheVisuels = new ArrayList<>();
//                VisuelManager um = VisuelManager.getInstance();
//                try {
//                    um.SelectAll();
//                } catch (BLLException ex) {
//                    ex.printStackTrace();
//                }
//                for (Visuel visuel : visuels) {
//                    String sp = String.valueOf(visuel.getVisuel_id());
//                    String recherche = txtRechercher.getText().toLowerCase();
//
//                    if (sp.equals(recherche)) {
//                        listRechercheVisuels.add(visuel);
//                        TableModelVisuel model = new TableModelVisuel(listRechercheVisuels);
//                        tableauVisuel.setModel(model);
//                    }
//                }
//                if (listRechercheVisuels.size() == 0) {
//                    JOptionPane.showMessageDialog(panPrincipal, "Aucun visuel trouvé", "warning", JOptionPane.INFORMATION_MESSAGE);
//                }
//            });

            btnAnnuler.addActionListener(e -> {
          //      txtRechercher.setText("");
                visuel = null;
                if (materiel == null){
                    afficheJTableWithAllVisuels();
                } else {
                    afficheJTableVisuels(materiel.getMateriel_id());
                }
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
                if(visuel == null){
                    JOptionPane.showMessageDialog(btnSupprimerVisuel, "Merci de sélectionner un visuel");
                    return;
                }
                VisuelManager am = VisuelManager.getInstance();

                int i= JOptionPane.showConfirmDialog(btnSupprimerVisuel, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer le visuel "+visuel.getVisuel_id()+" ?",
                        "Veuillez confirmer votre choix",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,icone);
                if (i==0) //user a dit oui
                {
                    try {
                        am.delete(visuel);
                        JOptionPane.showMessageDialog(btnSupprimerVisuel, "Visuel "+ visuel.getVisuel_id()+ " supprimé");
                        if (materiel == null){
                            afficheJTableWithAllVisuels();
                        } else {
                            afficheJTableVisuels(materiel.getMateriel_id());
                        }
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
                    int idVisuelSelected = (int) tableauVisuel.getValueAt(tableauVisuel.getSelectedRow(), 1);
                   JOptionPane.showMessageDialog( null, "Le visuel " + idVisuelSelected + " est sélectionné");
                    try {
                        visuel = VisuelManager.getInstance().SelectById(idVisuelSelected);

                    } catch (BLLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }//fin initialiserComposants


        private void afficheJTableVisuels(int pIdMateriel) {
            try {
                visuels = remplirJTableWithVisuelsIdMateriel(materiel.getMateriel_id());
                TableModelVisuel model = new TableModelVisuel(visuels);
                tableauVisuel.setModel(model);

            } catch (BLLException ex) {
                ex.printStackTrace();
            }

        } //fin afficheJTable

        private void afficheJTableWithAllVisuels() {
            try {
                visuels = remplirJTableWithAllVisuels();
                TableModelVisuel model = new TableModelVisuel(visuels);
                tableauVisuel.setModel(model);

            } catch (BLLException ex) {
                ex.printStackTrace();
            }

        } //fin afficheJTable


    public void afficheLeVisuel () throws BLLException {
        String adresseVisuel = "C:\\wamp64\\www\\handispap\\img\\mat";

        for (int i=0; i<visuels.size(); i++){
            String fichierVisuel = visuels.get(i).getVisuel_nom_fichier() + adresseVisuel;
            ImageIcon icone1 = new ImageIcon("C:\\wamp64\\www\\handispap\\img\\mat\\"+fichierVisuel);
            tableauVisuel.getValueAt(i, 0);
        }

    }

    }//fin class
