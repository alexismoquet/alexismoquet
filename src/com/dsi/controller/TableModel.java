package com.dsi.controller;

import com.dsi.model.beans.Adresse;
import com.dsi.model.beans.Utilisateur;

import java.util.List;

import javax.swing.table.AbstractTableModel;



public class TableModel extends AbstractTableModel {

    private final String[] titres = {"Noms", "Prénoms", "Email", "Téléphone mobile"
            , "Téléphone fixe", "Adresse", "Ville", "Code postal","Complément", "Département", "Pays"};

    private final List <Utilisateur> utilisateurs;
    private final List <Adresse> adresses;

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
    public Adresse getAdresseAt(int row) {
        if (row >= utilisateurs.size())
            return null;
        else
            return adresses.get(row);
    }

    public void deleteUtilisateurAt(int row){
        if (row < utilisateurs.size()){
            utilisateurs.remove(row);
            fireTableRowsDeleted(row, row);
        }
    }

    public TableModel(List<Utilisateur> utilisateurs, List<Adresse> adresses) {
        this.utilisateurs = utilisateurs;
        this.adresses = adresses;
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
                return adresses.get(row).getAdresse();
            case 6:
                return adresses.get(row).getVille();
            case 7:
                return adresses.get(row).getCodePostal();
            case 8:
                return adresses.get(row).getComplement();
            case 9:
                return adresses.get(row).getDepartement();
            case 10:
                return adresses.get(row).getPays();
            default:
                return "";
        }
    }

} //fin class