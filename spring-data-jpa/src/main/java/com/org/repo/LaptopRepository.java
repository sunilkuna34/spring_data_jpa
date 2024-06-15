package com.org.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.org.dto.Laptop;

public interface LaptopRepository extends JpaRepository<Laptop, Integer> {
	
	List<Laptop> findByName(String name);
	
	List<Laptop> findByNameAndColor(String name,String color);
	
	@Query("select l from Laptop l where l.price=?1 AND l.color=?2")
	List<Laptop> fetchBasedOnPrice(long price,String color);
	
	
	
	

}
