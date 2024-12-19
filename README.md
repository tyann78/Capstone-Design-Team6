# Capstone-Design-Team6
# 교통 상황 기반 일정 관리 및 알림 애플리케이션

이 프로젝트는 Kotlin을 사용한 프론트엔드와 Java와 Spring Boot를 사용한 백엔드로 구성된 교통 상황 기반 일정 관리 및 알림 애플리케이션입니다.

## 프로그램 개요

### 프론트엔드
- **언어**: Kotlin
- **특징**: 직관적이고 사용하기 쉬운 인터페이스 제공

### 백엔드
- **언어**: Java, Spring Boot
- **특징**: 교통 데이터와 일정 관리를 효율적으로 처리

## 주요 기능

### 1. 교통 상황에 따른 알림
사용자가 설정한 시간에 따라 실시간 교통 상황을 고려하여 최적의 출발 시간을 계산하고 알림을 전송합니다.

- **예시**: 출근, 통학, 회의, 약속 등 다양한 일정에 대해 교통 상황을 고려한 알림을 보냅니다.

### 2. 일정 표시 및 지도 연동
사용자가 등록한 일정을 한눈에 확인하고, 다음 일정 장소를 지도에서 확인하거나 내비게이션을 실행할 수 있습니다.

- **예시**: 다양한 일정에 대해 이동 경로를 안내합니다.

## 예상 사용 사례

1. **출근 및 통학 지원**: 매일의 출근 시간을 최적화하고 지각을 방지하기 위한 알림 기능
2. **회의 및 약속**: 비즈니스 미팅이나 친구와의 약속에 맞춰 교통 상황을 고려한 출발 시간 알림
3. **여행 및 출장**: 공항, 기차역 등으로의 이동을 최적화하고 여행 일정에 맞춰 알림을 제공
4. **의료 예약**: 병원 예약 시간에 맞춰 이동 경로를 안내하고 교통 상황을 반영한 알림
5. **기타 약속 및 이벤트**: 다양한 개인 일정(예: 운동, 쇼핑, 문화 행사 등)에 대한 이동 경로 및 교통 알림 제공

## 사용 기술 스택

- **프론트엔드**: Kotlin, Pixel 3a XL API 34 이상
- **백엔드**: Java, Spring Boot
- **데이터베이스**: MySQL
- **외부 API**: Tmap open api

## 프로그램 실행을 위한 요구 사항

### 프론트엔드

- **Kotlin**: Pixel 3a XL API 34 이상을 지원하는 Android 환경에서 실행됩니다.
- **Android Studio**: 해당 API 수준을 지원하는 프로젝트 설정이 필요합니다.

### 백엔드

- **Java**: Spring Boot가 필수입니다.
- **JDK**: JDK 11 이상 버전이 설치되어 있어야 하며, Spring Boot 2.x 버전 이상을 사용하는 것이 권장됩니다.

---

## 2. 프로그램 실행 방법

이 프로젝트는 프론트엔드와 백엔드로 나뉘어 있으며, 각 부분을 별도로 실행해야 합니다. 아래의 단계에 따라 실행할 수 있습니다.

### 1. 프로젝트 클론
먼저, 프로젝트를 GitHub에서 클론하여 로컬 환경에 복사합니다.

### 2. 프론트엔드 실행 (Kotlin + Android)
프론트엔드는 Pixel 3a XL API 34 이상을 지원하는 Android 기기에서 실행할 수 있습니다.

1. **Android Studio 설치**  
   Android Studio를 설치합니다. 최신 버전이 권장됩니다.

2. **프로젝트 열기**  
   Android Studio에서 프로젝트를 엽니다.

3. **종속성 확인**  
   `build.gradle` 파일을 확인하여 필요한 종속성이 설치되어 있는지 확인합니다.

4. **앱 실행**  
   Android Studio에서 Pixel 3a XL API 34 에뮬레이터 또는 실제 기기에서 앱을 실행합니다.

### 3. 백엔드 실행 (Java + Spring Boot)
백엔드에서는 Spring Boot를 사용하여 API 서버를 실행합니다.

1. **Java 11 이상 설치**  
   Java 11 이상 버전이 설치되어 있는지 확인합니다.

2. **설정 완료**  
   데이터베이스 연결 정보나 외부 API 키 등을 설정합니다. 예를 들어, `application.properties` 파일에 설정을 추가합니다.

3. **백엔드 서버 실행**  
   Spring Boot 애플리케이션을 실행합니다.

### 4. 데이터베이스 설정
이 프로젝트는 PostgreSQL을 사용합니다. 데이터베이스가 설치되어 있어야 하며, 설정이 필요합니다.

1. **PostgreSQL 설치**  
   PostgreSQL을 설치합니다.

2. **데이터베이스 연결 정보 수정**  
   `application.properties` 또는 `application.yml` 파일에서 데이터베이스 연결 정보를 수정합니다.

3. **데이터베이스 테이블 초기화**  
   프로젝트에서 제공하는 SQL 파일을 사용하여 데이터베이스 테이블을 생성합니다.

### 5. 프로그램 실행 확인
프론트엔드 앱을 실행한 후, 알림 기능과 교통 상황 알림이 올바르게 작동하는지 확인합니다.

백엔드 서버가 실행 중일 때, API 엔드포인트를 호출하여 스케줄 관리와 지도 연동 기능을 테스트합니다.

프론트엔드와 백엔드가 정상적으로 동작하면, 교통 상황에 따른 알림과 일정 관리 기능이 올바르게 작동합니다.

