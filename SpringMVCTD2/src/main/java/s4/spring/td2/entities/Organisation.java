package s4.spring.td2.entities;

import javax.persistence.GenerationType;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Organisation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String nom;
	private String domain;
	private String aliases;
	private String ville;
	
	@OneToMany(mappedBy="organisation", cascade=CascadeType.ALL)
	private List<Groupe> groupes;
	
	public Organisation() {
		groupes = new ArrayList<>();
	}
	
	public void addGroupe(Groupe groupe) {
		groupes.add(groupe);
		groupe.setOrganisation(this);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getAliases() {
		return aliases;
	}
	public void setAliases(String aliases) {
		this.aliases = aliases;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	
	public List<Groupe> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}

	@Override
	public String toString() {
		return nom + " à " + ville;
	}
	
}
