package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired /*Ya que no vamos a crear ni modificar nada de Role solo creamos el repository no el Service**/
	RoleRepository  roleRepository;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/userForm")
	public String userForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute("userList",userService.getAllUsers());
		model.addAttribute("listTab","active");
		return "user-form/user-view";
	}
	
	@PostMapping("/userForm")
	public String postUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm",user);
			model.addAttribute("formTab","active");
		}else {
			try {
				userService.createUser(user);
				model.addAttribute("userForm",user);
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("errorMessage",e.getMessage());
				model.addAttribute("userForm",new User());
				model.addAttribute("formTab","active");
				model.addAttribute("userList",userService.getAllUsers());
				model.addAttribute("roles",roleRepository.findAll());

			}
		}
		model.addAttribute("userList",userService.getAllUsers());
		model.addAttribute("roles",roleRepository.findAll());
		
	return "user-form/user-view";
	}
}
