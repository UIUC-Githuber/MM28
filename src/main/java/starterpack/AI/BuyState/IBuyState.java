package starterpack.AI.BuyState;

import starterpack.AI.AIState;
import starterpack.game.GameState;

public abstract class IBuyState extends AIState{
    public abstract void Buy(GameState gameState);
}
