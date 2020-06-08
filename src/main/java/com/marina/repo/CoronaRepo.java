package com.marina.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.marina.model.Corona;

@Repository
public interface CoronaRepo extends JpaRepository<Corona, Integer> {
	List<Corona> findByAreaCode(String code);
}