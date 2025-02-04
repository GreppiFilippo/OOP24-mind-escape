package mindescape.model.world.items.interactable.impl;

import java.util.Optional;
import mindescape.model.world.core.api.Dimensions;
import mindescape.model.world.core.api.Point2D;
import mindescape.model.world.core.impl.GameObjectImpl;
import mindescape.model.world.items.interactable.api.Pickable;
import mindescape.model.world.player.api.Player;

/**
 * Represents a concrete implementation of the {@link Pickable} interface.
 * <p>
 * Defines an item within the game world that can be picked up by the player. 
 * Extends {@link GameObjectImpl}, inheriting properties like position, name, 
 * and dimensions.
 * </p>
 * <p>
 * Tracks whether the item has been picked up and provides a description for 
 * additional context within the game.
 * </p>
 *
 * @see Pickable
 * @see GameObjectImpl
 */
public class PickableImpl extends GameObjectImpl implements Pickable {

    private final String description;
    private final Integer id; 

    /**
     * Constructs a pickable item with a specified position, name, dimensions, 
     * description, and ID.
     *
     * @param position    the optional position of the item in the game world
     * @param name        the name of the item
     * @param dimensions  the dimensions of the item
     * @param description a brief description of the item
     * @param id          the unique identifier of the item
     */
    public PickableImpl(final Optional<Point2D> position, final String name,
                        final Dimensions dimensions, final String description, final Integer id) {
        super(position, name, dimensions);
        this.description = description;
        this.id = id; 
    }

    /**
     * Defines the interaction behavior when the player interacts with the item.
     * <p>
     * Adds the item to the player's inventory and removes it from the current room.
     * </p>
     *
     * @param player the player interacting with the item
     */
    @Override
    public void onAction(final Player player) {
        player.getInventory().addItems(this);
        player.getCurrentRoom().removeGameObject(this);
    }

    /**
     * Retrieves the description of the item.
     *
     * @return the description of the item
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Retrieves the unique identifier of the item.
     *
     * @return the item's unique ID
     */
    @Override
    public Integer getId() {
        return this.id; 
    }
}
