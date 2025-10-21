package com.marketplace.dto.admin.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatistiqueResponse {
    // Utilisateurs
    private Long totalUtilisateurs;
    private Long utilisateursActifs;
    private Long nouvellesInscriptions30j;
    
    // Boutiques
    private Long totalBoutiques;
    private Long boutiquesActives;
    private Long boutiquesEnAttente;
    
    // Produits
    private Long totalProduits;
    private Long produitsActifs;
    
    // Commandes
    private Long totalCommandes;
    private Long commandesAujourdhui;
    private Long commandesSemaine;
    private Long commandesMois;
    
    // Chiffre d'affaires
    private BigDecimal caTotal;
    private BigDecimal caAujourdhui;
    private BigDecimal caSemaine;
    private BigDecimal caMois;
}