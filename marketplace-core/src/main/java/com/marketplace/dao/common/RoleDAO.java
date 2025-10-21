package com.marketplace.dao.common;

import com.marketplace.entities.Roles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class RoleDAO {
    private static final Logger logger = Logger.getLogger(RoleDAO.class.getName());
    private EntityManager em;
    
    /**
     * Constructeur - reçoit l'EntityManager
     */
    public RoleDAO(EntityManager em) {
        this.em = em;
    }
    
    public Roles create(Roles role) {
        try {
            em.getTransaction().begin();
            em.persist(role);
            em.flush();
            em.getTransaction().commit();
            return role;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
    
    public Optional<Roles> findById(Long id) {
        return Optional.ofNullable(em.find(Roles.class, id));
    }
    
    public List<Roles> findAll() {
        TypedQuery<Roles> query = em.createQuery(
            "SELECT r FROM Roles r ORDER BY r.nomRole",
            Roles.class
        );
        return query.getResultList();
    }
    
    public Optional<Roles> findByNom(String nomRole) {
        try {
            TypedQuery<Roles> query = em.createQuery(
                "SELECT r FROM Roles r WHERE r.nomRole = :nomRole",
                Roles.class
            );
            query.setParameter("nomRole", nomRole);
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}