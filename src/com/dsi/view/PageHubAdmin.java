package com.dsi.view;

import com.dsi.controller.tableModel.TableModelAnnonce;
import com.dsi.model.beans.Annonce;
import com.dsi.model.bll.AnnonceManager;
import com.dsi.model.bll.BLLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class HubAdmin
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageHubAdmin extends JFrame {
    private final JPanel panPrincipal = new JPanel();
    private final JPanel panBtn = new JPanel();
    private final JPanel panAnomalies = new JPanel();

    private final JButton bUtilisateursBO = new JButton("Utilisateurs BO");
    private final JButton bUtilisateurs = new JButton("Utilisateurs");
    private final JButton bCategories = new JButton("Catégories");
    private final JButton bSports = new JButton("Sports");
    private final JButton bCommentaires = new JButton("Commentaires");
    private final JButton bEnregistrer = new JButton("Enregistrer");
    private final JButton bAnnonces = new JButton("Annonces");
    private final JButton bMateriels = new JButton("Materiels");
    private final JButton bVisuels = new JButton("Visuels");
    private final JButton bSorties = new JButton("Sorties");
    private final JButton bAdresses = new JButton("Adresses");
    private final JButton bAnnuler = new JButton("Annuler");

    private final JTable tableauAnomalies = new JTable();
    private final JLabel anomaliesASurveiller = new JLabel();

    List<Annonce> listAnomalies = new ArrayList<>();
    List<Annonce> annonces;
    Annonce annonceSelect, annonce;
    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");

    /**
     * Constructeur par defaut
     */
    public PageHubAdmin() throws BLLException {
        initialiserComposants();
    }

    //************************************************************
    // Methode qui va charger les composants de la fenetre
    //************************************************************
    public void initialiserComposants() throws BLLException {

        setTitle("HandiSpap");
        setIconImage(Toolkit.getDefaultToolkit().getImage("LogoIconeDSI.png"));
        setSize(1000, 600);
        setLocation(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);

        /* Panel principal  */
        panPrincipal.setBackground(Color.decode("#11417d"));
        panPrincipal.setLayout(new BorderLayout());
        panPrincipal.add(panBtn, BorderLayout.NORTH);
        panPrincipal.add(panAnomalies, BorderLayout.CENTER);

        /* Panel des boutons*/
        panBtn.setBackground(Color.decode("#11417d")); //bleu
        panBtn.setPreferredSize(new Dimension(900, 100));
        panBtn.setBorder(new EmptyBorder(10, 10, 0, 10));

        panBtn.add(bUtilisateursBO);
        panBtn.add(bUtilisateurs);
        panBtn.add(bCategories);
        panBtn.add(bSports);
        panBtn.add(bAnnonces);
        panBtn.add(bMateriels);
        panBtn.add(bVisuels);
        panBtn.add(bSorties);
        panBtn.add(bCommentaires);
        panBtn.add(bAdresses);
        panBtn.add(bAnnuler);
        panBtn.add(bEnregistrer);

        /* Panel des anomalies */
        anomaliesASurveiller.setSize(200, 100);
        anomaliesASurveiller.setBackground(Color.white);
        anomaliesASurveiller.setText("<html><body><font color='white'>Anomalies de texte  dans les annonces à surveiller :</body></html>");
        anomaliesASurveiller.setToolTipText(anomaliesASurveiller.getText());

        panAnomalies.setBackground(Color.decode("#11417d")); //rouge = #7d1111
        tableauAnomalies.setGridColor(Color.decode("#7d1111"));
        panAnomalies.add(tableauAnomalies);
        panAnomalies.setLayout(new BorderLayout());
        panAnomalies.add(anomaliesASurveiller, BorderLayout.NORTH);
        panAnomalies.add(new JScrollPane(tableauAnomalies), BorderLayout.CENTER);
        panAnomalies.setBorder(new EmptyBorder(0, 20, 10, 20));
        tableauAnomalies.setRowHeight(30);

        bUtilisateurs.setSize(100, 50);
        bUtilisateurs.addActionListener(e -> new PageUtilisateurs());

        bUtilisateursBO.setSize(100, 50);
        bUtilisateursBO.addActionListener(e -> new PageUtilisateurBO());

        bCategories.setSize(100, 50);
        bCategories.addActionListener(e -> new PageCategories());

        bSports.setSize(100, 50);
        bSports.addActionListener(e -> new PageSports());

        bCommentaires.setSize(100, 50);
        bCommentaires.addActionListener(e -> new PageCommentaires());

        bAnnonces.setSize(100, 50);
        bAnnonces.addActionListener(e -> new PageAnnonces());

        bMateriels.setSize(100, 50);
        bMateriels.addActionListener(e -> new PageMateriels());

        bVisuels.setSize(100, 50);
        bVisuels.addActionListener(e -> new PageVisuels());

        bSorties.setSize(100, 50);
        bSorties.addActionListener(e -> new PageSorties());

        bAdresses.setSize(100, 50);
        bAdresses.addActionListener(e -> new PageAdresses());

        bAnnuler.setSize(100, 50);
        bAnnuler.addActionListener(e -> {
            annonce = null;
            try {
                remplirJTableWithAnomalies();
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }
        });

        /*
         * Listener btnEnregistrer qui enregistre les modifications du tableau des anomalies dans les annonces
         */
        bEnregistrer.setSize(100, 50);
        bEnregistrer.addActionListener(e -> {
            /* Récupérer les valeurs du tableauAnomalies, on vérifie pour chaque ligne */
            for (int i = 0; i < tableauAnomalies.getRowCount(); i++) {

                try {
                    annonce = AnnonceManager.getInstance().SelectById((Integer) tableauAnomalies.getValueAt(i, 3));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                String titreAnnonceModifie = String.valueOf(tableauAnomalies.getValueAt(i, 0));
                String descriptionAnnonceModifie = String.valueOf(tableauAnomalies.getValueAt(i, 1));
                int annonceIdUtilisateurModifie = (int) tableauAnomalies.getValueAt(i, 2);
                int annonceIdMaterielModifie = (int) tableauAnomalies.getValueAt(i, 4);
                Date annonceDateParutionModifie = (Date) tableauAnomalies.getValueAt(i, 5);

//                    tableauAnomalies.setValueAt(titreAnnonceModifie, i, 0);
//                    tableauAnomalies.setValueAt(descriptionAnnonceModifie, i, 1);
//                    tableauAnomalies.setValueAt(annonceIdUtilisateurModifie, i, 2);
//                    tableauAnomalies.setValueAt(annonceIdMaterielModifie, i, 4);
//                    tableauAnomalies.setValueAt(annonceDateParutionModifie, i, 5);


                /* ENREGISTRER LES VALEURS DS LA BASE */
                if (!annonce.getAnnonce_titre().equals(titreAnnonceModifie) || !annonce.getAnnonce_description().equals(descriptionAnnonceModifie)
                        || annonce.getAnnonce_utilisateur_id() != annonceIdUtilisateurModifie || annonce.getAnnonce_materiel_id() != annonceIdMaterielModifie) {
                    try {
                        annonce.setAnnonce_description(descriptionAnnonceModifie);
                        annonce.setAnnonce_titre(titreAnnonceModifie);
                        annonce.setAnnonce_utilisateur_id(annonceIdUtilisateurModifie);
                        annonce.setAnnonce_materiel_id(annonceIdMaterielModifie);
                        annonce.setAnnonce_date_parution(annonceDateParutionModifie);

                        int j = JOptionPane.showConfirmDialog(bEnregistrer, "La modification est irréversible. Êtes-vous sûr de vouloir enregistrer l'annonce " + annonce.getAnnonce_id() + " ?",
                                "Veuillez confirmer votre choix",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);

                        if (j == 0)  /*user a dit oui*/ {
                            AnnonceManager.getInstance().update(annonce);
                            JOptionPane.showMessageDialog(null, "Annonce " + tableauAnomalies.getValueAt(i, 3) + " enregistrée");
                            remplirJTableWithAnomalies();
                            break;
                        }
                    } catch (BLLException bllException) {
                        bllException.printStackTrace();
                    }
                }
            }//fin boucle for
            tableauAnomalies.clearSelection();
        });

        remplirJTableWithAnomalies();

        setContentPane(panPrincipal);

    }//fin initialiser composants

    /**
     * Méthode qui affiche des anomalies A DEFINIR : ci-dessous, pour le mot "sex" dans les annonces
     */
    private void remplirJTableWithAnomalies() throws BLLException {
        listAnomalies = new ArrayList<>();
        AnnonceManager am = new AnnonceManager();
        
        annonces = am.SelectAll();

        for (Annonce value : annonces) {
            annonceSelect = value;
            String titreAnnonceSelect = value.getAnnonce_titre().toLowerCase();
            String descriptionAnnonceSelect = value.getAnnonce_description().toLowerCase();

            
            /*
             Anomalies à définir
             */
            if (titreAnnonceSelect.contains("sex") || descriptionAnnonceSelect.contains("sex")) {
                listAnomalies.add(annonceSelect);
            }
        }
        TableModelAnnonce model = new TableModelAnnonce(listAnomalies);
        tableauAnomalies.setModel(model);

    }//fin JTable

}//fin class