package src.entity;

public class Station {
    private int discountSequence;
    private String stationName;

    public Station(int discountSequence, String stationName) {
        this.discountSequence = discountSequence;
        this.stationName = stationName;
    }

    public String getStationName(){
        return stationName;
    }

    public void setStationName(String stationName){
        this.stationName = stationName;
    }
}
