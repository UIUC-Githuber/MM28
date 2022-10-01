package starterpack.AI;

import starterpack.game.GameState;
import starterpack.game.PlayerState;

interface IAIState{
    public void ChangeState(AIState state);
    public void SetAttackTarget();
    public void SetMoveTarget();
    public void Update();
}
public abstract class AIState implements IAIState{
    private GameState gameState;
    private int playerIndex;
    private PlayerState player;
    // Note: GameState and PlayerIndex will not be changeable after construct.
    public AIState(GameState gameState, int playerIndex) {
        this.gameState = gameState;
        this.playerIndex = playerIndex;
        this.player = gameState.getPlayerStateByIndex(playerIndex);
    }
    public GameState getGameState() {
        return gameState;
    }
    public int getPlayerIndex() {
        return playerIndex;
    }
    public PlayerState getPlayerState(){
        return player;
    }
    
    public void ChangeState(AIState state) {
        // TODO
    }
    public void SetAttackTarget() {
        // TODO
    }
    public void SetMoveTarget() {
        // TODO
    }
}