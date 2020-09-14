package com.dsi.controller.tableModel;

import com.dsi.librairies.Roles;
import com.dsi.model.beans.UtilisateurBo;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TableModelUtilisateurBO extends AbstractTableModel {

    private final String[] titres = {"Login", "Mot de passe", "Rôle", "Id"};

    private List<UtilisateurBo> utilisateurBoList;

    /**************************Contructeurs***********************/
    public TableModelUtilisateurBO() {

    }

    public TableModelUtilisateurBO(List<UtilisateurBo> utilisateurBoList) {
        this.utilisateurBoList = utilisateurBoList;
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
        return utilisateurBoList.size();
    }

    @Override
    public String getColumnName(int column) {
        return titres[column];
    }

    @Override
    public Object getValueAt(int row, int column) {

        switch (column) {
            case 0:
                return utilisateurBoList.get(row).getLogin();
            case 1:
                return utilisateurBoList.get(row).getMdp();
            case 2:
                return utilisateurBoList.get(row).getRole();
            case 3:
                return utilisateurBoList.get(row).getIdUtilisateur();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        fireTableCellUpdated(row, column);

        if (column == 0) {
            utilisateurBoList.get(row).setLogin((String) value);
        } else if (column == 1) {
            utilisateurBoList.get(row).setMdp((String) value);
        } else if (column == 2) {
            utilisateurBoList.get(row).setRole(Roles.valueOf(value.toString()));
        }
    }

} //fin class

