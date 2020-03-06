package com.dsi.controller.tableModel;

import com.dsi.model.beans.Sport;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelSport extends AbstractTableModel {

    private final String[] titres = {"Sports"};

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
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return sports.get(rowIndex).getSport_libelle();
            default:
                return "";
        }
    }

}//fin class
