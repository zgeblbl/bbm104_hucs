import java.time.LocalDateTime;
import java.util.Objects;
/**
 * Class contains fields and methods for the smart devices and its subclasses.
 *
 */
public class SmartDevices {
    protected String name;
    protected String status = "off";
    protected LocalDateTime switchTime;
    /**
     * Accesses the name of the device which is specific for every device like a fingerprint.
     *
     * @return   name of the device
     */
    public String getName() {
        return name;
    }
    /**
     * Modifies the device name.
     *
     * @param   name for changing name of the device
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Accesses the on/off status of the device.
     *
     * @return   status of the device
     */
    public String getStatus() {
        return status;
    }
    /**
     * Modifies the device status.
     *
     * @param   status for changing the device status
     * @throws ErroneousCommandException if the new status is not equal to "on" or "off"
     */
    public void setStatus(String status) throws ErroneousCommandException {
        if(!Objects.equals(status, "on") && !Objects.equals(status, "off")){
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }else{
            this.status = status;
        }
    }
    /**
     * Accesses the switch time of the device at which, the device gets turned on/off.
     *
     * @return   switch time of the device
     */
    public LocalDateTime getSwitchTime() {
        return switchTime;
    }
    /**
     * Modifies the device switch time.
     *
     * @param   switchTime for changing the device switch time
     * @param   currentTime for checking if new switch time is valid
     * @throws ReverseTimeException if the switch time is before the current time.
     */
    public void setSwitchTime(LocalDateTime switchTime, LocalDateTime currentTime) throws ReverseTimeException {
        if(switchTime!=null && switchTime.isBefore(currentTime)){
            throw new ReverseTimeException("ERROR: Switch time cannot be in the past!");
        }else{
            this.switchTime = switchTime;
        }

    }
}
