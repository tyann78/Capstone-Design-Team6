package com.example.tmap.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.tmap.dto.RouteRequestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Component
public class TravelTimeCalculator {

	@Value("${tmap.api.key}")
    private String API_KEY;
	private final RestTemplate restTemplate = new RestTemplate();

    public Long calculateTravelTime(double startX, double startY, double endX, double endY, int transportMethod, String departureTime, Long preArrivalTime) {
        
    	
    	String expectedTimeString = "0";
    	
    	if (transportMethod == 0) {
    		expectedTimeString = getDrivingTime(startX, startY, endX, endY, departureTime);
    	}
    	else if (transportMethod == 1) {
    		expectedTimeString = getWalkingTime(startX, startY, endX, endY, 12);
    	}
    	else if (transportMethod == 2) {
    		expectedTimeString = getWalkingTime(startX, startY, endX, endY, 5);
    	}
    	else {
    		// error
    	}
    	
    	return Long.parseLong(expectedTimeString);
    }
    
    private String parseArrivalTime(String jsonResponse) {
        try {
            // ObjectMapper 인스턴스 생성
            ObjectMapper objectMapper = new ObjectMapper();

            // JSON 문자열을 JsonNode 객체로 변환
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // "arrivalTime" 필드를 추출
            JsonNode featureNode = rootNode.path("features");
            		
            // routes 배열이 존재하고 비어 있지 않은지 확인
            if (featureNode.isArray()) {
                for (JsonNode feature : featureNode) {
                    // 각 route 객체에서 arrivalTime 추출
                    JsonNode arrivalTimeNode = feature.path("properties").path("totalTime");
                    if (!arrivalTimeNode.isMissingNode()) {
                        return arrivalTimeNode.asText();
                    }
                    else {
                    	return "arrivalTime not found";
                    }
                }
            }	

        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing JSON: " + e.getMessage();
        }
		return "ERROR";
    }
    
    public String getDrivingTime(double _startX, double _startY, double _endX, double _endY, String departureTime) {
    	// convert
    	String startX = String.valueOf(_startX);
    	String startY = String.valueOf(_startY);
    	String endX = String.valueOf(_endX);
    	String endY = String.valueOf(_endY);
    	
        String url = UriComponentsBuilder.fromHttpUrl("https://apis.openapi.sk.com/tmap/routes/prediction")
        		.queryParam("version", "1")
        		.queryParam("format", "json")
        		.build().toString();
        
        // HTTP 헤더에 API 키 추가
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("appKey", API_KEY);        
        
        RouteRequestDTO routeRequestDTO = new RouteRequestDTO();
        RouteRequestDTO.RoutesInfo routesInfo = new RouteRequestDTO.RoutesInfo();
        RouteRequestDTO.Departure departure = new RouteRequestDTO.Departure();
        departure.setName("test1");
        departure.setLon(startX);
        departure.setLat(startY);
        departure.setDepSearchFlag("05");
        RouteRequestDTO.Destination destination = new RouteRequestDTO.Destination();
        destination.setName("test2"); 
        destination.setLon(endX);
        destination.setLat(endY);
        destination.setPoiId("1000559885");
        destination.setRpFlag("16");
        destination.setDestSearchFlag("03");
        routesInfo.setDeparture(departure);
        routesInfo.setDestination(destination);
        routesInfo.setPredictionType("departure");
        routesInfo.setPredictionTime(departureTime);
        routesInfo.setSearchOption("00");
        routesInfo.setTollgateCarType("car");
        routesInfo.setTrafficInfo("N");
        routeRequestDTO.setRoutesInfo(routesInfo);

        HttpEntity<RouteRequestDTO> request = new HttpEntity<>(routeRequestDTO, headers);
        
        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(
        		url,             // 요청을 보낼 URL
                HttpMethod.POST, // HTTP 메소드
                request,         // HttpEntity (헤더 + 바디)
                String.class     // 응답을 받을 타입
        );
        
        return this.parseArrivalTime(response.getBody());
    }
    private String getWalkingTime(double _startX, double _startY, double _endX, double _endY, int speed) {
    	// convert
    	String startX = String.valueOf(_startX);
    	String startY = String.valueOf(_startY);
    	String endX = String.valueOf(_endX);
    	String endY = String.valueOf(_endY);
    	
        String url = UriComponentsBuilder.fromHttpUrl("https://apis.openapi.sk.com/tmap/routes/pedestrian")
                .queryParam("version", "1")
                .queryParam("format", "json")
                .queryParam("startX", startX)
                .queryParam("startY", startY)
                .queryParam("endX", endX)
                .queryParam("endY", endY)
                .queryParam("speed", speed)
                .queryParam("startName", "test1")
                .queryParam("endName", "test2")
                .build().toString();
        
        // HTTP 헤더에 API 키 추가
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("appKey", API_KEY);
        
        HttpEntity<String> request = new HttpEntity<>(headers);
        
        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(
        		url,             // 요청을 보낼 URL
                HttpMethod.POST, // HTTP 메소드
                request,         // HttpEntity (헤더 + 바디)
                String.class     // 응답을 받을 타입
        );
        
        // response parsing
        try {
            // ObjectMapper 인스턴스 생성
            ObjectMapper objectMapper = new ObjectMapper();

            // JSON 문자열을 JsonNode 객체로 변환
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            // "arrivalTime" 필드를 추출
            JsonNode featureNode = rootNode.path("features");
            		
            // routes 배열이 존재하고 비어 있지 않은지 확인
            if (featureNode.isArray()) {
                for (JsonNode feature : featureNode) {
                    // 각 route 객체에서 arrivalTime 추출
                    JsonNode arrivalTimeNode = feature.path("properties").path("totalTime");
                    if (!arrivalTimeNode.isMissingNode()) {
                        return arrivalTimeNode.asText();
                    }
                    else {
                    	return "arrivalTime not found";
                    }
                }
            }	

        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing JSON: " + e.getMessage();
        }
		return "ERROR";
    }
}

