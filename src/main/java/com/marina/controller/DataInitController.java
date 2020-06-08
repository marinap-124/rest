package com.marina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.marina.service.DataInitService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/init")
public class DataInitController {
	
	@Autowired
	private DataInitService dataInitService;
	
    @ApiOperation(value = "Data initialization")
    @ApiResponses({ @ApiResponse(code = 200, message = "Initialization completed"),
		@ApiResponse(code = 404, message = "Error in initialization data") })
    @PostMapping("/data")
	public @ResponseBody String initCoronacases() {
		return dataInitService.initData();
	}
}
