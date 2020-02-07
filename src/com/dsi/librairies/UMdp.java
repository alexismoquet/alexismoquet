package com.dsi.librairies;

import christophe.michard.Hash;

/**
 * Classe UMdp
 * Utilitaire de mot de passe
 *
 * @author Christophe Michard
 * @since Créé le 07/02/2020
 */
public class UMdp {

    /**
     * Crypte le mot de passe
     * @param pMotDePasse: Mot de passe à crypter
     * @return String: Mot de passe crypté
     */
    public static String mdpCrypte(String pMotDePasse){
        return Hash.creerHash_SHA256(pMotDePasse);
    }

    /**
     * Compare un mot de passe saisie avec un mot de passe crypté
     * @param pMdpAValider: Mot de passe à valider
     * @param pMdpValide: Mot de passe valide et crypté
     * @return boolean: True si le mot de passe est valide sinon false
     */
    public static boolean mdpCompare(String pMdpAValider, String pMdpValide){
        return pMdpValide.equals(Hash.creerHash_SHA256(pMdpAValider));
    }
}
