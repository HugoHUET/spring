package s4.spring.td2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import s4.spring.td2.entities.Organisation;
import s4.spring.td2.repositories.OrgaRepositoriy;

@RestController
@RequestMapping
public class RestTestController {
	
	@Autowired
	private OrgaRepositoriy repo;
	
	@ResponseBody
	@GetMapping("")
	public List<Organisation> get(){
		
		return repo.findAll();
		
	}
	
	@PostMapping("/orgas/post")
	public void post(@RequestBody Organisation orga) {
		
	}
}
