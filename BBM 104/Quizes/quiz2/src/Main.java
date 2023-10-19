public class Main {
    public static void main(String[] args) {
        String readerPath = args[0];
        String writerPath = args[1];
        ioOperations.writer(writerPath, "Departure order:", false, true);
        TripController controller = new TripController();
        TripSchedule tripSchedule = new TripSchedule();
        controller.DepartureSchedule(tripSchedule, writerPath, readerPath);
        ioOperations.writer(writerPath, "\nArrival order:", true, true);
        controller.ArrivalSchedule(tripSchedule, writerPath, readerPath);
    }
}
