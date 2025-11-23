package entity;

public final class SudokuPuzzle {
    private final int[][] startingBoard;
    private final int[][] initial;   // 0 = empty
    private final int[][] solution;  // full solved grid (can be null for now)
    private final String difficulty; // keep String to match your Game

    public SudokuPuzzle(int[][] initial, int[][] solution, String difficulty) {
        SudokuRules.validate9x9(initial, "initial");
        if (solution != null) SudokuRules.validate9x9(solution, "solution");
        this.startingBoard =SudokuRules.copy(initial);
        this.initial = SudokuRules.copy(initial);
        this.solution = (solution == null ? null : SudokuRules.copy(solution));
        this.difficulty = difficulty;
    }
    public int[][] getStartingBoard() {
        return startingBoard;
    }
    public int[][] getInitial()   { return initial; }
    public int[][] getSolution()  { return solution == null ? null : SudokuRules.copy(solution); }
    public String  getDifficulty(){ return difficulty; }
}
