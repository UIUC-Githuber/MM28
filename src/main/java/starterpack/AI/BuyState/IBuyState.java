package starterpack.AI.BuyState;

import starterpack.AI.AIState;
import starterpack.game.GameState;
import starterpack.game.Item;

public abstract class IBuyState extends AIState{
    public IBuyState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }
    public abstract Item Update();
    public abstract Item Buy();
}
