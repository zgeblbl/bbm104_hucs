/**
 * Class contains specific fields and methods of the smart color lamp object
 *
 */
public class SmartColorLamp extends SmartLamp{
    /**
     * Constructor for Smart Color Lamp
     *
     * @param name  name of the smart color lamp
     */
    public SmartColorLamp(String name) {
        super(name);
    }
    private int colorCode;
    private boolean colorFeature;
    private String colorCodeStr;
    /**
     * Modifies the color code.
     *
     * @param   colorCode for updating the color code
     * @param   brightness for only changing the color code if brightness is in the correct range
     * @throws InvalidNumberException if color code is not in the required range
     */
    public void setColorCode(int colorCode, int brightness) throws InvalidNumberException {
        if(0x000000<=colorCode && colorCode<=0xFFFFFF){
            if(0<=brightness && brightness<=100){
                this.colorCode = colorCode;
            }
        }else{
            throw new InvalidNumberException("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");
        }
    }
    /**
     * Changes the color feature to on/off.
     *
     * @param   colorFeature for updating the color feature in boolean
     * @param   brightness for only changing the color mode condition if brightness is in the correct range
     */
    public void setColorFeature(boolean colorFeature, int brightness) {
        if(0<=brightness && brightness<=100){
            this.colorFeature = colorFeature;
        }
    }
    /**
     * Accesses if the color mode is on or not.
     *
     * @return   the color mode in boolean
     */
    public boolean getColorFeature() {
        return colorFeature;
    }
    /**
     * Accesses the color code string for printing to the output file.
     *
     * @return   the color code in string format
     */
    public String getColorCodeStr() {
        return colorCodeStr;
    }
    /**
     * Modifies the color code in string format
     *
     * @param   colorCodeStr for updating the color code string
     * @param   brightness for only changing the color code string if brightness is in the correct range
     */
    public void setColorCodeStr(String colorCodeStr, int brightness) {
        if(0<=brightness && brightness<=100){
            this.colorCodeStr = colorCodeStr;
        }
    }
}
