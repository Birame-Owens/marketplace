package com.marketplace.dao.admin;

import com.marketplace.entities.Boutiques;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class AdminBoutiqueDAO {
    
    @PersistenceContext(unitName = "marketplacePU")
    private EntityManager em;
    
    public Boutiques create(Boutiques boutique) {
        em.persist(boutique);
        em.flush();
        return boutique;
    }
    
    public Boutiques update(Boutiques boutique) {
        return em.merge(boutique);
    }
    
    public void delete(Long id) {
        Boutiques boutique = em.find(Boutiques.class, id);
        if (boutique != null) {
            em.remove(boutique);
        }
    }
    
    public Optional<Boutiques> findById(Long id) {
        return Optional.ofNullable(em.find(Boutiques.class, id));
    }
    
    public List<Boutiques> findAll() {
        TypedQuery<Boutiques> query = em.createQuery(
            "SELECT b FROM Boutiques b ORDER BY b.dateCreation DESC", 
            Boutiques.class
        );
        return query.getResultList();
    }
    
    public List<Boutiques> findActives() {
        TypedQuery<Boutiques> query = em.createQuery(
            "SELECT b FROM Boutiques b WHERE b.estActive = true " +
            "ORDER BY b.dateCreation DESC", 
            Boutiques.class
        );
        return query.getResultList();
    }
    
    public List<Boutiques> findEnAttente() {
        TypedQuery<Boutiques> query = em.createQuery(
            "SELECT b FROM Boutiques b WHERE b.estVerifiee = false " +
            "ORDER BY b.dateCreation DESC", 
            Boutiques.class
        );
        return query.getResultList();
    }
    
    public List<Boutiques> findVerifiees() {
        TypedQuery<Boutiques> query = em.createQuery(
            "SELECT b FROM Boutiques b WHERE b.estVerifiee = true " +
            "ORDER BY b.dateCreation DESC", 
            Boutiques.class
        );
        return query.getResultList();
    }
    
    public Long countAll() {
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(b) FROM Boutiques b", 
            Long.class
        );
        return query.getSingleResult();
    }
    
    public Long countActives() {
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(b) FROM Boutiques b WHERE b.estActive = true", 
            Long.class
        );
        return query.getSingleResult();
    }
    
    public Long countEnAttente() {
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(b) FROM Boutiques b WHERE b.estVerifiee = false", 
            Long.class
        );
        return query.getSingleResult();
    }
    
    public Optional<Boutiques> findBySlug(String slug) {
        try {
            TypedQuery<Boutiques> query = em.createQuery(
                "SELECT b FROM Boutiques b WHERE b.slug = :slug", 
                Boutiques.class
            );
            query.setParameter("slug", slug);
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}