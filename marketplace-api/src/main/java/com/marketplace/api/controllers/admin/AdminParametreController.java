package com.marketplace.api.controllers.admin;

import com.marketplace.dto.common.ApiResponse;
import com.marketplace.entities.ParametresSysteme;
import com.marketplace.services.admin.AdminParametreService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Controller pour la gestion des paramètres système
 * 
 * Endpoints:
 * - GET    /api/admin/parametres           - Liste tous les paramètres
 * - GET    /api/admin/parametres/{id}      - Détails d'un paramètre
 * - GET    /api/admin/parametres/cle/{cle} - Paramètre par clé
 * - POST   /api/admin/parametres           - Créer un paramètre
 * - PUT    /api/admin/parametres/{id}      - Modifier un paramètre
 * - DELETE /api/admin/parametres/{id}      - Supprimer un paramètre
 */
@Path("/admin/parametres")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminParametreController {
    
    @EJB
    private AdminParametreService parametreService;
    
    /**
     * Récupérer tous les paramètres
     * GET /api/admin/parametres
     */
    @GET
    public Response getAllParametres() {
        try {
            List<ParametresSysteme> parametres = parametreService.getAllParametres();
            return Response.ok(ApiResponse.success(parametres)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupérer un paramètre par ID
     * GET /api/admin/parametres/{id}
     */
    @GET
    @Path("/{id}")
    public Response getParametreById(@PathParam("id") Long id) {
        try {
            ParametresSysteme parametre = parametreService.getParametreById(id);
            return Response.ok(ApiResponse.success(parametre)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(ApiResponse.error("Paramètre non trouvé"))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupérer un paramètre par clé
     * GET /api/admin/parametres/cle/{cle}
     * Exemple: /api/admin/parametres/cle/maintenance_mode
     */
    @GET
    @Path("/cle/{cle}")
    public Response getParametreByCle(@PathParam("cle") String cle) {
        try {
            ParametresSysteme parametre = parametreService.getParametreByCle(cle);
            return Response.ok(ApiResponse.success(parametre)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(ApiResponse.error("Paramètre non trouvé"))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Créer un nouveau paramètre
     * POST /api/admin/parametres
     */
    @POST
    public Response creerParametre(ParametresSysteme parametre) {
        try {
            ParametresSysteme created = parametreService.creerParametre(parametre);
            return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success("Paramètre créé avec succès", created))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Modifier un paramètre
     * PUT /api/admin/parametres/{id}
     */
    @PUT
    @Path("/{id}")
    public Response updateParametre(@PathParam("id") Long id, ParametresSysteme parametre) {
        try {
            parametre.setIdParametre(id);
            ParametresSysteme updated = parametreService.updateParametre(parametre);
            return Response.ok(ApiResponse.success("Paramètre modifié avec succès", updated)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(ApiResponse.error("Paramètre non trouvé"))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Supprimer un paramètre
     * DELETE /api/admin/parametres/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response supprimerParametre(@PathParam("id") Long id) {
        try {
            parametreService.supprimerParametre(id);
            return Response.ok(ApiResponse.success("Paramètre supprimé avec succès", null)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(ApiResponse.error("Paramètre non trouvé"))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur : " + e.getMessage()))
                .build();
        }
    }
}