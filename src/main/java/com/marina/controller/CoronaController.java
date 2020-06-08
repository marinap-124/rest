package com.marina.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.marina.model.Corona;
import com.marina.repo.CoronaRepo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/corona")
public class CoronaController {
	@Autowired
	private CoronaRepo coronaRepo;

	
	@ApiOperation(value = "load corona cases by area code", response = List.class)
    @ApiResponses({ @ApiResponse(code = 200, message = "Search completed"),
    				@ApiResponse(code = 404, message = "Error in search") })
	@GetMapping(path = "/area/{areacode}")
	public List<Corona> getCoronaByAreaCode(@PathVariable final String areacode) {
		return coronaRepo.findByAreaCode(areacode);
	}

}
