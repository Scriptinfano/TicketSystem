package src.entity;

import java.util.Date;

public class Ticket {
    private int accountDiscountSequence;
    private int ticketDiscountSequence;
    private int ticketId;
    private int trainServiceId;
    private String startStationName;
    private String endStationName;
    private Date startTime;
    private int wagonNum;
    private int seatNum;

    private Ticket(int accountDiscountSequence, int ticketDiscountSequence, int ticketId, int trainserviceId, String startStationName, String endStationName, Date startTime, int wagonNum, int seatNum) {
        this.accountDiscountSequence = accountDiscountSequence;
        this.ticketDiscountSequence = ticketDiscountSequence;
        this.ticketId = ticketId;
        this.trainServiceId = trainserviceId;
        this.startStationName = startStationName;
        this.endStationName = endStationName;
        this.startTime = startTime;
        this.wagonNum = wagonNum;
        this.seatNum = seatNum;
    }

    public int getAccountDiscountSequence() {
        return accountDiscountSequence;
    }

    public void setAccountDiscountSequence(int accountDiscountSequence) {
        this.accountDiscountSequence = accountDiscountSequence;
    }

    public int getTicketDiscountSequence() {
        return ticketDiscountSequence;
    }

    public void setTicketDiscountSequence(int ticketDiscountSequence) {
        this.ticketDiscountSequence = ticketDiscountSequence;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
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

    public int getWagonNum() {
        return wagonNum;
    }

    public void setWagonNum(int wagonNum) {
        this.wagonNum = wagonNum;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }
}
