package com.petshop.admin.icon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IconController {
	
	@GetMapping("/icons")
	public String viewIcon() {
		return "icons/mdi";
	}
}
