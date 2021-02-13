package games.awale;

import games.dominos.DominosBoard;
import games.dominos.DominosMove;
import games.dominos.DominosRole;
import iialib.games.model.IBoard;
import iialib.games.model.Score;

import java.util.ArrayList;

public class AwaleBoard implements IBoard<AwaleMove, AwaleRole, AwaleBoard> {
    @Override
    public ArrayList<AwaleMove> possibleMoves(AwaleRole playerRole) {
        return null;
    }

    @Override
    public AwaleBoard play(AwaleMove move, AwaleRole playerRole) {
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
}
