package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "categories_boutiques")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesBoutiques implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie_boutique")
    private Long idCategorieBoutique;
    
    @Column(name = "nom_categorie", nullable = false, length = 100)
    private String nomCategorie;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 255)
    private String icone;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToMany(mappedBy = "categories")
    private List<Boutiques> boutiques;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}