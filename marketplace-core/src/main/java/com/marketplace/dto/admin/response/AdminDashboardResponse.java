package com.marketplace.dto.admin.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {
    private Long totalUtilisateurs;
    private Long totalAdmins;
    private Long totalGerants;
    private Long totalClients;
    private Long totalBoutiques;
    private Long boutiquesActives;
    private Long boutiquesEnAttente;
    private Long totalProduits;
    private Long totalCommandes;
    private Long commandesAujourdhui;
	public Long getTotalUtilisateurs() {
		return totalUtilisateurs;
	}
	public void setTotalUtilisateurs(Long totalUtilisateurs) {
		this.totalUtilisateurs = totalUtilisateurs;
	}
	public Long getTotalAdmins() {
		return totalAdmins;
	}
	public void setTotalAdmins(Long totalAdmins) {
		this.totalAdmins = totalAdmins;
	}
	public Long getTotalGerants() {
		return totalGerants;
	}
	public void setTotalGerants(Long totalGerants) {
		this.totalGerants = totalGerants;
	}
	public Long getTotalClients() {
		return totalClients;
	}
	public void setTotalClients(Long totalClients) {
		this.totalClients = totalClients;
	}
	public Long getTotalBoutiques() {
		return totalBoutiques;
	}
	public void setTotalBoutiques(Long totalBoutiques) {
		this.totalBoutiques = totalBoutiques;
	}
	public Long getBoutiquesActives() {
		return boutiquesActives;
	}
	public void setBoutiquesActives(Long boutiquesActives) {
		this.boutiquesActives = boutiquesActives;
	}
	public Long getBoutiquesEnAttente() {
		return boutiquesEnAttente;
	}
	public void setBoutiquesEnAttente(Long boutiquesEnAttente) {
		this.boutiquesEnAttente = boutiquesEnAttente;
	}
	public Long getTotalProduits() {
		return totalProduits;
	}
	public void setTotalProduits(Long totalProduits) {
		this.totalProduits = totalProduits;
	}
	public Long getTotalCommandes() {
		return totalCommandes;
	}
	public void setTotalCommandes(Long totalCommandes) {
		this.totalCommandes = totalCommandes;
	}
	public Long getCommandesAujourdhui() {
		return commandesAujourdhui;
	}
	public void setCommandesAujourdhui(Long commandesAujourdhui) {
		this.commandesAujourdhui = commandesAujourdhui;
	}
}