import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class TripController implements ArrivalController, DepartureController{
    public void DepartureSchedule(TripSchedule tripSchedule, String writerPath, String readerPath){
        Trip[] trips = TripSchedule.arrayBuilder(readerPath);
        ArrayList<String> departureTimes = new ArrayList<>();
        Trip[] DepartureSorted = new Trip[trips.length];
        for (Trip trip : trips) {
            if(trip != null ) {
                departureTimes.add(trip.getDepartureTimeStr());
            }
        }
        Collections.sort(departureTimes);
        for(int i =0; i< departureTimes.size() && departureTimes.get(i) != null; i++) {
            for(int j = 0; trips[j] != null; j++) {
                if(Objects.equals(departureTimes.get(i), trips[j].getDepartureTimeStr()) && !Arrays.asList(DepartureSorted).contains(trips[j])) {
                    DepartureSorted[i] = trips[j];
                    for(int k = 0; k < departureTimes.size(); k++){
                        if(Objects.equals(trips[j].getDepartureTimeStr(), trips[k].getDepartureTimeStr()) && k!=j) {
                            trips[j].setState("DELAYED");
                            trips[k].setState("DELAYED");
                        }
                    }break;
                }
            }
        }
        for(Trip trip : DepartureSorted){
            if(trip!=null){
                ioOperations.writer(writerPath, trip.getTripName()+" depart at "+trip.getDepartureTimeStr()+"\tTrip State:"+trip.getState(), true, true);
            }
        }
    }
    public void ArrivalSchedule(TripSchedule tripSchedule, String writerPath, String readerPath) {
        Trip[] trips = TripSchedule.arrayBuilder(readerPath);
        ArrayList<String> arrivalTimes = new ArrayList<>();
        Trip[] ArrivalSorted = new Trip[trips.length];
        for (Trip trip : trips) {
            if(trip != null ) {
                arrivalTimes.add(trip.getArrivalTimeStr());
            }
        }
        Collections.sort(arrivalTimes);
        for(int i =0; i< arrivalTimes.size() && arrivalTimes.get(i) != null; i++) {
            for(int j = 0; trips[j] != null; j++) {
                if(Objects.equals(arrivalTimes.get(i), trips[j].getArrivalTimeStr()) && !Arrays.asList(ArrivalSorted).contains(trips[j])) {
                    ArrivalSorted[i] = trips[j];
                    for(int k = 0; k < arrivalTimes.size(); k++){
                        if(Objects.equals(trips[j].getArrivalTimeStr(), trips[k].getArrivalTimeStr()) && k!=j) {
                            trips[j].setState("DELAYED");
                            trips[k].setState("DELAYED");
                        }
                    }break;
                }
            }
        }
        for(Trip trip : ArrivalSorted){
            if(trip!=null){
                ioOperations.writer(writerPath, trip.getTripName()+" arrive at "+trip.getArrivalTimeStr()+"\tTrip State:"+trip.getState(), true, true);
            }
        }
    }
}
