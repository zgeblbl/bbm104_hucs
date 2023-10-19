import java.util.*;
public class Main {
    /**
     * Reads the input file and creates an arraylist of arraylists of all commands.
     * Adds ZReport at the end of the commands if there is not one already.
     * Creates the smart device and switched device lists to be used in the commands class methods.
     * Terminates the programme if initial command is incorrect.
     * Directs the system to each command's method.
     *
     */
    public static void main(String[] args) {
        String[] inputArray = ioOperations.reader(args[0]);
        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        assert inputArray != null;
        for (String line: inputArray){
            if(!(Objects.equals(line, ""))){
                String[] elements = line.split("\t");
                ArrayList<String> myList = new ArrayList<>(Arrays.asList(elements));
                lines.add(myList);
            }
        }boolean zReport = false;
        if(!Objects.equals(lines.get(lines.size() - 1).get(0), "ZReport")){
            ArrayList<String> tempList = new ArrayList<>();
            tempList.add("ZReport");
            lines.add(tempList);
            zReport =true;
        }
        ArrayList<SmartDevices> smartDevices = new ArrayList<>();
        ArrayList<SmartDevices> switchedList = new ArrayList<>();
        ArrayList<Object> initialTimeArray = InitialTimeOperations.InitialTimeOp(lines, args[1]);
        boolean terminated = (boolean) initialTimeArray.get(0);
        CurrentTime currentTime = (CurrentTime) initialTimeArray.get(1);
        int i = 0;
        if(!terminated){
            for (ArrayList<String> list:lines.subList(1, lines.size())){
                String str = String.join("\t", list);
                if(zReport && i==lines.size()-2){
                    ioOperations.writer(args[1], str+":", true, true);
                }else{
                    ioOperations.writer(args[1], "COMMAND: " + str, true, true);
                }
                assert currentTime != null;
                try {
                    switch (list.get(0)) {
                        case "SetTime":
                            Commands.SetTime(args[1],smartDevices, list, switchedList, currentTime);
                            break;
                        case "Add":
                            Commands.Add(smartDevices, list, currentTime);
                            break;
                        case "Switch":
                            Commands.Switch(args[1], smartDevices, list, currentTime);
                            break;
                        case "ChangeName":
                            Commands.ChangeName(args[1], smartDevices, list);
                            break;
                        case "Remove":
                            Commands.Remove(args[1], smartDevices, list, currentTime);
                            break;
                        case "SetKelvin":
                            Commands.SetKelvin(args[1], smartDevices, list);
                            break;
                        case "SetBrightness":
                            Commands.SetBrightness(args[1], smartDevices, list);
                            break;
                        case "SetWhite":
                            Commands.SetWhite(args[1], smartDevices, list);
                            break;
                        case "SetColor":
                            Commands.SetColor(args[1], smartDevices, list);
                            break;
                        case "SetColorCode":
                            Commands.SetColorCode(args[1], smartDevices, list);
                            break;
                        case "PlugIn":
                            Commands.PlugIn(args[1], smartDevices, list, currentTime);
                            break;
                        case "PlugOut":
                            Commands.PlugOut(args[1], smartDevices, list, currentTime);
                            break;
                        case "SkipMinutes":
                            Commands.SkipMinutes(args[1], smartDevices, list, switchedList, currentTime);
                            break;
                        case "ZReport":
                            if(list.size()!=1) {
                                throw new ErroneousCommandException("ERROR: Erroneous command!");
                            }Commands.Report(args[1], smartDevices, switchedList, currentTime);
                            break;
                        case "SetSwitchTime":
                            Commands.SetSwitchTime(args[1], smartDevices, list, switchedList, currentTime);
                            break;
                        case "Nop":
                            if(list.size()!=1) {
                                throw new ErroneousCommandException("ERROR: Erroneous command!");
                            }Commands.Nop(args[1], smartDevices, switchedList, currentTime);
                            break;
                        default:
                            throw new ErroneousCommandException("ERROR: Erroneous command!");
                    }
                } catch (IndexOutOfBoundsException e) {
                    ioOperations.writer(args[1], "ERROR: Erroneous command!", true, true);
                } catch (RepetitionException | InvalidNumberException | ErroneousCommandException |
                         ReverseTimeException e) {
                    ioOperations.writer(args[1], e.getLocalizedMessage(), true, true);
                }i++;
            }
        }
    }
}
