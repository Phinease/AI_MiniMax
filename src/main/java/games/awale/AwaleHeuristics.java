package games.awale;

import iialib.games.algs.IHeuristic;

public class AwaleHeuristics {

    /*
    On a essayé 4 idées:
        1. différences des graines des deux cotés:
            htop = NbSeedLastTop - NbSeedTop
            hDown = NbSeedLastDown - NbSeedDown
        2. Nombre de graines de notre coté:
            htop = NbSeedTop
            hDown = NbSeedDown
        3. Nombre de coups possibles:
            hTop = board.possibleMoves(AwaleRole.Top).size()
            hDown = board.possibleMoves(AwaleRole.Down).size()
        4. La différence entre le nombre de graines restantes
        de notre coté et le nombre de graines restantes de l'autre coté.
     */
    // 4ème Idée la meilleur
    public static IHeuristic<AwaleBoard, AwaleRole> hTop = (board, role) -> board.nbSeedTop - board.nbSeedDown;
    public static IHeuristic<AwaleBoard, AwaleRole> hDown = (board, role) -> board.nbSeedDown - board.nbSeedTop;
}
