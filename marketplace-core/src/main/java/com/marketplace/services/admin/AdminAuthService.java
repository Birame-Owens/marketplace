package com.marketplace.services.admin;

import com.marketplace.dao.admin.AdminUtilisateurDAO;
import com.marketplace.dao.common.RoleDAO;
import com.marketplace.dto.admin.request.CreateUtilisateurRequest;
import com.marketplace.dto.admin.response.UtilisateurResponse;
import com.marketplace.dto.common.LoginRequest;
import com.marketplace.dto.common.LoginResponse;
import com.marketplace.entities.Roles;
import com.marketplace.entities.Utilisateurs;
import com.marketplace.utils.PasswordUtil;
import com.marketplace.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service d'authentification Admin
 * Note: Pas de @Stateless - gère son propre EntityManager via JpaUtil
 */
public class AdminAuthService {
    private static final Logger logger = Logger.getLogger(AdminAuthService.class.getName());
    
    private AdminUtilisateurDAO utilisateurDAO;
    private RoleDAO roleDAO;
    private EntityManager em;
    
    /**
     * Constructeur - crée l'EntityManager et les DAOs
     */
    public AdminAuthService() {
        try {
            this.em = JpaUtil.getEntityManager();
            this.utilisateurDAO = new AdminUtilisateurDAO(em);
            this.roleDAO = new RoleDAO(em);
            logger.info("✅ AdminAuthService initialisé");
        } catch (Exception e) {
            logger.severe("❌ Erreur init AdminAuthService: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Authentification Admin
     */
    public LoginResponse loginAdmin(LoginRequest request) {
        logger.info("Tentative login: " + request.getEmail());
        
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return new LoginResponse(false, "Email obligatoire", null, null);
        }
        
        if (request.getMotDePasse() == null || request.getMotDePasse().isEmpty()) {
            return new LoginResponse(false, "Mot de passe obligatoire", null, null);
        }
        
        Optional<Utilisateurs> optUser = utilisateurDAO.findByEmail(request.getEmail());
        
        if (!optUser.isPresent()) {
            logger.warning("Email non trouvé: " + request.getEmail());
            return new LoginResponse(false, "Email ou mot de passe incorrect", null, null);
        }
        
        Utilisateurs utilisateur = optUser.get();
        
        if (utilisateur.getRole() == null || !"ADMIN".equals(utilisateur.getRole().getNomRole())) {
            logger.warning("Accès non admin: " + request.getEmail());
            return new LoginResponse(false, "Accès non autorisé. Réservé aux administrateurs.", null, null);
        }
        
        if (!PasswordUtil.verifyPassword(request.getMotDePasse(), utilisateur.getMotDePasse())) {
            logger.warning("Mot de passe incorrect: " + request.getEmail());
            return new LoginResponse(false, "Email ou mot de passe incorrect", null, null);
        }
        
        if (!utilisateur.getEstActif()) {
            logger.warning("Compte désactivé: " + request.getEmail());
            return new LoginResponse(false, "Compte désactivé. Contactez un administrateur.", null, null);
        }
        
        utilisateur.setDateDerniereConnexion(LocalDateTime.now());
        utilisateurDAO.update(utilisateur);
        
        UtilisateurResponse userResponse = UtilisateurResponse.fromEntity(utilisateur);
        logger.info("✅ Login réussi: " + request.getEmail());
        
        return new LoginResponse(true, "Connexion réussie", userResponse, null);
    }
    
    /**
     * Créer un nouvel utilisateur (par admin)
     */
    public UtilisateurResponse creerUtilisateur(CreateUtilisateurRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email obligatoire");
        }
        
        if (request.getMotDePasse() == null || request.getMotDePasse().isEmpty()) {
            throw new IllegalArgumentException("Mot de passe obligatoire");
        }
        
        if (request.getNom() == null || request.getNom().isEmpty()) {
            throw new IllegalArgumentException("Nom obligatoire");
        }
        
        if (request.getPrenom() == null || request.getPrenom().isEmpty()) {
            throw new IllegalArgumentException("Prénom obligatoire");
        }
        
        if (utilisateurDAO.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Cet email est déjà utilisé");
        }
        
        Utilisateurs utilisateur = new Utilisateurs();
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setTelephone(request.getTelephone());
        utilisateur.setDateNaissance(request.getDateNaissance());
        utilisateur.setGenre(request.getGenre());
        
        String hashedPassword = PasswordUtil.hashPassword(request.getMotDePasse());
        utilisateur.setMotDePasse(hashedPassword);
        
        if (request.getIdRole() != null) {
            Optional<Roles> role = roleDAO.findById(request.getIdRole());
            if (role.isPresent()) {
                utilisateur.setRole(role.get());
            } else {
                throw new IllegalArgumentException("Rôle non trouvé");
            }
        } else {
            Optional<Roles> roleClient = roleDAO.findByNom("CLIENT");
            if (roleClient.isPresent()) {
                utilisateur.setRole(roleClient.get());
            }
        }
        
        Utilisateurs created = utilisateurDAO.create(utilisateur);
        logger.info("✅ Utilisateur créé: " + created.getEmail());
        
        return UtilisateurResponse.fromEntity(created);
    }
}