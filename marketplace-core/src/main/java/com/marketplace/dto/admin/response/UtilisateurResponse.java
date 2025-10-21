package com.marketplace.dto.admin.response;

import com.marketplace.entities.Utilisateurs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurResponse {
    private Long idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String photoProfil;
    private LocalDate dateNaissance;
    private String genre;
    private Boolean estActif;
    private Boolean estVerifie;
    private LocalDateTime dateDerniereConnexion;
    private LocalDateTime dateCreation;
    private String nomRole;
    private Long idRole;
    
    public static UtilisateurResponse fromEntity(Utilisateurs utilisateur) {
        if (utilisateur == null) return null;
        
        UtilisateurResponse dto = new UtilisateurResponse();
        dto.setIdUtilisateur(utilisateur.getIdUtilisateur());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        dto.setEmail(utilisateur.getEmail());
        dto.setTelephone(utilisateur.getTelephone());
        dto.setPhotoProfil(utilisateur.getPhotoProfil());
        dto.setDateNaissance(utilisateur.getDateNaissance());
        dto.setGenre(utilisateur.getGenre());
        dto.setEstActif(utilisateur.getEstActif());
        dto.setEstVerifie(utilisateur.getEstVerifie());
        dto.setDateDerniereConnexion(utilisateur.getDateDerniereConnexion());
        dto.setDateCreation(utilisateur.getDateCreation());
        
        if (utilisateur.getRole() != null) {
            dto.setNomRole(utilisateur.getRole().getNomRole());
            dto.setIdRole(utilisateur.getRole().getIdRole());
        }
        
        return dto;
    }
}