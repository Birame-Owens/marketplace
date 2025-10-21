package com.marketplace.api.controllers.admin;

import com.marketplace.dto.admin.response.AdminDashboardResponse;
import com.marketplace.dto.admin.response.StatistiqueResponse;
import com.marketplace.dto.common.ApiResponse;
import com.marketplace.services.admin.AdminStatistiqueService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Controller pour le dashboard admin
 * 
 * Endpoints:
 * - GET /api/admin/dashboard              - Vue d'ensemble
 * - GET /api/admin/dashboard/statistiques - Statistiques détaillées
 */
@Path("/admin/dashboard")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminDashboardController {
    
    @EJB
    private AdminStatistiqueService statistiqueService;
    
    /**
     * Récupérer le dashboard principal
     * GET /api/admin/dashboard
     * 
     * Retourne les statistiques essentielles pour l'affichage principal
     */
    @GET
    public Response getDashboard() {
        try {
            AdminDashboardResponse dashboard = statistiqueService.getDashboard();
            return Response.ok(ApiResponse.success(dashboard)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur lors de la récupération du dashboard : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupérer les statistiques détaillées
     * GET /api/admin/dashboard/statistiques
     * 
     * Retourne toutes les statistiques incluant le chiffre d'affaires
     */
    @GET
    @Path("/statistiques")
    public Response getStatistiques() {
        try {
            StatistiqueResponse stats = statistiqueService.getStatistiques();
            return Response.ok(ApiResponse.success(stats)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur lors de la récupération des statistiques : " + e.getMessage()))
                .build();
        }
    }
    
    /**
     * Test de connectivité API
     * GET /api/admin/dashboard/ping
     */
    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok(ApiResponse.success("Dashboard API opérationnel", null)).build();
    }
}