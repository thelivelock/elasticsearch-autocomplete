package com.thelivelock.elasticsearch_autocomplete.controller;

import com.thelivelock.elasticsearch_autocomplete.model.User;
import com.thelivelock.elasticsearch_autocomplete.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Stream;

@Controller
public class UIController {
	private UserService userService;

	@Autowired
	public UIController(UserService userService) {
		this.userService = userService;
	}

	//when reload localhost:8080/search
	@GetMapping("/search")
	public String home(Model model) {
		List<User> users = this.userService.search("");
		List<String> names = users.stream().flatMap(user -> Stream.of(user.getCountry())).toList();
		model.addAttribute("names", names);
		return "search";
	}
}
