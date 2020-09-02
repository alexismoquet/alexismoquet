package com.dsi.controller.tableModel;

import com.dsi.librairies.FonctionsDate;
import com.dsi.model.beans.Utilisateur;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;

public class TableModelUtilisateur extends AbstractTableModel {

    private final String[] titres = {"Nom", "Prénom", "Email", "Téléphone mobile", "Téléphone fixe", "Mot de passe", "Date d'inscription", "Id"};

    private List <Utilisateur> utilisateurs;

    /**************************Contructeurs***********************/

    public TableModelUtilisateur(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    /**************************Fin Contructeurs***********************/

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    @Override
    public int getColumnCount() {
        return titres.length;
    }

    @Override
    public int getRowCount() {
        return utilisateurs.size();
    }

    @Override
    public String getColumnName(int column) {
        return titres[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

        switch (column) {
            case 0:
                return utilisateurs.get(row).getNom();
            case 1:
                return utilisateurs.get(row).getPrenom();
            case 2:
                return utilisateurs.get(row).getEmail();
            case 3:
                return utilisateurs.get(row).getTelMob();
            case 4:
                return utilisateurs.get(row).getTelFix();
            case 5:
                return utilisateurs.get(row).getMotDePasse();
            case 6:
                return utilisateurs.get(row).getDateInscription();
            case 7:
                return utilisateurs.get(row).getIdUtilisateur();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        fireTableCellUpdated(row, column);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

        if (column == 0) {
            utilisateurs.get(row).setNom((String) value);
        }
        else if (column == 1) {
            utilisateurs.get(row).setPrenom((String) value);
        }
        else if (column == 2) {
            utilisateurs.get(row).setEmail((String) value);
        }
        else if (column == 3) {
          utilisateurs.get(row).setTelMob((String) value);
        }
        else if (column == 4) {
            utilisateurs.get(row).setTelFix((String) value);
        }
        else if (column == 5) {
            utilisateurs.get(row).setMotDePasse((String) value);
            }
        else if (column == 6) {
            try {
                utilisateurs.get(row).setDateInscription(sdf.parse(value.toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    } //fin setValue

} //fin class