import java.util.*;
public class Movement {
    public static int[] horizontalMovements(String symbol, int colNum, int myRow, int myCol, int columnOne, int columnTwo, List<List<String>> boardList) {
        int myScore = 0;
        int gameOver = 0;
        if (Objects.equals(symbol, "W")) {
            Collections.swap(boardList.get(myRow), (columnOne) % colNum, (myCol) % colNum);
            if (boardList.get(myRow).get((myCol) % colNum).matches("[RBY]")) {
                if (boardList.get(myRow).get((myCol) % colNum).matches("R")) {
                    myScore += 10;
                } else if (boardList.get(myRow).get((myCol) % colNum).matches("Y")) {
                    myScore += 5;
                } else if (boardList.get(myRow).get((myCol) % colNum).matches("B")) {
                    myScore -= 5;
                }
                boardList.get(myRow).set((myCol) % colNum, "X");
            }
            myCol = columnOne % colNum;
        } else if (Objects.equals(symbol, "H")) {
            boardList.get(myRow).set((myCol) % colNum, " ");
            gameOver = 1;
        } else {
            Collections.swap(boardList.get(myRow), (columnTwo) % colNum, (myCol) % colNum);
            if (Objects.equals(symbol, "R")) {
                myScore += 10;
                boardList.get(myRow).set((myCol) % colNum, "X");
            } else if (Objects.equals(symbol, "Y")) {
                myScore += 5;
                boardList.get(myRow).set((myCol) % colNum, "X");
            } else if (Objects.equals(symbol, "B")) {
                myScore -= 5;
                boardList.get(myRow).set((myCol) % colNum, "X");
            }
            myCol = (columnTwo) % colNum;
        }
        int[] returnValues = new int[3];
        returnValues[0] = myCol;
        returnValues[1] = myScore;
        returnValues[2] = gameOver;
        return returnValues;
    }
    public static int[] verticalMovements(String symbol, int rowNum, int myRow, int myCol, int rowOne, int rowTwo, List<List<String>> boardList){
        int myScore = 0;
        int gameOver = 0;
        if(Objects.equals(symbol, "W")) {
            String star = boardList.get((myRow)%rowNum).get(myCol);
            boardList.get((myRow)%rowNum).set(myCol, boardList.get((rowTwo)%rowNum).get(myCol));
            boardList.get((rowTwo)%rowNum).set(myCol, star);
            if(boardList.get(myRow).get(myCol).matches("[RBY]")){
                if(boardList.get(myRow).get(myCol).matches("R")){
                    myScore += 10;
                }else if(boardList.get(myRow).get(myCol).matches("Y")){
                    myScore += 5;
                }else if(boardList.get(myRow).get(myCol).matches("B")) {
                    myScore -= 5;
                }boardList.get(myRow).set(myCol, "X");
            }
            myRow = rowTwo%rowNum;
        }else if(Objects.equals(symbol, "H")){
            boardList.get((myRow)%rowNum).set(myCol, " ");
            gameOver = 1;
        }else{
            String star = boardList.get((myRow)%rowNum).get(myCol);
            boardList.get((myRow)%rowNum).set(myCol, boardList.get((rowOne)%rowNum).get(myCol));
            boardList.get((rowOne)%rowNum).set(myCol, star);
            if(Objects.equals(symbol, "R")){
                myScore += 10;
                boardList.get((myRow)%rowNum).set(myCol, "X");
            }else if(Objects.equals(symbol, "Y")){
                myScore += 5;
                boardList.get((myRow)%rowNum).set(myCol, "X");
            }else if(Objects.equals(symbol, "B")) {
                myScore -= 5;
                boardList.get((myRow)%rowNum).set(myCol, "X");
            }myRow = (rowOne)%rowNum;
        }
        int[] returnValues = new int[3];
        returnValues[0] = myRow;
        returnValues[1] = myScore;
        returnValues[2] = gameOver;
        return returnValues;
    }
}
