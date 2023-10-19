import java.time.LocalDateTime;
/**
 * Class contains specific fields and methods of the smart camera object
 *
 */
public class SmartCamera extends SmartDevices{
    /**
     * Constructor for Smart Camera
     *
     * @param name  name of the smart camera
     * @param megaBytesPerRecord  megabytes of storage used per record
     * @throws InvalidNumberException if megabyte value is not positive
     */
    public SmartCamera(String name, double megaBytesPerRecord) throws InvalidNumberException {
        this.name = name;
        if(megaBytesPerRecord<=0){
            throw new InvalidNumberException("ERROR: Megabyte value must be a positive number!");
        }else{
            this.megaBytesPerRecord = megaBytesPerRecord;
        }
    }
    private double megaBytesPerRecord;
    private double usedStorage;
    private LocalDateTime initialTime;
    private LocalDateTime finalTime;
    public double getMegaBytesPerRecord() {
        return megaBytesPerRecord;
    }
    /**
     * Modifies the megabytes used per record.
     *
     * @param   megaBytesPerRecord for updating the megabytes used per record
     * @throws InvalidNumberException if megabyte value is not positive
     */
    public void setMegaBytesPerRecord(double megaBytesPerRecord) throws InvalidNumberException {
        if(megaBytesPerRecord<=0){
            throw new InvalidNumberException("ERROR: Megabyte value must be a positive number!");
        }else{
            this.megaBytesPerRecord = megaBytesPerRecord;
        }
    }
    /**
     * Accesses the used storage value.
     *
     * @return   usedStorage for outputs or updates
     */
    public double getUsedStorage() {
        return usedStorage;
    }
    /**
     * Updates the used storage.
     *
     * @param   usedStorage for updating the used storage
     */
    public void setUsedStorage(double usedStorage) {
        this.usedStorage = usedStorage;
    }
    /**
     * Accesses the initial time the camera started using storage
     *
     * @return   initial time for duration calculations
     */
    public LocalDateTime getInitialTime() {
        return initialTime;
    }
    /**
     * Updates the initial time.
     *
     * @param   initialTime for updating the initial time the camera started using storage
     */
    public void setInitialTime(LocalDateTime initialTime) {
        this.initialTime = initialTime;
    }
    /**
     * Accesses the final time before the camera stops using storage.
     *
     * @return   final time for duration calculations
     */
    public LocalDateTime getFinalTime() {
        return finalTime;
    }
    /**
     * Updates the final time.
     *
     * @param   finalTime for updating the final time before the camera stops using storage
     */
    public void setFinalTime(LocalDateTime finalTime) {
        this.finalTime = finalTime;
    }
}
