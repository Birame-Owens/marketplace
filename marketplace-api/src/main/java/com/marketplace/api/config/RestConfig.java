package com.marketplace.api.config;

import com.marketplace.api.controllers.admin.AdminAuthController;
import com.marketplace.api.controllers.admin.AdminBoutiqueController;
import com.marketplace.api.controllers.admin.AdminDashboardController;
import com.marketplace.api.controllers.admin.AdminParametreController;
import com.marketplace.api.controllers.admin.AdminUtilisateurController;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class RestConfig extends Application {
    private static final Logger logger = Logger.getLogger(RestConfig.class.getName());

    public RestConfig() {
        logger.info("✅ RestConfig initialisé");
    }

    @Override
    public Set<Class<?>> getClasses() {
        logger.info("🔴 RestConfig.getClasses() appelé");
        Set<Class<?>> classes = new HashSet<>();
        classes.add(AdminAuthController.class);
        classes.add(AdminBoutiqueController.class);
        classes.add(AdminDashboardController.class);
        classes.add(AdminParametreController.class);
        classes.add(AdminUtilisateurController.class);
        logger.info("✅ " + classes.size() + " controllers enregistrés");
        return classes;
    }
}