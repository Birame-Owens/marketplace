package com.marketplace.api.controllers.admin;

import com.marketplace.api.factory.ServiceFactory;
import com.marketplace.dto.admin.request.CreateUtilisateurRequest;
import com.marketplace.dto.admin.response.UtilisateurResponse;
import com.marketplace.dto.common.ApiResponse;
import com.marketplace.dto.common.LoginRequest;
import com.marketplace.dto.common.LoginResponse;
import com.marketplace.services.admin.AdminAuthService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/admin/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminAuthController {

    private static final Logger logger = Logger.getLogger(AdminAuthController.class.getName());
    private AdminAuthService authService;

    public AdminAuthController() {
        this.authService = ServiceFactory.getAdminAuthService();
        logger.info("✅ AdminAuthController initialisé avec AdminAuthService");
    }

    @GET
    @Path("/status")
    public Response getStatus() {
        logger.info("🔵 GET /status appelé");
        return Response.ok(ApiResponse.success("API Admin active", null)).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        logger.info("🔴 POST /login appelé - Email: " + request.getEmail());
        try {
            LoginResponse response = authService.loginAdmin(request);
            if (response.isSuccess()) {
                return Response.ok(response).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
            }
        } catch (Exception e) {
            logger.severe("❌ Erreur login: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur serveur : " + e.getMessage()))
                .build();
        }
    }

    @POST
    @Path("/create-user")
    public Response createUser(CreateUtilisateurRequest request) {
        logger.info("🟡 POST /create-user appelé");
        try {
            UtilisateurResponse utilisateur = authService.creerUtilisateur(request);
            return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success("Utilisateur créé avec succès", utilisateur))
                .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(ApiResponse.error(e.getMessage()))
                .build();
        } catch (Exception e) {
            logger.severe("❌ Erreur create-user: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Erreur serveur : " + e.getMessage()))
                .build();
        }
    }
}