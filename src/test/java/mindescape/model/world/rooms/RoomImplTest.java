package mindescape.model.world.rooms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mindescape.model.world.core.api.Dimensions;
import mindescape.model.world.core.api.Point2D;
import mindescape.model.world.core.impl.GameObjectImpl;
import mindescape.model.world.items.interactable.api.Pickable;
import mindescape.model.world.items.interactable.impl.DoorLockedWithEnigma;
import mindescape.model.world.items.interactable.impl.PickableImpl;
import mindescape.model.world.rooms.api.Room;
import mindescape.model.world.rooms.impl.RoomImpl;

final class RoomImplTest {

    private Room bedroom; 

    @BeforeEach
    void setUp() {
        final List<Room> rooms = RoomImpl.createRooms();
        bedroom = rooms.stream()
            .filter(room -> "bedroom".equals(room.getName()))
            .findFirst().get();
    }

    @Test
    void testIsPlayerPresent() {
        assertFalse(bedroom.isPlayerPresent());
    }

    // CHECKSTYLE: MagicNumber OFF
    // Magic numbers in a test are acceptable
    @Test
    void testAddGameObject() {
        final int size = bedroom.getGameObjects().size();
        bedroom.addGameObject(new GameObjectImpl(new Point2D(0, 0), "dummy", Dimensions.TILE));
        assertEquals(size + 1, bedroom.getGameObjects().size());
    }

    @Test
    void testRemoveGameObject() {
        final int size = bedroom.getGameObjects().size();
        final Pickable obj = new PickableImpl(new Point2D(0, 0), "", Dimensions.TILE, "", 0);
        bedroom.addGameObject(obj);
        assertEquals(size + 1, bedroom.getGameObjects().size());
        bedroom.removeGameObject(obj);
        assertEquals(size, bedroom.getGameObjects().size());
    }

    @Test
    void testGetGameObjects() {
        assertEquals(bedroom.getGameObjects().size(), 13);
        assertEquals(bedroom.getGameObjects().stream().filter(obj -> obj instanceof DoorLockedWithEnigma).count(), 1);
        final Pickable obj = new PickableImpl(new Point2D(0, 0), "", Dimensions.TILE, "", 0);
        bedroom.addGameObject(obj);
        assertTrue(bedroom.getGameObjects().stream().anyMatch(x -> x.equals(obj)));
    }

    @Test
    void testIsPositionValid() {
        assertFalse(bedroom.isPositionValid(new Point2D(0, -1), Dimensions.TILE));
        assertFalse(bedroom.isPositionValid(new Point2D(254., 254), Dimensions.TILE));
        assertTrue(bedroom.isPositionValid(new Point2D(120, 120), Dimensions.TILE));
    }
    // CHECKSTYLE: MagicNumber OFF

    @Test
    void testGetName() {
        assertEquals("bedroom", bedroom.getName());
    }

}
