package com.danielohagan;

public class SudokuBuilder {

    public static int EMPTY_CELL_KEY = 0;

    private int[][] mGrid;
    int mGridRowCount, mGridColumnCount, mGridBoxCountX, mGridBoxCountY;
    int mBoxRowCount, mBoxColumnCount; //num of rows and columns in a box

    public int[][] getGrid() {
        return mGrid;
    }

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
        removeRandomCells(
                0, 0, mGridRowCount, mGridColumnCount,
                15, 19
        );//change the last two arguments here for lower/higher difficulty
    }

    private void buildRandomSolvedGrid() {
        mGrid = new int[mGridRowCount][mGridColumnCount];
        int numCellsToFill = (int) Math.ceil(Math.random() * 2) + 3; //decides how many numbers to place
        int numMax = mGridColumnCount > mGridRowCount ? mGridColumnCount : mGridRowCount; //highest value the number can be
        int firstBoxMinNumCount = 2; //the minimum amount of numbers that should be in the first box, so the grid is more random
        int firstBoxNumCount = 0;
        Sudoku sudoku;

        clearGrid();

        //create a random solved grid:
        //add the numbers in random places
        addRandomNumbers(mGridRowCount, mGridColumnCount, numCellsToFill, numMax);

        //make sure that no boxes are completely full
        for (int i = 0; i < mGridRowCount; i += mBoxRowCount) {
            for (int j = 0; j < mGridColumnCount; j += mBoxColumnCount) {
                if (getBoxNumberCount(i, j) == 9) {//removes 1 or 2 cells if the box contains 9
                    removeRandomCells(i, j, mBoxRowCount, mGridColumnCount, 7, 8);
                }
            }
        }

        firstBoxNumCount = getBoxNumberCount(0, 0);
        if (firstBoxNumCount < firstBoxMinNumCount) {
            addRandomNumbers(
                    mBoxRowCount, mBoxColumnCount,
                    firstBoxMinNumCount - firstBoxNumCount,
                    numMax
            );
        }

        //solve the grid
        sudoku = new Sudoku(mGrid);
        if (sudoku.solveRecursively()) {
            mGrid = sudoku.getGrid();
        } else {
            buildRandomGrid(mGridRowCount, mGridColumnCount);
        }
    }

    private void addRandomNumbers(int rowCount, int columnCount, int numberOfCellsToFill, int numberMax) {
        for (int i = 0; i < numberOfCellsToFill; i++) {
            int rowPos = (int) Math.ceil(Math.random() * rowCount) - 1;
            int columnPos = (int) Math.ceil(Math.random() * columnCount) - 1;
            int number = (int) Math.ceil(Math.random() * numberMax);
            if (mGrid[rowPos][columnPos] == EMPTY_CELL_KEY && isNumberValid(rowPos, columnPos, number)) {
                mGrid[rowPos][columnPos] = number;
            } else {
                i--; //rerun this iteration
            }
        }
    }

    private void removeRandomCells(
            int startRow, int startColumn, int rowCount,
            int columnCount, int minNumUsedCells, int maxNumUsedCells
    ) {
        int range = (maxNumUsedCells - minNumUsedCells) > 0 ? (maxNumUsedCells - minNumUsedCells) : 5; //a check for if the two values were input wrong
        int numOfCells = rowCount * columnCount;
        int numOfCellsRemoving = numOfCells - (
                minNumUsedCells + (int) Math.ceil(Math.random() * range)
        );

        for (int i = 0; i < numOfCellsRemoving; i++) {
            int rowPos = ((int) Math.ceil(Math.random() * rowCount) - 1) + startRow;
            int columnPos = ((int) Math.ceil(Math.random() * columnCount) - 1) + startColumn;

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

    private int getBoxNumberCount(int row, int column) {
        int boxRow = row - (row % mBoxRowCount);
        int boxColumn = column - (column % mBoxColumnCount);
        int boxNumberCount = 0; //amount of numbers in the box

        for (int i = boxRow; i < boxRow + mBoxRowCount; i++) {
            for (int j = boxColumn; j < boxColumn + mBoxColumnCount; j++) {
                if (mGrid[i][j] != EMPTY_CELL_KEY) {
                    boxNumberCount++;
                }
            }
        }

        return boxNumberCount;
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