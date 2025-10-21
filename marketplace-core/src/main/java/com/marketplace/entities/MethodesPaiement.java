package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "methodes_paiement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodesPaiement implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_methode")
    private Long idMethode;
    
    @Column(name = "nom_methode", nullable = false, length = 100)
    private String nomMethode;
    
    @Column(name = "code_methode", unique = true, nullable = false, length = 50)
    private String codeMethode;
    
    @Column(length = 255)
    private String icone;
    
    @Column(name = "est_active")
    private Boolean estActive = true;
    
    @Column(name = "frais_fixe", precision = 10, scale = 2)
    private BigDecimal fraisFixe = BigDecimal.ZERO;
    
    @Column(name = "frais_pourcentage", precision = 5, scale = 2)
    private BigDecimal fraisPourcentage = BigDecimal.ZERO;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @OneToMany(mappedBy = "methode", fetch = FetchType.LAZY)
    private List<Paiements> paiements;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (estActive == null) estActive = true;
        if (fraisFixe == null) fraisFixe = BigDecimal.ZERO;
        if (fraisPourcentage == null) fraisPourcentage = BigDecimal.ZERO;
    }
}