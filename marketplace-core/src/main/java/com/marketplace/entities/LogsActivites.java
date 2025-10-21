package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs_activites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogsActivites implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Long idLog;
    
    @Column(name = "type_action", nullable = false, length = 100)
    private String typeAction;
    
    @Column(name = "entite_cible", length = 100)
    private String entiteCible;
    
    @Column(name = "id_entite")
    private Integer idEntite;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "adresse_ip", length = 50)
    private String adresseIp;
    
    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;
    
    @Column(name = "date_action")
    private LocalDateTime dateAction;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateurs utilisateur;
    
    @PrePersist
    protected void onCreate() {
        dateAction = LocalDateTime.now();
    }
}