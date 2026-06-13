package com.tola.controller;

import com.tola.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping()
    public String HomeController() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Hello this is airline pricing service");
        return apiResponse.getMessage();
    }
}
