import java.time.LocalDateTime;
/**
 * Class contains specific fields and methods of the smart plug object.
 *
 */

public class SmartPlug extends SmartDevices {
    /**
     * Constructor for Smart Plug
     *
     * @param name  name of the smart plug
     */
    public SmartPlug(String name){

        this.name = name;
    }
    private double ampere;
    private boolean plugin =false;
    private double consumedEnergy;
    private LocalDateTime initialTime;
    private LocalDateTime finalTime;
    /**
     * Accesses the ampere value.
     *
     * @return   ampere value of the smart plug
     */
    public double getAmpere() {
        return ampere;
    }
    /**
     * Modifies the ampere value.
     *
     * @param   ampere for changing the ampere value
     * @throws InvalidNumberException if ampere value is not positive
     */
    public void setAmpere(double ampere) throws InvalidNumberException {
        if(ampere<=0){
            throw new InvalidNumberException("ERROR: Ampere value must be a positive number!");
        }else{
            this.ampere = ampere;
        }
    }
    /**
     * Tells if a device is plugged in or not.
     *
     * @return   true if a device is plugged into the smart plug, false if not
     */
    public boolean isPlugin() {
        return plugin;
    }
    /**
     * Modifies the plug in situation.
     *
     * @param   plugin for changing the plug in/out boolean value
     */
    public void setPlugin(boolean plugin) {
        this.plugin = plugin;
    }
    /**
     * Accesses the consumed energy value.
     *
     * @return   consumed energy value
     */
    public double getConsumedEnergy() {
        return consumedEnergy;
    }
    /**
     * Updates the energy consumption.
     *
     * @param   consumedEnergy for changing the consumed energy value
     */
    public void setConsumedEnergy(double consumedEnergy) {
        this.consumedEnergy = consumedEnergy;
    }
    /**
     * Accesses the initial time the plug was in status on and plug in condition.
     *
     * @return   initial time for duration
     */
    public LocalDateTime getInitialTime() {
        return initialTime;
    }
    /**
     * Updates the initial time after plug is in status on and plug in condition.
     *
     * @param   initialTime for changing the initial time value
     */
    public void setInitialTime(LocalDateTime initialTime) {
        this.initialTime = initialTime;
    }
    /**
     * Accesses the final time the plug was in status on and plug in condition.
     *
     * @return   final time for duration
     */
    public LocalDateTime getFinalTime() {
        return finalTime;
    }
    /**
     * Updates the final time after the plug is off, plug out or both.
     *
     * @param   finalTime for changing the final time value
     */
    public void setFinalTime(LocalDateTime finalTime) {
        this.finalTime = finalTime;
    }
}
