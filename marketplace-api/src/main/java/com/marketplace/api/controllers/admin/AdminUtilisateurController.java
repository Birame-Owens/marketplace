package com.marketplace.api.controllers.admin;

import com.marketplace.dto.admin.response.UtilisateurResponse;
import com.marketplace.dto.common.ApiResponse;
import com.marketplace.services.admin.AdminUtilisateurService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller pour la gestion des utilisateurs par l'admin
 * 
 * Endpoints:
 * - GET    /api/admin/utilisateurs              - Liste tous les utilisateurs
 * - GET    /api/admin/utilisateurs/{id}         - Détails d'un utilisateur
 * - GET    /api/admin/utilisateurs/role/{role}  - Utilisateurs par rôle
 * - GET    /api/admin/utilisateurs/stats        - Statistiques utilisateurs
 * - PUT    /api/admin/utilisateurs/{id}/toggle  - Activer/désactiver
 * - DELETE /api/admin/utilisateurs/{id}         - Supprimer
 */
@Path("/admin/utilisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminUtilisateurController {
    
    @EJB
    private AdminUtilisateurService utilisateurService;
    
    /**
     * Récupérer tous les utilisateurs
     * GET /api/admin/utilisateurs
     */
    @GET
    public Response getAllUtilisateurs() {
        try {
            List<UtilisateurResponse> utilisateurs = utilisateurService.getAllUtilisateurs();
            return Response.ok(ApiResponse.success(utilisateurs)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupérer un utilisateur par ID
     * GET /api/admin/utilisateurs/{id}
     */
    @GET
    @Path("/{id}")
    public Response getUtilisateurById(@PathParam("id") Long id) {
        try {
            UtilisateurResponse utilisateur = utilisateurService.getUtilisateurById(id);
            return Response.ok(ApiResponse.success(utilisateur)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(ApiResponse.error("Utilisateur non trouvé"))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupérer les utilisateurs par rôle
     * GET /api/admin/utilisateurs/role/{role}
     * Exemples: /api/admin/utilisateurs/role/ADMIN
     *           /api/admin/utilisateurs/role/GERANT
     *           /api/admin/utilisateurs/role/CLIENT
     */
    @GET
    @Path("/role/{role}")
    public Response getUtilisateursByRole(@PathParam("role") String role) {
        try {
            List<UtilisateurResponse> utilisateurs = utilisateurService.getUtilisateursByRole(role);
            return Response.ok(ApiResponse.success(utilisateurs)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupérer les utilisateurs actifs
     * GET /api/admin/utilisateurs/actifs
     */
    @GET
    @Path("/actifs")
    public Response getUtilisateursActifs() {
        try {
            List<UtilisateurResponse> utilisateurs = utilisateurService.getUtilisateursActifs();
            return Response.ok(ApiResponse.success(utilisateurs)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupérer les utilisateurs inactifs
     * GET /api/admin/utilisateurs/inactifs
     */
    @GET
    @Path("/inactifs")
    public Response getUtilisateursInactifs() {
        try {
            List<UtilisateurResponse> utilisateurs = utilisateurService.getUtilisateursInactifs();
            return Response.ok(ApiResponse.success(utilisateurs)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Activer/Désactiver un utilisateur
     * PUT /api/admin/utilisateurs/{id}/toggle-actif
     */
    @PUT
    @Path("/{id}/toggle-actif")
    public Response toggleActif(@PathParam("id") Long id) {
        try {
            UtilisateurResponse utilisateur = utilisateurService.toggleActif(id);
            String message = utilisateur.getEstActif() 
                ? "Utilisateur activé avec succès" 
                : "Utilisateur désactivé avec succès";
            return Response.ok(ApiResponse.success(message, utilisateur)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(ApiResponse.error("Utilisateur non trouvé"))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Supprimer un utilisateur
     * DELETE /api/admin/utilisateurs/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response supprimerUtilisateur(@PathParam("id") Long id) {
        try {
            utilisateurService.supprimerUtilisateur(id);
            return Response.ok(ApiResponse.success("Utilisateur supprimé avec succès", null)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(ApiResponse.error("Utilisateur non trouvé"))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Statistiques des utilisateurs
     * GET /api/admin/utilisateurs/stats
     * 
     * Retourne:
     * {
     *   "total": 150,
     *   "admins": 5,
     *   "gerants": 20,
     *   "clients": 125,
     *   "actifs": 140
     * }
     */
    @GET
    @Path("/stats")
    public Response getStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("total", utilisateurService.countUtilisateurs());
            stats.put("admins", utilisateurService.countUtilisateursByRole("ADMIN"));
            stats.put("gerants", utilisateurService.countUtilisateursByRole("GERANT"));
            stats.put("clients", utilisateurService.countUtilisateursByRole("CLIENT"));
            stats.put("actifs", utilisateurService.countActifs());
            
            return Response.ok(ApiResponse.success(stats)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
}