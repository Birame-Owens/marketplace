package com.marketplace.api.controllers.admin;

import com.marketplace.dto.common.ApiResponse;
import com.marketplace.entities.Boutiques;
import com.marketplace.services.admin.AdminBoutiqueService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller pour la gestion des boutiques par l'admin
 * 
 * Endpoints:
 * - GET    /api/admin/boutiques                - Liste toutes les boutiques
 * - GET    /api/admin/boutiques/{id}           - Détails d'une boutique
 * - GET    /api/admin/boutiques/actives        - Boutiques actives
 * - GET    /api/admin/boutiques/en-attente     - Boutiques en attente
 * - GET    /api/admin/boutiques/stats          - Statistiques
 * - PUT    /api/admin/boutiques/{id}/valider   - Valider une boutique
 * - PUT    /api/admin/boutiques/{id}/toggle    - Activer/désactiver
 * - DELETE /api/admin/boutiques/{id}           - Supprimer
 */
@Path("/admin/boutiques")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminBoutiqueController {
    
    @EJB
    private AdminBoutiqueService boutiqueService;
    
    /**
     * Récupérer toutes les boutiques
     * GET /api/admin/boutiques
     */
    @GET
    public Response getAllBoutiques() {
        try {
            List<Boutiques> boutiques = boutiqueService.getAllBoutiques();
            return Response.ok(ApiResponse.success(boutiques)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupérer une boutique par ID
     * GET /api/admin/boutiques/{id}
     */
    @GET
    @Path("/{id}")
    public Response getBoutiqueById(@PathParam("id") Long id) {
        try {
            Boutiques boutique = boutiqueService.getBoutiqueById(id);
            return Response.ok(ApiResponse.success(boutique)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(ApiResponse.error("Boutique non trouvée"))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupérer les boutiques actives
     * GET /api/admin/boutiques/actives
     */
    @GET
    @Path("/actives")
    public Response getBoutiquesActives() {
        try {
            List<Boutiques> boutiques = boutiqueService.getBoutiquesActives();
            return Response.ok(ApiResponse.success(boutiques)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupérer les boutiques en attente de validation
     * GET /api/admin/boutiques/en-attente
     */
    @GET
    @Path("/en-attente")
    public Response getBoutiquesEnAttente() {
        try {
            List<Boutiques> boutiques = boutiqueService.getBoutiquesEnAttente();
            return Response.ok(ApiResponse.success(boutiques)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupérer les boutiques vérifiées
     * GET /api/admin/boutiques/verifiees
     */
    @GET
    @Path("/verifiees")
    public Response getBoutiquesVerifiees() {
        try {
            List<Boutiques> boutiques = boutiqueService.getBoutiquesVerifiees();
            return Response.ok(ApiResponse.success(boutiques)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Valider une boutique
     * PUT /api/admin/boutiques/{id}/valider
     */
    @PUT
    @Path("/{id}/valider")
    public Response validerBoutique(@PathParam("id") Long id) {
        try {
            Boutiques boutique = boutiqueService.validerBoutique(id);
            return Response.ok(ApiResponse.success("Boutique validée avec succès", boutique)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(ApiResponse.error("Boutique non trouvée"))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Activer/Désactiver une boutique
     * PUT /api/admin/boutiques/{id}/toggle-actif
     */
    @PUT
    @Path("/{id}/toggle-actif")
    public Response toggleActif(@PathParam("id") Long id) {
        try {
            Boutiques boutique = boutiqueService.toggleActif(id);
            String message = boutique.getEstActive() 
                ? "Boutique activée avec succès" 
                : "Boutique désactivée avec succès";
            return Response.ok(ApiResponse.success(message, boutique)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(ApiResponse.error("Boutique non trouvée"))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Supprimer une boutique
     * DELETE /api/admin/boutiques/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response supprimerBoutique(@PathParam("id") Long id) {
        try {
            boutiqueService.supprimerBoutique(id);
            return Response.ok(ApiResponse.success("Boutique supprimée avec succès", null)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(ApiResponse.error("Boutique non trouvée"))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Statistiques des boutiques
     * GET /api/admin/boutiques/stats
     */
    @GET
    @Path("/stats")
    public Response getStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("total", boutiqueService.countBoutiques());
            stats.put("actives", boutiqueService.countBoutiquesActives());
            stats.put("enAttente", boutiqueService.countBoutiquesEnAttente());
            
            return Response.ok(ApiResponse.success(stats)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
}