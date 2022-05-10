package metier;

import enumeration.CellState;
import model.Coordinate;

import java.util.function.Function;

import static enumeration.CellState.*;


public class Tick implements Function<Grid, Grid> {
    @Override
    public Grid apply(Grid generation) {
        return generation.mapParticipatingCells(c -> tickCell(generation, c));
    }

    private CellState tickCell(Grid generation, Coordinate coordinate) {
        final int numberOfAliveNeighbours = generation.numberOfAliveNeighbours(coordinate);
        switch (generation.at(coordinate)) {
            case ALIVE:
                return (numberOfAliveNeighbours < 2) ? DEAD
                        : (numberOfAliveNeighbours == 2 || numberOfAliveNeighbours == 3) ? ALIVE
                        : DEAD;
            default:
            case DEAD:
                return (numberOfAliveNeighbours == 3) ? ALIVE
                        : DEAD;
        }
    }
}
