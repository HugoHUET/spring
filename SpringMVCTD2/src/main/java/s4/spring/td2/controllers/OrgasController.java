package s4.spring.td2.controllers;

import java.util.List;
import java.util.Optional;

import javax.naming.OperationNotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import s4.spring.td2.entities.Groupe;
import s4.spring.td2.entities.Organisation;
import s4.spring.td2.repositories.GroupesRepository;
import s4.spring.td2.repositories.OrgaRepositoriy;

@Controller
@RequestMapping("/orgas/")
public class OrgasController {
	
	@Autowired
	private OrgaRepositoriy orgasRepo;
	
	@Autowired
	private GroupesRepository groupesRepo;
	
	@RequestMapping("create")
	@ResponseBody
	public String createOrga() {
		Organisation organisation = new Organisation();
		organisation.setNom("Iut Ifs");
		organisation.setDomain("unicaen.fr");
		organisation.setAliases("iutc3.unicaen.fr");
		organisation.setVille("Ifs");
		
		orgasRepo.save(organisation);
		
		return organisation + " ajoutée dans la bdd";
	}
	
	@RequestMapping("groupes")
	@ResponseBody
	public String createGroupe(GroupesRepository gpr) {
		Groupe groupe = new Groupe();
		groupe.setName("groupeTest");
		groupesRepo.save(groupe);
		return "Groupe crée";
	}
	
	@RequestMapping("create/groupes/{id}")
	@ResponseBody
	public String createOrgaAvecGroupes(@PathVariable int id) {
		Optional<Organisation> optOrga = orgasRepo.findById(id);
		
		if(optOrga.isPresent()) {
			Organisation organisation = optOrga.get();
			Groupe groupe = new Groupe();
			groupe.setName("Etudiants");
			
			organisation.addGroupe(groupe);
			
			orgasRepo.save(organisation);
			
			return organisation + " ajoutée dans la bdd";
		}else{
			return "Organisation non trouvée";
		}
	}
	
	@RequestMapping("index")
	public String index(ModelMap model) {
		
		List<Organisation> orgas = orgasRepo.findAll();
		
		model.addAttribute("orgas", orgas);
		
		return "index";
	}
	
	@RequestMapping("new")
	public String newO(ModelMap model) {
		model.addAttribute("organisation", new Organisation());
		
		return "new";
	}
	
	@PostMapping("new")
	public RedirectView add(@ModelAttribute("organisation") Organisation orga) {
		
		orgasRepo.save(orga);
		
		return new RedirectView("index");
	}
	
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable int id, ModelMap model) {
		Optional<Organisation> orga = orgasRepo.findById(id);
		
		if(orga.isPresent()) {
			Organisation organisation = orga.get();
			
			model.addAttribute("orga", organisation);
		}
		
		return "edit";
	}
	
	@PostMapping("edit/editForm")
	public RedirectView editUpdate(@ModelAttribute("organisation") Organisation orga, Model model) {
		Optional<Organisation> organi = orgasRepo.findById(orga.getId());
		if(organi.isPresent()) {
			Organisation organisation = organi.get();
			
			organisation.setNom(orga.getNom());
			
			organisation.setAliases(orga.getAliases());
			
			organisation.setDomain(orga.getDomain());
			
			orgasRepo.save(organisation);
		}
		
		return new RedirectView("/orgas/index");
	}
	
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable int id, ModelMap model) {
		List<Organisation> orgas = orgasRepo.findAll();
		
		model.addAttribute("orgas", orgas);

		Optional<Organisation> orga = orgasRepo.findById(id);
		
		if(orga.isPresent()) {
			Organisation organisation = orga.get();
			
			model.addAttribute("orga", organisation);
		}
		
		return "delete";
	}
	
	@RequestMapping("deleteForm/{id}")
	public RedirectView deleteForm(@PathVariable int id, Model model) {
		Optional<Organisation> orga = orgasRepo.findById(id);
		
		if(orga.isPresent()) {
			Organisation organisation = orga.get();
			
			orgasRepo.delete(organisation);
			
		}
		
		return new RedirectView("/orgas/index");
	}
	
	@RequestMapping("display/{id}")
	public String display(@PathVariable int id, ModelMap model) {
		List<Organisation> orgas = orgasRepo.findAll();
		
		model.addAttribute("orgas", orgas);

		Optional<Organisation> orga = orgasRepo.findById(id);
		
		if(orga.isPresent()) {
			Organisation organisation = orga.get();
			
			model.addAttribute("orga", organisation);
		}
		
		return "display";
	}
}
