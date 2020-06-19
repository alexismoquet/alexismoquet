package com.dsi.controller.tableModel;

import com.dsi.model.beans.Categorie;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelCategorie extends AbstractTableModel {

    private final String[] titres = {"Libellé","IdCategorie"};

    private List<Categorie> categories;

    public TableModelCategorie (List<Categorie> categories) {
        this.categories = categories;
    }

    @Override
    public int getRowCount() {
        return categories.size();
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
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return categories.get(rowIndex).getCategorie_libelle();
            case 1:
                return categories.get(rowIndex).getCategorie_id();
            default:
                return "";
        }
    }
}
