package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Messages implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private Long idMessage;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;
    
    @Column(name = "est_lu")
    private Boolean estLu = false;
    
    @Column(name = "date_lecture")
    private LocalDateTime dateLecture;
    
    @Column(name = "date_envoi")
    private LocalDateTime dateEnvoi;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conversation", nullable = false)
    private Conversations conversation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expediteur")
    private Utilisateurs expediteur;
    
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PiecesJointesMessages> piecesJointes;
    
    @PrePersist
    protected void onCreate() {
        dateEnvoi = LocalDateTime.now();
        if (estLu == null) estLu = false;
    }
}