package games.awale;

import iialib.games.model.IMove;

public class AwaleMove implements IMove {
    int take;

    AwaleMove(int take) {
        this.take = take;
    }

    @Override
    public String toString() { return "Move{" + (take + 1) + "}"; }
}
