package com.dsi.view;

import com.dsi.controller.tableModel.TableModelUtilisateurBO;
import com.dsi.librairies.Roles;
import com.dsi.model.beans.UtilisateurBo;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurBoManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.dsi.controller.UtilisateursBO.remplirJTableWithAllUtilisateursBO;

/**
 * Classe UtilisateursBO
 *
 * @author Alexis Moquet
 * @since Créé le 04/02/2020
 */
public class PageUtilisateurBO extends JFrame {

    private final JPanel panPrincipal = new JPanel();
    private final JPanel panHaut = new JPanel();
    private final JPanel panCentre = new JPanel();
    private final JPanel panBas = new JPanel();

    private final JButton btnEnrModif = new JButton("Enregistrer");
    private final JButton btnAjouterLigne = new JButton("Ajouter");
    private final JButton btnSupprimerUtilisateur = new JButton("Supprimer");
    private final JButton btnAnnuler = new JButton("Annuler");

    private final JTextField txtRechercher = new JTextField();
    private final JButton btnRechercher = new JButton("Rechercher");
    private final JLabel notice = new JLabel();
    private final JTable tableauUtilisateurBO = new JTable();
    List<UtilisateurBo> utilisateurBoList = new ArrayList<>();

    List<UtilisateurBo> listRechercheUtilisateursBO = new ArrayList<>();
    UtilisateurBo utilisateurBo, blankUtilisateurBO;
    boolean verifSiAjout = false;

    ImageIcon icone = new ImageIcon("LogoIconeDSI.png");
 //   ImageIcon iconeAttention = new ImageIcon("attention.png");

    /**
     * Constructeur par defaut
     */
    public PageUtilisateurBO() {
        initialiserComposants();
    }


    /**
     * Méthode qui affiche le graphisme de la page
     */
    public void initialiserComposants() {
        setTitle("Utilisateurs Back office");
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
        panHaut.setLayout(new BorderLayout());
        txtRechercher.setText(" Rechercher par login ");
        panHaut.add(txtRechercher, BorderLayout.CENTER);
        panHaut.add(btnRechercher, BorderLayout.NORTH);
        panHaut.add(notice, BorderLayout.SOUTH);

        notice.setSize(200, 50);
       // notice.setIcon(iconeAttention);
        notice.setText("Rôle = MODERATEUR  ou  ADMIN");
        notice.setToolTipText(notice.getText());

        //Panel centre
        panCentre.setPreferredSize(new Dimension(900, 250));
        panCentre.setLayout(new BorderLayout());
        panCentre.add(tableauUtilisateurBO.getTableHeader(), BorderLayout.NORTH);
        tableauUtilisateurBO.setRowHeight(30);

        //ScrollPane
        JScrollPane scrollPane = new JScrollPane(tableauUtilisateurBO);
        panCentre.add(scrollPane, BorderLayout.CENTER);

        panCentre.add(tableauUtilisateurBO, BorderLayout.CENTER);
        panCentre.add(new JScrollPane(tableauUtilisateurBO), BorderLayout.CENTER);

        panBas.setSize(500, 200);
        panBas.add(btnEnrModif);
        panBas.add(btnAjouterLigne);
        panBas.add(btnSupprimerUtilisateur);
        panBas.add(btnAnnuler);

        afficheJTableWithAllUtilisateurBO();

        setContentPane(panPrincipal);

        /*
         * Mouse listenner du champ de recherche qui efface le contenu du champ
         **/
        txtRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField txtRechercher = ((JTextField) e.getSource());
                txtRechercher.setText("");
            }
        });

        /*
         * Mouse listenner du bouton de recherche
         **/
        btnRechercher.addActionListener(e -> {
            listRechercheUtilisateursBO = new ArrayList<>();
            try {
                UtilisateurBoManager.getInstance().SelectAll();
            } catch (BLLException ex) {
                ex.printStackTrace();
            }
            for (UtilisateurBo utilisateurBo : utilisateurBoList) {
                String login = utilisateurBo.getLogin().toLowerCase();

                String recherche = txtRechercher.getText().toLowerCase();

                if (login.contains(recherche)) {
                    listRechercheUtilisateursBO.add(utilisateurBo);
                    TableModelUtilisateurBO model = new TableModelUtilisateurBO(listRechercheUtilisateursBO);
                    tableauUtilisateurBO.setModel(model);
                }
            }
            if (listRechercheUtilisateursBO.size() == 0) {
                JOptionPane.showMessageDialog(panPrincipal, "Aucun utilisateurBO trouvé", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        /*
         * Mouse listenner sur le tableau UtilisateurBO
         */
        tableauUtilisateurBO.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idUtilisateurBOSelected = (int) tableauUtilisateurBO.getValueAt(tableauUtilisateurBO.getSelectedRow(), 3);
                try {
                    utilisateurBo = UtilisateurBoManager.getInstance().SelectById(idUtilisateurBOSelected);
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /*
         * Mouse listenner sur le bouton annuler
         */
        btnAnnuler.addActionListener(e -> {
            txtRechercher.setText(" Rechercher par login ");
            utilisateurBo = null;
            blankUtilisateurBO = null;
            afficheJTableWithAllUtilisateurBO();
        });

        /*
         * listenner sur le btnAjouterUtilisateurBO pour ajouter une ligne vierge
         */
        btnAjouterLigne.setSize(140, 50);
        btnAjouterLigne.addActionListener(e -> {
            verifSiAjout = true;
            List<UtilisateurBo> allUtilisateursBO = null;
            try {
                allUtilisateursBO = UtilisateurBoManager.getInstance().SelectAll();
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }
            blankUtilisateurBO = new UtilisateurBo();
            utilisateurBoList.add(blankUtilisateurBO);

            //////  On récupére la plus haute id du tableau pour assigner blankSport à 1 au dessus ////////////////
            assert allUtilisateursBO != null;
            int idMax = allUtilisateursBO.get(0).getIdUtilisateur();

            for (UtilisateurBo bo : allUtilisateursBO) {
                int idUtilisateurBO = bo.getIdUtilisateur();
                if (idUtilisateurBO > idMax) {
                    idMax = idUtilisateurBO;
                }
            }
            blankUtilisateurBO.setIdUtilisateur(idMax + 1);
            blankUtilisateurBO.setLogin("");
            blankUtilisateurBO.setMdp("");
            blankUtilisateurBO.setRole(Roles.ADMIN);

            try {
                String input = JOptionPane.showInputDialog(null, "Merci de saisir le  MOT DE PASSE qui sera crypté");
                blankUtilisateurBO.setMdp(input);
                UtilisateurBoManager.getInstance().insert(blankUtilisateurBO);
            } catch (BLLException bllException) {
                bllException.printStackTrace();
            }

            TableModelUtilisateurBO model = new TableModelUtilisateurBO(utilisateurBoList);
            model.fireTableDataChanged();
            tableauUtilisateurBO.revalidate();
            tableauUtilisateurBO.setModel(model);

            blankUtilisateurBO = null;
            afficheJTableWithAllUtilisateurBO();
        });

        /*
         * listenner sur le btnEnrModif pour enregistrer les modifications
         */
        btnEnrModif.addActionListener(e -> {
            /* Récupérer les valeurs du tableauAnomalies, on vérifie pour chaque ligne */
            for (int i = 0; i < tableauUtilisateurBO.getRowCount(); i++) {
                try {
                    utilisateurBo = UtilisateurBoManager.getInstance().SelectById((Integer) tableauUtilisateurBO.getValueAt(i, 3));
                } catch (BLLException bllException) {
                    bllException.printStackTrace();
                }

                String loginUtilisateurBO = String.valueOf(tableauUtilisateurBO.getValueAt(i, 0));
                String mdpUtilisateurBO = String.valueOf(tableauUtilisateurBO.getValueAt(i, 1));
                Roles roleUtilisateurBO;

                if (tableauUtilisateurBO.getValueAt(i, 2).equals(Roles.ADMIN)) {
                    roleUtilisateurBO = Roles.ADMIN;
                } else {
                    roleUtilisateurBO = Roles.MODERATEUR;
                }

                /* ENREGISTRER LES VALEURS DS LA BASE ***/
                if (!utilisateurBo.getLogin().equals(loginUtilisateurBO) || !utilisateurBo.getMdp().equals(mdpUtilisateurBO)
                        || !utilisateurBo.getRole().getLibelle().equals(roleUtilisateurBO.toString())) {
                    try {
                        utilisateurBo.setLogin(loginUtilisateurBO);
                        utilisateurBo.setMdp(mdpUtilisateurBO);
                        utilisateurBo.setRole(roleUtilisateurBO);

                        int j;
                        if (verifSiAjout) {
                            j = JOptionPane.showConfirmDialog(btnEnrModif, "Êtes-vous sûr de vouloir enregistrer l'utilisateur BO " + utilisateurBo.getIdUtilisateur() + " ?",
                                    "Veuillez confirmer votre choix",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                                    verifSiAjout = false;
                        } else {
                            j = JOptionPane.showConfirmDialog(btnEnrModif, "La modification est irréversible. Êtes-vous sûr de vouloir enregistrer l'utilisateur BO " + utilisateurBo.getIdUtilisateur() + " ?",
                                    "Veuillez confirmer votre choix",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
                        }
                        if (j == 0)  /*user a dit oui*/ {
                            UtilisateurBoManager.getInstance().update(utilisateurBo);
                            JOptionPane.showMessageDialog(null, "Utilisateur BO " + tableauUtilisateurBO.getValueAt(i, 3) + " enregistré ");
                        }
                    } catch (BLLException bllException) {
                        bllException.printStackTrace();
                    }
                }
            }//fin boucle for
           //   JOptionPane.showMessageDialog(null, "Aucune(s) modification(s) détectée(s)");
            afficheJTableWithAllUtilisateurBO();
        });

        /*
         * listenner sur le btnSupprimerUtilisateur pour supprimer l'utilisateurBo sélectionné
         */
        btnSupprimerUtilisateur.addActionListener(e -> {
            if (utilisateurBo == null) {
                JOptionPane.showMessageDialog(btnSupprimerUtilisateur, "Veuillez sélectionner un utilisateur");
                return;
            }

            int i = JOptionPane.showConfirmDialog(btnSupprimerUtilisateur, "La suppression est irréversible. Êtes-vous sûr de vouloir supprimer l'utilisateur BO " + utilisateurBo.getIdUtilisateur() + " ?",
                    "Veuillez confirmer votre choix",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icone);
            if (i == 0) //user a dit oui
            {
                try {
                    UtilisateurBoManager.getInstance().delete(utilisateurBo);
                    JOptionPane.showMessageDialog(btnSupprimerUtilisateur, "Utilisateur BO " + utilisateurBo.getIdUtilisateur() + " supprimé");
                    afficheJTableWithAllUtilisateurBO();
                } catch (BLLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }//fin initialiserComposants

    /**
     * Méthode qui affiche tous les utilisateursBO
     */
    private void afficheJTableWithAllUtilisateurBO() {
        try {
            utilisateurBoList = remplirJTableWithAllUtilisateursBO();
            TableModelUtilisateurBO model = new TableModelUtilisateurBO(utilisateurBoList);
            tableauUtilisateurBO.setModel(model);
        } catch (BLLException e) {
            e.printStackTrace();
        }
    }

}//fin class
