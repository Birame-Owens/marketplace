package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "avoir_factures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvoirFactures implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avoir")
    private Long idAvoir;
    
    @Column(name = "numero_avoir", unique = true, nullable = false, length = 50)
    private String numeroAvoir;
    
    @Column(name = "montant_avoir", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantAvoir;
    
    @Column(name = "raison_avoir", columnDefinition = "TEXT")
    private String raisonAvoir;
    
    @Column(name = "chemin_pdf", length = 500)
    private String cheminPdf;
    
    @Column(name = "date_emission", nullable = false)
    private LocalDate dateEmission;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_facture")
    private Factures facture;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (dateEmission == null) dateEmission = LocalDate.now();
    }
}