package Dags2D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {

    @Test
    void getInstance() {
        Space space = Space.getInstance(new Origin(new Coord2D(3.13, 5.12)));
        Space anotherSpace = Space.getInstance(new Origin(new Coord2D(1.13, -23.122)));

        assertEquals(space, anotherSpace);

        anotherSpace = Space.getInstance(new Origin(new Coord2D(-111.13, 20.11112)));
        assertEquals(space, anotherSpace);

        space = Space.getInstance(null);
        assertEquals(space, anotherSpace);

    }
}