package org.sid.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Entity
public class Produit implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue
	private Long id;
	@NotNull
	@Size(min=4, max=25)  // annotation de validation du champs (s'effectue au niveau Vue wwaw ??)
	private String designation;
	@DecimalMin("100")  // annotation de validation du champs (s'effectue au niveau Vue wwaw ??)
	private double prix;
	private int quantite;
	public Produit() {
		super();
	}
	public Produit(String designation, double prix, int quantite) {
		super();
		this.designation = designation;
		this.prix = prix;
		this.quantite = quantite;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	

}
