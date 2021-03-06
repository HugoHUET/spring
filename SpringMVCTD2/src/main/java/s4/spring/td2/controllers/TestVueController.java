package s4.spring.td2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.utilities.Http;
import io.github.jeemv.springboot.vuejs.utilities.JsArray;
import s4.spring.td2.entities.Organisation;
import s4.spring.td2.repositories.OrgaRepositoriy;

@Controller
@RequestMapping("/vue/")
public class TestVueController {
	
	@Autowired
	private VueJS vue;
	
	@Autowired
	private OrgaRepositoriy repo;
	
	@GetMapping("test")
	public String test(Model model) {
		model.addAttribute("vue", vue);
		vue.addMethod("update", "this.message='Message modifié !';");
		vue.addMethod("testAjax", 
				"var self=this;"+Http.post("/vue/test/ajax",
				"{}", 
				"self.ajaxMessage=response.data;self.alertVisible=true;"));
		vue.addData("message", "hello world !");
		vue.addData("alertVisible", false);
		vue.addData("ajaxMessage");
		vue.addData("inputValue");
		
		
		return "vueJs/test";
	}
	
	@PostMapping("test/ajax")
	@ResponseBody
	public String testAjax() {
		return "test ok";
	}
	
	@GetMapping("/orgas")
	public String geneSpaOrgas(Model model) {
		model.addAttribute("vue", vue);
		List<Organisation> orgas = repo.findAll();
		vue.addData("orgas", orgas);
		vue.addDataRaw("headers", "[{text:'Name', value:'nom'},{text:'Domain', value:'domain'},{text:'Aliases', value:'aliases,'},{text:'Action'}]");
		vue.addDataRaw("dialog", "false");
		vue.addDataRaw("editedItem", "{}");
		vue.addDataRaw("editedIndex", "-1");
		vue.addComputed("formTitle", "(this.itemIndex==-1)?'Nouvelle orga':'Modification orga'");
		vue.addMethod("close", "this.dialog=false;");
		
		vue.addMethod(
				"save",
				"var self=this;"+Http.post(
						"/rest/orgas/create",
						"self.editedItem", 
						"self.orgas.push(response.data);self.dialog=false;")
				);
		
		vue.addMethod(
				"deleteItem",
				"var self=this;"+Http.delete(
						"/rest/orgas/delete",
						(Object)"{data:orga}",
						JsArray.remove("self.orgas", "orga")),
				"orga");
		
		vue.addMethod(
				"editItem",
				"var self=this; self.dialog=true; self.editedIndex=1; self.editedItem=orga;"+Http.put(
						"/rest/orgas/update",
						(Object)"{data:orga}",
						"console.log('ok ?');"),
				"orga");
		
		
		
		return "vueJs/index";
	}
	
	
}
