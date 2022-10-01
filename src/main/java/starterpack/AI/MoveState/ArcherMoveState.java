package starterpack.AI.MoveState;

import java.util.List;

import starterpack.AI.Utils.Utils;
import starterpack.game.CharacterClass;
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
        PlayerState target;
        List<PlayerState> playerStateList = Utils.GetDangerousPlayerState(this);
        if(playerStateList == null||playerStateList.size()==0){
            target = Utils.GetNearestPlayerState(this);
        }
        else{
           
            target = Utils.GetMostDangerousEnemy(playerStateList);
                //int rangeArray[] = new int[4];
                //rangeArray = Util.GetRangeBox();
                
            switch(target.getCharacterClass()){
                    //if Archer, then move to the places where
                    case ARCHER:
                        break;
                    case KNIGHT:
                        
                        break;
                    case WIZARD:
                        break;
                    default:
                        break;
            }
            
        }

        return Utils.GetAttackPositionInRange(this, Utils.Getplayerindex(target, getGameState()) );
    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub
        
    }


}
