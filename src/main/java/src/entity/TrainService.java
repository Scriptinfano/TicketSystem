package src.entity;

import java.util.Date;

public class TrainService {
    private int discountSequence;
    private int trainServiceId;
    private String startStationName;
    private String endStationName;
    private Date startTime;
    private int personNum;

    public TrainService(int discountSequence, int trainServiceId, String startStationName, String endStationName, Date startTime, int personNum) {
        this.discountSequence = discountSequence;
        this.trainServiceId = trainServiceId;
        this.startStationName = startStationName;
        this.endStationName = endStationName;
        this.startTime = startTime;
        this.personNum = personNum;
    }

    public int getDiscountSequence() {
        return discountSequence;
    }

    public void setDiscountSequence(int discountSequence) {
        this.discountSequence = discountSequence;
    }

    public int getTrainServiceId() {
        return trainServiceId;
    }

    public void setTrainServiceId(int trainServiceId) {
        this.trainServiceId = trainServiceId;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }
}
