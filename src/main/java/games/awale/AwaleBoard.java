package games.awale;

import games.dominos.DominosBoard;
import games.dominos.DominosMove;
import games.dominos.DominosRole;
import iialib.games.model.IBoard;
import iialib.games.model.Score;

import java.util.ArrayList;

public class AwaleBoard implements IBoard<AwaleMove, AwaleRole, AwaleBoard> {
    private static final int GRID_LENGTH=6;
    private static final int GRID_HEIGHT=2;
    private int[][] boardGrid;
    // ---------------------- Constructors ---------------------
    public AwaleBoard(){
        boardGrid=new int[GRID_HEIGHT][GRID_LENGTH];
    }

    @Override
    public ArrayList<AwaleMove> possibleMoves(AwaleRole playerRole) {
        if(playerRole==AwaleRole.Top){
            return possibleMovesTop();
        }else{
            return possibleMovesDown();
        }
    }



    @Override
    public AwaleBoard play(AwaleMove move, AwaleRole playerRole) {
        int[][] newGrid=copyGrid();
        int x=move.take;
        if(playerRole==AwaleRole.Top){
            if(x<=2){
                for(int i=1;i<=4;i++){
                    newGrid[0][x+i]+=1;
                }
            }else{
                int n=4;
                for(int i=1;i<=4;i++){
                    if(x+1<=5){
                        newGrid[0][x+i]+=1;
                        n--;
                    }
                    else
                        break;
                }

            }
        }


        return null;
    }

    @Override
    public boolean isValidMove(AwaleMove move, AwaleRole playerRole) {
        return false;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public ArrayList<Score<AwaleRole>> getScores() {
        return null;
    }

    private int[][]copyGrid(){
        int[][]newGrid=new int[GRID_HEIGHT][GRID_LENGTH];
        for (int i = 0; i < GRID_HEIGHT; i++){
                System.arraycopy(boardGrid[i], 0, newGrid[i], 0, GRID_HEIGHT);
        }
        return newGrid;
    }
    private ArrayList<AwaleMove> possibleMovesTop() {
        ArrayList<AwaleMove> allPossibleMovesTop = new ArrayList<>();
        for (int j = 0; j < GRID_LENGTH - 1; j++) {
            if(boardGrid[0][j]>0){
                allPossibleMovesTop.add(new AwaleMove(j));
            }
        }
        return allPossibleMovesTop;
    }
    private ArrayList<AwaleMove> possibleMovesDown(){
        ArrayList<AwaleMove> allPossibleMovesDown = new ArrayList<>();
        for (int j = 0; j < GRID_LENGTH - 1; j++) {
            if(boardGrid[1][j]>0){
                allPossibleMovesDown.add(new AwaleMove(j));
            }
        }
        return allPossibleMovesDown;
    }
}
