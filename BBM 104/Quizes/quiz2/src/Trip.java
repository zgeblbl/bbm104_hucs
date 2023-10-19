import java.time.LocalTime;

public class Trip {
    public Trip(String tripName, LocalTime departureTime, LocalTime arrivalTime, int duration, String state,
                String arrivalTimeStr, String departureTimeStr){
        this.tripName = tripName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.state = state;
        // arrivalTimeStr and departureTimeStr were added by me to make the sorting easier.
        this.arrivalTimeStr = arrivalTimeStr;
        this.departureTimeStr = departureTimeStr;

    }
    private String tripName;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int duration;
    private String state;
    private String arrivalTimeStr;
    private String departureTimeStr;

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime){
        this.arrivalTime = arrivalTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTripName() {
        return tripName;
    }

    public LocalTime arrivalTimeCalc(LocalTime departureTime, int duration){
        return departureTime.plusMinutes(duration);
    }

    public String getArrivalTimeStr() {
        return arrivalTimeStr;
    }

    public void setArrivalTimeStr(String arrivalTimeStr) {
        this.arrivalTimeStr = arrivalTimeStr;
    }

    public String getDepartureTimeStr() {
        return departureTimeStr;
    }
}
