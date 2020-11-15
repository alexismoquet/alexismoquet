package com.dsi.librairies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe FonctionsDate
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public class FonctionsDate {

    public FonctionsDate() throws ParseException {
    }

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

    /**
     * Convertion d'une date format java.util.Date au format java.sql.Date
     * @param pUtilDate: Date au format java.util.Date
     * @return java.sql.Date: Date convertie
     */
    public static java.sql.Date utilDateVersSqlDate(java.util.Date pUtilDate){
        java.sql.Date javaDate = null;

        if (pUtilDate != null){
            javaDate = new java.sql.Date(pUtilDate.getTime());
        }

        return javaDate;
    }

}//fin class
