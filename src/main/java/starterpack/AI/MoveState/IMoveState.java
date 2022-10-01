package starterpack.AI.MoveState;

import starterpack.AI.AIState;
import starterpack.game.GameState;

public abstract class IMoveState extends AIState{
    public IMoveState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }
    public abstract void Move();
    public abstract void DetectTarget();
    public abstract void Teleport();
}
