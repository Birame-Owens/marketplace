package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "marques")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marques implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marque")
    private Long idMarque;
    
    @Column(name = "nom_marque", unique = true, nullable = false, length = 100)
    private String nomMarque;
    
    @Column(length = 500)
    private String logo;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "site_web", length = 255)
    private String siteWeb;
    
    @Column(name = "est_active")
    private Boolean estActive = true;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @OneToMany(mappedBy = "marque", fetch = FetchType.LAZY)
    private List<Produits> produits;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (estActive == null) estActive = true;
    }
}