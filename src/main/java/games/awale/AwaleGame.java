package games.awale;

import games.dominos.DominosBoard;
import games.dominos.DominosMove;
import games.dominos.DominosRole;
import iialib.games.algs.AIPlayer;
import iialib.games.algs.AbstractGame;

import java.util.ArrayList;

public class AwaleGame extends AbstractGame<DominosMove, DominosRole, DominosBoard> {
    public AwaleGame(ArrayList<AIPlayer<DominosMove, DominosRole, DominosBoard>> aiPlayers, DominosBoard initialBoard) {
        super(aiPlayers, initialBoard);
    }
}
