package vux.codejava.controller.shift;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShiftController {

	@GetMapping("/shift")
	public String viewOprate(Model model) {	
		
		model.addAttribute("pageTitle", "Giao - Nhận ca");
		model.addAttribute("pagePath", "shift");
		return "shift";
	}
	
	@PostMapping("/shift")
	public String addShift() {
		
		return "do add shift";
	}
}
