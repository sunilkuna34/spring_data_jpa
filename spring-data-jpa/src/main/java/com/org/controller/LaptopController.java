package com.org.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.dto.Laptop;
import com.org.repo.LaptopRepository;
@RestController
@CrossOrigin(origins = "*",allowedHeaders="*")
public class LaptopController {
	@Autowired
	LaptopRepository repo;
	@GetMapping("/msg")
	public String msg() {
		return "hello";
		
	}
	
	@PostMapping("/save")
	public ResponseEntity<Laptop> save(@RequestBody Laptop laptop) {
		//return repo.save(laptop);
		Laptop laptop2 = repo.save(laptop);
		if(laptop2!=null) {
			return new ResponseEntity<>(laptop2, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	@GetMapping("/fetchAll")
	public List<Laptop> fetchAll(){
		return repo.findAll()
				;
	}
	@DeleteMapping("/removebyId/{id}")
	public String removeById(@PathVariable int id) {
		 repo.deleteById(id);
		return "record deleted";
			

	
	
	
	
	}
	@GetMapping("/fetchById/{id}")
	public ResponseEntity<Laptop> fetchById(@PathVariable int id) {
		
		
		Optional<Laptop> optional = repo.findById(id);
		Laptop obj=null;
		if(optional.isPresent()) {
			obj= optional.get();
		return new ResponseEntity<>(obj, HttpStatus.OK);
		}
//		return optional.isPresent() ? optional.get():null;
//		return repo.findById(id).orElse(null);
		//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		throw new LaptopNotFoundException("Not found");
	}
	@ExceptionHandler
	public ResponseEntity<LaptopExceptionResourceHolder> handler(LaptopNotFoundException e){
		LaptopExceptionResourceHolder holder = new LaptopExceptionResourceHolder();
		String msg=e.getMessage();
		int status=HttpStatus.NOT_FOUND.value();
		long timeStamp=System.currentTimeMillis();
		
		holder.setMsg(msg);
		holder.setStatus(status);
		holder.setTimestamp(timeStamp);
		
		return new ResponseEntity<>(holder,HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/byName/{name}")
	public List<Laptop> fetchByName(@PathVariable String name){
		return repo.findByName(name);
		
	}
	
	@GetMapping("/byNameAndColor/{name}/{color}")
	public List<Laptop> fetchByNameAndColor(@PathVariable String name,@PathVariable String color){
		return repo.findByNameAndColor(name, color);
		
	}
	@GetMapping("/byPriceAndColor/{price}/{color}")
	public List<Laptop> fetchByPriceAndColor(@PathVariable long price,@PathVariable String color){
		return repo.fetchBasedOnPrice(price, color);
		
	}
	
	
	

}
