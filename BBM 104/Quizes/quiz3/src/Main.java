//Özge Bülbül 2220765008
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try{
            if(args.length!=1){
                throw new InvalidParameterCountException("There should be only 1 paremeter");
            }
            Path path = Paths.get(args[0]);
            String[] input = ioOperations.reader(path.toAbsolutePath());
            assert input != null;
            ArrayList<String> lines = new ArrayList<>(Arrays.asList(input));
            if(lines.size()==0){
                throw new EmptyFileException("The input file should not be empty");
            }
            for (String line : lines) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) < 65 || 90 < line.charAt(i)) {
                        if(line.charAt(i) < 97 || 122 < line.charAt(i)){
                            if(line.charAt(i)!=' ') {
                                throw new InvalidInputException("The input file should not contains unexpected characters");
                            }
                        }
                    }
                }
            }
            ioOperations.writer("output.txt", String.join("", input), true, true);
        }catch(NullPointerException e){
            ioOperations.writer("output.txt", "The input file should not be empty", true, true);
        }catch(EmptyFileException | InvalidInputException | RuntimeException | InvalidParameterCountException e){
            ioOperations.writer("output.txt", e.getLocalizedMessage(), true, true);
        }

    }
}