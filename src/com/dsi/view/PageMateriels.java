package com.dsi.view;

import com.dsi.controller.Materiels;
import com.dsi.controller.tableModel.TableModelCommentaire;
import com.dsi.controller.tableModel.TableModelMateriel;
import com.dsi.controller.tableModel.TableModelVisuel;
import com.dsi.model.beans.*;
import com.dsi.model.beans.Materiel;
import com.dsi.model.bll.MaterielManager;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.VisuelManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.dsi.controller.Commentaires.remplirJTableWithCommentaires;
import static com.dsi.controller.Materiels.*;


public class PageMateriels extends JFrame {


    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnModifierMateriel = new JButton("Modifier materiel");
    private JButton btnSupprimerMateriel = new JButton("Supprimer materiel");
    private JButton btnAnnuler = new JButton("Annuler");
    private JButton btnVisuels = new JButton("visuels");
    private JButton btnRechercher = new JButton("Rechercher");

    private JTextField txtRechercher = new JTextField();
    private JTable tableauMateriel = new JTable();
    List<Materiel> materiels = new ArrayList<>();
    List<Materiel> listRechercheMateriels = new ArrayList<>();
    List<Visuel> listRechercheVisuels = new ArrayList<>();
    List<Visuel> visuels = new ArrayList<>();

    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
    Categorie categorie;
    Sport sport;

    Utilisateur utilisateur;
    Materiel materiel;


    /************************************************************/
    /******************** Constructeurs****************/
    /************************************************************/
    public PageMateriels() {
        initialiserComposants();
    }

    public PageMateriels(Utilisateur pUtilisateur) {
        this.utilisateur = pUtilisateur;
        initialiserComposants();
    }

    public PageMateriels(Categorie pCategorie) {
        this.categorie = pCategorie;
        initialiserComposants();
    }

    /*************************************************************/


    public void initialiserComposants() {
        setTitle("Materiels");
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
        txtRechercher.setText("  Rechercher par mots      ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauMateriel.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauMateriel, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauMateriel), BorderLayout.CENTER);

        panBas.setSize(500, 200);
        panBas.add(btnModifierMateriel);
        panBas.add(btnSupprimerMateriel);
        panBas.add(btnAnnuler);
        panBas.add(btnVisuels);

        setContentPane(panPrincipal);

        if (utilisateur == null  && categorie == null) {
            afficheJTableMateriels();
        } else if (utilisateur == null) {
            afficheJTableMaterielsWithIdCategorie(categorie.getCategorie_id());
        } else {
            afficheJTableMaterielsWithIdAdresse();
        }


        /**************************************************************************************************************************************/
        /*************************************************************** Les listenners *******************************************************/
        /**************************************************************************************************************************************/

        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField txtRechercher = ((JTextField) e.getSource());
                txtRechercher.setText("");
                //txtRechercher.removeMouseListener(this);
            }
        });

        btnRechercher.addActionListener(e -> {
            listRechercheMateriels = new ArrayList<>();
            MaterielManager um = MaterielManager.getInstance();
            try {
                um.SelectAll();
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            for (Materiel materiel : materiels) {
                String sp = materiel.getMateriel_nom().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (sp.contains(recherche)) {
                    listRechercheMateriels.add(materiel);
                    TableModelMateriel model = new TableModelMateriel(listRechercheMateriels);
                    tableauMateriel.setModel(model);
                }
            }
            if (listRechercheMateriels.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucun materiel trouvé", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText("");
            materiel = null;
            if (utilisateur == null) {
                afficheJTableMateriels();
            } else {
                afficheJTableMaterielsWithIdAdresse();
            }
        });

        btnModifierMateriel.addActionListener(e -> {
        });

        btnSupprimerMateriel.addActionListener(e -> {
            MaterielManager am = MaterielManager.getInstance();

            int i = JOptionPane.showConfirmDialog(btnSupprimerMateriel, "La suppression est irréversible. Êtes-vous sûr de vouloir continuer ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
            if (i == 0) //user a dit oui
            {
                try {
                    am.delete(materiel);
                    JOptionPane.showMessageDialog(btnSupprimerMateriel, "Materiel " + materiel.getMateriel_id() + " supprimé");

                    if (utilisateur == null) {
                        afficheJTableMateriels();
                    } else {
                        afficheJTableMaterielsWithIdAdresse();
                        tableauMateriel.clearSelection();
                    }
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }

            }
        });


        /**
         * Mouse listenner sur le tableau materiel
         */
        tableauMateriel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idMaterielSelected = (int) tableauMateriel.getValueAt(tableauMateriel.getSelectedRow(), 3);

                //    JOptionPane.showMessageDialog( btnVisuels, "Le materiel " + idMaterielSelected + " est sélectionnée");
                try {

                    materiel = MaterielManager.getInstance().SelectById(idMaterielSelected);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        /**
         * listenner sur le bouton Visuel
         */
        btnVisuels.setSize(100, 50);
        btnVisuels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (materiel == null) {
                    JOptionPane.showMessageDialog(btnVisuels, "veuillez sélectionner un materiel");
                } else {
                    new PageVisuels(materiel);
                }
            }
        });

    }//fin initialiserComposants


    private void afficheJTableMaterielsWithIdSport(int pIdSport) {
        try {
            materiels = remplirJTableWithMaterielsIdSport(sport.getSport_id());
            TableModelMateriel model = new TableModelMateriel(materiels);
            tableauMateriel.setModel(model);

        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

    private void afficheJTableMaterielsWithIdCategorie(int idCategorie) {

        try {
            materiels = remplirJTableWithMaterielsIdCategorie(categorie.getCategorie_id());
            TableModelMateriel model = new TableModelMateriel(materiels);
            tableauMateriel.setModel(model);

        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

    private void afficheJTableMaterielsWithIdAdresse() {

        try {
            materiels = remplirJTableWithMaterielsIdAdresse(utilisateur.getAdresses().get(0).getIdAdresse());
            TableModelMateriel model = new TableModelMateriel(materiels);
            tableauMateriel.setModel(model);

        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable

    private void afficheJTableMateriels() {
        try {
            materiels = remplirJTableWithMateriels();
            TableModelMateriel model = new TableModelMateriel(materiels);
            tableauMateriel.setModel(model);

        } catch (BLLException ex) {
            ex.printStackTrace();
        }

    } //fin afficheJTable


}//fin class
