package learncode.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	
	@RequestMapping("/index")
	public String index() {
		return "/layouts/admin";
	}
}
