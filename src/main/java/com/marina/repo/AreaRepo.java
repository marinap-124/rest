package com.marina.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.marina.model.Area;

@Repository
public interface AreaRepo extends JpaRepository<Area, Long> {
	Area findByCode(String code);

	@Query("SELECT i FROM Area i WHERE i.label LIKE  CONCAT('%',:label,'%') ORDER BY i.label DESC")
	List<Area> findByLabelOrderByLabelDesc(String label);

}
