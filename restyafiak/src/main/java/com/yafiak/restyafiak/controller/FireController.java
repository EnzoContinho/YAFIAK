package com.yafiak.restyafiak.controller;

import com.yafiak.restyafiak.model.Fire;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yafiak.restyafiak.repository.FireRepository;

@RestController
public class FireController {

	@Autowired
	private FireRepository fireRepository;
	
	@GetMapping("api/fires")
    public List<Fire> getFires() {
        return fireRepository.findAll();
    }
	
	@GetMapping("api/fires/{id}")
	@ResponseBody
    public Optional<Fire> getFireById(@PathVariable Long id) {
        return fireRepository.findById(id);
    }
	
	@PostMapping("api/fires")
	public Fire createFire(@RequestBody Fire fire) {
		return fireRepository.save(fire);
	}
	
	@PutMapping("api/fires/{id}")
	public ResponseEntity<Object> updateFire(@RequestBody Fire fire, @PathVariable long id) {
		Optional<Fire> fireOptional = fireRepository.findById(id);
		if (!fireOptional.isPresent())
			return ResponseEntity.notFound().build();
		fire.setId(id);
		fireRepository.save(fire);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("api/fires/{id}")
	public void deleteFire(@PathVariable long id) {
		fireRepository.deleteById(id);
	}
	
}
