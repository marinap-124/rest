package com.marina.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.marina.dto.AreaDto;
import com.marina.dto.AreaSearch;
import com.marina.dto.converter.AreaConverter;
import com.marina.model.Area;
import com.marina.repo.AreaRepo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/area")
public class AreaController {
	@Autowired
	private AreaRepo areaRepo;

	@Autowired
	AreaConverter areaConverter;


	@ApiOperation(value = "load all areas from db", response = Iterable.class)
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Area> getAllAreas() {

		return areaRepo.findAll();
	}

	@ApiOperation(value = "get area", response = AreaDto.class)
	@GetMapping(path = "/{code}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public AreaDto getArea(@PathVariable final String code) {
		
		return areaConverter.convertArea2Dto(areaRepo.findByCode(code));
	}
	
	
    @ApiOperation(value = "search",
            notes = "Search for area")
    @ApiResponses({ @ApiResponse(code = 200, message = "Search completed"),
    				@ApiResponse(code = 404, message = "Error in search") })
    @PostMapping(path = "/search")
    public @ResponseBody Iterable<AreaDto> search(final @RequestBody AreaSearch areaSearch ) {
    	
    	List<Area> list = areaRepo.findByLabelOrderByLabelDesc(areaSearch.getLabel());
    	List<AreaDto> result =  areaConverter.convertArea2Dto(list);

        return result;
    }

}
