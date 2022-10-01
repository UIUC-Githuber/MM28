package starterpack.AI.MoveState;

import starterpack.AI.AIState;
import starterpack.game.PlayerState;
import starterpack.game.Position;

public abstract class IMoveState extends AIState{
    public abstract void Move(Position pos);
    public abstract void DetectTarget(PlayerState Target);
}
