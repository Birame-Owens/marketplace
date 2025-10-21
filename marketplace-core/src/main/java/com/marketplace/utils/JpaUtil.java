package com.marketplace.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.logging.Logger;

/**
 * Utilitaire de gestion de l'EntityManager sans conteneur EJB
 * Remplace @PersistenceContext sur Tomcat
 */
public class JpaUtil {
    private static final Logger logger = Logger.getLogger(JpaUtil.class.getName());
    private static EntityManagerFactory emf;
    
    static {
        try {
            emf = Persistence.createEntityManagerFactory("marketplacePU");
            logger.info("✅ EntityManagerFactory créée");
        } catch (Exception e) {
            logger.severe("❌ Erreur création EMF: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Crée un nouvel EntityManager
     */
    public static EntityManager getEntityManager() {
        if (emf == null) {
            throw new RuntimeException("EntityManagerFactory non initialisée");
        }
        return emf.createEntityManager();
    }
    
    /**
     * Retourne la factory (optionnel)
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}