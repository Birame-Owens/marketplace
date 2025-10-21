package com.marketplace.dao.admin;

import com.marketplace.entities.ParametresSysteme;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class AdminParametreDAO {
    
    @PersistenceContext(unitName = "marketplacePU")
    private EntityManager em;
    
    public ParametresSysteme create(ParametresSysteme parametre) {
        em.persist(parametre);
        em.flush();
        return parametre;
    }
    
    public ParametresSysteme update(ParametresSysteme parametre) {
        return em.merge(parametre);
    }
    
    public void delete(Long id) {
        ParametresSysteme parametre = em.find(ParametresSysteme.class, id);
        if (parametre != null) {
            em.remove(parametre);
        }
    }
    
    public Optional<ParametresSysteme> findById(Long id) {
        return Optional.ofNullable(em.find(ParametresSysteme.class, id));
    }
    
    public List<ParametresSysteme> findAll() {
        TypedQuery<ParametresSysteme> query = em.createQuery(
            "SELECT p FROM ParametresSysteme p ORDER BY p.cleParametre", 
            ParametresSysteme.class
        );
        return query.getResultList();
    }
    
    public Optional<ParametresSysteme> findByCle(String cle) {
        try {
            TypedQuery<ParametresSysteme> query = em.createQuery(
                "SELECT p FROM ParametresSysteme p WHERE p.cleParametre = :cle", 
                ParametresSysteme.class
            );
            query.setParameter("cle", cle);
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    public List<ParametresSysteme> findByType(String type) {
        TypedQuery<ParametresSysteme> query = em.createQuery(
            "SELECT p FROM ParametresSysteme p WHERE p.typeParametre = :type " +
            "ORDER BY p.cleParametre", 
            ParametresSysteme.class
        );
        query.setParameter("type", type);
        return query.getResultList();
    }
}