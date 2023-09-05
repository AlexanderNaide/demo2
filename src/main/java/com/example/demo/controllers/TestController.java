package com.example.demo.controllers;

import com.example.demo.model.CityDto;
import com.example.demo.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
public class TestController {
    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/test")
    public CityDto getCity(@RequestParam String ip) {
        return cityService.getCityDto(ip);
    }


}
