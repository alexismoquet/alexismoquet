package com.dsi.controller.tableModel;

import com.dsi.librairies.FonctionsDate;
import com.dsi.model.beans.Materiel;
import com.dsi.model.beans.Sortie;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;


public class TableModelSortie extends AbstractTableModel {

    private final String[] titres = {"Date retour","Date sortie", "sortie état", "IdMateriel", "IdSortie"};

    private List<Sortie> sorties;

    public TableModelSortie (List<Sortie> sorties) {
        this.sorties = sorties;

    }

    public int getRowCount() {
        return sorties.size();
    }

    public int getColumnCount() {
        return titres.length;
    }

    public String getColumnName(int column) {
        return titres[column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return sorties.get(rowIndex).getSortie_date_retour();
            case 1:
                return sorties.get(rowIndex).getSortie_date_sortie();
            case 2:
                return sorties.get(rowIndex).getSortie_etat();
            case 3:
                return sorties.get(rowIndex).getSortie_materiel_id();
            case 4:
                return sorties.get(rowIndex).getSortie_id();

            default:
                return "";
        }
    }
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        fireTableCellUpdated(rowIndex, columnIndex);

        if(columnIndex == 0) {
            sorties.get(rowIndex).setSortie_date_retour((Date) value);
        } else if (columnIndex == 1){
            sorties.get(rowIndex).setSortie_date_sortie((Date) value);
        } else if (columnIndex == 2){
            sorties.get(rowIndex).setSortie_etat((String) value);
        }
    }
}//fin class
