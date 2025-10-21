package com.marketplace.services.admin;

import com.marketplace.dao.admin.AdminUtilisateurDAO;
import com.marketplace.dto.admin.response.UtilisateurResponse;
import com.marketplace.entities.Utilisateurs;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AdminUtilisateurService {
    
    @EJB
    private AdminUtilisateurDAO utilisateurDAO;
    
    public List<UtilisateurResponse> getAllUtilisateurs() {
        return utilisateurDAO.findAll().stream()
            .map(UtilisateurResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    public UtilisateurResponse getUtilisateurById(Long id) {
        Utilisateurs utilisateur = utilisateurDAO.findById(id)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return UtilisateurResponse.fromEntity(utilisateur);
    }
    
    public List<UtilisateurResponse> getUtilisateursByRole(String nomRole) {
        return utilisateurDAO.findByRole(nomRole).stream()
            .map(UtilisateurResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    public List<UtilisateurResponse> getUtilisateursActifs() {
        return utilisateurDAO.findActifs().stream()
            .map(UtilisateurResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    public List<UtilisateurResponse> getUtilisateursInactifs() {
        return utilisateurDAO.findInactifs().stream()
            .map(UtilisateurResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    public UtilisateurResponse toggleActif(Long id) {
        Utilisateurs utilisateur = utilisateurDAO.findById(id)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        utilisateur.setEstActif(!utilisateur.getEstActif());
        Utilisateurs updated = utilisateurDAO.update(utilisateur);
        
        return UtilisateurResponse.fromEntity(updated);
    }
    
    public void supprimerUtilisateur(Long id) {
        utilisateurDAO.delete(id);
    }
    
    public Long countUtilisateurs() {
        return utilisateurDAO.countAll();
    }
    
    public Long countUtilisateursByRole(String nomRole) {
        return utilisateurDAO.countByRole(nomRole);
    }
    
    public Long countActifs() {
        return utilisateurDAO.countActifs();
    }
}