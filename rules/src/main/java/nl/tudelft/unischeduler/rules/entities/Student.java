package nl.tudelft.unischeduler.rules.entities;

import java.sql.Timestamp;

public class Student {
    
    private String netId;
    private boolean interested;
    private boolean recovered;

    /**
     * Constructor for Student.
     * 
     * @param netId the student's netId
     * @param interested whether or not they are interested in attending lectures
     * @param recovered whether or not they have recorvered from being sick (with corona)
     */
    public Student(String netId, boolean interested, boolean recovered) {
        this.netId = netId;
        this.interested = interested;
        this.recovered = recovered;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public boolean isInterested() {
        return interested;
    }

    public void setInterested(boolean interested) {
        this.interested = interested;
    }

    public boolean isRecovered() {
        return recovered;
    }

    public void setRecovered(boolean recovered) {
        this.recovered = recovered;
    }
}
