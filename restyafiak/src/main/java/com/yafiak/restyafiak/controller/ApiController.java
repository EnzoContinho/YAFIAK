package com.yafiak.restyafiak.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {

	@GetMapping("api")
	public String getStatus() {return "[<span style=\"font-weight:bold;color:green;\">READY</span>] YAFIAK REST API";}
	
}
