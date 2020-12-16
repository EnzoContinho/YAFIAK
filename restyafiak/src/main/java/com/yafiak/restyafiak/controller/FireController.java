package com.yafiak.restyafiak.controller;

import com.yafiak.restyafiak.model.Fire;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yafiak.restyafiak.repository.FireRepository;

@RestController
public class FireController {

	@Autowired
	private FireRepository fireRepository;
	
	@GetMapping("/fires")
    public List<Fire> getFires() {
        return fireRepository.findAll();
    }
	
	@PostMapping("/fires")
	public Fire createFire(@RequestBody Fire fire) {
		return fireRepository.save(fire);
	}
	
}
