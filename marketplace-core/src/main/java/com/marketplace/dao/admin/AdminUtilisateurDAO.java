package com.marketplace.dao.admin;

import com.marketplace.entities.Utilisateurs;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class AdminUtilisateurDAO {
    private static final Logger logger = Logger.getLogger(AdminUtilisateurDAO.class.getName());
    private EntityManager em;
    
    /**
     * Constructeur - reçoit l'EntityManager
     */
    public AdminUtilisateurDAO(EntityManager em) {
        this.em = em;
    }
    
    public Utilisateurs create(Utilisateurs utilisateur) {
        try {
            em.getTransaction().begin();
            em.persist(utilisateur);
            em.flush();
            em.getTransaction().commit();
            logger.info("✅ Utilisateur créé: " + utilisateur.getEmail());
            return utilisateur;
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.severe("❌ Erreur create: " + e.getMessage());
            throw e;
        }
    }
    
    public Utilisateurs update(Utilisateurs utilisateur) {
        try {
            em.getTransaction().begin();
            Utilisateurs merged = em.merge(utilisateur);
            em.getTransaction().commit();
            logger.info("✅ Utilisateur modifié: " + utilisateur.getEmail());
            return merged;
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.severe("❌ Erreur update: " + e.getMessage());
            throw e;
        }
    }
    
    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            Utilisateurs utilisateur = em.find(Utilisateurs.class, id);
            if (utilisateur != null) {
                em.remove(utilisateur);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.severe("❌ Erreur delete: " + e.getMessage());
            throw e;
        }
    }
    
    public Optional<Utilisateurs> findById(Long id) {
        return Optional.ofNullable(em.find(Utilisateurs.class, id));
    }
    
    public List<Utilisateurs> findAll() {
        TypedQuery<Utilisateurs> query = em.createQuery(
            "SELECT u FROM Utilisateurs u ORDER BY u.dateCreation DESC",
            Utilisateurs.class
        );
        return query.getResultList();
    }
    
    public Optional<Utilisateurs> findByEmail(String email) {
        try {
            TypedQuery<Utilisateurs> query = em.createQuery(
                "SELECT u FROM Utilisateurs u WHERE u.email = :email",
                Utilisateurs.class
            );
            query.setParameter("email", email);
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    public List<Utilisateurs> findByRole(String nomRole) {
        TypedQuery<Utilisateurs> query = em.createQuery(
            "SELECT u FROM Utilisateurs u WHERE u.role.nomRole = :nomRole " +
            "ORDER BY u.dateCreation DESC",
            Utilisateurs.class
        );
        query.setParameter("nomRole", nomRole);
        return query.getResultList();
    }
    
    public List<Utilisateurs> findActifs() {
        TypedQuery<Utilisateurs> query = em.createQuery(
            "SELECT u FROM Utilisateurs u WHERE u.estActif = true " +
            "ORDER BY u.dateCreation DESC",
            Utilisateurs.class
        );
        return query.getResultList();
    }
    
    public List<Utilisateurs> findInactifs() {
        TypedQuery<Utilisateurs> query = em.createQuery(
            "SELECT u FROM Utilisateurs u WHERE u.estActif = false " +
            "ORDER BY u.dateCreation DESC",
            Utilisateurs.class
        );
        return query.getResultList();
    }
    
    public Long countAll() {
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(u) FROM Utilisateurs u",
            Long.class
        );
        return query.getSingleResult();
    }
    
    public Long countByRole(String nomRole) {
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(u) FROM Utilisateurs u WHERE u.role.nomRole = :nomRole",
            Long.class
        );
        query.setParameter("nomRole", nomRole);
        return query.getSingleResult();
    }
    
    public Long countActifs() {
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(u) FROM Utilisateurs u WHERE u.estActif = true",
            Long.class
        );
        return query.getSingleResult();
    }
}