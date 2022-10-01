package starterpack.AI.BuyState;

import starterpack.AI.AIState;
import starterpack.game.Item;

public abstract class IBuyState extends AIState{
    public abstract void Buy(Item item);
}
