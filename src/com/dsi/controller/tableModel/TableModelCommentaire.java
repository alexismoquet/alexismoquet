package com.dsi.controller.tableModel;

import com.dsi.model.beans.Commentaire;
import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class TableModelCommentaire extends AbstractTableModel {

    private final String[] titres = {"Message","Date parution", "Note", "IdCommentaire","IdUtilisateur", "IdAnnonce"};

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
                case 5:
                return commentaires.get(rowIndex).getCommentaire_annonce_id();
            default:
                return "";
        }
    }

    public void setValueAt(Object value, int row, int column) {
        fireTableCellUpdated(row, column);
        if (column == 0) {
            commentaires.get(row).setCommentaire_message((String) value);
        } else if (column ==1){
            commentaires.get(row).setCommentaire_date_parution(new Date());
        } else if (column ==2){
            commentaires.get(row).setCommentaire_note(Integer.parseInt((String)value));
        } else if (column ==3){
            commentaires.get(row).setCommentaire_id(Integer.parseInt((String)value));
        } else if (column ==4){
            commentaires.get(row).setCommentaire_utilisateur_id(Integer.parseInt((String)value));
        }else if (column ==5){
            commentaires.get(row).setCommentaire_annonce_id(Integer.parseInt((String)value));
        }
    }

}//fin class
