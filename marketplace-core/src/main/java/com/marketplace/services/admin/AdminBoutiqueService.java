package com.marketplace.services.admin;

import com.marketplace.dao.admin.AdminBoutiqueDAO;
import com.marketplace.entities.Boutiques;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class AdminBoutiqueService {
    
    @EJB
    private AdminBoutiqueDAO boutiqueDAO;
    
    public List<Boutiques> getAllBoutiques() {
        return boutiqueDAO.findAll();
    }
    
    public Boutiques getBoutiqueById(Long id) {
        return boutiqueDAO.findById(id)
            .orElseThrow(() -> new RuntimeException("Boutique non trouvée"));
    }
    
    public List<Boutiques> getBoutiquesActives() {
        return boutiqueDAO.findActives();
    }
    
    public List<Boutiques> getBoutiquesEnAttente() {
        return boutiqueDAO.findEnAttente();
    }
    
    public List<Boutiques> getBoutiquesVerifiees() {
        return boutiqueDAO.findVerifiees();
    }
    
    /**
     * Valider une boutique
     */
    public Boutiques validerBoutique(Long id) {
        Boutiques boutique = boutiqueDAO.findById(id)
            .orElseThrow(() -> new RuntimeException("Boutique non trouvée"));
        
        boutique.setEstVerifiee(true);
        boutique.setDateVerification(LocalDateTime.now());
        
        return boutiqueDAO.update(boutique);
    }
    
    /**
     * Activer/Désactiver une boutique
     */
    public Boutiques toggleActif(Long id) {
        Boutiques boutique = boutiqueDAO.findById(id)
            .orElseThrow(() -> new RuntimeException("Boutique non trouvée"));
        
        boutique.setEstActive(!boutique.getEstActive());
        
        return boutiqueDAO.update(boutique);
    }
    
    /**
     * Supprimer une boutique
     */
    public void supprimerBoutique(Long id) {
        boutiqueDAO.delete(id);
    }
    
    public Long countBoutiques() {
        return boutiqueDAO.countAll();
    }
    
    public Long countBoutiquesActives() {
        return boutiqueDAO.countActives();
    }
    
    public Long countBoutiquesEnAttente() {
        return boutiqueDAO.countEnAttente();
    }
}