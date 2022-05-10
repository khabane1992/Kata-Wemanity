package metier;

import enumeration.CellState;
import model.Coordinate;

import java.util.function.Function;

public interface Grid {

    int numberOfAliveNeighbours(Coordinate coordinate);

    Grid mapParticipatingCells(Function<Coordinate, CellState> cellMap);

    CellState at(Coordinate coordinate);

    boolean isEmpty();
}