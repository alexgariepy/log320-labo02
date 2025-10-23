import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class CPUPlayer {

    // Contient le nombre de noeuds visités (le nombre
    // d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée
    // au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;
    // Store the CPU's mark
    private Mark cpuMark;

    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu) {
        this.cpuMark = cpu;
    }

    // Ne pas changer cette méthode
    public int getNumOfExploredNodes() {
        return numExploredNodes;
    }

    // Retourne la liste des coups possibles. Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score
    public ArrayList<Move> getNextMoveMinMax(Board board) {
        numExploredNodes = 0;

        // Assume 'cpu' is the maximizing player
        ArrayList<Move> bestMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;
        // Find all possible moves
        ArrayList<Move> possibleMoves = getAvailableMoves(board);

        for (Move move : possibleMoves) {
            Board newbBoard = cloneBoard(board);
            newbBoard.play(move, getCpuMark());
            int score = minmax(newbBoard, false);
            if (score > bestScore) { // On va chercher le score maximum à partir de l'algorithme
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (score == bestScore) {
                bestMoves.add(move);
            }
        }

        return bestMoves;
    }

    // Retourne la liste des coups possibles. Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board) {
        numExploredNodes = 0;
        // Assume 'cpu' is the maximizing player
        ArrayList<Move> bestMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;
        // Find all possible moves
        ArrayList<Move> possibleMoves = getAvailableMoves(board);
        for (Move move : possibleMoves) {
            Board newBoard = cloneBoard(board);
            newBoard.play(move, getCpuMark());
            int score = alphaBeta(newBoard, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (score == bestScore) {
                bestMoves.add(move);
            }
        }
        return bestMoves;
    }

    private Mark getCpuMark() {
        return cpuMark;
    }

    // Helper: get the opponent's mark
    private Mark getOpponentMark() {
        return cpuMark == Mark.X ? Mark.O : Mark.X;
    }

    // Helper: generate all available moves
    private ArrayList<Move> getAvailableMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                // Assume Board has a method to get mark at (r, c)
                if (getMarkAt(board, r, c) == Mark.EMPTY) {
                    moves.add(new Move(r, c));
                }
            }
        }
        return moves;
    }

    // Helper: get mark at position (assumes Board has a method or field for this)
    private Mark getMarkAt(Board board, int row, int col) {
        // Reflection or direct access if possible
        try {
            java.lang.reflect.Field f = board.getClass().getDeclaredField("board");
            f.setAccessible(true);
            Mark[][] b = (Mark[][]) f.get(board);
            return b[row][col];
        } catch (Exception e) {
            return Mark.EMPTY; // fallback
        }
    }

    // Helper: clone the board (deep copy)
    private Board cloneBoard(Board board) {
        Board newBoard = new Board();
        try {
            java.lang.reflect.Field f = board.getClass().getDeclaredField("board");
            f.setAccessible(true);
            Mark[][] b = (Mark[][]) f.get(board);
            Mark[][] copy = new Mark[3][3];
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    copy[i][j] = b[i][j];
            java.lang.reflect.Field f2 = newBoard.getClass().getDeclaredField("board");
            f2.setAccessible(true);
            f2.set(newBoard, copy);
        } catch (Exception e) {
            // ignore
        }
        return newBoard;
    }

    // Alpha-Beta recursive search
    private int alphaBeta(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        numExploredNodes++;
        int eval = board.evaluate(cpuMark);
        if (Math.abs(eval) == 100 || getAvailableMoves(board).isEmpty()) {
            return eval;
        }
        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : getAvailableMoves(board)) {
                Board newBoard = cloneBoard(board);
                newBoard.play(move, cpuMark);
                int evalChild = alphaBeta(newBoard, depth + 1, alpha, beta, false);
                maxEval = Math.max(maxEval, evalChild);
                alpha = Math.max(alpha, evalChild);
                if (beta <= alpha)
                    break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : getAvailableMoves(board)) {
                Board newBoard = cloneBoard(board);
                newBoard.play(move, getOpponentMark());
                int evalChild = alphaBeta(newBoard, depth + 1, alpha, beta, true);
                minEval = Math.min(minEval, evalChild);
                beta = Math.min(beta, evalChild);
                if (beta <= alpha)
                    break;

            }
            return minEval;
        }
    }

    // Minmax recursive search
    private int minmax(Board board, boolean maximizingPlayer) {
        numExploredNodes++;
        int eval = board.evaluate(cpuMark);

        ArrayList<Move> possiblesMoves = getAvailableMoves(board);
        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        if (Math.abs(eval) == 100 || possiblesMoves.isEmpty()) {// ici on evalue si on a deja gagné ou on a plus de
                                                                // moves disponibles.
            return eval;
        }

        for (Move move : possiblesMoves) { // on parcours les moves possibles
            Board newBoard = cloneBoard(board);// on fait une copie du board actuelle.
            newBoard.play(move, maximizingPlayer ? cpuMark : getOpponentMark()); // on joue un move en fonction du tour
                                                                                 // à jouer
            int score = minmax(newBoard, !maximizingPlayer); // on joue pour l'adversaire avec maximizingPlayer = false

            // Ce bloc sert à choisir le meilleur score selon le joueur courant :
            if (maximizingPlayer) { // je suis max
                bestScore = Math.max(bestScore, score);
            } else { // je suis min
                bestScore = Math.min(bestScore, score);
            }

        }

        return bestScore;

    }
}

// end of CPUPlayer class
