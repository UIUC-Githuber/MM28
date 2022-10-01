package starterpack.AI.UseState;

import starterpack.AI.AIState;
import starterpack.game.GameState;

public abstract class IUseState extends AIState{
    public abstract void Use(GameState gameState); // Note: Should Check whether item is useable.
}
