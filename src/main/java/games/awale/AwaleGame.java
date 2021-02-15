package games.awale;

import iialib.games.algs.AIPlayer;
import iialib.games.algs.AbstractGame;
import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.AlphaBeta;
import iialib.games.algs.algorithms.MiniMax;

import java.util.ArrayList;

class AwaleGame extends AbstractGame<AwaleMove, AwaleRole, AwaleBoard> {

    public AwaleGame(ArrayList<AIPlayer<AwaleMove, AwaleRole, AwaleBoard>> aiPlayers, AwaleBoard initialBoard) {
        super(aiPlayers, initialBoard);
    }

    public static void main(String[] args) {

        AwaleRole roleT = AwaleRole.Top;
        AwaleRole roleD = AwaleRole.Down;

//        GameAlgorithm<AwaleMove, AwaleRole, AwaleBoard> algT = new MiniMax<>(
//                roleT, roleD, AwaleHeuristics.hTop, 4); // Minimax depth 4
//
//        GameAlgorithm<AwaleMove, AwaleRole, AwaleBoard> algD = new MiniMax<>(
//                roleD, roleT, AwaleHeuristics.hDown, 2); // Minimax depth 2
//
//        GameAlgorithm<AwaleMove, AwaleRole, AwaleBoard> algT = new AlphaBeta<>(
//                roleT, roleD, AwaleHeuristics.hTop, 4); // Alphabeta depth 4
//
//        GameAlgorithm<AwaleMove, AwaleRole, AwaleBoard> algD = new AlphaBeta<>(
//                roleD, roleT, AwaleHeuristics.hDown, 2); // Alphabeta depth 2

        GameAlgorithm<AwaleMove, AwaleRole, AwaleBoard> algT = new MiniMax<>(
                roleT, roleD, AwaleHeuristics.hTop, 4); // MiniMax depth 4
        GameAlgorithm<AwaleMove, AwaleRole, AwaleBoard> algD = new AlphaBeta<>(
                roleD, roleT, AwaleHeuristics.hDown, 4); // Alphabeta depth 4

        AIPlayer<AwaleMove, AwaleRole, AwaleBoard> playerT = new AIPlayer<>(
                roleT, algT);

        AIPlayer<AwaleMove, AwaleRole, AwaleBoard> playerD = new AIPlayer<>(
                roleD, algD);

        ArrayList<AIPlayer<AwaleMove, AwaleRole, AwaleBoard>> players = new ArrayList<>();

        players.add(playerT); // First Player
        players.add(playerD); // Second Player

        // Setting the initial Board
        AwaleBoard initialBoard = new AwaleBoard();

        AwaleGame game = new AwaleGame(players, initialBoard);
        game.runGame();
    }
}
