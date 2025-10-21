package com.marketplace.api.factory;

import com.marketplace.services.admin.AdminAuthService;
import java.util.logging.Logger;

/**
 * Factory pour instancier les services
 * Remplace l'injection EJB sur Tomcat
 */
public class ServiceFactory {
    private static final Logger logger = Logger.getLogger(ServiceFactory.class.getName());
    
    private static AdminAuthService authService;
    
    /**
     * Retourne l'instance unique du service d'auth
     */
    public static AdminAuthService getAdminAuthService() {
        if (authService == null) {
            try {
                authService = new AdminAuthService();
                logger.info("✅ AdminAuthService créé via ServiceFactory");
            } catch (Exception e) {
                logger.severe("❌ Erreur création AdminAuthService: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return authService;
    }
}