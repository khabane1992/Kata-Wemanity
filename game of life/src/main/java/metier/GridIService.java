package metier;

import enumeration.CellState;
import model.Coordinate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static enumeration.CellState.*;

public class GridIService {

    private GridIService(){}

    public static class GridBuilderImpl implements GridBuilder {
        private Grid current = new EmptyGridImpl();

        @Override
        public GridBuilder at(Coordinate coordinate) {
            return at(coordinate, ALIVE);
        }

        @Override
        public GridBuilder at(Coordinate coordinate, CellState state) {
            if (state == ALIVE) {
                current = new GridImpl(coordinate, current);
            }
            return this;
        }

        @Override
        public Grid build() {
            return current;
        }
    }

    public static class GridImpl implements Grid {

        private Grid parent;
        private Coordinate coordinate;

        public GridImpl(Coordinate coordinate, Grid parent) {
            this.coordinate = coordinate;
            this.parent = parent;
        }

        @Override
        public int numberOfAliveNeighbours(Coordinate coordinate) {
            return isAlive(coordinate.add(-1, -1)) +
                    isAlive(coordinate.add(0, -1)) +
                    isAlive(coordinate.add(1, -1)) +

                    isAlive(coordinate.add(-1, 0)) +
                    isAlive(coordinate.add(1, 0)) +

                    isAlive(coordinate.add(-1, 1)) +
                    isAlive(coordinate.add(0, 1)) +
                    isAlive(coordinate.add(1, 1));
        }

        private int isAlive(Coordinate coordinate) {
            return at(coordinate) == ALIVE ? 1 : 0;
        }

        @Override
        public Grid mapParticipatingCells(Function<Coordinate, CellState> cellMap) {
            final List<Coordinate> coordinates = Arrays.asList(
                    coordinate.add(-1, -1),
                    coordinate.add(0, -1),
                    coordinate.add(1, -1),
                    coordinate.add(-1, 0),
                    coordinate.add(0, 0),
                    coordinate.add(1, 0),
                    coordinate.add(-1, 1),
                    coordinate.add(0, 1),
                    coordinate.add(1, 1));

            Grid accumulator = parent.mapParticipatingCells(cellMap);
            for (Coordinate c : coordinates) {
                accumulator = addCell(accumulator, c, cellMap.apply(c));
            }
            return accumulator;

        }

        private Grid addCell(Grid grid, Coordinate coordinate, CellState cellState) {
            if (cellState == ALIVE) {
                return new GridImpl(coordinate, grid);
            } else {
                return grid;
            }
        }

        @Override
        public CellState at(Coordinate coordinate) {
            return this.coordinate.equals(coordinate) ? ALIVE : parent.at(coordinate);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    public static class EmptyGridImpl implements Grid {

        @Override
        public int numberOfAliveNeighbours(Coordinate coordinate) {
            return 0;
        }

        @Override
        public Grid mapParticipatingCells(Function<Coordinate, CellState> cellMap) {
            return new EmptyGridImpl();
        }

        @Override
        public CellState at(Coordinate coordinate) {
            return DEAD;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }
}
