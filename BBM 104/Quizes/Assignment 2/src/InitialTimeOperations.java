import java.time.DateTimeException;
import java.util.ArrayList;
/**
 * InitialTimeOperations is the class that checks all the possibilities that may occur while setting initial time which
 * is a very important task since time flow takes a huge part of this project.
 */
public class InitialTimeOperations {
    /**
     * Sets initial time and returns the current time.
     * Tells whether the program is going to be terminated or not by returning a boolean.
     *
     * @param lines  arraylist that holds many arraylists of commands
     * @param path  path of the file the content is going to be written
     * @return  an arraylist that contains a boolean to see if the program has terminated and the set current time
     */
    public static ArrayList<Object> InitialTimeOp(ArrayList<ArrayList<String>> lines, String path) {
        CurrentTime currentTime = new CurrentTime(null);
        boolean terminated = false;
        for (ArrayList<String> list:lines) {
            String str = String.join("\t", list);
            ioOperations.writer(path, "COMMAND: " + str, true, true);
            try{
                if((lines.get(0).get(0)).equals("SetInitialTime")){
                    currentTime = Commands.SetInitialTime(path, list);
                }else {
                    ioOperations.writer(path, "ERROR: First command must be set initial time! Program is going to terminate!", true, true);
                    terminated = true;
                }
            }catch(DateTimeException e){
                ioOperations.writer(path, "ERROR: Format of the initial date is wrong! Program is going to terminate!", true, true);
                terminated = true;
            }catch(ErroneousCommandException e){
                ioOperations.writer(path, e.getLocalizedMessage(), true, true);
                terminated = true;
            }break;
        }ArrayList<Object> myList = new ArrayList<>();
        myList.add(terminated);
        myList.add(currentTime);
        return myList;
    }
}
