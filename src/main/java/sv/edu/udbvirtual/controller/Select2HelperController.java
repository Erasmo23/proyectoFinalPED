package sv.edu.udbvirtual.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sv.edu.udbvirtual.commons.Constants;
import sv.edu.udbvirtual.commons.S2;
import sv.edu.udbvirtual.commons.S2Response;

@Controller
@RequestMapping("/select2")
public class Select2HelperController {

	@GetMapping(value = { "/cboFilterTrueFalse" }, produces = Constants.APPLICATION_JSON)
	public @ResponseBody S2Response<S2> cboFilterTrueFalse(@RequestParam(value = "q", required = false) String q,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows) {
		S2Response<S2> response;
		List<S2> results = new ArrayList<>();
		results.add(new S2("true", "Activo"));
		results.add(new S2("false", "Inactivo"));

		response = new S2Response<>(results, Boolean.FALSE);

		return response;
	}

}