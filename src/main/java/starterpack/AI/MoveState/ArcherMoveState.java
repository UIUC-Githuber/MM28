package starterpack.AI.MoveState;

import java.lang.System.Logger;
import java.util.List;

import starterpack.Main;
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
        return NaiveMove();
        
    }
    public Position NaiveMove(){
        PlayerState target = Utils.GetNearestPlayerState(this);
        Main.LOGGER.info("playerid = "+ Utils.Getplayerindex(target, getGameState()));
        Position targePosition = Utils.GetPosition(this, Utils.Getplayerindex(target, getGameState()));
        Main.LOGGER.info("moveTo: x = "+targePosition.getX()+" y = "+targePosition.getY());
        Position result = Utils.GetAttackPositionInRange(this, Utils.Getplayerindex(target, getGameState()));
        return result;
    }
    @Override
    public Position Move() {
        PlayerState target;
        List<PlayerState> playerStateList = Utils.GetDangerousPlayerState(this);
        if(playerStateList == null||playerStateList.size()==0){
            //if you are safe now, search for a hunter target
            target = Utils.GetNearestPlayerState(this);
            Main.LOGGER.info("Utils.GetDangerousPlayerState: "+target);
        }
        else{
            for(int i=0;i<playerStateList.size();i++){
                Main.LOGGER.info("Utils.GetDangerousPlayerState: "+playerStateList.get(i));
            }
            target = Utils.GetMostDangerousEnemy(playerStateList);
            Main.LOGGER.info("Utils.GetMostDangerousEnemy: "+target);
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
        //Position result = Utils.Getplayerindex(target, getGameState());
        Position result = Utils.GetAttackPositionInRange(this, Utils.Getplayerindex(target, getGameState()));
        Main.LOGGER.info("moveTo: x = "+result.getX()+" y = "+result.getY());
        return result;
    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub
        
    }


}
