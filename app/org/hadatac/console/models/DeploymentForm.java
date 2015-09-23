package org.hadatac.console.models;

import java.util.Date;

public class DeploymentForm {

    public String platform;
    public String hasFirstCoordinate;
    public String hasSecondCoordinate;
    public String instrument;
    public String detector;
    public String startDateTime;
    public String endDateTime;
 
    public String getPlatform() {
    	return platform;
    }
    
    public void setPlatform(String platform) {
    	this.platform = platform;
    }

    public String getHasFirstCoordinate() {
    	return hasFirstCoordinate;
    }
    
    public void setHasFirstCoordinate(String hasFirstCoordinate) {
    	this.hasFirstCoordinate = hasFirstCoordinate;
    }

    public String getHasSecondCoordinate() {
    	return hasSecondCoordinate;
    }
    
    public void setHasSecondCoordinate(String hasSecondCoordinate) {
    	this.hasSecondCoordinate = hasSecondCoordinate;
    }

    public String getInstrument() {
    	return instrument;
    }
    
    public void setInstrument(String instrument) {
    	this.instrument = instrument;
    }

    public String getDetector() {
    	return detector;
    }
    
    public void setDetector(String detector) {
    	this.detector = detector;
    }

    public String getStartDateTime() {
    	return startDateTime;
    }
    
    public void setStartDateTime(String startDateTime) {
    	this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
    	return endDateTime;
    }
    
    public void setEndDateTime(String endDateTime) {
    	this.endDateTime = endDateTime;
    }


}