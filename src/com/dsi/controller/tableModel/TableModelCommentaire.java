package com.dsi.controller.tableModel;

import com.dsi.model.beans.Commentaire;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelCommentaire extends AbstractTableModel {

    private final String[] titres = {"Message","Date parution", "Note", "IdCommentaire","IdUtilisateur"};

    private List<Commentaire> commentaires;

    public TableModelCommentaire (List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    @Override
    public int getRowCount() {
        return commentaires.size();
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
                return commentaires.get(rowIndex).getCommentaire_message();
            case 1:
                return commentaires.get(rowIndex).getCommentaire_date_parution();
            case 2:
                return commentaires.get(rowIndex).getCommentaire_note();
            case 3:
                return commentaires.get(rowIndex).getCommentaire_id();
            case 4:
                return commentaires.get(rowIndex).getCommentaire_utilisateur_id();
            default:
                return "";
        }
    }
}
