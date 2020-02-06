package com.dsi.librairies;

/**
 * Enumération Roles
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public enum Roles {

    ADMIN("Administrateur"), MODERATEUR("Modérateur");

    private String libelle;

    Roles(String pLibelle){
        this.libelle = pLibelle;
    }

    public String getLibelle(){ return this.libelle; }

    @Override
    public String toString() {
        return this.libelle;
    }
}
