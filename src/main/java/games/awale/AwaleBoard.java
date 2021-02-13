package games.awale;

import games.dominos.DominosBoard;
import iialib.games.model.IBoard;
import iialib.games.model.Score;

import java.util.ArrayList;

class AwaleBoard implements IBoard<AwaleMove, AwaleRole, AwaleBoard> {
    private static final int GRID_LENGTH = 6;
    private static final int GRID_HEIGHT = 2;
    private final int[][] boardGrid;
    private int TopScore = 0;
    private int DownScore = 0;

    // ---------------------- Constructors ---------------------
    public AwaleBoard() {
        boardGrid = new int[GRID_HEIGHT][GRID_LENGTH];
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_LENGTH; j++) {
                boardGrid[i][j] = 4;
            }
        }
    }

    public AwaleBoard(int[][] other) {
        boardGrid = other;
    }

    @Override
    public ArrayList<AwaleMove> possibleMoves(AwaleRole playerRole) {
        if (playerRole == AwaleRole.Top) {
            return possibleMovesTop();
        } else {
            return possibleMovesDown();
        }
    }


    @Override
    public AwaleBoard play(AwaleMove move, AwaleRole playerRole) {
        int[][] newGrid = copyGrid();
        int player = (playerRole == AwaleRole.Top) ? 0 : 1;
        int Nbstones = newGrid[player][move.take];
        int index = (player == 0) ? move.take - 1 : move.take + 1;

        newGrid[player][move.take] = 0;
        while (true) {
            if (index >= GRID_LENGTH) {
                player = (player + 1) % 2;
                index = 5;
            }

            if (index < 0) {
                player = (player + 1) % 2;
                index = 0;
            }

            newGrid[player][index] += 1;
            Nbstones--;
            if (Nbstones == 0) break;

            if (player == 0) {
                index--;
            } else {
                index++;
            }
        }

        int[][] backup = newGrid.clone();
        int enemy = (playerRole == AwaleRole.Top) ? 1 : 0;
        if (player == enemy) {
            boolean bonus = (newGrid[player][index] == 2) || (newGrid[player][index] == 3);
            while (bonus) {
                if (playerRole == AwaleRole.Top) {
                    TopScore += newGrid[player][index];
                } else {
                    DownScore += newGrid[player][index];
                }

                newGrid[player][index] = 0;
                System.out.println("CONGRADUATION!!!!!!!!!!!!!!");
                index += (player == 0) ? 1 : -1;

                if (index >= GRID_LENGTH) {
                    player = (player + 1) % 2;
                    index = 5;
                }
                if (index < 0) {
                    player = (player + 1) % 2;
                    index = 0;
                }
                bonus = (newGrid[player][index] == 2) || (newGrid[player][index] == 3);
            }
        }

        boolean famine = true;
        for (int i = 0; i < GRID_LENGTH; i++) {
            famine = famine && (newGrid[enemy][i] == 0);
        }
        if (famine) newGrid = backup;
        return new AwaleBoard(newGrid);
    }

    @Override
    public boolean isValidMove(AwaleMove move, AwaleRole playerRole) {
        boolean nourir = true;
        for (int i = 0; i < 6; i++) {
            nourir = nourir && (boardGrid[(playerRole == AwaleRole.Top) ? 1 : 0][i] == 0);
        }

        if (nourir) {
            return boardGrid[(playerRole == AwaleRole.Top) ? 0 : 1][move.take] > 5 - move.take;
        }

        return boardGrid[(playerRole == AwaleRole.Top) ? 0 : 1][move.take] > 0;
    }

    @Override
    public boolean isGameOver() {
        if (this.possibleMovesDown().isEmpty()) {
            for (int i = 0; i < GRID_LENGTH; i++) {
                this.TopScore += boardGrid[1][i];
            }
            return true;
        }
        if (this.possibleMovesTop().isEmpty()) {
            for (int i = 0; i < GRID_LENGTH; i++) {
                this.DownScore += boardGrid[0][i];
            }
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Score<AwaleRole>> getScores() {
        ArrayList<Score<AwaleRole>> scores = new ArrayList<Score<AwaleRole>>();
        if (this.isGameOver()) {
            if (TopScore < DownScore) {
                scores.add(new Score<>(AwaleRole.Top, Score.Status.LOOSE, 0));
                scores.add(new Score<>(AwaleRole.Down, Score.Status.WIN, 1));
            } else if (TopScore > DownScore) {
                scores.add(new Score<>(AwaleRole.Top, Score.Status.WIN, 1));
                scores.add(new Score<>(AwaleRole.Down, Score.Status.LOOSE, 0));
            }
        }
        return scores;
    }

    private int[][] copyGrid() {
        int[][] newGrid = new int[GRID_HEIGHT][GRID_LENGTH];
        for (int i = 0; i < GRID_HEIGHT; i++) {
            System.arraycopy(boardGrid[i], 0, newGrid[i], 0, GRID_LENGTH);
        }
        return newGrid;
    }

    private ArrayList<AwaleMove> possibleMovesTop() {
        ArrayList<AwaleMove> allPossibleMovesTop = new ArrayList<>();
        for (int j = 0; j < GRID_LENGTH - 1; j++) {
            if (boardGrid[0][j] > 0) {
                allPossibleMovesTop.add(new AwaleMove(j));
            }
        }
        return allPossibleMovesTop;
    }

    private ArrayList<AwaleMove> possibleMovesDown() {
        ArrayList<AwaleMove> allPossibleMovesDown = new ArrayList<>();
        for (int j = 0; j < GRID_LENGTH - 1; j++) {
            if (boardGrid[1][j] > 0) {
                allPossibleMovesDown.add(new AwaleMove(j));
            }
        }
        return allPossibleMovesDown;
    }

    @Override
    public String toString() {
        StringBuilder retstr = new StringBuilder();
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_LENGTH; j++) {
                retstr.append(boardGrid[i][j]).append("\t");
            }
            retstr.append("\n");
        }
        retstr.append(TopScore).append(", ").append(DownScore).append("\n");
        return retstr.toString();
    }
}
