package com.dsi.view;

import com.dsi.controller.tableModel.TableModelMateriel;
import com.dsi.model.beans.*;
import com.dsi.model.beans.Materiel;
import com.dsi.model.bll.MaterielManager;
import com.dsi.model.bll.BLLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.dsi.controller.Materiels.remplirJTableWithMateriels;

public class PageMateriels extends JFrame {

    private JPanel panPrincipal = new JPanel();
    private JPanel panHaut = new JPanel();
    private JPanel panCentre = new JPanel();
    private JPanel panBas = new JPanel();

    private JButton btnModifierMateriel = new JButton("Modifier materiel");
    private JButton btnSupprimerMateriel = new JButton("Supprimer materiel");
    private JButton btnAnnuler = new JButton("Annuler");


    private JTextField txtRechercher = new JTextField();
    private JButton btnRechercher = new JButton("Rechercher");
    private JButton bVisuels = new JButton("Visuels");

    private JTable tableauMateriel = new JTable();
    List<Materiel> materiels = new ArrayList<>();
    List <Materiel> listRechercheMateriels = new ArrayList<>();

    Materiel materiel;
    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
    Adresse adresse;
    Annonce annonce;


    /************************************************************/
    /******************** Constructeurs****************/
    /************************************************************/
    public PageMateriels() {
        initialiserComposants();
    }

    public PageMateriels(Adresse padresse) {
        this.adresse = padresse;
        initialiserComposants();
    }

    public PageMateriels(Annonce pAnnonce) {
        this.annonce = pAnnonce;
        initialiserComposants();
    }

    /****************************************************************************************/


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
        txtRechercher.setText("     Rechercher     ");
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

        setContentPane(panPrincipal);

        afficheJTableMateriels();

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
            afficheJTableMateriels();

        });

        btnModifierMateriel.addActionListener(e -> {
        });

        btnSupprimerMateriel.addActionListener(e -> {
            MaterielManager am = MaterielManager.getInstance();

            int i= JOptionPane.showConfirmDialog(btnSupprimerMateriel, "La suppression est irréversible. Êtes-vous sûr de vouloir continuer ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,icone);
            if (i==0) //user a dit oui
            {
                try {
                    am.delete(materiel);
                    afficheJTableMateriels();
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
                tableauMateriel.clearSelection();
            }
        });


        /**
         * Mouse listenner sur le tableau materiel
         */
        tableauMateriel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idMaterielSelected = (int) tableauMateriel.getValueAt(tableauMateriel.getSelectedRow(), 3);

                JOptionPane.showMessageDialog( bVisuels, "Le materiel " + idMaterielSelected + " est sélectionnée");
                try {
                    materiel = MaterielManager.getInstance().SelectById(materiel.getMateriel_id());
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /**
         * Mouse listenner sur le tableau visuel
         */
        bVisuels.setSize(100,50);
        bVisuels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (materiel == null){
                    JOptionPane.showMessageDialog( bVisuels, "veuillez sélectionner un materiel");
                } else {
                    PageVisuels pc = new PageVisuels(materiel);
                }
            }
        });

    }//fin initialiserComposants


    private void afficheJTableMateriels() {
        try {
            materiels = remplirJTableWithMateriels(materiel.getMateriel_id());
            TableModelMateriel model = new TableModelMateriel(materiels);
            tableauMateriel.setModel(model);

        } catch (BLLException ex) {
            ex.printStackTrace();
        }
    } //fin afficheJTable


}//fin class
