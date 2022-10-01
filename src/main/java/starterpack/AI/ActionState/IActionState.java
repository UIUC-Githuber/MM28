package starterpack.AI.ActionState;

import starterpack.AI.AIState;
import starterpack.game.PlayerState;

public abstract class IActionState extends AIState{
    public abstract void Attack(PlayerState target);
}
