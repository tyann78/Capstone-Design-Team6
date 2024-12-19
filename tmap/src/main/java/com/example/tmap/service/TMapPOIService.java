package com.example.tmap.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TMapPOIService {
	
	@Value("${tmap.api.key}")
    private String TMAP_API_KEY;
    private final String TMAP_POI_SEARCH_URL = "https://apis.openapi.sk.com/tmap/pois?version=1";

    public String searchPOI(String searchKeyword) {
        // RestTemplate을 사용하여 GET 요청을 보냄
        RestTemplate restTemplate = new RestTemplate();
        
        // URI 구성
        String uri = UriComponentsBuilder.fromHttpUrl(TMAP_POI_SEARCH_URL)
                .queryParam("appKey", TMAP_API_KEY)
                .queryParam("searchKeyword", searchKeyword)
                .queryParam("count", 10)  // 반환할 결과 수
                .build()
                .toUriString();
        
        // API 호출
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        
        // 반환된 JSON 결과를 문자열로 리턴
        return response.getBody();
    }
}

