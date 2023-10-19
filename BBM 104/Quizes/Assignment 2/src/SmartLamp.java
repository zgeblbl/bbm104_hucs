/**
 * Class contains specific fields and methods of the smart lamp object
 *
 */
public class SmartLamp extends SmartDevices {
    /**
     * Constructor for Smart Lamp
     *
     * @param name  name of the smart lamp
     */
    public SmartLamp(String name){
        this.name = name;
    }
    protected int kelvin = 4000;
    protected int brightness = 100;
    /**
     * Accesses the kelvin value.
     *
     * @return   kelvin value of the smart lamp
     */
    public int getKelvin() {  //2000-6500
        return kelvin;
    }
    /**
     * Modifies the kelvin value.
     *
     * @param   kelvin for updating the kelvin value
     * @param   brightness for only changing the kelvin value if brightness is in the correct range
     * @throws InvalidNumberException if kelvin value is not in the required range
     */
    public void setKelvin(int kelvin, int brightness) throws InvalidNumberException {
        if(2000<=kelvin && kelvin<=6500){
            if(0<=brightness && brightness<=100){
                this.kelvin = kelvin;
            }
        }else{
            throw new InvalidNumberException("ERROR: Kelvin value must be in range of 2000K-6500K!");
        }
    }
    /**
     * Accesses the brightness value.
     *
     * @return   brightness value of the smart lamp
     */
    public int getBrightness() {
        return brightness;
    }
    /**
     * Modifies the brightness value.
     *
     * @param   brightness for updating the brightness value
     * @throws InvalidNumberException if brightness value is not in the required range
     */
    public void setBrightness(int brightness) throws InvalidNumberException {
        if(0<=brightness && brightness<=100){
            this.brightness = brightness;
        }else{
            throw new InvalidNumberException("ERROR: Brightness must be in range of 0%-100%!");
        }
    }
}
