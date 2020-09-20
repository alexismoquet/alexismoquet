package com.dsi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe Parametres
 *
 * @author Christophe Michard
 * @since Créé le 05/02/2020
 */
public class Parametres {

    //Constantes
    private static final String NOM_FICHIER = "src/com/dsi/conf.properties";
    private static final String CONFIRMER_SORTIE = "ConfirmerSortie";

    //Attributs de la classe
    private static final Properties props = new Properties();

    //Paramètres de l'application
    public static boolean confirmerQuitter = true;

    /*
     * Charge les paramètres contenu dans le fichier "conf.properties" au chargement de la classe
     */
    static {
        try (FileInputStream fis = new FileInputStream(NOM_FICHIER)){
            props.load(fis);

            confirmerQuitter = Boolean.parseBoolean(props.getProperty(CONFIRMER_SORTIE,"true"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retourne une propriété selon une clé
     * @param pKey: Clé
     * @return Valeur
     */
    public static String getProperties(String pKey) {
        return props.getProperty(pKey,null);
    }

    /**
     * Enregistre les paramètres dans le fichier
     * @return Vrai si l'enregistrement à bien eu lieu
     */
    public static boolean sauve(){
        boolean res = false;

        props.setProperty(CONFIRMER_SORTIE, String.valueOf(confirmerQuitter));

        try (FileOutputStream fos = new FileOutputStream(NOM_FICHIER)){
            props.store(fos,"Sauve Paramètres") ;
            res = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

}