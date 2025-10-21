package com.marketplace.dao.admin;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class AdminStatistiqueDAO {
    
    @PersistenceContext(unitName = "marketplacePU")
    private EntityManager em;
    
    /**
     * Statistiques globales du système
     */
    public Map<String, Object> getStatistiquesGlobales() {
        Map<String, Object> stats = new HashMap<>();
        
        // Utilisateurs
        stats.put("totalUtilisateurs", countUtilisateurs());
        stats.put("utilisateursActifs", countUtilisateursActifs());
        stats.put("nouvellesInscriptions30j", countNouveauxUtilisateurs30j());
        
        // Boutiques
        stats.put("totalBoutiques", countBoutiques());
        stats.put("boutiquesActives", countBoutiquesActives());
        stats.put("boutiquesEnAttente", countBoutiquesEnAttente());
        
        // Produits
        stats.put("totalProduits", countProduits());
        stats.put("produitsActifs", countProduitsActifs());
        
        // Commandes
        stats.put("totalCommandes", countCommandes());
        stats.put("commandesAujourdhui", countCommandesAujourdhui());
        stats.put("commandesSemaine", countCommandesSemaine());
        stats.put("commandesMois", countCommandesMois());
        
        // Chiffre d'affaires
        stats.put("caTotal", getChiffreAffairesTotal());
        stats.put("caAujourdhui", getChiffreAffairesAujourdhui());
        stats.put("caSemaine", getChiffreAffairesSemaine());
        stats.put("caMois", getChiffreAffairesMois());
        
        return stats;
    }
    
    // Compteurs Utilisateurs
    private Long countUtilisateurs() {
        return em.createQuery("SELECT COUNT(u) FROM Utilisateurs u", Long.class)
            .getSingleResult();
    }
    
    private Long countUtilisateursActifs() {
        return em.createQuery(
            "SELECT COUNT(u) FROM Utilisateurs u WHERE u.estActif = true", 
            Long.class
        ).getSingleResult();
    }
    
    private Long countNouveauxUtilisateurs30j() {
        LocalDate date30jAgo = LocalDate.now().minusDays(30);
        return em.createQuery(
            "SELECT COUNT(u) FROM Utilisateurs u WHERE u.dateCreation >= :date", 
            Long.class
        ).setParameter("date", date30jAgo.atStartOfDay()).getSingleResult();
    }
    
    // Compteurs Boutiques
    private Long countBoutiques() {
        return em.createQuery("SELECT COUNT(b) FROM Boutiques b", Long.class)
            .getSingleResult();
    }
    
    private Long countBoutiquesActives() {
        return em.createQuery(
            "SELECT COUNT(b) FROM Boutiques b WHERE b.estActive = true", 
            Long.class
        ).getSingleResult();
    }
    
    private Long countBoutiquesEnAttente() {
        return em.createQuery(
            "SELECT COUNT(b) FROM Boutiques b WHERE b.estVerifiee = false", 
            Long.class
        ).getSingleResult();
    }
    
    // Compteurs Produits
    private Long countProduits() {
        return em.createQuery("SELECT COUNT(p) FROM Produits p", Long.class)
            .getSingleResult();
    }
    
    private Long countProduitsActifs() {
        return em.createQuery(
            "SELECT COUNT(p) FROM Produits p WHERE p.estActif = true", 
            Long.class
        ).getSingleResult();
    }
    
    // Compteurs Commandes
    private Long countCommandes() {
        return em.createQuery("SELECT COUNT(c) FROM Commandes c", Long.class)
            .getSingleResult();
    }
    
    private Long countCommandesAujourdhui() {
        LocalDate today = LocalDate.now();
        return em.createQuery(
            "SELECT COUNT(c) FROM Commandes c WHERE DATE(c.dateCommande) = :today", 
            Long.class
        ).setParameter("today", today).getSingleResult();
    }
    
    private Long countCommandesSemaine() {
        LocalDate startOfWeek = LocalDate.now().minusDays(7);
        return em.createQuery(
            "SELECT COUNT(c) FROM Commandes c WHERE c.dateCommande >= :date", 
            Long.class
        ).setParameter("date", startOfWeek.atStartOfDay()).getSingleResult();
    }
    
    private Long countCommandesMois() {
        LocalDate startOfMonth = LocalDate.now().minusDays(30);
        return em.createQuery(
            "SELECT COUNT(c) FROM Commandes c WHERE c.dateCommande >= :date", 
            Long.class
        ).setParameter("date", startOfMonth.atStartOfDay()).getSingleResult();
    }
    
    // Chiffre d'affaires
    private BigDecimal getChiffreAffairesTotal() {
        BigDecimal ca = em.createQuery(
            "SELECT SUM(c.montantTotal) FROM Commandes c", 
            BigDecimal.class
        ).getSingleResult();
        return ca != null ? ca : BigDecimal.ZERO;
    }
    
    private BigDecimal getChiffreAffairesAujourdhui() {
        LocalDate today = LocalDate.now();
        BigDecimal ca = em.createQuery(
            "SELECT SUM(c.montantTotal) FROM Commandes c " +
            "WHERE DATE(c.dateCommande) = :today", 
            BigDecimal.class
        ).setParameter("today", today).getSingleResult();
        return ca != null ? ca : BigDecimal.ZERO;
    }
    
    private BigDecimal getChiffreAffairesSemaine() {
        LocalDate startOfWeek = LocalDate.now().minusDays(7);
        BigDecimal ca = em.createQuery(
            "SELECT SUM(c.montantTotal) FROM Commandes c " +
            "WHERE c.dateCommande >= :date", 
            BigDecimal.class
        ).setParameter("date", startOfWeek.atStartOfDay()).getSingleResult();
        return ca != null ? ca : BigDecimal.ZERO;
    }
    
    private BigDecimal getChiffreAffairesMois() {
        LocalDate startOfMonth = LocalDate.now().minusDays(30);
        BigDecimal ca = em.createQuery(
            "SELECT SUM(c.montantTotal) FROM Commandes c " +
            "WHERE c.dateCommande >= :date", 
            BigDecimal.class
        ).setParameter("date", startOfMonth.atStartOfDay()).getSingleResult();
        return ca != null ? ca : BigDecimal.ZERO;
    }
}