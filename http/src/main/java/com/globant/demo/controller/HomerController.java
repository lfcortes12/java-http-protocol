package com.globant.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomerController.class);

	private List<String> names;

	@PostConstruct
	public void init() {
		names = new ArrayList<>();
		names.addAll(Arrays.asList("Juan", "Oscar", "Breymer", "Beatriz", "Beto", "Jose", "Jorge", "Ortiz", "Edgar"));
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String home() {
		return "Hello word!";
	}

	@RequestMapping(path = "/search", method = RequestMethod.GET)
	public List<String> search(@RequestParam(name = "name") final String name) {
		return names.stream().filter(user -> user.toLowerCase().startsWith(name.toLowerCase()))
				.collect(Collectors.toList());
	}

	@RequestMapping(path = "/", method = RequestMethod.PUT)
	public String save(@RequestParam(name = "username") final String name) {
		names.add(name);
		return "created";
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public String update(@RequestBody @RequestParam(name = "username") final String name,
			@RequestBody @RequestParam(name = "newName") final String newName) {
		LOGGER.info("Updating  {} {}", name, newName );
		for (String string : names) {
			if (string.equalsIgnoreCase(name)) {
				LOGGER.info("Updated!");
				string = newName;
			}
		}
			
		return "updated";
	}
	
	@RequestMapping(path = "/", method = RequestMethod.DELETE)
	public boolean remove(@RequestBody @RequestParam(name = "username") final String name) {
		return names.remove(name);
	}

}
