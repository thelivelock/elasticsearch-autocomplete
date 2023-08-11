package com.thelivelock.elasticsearch_autocomplete.controller;

import com.thelivelock.elasticsearch_autocomplete.model.User;
import com.thelivelock.elasticsearch_autocomplete.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.listAll();
    }

    @GetMapping(path = "/query")
    @ResponseBody
    public List<User> searchUsers(@RequestParam String keywords) {
        return this.userService.search(keywords);
    }

    @GetMapping("/suggestion")
    @ResponseBody
    public List<String> fetchSuggestions(@RequestParam(value = "q", required = false) String query) {
        List<User> users =  this.userService.search(query);
        List<String> names = users.stream().flatMap(user-> Stream.of(user.getCountry())).toList();
        return names;
    }
}
