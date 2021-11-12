package com.api.payMyBuddy.service;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    // Méthode
    //          ==>>>> Appeller repository
    //       Optional<Utilisateur> optionalUtilisateur = utlisateurReposiroty.findByOne(emailRecherché)
    //         if(optionalUtilisateur.isEmpty()) => si vide pas d'entite avec l'email recherché
    //                              si non vide , Utilisateur utilisateurEntity = optionalutilisayeur.get();
    //                          utilisateyurEntoty ==MAPPER OU new UtilisateurFront(utilisateurEnrtity)
    //                          =>>>> Utilisateur Front
    //          return UtilisateurFront
}
