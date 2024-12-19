package com.example.tmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.tmap.dto.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAll();
    
    // 특정 id에 해당하는 row 삭제
    @Transactional
    void deleteById(Long id);
}
