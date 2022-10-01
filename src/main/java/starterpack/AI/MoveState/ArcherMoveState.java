package starterpack.AI.MoveState;

import java.util.List;

import starterpack.AI.Utils.Utils;
import starterpack.game.GameState;
import starterpack.game.PlayerState;
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
        List<PlayerState> playerStateList = Utils.GetDangerousPlayerState(this);
        if(playerStateList == null||playerStateList.size()==0){

        }
        for(PlayerState playerState:playerStateList){

        }
        return new Position(0,0);
    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub
        
    }


}
