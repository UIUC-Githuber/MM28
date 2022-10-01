package starterpack.AI.AttackState;

import starterpack.AI.AIState;
import starterpack.game.GameState;

public abstract class IAttackState extends AIState{
    public abstract void Attack(GameState gameState);
}
