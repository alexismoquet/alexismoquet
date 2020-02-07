package com.dsi.librairies;

/**
 * Classe FonctionsDate
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public class FonctionsDate {

    /**
     * Convertion d'une date format java.sql.Date au format java.util.Date
     * @param pSqlDate: Date au format java.sql.Date
     * @return java.util.Date: Date convertie
     */
    public static java.util.Date sqlDateVersJavaDate(java.sql.Date pSqlDate){
        java.util.Date javaDate = null;

        if (pSqlDate != null){
            javaDate = new java.util.Date(pSqlDate.getTime());
        }

        return javaDate;
    }
}
