package mindescape.model.enigma.enigmapuzzle.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import mindescape.model.enigma.enigmapuzzle.api.EnigmaPuzzleModel;

/**
 * The {@code EnigmaPuzzleModelImpl} class implements {@code EnigmaPuzzleModel} to provide functionalities
 * for the Enigma Puzzle enigma.
 */
public final class EnigmaPuzzleModelImpl implements EnigmaPuzzleModel, Serializable {

    private static final long serialVersionUID = 1L;
    private final int rows;
    private final int cols;
    private final Integer[][] pieces;
    private final String puzzleName;
    private Integer clickedButtonIndex;

    /**
     * Constructs an EnigmaPuzzleModelImpl with the specified number of rows and columns and the puzzle name.
     *
     * @param rows the number of rows in the puzzle
     * @param cols the number of columns in the puzzle
     * @param puzzleName the name of the puzzle
     */
    public EnigmaPuzzleModelImpl(final int rows, final int cols, final String puzzleName) {
        this.rows = rows;
        this.cols = cols;
        this.puzzleName = puzzleName;
        this.pieces = new Integer[rows][cols];
        for (int i = 0; i < rows * cols; i++) {
            pieces[i / cols][i % cols] = i;
        }
    }
    /**
     * Returns the number of rows in the enigma puzzle.
     *
     * @return the number of rows
     */
    @Override
    public int getRows() {
        return this.rows;
    }

    /**
     * Returns the number of columns in the enigma puzzle.
     *
     * @return the number of columns
     */
    @Override
    public int getCols() {
        return this.cols;
    }

    /**
     * Retrieves the image piece located at the specified row and column.
     *
     * @param row the row index of the piece to retrieve
     * @param col the column index of the piece to retrieve
     * @return the image piece at the specified row and column
     */
    @Override
    public Integer getPiece(final int row, final int col) {
        return this.pieces[row][col];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.puzzleName;
    }

    /**
     * Shuffles the pieces of the puzzle randomly.
     * This method first collects all pieces into a list, shuffles the list, and then
     * assigns the shuffled pieces back to the 2D array.
     */
    @Override
    public void shufflePieces() {
        final List<Integer> shuffledPieces = new ArrayList<>();

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                shuffledPieces.add(pieces[i][j]);
            }
        }

        final Random rand = new Random();
        Collections.shuffle(shuffledPieces, rand);

        int index = 0;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                pieces[i][j] = shuffledPieces.get(index++);
            }
        }
    }

    /**
     * Retrieves a copy of the 2D array of image pieces that make up the enigma puzzle.
     *
     * @return a new 2D array of {@link Image} objects representing the pieces of the puzzle.
     */
    @Override
    public Integer[][] getPieces() {
        return Arrays.stream(pieces)
                     .map(row -> Arrays.copyOf(row, row.length))
                     .toArray(Integer[][]::new);
    }

    /**
     * Swaps the pieces at the specified indices.
     *
     * @param index1 the index of the first piece to swap
     * @param index2 the index of the second piece to swap
     */
    @Override
    public void swapPieces(final Integer index1, final Integer index2) {
        final Integer firstRow = index1 / this.cols;
        final Integer firstCol = index1 % this.cols;
        final Integer secondRow = index2 / this.cols;
        final Integer secondCol = index2 % this.cols;
        final Integer temp = pieces[firstRow][firstCol];
        pieces[firstRow][firstCol] = pieces[secondRow][secondCol];
        pieces[secondRow][secondCol] = temp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSolved() {
        int counter = 0;
        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                if (pieces[j][i] == counter) {
                    counter++;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hit(final Object value) {
        final Integer hitIndex = (Integer) value;
        if (this.clickedButtonIndex == null) {
            this.clickedButtonIndex = hitIndex;
            return false;
        } else {
            this.swapPieces(clickedButtonIndex, hitIndex);
            this.clickedButtonIndex = null;
            return true;
        }
    }
}
