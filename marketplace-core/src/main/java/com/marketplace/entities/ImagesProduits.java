package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "images_produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagesProduits implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private Long idImage;
    
    @Column(name = "url_image", nullable = false, length = 500)
    private String urlImage;
    
    @Column(name = "alt_text", length = 255)
    private String altText;
    
    @Column
    private Integer ordre = 0;
    
    @Column(name = "est_principale")
    private Boolean estPrincipale = false;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produits produit;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (ordre == null) ordre = 0;
        if (estPrincipale == null) estPrincipale = false;
    }
}