package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;
    
    @Column(name = "nom_role", nullable = false, unique = true, length = 50)
    private String nomRole;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<Utilisateurs> utilisateurs;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}