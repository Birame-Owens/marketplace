package com.marketplace.services.admin;

import com.marketplace.dao.admin.AdminParametreDAO;
import com.marketplace.entities.ParametresSysteme;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class AdminParametreService {
    
    @EJB
    private AdminParametreDAO parametreDAO;
    
    public List<ParametresSysteme> getAllParametres() {
        return parametreDAO.findAll();
    }
    
    public ParametresSysteme getParametreById(Long id) {
        return parametreDAO.findById(id)
            .orElseThrow(() -> new RuntimeException("Paramètre non trouvé"));
    }
    
    public ParametresSysteme getParametreByCle(String cle) {
        return parametreDAO.findByCle(cle)
            .orElseThrow(() -> new RuntimeException("Paramètre non trouvé"));
    }
    
    public ParametresSysteme creerParametre(ParametresSysteme parametre) {
        return parametreDAO.create(parametre);
    }
    
    public ParametresSysteme updateParametre(ParametresSysteme parametre) {
        return parametreDAO.update(parametre);
    }
    
    public void supprimerParametre(Long id) {
        parametreDAO.delete(id);
    }
    
    public String getValeurParametre(String cle, String defaultValue) {
        try {
            ParametresSysteme param = getParametreByCle(cle);
            return param.getValeurParametre() != null ? param.getValeurParametre() : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }
}