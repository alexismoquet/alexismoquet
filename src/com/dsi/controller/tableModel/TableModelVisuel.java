package com.dsi.controller.tableModel;

import com.dsi.model.beans.Visuel;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelVisuel extends AbstractTableModel {

    private final String[] titres = {"Visuel","Nom du fichier", "IdVisuel", "IdMatériel"};
    private List<Visuel> visuels;

    
    public TableModelVisuel(List<Visuel> visuels) {
        this.visuels = visuels;
    }

    @Override
    public int getRowCount() {
        return visuels.size();
    }

    @Override
    public int getColumnCount() {
        return titres.length;
    }

    @Override
    public String getColumnName(int column) {
        return titres[column];
    }

    @Override
    public Class getColumnClass(int column) {
        return (column == 0) ? Icon.class : Object.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return new ImageIcon(visuels.get(rowIndex).getVisuel_nom_fichier());
            case 1:
                return visuels.get(rowIndex).getVisuel_nom_fichier();
            case 2:
                return visuels.get(rowIndex).getVisuel_id();
            case 3:
                return visuels.get(rowIndex).getVisuel_materiel_id();
            default:
                return "";
        }
    }
    @Override
    public void setValueAt(Object value, int row, int column) {
        fireTableCellUpdated(row, column);
        if (column == 1) {
            visuels.get(row).setVisuel_nom_fichier((String) value);
        } else if (column == 2){
            visuels.get(row).setVisuel_materiel_id(Integer.parseInt(value.toString()));
        }
    }

}//fin class
