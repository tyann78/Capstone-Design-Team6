package com.example.tmap.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.tmap.dto.BoolResponse;
import com.example.tmap.dto.Schedule;
import com.example.tmap.repository.ScheduleRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TravelTimeCalculator travelTimeCalculator; // 이동 시간 계산 로직 클래스

    public ScheduleService(ScheduleRepository scheduleRepository, TravelTimeCalculator travelTimeCalculator) {
        this.scheduleRepository = scheduleRepository;
        this.travelTimeCalculator = travelTimeCalculator;
    }

    // 현재 시간보다 미래의 일정들을 찾아서 예상 이동 시간을 계산 후 업데이트
    public void updateFutureSchedules() {
        List<Schedule> futureSchedules = scheduleRepository.findAll();

        for (Schedule schedule : futureSchedules) {
            Long updatedTravelTime = travelTimeCalculator.calculateTravelTime(
                    schedule.getStartX(),
                    schedule.getStartY(),
                    schedule.getEndX(),
                    schedule.getEndY(),
                    schedule.getTransportMethod(),
                    schedule.getStartTime(),
                    schedule.getBufferTime()
            );
            schedule.setExpectedTravelTime(updatedTravelTime); // 예상 이동 시간 업데이트
            scheduleRepository.save(schedule); // DB에 저장
        }
    }
    

    public BoolResponse addSchedule(Schedule newSchedule) {
    	BoolResponse response = new BoolResponse();
    	
    	System.out.println(newSchedule.getBufferTime());
    	
    	String newDepartureTime = newSchedule.getStartTime();
    	Long ett = travelTimeCalculator.calculateTravelTime(
    			newSchedule.getStartX(),
    			newSchedule.getStartY(),
    			newSchedule.getEndX(),
    			newSchedule.getEndY(),
    			newSchedule.getTransportMethod(),
    			newSchedule.getStartTime(),
    			newSchedule.getBufferTime()
        );
    	newSchedule.setExpectedTravelTime(ett); // 예상 이동 시간 업데이트

        // 모든 일정을 불러온다
        List<Schedule> allSchedules = (List<Schedule>) scheduleRepository.findAll();

        // 입력된 일정의 departureTime을 LocalDateTime으로 변환
        LocalDateTime newScheduleTime = convertStringToLocalDateTime(newDepartureTime);

        // 과거 일정 중 가장 최근 일정을 찾는다
        Schedule lastSchedule = findLatestScheduleBefore(newScheduleTime, allSchedules);

        if (lastSchedule != null) {
            // 마지막 일정과 현재 일정 사이의 시간 차이 계산 (초 단위)
            long timeDifference = calculateTimeDifference(lastSchedule.getStartTime(), newDepartureTime);
            long expectedTravelTime = newSchedule.getExpectedTravelTime();

            // 경고 로직
            if (timeDifference < expectedTravelTime) {
            	
            	response.setValue(false);
                return response;
            }
        }

        // 일정 저장
        scheduleRepository.save(newSchedule);
    	response.setValue(true);
        return response;
    }


    // 모든 일정 반환
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
    
    // id 로 삭제
    public void deleteScheduleById(Long id) {
        scheduleRepository.deleteById(id);
    }
    
    @Scheduled(fixedRate = 300000)  // 5분마다 호출 (5분 = 300,000ms)
    public void updateTravelTimeMost() {
        // 현재 시간
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now + "__5");

        // 모든 일정을 불러옴
        List<Schedule> allSchedules = (List<Schedule>) scheduleRepository.findAll();

        // 각 일정에 대해 처리
        for (Schedule schedule : allSchedules) {
            LocalDateTime departureTime = convertStringToLocalDateTime(schedule.getStartTime());

            // 현재 시간과의 시간 차이 계산
            long timeDifference = Duration.between(now, departureTime).toMinutes(); // 시간 차이를 분 단위로 계산

            // 시간 차이에 따라 업데이트 주기를 설정
            if (timeDifference <= 360) { 
                    updateExpectedTravelTime(schedule);
                    scheduleRepository.save(schedule);
            }
        }
    }
    
    @Scheduled(fixedRate = 1800000)  // 30 minute 마다 호출 
    public void updateTravelTimeMore() {
        // 현재 시간
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now + "__30");

        // 모든 일정을 불러옴
        List<Schedule> allSchedules = (List<Schedule>) scheduleRepository.findAll();

        // 각 일정에 대해 처리
        for (Schedule schedule : allSchedules) {
            LocalDateTime departureTime = convertStringToLocalDateTime(schedule.getStartTime());

            // 현재 시간과의 시간 차이 계산
            long timeDifference = Duration.between(now, departureTime).toMinutes(); // 시간 차이를 분 단위로 계산

            // 시간 차이에 따라 업데이트 주기를 설정
            if (360 < timeDifference && timeDifference <= 720) { 
                    updateExpectedTravelTime(schedule);
                    scheduleRepository.save(schedule);
            }
        }
    }
    
    @Scheduled(fixedRate = 7200000)  // 2시간마다 호출 
    public void updateTravelTime() {
        // 현재 시간
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now + "__120");

        // 모든 일정을 불러옴
        List<Schedule> allSchedules = (List<Schedule>) scheduleRepository.findAll();

        // 각 일정에 대해 처리
        for (Schedule schedule : allSchedules) {
            LocalDateTime departureTime = convertStringToLocalDateTime(schedule.getStartTime());

            // 현재 시간과의 시간 차이 계산
            long timeDifference = Duration.between(now, departureTime).toMinutes(); // 시간 차이를 분 단위로 계산

            // 시간 차이에 따라 업데이트 주기를 설정
            if (timeDifference > 720) { 
                    updateExpectedTravelTime(schedule);
                    scheduleRepository.save(schedule);
            }
        }
    }
    
    // String 을 LocalDateTime으로 변환
    private LocalDateTime convertStringToLocalDateTime(String departureTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        return LocalDateTime.parse(departureTime, formatter);
    }

    // 가장 최근의 일정 찾기
    private Schedule findLatestScheduleBefore(LocalDateTime newScheduleTime, List<Schedule> allSchedules) {
        Schedule lastSchedule = null;
        for (Schedule schedule : allSchedules) {
            LocalDateTime scheduleTime = convertStringToLocalDateTime(schedule.getStartTime());
            if (scheduleTime.isBefore(newScheduleTime)) {
                if (lastSchedule == null || scheduleTime.isAfter(convertStringToLocalDateTime(lastSchedule.getStartTime()))) {
                    lastSchedule = schedule;
                }
            }
        }
        return lastSchedule;
    }
    
    // 시간 차이 계산 (초 단위)
    private long calculateTimeDifference(String lastDepartureTime, String newDepartureTime) {
        LocalDateTime lastDate = convertStringToLocalDateTime(lastDepartureTime);
        LocalDateTime newDate = convertStringToLocalDateTime(newDepartureTime);

        // 시간 차이 (초 단위)
        return java.time.Duration.between(lastDate, newDate).getSeconds();
    }
    
    // 예상 이동 시간 업데이트
    private void updateExpectedTravelTime(Schedule schedule) {
    	Long ett = travelTimeCalculator.calculateTravelTime(
    			schedule.getStartX(),
    			schedule.getStartY(),
    			schedule.getEndX(),
    			schedule.getEndY(),
    			schedule.getTransportMethod(),
    			schedule.getStartTime(),
    			schedule.getBufferTime()
        );
    	schedule.setExpectedTravelTime(ett); // 예상 이동 시간 업데이트
    }
}

