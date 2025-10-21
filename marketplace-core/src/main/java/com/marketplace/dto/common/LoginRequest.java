package com.marketplace.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la requête de connexion
 * Utilisé pour l'authentification (Admin, Gérant, Client)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    /**
     * Email de l'utilisateur
     */
    private String email;
    
    /**
     * Mot de passe en clair (sera hashé lors de la vérification)
     */
    private String motDePasse;
}