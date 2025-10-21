package com.marketplace.dto.common;

import com.marketplace.dto.admin.response.UtilisateurResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    private boolean success;
    private String message;
    private UtilisateurResponse utilisateur;
    private String token; // Pour JWT (optionnel pour l'instant)
    
    public LoginResponse(boolean b, String message2, Object object, Object object2) {
		// TODO Auto-generated constructor stub
	}

	/**
     * Constructeur pour une réponse réussie
     */
    public static LoginResponse success(UtilisateurResponse utilisateur) {
        return new LoginResponse(true, "Connexion réussie", utilisateur, null);
    }
    
    /**
     * Constructeur pour une réponse réussie avec token
     */
    public static LoginResponse success(UtilisateurResponse utilisateur, String token) {
        return new LoginResponse(true, "Connexion réussie", utilisateur, token);
    }
    
    /**
     * Constructeur pour une réponse d'erreur
     */
    public static LoginResponse error(String message) {
        return new LoginResponse(false, message, null, null);
    }
}