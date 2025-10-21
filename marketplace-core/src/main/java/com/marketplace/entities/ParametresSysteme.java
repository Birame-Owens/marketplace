package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "parametres_systeme")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametresSysteme implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametre")
    private Long idParametre;
    
    @Column(name = "cle_parametre", unique = true, nullable = false, length = 100)
    private String cleParametre;
    
    @Column(name = "valeur_parametre", columnDefinition = "TEXT")
    private String valeurParametre;
    
    @Column(name = "type_parametre", length = 50)
    private String typeParametre;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @PrePersist
    protected void onCreate() {
        dateModification = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}