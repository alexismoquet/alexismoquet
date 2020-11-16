package com.dsi.controller.tableModels;

import com.dsi.model.beans.Sport;
import javax.swing.table.AbstractTableModel;
import java.io.Serializable;
import java.util.List;

public class TableModelSport extends AbstractTableModel implements Serializable {

    private final String[] titres = {"Libellé sport","IdSport"};

    private List<Sport> sports;

    public TableModelSport (List<Sport> sports) {
        this.sports = sports;
    }

    @Override
    public int getRowCount() {
        return sports.size();
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
            if (columnIndex == 0) {
                return sports.get(rowIndex).getSport_libelle();
            } else if (columnIndex == 1) {
                return sports.get(rowIndex).getSport_id();
            } else {
                return "";
            }
        }

    @Override
    public void setValueAt(Object value, int row, int column) {
        fireTableCellUpdated(row, column);
        if (column == 0) {
            sports.get(row).setSport_libelle((String) value);
        }
    }

}//fin class
