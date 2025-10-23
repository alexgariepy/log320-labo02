public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to your basic Java app!");

        // Test different board scenarios
        System.out.println("\n--- Board Scenarios: CPU as X ---");
        CPUPlayer cpu = new CPUPlayer(Mark.X);

        // Scenario 1: X can win
        Board board1 = new Board();
        board1.play(new Move(0, 0), Mark.X);
        board1.play(new Move(0, 1), Mark.X);
        board1.play(new Move(1, 0), Mark.O);
        board1.play(new Move(1, 1), Mark.O);
        System.out.println("Scenario 1: X can win");
        var moves1 = cpu.getNextMoveAB(board1);
        for (Move m : moves1) {
            System.out.println("Best move: Row " + m.getRow() + ", Col " + m.getCol());
        }

        // Scenario 2: O can win, X must block
        Board board2 = new Board();
        board2.play(new Move(0, 0), Mark.O);
        board2.play(new Move(0, 1), Mark.O);
        board2.play(new Move(1, 0), Mark.X);
        board2.play(new Move(1, 1), Mark.X);
        System.out.println("\nScenario 2: O can win, X must block");
        var moves2 = cpu.getNextMoveAB(board2);
        for (Move m : moves2) {
            System.out.println("Best move: Row " + m.getRow() + ", Col " + m.getCol());
        }

        // Scenario 3: Fork opportunity
        Board board3 = new Board();
        board3.play(new Move(0, 0), Mark.X);
        board3.play(new Move(2, 2), Mark.X);
        board3.play(new Move(1, 1), Mark.O);
        System.out.println("\nScenario 3: Fork opportunity");
        var moves3 = cpu.getNextMoveAB(board3);
        for (Move m : moves3) {
            System.out.println("Best move: Row " + m.getRow() + ", Col " + m.getCol());
        }

        // Test different board scenarios with minmax
        System.out.println("\n----------------------------------------------------------------------");
        System.out.println("\n--- Minmax Board Scenarios: CPU as X ---");
        CPUPlayer cpu2 = new CPUPlayer(Mark.X);

        // Scenario 1: X can win
        Board board4 = new Board();
        board4.play(new Move(0, 0), Mark.X);
        board4.play(new Move(0, 1), Mark.X);
        board4.play(new Move(1, 0), Mark.O);
        board4.play(new Move(1, 1), Mark.O);
        System.out.println("Scenario 1: X can win");
        var moves4 = cpu2.getNextMoveMinMax(board1);
        for (Move m : moves4) {
            System.out.println("Best move: Row " + m.getRow() + ", Col " + m.getCol());
        }

        // Scenario 2: O can win, X must block
        Board board5 = new Board();
        board5.play(new Move(0, 0), Mark.O);
        board5.play(new Move(0, 1), Mark.O);
        board5.play(new Move(1, 0), Mark.X);
        board5.play(new Move(1, 1), Mark.X);
        System.out.println("\nScenario 2: O can win, X must block");
        var moves5 = cpu2.getNextMoveMinMax(board2);
        for (Move m : moves5) {
            System.out.println("Best move: Row " + m.getRow() + ", Col " + m.getCol());
        }

        // Scenario 3: Fork opportunity
        Board board6 = new Board();
        board6.play(new Move(0, 0), Mark.X);
        board6.play(new Move(2, 2), Mark.X);
        board6.play(new Move(1, 1), Mark.O);
        System.out.println("\nScenario 3: Fork opportunity");
        var moves6 = cpu2.getNextMoveMinMax(board3);
        for (Move m : moves6) {
            System.out.println("Best move: Row " + m.getRow() + ", Col " + m.getCol());
        }

        // Simulate a full game: CPU vs CPU (X vs O)
        /*
         * Board board = new Board();
         * CPUPlayer cpuX = new CPUPlayer(Mark.X);
         * CPUPlayer cpuO = new CPUPlayer(Mark.O);
         * Mark current = Mark.X;
         * int moveNum = 1;
         * System.out.println("\nSimulating full game (CPU X vs CPU O):");
         * for (int i = 0; i < 9; i++) {
         * CPUPlayer cpu = (current == Mark.X) ? cpuX : cpuO;
         * var moves = cpu.getNextMoveAB(board);
         * if (moves.isEmpty()) {
         * System.out.println("No moves left!");
         * break;
         * }
         * Move bestMove = moves.get(0); // pick first best move
         * board.play(bestMove, current);
         * System.out.println("Move " + moveNum + ": Player " + current + " plays Row: "
         * + bestMove.getRow() + ", Col: " + bestMove.getCol());
         * int eval = board.evaluate(current);
         * if (eval == 100) {
         * System.out.println("Player " + current + " wins!");
         * break;
         * } else if (eval == -100) {
         * System.out.println("Player " + (current == Mark.X ? "O" : "X") + " wins!");
         * break;
         * }
         * // Switch player
         * current = (current == Mark.X) ? Mark.O : Mark.X;
         * moveNum++;
         * }
         * if (board.evaluate(Mark.X) == 0 && board.evaluate(Mark.O) == 0) {
         * System.out.println("Game ended in a draw.");
         * }
         */
    }
}
