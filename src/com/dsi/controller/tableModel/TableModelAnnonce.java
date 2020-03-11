package com.dsi.controller.tableModel;

import com.dsi.model.beans.Annonce;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelAnnonce extends AbstractTableModel {

    private final String[] titres = {"Titres","Description","IdUtilisateur","idAnnonce"};

    private List<Annonce> annonces;

    public TableModelAnnonce (List<Annonce> annonces) {
        this.annonces = annonces;
    }

    @Override
    public int getRowCount() {
        return annonces.size();
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return annonces.get(rowIndex).getAnnonce_titre();
            case 1:
                return annonces.get(rowIndex).getAnnonce_description();
            case 2:
                return annonces.get(rowIndex).getAnnonce_utilisateur_id();
            case 3:
                return annonces.get(rowIndex).getAnnonce_id();
            default:
                return "";
        }
    }

}//fin class
