package br.com.test.servidorapp.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.test.servidorapp.domains.Servidor;
import br.com.test.servidorapp.repositories.ServidorRepository;

@Controller
public class ServidorController {

	@Autowired
	private ServidorRepository servidorRepository;

	@GetMapping({ "/", "/index" })
	public String mostraTodos(Model model) {
		model.addAttribute("servidores", servidorRepository.findAll());
		return "index";
	}

	@GetMapping("/add")
	public String adicionarServidor(Servidor servidor) {

		return "add";
	}

	@GetMapping("/deletePage")
	public String deleteServidor() {

		return "delete";
	}

	@GetMapping("/delete/{matricula}")
	public String deleteServidor(@PathVariable("matricula") String matricula, RedirectAttributes redirectAttributes) {

		Optional<Servidor> servidorEncontrado = servidorRepository.findById(matricula);

		if (servidorEncontrado.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "A matrícula " + matricula + " não foi encontrada!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-warning");

			return "redirect:/";
		} else {
			servidorRepository.delete(servidorEncontrado.get());

			redirectAttributes.addFlashAttribute("message",
					"O(a) servidor(a) " + servidorEncontrado.get().getNome() + " foi excluído com sucesso!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		}

		return "redirect:/";
	}

	@PostMapping("/severalDelete")
	public String deleteServidores(@RequestParam(value = "matriculas", required = false) ArrayList<String> matriculas,
			RedirectAttributes redirectAttributes) {

		try {

			if (matriculas == null) {
				redirectAttributes.addFlashAttribute("message",
						"Nenhum servidor selecionado para exclusão!");
				redirectAttributes.addFlashAttribute("alertClass", "alert-warning");

			} else {
				matriculas.forEach((x) -> servidorRepository.deleteById(x));

				redirectAttributes.addFlashAttribute("message",
						"Os servidores selecionados foram excluídos com sucesso!");
				redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			}
			return "redirect:/";

		} catch (Exception e) {

			redirectAttributes.addFlashAttribute("message", "Ocorreu erro na exclusão: " + e.getMessage());
			redirectAttributes.addFlashAttribute("alertClass", "alert-warning");

			return "redirect:/";
		}

	}

	@PostMapping("/severalDeleteFilter")
	public String deleteServidores(@RequestParam(value = "matriculas", required = false) String matriculas,
			RedirectAttributes redirectAttributes) {

		List<String> matriculaList = Arrays.asList(matriculas.split(";|\\,"));
		List<String> matriculaListWithoutDuplicates = matriculaList.stream().distinct().collect(Collectors.toList());

		try {

			matriculaListWithoutDuplicates.removeIf(x -> servidorRepository.findById(x).isEmpty());

			if (matriculaListWithoutDuplicates.isEmpty()) {
				redirectAttributes.addFlashAttribute("message", "Nenhum servidor foi encontrado para exclusão!");
				redirectAttributes.addFlashAttribute("alertClass", "alert-warning");
			} else {
				matriculaListWithoutDuplicates.forEach((x) -> servidorRepository.deleteById(x));

				redirectAttributes.addFlashAttribute("message",
						"Os servidores indicados existentes foram excluídos com sucesso!");
				redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			}

			return "redirect:/";

		} catch (Exception e) {

			redirectAttributes.addFlashAttribute("message", "Ocorreu erro na exclusão: " + e.getMessage());
			redirectAttributes.addFlashAttribute("alertClass", "alert-warning");

			return "redirect:/";
		}

	}

	@PostMapping("/add")
	public String addServidorAction(@Valid Servidor servidor, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "add";
		}

		Optional<Servidor> servidorEncontrado = servidorRepository.findById(servidor.getMatricula());

		if (servidorEncontrado.isEmpty()) {
			Servidor servidorNovo = servidorRepository.save(servidor);

			redirectAttributes.addFlashAttribute("message",
					"O(a) servidor(a) " + servidorNovo.getNome() + " foi adicionado com sucesso!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");

			return "redirect:/";
		} else {
			result.rejectValue("matricula", "error.servidor", "Esta matrícula já se encontra cadastrada");
			return "add";
		}

	}

	@PostMapping("/edit")
	public String changeServidorAction(@Valid Servidor servidor, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "edit";
		}

		Servidor servidorNovo = servidorRepository.save(servidor);

		redirectAttributes.addFlashAttribute("message",
				"O(a) servidor(a) " + servidorNovo.getNome() + " foi alterado com sucesso!");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		return "redirect:/";

	}

	@GetMapping("/edit/{matricula}")
	public String changeServidor(@PathVariable("matricula") String matricula, RedirectAttributes redirectAttributes,
			Model model) {

		Optional<Servidor> servidorEncontrado = servidorRepository.findById(matricula);

		if (servidorEncontrado.isEmpty()) {

			redirectAttributes.addFlashAttribute("message", "A matrícula " + matricula + " não foi encontrada!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-warning");

			return "redirect:/";
		} else {
			model.addAttribute("servidor", servidorEncontrado);
			return "edit";
		}

	}

}