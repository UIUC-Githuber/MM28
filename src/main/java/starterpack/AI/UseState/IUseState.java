package starterpack.AI.UseState;

import starterpack.AI.AIState;
import starterpack.game.GameState;

public abstract class IUseState extends AIState{
    public IUseState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
    }

    public abstract boolean Use(); // Note: Should Check whether item is useable.
    public abstract boolean Update();

}
