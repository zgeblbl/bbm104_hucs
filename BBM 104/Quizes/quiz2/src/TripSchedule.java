import java.time.LocalTime;
import java.util.ArrayList;

public class TripSchedule {
    public static Trip[] arrayBuilder(String path){
        String[] input_str = ioOperations.reader(path);
        ArrayList<String[]> arraylist = new ArrayList<>();
        String[][] trainData = new String[0][];
        assert input_str != null;
        for (String line: input_str){
            String[] elements = line.split("\t");
            arraylist.add(elements);
            trainData = arraylist.toArray(trainData);
        }
        Trip[] trips = new Trip[100];
        ArrayList<Trip> arraylist2 = new ArrayList<>();
        for(String[] elements: trainData){
            Trip trip = new Trip(elements[0], LocalTime.parse(elements[1]), null, Integer.parseInt(elements[2]),
                    "IDLE", null, elements[1]);
            trip.setArrivalTime(trip.arrivalTimeCalc(LocalTime.parse(elements[1]), Integer.parseInt(elements[2])));
            trip.setArrivalTimeStr((trip.arrivalTimeCalc(LocalTime.parse(elements[1]), Integer.parseInt(elements[2]))).toString());
            arraylist2.add(trip);
            trips = arraylist2.toArray(trips);
        }
        return(trips);
    }
}
