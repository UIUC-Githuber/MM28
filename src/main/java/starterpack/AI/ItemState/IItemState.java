package starterpack.AI.ItemState;

import starterpack.AI.AIState;
import starterpack.game.Item;

public abstract class IItemState extends AIState{
    public abstract void Buy(Item item);
}
