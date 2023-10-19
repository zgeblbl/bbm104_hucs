import java.time.LocalDateTime;
/**
 * Class contains specific fields and methods of the current time object
 *
 */
public class CurrentTime{
    /**
     * Constructor for Current Time
     *
     * @param time  year-month-day-hour-minute-second of the current time
     */
    public CurrentTime(LocalDateTime time){

        this.time = time;

    }
    private LocalDateTime time;
    /**
     * Accesses the current time.
     *
     * @return   time for time related operations
     */
    public LocalDateTime getCurrentTime() {
        return time;
    }
    /**
     * Updates the current time.
     *
     * @param   time for changing the current time
     * @throws ReverseTimeException if the new time is same as or before the current time.
     */
    public void setCurrentTime(LocalDateTime time) throws ReverseTimeException {
        if(time.isBefore(this.time)){
            throw new ReverseTimeException("ERROR: Time cannot be reversed!");
        }else if (time.equals(this.time)){
            throw new ReverseTimeException("ERROR: There is nothing to change!");
        }else{
            this.time = time;
        }
    }
}
