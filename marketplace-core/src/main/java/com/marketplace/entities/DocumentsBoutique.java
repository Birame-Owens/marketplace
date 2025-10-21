package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents_boutique")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsBoutique implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document")
    private Long idDocument;
    
    @Column(name = "type_document", length = 100)
    private String typeDocument;
    
    @Column(name = "nom_fichier", length = 255)
    private String nomFichier;
    
    @Column(name = "chemin_fichier", length = 500)
    private String cheminFichier;
    
    @Column(name = "est_verifie")
    private Boolean estVerifie = false;
    
    @Column(name = "date_upload")
    private LocalDateTime dateUpload;
    
    @Column(name = "date_verification")
    private LocalDateTime dateVerification;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boutique", nullable = false)
    private Boutiques boutique;
    
    @PrePersist
    protected void onCreate() {
        dateUpload = LocalDateTime.now();
        if (estVerifie == null) estVerifie = false;
    }
}