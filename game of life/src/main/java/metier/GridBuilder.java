package metier;

import enumeration.CellState;
import model.Coordinate;

public interface GridBuilder {

    GridBuilder at(Coordinate coordinate);

    GridBuilder at(Coordinate coordinate, CellState state);

    Grid build();
}