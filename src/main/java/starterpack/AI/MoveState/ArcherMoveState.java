package starterpack.AI.MoveState;

import starterpack.game.GameState;
import starterpack.game.Position;

public class ArcherMoveState extends IMoveState{

    public ArcherMoveState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Position Update() {
        return new Position(0,0);
        
    }

    @Override
    public Position Move() {
        return new Position(0,0);
        
    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub
        
    }


}
