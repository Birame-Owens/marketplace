package com.marketplace.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe générique pour les réponses API
 * Encapsule toutes les réponses REST avec un format standard
 * 
 * @param <T> Type de données retournées
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    /**
     * Indique si la requête a réussi
     */
    private boolean success;
    
    /**
     * Message descriptif (succès ou erreur)
     */
    private String message;
    
    /**
     * Données retournées (peut être null en cas d'erreur)
     */
    private T data;
    
    /**
     * Crée une réponse de succès avec des données
     * 
     * @param data Données à retourner
     * @return ApiResponse avec success=true
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Opération réussie", data);
    }
    
    /**
     * Crée une réponse de succès avec un message personnalisé et des données
     * 
     * @param message Message de succès
     * @param data Données à retourner
     * @return ApiResponse avec success=true
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }
    
    /**
     * Crée une réponse d'erreur avec un message
     * 
     * @param message Message d'erreur
     * @return ApiResponse avec success=false et data=null
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
    
    /**
     * Crée une réponse d'erreur avec un message et des données optionnelles
     * 
     * @param message Message d'erreur
     * @param data Données additionnelles (peut contenir détails de l'erreur)
     * @return ApiResponse avec success=false
     */
    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }
}