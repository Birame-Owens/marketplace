package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "images_avis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagesAvis implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image_avis")
    private Long idImageAvis;
    
    @Column(name = "url_image", nullable = false, length = 500)
    private String urlImage;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_avis", nullable = false)
    private AvisProduits avis;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}