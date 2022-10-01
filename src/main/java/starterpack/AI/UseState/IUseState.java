package starterpack.AI.UseState;

import starterpack.AI.AIState;
import starterpack.game.Item;

public abstract class IUseState extends AIState{
    public abstract void Use(Item item); // Note: Should Check whether item is useable.
}
