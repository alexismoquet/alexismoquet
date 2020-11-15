package com.dsi.view;

import com.dsi.controller.tableModels.ImageCellRenderer;
import com.dsi.controller.tableModels.TableModelVisuel;
import com.dsi.model.beans.Materiel;
import com.dsi.model.beans.Visuel;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.VisuelManager;

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

    private final JPanel panPrincipal = new JPanel();
    private final JPanel panHaut = new JPanel();
    private final JPanel panCentre = new JPanel();
    private final JPanel panBas = new JPanel();

    private final JButton btnEnregistrerVisuel = new JButton("Enregistrer");
    private final JButton btnSupprimerVisuel = new JButton("Supprimer");
    private final JButton btnAnnuler = new JButton("Annuler");
    private final JButton btnAjouterVisuel = new JButton("Ajouter");

    private final JTextField txtRechercher = new JTextField();
    private final JButton btnRechercher = new JButton("Rechercher");

    private final JTable tableauVisuel = new JTable();
    private List<Visuel> visuels = new ArrayList<>();
    private List<Visuel> listRechercheVisuels = new ArrayList<>();
    private Visuel visuel, blankVisuel;
    private final ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
    private Materiel materiel;
    boolean verifSiAjout = false;

    /**
     * Constructeur par defaut
     */
    public PageVisuels() {
        initialiserComposants();
    }

    /**
     * Constructeur
     *
     * @param: pMateriel
     */
    public PageVisuels(Materiel pMateriel) {
        this.materiel = pMateriel;
        initialiserComposants();
    }

    public void initialiserComposants() {
        setTitle("Visuels");
        setIconImage(Toolkit.getDefaultToolkit().getImage("LogoIconeDSI.png"));
        setSize(1100, 700);
        setVisible(true);
        setResizable(true);

        panPrincipal.setLayout(new BorderLayout());
        panPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        panPrincipal.setBackground(Color.decode("#11417d"));
        panPrincipal.add(panHaut, BorderLayout.NORTH);
        panPrincipal.add(panCentre, BorderLayout.CENTER);
        panPrincipal.add(panBas, BorderLayout.SOUTH);

        panHaut.setPreferredSize(new Dimension(900, 100));
        txtRechercher.setText("   Rechercher par nom de fichier  ");
        panHaut.add(txtRechercher);
        panHaut.add(btnRechercher);

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauVisuel.getTableHeader(), BorderLayout.NORTH);
        panCentre.add(tableauVisuel, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauVisuel), BorderLayout.CENTER);
        tableauVisuel.setRowHeight(40);

        panBas.setSize(500, 200);

        if (materiel != null) {
            panBas.add(btnAjouterVisuel);
        }
        panBas.add(btnEnregistrerVisuel);
        panBas.add(btnSupprimerVisuel);
        panBas.add(btnAnnuler);

        setContentPane(panPrincipal);



        tableauVisuel.setAutoCreateRowSorter(true);

        displayRightTable();


        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtRechercher.setText("");
            }
        });

        btnRechercher.addActionListener(e -> {
            listRechercheVisuels = new ArrayList<>();
            try {
                visuels = VisuelManager.getInstance().SelectAll();
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            for (Visuel visuelRech : visuels) {
                String sp = visuelRech.getVisuel_nom_fichier().toLowerCase();
                String recherche = txtRechercher.getText().toLowerCase();

                if (sp.contains(recherche)) {
                    listRechercheVisuels.add(visuelRech);
                    TableModelVisuel model = new TableModelVisuel(listRechercheVisuels);
                    tableauVisuel.setModel(model);
                }
            }
            if (listRechercheVisuels.isEmpty()) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucun visuel trouvé", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText("   Rechercher par nom de fichier  ");
            visuel = null;
            if (materiel == null) {
                afficheJTableWithAllVisuels();
            } else {
                afficheJTableVisuelsWithIdMateriel(materiel.getMateriel_id());
            }
        });

        /*
         * Bouton supprimer le visuel
         */
        btnSupprimerVisuel.addActionListener(e -> {
            if (visuel == null) {
                JOptionPane.showMessageDialog(null, "Merci de sélectionner un visuel");
                return;
            }

            ///Supprime tous les visuels sélectionnés
            int[] selection = tableauVisuel.getSelectedRows();
            for (int j : selection) {
                visuel = visuels.get(j);
                try {
                    visuel = VisuelManager.getInstance().SelectById(visuel.getVisuel_id());
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }

                int i = JOptionPane.showConfirmDialog(null, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer le visuel " + visuel.getVisuel_id() + " ?",
                        "Veuillez confirmer votre choix",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                if (i == 0) //user a dit oui
                {
                    try {
                        VisuelManager.getInstance().delete(visuel);
                        JOptionPane.showMessageDialog(null, "Visuel " + visuel.getVisuel_id() + " supprimé");

                    } catch (BLLException ex) {
                        ex.printStackTrace();
                    }
                    tableauVisuel.clearSelection();
                }
            }//fin for
            if (materiel == null) {
                afficheJTableWithAllVisuels();
            } else {
                afficheJTableVisuelsWithIdMateriel(materiel.getMateriel_id());
            }
        });

        /*
         * listenner sur le btnajouterVisuel pour ajouter une ligne vierge
         */
        btnAjouterVisuel.setSize(140, 50);
        btnAjouterVisuel.addActionListener(e -> {
            verifSiAjout = true;
            blankVisuel = new Visuel();

            //////  On récupére la plus haute id du tableu pour assigner blankSortie à 1 au dessus ////////////////
            if (!(visuels.isEmpty())) {
                List<Visuel> allVisuels = null;
                try {
                    allVisuels = VisuelManager.getInstance().SelectAll();
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }
                assert allVisuels != null;
                int idMax = allVisuels.get(0).getVisuel_id();

                for (Visuel allVisuel : allVisuels) {
                    int sortieId = allVisuel.getVisuel_id();
                    if (sortieId > idMax) {
                        idMax = sortieId;
                    }
                }
                blankVisuel.setVisuel_id(idMax + 1);
            } else {
                blankVisuel.setVisuel_id(1);
            }
            blankVisuel.setVisuel_nom_fichier("");

            if (materiel == null) {
                blankVisuel.setVisuel_materiel_id(1);
            } else {
                blankVisuel.setVisuel_materiel_id(materiel.getMateriel_id());
            }

            try {
                VisuelManager.getInstance().insert(blankVisuel);
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }

            visuels.add(blankVisuel);

            TableModelVisuel model = new TableModelVisuel(visuels);
            model.fireTableDataChanged();
            tableauVisuel.revalidate();
            tableauVisuel.setModel(model);

            blankVisuel = null;

            displayRightTable();
        });

        /*
         * Bouton Modifier le visuel
         */
        btnEnregistrerVisuel.addActionListener(e -> {

            /* Récupérer les valeurs du tableauUtilisateur, on boucle pour chaque ligne */
            for (int i = 0; i < tableauVisuel.getRowCount(); i++) {
                try {
                    visuel = VisuelManager.getInstance().SelectById((Integer) tableauVisuel.getValueAt(i, 2));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }

                String nomFichierVisuelModifie = String.valueOf(tableauVisuel.getValueAt(i, 1));
                int idMaterielVisuelModifie = (int) tableauVisuel.getValueAt(i, 3);

                tableauVisuel.setValueAt(nomFichierVisuelModifie, i, 1);
                tableauVisuel.setValueAt(idMaterielVisuelModifie, i, 3);

                if (visuel == null) {
                    return;
                } else {
                    /* ENREGISTRER LES VALEURS DS LA BASE SI BESOIN ***/
                    if (!visuel.getVisuel_nom_fichier().equalsIgnoreCase(nomFichierVisuelModifie) || !(visuel.getVisuel_materiel_id() == idMaterielVisuelModifie)) {
                        visuel.setVisuel_nom_fichier(nomFichierVisuelModifie);
                        visuel.setVisuel_materiel_id(idMaterielVisuelModifie);

                        int j;
                        String confirmChoix = "Veuillez confirmer votre choix";
                        if (verifSiAjout) {
                            j = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir enregistrer le visuel " + visuel.getVisuel_id() + " ?",
                                    confirmChoix,
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                            verifSiAjout = false;
                        } else {
                            j = JOptionPane.showConfirmDialog(null, "La modification est irréversible. Êtes-vous sûr de vouloir enregistrer le visuel " + visuel.getVisuel_id() + " ?",
                                    confirmChoix,
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                        }
                        if (j == 0)  /*user a dit oui*/ {
                            try {
                                VisuelManager.getInstance().update(visuel);
                                JOptionPane.showMessageDialog(null, "Visuel " + visuel.getVisuel_id() + " enregistré");
                            } catch (BLLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
            displayRightTable();
        });

        /*
         * Mouse listenner sur le tableau Visuel
         */
        tableauVisuel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idVisuelSelected = (int) tableauVisuel.getValueAt(tableauVisuel.getSelectedRow(), 2);
                tableauVisuel.getValueAt(tableauVisuel.getSelectedRow(), 0);

                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    ImageCellRenderer icr = new ImageCellRenderer();
                    icr.getTableCellRendererComponent(tableauVisuel, tableauVisuel.getValueAt(tableauVisuel.getSelectedRow(), 1), true, true, tableauVisuel.getSelectedRow(), 0);
                }
//Gêne pour modifier une ligne du tableauVisuel //JOptionPane.showMessageDialog( null, "Le visuel " + idVisuelSelected + " est sélectionné");
                try {
                    visuel = VisuelManager.getInstance().SelectById(idVisuelSelected);

                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }//fin initialiserComposants


    private void afficheJTableVisuelsWithIdMateriel(int pMateriel_Id) {
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


    private void displayRightTable() {
        if (materiel == null) {
            afficheJTableWithAllVisuels();
        } else {
            afficheJTableVisuelsWithIdMateriel(materiel.getMateriel_id());
        }
    }

}//fin class
