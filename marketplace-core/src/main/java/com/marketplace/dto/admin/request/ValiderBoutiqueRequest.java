package com.marketplace.dto.admin.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValiderBoutiqueRequest {
    private Long idBoutique;
    private String commentaire;
}