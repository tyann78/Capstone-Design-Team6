package com.example.tmap.dto;

public class RouteRequestDTO {

    private RoutesInfo routesInfo;

    // Getters and Setters
    public RoutesInfo getRoutesInfo() {
        return routesInfo;
    }

    public void setRoutesInfo(RoutesInfo routesInfo) {
        this.routesInfo = routesInfo;
    }

    // Nested class for RoutesInfo
    public static class RoutesInfo {
        private Departure departure;
        private Destination destination;
        private String predictionType;
        private String predictionTime;
        private String searchOption;
        private String tollgateCarType;
        private String trafficInfo;       
        
        // Getters and Setters
        public Departure getDeparture() {
            return departure;
        }

        public void setDeparture(Departure departure) {
            this.departure = departure;
        }

        public Destination getDestination() {
            return destination;
        }

        public void setDestination(Destination destination) {
            this.destination = destination;
        }

        public String getPredictionType() {
            return predictionType;
        }

        public void setPredictionType(String predictionType) {
            this.predictionType = predictionType;
        }

        public String getPredictionTime() {
            return predictionTime;
        }

        public void setPredictionTime(String predictionTime) {
            this.predictionTime = predictionTime;
        }

        public String getSearchOption() {
            return searchOption;
        }

        public void setSearchOption(String searchOption) {
            this.searchOption = searchOption;
        }

        public String getTollgateCarType() {
            return tollgateCarType;
        }

        public void setTollgateCarType(String tollgateCarType) {
            this.tollgateCarType = tollgateCarType;
        }

        public String getTrafficInfo() {
            return trafficInfo;
        }

        public void setTrafficInfo(String trafficInfo) {
            this.trafficInfo = trafficInfo;
        }
    }

    // Nested class for Departure
    public static class Departure {
        private String name;
        private String lon;
        private String lat;
        private String depSearchFlag;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getDepSearchFlag() {
            return depSearchFlag;
        }

        public void setDepSearchFlag(String depSearchFlag) {
            this.depSearchFlag = depSearchFlag;
        }
    }

    // Nested class for Destination
    public static class Destination {
        private String name;
        private String lon;
        private String lat;
        private String poiId;
        private String rpFlag;
        private String destSearchFlag;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getPoiId() {
            return poiId;
        }

        public void setPoiId(String poiId) {
            this.poiId = poiId;
        }

        public String getRpFlag() {
            return rpFlag;
        }

        public void setRpFlag(String rpFlag) {
            this.rpFlag = rpFlag;
        }

        public String getDestSearchFlag() {
            return destSearchFlag;
        }

        public void setDestSearchFlag(String destSearchFlag) {
            this.destSearchFlag = destSearchFlag;
        }
    }
}