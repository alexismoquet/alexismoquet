package com.dsi.controller.tableModel;

import com.dsi.model.beans.Adresse;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelAdresse extends AbstractTableModel {

    private final String[] titres = {"Adresse","Ville","Code postal","Complément", "Département", "Pays", "IdAdresse", "IdUtilisateur"};

    private List<Adresse> adresses;

    public TableModelAdresse (List<Adresse> adresses) {
        this.adresses = adresses;
    }

    @Override
    public int getRowCount() {
        return adresses.size();
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
                return adresses.get(rowIndex).getAdresse();
            case 1:
                return adresses.get(rowIndex).getVille();
            case 2:
                return adresses.get(rowIndex).getCodePostal();
            case 3:
                return adresses.get(rowIndex).getComplement();
            case 4:
                return adresses.get(rowIndex).getDepartement();
            case 5:
                return adresses.get(rowIndex).getPays();
            case 6:
                return adresses.get(rowIndex).getIdAdresse();
            case 7:
                return adresses.get(rowIndex).getIdUtilisateur();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        fireTableCellUpdated(rowIndex, columnIndex);

        if(columnIndex == 0) {
            adresses.get(rowIndex).setAdresse(value.toString());
        } else if (columnIndex == 1){
            adresses.get(rowIndex).setVille(value.toString());
        } else if (columnIndex ==2){
            adresses.get(rowIndex).setCodePostal(value.toString());
        }else if (columnIndex ==3){
            adresses.get(rowIndex).setComplement(value.toString());
        }else if (columnIndex ==4){
            adresses.get(rowIndex).setDepartement(value.toString());
        }else if (columnIndex ==5){
            adresses.get(rowIndex).setPays(value.toString());
        }else if (columnIndex ==6){
            adresses.get(rowIndex).setIdAdresse(Integer.parseInt(String.valueOf(value)));
        }
    }

}//fin class
