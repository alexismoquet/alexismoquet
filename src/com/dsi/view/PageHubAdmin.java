package com.dsi.view;

import com.dsi.controller.tableModel.TableModelAnnonce;
import com.dsi.model.beans.Annonce;
import com.dsi.model.bll.AnnonceManager;
import com.dsi.model.bll.BLLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe HubAdmin
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageHubAdmin extends JFrame {
    private JPanel panPrincipal = new JPanel();
    private JPanel panBtn = new JPanel();
    private JPanel panAnomalies = new JPanel();

    private JButton bUtilisateurs = new JButton("Utilisateurs");
    private JButton bCategories = new JButton("Catégories");
    private JButton bSports = new JButton("Sports");
    private JButton bCommentaires = new JButton("Commentaires");
    private JButton bEnregistrer = new JButton("Enregistrer");

    private JTable tableauAnomalies = new JTable();

    private JLabel anomaliesASurveiller = new JLabel();

    List<Annonce> listAnomalies = new ArrayList<>();
    List<Annonce> annonces;
    Annonce annonceSelect, annonce;
    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
    String titreAnnonceModifiee;
    String descriptionAnnonceModifiee;


    //************************************************************
    // Constructeur par defaut
    //************************************************************
    public PageHubAdmin() throws BLLException {
        initialiserComposants();
    }

    //************************************************************
    // Methode qui va charger les composants de la fenetre
    //************************************************************
    public void initialiserComposants() throws BLLException {

        setTitle("HUB Admin");
        setIconImage(Toolkit.getDefaultToolkit().getImage("LogoIconeDSI.png"));
        setSize(900, 500);
        setLocation(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);


        /***** Panel principal  *****/
        panPrincipal.setBackground(Color.decode("#11417d"));
        panPrincipal.setLayout(new BorderLayout());
        panPrincipal.add(panBtn, BorderLayout.CENTER);
        panPrincipal.add(panAnomalies, BorderLayout.SOUTH);


        /***** Panel des boutons*/
        panBtn.setBackground(Color.decode("#11417d")); //bleu
        panBtn.setPreferredSize(new Dimension(900, 150));
        panBtn.setBorder(new EmptyBorder(10, 10, 5, 10));
        panBtn.add(bUtilisateurs);
        panBtn.add(bCategories);
        panBtn.add(bSports);
        panBtn.add(bEnregistrer);

        /***** Panel des anomalies*/
        anomaliesASurveiller.setSize(200, 70);
        anomaliesASurveiller.setBackground(Color.white);
        anomaliesASurveiller.setText("<html><body><font color='white'>Anomalies de texte à surveiller :</body></html>");
        anomaliesASurveiller.setToolTipText(anomaliesASurveiller.getText());

        panAnomalies.setBackground(Color.decode("#11417d")); //rouge = #7d1111
        tableauAnomalies.setGridColor(Color.decode("#7d1111"));
        panAnomalies.add(tableauAnomalies);
        panAnomalies.setLayout(new BorderLayout());
        panAnomalies.add(anomaliesASurveiller, BorderLayout.NORTH);
        panAnomalies.add(new JScrollPane(tableauAnomalies), BorderLayout.CENTER);
        panAnomalies.setPreferredSize(new Dimension(900, 400));
        panAnomalies.setBorder(new EmptyBorder(20, 40, 20, 40));


        bUtilisateurs.setSize(100, 50);
        bUtilisateurs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageUtilisateurs pu = new PageUtilisateurs();
            }
        });

        bCategories.setSize(100, 50);
        bCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageCategories pu = new PageCategories();
            }
        });

        bSports.setSize(100, 50);
        bSports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageSports ps = new PageSports();
            }
        });

        bCommentaires.setSize(100, 50);
        bCommentaires.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageCommentaires pc = new PageCommentaires();
            }
        });

        bEnregistrer.setSize(100, 50);
        bEnregistrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /** Récupérer les valeurs du tableauAnomalies, on boucle pour chaque ligne */
                for (int i = 0; i <tableauAnomalies.getRowCount(); i++) {

                    String titreAnnonceModifiee = String.valueOf(tableauAnomalies.getValueAt(i, 0));
                    String descriptionAnnonceModifiee = String.valueOf(tableauAnomalies.getValueAt(i, 1));

                    //    tableauAnomalies.changeSelection(tableauAnomalies.getSelectedRow(), 0, false, false);
                    tableauAnomalies.setValueAt(descriptionAnnonceModifiee, i, 1);
                    tableauAnomalies.setValueAt(titreAnnonceModifiee, i, 0);

                    /*** ENREGISTRER LES VALEURS DS LA BASE ***/
                    if (!annonce.getAnnonce_titre().equals(titreAnnonceModifiee) || !annonce.getAnnonce_description().equals(descriptionAnnonceModifiee)) {
                        try {
                            annonce.setAnnonce_description(descriptionAnnonceModifiee);
                            annonce.setAnnonce_titre(titreAnnonceModifiee);

                            int j = JOptionPane.showConfirmDialog(bEnregistrer, "La modification est irréversible. Êtes-vous sûr de vouloir continuer ?",
                                    "Veuillez confirmer votre choix",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                            if (j == 0)  /**user a dit oui*/ {
                                AnnonceManager.getInstance().update(annonce);
                                JOptionPane.showMessageDialog(null, "Annonce " + tableauAnomalies.getValueAt(i,3) + " modifiée");
                            }
                        } catch (BLLException bllException) {
                            bllException.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(panPrincipal, "Pas de modification de l'Annonce " + tableauAnomalies.getValueAt(i,3), "warning", JOptionPane.INFORMATION_MESSAGE);
                    }
                }//fin boucle for

            }//fin actionPerformed

        });


        /**
         * Mouse listenner sur le tableau anomalie
         */
        tableauAnomalies.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idAnnonceAnomalieSelected = (int) tableauAnomalies.getValueAt(tableauAnomalies.getSelectedRow(), 3);

                try {
                    annonce = AnnonceManager.getInstance().SelectById(idAnnonceAnomalieSelected);

                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        remplirJTableWithAnomalies();

        setContentPane(panPrincipal);

    }//fin initialiser composants


    private void remplirJTableWithAnomalies() throws BLLException {
        AnnonceManager am = new AnnonceManager();
        annonces = am.SelectAll();

        for (int i = 0; i < annonces.size(); i++) {
            annonceSelect = annonces.get(i);
            String titreAnnonceSelect = annonces.get(i).getAnnonce_titre().toLowerCase();
            String descriptionAnnonceSelect = annonces.get(i).getAnnonce_description().toLowerCase();

            if (titreAnnonceSelect.contains("sexe") || descriptionAnnonceSelect.contains("sexe")) {
                listAnomalies.add(annonceSelect);
            }
        }
        TableModelAnnonce model = new TableModelAnnonce(listAnomalies);
        tableauAnomalies.setModel(model);

    }//fin JTable

}//fin class