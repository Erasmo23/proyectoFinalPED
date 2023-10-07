package sv.edu.udbvirtual.controller;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class MainController {

	private static final String INDEX_DEFAULT = "commons/index";

	static Logger logger = Logger.getLogger(MainController.class.getName());

	@GetMapping(value = { "/", "/index" })
	public String index(Model modelo) {

		return INDEX_DEFAULT;

	}

	@GetMapping(value = "/login")
	public String login(Model modelo) {
		return "commons/login";
	}
}
