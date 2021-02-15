package games.awale;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

import java.util.ArrayList;

class AwaleBoard implements IBoard<AwaleMove, AwaleRole, AwaleBoard> {
    private static final int GRID_LENGTH = 6;
    private static final int GRID_HEIGHT = 2;

    // int[GRID_HEIGHT][GRID_LENGTH]
    // boardGrid[0] - Cases de joueur Top / boardGrid[1] - Cases de joueur Down
    private final int[][] boardGrid;
    private int TopScore = 0;
    private int DownScore = 0;
    public int nbSeedTop = 0;
    public int nbSeedDown = 0;
    public int nbSeedRest;

    // ---------------------- Constructors ---------------------
    public AwaleBoard() {
        boardGrid = new int[GRID_HEIGHT][GRID_LENGTH];
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_LENGTH; j++) {
                boardGrid[i][j] = 4;
            }
        }
        for (int j = 0; j < GRID_LENGTH; j++) {
            nbSeedTop += boardGrid[0][j];
            nbSeedDown += boardGrid[1][j];
        }
        nbSeedRest = nbSeedDown + nbSeedTop;
    }

    public AwaleBoard(int[][] other, int TopScore, int DownScore) {
        boardGrid = other;
        this.TopScore = TopScore;
        this.DownScore = DownScore;
        for (int j = 0; j < GRID_LENGTH; j++) {
            nbSeedTop += boardGrid[0][j];
            nbSeedDown += boardGrid[1][j];
        }
        nbSeedRest = nbSeedDown + nbSeedTop;
    }

    // ---------------------- Public Methods ---------------------

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
        int[][] newGrid = copyGrid(boardGrid);
        int player = (playerRole == AwaleRole.Top) ? 0 : 1;
        int Nbstones = newGrid[player][move.take];
        int index = (player == 0) ? move.take - 1 : move.take + 1;
        // System.out.println("Move: " + move + ", Nbstones: " + Nbstones);

        newGrid[player][move.take] = 0;
        while (true) {
            if (player == ((playerRole == AwaleRole.Top) ? 0 : 1) && index == move.take) {
                if (player == 0) {
                    index--;
                } else {
                    index++;
                }
            }

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

        int newTopScore = TopScore;
        int newDownScore = DownScore;
        int[][] backup = copyGrid(newGrid);
        int enemy = (playerRole == AwaleRole.Top) ? 1 : 0;
        if (player == enemy) {
            boolean bonus = (newGrid[player][index] == 2) || (newGrid[player][index] == 3);
            while (bonus) {
                if (playerRole == AwaleRole.Top) {
                    newTopScore += newGrid[player][index];
                } else {
                    newDownScore += newGrid[player][index];
                }

                newGrid[player][index] = 0;
                index += (player == 0) ? 1 : -1;

                // Debug
                // if (index >= GRID_LENGTH || index < 0) break;

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
        for (int i = 0; i < GRID_LENGTH; i++) famine = famine && (newGrid[enemy][i] == 0);
        if (famine) return new AwaleBoard(backup, TopScore, DownScore);

        return new AwaleBoard(newGrid, newTopScore, newDownScore);
    }

    @Override
    public boolean isValidMove(AwaleMove move, AwaleRole playerRole) {
        boolean nourir = true;
        for (int i = 0; i < 6; i++) {
            nourir = nourir && (boardGrid[(playerRole == AwaleRole.Top) ? 1 : 0][i] == 0);
        }

        if (nourir) {
            if (playerRole == AwaleRole.Top) {
                return boardGrid[0][move.take] > move.take;
            } else {
                return boardGrid[1][move.take] > 6 - move.take;
            }
        }

        return boardGrid[(playerRole == AwaleRole.Top) ? 0 : 1][move.take] > 0;
    }

    @Override
    public boolean isGameOver() {
        if (this.possibleMovesTop().isEmpty() && this.possibleMovesDown().isEmpty()) {
            for (int i = 0; i < GRID_LENGTH; i++) {
                if (boardGrid[0][i] != 0) {
                    this.TopScore += boardGrid[0][i];
                } else {
                    this.DownScore += boardGrid[1][i];
                }
            }
            return true;
        }
        return DownScore > 24 || TopScore > 24 || DownScore + TopScore > 41;
    }

    @Override
    public ArrayList<Score<AwaleRole>> getScores() {
        ArrayList<Score<AwaleRole>> scores = new ArrayList<>();
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

    // ---------------------- Private Methods ---------------------

    private int[][] copyGrid(int[][] boardGrid) {
        int[][] newGrid = new int[GRID_HEIGHT][GRID_LENGTH];
        for (int i = 0; i < GRID_HEIGHT; i++) {
            System.arraycopy(boardGrid[i], 0, newGrid[i], 0, GRID_LENGTH);
        }

        return newGrid;
    }

    private ArrayList<AwaleMove> possibleMovesTop() {
        ArrayList<AwaleMove> allPossibleMovesTop = new ArrayList<>();
        for (int j = 0; j < GRID_LENGTH; j++) {
            if (boardGrid[0][j] > 0) {
                AwaleMove possible = new AwaleMove(j);
                if (isValidMove(possible, AwaleRole.Top)) allPossibleMovesTop.add(possible);
            }
        }
        return allPossibleMovesTop;
    }

    private ArrayList<AwaleMove> possibleMovesDown() {
        ArrayList<AwaleMove> allPossibleMovesDown = new ArrayList<>();
        for (int j = 0; j < GRID_LENGTH; j++) {
            if (boardGrid[1][j] > 0) {
                AwaleMove possible = new AwaleMove(j);
                if (isValidMove(possible, AwaleRole.Down)) allPossibleMovesDown.add(possible);
            }
        }
        return allPossibleMovesDown;
    }
}
