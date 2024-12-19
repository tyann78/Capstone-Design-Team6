package com.example.tmap.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tmap.service.TMapService;

@RestController
public class TMapController {

    private final TMapService tMapService;

    public TMapController(TMapService tMapService) {
        this.tMapService = tMapService;
    }

    @GetMapping("/api/driving-time")
    public String getDrivingTime(
            @RequestParam String startX,
            @RequestParam String startY,
            @RequestParam String endX,
            @RequestParam String endY,
            @RequestParam String departureTime){
        return tMapService.getDrivingTime(startX, startY, endX, endY, departureTime + "+0900");
    }

    @GetMapping("/api/walking-time")
    public String getWalkingTime(
            @RequestParam String startX,
            @RequestParam String startY,
            @RequestParam String endX,
            @RequestParam String endY,
            @RequestParam int speed) {
        return tMapService.getWalkingTime(startX, startY, endX, endY, speed);
    }
}