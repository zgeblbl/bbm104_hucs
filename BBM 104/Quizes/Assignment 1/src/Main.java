import java.util.*;
public class Main {

    public static void main(String[] args) {
        String[] board_str = ioOperations.reader("board.txt");
        String[] moves_str = ioOperations.reader("move.txt");
        List<String> legitMoves = new ArrayList<>();
        int score = 0;
        int gameOver = 0;
        assert moves_str != null;
        String[] moveArray = moves_str[0].split(" ");
        List<String> moves = new ArrayList<>(Arrays.asList(moveArray));
        List<List<String>> board = new ArrayList<>();
        assert board_str != null;
        int rowNumber = board_str.length;
        int columnNumber = board_str[0].split(" ").length;
        for (String line: board_str){
            String[] elements = line.split(" ");
            List<String> mylist = new ArrayList<>(Arrays.asList(elements));
            board.add(mylist);
            }
        ioOperations.writer("output.txt", "Game board:", false, true);
        for(List<String> mylist : board){
            String joined = String.join(" ", mylist);
            ioOperations.writer("output.txt", joined, true, true);
        }
        int row = 0;
        int column = 0;
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                if(board.get(i).indexOf("*") == j){
                    row = i;
                    column = j;
                }
            }
        }
        for (String move : moves) {
            if(gameOver == 0){
                legitMoves.add(move);
            }if (Objects.equals(move, "L") && gameOver == 0) {
                if(column == 0) {
                    column = columnNumber;
                }String newSymbol = board.get(row).get(column-1);
                int[] returnValues = Movement.horizontalMovements(newSymbol, columnNumber, row, column, column+1,
                        column-1, board);
                column = returnValues[0];
                score += returnValues[1];
                gameOver = returnValues[2];
            }else if(Objects.equals(move, "R") && gameOver == 0) {
                if((column == 0) && (board.get(row).get(1)).matches("W")) {
                    column = columnNumber;
                }String newSymbol = board.get(row).get((column+1)%columnNumber);
                int[] returnValues = Movement.horizontalMovements(newSymbol, columnNumber, row, column, column-1,
                        column+1, board);
                column = returnValues[0];
                score += returnValues[1];
                gameOver = returnValues[2];
            }else if(Objects.equals(move, "U") && gameOver == 0) {
                if(row == 0 && (board.get(rowNumber-1).get(column)).matches("W")){
                    row = rowNumber;
                }else if(row == 0){
                    row = rowNumber;
                }String newSymbol = board.get(row-1).get(column);
                int[] returnValues = Movement.verticalMovements(newSymbol, rowNumber, row, column, row - 1,
                        row + 1, board);
                row = returnValues[0];
                score += returnValues[1];
                gameOver = returnValues[2];
            }else if(Objects.equals(move, "D") && gameOver == 0) {
                if(row == 0 && (board.get(1).get(column)).matches("W")){
                    row = rowNumber;
                }
                String newSymbol = board.get((row+1)%rowNumber).get(column);
                int[] returnValues = Movement.verticalMovements(newSymbol, rowNumber, row, column, row+1,
                        row-1, board);
                row = returnValues[0];
                score += returnValues[1];
                gameOver = returnValues[2];
            }
        }
        ioOperations.writer("output.txt", "\nYour movement is:", true, true);
        String moveString = String.join(" ", legitMoves);
        ioOperations.writer("output.txt", moveString, true, true);
        ioOperations.writer("output.txt", "\nYour output is:", true, true);
        for(List<String> mylist : board){
            String joined = String.join(" ", mylist);
            ioOperations.writer("output.txt", joined, true, true);
        }
        if(gameOver == 1){
            ioOperations.writer("output.txt", "\nGame Over!", true, false);
        }
        ioOperations.writer("output.txt", "\nScore: "+score, true, true);
    }
}
