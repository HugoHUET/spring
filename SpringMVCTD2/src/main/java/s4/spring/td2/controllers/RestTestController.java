package s4.spring.td2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import s4.spring.td2.entities.Organisation;
import s4.spring.td2.repositories.OrgaRepositoriy;

@RestController
@RequestMapping("/rest/orgas/")
public class RestTestController {
	
	@Autowired
	private OrgaRepositoriy repo;
	
	@ResponseBody
	@GetMapping("")
	public List<Organisation> get(){
		
		return repo.findAll();
		
	}
	
	@PostMapping("create")
	@ResponseBody
	public Organisation createOrUpdate(@RequestBody Organisation orga) {
		repo.save(orga);
		return orga;
	}
	
	@DeleteMapping("delete")
	@ResponseBody
	public String delete(@RequestBody Organisation orga) {
		repo.delete(orga);
		return "{deleted : true}";
	}
	
	@PutMapping("update")
	@ResponseBody
	public Organisation update(@RequestBody Organisation orga) {
		//repo.save(orga);
		return orga;
	}

}
