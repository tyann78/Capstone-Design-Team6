package com.example.tmap.dto;


import jakarta.persistence.*;

@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    private Long id;
    
    private String name;

    private double startX, startY;
    private double endX, endY;

    private String startTime; // 일정 시간
    private String endTime;

    private Long bufferTime; // 미리도착 시간 (초 단위)

    private int transportMethod; // 이동 수단 (0: 자가용, 1: 자전거, 2: 도보)

    private Long expectedTravelTime; // 예상 이동 시간 ( 단위)
    
    private Boolean alarmEnabled;
    private Boolean repeatEnabled;

    public Long getID() {return id;}
    public String getName() {return name;}
    public double getStartX() {return startX;}
    public double getStartY() {return startY;}
    public double getEndX() {return endX;}
    public double getEndY() {return endY;}
    public String getStartTime() {return startTime;} 
    public String getEndTime() {return endTime;}
    public Long getBufferTime() {return bufferTime;}
    public int getTransportMethod() {return transportMethod;}
    public Long getExpectedTravelTime() {return expectedTravelTime;}
    public Boolean getAlarmEnabled() {return alarmEnabled;}
    public Boolean getRepeatEnabled() {return repeatEnabled;}
    
    public void setExpectedTravelTime(Long t) {expectedTravelTime = t;}
}

