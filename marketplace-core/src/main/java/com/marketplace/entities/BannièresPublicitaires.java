package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "bannières_publicitaires")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannièresPublicitaires implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banniere")
    private Long idBanniere;
    
    @Column(length = 255)
    private String titre;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "image_banniere", length = 500)
    private String imageBanniere;
    
    @Column(name = "lien_cible", length = 500)
    private String lienCible;
    
    @Column(length = 50)
    private String position;
    
    @Column
    private Integer ordre = 0;
    
    @Column(name = "est_active")
    private Boolean estActive = true;
    
    @Column(name = "date_debut")
    private LocalDateTime dateDebut;
    
    @Column(name = "date_fin")
    private LocalDateTime dateFin;
    
    @Column(name = "nombre_clics")
    private Integer nombreClics = 0;
    
    @Column(name = "nombre_vues")
    private Integer nombreVues = 0;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (ordre == null) ordre = 0;
        if (estActive == null) estActive = true;
        if (nombreClics == null) nombreClics = 0;
        if (nombreVues == null) nombreVues = 0;
    }
}