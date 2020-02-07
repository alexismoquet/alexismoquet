package com.dsi.Tests;

import com.dsi.librairies.UMdp;
import com.dsi.model.beans.UtilisateurBo;
import com.dsi.model.bll.BLLException;
import com.dsi.model.bll.UtilisateurBoManager;

/**
 * Classe Test_UMdp
 * Test de l'utilitaire de mot de passe
 *
 * @author Christophe Michard
 * @since Créé le 07/02/2020
 */
public class Test_UMdp {

    public static void main(String[] args) {

        String mdpValide = "P@ssw0rd"; //Mot de passe valide pour l'utilisateur admin
        String mdpNonValide = "Password";

        //Création du manager utilisateur du back office
        UtilisateurBoManager ubm = UtilisateurBoManager.getInstance();

        //Récupération du mot de passe de l'utilisateur admin en base
        UtilisateurBo utilisateur = null;
        try {
            utilisateur = ubm.selectByLogin("admin");
        } catch (BLLException e) {
            e.printStackTrace();
        }

        //Contrôle d'un mot de passe valide,
        System.out.println("\n#################################################");
        System.out.println("### Contrôle d'un mot de passe saisie, valide ###");
        System.out.println("#################################################");
        System.out.println("\tResultat attendu : Mot de passe valide !");
        String res1 = UMdp.mdpCompare(mdpValide, utilisateur.getMdp())? "Mot de passe valide !": "Le mot de passe n'est pas valide !";
        System.out.println("\tResultat obtenu : "+res1);

        //Contrôle d'un mot de passe non valide,
        System.out.println("\n#####################################################");
        System.out.println("### Contrôle d'un mot de passe saisie, non valide ###");
        System.out.println("#####################################################");
        System.out.println("\tResultat attendu : Le mot de passe n'est pas valide !");
        String res2 = UMdp.mdpCompare(mdpNonValide, utilisateur.getMdp())? "Mot de passe valide !": "Le mot de passe n'est pas valide !";
        System.out.println("\tResultat obtenu : "+res2);

        //Contrôle du cryptage d'un mot de passe, succes
        //Cryptage du mot de passe
        String mdpCrypte = UMdp.mdpCrypte(mdpValide);

        //Contrôle du cryptage
        boolean res3 = (mdpCrypte.equals(utilisateur.getMdp()));

        //Affichage du résultat
        System.out.println("\n##############################################");
        System.out.println("### Contrôle d'un cryptage de mot de passe ###");
        System.out.println("##############################################");
        System.out.println("\tResultat attendu : true");
        System.out.println("\tResultat obtenu : "+res3);

        //Contrôle du cryptage d'un mot de passe, echec
        //Cryptage du mot de passe
        mdpCrypte = UMdp.mdpCrypte(mdpValide);

        //Contrôle du cryptage
        boolean res4 = (mdpCrypte.equals(utilisateur.getMdp()+"abc"));

        //Affichage du résultat
        System.out.println("\n##############################################");
        System.out.println("### Contrôle d'un cryptage de mot de passe ###");
        System.out.println("##############################################");
        System.out.println("\tResultat attendu : false");
        System.out.println("\tResultat obtenu : "+res4);
    }
}
