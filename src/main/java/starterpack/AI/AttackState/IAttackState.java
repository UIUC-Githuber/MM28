package starterpack.AI.AttackState;

import starterpack.AI.AIState;
import starterpack.game.GameState;

public abstract class IAttackState extends AIState{
    public IAttackState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    public abstract int Update();
    public abstract int Attack();
}
