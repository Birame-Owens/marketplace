package com.marketplace.services.admin;

import com.marketplace.dao.admin.AdminStatistiqueDAO;
import com.marketplace.dto.admin.response.AdminDashboardResponse;
import com.marketplace.dto.admin.response.StatistiqueResponse;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.math.BigDecimal;
import java.util.Map;

@Stateless
public class AdminStatistiqueService {
    
    @EJB
    private AdminStatistiqueDAO statistiqueDAO;
    
    @EJB
    private AdminUtilisateurService utilisateurService;
    
    @EJB
    private AdminBoutiqueService boutiqueService;
    
    /**
     * Dashboard admin avec toutes les statistiques
     */
    public AdminDashboardResponse getDashboard() {
        Map<String, Object> stats = statistiqueDAO.getStatistiquesGlobales();
        
        AdminDashboardResponse dashboard = new AdminDashboardResponse();
        
        // Utilisateurs
        dashboard.setTotalUtilisateurs((Long) stats.get("totalUtilisateurs"));
        dashboard.setTotalAdmins(utilisateurService.countUtilisateursByRole("ADMIN"));
        dashboard.setTotalGerants(utilisateurService.countUtilisateursByRole("GERANT"));
        dashboard.setTotalClients(utilisateurService.countUtilisateursByRole("CLIENT"));
        
        // Boutiques
        dashboard.setTotalBoutiques((Long) stats.get("totalBoutiques"));
        dashboard.setBoutiquesActives((Long) stats.get("boutiquesActives"));
        dashboard.setBoutiquesEnAttente((Long) stats.get("boutiquesEnAttente"));
        
        // Produits
        dashboard.setTotalProduits((Long) stats.get("totalProduits"));
        
        // Commandes
        dashboard.setTotalCommandes((Long) stats.get("totalCommandes"));
        dashboard.setCommandesAujourdhui((Long) stats.get("commandesAujourdhui"));
        
        return dashboard;
    }
    
    /**
     * Statistiques détaillées
     */
    public StatistiqueResponse getStatistiques() {
        Map<String, Object> stats = statistiqueDAO.getStatistiquesGlobales();
        
        StatistiqueResponse response = new StatistiqueResponse();
        response.setTotalUtilisateurs((Long) stats.get("totalUtilisateurs"));
        response.setUtilisateursActifs((Long) stats.get("utilisateursActifs"));
        response.setNouvellesInscriptions30j((Long) stats.get("nouvellesInscriptions30j"));
        
        response.setTotalBoutiques((Long) stats.get("totalBoutiques"));
        response.setBoutiquesActives((Long) stats.get("boutiquesActives"));
        response.setBoutiquesEnAttente((Long) stats.get("boutiquesEnAttente"));
        
        response.setTotalProduits((Long) stats.get("totalProduits"));
        response.setProduitsActifs((Long) stats.get("produitsActifs"));
        
        response.setTotalCommandes((Long) stats.get("totalCommandes"));
        response.setCommandesAujourdhui((Long) stats.get("commandesAujourdhui"));
        response.setCommandesSemaine((Long) stats.get("commandesSemaine"));
        response.setCommandesMois((Long) stats.get("commandesMois"));
        
        response.setCaTotal((BigDecimal) stats.get("caTotal"));
        response.setCaAujourdhui((BigDecimal) stats.get("caAujourdhui"));
        response.setCaSemaine((BigDecimal) stats.get("caSemaine"));
        response.setCaMois((BigDecimal) stats.get("caMois"));
        
        return response;
    }
}