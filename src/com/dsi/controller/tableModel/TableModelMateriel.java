package com.dsi.controller.tableModel;

import com.dsi.model.beans.Materiel;
import javax.swing.table.AbstractTableModel;
import java.util.List;


public class TableModelMateriel extends AbstractTableModel {

    private final String[] titres = {"Nom","Description","idAdresse","idCategorie","idMateriel"};

    private List<Materiel> materiels;

    public TableModelMateriel (List<Materiel> materiels) {
        this.materiels = materiels;

    }

    public int getRowCount() {
        return materiels.size();
    }

    public int getColumnCount() {
        return titres.length;
    }

    public String getColumnName(int column) {
        return titres[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return materiels.get(rowIndex).getMateriel_nom();
            case 1:
                return materiels.get(rowIndex).getMateriel_description();
            case 2:
                return materiels.get(rowIndex).getMateriel_adresse_id();
            case 3:
                return materiels.get(rowIndex).getMateriel_categorie_id();
            case 4:
                return materiels.get(rowIndex).getMateriel_id();
            default:
                return "";
        }
    }

}//fin class
