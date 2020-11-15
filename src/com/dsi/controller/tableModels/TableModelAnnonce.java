package com.dsi.controller.tableModels;

import com.dsi.model.beans.Annonce;
import javax.swing.table.AbstractTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TableModelAnnonce extends AbstractTableModel {

    private final String[] titres = {"Titre","Description","IdUtilisateur","IdAnnonce", "IdMateriel", "Date de parution"};

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
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        fireTableCellUpdated(rowIndex, columnIndex);
        switch (columnIndex) {
            case 0:
                return annonces.get(rowIndex).getAnnonce_titre();
            case 1:
                return annonces.get(rowIndex).getAnnonce_description();
            case 2:
                return annonces.get(rowIndex).getAnnonce_utilisateur_id();
            case 3:
                return annonces.get(rowIndex).getAnnonce_id();
            case 4:
                return annonces.get(rowIndex).getAnnonce_materiel_id();
            case 5:
                return annonces.get(rowIndex).getAnnonce_date_parution();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        fireTableCellUpdated(rowIndex, columnIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

        if(columnIndex == 0) {
            annonces.get(rowIndex).setAnnonce_titre(value.toString());
        } else if (columnIndex == 1){
            annonces.get(rowIndex).setAnnonce_description(value.toString());
        } else if (columnIndex == 2){
            annonces.get(rowIndex).setAnnonce_utilisateur_id(Integer.parseInt(value.toString()));
        } else if (columnIndex == 3){
            annonces.get(rowIndex).setAnnonce_id(Integer.parseInt(value.toString()));
        } else if (columnIndex == 4){
            annonces.get(rowIndex).setAnnonce_materiel_id(Integer.parseInt(value.toString()));
        }else if (columnIndex == 5){
            try {
                annonces.get(rowIndex).setAnnonce_date_parution(sdf.parse(value.toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}//fin class
