package com.example.tmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.tmap") 
public class TmapApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmapApplication.class, args);
	}

}


// TODO 주소변환
// TODO 지도
// TODO DB 연결 