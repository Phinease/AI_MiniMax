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
        // Initialisation de nombre de graine et la position à poser
        int[][] newgrid = copyGrid(boardGrid);
        int indexplayer = (playerRole == AwaleRole.Top) ? 0 : 1;
        int nbgraines = newgrid[indexplayer][move.take];
        int index = (indexplayer == 0) ? move.take - 1 : move.take + 1;
        newgrid[indexplayer][move.take] = 0;
        // System.out.println("Move: " + move + ", nbgraines: " + nbgraines);

        // Poser les graines en ordre de la régle
        while (true) {
            // Passer la case qu'on prend les graines
            if (indexplayer == ((playerRole == AwaleRole.Top) ? 0 : 1) && index == move.take) {
                index += (indexplayer == 0) ? -1 : 1;
            }

            // Changer de la direction au bord de grid
            if (index >= GRID_LENGTH) {
                indexplayer = (indexplayer + 1) % 2;
                index = 5;
            }
            if (index < 0) {
                indexplayer = (indexplayer + 1) % 2;
                index = 0;
            }

            newgrid[indexplayer][index] += 1;
            nbgraines--;
            if (nbgraines == 0) break;

            // Avancer une case
            index += (indexplayer == 0) ? -1 : 1;
        }

        // ------- Calculer les scores -------

        // Sauvegarder la situation actuel si on famine l'ennemi
        int newTopScore = TopScore;
        int newDownScore = DownScore;
        int[][] backup = copyGrid(newgrid);

        // Calculer combien à tirer
        int enemy = (playerRole == AwaleRole.Top) ? 1 : 0;
        boolean bonus = indexplayer == enemy && ((newgrid[indexplayer][index] == 2) || (newgrid[indexplayer][index] == 3));
        while (bonus) {
            if (playerRole == AwaleRole.Top) {
                newTopScore += newgrid[indexplayer][index];
            } else {
                newDownScore += newgrid[indexplayer][index];
            }

            newgrid[indexplayer][index] = 0;
            index += (indexplayer == 0) ? 1 : -1;

            // Debug
            // On sais que on ne peux pas tirer les cases de notre coté
            // Mais si on suit la régle, la partie ne s'arrête pas dans certaine situation
            // if (index >= GRID_LENGTH || index < 0) break;

            if (index >= GRID_LENGTH) {
                indexplayer = (indexplayer + 1) % 2;
                index = 5;
            }
            if (index < 0) {
                indexplayer = (indexplayer + 1) % 2;
                index = 0;
            }
            bonus = (newgrid[indexplayer][index] == 2) || (newgrid[indexplayer][index] == 3);
        }

        // Si on famine l'ennemi, on retourne un board sans pris de graine
        boolean famine = true;
        for (int i = 0; i < GRID_LENGTH; i++) famine = famine && (newgrid[enemy][i] == 0);
        if (famine) return new AwaleBoard(backup, TopScore, DownScore);

        return new AwaleBoard(newgrid, newTopScore, newDownScore);
    }

    @Override
    public boolean isValidMove(AwaleMove move, AwaleRole playerRole) {
        // Vérifier si on a besoin de nourrir l'ennemi
        boolean nourrir = true;
        for (int i = 0; i < 6; i++) nourrir = nourrir && (boardGrid[(playerRole == AwaleRole.Top) ? 1 : 0][i] == 0);

        if (nourrir) {
            if (playerRole == AwaleRole.Top) {
                return boardGrid[0][move.take] > move.take;
            } else {
                return boardGrid[1][move.take] > 6 - move.take;
            }
        }

        // Vérifier si la case est vide
        return boardGrid[(playerRole == AwaleRole.Top) ? 0 : 1][move.take] > 0;
    }

    @Override
    public boolean isGameOver() {
        // Si un joueur n'a pas de coups et l'autre coté ne peut pas lui nourrir (isValidMove dans possibleMovesXXXX)
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
        //
        return DownScore > 24 || TopScore > 24 || DownScore + TopScore > 41;
    }

    @Override
    public ArrayList<Score<AwaleRole>> getScores() {
        ArrayList<Score<AwaleRole>> scores = new ArrayList<>();
        if (this.isGameOver()) {
            if (TopScore < DownScore) {
                scores.add(new Score<>(AwaleRole.Top, Score.Status.LOOSE, TopScore));
                scores.add(new Score<>(AwaleRole.Down, Score.Status.WIN, DownScore));
            } else if (TopScore > DownScore) {
                scores.add(new Score<>(AwaleRole.Top, Score.Status.WIN, TopScore));
                scores.add(new Score<>(AwaleRole.Down, Score.Status.LOOSE, DownScore));
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
