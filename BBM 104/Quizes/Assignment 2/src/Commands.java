import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
/**
 * Commands is the base class that contains all the necessary methods to be called from Main class when required.
 */
public class Commands {
    /**
     * Creates and adds smart device to the arraylist.
     *
     * @param smartDevices  arraylist that the smart device is added to
     * @param list  arraylist of commands, one line of the input file
     * @param time  for setting initial time of plug and camera
     * @throws  RepetitionException if name of the device-to-be-added is already taken
     * @throws  InvalidNumberException if format of the parameters of the setters are incorrect
     * @throws  ErroneousCommandException if format of the given command is incorrect
     */
    public static void Add(ArrayList<SmartDevices> smartDevices, ArrayList<String> list, CurrentTime time)
            throws RepetitionException, InvalidNumberException, ErroneousCommandException {
        switch (list.get(1)) {
            case "SmartPlug":
                if(5<list.size() || list.size()<3){
                    throw new ErroneousCommandException("ERROR: Erroneous command!");
                }for (SmartDevices plug : smartDevices) {
                    if (plug.getName().equals(list.get(2))) {
                        throw new RepetitionException("ERROR: There is already a smart device with same name!");
                    }
                }SmartPlug plug = new SmartPlug(list.get(2));
                if (4 < list.size()) {
                    plug.setAmpere(Double.parseDouble(list.get(4)));
                    plug.setPlugin(true);
                }if (3 < list.size()) {
                    plug.setStatus((list.get(3)).toLowerCase());
                }smartDevices.add(plug);
                if(plug.getStatus().equals("on")&&plug.isPlugin()){
                    plug.setInitialTime(time.getCurrentTime());
                }
                break;
            case "SmartCamera":
                if(6<list.size() || list.size()<4){
                    throw new ErroneousCommandException("ERROR: Erroneous command!");
                }for (SmartDevices camera : smartDevices) {
                    if (camera.getName().equals(list.get(2))) {
                        throw new RepetitionException("ERROR: There is already a smart device with same name!");
                    }
                }SmartCamera camera = new SmartCamera(list.get(2), Double.parseDouble(list.get(3)));
                if (4 < list.size()) {
                    camera.setStatus((list.get(4).toLowerCase()));
                }if (3 < list.size()) {
                camera.setMegaBytesPerRecord(Double.parseDouble(list.get(3)));
                }smartDevices.add(camera);
                if(camera.getStatus().equals("on")){
                    camera.setInitialTime(time.getCurrentTime());
                }
                break;
            case "SmartLamp":
                if(list.size()!=3 && list.size()!=4 && list.size()!=6){
                    throw new ErroneousCommandException("ERROR: Erroneous command!");
                }for (SmartDevices lamp : smartDevices) {
                    if (lamp.getName().equals(list.get(2))) {
                        throw new RepetitionException("ERROR: There is already a smart device with same name!");
                    }
                }SmartLamp lamp = new SmartLamp(list.get(2));
                if (5 < list.size()) {
                    try{
                        lamp.setKelvin(Integer.parseInt(list.get(4)), Integer.parseInt(list.get(5)));
                        lamp.setBrightness(Integer.parseInt(list.get(5)));
                    }catch(NumberFormatException e){
                        throw new ErroneousCommandException("ERROR: Erroneous command!");
                    }

                }if (3 < list.size()) {
                    lamp.setStatus((list.get(3)).toLowerCase());
                }smartDevices.add(lamp);
                break;
            case "SmartColorLamp":
                if(list.size()!=3 && list.size()!=4 && list.size()!=6){
                    throw new ErroneousCommandException("ERROR: Erroneous command!");
                }for (SmartDevices clamp : smartDevices) {
                    if (clamp.getName().equals(list.get(2))) {
                        throw new RepetitionException("ERROR: There is already a smart device with same name!");
                    }
                }SmartColorLamp clamp = new SmartColorLamp(list.get(2));
                if (list.size() == 6) {
                    try {
                        if(list.get(4).startsWith("0x")){
                            clamp.setColorCode(Integer.decode(list.get(4)), Integer.parseInt(list.get(5)));
                            clamp.setColorFeature(true, Integer.parseInt(list.get(5)));
                            clamp.setColorCodeStr(list.get(4), Integer.parseInt(list.get(5)));
                        }else {
                            clamp.setKelvin(Integer.parseInt(list.get(4)), Integer.parseInt(list.get(5)));
                            clamp.setColorFeature(false, Integer.parseInt(list.get(5)));
                        }clamp.setBrightness(Integer.parseInt(list.get(5)));
                    } catch (NumberFormatException e) {
                        throw new ErroneousCommandException("ERROR: Erroneous command!");
                    }
                }if (3 < list.size()) {
                    clamp.setStatus((list.get(3)).toLowerCase());
                }smartDevices.add(clamp);
                break;
        }
    }
    /**
     * Switches smart device on or off.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @param time  for setting initial and final time of plug and camera objects
     * @throws  ErroneousCommandException if format of the command is incorrect
     * @throws ReverseTimeException    if an earlier time is being tried to set to the current time
     */
    public static void Switch(String path, ArrayList<SmartDevices> smartDevices, ArrayList<String> list, CurrentTime time)
            throws ErroneousCommandException, ReverseTimeException {
        if(list.size() != 3) {
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }
        boolean deviceExists = false;
        for (SmartDevices device : smartDevices) {
            if (device.getName().equals(list.get(1))) {
                deviceExists = true;
                if (!device.getStatus().equals((list.get(2)).toLowerCase())) {
                    device.setStatus((list.get(2)).toLowerCase());
                    device.setSwitchTime(null, time.getCurrentTime());
                    if(device.getStatus().equals("off") && device instanceof SmartPlug && ((SmartPlug) device).isPlugin()){
                        ((SmartPlug) device).setFinalTime(time.getCurrentTime());
                        ((SmartPlug) device).setConsumedEnergy(((SmartPlug) device).getConsumedEnergy()
                                +(((SmartPlug) device).getAmpere()*220*((Duration.between(((SmartPlug) device)
                                .getInitialTime(), ((SmartPlug) device).getFinalTime()).toMinutes())/60.00)));
                    }else if(device.getStatus().equals("on") && device instanceof SmartPlug && ((SmartPlug) device).isPlugin()){
                        ((SmartPlug) device).setInitialTime(time.getCurrentTime());
                    }else if(device.getStatus().equals("off") && device instanceof SmartCamera){
                        ((SmartCamera) device).setFinalTime(time.getCurrentTime());
                        ((SmartCamera) device).setUsedStorage(((SmartCamera) device).getUsedStorage()
                                +(((SmartCamera) device).getMegaBytesPerRecord()*(Duration.between(((SmartCamera) device)
                                .getInitialTime(), ((SmartCamera) device).getFinalTime()).toMinutes())));
                    }else if(device.getStatus().equals("on") && device instanceof SmartCamera) {
                        ((SmartCamera) device).setInitialTime(time.getCurrentTime());
                    }
                }else{
                    ioOperations.writer(path, "ERROR: This device is already switched "+device.getStatus()+"!"
                            , true, true);
                }
            }
        }if (!deviceExists) {
            ioOperations.writer(path, "ERROR: There is not such a device!", true, true);
        }
    }
    /**
     * Changes name of the smart device.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @throws  ErroneousCommandException if format of the command is incorrect
     */
    public static void ChangeName(String path, ArrayList<SmartDevices> smartDevices, ArrayList<String> list)
            throws ErroneousCommandException {
        if(list.size()!=3) {
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }
        boolean deviceExists = false;
        boolean nameDuplication = false;
        if (list.get(1).equals(list.get(2))) {
            ioOperations.writer(path, "ERROR: Both of the names are the same, nothing changed!", true, true);
        } else {
            for (SmartDevices device : smartDevices) {
                if (device.getName().equals(list.get(1))) {
                    deviceExists = true;
                    for (SmartDevices device2 : smartDevices) {
                        if (!device.getName().equals(list.get(2)) && device2.getName().equals(list.get(2))) {
                            nameDuplication = true;
                            ioOperations.writer(path, "ERROR: There is already a smart device with same name!"
                                    , true, true);
                            break;
                        }
                    }if (!nameDuplication) {
                        device.setName(list.get(2));
                    }
                }
            }if (!deviceExists) {
                ioOperations.writer(path, "ERROR: There is not such a device!", true, true);
            }
        }
    }
    /**
     * Removes smart device from the system.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @param time  for setting final time of smart camera and smart plug
     * @throws  ErroneousCommandException if format of the command is incorrect
     */
    public static void Remove(String path, ArrayList<SmartDevices> smartDevices, ArrayList<String> list
            , CurrentTime time) throws ErroneousCommandException {
        if(list.size()!=2) {
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }boolean deviceExists = false;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumFractionDigits(2);
        for (SmartDevices device : smartDevices) {
            if (device.getName().equals(list.get(1))) {
                deviceExists = true;
                String className = device.getClass().getSimpleName().replaceAll("([A-Z])", " $1")
                        .substring(1);
                ioOperations.writer(path, "SUCCESS: Information about removed smart device is as follows:"
                        , true, true);
                String switchTimeStr;
                if(device.getSwitchTime() == null) {
                    switchTimeStr = "null";
                }else{
                    switchTimeStr = device.getSwitchTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
                }
                if (device instanceof SmartCamera && device.getStatus().equals(("on"))) {
                    ((SmartCamera) device).setFinalTime(time.getCurrentTime());
                    ((SmartCamera) device).setUsedStorage(((SmartCamera) device).getUsedStorage()+(((SmartCamera) device)
                            .getMegaBytesPerRecord()*(Duration.between(((SmartCamera) device).getInitialTime()
                            , ((SmartCamera) device).getFinalTime()).toMinutes())));
                }else if (device instanceof SmartPlug && device.getStatus().equals(("on")) && ((SmartPlug) device).isPlugin()) {
                    ((SmartPlug) device).setFinalTime(time.getCurrentTime());
                    ((SmartPlug) device).setConsumedEnergy(((SmartPlug) device).getConsumedEnergy()
                            + (((SmartPlug) device).getAmpere() * 220 * ((Duration.between(((SmartPlug) device)
                            .getInitialTime(), ((SmartPlug) device).getFinalTime()).toMinutes()) / 60.00)));
                }
                device.setStatus("off");
                if (device instanceof SmartCamera) {
                    ioOperations.writer(path, className+" "+device.getName()+""+" is "+device.getStatus()
                            +" and used "+df.format(((SmartCamera) device).getUsedStorage())
                            +" MB of storage so far (excluding current status), and its time to switch its status is "
                            +switchTimeStr+".", true, true);
                }else if (device instanceof SmartPlug){
                    ioOperations.writer(path, className+" "+device.getName()+""+" is "+device.getStatus()+
                            " and consumed "+df.format(((SmartPlug) device).getConsumedEnergy())
                            +"W so far (excluding current device), and its time to switch its status is "+switchTimeStr
                            +".", true, true);
                }else if(device instanceof SmartColorLamp){
                    if (((SmartColorLamp) device).getColorFeature()){
                        ioOperations.writer(path, className+" "+device.getName()+""+" is "+device.getStatus()
                                +" and its color value is "+((SmartColorLamp) device).getColorCodeStr()+" with "
                                +((SmartColorLamp) device).getBrightness()+"% brightness, and its time to switch its status is "
                                +switchTimeStr+".", true, true);
                    }else{
                        ioOperations.writer(path, className+" "+device.getName()+""+" is "+device.getStatus()
                                +" and its color value is "+((SmartColorLamp) device).getKelvin()+"K with "
                                +((SmartColorLamp) device).getBrightness()+"% brightness, and its time to switch its status is "
                                +switchTimeStr+".", true, true);
                    }
                } else if (device instanceof SmartLamp) {
                    ioOperations.writer(path, className+" "+device.getName()+""+" is "+device.getStatus()
                            +" and its kelvin value is "+((SmartLamp) device).getKelvin()+"K with "+((SmartLamp) device)
                            .getBrightness()+"% brightness, and its time to switch its status is "+switchTimeStr+"."
                            , true, true);
                }smartDevices.remove(device);
                break;
            }
        }if (!deviceExists) {
            ioOperations.writer(path, "ERROR: There is not such a device!", true, true);
        }
    }
    /**
     * Sets kelvin value of the smart lamp.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @throws  InvalidNumberException if kelvin number is out of the required range
     * @throws  ErroneousCommandException if format of the command or the kelvin value are incorrect
     */
    public static void SetKelvin(String path, ArrayList<SmartDevices> smartDevices, ArrayList<String> list)
            throws InvalidNumberException, ErroneousCommandException {
        if(list.size()!=3) {
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }
        boolean deviceExists = false;
        for (SmartDevices device : smartDevices) {
            if (device.getName().equals(list.get(1))) {
                deviceExists = true;
                if (device instanceof SmartLamp) {
                    try{
                        ((SmartLamp) device).setKelvin(Integer.parseInt(list.get(2)), ((SmartLamp) device).getBrightness());
                    }catch(NumberFormatException e){
                        throw new ErroneousCommandException("ERROR: Erroneous command!");
                    }

                } else {
                    ioOperations.writer(path, "ERROR: This device is not a smart lamp!", true, true);
                }break;
            }
        }if (!deviceExists) {
            ioOperations.writer(path, "ERROR: There is not such a device!", true, true);
        }
    }
    /**
     * Sets brightness value of the smart lamp.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @throws  InvalidNumberException if brightness value is out of the required range
     * @throws  ErroneousCommandException if format of the command or the brightness value are incorrect
     */
    public static void SetBrightness(String path, ArrayList<SmartDevices> smartDevices, ArrayList<String> list)
            throws InvalidNumberException, ErroneousCommandException {
        if(list.size()!=3) {
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }
        boolean deviceExists = false;
        for (SmartDevices device : smartDevices) {
            if (device.getName().equals(list.get(1))) {
                deviceExists = true;
                if (device instanceof SmartLamp) {
                    try{
                        ((SmartLamp) device).setBrightness(Integer.parseInt(list.get(2)));
                    }catch(NumberFormatException e){
                        throw new ErroneousCommandException("ERROR: Erroneous command!");
                    }
                } else {
                    ioOperations.writer(path, "ERROR: This device is not a smart lamp!", true, true);
                }break;
            }
        }if (!deviceExists) {
            ioOperations.writer(path, "ERROR: There is not such a device!", true, true);
        }
    }
    /**
     * Sets kelvin and brightness values of the smart lamp.
     * Turns off color feature if device is a smart color lamp.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @throws  InvalidNumberException if kelvin or brightness values are out of the required range
     * @throws  ErroneousCommandException if format of the command or the kelvin/brightness values are incorrect
     */
    public static void SetWhite(String path, ArrayList<SmartDevices> smartDevices, ArrayList<String> list)
            throws InvalidNumberException, ErroneousCommandException {
        if(list.size()!=4) {
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }
        boolean deviceExists = false;
        for (SmartDevices device : smartDevices) {
            if (device.getName().equals(list.get(1))) {
                deviceExists = true;
                if (device instanceof SmartLamp) {
                    try{
                        if(device instanceof SmartColorLamp){
                            ((SmartColorLamp) device).setKelvin(Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                            ((SmartColorLamp) device).setBrightness(Integer.parseInt(list.get(3)));
                            ((SmartColorLamp) device).setColorFeature(false, Integer.parseInt(list.get(3)));
                        }else{
                            ((SmartLamp) device).setKelvin(Integer.parseInt(list.get(2)), Integer.parseInt(list.get(3)));
                            ((SmartLamp) device).setBrightness(Integer.parseInt(list.get(3)));
                        }
                    }catch(NumberFormatException e){
                        throw new ErroneousCommandException("ERROR: Erroneous command!");
                    }
                } else {
                    ioOperations.writer(path, "ERROR: This device is not a smart lamp!", true, true);
                }break;
            }
        }if (!deviceExists) {
            ioOperations.writer(path, "ERROR: There is not such a device!", true, true);
        }
    }
    /**
     * Sets color code and brightness values of the smart color lamp.
     * Turns on color feature if it was turned off.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @throws  InvalidNumberException if color code or brightness values are out of the required range
     * @throws  ErroneousCommandException if format of the command or the color code/brightness values are incorrect
     */
    public static void SetColor(String path, ArrayList<SmartDevices> smartDevices, ArrayList<String> list)
            throws InvalidNumberException, ErroneousCommandException {
        if(list.size()!=4) {
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }
        boolean deviceExists = false;
        for (SmartDevices device : smartDevices) {
            if (device.getName().equals(list.get(1))) {
                deviceExists = true;
                if (device instanceof SmartColorLamp) {
                    try{
                        ((SmartColorLamp) device).setColorCode(Integer.decode(list.get(2)), Integer.parseInt(list.get(3)));
                        ((SmartColorLamp) device).setColorFeature(true, Integer.parseInt(list.get(3)));
                        ((SmartColorLamp) device).setColorCodeStr(list.get(2), Integer.parseInt(list.get(3)));
                        ((SmartColorLamp) device).setBrightness(Integer.parseInt(list.get(3)));
                    }catch(NumberFormatException e){
                        throw new ErroneousCommandException("ERROR: Erroneous command!");
                    }

                } else {
                    ioOperations.writer(path, "ERROR: This device is not a smart color lamp!", true, true);
                }break;
            }
        }if (!deviceExists) {
            ioOperations.writer(path, "ERROR: There is not such a device!", true, true);
        }
    }
    /**
     * Sets color code value of the smart color lamp.
     * Turns on color feature if it was turned off.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @throws  InvalidNumberException if color code value is out of the required range
     * @throws  ErroneousCommandException if format of the command or the color code value are incorrect
     */
    public static void SetColorCode(String path, ArrayList<SmartDevices> smartDevices, ArrayList<String> list)
            throws InvalidNumberException, ErroneousCommandException {
        if(list.size()!=3) {
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }boolean deviceExists = false;
        for (SmartDevices device : smartDevices) {
            if (device.getName().equals(list.get(1))) {
                deviceExists = true;
                if (device instanceof SmartColorLamp) {
                    try {
                        ((SmartColorLamp) device).setColorCode(Integer.decode(list.get(2)), ((SmartColorLamp) device).getBrightness());
                        ((SmartColorLamp) device).setColorFeature(true, ((SmartColorLamp) device).getBrightness());
                        ((SmartColorLamp) device).setColorCodeStr(list.get(2), ((SmartColorLamp) device).getBrightness());
                    } catch (NumberFormatException ex) {
                        throw new ErroneousCommandException("ERROR: Erroneous command!");
                    }
                }else {
                    ioOperations.writer(path, "ERROR: This device is not a smart color lamp!", true, true);
                }break;
            }
        }if (!deviceExists) {
            ioOperations.writer(path, "ERROR: There is not such a device!", true, true);
        }
    }
    /**
     * Plugs in to the given smart plug with the given ampere value.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @param time  for setting initial time of the smart plug
     * @throws  InvalidNumberException if ampere value is not positive
     * @throws  ErroneousCommandException if format of the command is incorrect
     */
    public static void PlugIn(String path, ArrayList<SmartDevices> smartDevices, ArrayList<String> list, CurrentTime time)
            throws InvalidNumberException, ErroneousCommandException {
        if(list.size()!=3) {
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }
        boolean deviceExists = false;
        for (SmartDevices device : smartDevices) {
            if (device.getName().equals(list.get(1))) {
                deviceExists = true;
                if (device instanceof SmartPlug) {
                    if(((SmartPlug) device).isPlugin()){
                        ioOperations.writer(path, "ERROR: There is already an item plugged in to that plug!"
                                , true, true);
                    }else{
                        ((SmartPlug) device).setAmpere(Double.parseDouble(list.get(2)));
                        ((SmartPlug) device).setPlugin(true);
                        if(device.getStatus().equals("on")){
                            ((SmartPlug) device).setInitialTime(time.getCurrentTime());
                        }
                    }
                }else {
                    ioOperations.writer(path, "ERROR: This device is not a smart plug!", true, true);
                }break;
            }
        }if (!deviceExists) {
            ioOperations.writer(path, "ERROR: There is not such a device!", true, true);
        }
    }
    /**
     * Plugs out of the given smart plug.
     * Calculates energy consumption of the smart plug, if a device was plugged in to the plug and the plug was
     * switched on.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @param time  for setting the final time of the smart plug
     * @throws  ErroneousCommandException if format of the command is incorrect
     */
    public static void PlugOut(String path, ArrayList<SmartDevices> smartDevices, ArrayList<String> list, CurrentTime time)
            throws ErroneousCommandException {
        if(list.size()!=2) {
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }
        boolean deviceExists = false;
        for (SmartDevices device : smartDevices) {
            if (device.getName().equals(list.get(1))) {
                deviceExists = true;
                if (device instanceof SmartPlug) {
                    if(!(((SmartPlug) device).isPlugin())){
                        ioOperations.writer(path, "ERROR: This plug has no item to plug out from that plug!"
                                , true, true);
                    }else{
                        ((SmartPlug) device).setPlugin(false);
                        if(device.getStatus().equals("on")){
                            ((SmartPlug) device).setFinalTime(time.getCurrentTime());
                            ((SmartPlug) device).setConsumedEnergy(((SmartPlug) device).getConsumedEnergy()
                                    +(((SmartPlug) device).getAmpere()*220*((Duration.between(((SmartPlug) device)
                                    .getInitialTime(), ((SmartPlug) device).getFinalTime()).toMinutes())/60.00)));
                        }
                    }
                }else {
                    ioOperations.writer(path, "ERROR: This device is not a smart plug!", true, true);
                }break;
            }
        }if (!deviceExists) {
            ioOperations.writer(path, "ERROR: There is not such a device!", true, true);
        }
    }
    /**
     * Skips given amount of minutes and changes the current time.
     * Sets initial and final times of plugs and cameras and does the necessary calculations.
     * Keeps track of the devices that got switched after the skipping and sorts them for the ZReport device order.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @param switchedList  keeps devices that got switched after the skipping, it is sorted
     * @param time  for updating the current time and setting initial and final times of the smart plug and camera
     * @throws  ReverseTimeException    if time is being set to an earlier time
     * @throws  ErroneousCommandException   if format of the command is incorrect
     */
    public static void SkipMinutes(String path, ArrayList<SmartDevices> smartDevices
            , ArrayList<String> list, ArrayList<SmartDevices> switchedList, CurrentTime time)
            throws ReverseTimeException, ErroneousCommandException {
        if(list.size()!=2){
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }
        try {
            if (Integer.parseInt(list.get(1)) < 0) {
                throw new ReverseTimeException("ERROR: Time cannot be reversed!");
            } else if (Integer.parseInt(list.get(1)) == 0) {
                ioOperations.writer(path, "ERROR: There is nothing to skip!", true, true);
            }else{
                time.setCurrentTime(time.getCurrentTime().plusMinutes(Integer.parseInt(list.get(1))));
                for(SmartDevices device:smartDevices){
                    if(device.getSwitchTime()!=null && device.getSwitchTime().isBefore(time.getCurrentTime())){
                        if(!(switchedList.contains(device))){
                            switchedList.add(device);
                        }
                        if(switchedList.size()>1){
                            switchedList.sort(Comparator.comparing(SmartDevices::getSwitchTime, Comparator.nullsLast(Comparator.reverseOrder())));
                        }if(device.getStatus().equals("off")){
                            device.setStatus("on");
                            if(device instanceof SmartCamera){
                                ((SmartCamera) device).setInitialTime(time.getCurrentTime());
                            }else if(device instanceof SmartPlug && ((SmartPlug) device).isPlugin()){
                                ((SmartPlug) device).setInitialTime(time.getCurrentTime());
                            }
                        }else{
                            device.setStatus("off");
                            if(device instanceof SmartCamera){
                                ((SmartCamera) device).setFinalTime(time.getCurrentTime());
                                ((SmartCamera) device).setUsedStorage(((SmartCamera) device).getUsedStorage()
                                        +(((SmartCamera) device).getMegaBytesPerRecord()
                                        *(Duration.between(((SmartCamera) device).getInitialTime()
                                        , ((SmartCamera) device).getFinalTime()).toMinutes())));
                            }else if(device instanceof SmartPlug && ((SmartPlug) device).isPlugin()){
                                ((SmartPlug) device).setFinalTime(time.getCurrentTime());
                                ((SmartPlug) device).setConsumedEnergy(((SmartPlug) device).getConsumedEnergy()
                                        +(((SmartPlug) device).getAmpere()*220*((Duration.between(((SmartPlug) device)
                                        .getInitialTime(), ((SmartPlug) device).getFinalTime()).toMinutes())/60.00)));
                            }
                        }device.setSwitchTime(null, time.getCurrentTime());
                    }
                }
            }
        }catch (NumberFormatException e) {
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }
    }
    /**
     * Writes the current time and sorted list of devices with all of their data to the output file.
     * Sorts devices that had a switch time but latterly became switched, in the correct order using the
     * "smartDevices" and "switchedList" arraylists.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param switchedList  keeps devices that got switched latterly, it is sorted
     * @param time  for getting the current time
     */
    public static void Report(String path, ArrayList<SmartDevices> smartDevices,ArrayList<SmartDevices> switchedList
            , CurrentTime time){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumFractionDigits(2);
        if(!switchedList.isEmpty()){
            for(SmartDevices switchedDevice : switchedList){
                smartDevices.removeIf(switchedDevice::equals);
            }
        }
        smartDevices.sort(Comparator.nullsLast(Comparator.comparing(SmartDevices::getSwitchTime
                , Comparator.nullsLast(Comparator.naturalOrder()))));
        int i=0;
        if(!switchedList.isEmpty()){
            if(!smartDevices.isEmpty()){
                for(SmartDevices device : smartDevices){
                    if(device.getSwitchTime() == null){
                        smartDevices.addAll(i, switchedList);
                        break;
                    }i++;
                }
            }else{
                smartDevices.addAll(switchedList);
            }
        }
        ioOperations.writer(path, "Time is:\t"+time.getCurrentTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")), true, true);
        for(SmartDevices device : smartDevices) {
            String className = device.getClass().getSimpleName().replaceAll("([A-Z])", " $1").substring(1);
            String switchTimeStr;
            if(device.getSwitchTime() == null) {
                switchTimeStr = "null";
            }else{
                switchTimeStr = device.getSwitchTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
            }if (device instanceof SmartCamera) {
                ioOperations.writer(path, className + " " + device.getName() + " is " + device.getStatus()
                        + " and used " + df.format(((SmartCamera) device).getUsedStorage())
                        + " MB of storage so far (excluding current status), and its time to switch its status is "
                        +switchTimeStr+".", true, true);
            } else if (device instanceof SmartPlug) {
                ioOperations.writer(path, className + " " + device.getName() + " is " + device.getStatus()
                        + " and consumed " + df.format(((SmartPlug) device).getConsumedEnergy())
                        + "W so far (excluding current device), and its time to switch its status is "
                        +switchTimeStr+".", true, true);
            } else if (device instanceof SmartColorLamp) {
                if (((SmartColorLamp) device).getColorFeature()) {
                    ioOperations.writer(path, className + " " + device.getName() + " is " + device.getStatus()
                            + " and its color value is " + ((SmartColorLamp) device).getColorCodeStr() + " with "
                            + ((SmartColorLamp) device).getBrightness() + "% brightness, and its time to switch its status is "
                            +switchTimeStr+".", true, true);
                } else {
                    ioOperations.writer(path, className + " " + device.getName() + " is " + device.getStatus()
                            + " and its color value is " + ((SmartColorLamp) device).getKelvin() + "K with "
                            + ((SmartColorLamp) device).getBrightness() + "% brightness, and its time to switch its status is "
                            +switchTimeStr+".", true, true);
                }
            } else if (device instanceof SmartLamp) {
                ioOperations.writer(path, className + " " + device.getName() + " is " + device.getStatus()
                        + " and its kelvin value is " + ((SmartLamp) device).getKelvin() + "K with "
                        + ((SmartLamp) device).getBrightness() + "% brightness, and its time to switch its status is "
                        +switchTimeStr+".", true, true);
            }
        }
    }
    /**
     * Sets the switch time of the given device.
     * If it's same as the current time, calls switch method and switches the device.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @param switchedList  keeps devices that got switched latterly, it is sorted
     * @param time  to check if given switch time is appropriate
     * @throws  ReverseTimeException    if given switch time is before the current time or the previously set switch time
     * @throws ErroneousCommandException    if format of the command is incorrect
     */
    public static void SetSwitchTime(String path, ArrayList<SmartDevices> smartDevices, ArrayList<String> list
            , ArrayList<SmartDevices> switchedList, CurrentTime time) throws ReverseTimeException, ErroneousCommandException {
        if(list.size()!=3) {
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }
        boolean deviceExists = false;
        for (SmartDevices device : smartDevices) {
            if (device.getName().equals(list.get(1))) {
                deviceExists = true;
                String[] dateTimeParts = list.get(2).split("_");
                String[] dateParts = dateTimeParts[0].split("-");
                String[] timeParts = dateTimeParts[1].split(":");
                String formattedDateTime = String.format("%s-%02d-%02d_%02d:%02d:%02d", dateParts[0],
                        Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]), Integer.parseInt(timeParts[0]),
                        Integer.parseInt(timeParts[1]), Integer.parseInt(timeParts[2]));
                if((LocalDateTime.parse(formattedDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))).equals(time.getCurrentTime())){
                    ArrayList<String> switchCommandList = new ArrayList<>();
                    switchCommandList.add("Switch");
                    switchCommandList.add(list.get(1));
                    if (device.getStatus().equals("on")){
                        switchCommandList.add("off");
                    }else{
                        switchCommandList.add("on");
                    }
                    if(!(switchedList.contains(device))){
                        switchedList.add(device);
                    }
                    if(switchedList.size()>1) {
                        switchedList.sort(Comparator.comparing(SmartDevices::getSwitchTime, Comparator.nullsLast(Comparator.reverseOrder())));
                    }
                    Switch(path, smartDevices, switchCommandList, time);
                }else{
                    device.setSwitchTime(LocalDateTime.parse(formattedDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")), time.getCurrentTime());
                }
            }
        }if(!deviceExists){
            ioOperations.writer(path, "ERROR: There is not such a device!", true, true);
        }
    }
    /**
     * Sets the initial time at the very beginning.
     *
     * @param path  path of the file the content is going to be written
     * @param list  arraylist of commands, one line of the input file
     * @return time variable of the current time object
     * @throws ErroneousCommandException    if format of the command is incorrect
     */
    public static CurrentTime SetInitialTime(String path, ArrayList<String> list)
            throws ErroneousCommandException{
        if(list.size()!=2){
            throw new ErroneousCommandException
                    ("ERROR: First command must be set initial time! Program is going to terminate!");
        }
        if (list.get(0).equals("SetInitialTime")) {
            String[] dateTimeParts = list.get(1).split("_");
            String[] dateParts = dateTimeParts[0].split("-");
            String[] timeParts = dateTimeParts[1].split(":");
            String formattedDateTime = String.format("%s-%02d-%02d_%02d:%02d:%02d", dateParts[0],
                    Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]), Integer.parseInt(timeParts[0]),
                    Integer.parseInt(timeParts[1]), Integer.parseInt(timeParts[2]));
            CurrentTime time = new CurrentTime(LocalDateTime.parse(formattedDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")));
            ioOperations.writer(path, "SUCCESS: Time has been set to " + list.get(1) + "!", true, true);
            return time;
        }return null;
    }
    /**
     * Updates the current time.
     * Does the necessary calculations, if plugs or cameras are switched.
     * Adds these plugs or cameras in "switchedList" and sorts them.
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param list  arraylist of commands, one line of the input file
     * @param switchedList  keeps devices that got switched latterly, it is sorted
     * @param time  for changing current time and setting initial/final times of plugs and cameras
     * @throws ReverseTimeException    if given time to be set is before the current time
     * @throws ErroneousCommandException    if format of the command is incorrect
     */
    public static void SetTime(String path, ArrayList<SmartDevices> smartDevices, ArrayList<String> list
            , ArrayList<SmartDevices> switchedList, CurrentTime time)
            throws ReverseTimeException, ErroneousCommandException {
        if(list.size()!=2){
            throw new ErroneousCommandException("ERROR: Erroneous command!");
        }
        try{
            String[] dateTimeParts = list.get(1).split("_");
            String[] dateParts = dateTimeParts[0].split("-");
            String[] timeParts = dateTimeParts[1].split(":");
            String formattedDateTime = String.format("%s-%02d-%02d_%02d:%02d:%02d",
                    dateParts[0], Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]),
                    Integer.parseInt(timeParts[0]), Integer.parseInt(timeParts[1]), Integer.parseInt(timeParts[2]));
            time.setCurrentTime(LocalDateTime.parse(formattedDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")));
            for(SmartDevices device:smartDevices){
                if(device.getSwitchTime()!=null && device.getSwitchTime().isBefore(time.getCurrentTime())){
                    if(!(switchedList.contains(device))){
                        switchedList.add(device);
                    }
                    if(device.getStatus().equals("off")){
                        device.setStatus("on");
                        if(device instanceof SmartCamera){
                            ((SmartCamera) device).setInitialTime(time.getCurrentTime());
                        }else if(device instanceof SmartPlug && ((SmartPlug) device).isPlugin()){
                            ((SmartPlug) device).setInitialTime(time.getCurrentTime());
                        }
                    }else{
                        device.setStatus("off");
                        if(device instanceof SmartCamera){
                            ((SmartCamera) device).setFinalTime(time.getCurrentTime());
                            ((SmartCamera) device).setUsedStorage(((SmartCamera) device).getUsedStorage()
                                    +(((SmartCamera) device).getMegaBytesPerRecord()*(Duration.between(((SmartCamera) device)
                                    .getInitialTime(), ((SmartCamera) device).getFinalTime()).toMinutes())));
                        }else if(device instanceof SmartPlug && ((SmartPlug) device).isPlugin()){
                            ((SmartPlug) device).setFinalTime(time.getCurrentTime());
                            ((SmartPlug) device).setConsumedEnergy(((SmartPlug) device).getConsumedEnergy()
                                    +(((SmartPlug) device).getAmpere()*220*((Duration.between(((SmartPlug) device)
                                    .getInitialTime(), ((SmartPlug) device).getFinalTime()).toMinutes())/60.00)));
                        }
                    }
                }
            }
            if(switchedList.size()>1){
                switchedList.sort(Comparator.comparing(SmartDevices::getSwitchTime, Comparator.nullsLast(Comparator.reverseOrder())));
            }
            for(SmartDevices device:switchedList){
                device.setSwitchTime(null, time.getCurrentTime());
            }
        }catch (DateTimeParseException e){
            ioOperations.writer(path, "ERROR: Time format is not correct!", true, true);
        }
    }
    /**
     * Updates current time to the next/the earliest switch time if there is any.
     * If the switch time belongs to a plug or camera, and they were consuming energy or using storage; does the
     * necessary calculations.
     * Adds the latterly switched device or devices to "switchedList".
     *
     * @param path  path of the file the content is going to be written
     * @param smartDevices  devices are kept in this arraylist
     * @param switchedList  keeps devices that got switched latterly, it is sorted
     * @param time  for changing and getting current time and setting initial/final times of plugs and cameras
     * @throws ReverseTimeException    if an earlier time is being tried to set to the current time
     * @throws ErroneousCommandException    if an incorrect status is being tried to set to device status
     */
    public static void Nop(String path, ArrayList<SmartDevices> smartDevices, ArrayList<SmartDevices> switchedList
            , CurrentTime time) throws ReverseTimeException, ErroneousCommandException {
        ArrayList<LocalDateTime> timeList = new ArrayList<>();
        for(SmartDevices device : smartDevices){
            if(device.getSwitchTime() != null){
                timeList.add(device.getSwitchTime());
            }
        }if(!timeList.isEmpty()){
            LocalDateTime firstSwitchTime = Collections.min(timeList, Comparator.naturalOrder());
            time.setCurrentTime(firstSwitchTime);
            for(SmartDevices device : smartDevices){
                if(device.getSwitchTime() != null && device.getSwitchTime().equals(time.getCurrentTime())){
                    if(!(switchedList.contains(device))){
                        switchedList.add(device);
                    }
                    if(device.getStatus().equals("off")){
                        device.setStatus("on");
                        if(device instanceof SmartCamera){
                            ((SmartCamera) device).setInitialTime(time.getCurrentTime());
                        }else if(device instanceof SmartPlug && ((SmartPlug) device).isPlugin()){
                            ((SmartPlug) device).setInitialTime(time.getCurrentTime());
                        }
                    }else{
                        device.setStatus("off");
                        if(device instanceof SmartCamera){
                            ((SmartCamera) device).setFinalTime(time.getCurrentTime());
                            ((SmartCamera) device).setUsedStorage(((SmartCamera) device).getUsedStorage()
                                    +(((SmartCamera) device).getMegaBytesPerRecord()*(Duration.between(((SmartCamera) device)
                                    .getInitialTime(), ((SmartCamera) device).getFinalTime()).toMinutes())));
                        }else if(device instanceof SmartPlug && ((SmartPlug) device).isPlugin()){
                            ((SmartPlug) device).setFinalTime(time.getCurrentTime());
                            ((SmartPlug) device).setConsumedEnergy(((SmartPlug) device).getConsumedEnergy()
                                    +(((SmartPlug) device).getAmpere()*220*((Duration.between(((SmartPlug) device)
                                    .getInitialTime(), ((SmartPlug) device).getFinalTime()).toMinutes())/60.00)));
                        }
                    }
                }
            }if(switchedList.size()>1){
                switchedList.sort(Comparator.comparing(SmartDevices::getSwitchTime, Comparator.nullsLast(Comparator.reverseOrder())));
            }
            for(SmartDevices device:switchedList){
                device.setSwitchTime(null, time.getCurrentTime());
            }
        }else{
            ioOperations.writer(path, "ERROR: There is nothing to switch!", true, true);
        }
    }
}
