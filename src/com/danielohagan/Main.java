package com.danielohagan;

public class Main {

    private static int[][] GRID_TO_SOLVE_3x3 = {
            {9, 0, 0, 1, 0, 0, 0, 0, 5},
            {0, 0, 5, 0, 9, 0, 2, 0, 1},
            {8, 0, 0, 0, 4, 0, 0, 0, 0},
            {0, 0, 0, 0, 8, 0, 0, 0, 0},
            {0, 0, 0, 7, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 2, 6, 0, 0, 9},
            {2, 0, 0, 3, 0, 0, 0, 0, 6},
            {0, 0, 0, 2, 0, 0, 9, 0, 0},
            {0, 0, 1, 9, 0, 4, 5, 7, 0},
    };

    private static int[][] GRID_TO_SOLVE_4x4 = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    private static Sudoku mSudoku;

    public static void main(String[] args) {

        /*
        commented code because recursive backtracking is slow,
        remove the comments to view the results
         */


//        Sudoku class
//        mSudoku = new Sudoku(GRID_TO_SOLVE_3x3);
//        mSudoku.printToSystem();
//        if (mSudoku.solveRecursively()) {
//            System.out.println();
//            System.out.println("SOLVED");
//            System.out.println();
//            mSudoku.printToSystem();
//        } else {
//            System.out.println();
//            System.out.println("Unsolvable");
//            System.out.println();
//            mSudoku.printToSystem();
//        }
//        System.out.println();
//        System.out.println("Number of cells solved: " + mSudoku.getCellSolveCount());

//        SudokuBuilder class
//        SudokuBuilder sudokuBuilder = new SudokuBuilder();
//        sudokuBuilder.buildRandomGrid(9, 9);
//        sudokuBuilder.printToSystem();

        //both in use
        SudokuBuilder sudokuBuilder1 = new SudokuBuilder();
        Sudoku sudoku;
        sudokuBuilder1.buildRandomGrid(9, 9);
        sudokuBuilder1.printToSystem();

        System.out.println();
        System.out.println();

        sudoku = new Sudoku(sudokuBuilder1.getGrid());
        sudoku.solveRecursively();
        sudoku.printToSystem();
    }
}
