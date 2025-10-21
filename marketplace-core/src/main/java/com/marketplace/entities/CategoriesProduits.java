package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "categories_produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesProduits implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie")
    private Long idCategorie;
    
    @Column(name = "nom_categorie", nullable = false, length = 100)
    private String nomCategorie;
    
    @Column(unique = true, nullable = false, length = 100)
    private String slug;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 500)
    private String image;
    
    @Column(length = 255)
    private String icone;
    
    @Column
    private Integer ordre = 0;
    
    @Column(name = "est_active")
    private Boolean estActive = true;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parent")
    private CategoriesProduits parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CategoriesProduits> sousCategories;
    
    @OneToMany(mappedBy = "categorie", fetch = FetchType.LAZY)
    private List<Produits> produits;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (ordre == null) ordre = 0;
        if (estActive == null) estActive = true;
    }
}