package com.example.tmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tmap.service.TMapPOIService;

@RestController
public class TMapPOIController {

    @Autowired
    private TMapPOIService POIService;

    @GetMapping("/searchPOI")
    public String searchPOI(@RequestParam String keyword) {
        // TMapService를 호출하여 검색 결과를 받아옴
        return POIService.searchPOI(keyword);
    }
}
