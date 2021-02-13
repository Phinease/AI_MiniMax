package games.awale;

import games.dominos.DominosBoard;
import games.dominos.DominosMove;
import games.dominos.DominosRole;
import iialib.games.model.IBoard;
import iialib.games.model.Score;

import java.util.ArrayList;

public class AwaleBoard implements IBoard<DominosMove, DominosRole, DominosBoard> {
    @Override
    public ArrayList<DominosMove> possibleMoves(DominosRole playerRole) {
        return null;
    }

    @Override
    public DominosBoard play(DominosMove move, DominosRole playerRole) {
        return null;
    }

    @Override
    public boolean isValidMove(DominosMove move, DominosRole playerRole) {
        return false;
    }

    @Override
    public boolean isGameOver() {
    }

    @Override
    public ArrayList<Score<DominosRole>> getScores() {
        return null;
    }
}
