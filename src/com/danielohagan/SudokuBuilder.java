package com.company;

public class SudokuBuilder {

    public static int EMPTY_CELL_KEY = 0;

    private int[][] mGrid;
    int mGridRowCount, mGridColumnCount, mGridBoxCountX, mGridBoxCountY;
    int mBoxRowCount, mBoxColumnCount; //num of rows and columns in a box


    public void buildRandomGrid(int gridRowCount, int gridColumnCount) {
        mGridRowCount = gridRowCount;
        mGridColumnCount = gridColumnCount;
        mGridBoxCountX = (int) Math.floor(Math.sqrt(mGridColumnCount));
        mGridBoxCountY = (int) Math.floor(Math.sqrt(mGridRowCount));
        mBoxRowCount = mGridRowCount / mGridBoxCountY;
        mBoxColumnCount = mGridColumnCount / mGridBoxCountX;

        //create a random solved grid
        buildRandomSolvedGrid();

        //remove cells from the grid
        removeRandomCells(9, 14); //change the arguments here for lower/higher difficulty
    }

    private void buildRandomSolvedGrid() {
        mGrid = new int[mGridRowCount][mGridColumnCount];
        int numberOfCellsToFill = (int) Math.ceil(Math.random() * 2) + 3; //decides how many numbers to place
        int numberMax = mGridColumnCount > mGridRowCount ? mGridColumnCount : mGridRowCount; //highest value the number can be
        Sudoku sudoku;

        clearGrid();

        //create a random solved grid:
        //add the numbers in random places
        for (int i = 0; i < numberOfCellsToFill; i++) {
            int rowPos = (int) Math.ceil(Math.random() * mGridRowCount) - 1;
            int columnPos = (int) Math.ceil(Math.random() * mGridColumnCount) - 1;
            int number = (int) Math.ceil(Math.random() * numberMax);
            if (isNumberValid(rowPos, columnPos, number)) {
                mGrid[rowPos][columnPos] = number;
            } else {
                i--; //rerun this iteration
            }
        }

        //solve the grid
        sudoku = new Sudoku(mGrid);
        if (sudoku.solveRecursively()) {
            mGrid = sudoku.getGrid();
        } else {
            buildRandomGrid(mGridRowCount, mGridColumnCount);
        }
    }

    private void removeRandomCells(int minNumUsedCells, int maxNumUsedCells) {
        int range = (maxNumUsedCells - minNumUsedCells) > 0 ? (maxNumUsedCells - minNumUsedCells) : 5; //a check for if the two values were input wrong
        int numOfCells = mGridColumnCount * mGridRowCount;
        int numOfCellsRemoving = numOfCells - (
                minNumUsedCells + (int) Math.ceil(Math.random() * range)
        );

        for (int i = 0; i < numOfCellsRemoving; i++) {
            int rowPos = (int) Math.ceil(Math.random() * mGridRowCount) - 1;
            int columnPos = (int) Math.ceil(Math.random() * mGridColumnCount) - 1;

            if (mGrid[rowPos][columnPos] != EMPTY_CELL_KEY) {
                mGrid[rowPos][columnPos] = EMPTY_CELL_KEY;
            } else {
                i--;
            }
        }
    }

    public void printToSystem() {
        for (int row = 0; row < mGridRowCount; row++) {
            for (int column = 0; column < mGridColumnCount; column++) {
                System.out.print(mGrid[row][column] + " ");
                if ((column + 1) % mGridBoxCountX == 0 && (column + 1) != mGridColumnCount) {
                    System.out.print("| ");
                }
            }

            System.out.println();

            if ((row + 1) % mGridBoxCountY == 0 && (row + 1) != mGridRowCount) {
                for (int column = 0; column < mGridColumnCount; column++) {
                    if (column == 0) {
                        System.out.print("-");
                    } else {
                        System.out.print(" -");
                    }

                    if ((column + 1) % mGridBoxCountX == 0 && (column + 1) != mGridColumnCount) {
                        System.out.print(" +");
                    }
                }
                System.out.println();
            }
        }
    }

    private boolean isNumberValid(int row, int col, int number) {
        return !isNumberInRow(row, number) &&
                !isNumberInColumn(col, number) &&
                !isNumberInBox(row, col, number);
    }

    private boolean isNumberInRow(int row, int number) {
        for (int column = 0; column < mGridColumnCount; column++) {
            if (mGrid[row][column] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberInColumn(int column, int number) {
        for (int row = 0; row < mGridRowCount; row++) {
            if (mGrid[row][column] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberInBox(int row, int column, int number) {
        int boxRow = row - (row % mBoxRowCount);
        int boxColumn = column - (column % mBoxColumnCount);

        for (int i = boxRow; i < boxRow + mBoxRowCount; i++) {
            for (int j = boxColumn; j < boxColumn + mBoxColumnCount; j++) {
                if (mGrid[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private void clearGrid() {
        //turns everything into the EMPTY_CELL_KEY
        for (int i = 0; i < mGridRowCount; i++) {
            for (int j = 0; j < mGridColumnCount; j++) {
                mGrid[i][j] = EMPTY_CELL_KEY;
            }
        }
    }

    public void reset() {
        //just a function that might be of some use sometime
        mGrid = null;
        mGridColumnCount = mGridRowCount = mGridBoxCountX = mGridBoxCountY = 0;
        mBoxRowCount = mBoxColumnCount = 0;
    }
}