package com.dsi.controller;

import com.dsi.model.beans.Adresse;
import com.dsi.model.beans.Utilisateur;

import java.util.List;

import javax.swing.table.AbstractTableModel;



public class TableModel extends AbstractTableModel {

    private final String[] titres = {"Noms", "Prénoms", "Email", "Téléphone mobile"
            , "Téléphone fixe", "Adresse", "Ville", "Code postal","Complément", "Département", "Pays"};

    private final List <Utilisateur> utilisateurs;

    public void addUtilisateur(Utilisateur utilisateur) {
        int index = utilisateurs.size();
        utilisateurs.add(utilisateur);
        fireTableRowsInserted(index, index);
    }

    public Utilisateur getUtilisateurAt(int row) {
        if (row >= utilisateurs.size())
            return null;
        else
            return utilisateurs.get(row);
    }


    public void deleteUtilisateurAt(int row){
        if (row < utilisateurs.size()){
            utilisateurs.remove(row);
            fireTableRowsDeleted(row, row);
        }
    }

    public TableModel(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    @Override
    public int getColumnCount() {
        return titres.length;
    }

    @Override
    public int getRowCount() {
        return utilisateurs.size();
    }

    @Override
    public String getColumnName(int column) {
        return titres[column];
    }

    @Override
    public Object getValueAt(int row, int column) {

        switch (column) {
            case 0:
                return utilisateurs.get(row).getNom();
            case 1:
                return utilisateurs.get(row).getPrenom();
            case 2:
                return utilisateurs.get(row).getEmail();
            case 3:
                return utilisateurs.get(row).getTelMob();
            case 4:
                return utilisateurs.get(row).getTelFix();
            case 5:
                if (utilisateurs.get(row).getAdresses().size() != 0) {
                    Adresse a = utilisateurs.get(row).getAdresses().get(0);

                    return a.getAdresse();
                }else{
                    return "";
                }
            case 6:
              if (utilisateurs.get(row).getAdresses().size() != 0) {
                  Adresse a = utilisateurs.get(row).getAdresses().get(0);

                   return a.getVille();
                }else{
                    return "";
                }
            case 7:
                if (utilisateurs.get(row).getAdresses().size() != 0) {
                    Adresse a = utilisateurs.get(row).getAdresses().get(0);

                    return a.getCodePostal();
                }else{
                    return "";
                }
            case 8:
                if (utilisateurs.get(row).getAdresses().size() != 0) {
                    Adresse a = utilisateurs.get(row).getAdresses().get(0);

                    return a.getComplement();
                }else{
                    return "";
                }
            case 9:
                if (utilisateurs.get(row).getAdresses().size() != 0) {
                    Adresse a = utilisateurs.get(row).getAdresses().get(0);

                    return a.getDepartement();
                }else{
                    return "";
                }
            case 10:
                if (utilisateurs.get(row).getAdresses().size() != 0) {
                    Adresse a = utilisateurs.get(row).getAdresses().get(0);

                    return a.getPays();
                }else{
                    return "";
                }
            default:
                return "";
        }
    }

} //fin class