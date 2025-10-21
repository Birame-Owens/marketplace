package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "pieces_jointes_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PiecesJointesMessages implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piece_jointe")
    private Long idPieceJointe;
    
    @Column(name = "nom_fichier", length = 255)
    private String nomFichier;
    
    @Column(name = "chemin_fichier", length = 500)
    private String cheminFichier;
    
    @Column(name = "type_fichier", length = 100)
    private String typeFichier;
    
    @Column(name = "taille_fichier")
    private Integer tailleFichier;
    
    @Column(name = "date_upload")
    private LocalDateTime dateUpload;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_message", nullable = false)
    private Messages message;
    
    @PrePersist
    protected void onCreate() {
        dateUpload = LocalDateTime.now();
    }
}