package com.dsi.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;


public class ResultSetTableModel extends AbstractTableModel{

    private ResultSet rs;

    public ResultSetTableModel(ResultSet rs) {
        this.rs = rs;
        fireTableDataChanged();
    }

    //************************************************************
    // Methode qui va retourner un nombre de lignes total
    //************************************************************
    @Override
    public int getRowCount() {
        try {
            if (rs == null) {
                return 0;
            } else {
                rs.last();
                return rs.getRow();
            }
        } catch (SQLException e) {
            System.out.println("un probleme dans le comptage des lignes");
            System.out.println(e.getMessage());
            return 0;
        }
    }

    //************************************************************
    // Methode qui va retourner un nombre de colonnes total
    //************************************************************
    @Override
    public int getColumnCount() {
        try {
            if (rs == null) {
                return 0;
            } else {
                return  rs.getMetaData().getColumnCount();
            }
        } catch (SQLException e) {
            System.out.println("un probleme dans le comptage des colonnes");
            System.out.println(e.getMessage());
            return 0;
        }
    }

    //*******************************************************************
    // Methode qui va afficher la valeur aux index du tableau
    // utilise les retours de methode de getRowCount() et getColumnCount()
    //*******************************************************************
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > getRowCount()
                || columnIndex < 0 || columnIndex > getColumnCount()) {
            return null;
        }
        try {
            if (rs == null) {
                return null;
            } else {
                rs.absolute(rowIndex + 1);
                return rs.getObject(columnIndex + 1);
            }
        } catch (SQLException e) {
            System.out.println("un probleme dans le parcours des lignes");
            System.out.println(e.getMessage());
            return null;
        }
    }
    //************************************************************
    // Methode qui va récupérer les nom de colonnes dans la base
    //************************************************************
    @Override
    public String getColumnName(int columnIndex) {
        try {
            return rs.getMetaData().getColumnName(columnIndex + 1);
        } catch (SQLException e) {
            System.out.println("un probleme dans la recupération du nom des colonnes");
            System.out.println(e.getMessage());
        }
        return super.getColumnName(columnIndex);
    }
}
