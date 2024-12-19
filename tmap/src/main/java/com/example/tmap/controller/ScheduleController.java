package com.example.tmap.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tmap.dto.BoolResponse;
import com.example.tmap.dto.Schedule;
import com.example.tmap.service.ScheduleService;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 현재 시간보다 미래의 일정을 업데이트하는 엔드포인트
    @PutMapping("/update-future")
    public String updateFutureSchedules() {
        scheduleService.updateFutureSchedules();
        return "Future schedules updated successfully!";
    }
    
    @PostMapping("/add")
    public BoolResponse addSchedule(@RequestBody Schedule newSchedule) {
        return scheduleService.addSchedule(newSchedule);
    }

    // 모든 일정 반환 엔드포인트
    @GetMapping("/all")
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }
    
    // 특정 Id로 삭제하는 API
    @DeleteMapping("/delete/id/{id}")
    public void deleteById(@PathVariable Long id) {
        scheduleService.deleteScheduleById(id);
    }
}

