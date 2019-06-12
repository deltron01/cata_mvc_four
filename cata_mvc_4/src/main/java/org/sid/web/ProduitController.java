package org.sid.web;

import java.util.List;

import javax.validation.Valid;

import org.sid.dao.ProduitRepository;
import org.sid.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProduitController {
	@Autowired
	private ProduitRepository produitRepository;

	@RequestMapping(value="/index")
	public String index(Model model, @RequestParam(name="page", defaultValue="0") int p, 
			                         @RequestParam(name="size", defaultValue="5") int s,
			                         @RequestParam(name="motCle", defaultValue="") String mc){
		//Page<Produit> pageProduits = produitRepository.findAll(new PageRequest(p,s)); //findAll qui retourne un type Page est déja intégrée dans Spring Data
		Page<Produit> pageProduits = produitRepository.chercher("%"+mc+"%", new PageRequest(p,s));
		model.addAttribute("listproduits", pageProduits.getContent());
		int[] pages = new int[pageProduits.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("size", s);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "produits";
	}
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(Long id){ // afin de rester sur le même contexte(page courante, size, recherche), on envoie tous les paramètres page, size, motCle en plus de id
		produitRepository.delete(id);
		return "redirect:/index";
	}
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String formProduit(Model model){
		model.addAttribute("produit", new Produit());
		return "FormProduit";
	}
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(Model model, @Valid Produit produit, BindingResult bindingResult){// @valid pour indiquer que le parameter subit une validation Spring
		if(bindingResult.hasErrors()){  // l'objet BindingResult contient les éventuelles erreurs dues à la validation!!!
			return "FormProduit"; 
		}
		produitRepository.save(produit);
		return "Confirmation";
	}
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String edit(Model model, Long id){
		Produit p = produitRepository.findOne(id);
		model.addAttribute("produit", p);
		return "EditProduit";
	}
	@RequestMapping(value="/403")
	public String accesDenied(){
		return "403";
	}
	@RequestMapping(value="/login")
	public String login(){
		return "login";
	}
	@RequestMapping(value="/")
	public String home(){
		return "redirect:/index";
	}
}
