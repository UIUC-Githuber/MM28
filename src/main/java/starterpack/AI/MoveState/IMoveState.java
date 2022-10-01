package starterpack.AI.MoveState;

import starterpack.AI.AIState;
import starterpack.game.GameState;

public abstract class IMoveState extends AIState{
    public abstract void Move(GameState gameState);
    public abstract void DetectTarget(GameState gameState);
    public abstract void Teleport(GameState gameState);
}
