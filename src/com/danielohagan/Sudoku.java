package com.danielohagan;

public class Sudoku {

    /*
        uses recursive backtracking to solve a Sudoku puzzle with a grid of any size
        0s are meant to represent an empty space, being a 0 it can be stored in the grid array
        NOTE: the any size is not full tested, seems to work with 3 3x3 boxes and maybe
        4 4x4 boxes
     */

    private final int GRID_ROW_COUNT;
    private final int GRID_COLUMN_COUNT;
    private final int GRID_BOX_COUNT_X;
    private final int GRID_BOX_COUNT_Y;
    private final int BOX_ROW_COUNT;
    private final int BOX_COLUMN_COUNT;

    private int mCellSolveCount = 0; //the amount of times the program assigns a number to a cell
    private int[][] mGrid;

    public Sudoku(int[][] grid) {
        mGrid = grid;
        GRID_COLUMN_COUNT = mGrid[0].length;
        GRID_ROW_COUNT = mGrid.length;
        GRID_BOX_COUNT_X = (int) Math.floor(Math.sqrt(GRID_COLUMN_COUNT));
        GRID_BOX_COUNT_Y = (int) Math.floor(Math.sqrt(GRID_ROW_COUNT));
        BOX_ROW_COUNT = GRID_ROW_COUNT / GRID_BOX_COUNT_Y;
        BOX_COLUMN_COUNT = GRID_COLUMN_COUNT / GRID_BOX_COUNT_X;
    }

    public int getCellSolveCount() {
        return mCellSolveCount;
    }

    public int[][] getGrid() {
        return mGrid;
    }

    public void printToSystem() {
        for (int row = 0; row < GRID_ROW_COUNT; row++) {
            for (int column = 0; column < GRID_COLUMN_COUNT; column++) {
                System.out.print(mGrid[row][column] + " ");
                if ((column + 1) % GRID_BOX_COUNT_X == 0 && (column + 1) != GRID_COLUMN_COUNT) {
                    System.out.print("| ");
                }
            }

            System.out.println();

            if ((row + 1) % GRID_BOX_COUNT_Y == 0 && (row + 1) != GRID_ROW_COUNT) {
                for (int column = 0; column < GRID_COLUMN_COUNT; column++) {
                    if (column == 0) {
                        System.out.print("-");
                    } else {
                        System.out.print(" -");
                    }

                    if ((column + 1) % GRID_BOX_COUNT_X == 0 && (column + 1) != GRID_COLUMN_COUNT) {
                        System.out.print(" +");
                    }
                }
                System.out.println();
            }
        }
    }

    private boolean isNumberInRow(int row, int number) {
        for (int column = 0; column < GRID_COLUMN_COUNT; column++) {
            if (mGrid[row][column] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberInColumn(int column, int number) {
        for (int row = 0; row < GRID_ROW_COUNT; row++) {
            if (mGrid[row][column] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberInBox(int row, int column, int number) {
        int boxRow = row - (row % BOX_ROW_COUNT);
        int boxColumn = column - (column % BOX_COLUMN_COUNT);

        for (int i = boxRow; i < boxRow + BOX_ROW_COUNT; i++) {
            for (int j = boxColumn; j < boxColumn + BOX_COLUMN_COUNT; j++) {
                if (mGrid[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isNumberValid(int row, int col, int number) {
        return !isNumberInRow(row, number) &&
                !isNumberInColumn(col, number) &&
                !isNumberInBox(row, col, number);
    }

    public boolean solveRecursively() {
        for (int row = 0; row < GRID_ROW_COUNT; row++) {
            if (!solveRow(row)) {
                return false;
            }
        }
        return true;
    }

    private boolean solveRow(int row) {
        for (int column = 0; column < GRID_COLUMN_COUNT; column++) {
            if (mGrid[row][column] == SudokuBuilder.EMPTY_CELL_KEY) {
                if (!solveCell(row, column)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean solveCell(int row, int column) {
        for (int number = 0; (number <= GRID_COLUMN_COUNT) || (number <= GRID_ROW_COUNT); number++) {
            if (isNumberValid(row, column, number)) {
                mCellSolveCount++;
                mGrid[row][column] = number;

                if (solveRecursively()) {
                    return true;
                } else {
                    mGrid[row][column] = SudokuBuilder.EMPTY_CELL_KEY;
                }
            }
        }
        return false;
    }
}