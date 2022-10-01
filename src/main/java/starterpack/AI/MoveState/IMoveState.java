package starterpack.AI.MoveState;

import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import starterpack.Main;
import starterpack.AI.AIState;
import starterpack.AI.Utils.Utils;
import starterpack.game.GameState;
import starterpack.game.Item;
import starterpack.game.PlayerState;
import starterpack.game.Position;
import starterpack.util.Utility;

public abstract class IMoveState extends AIState{
    public IMoveState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }
    public abstract Position Update();
    public abstract Position Move();
    public abstract void DetectTarget();
    public Position Teleport(){   
        if(getPlayerState().getItem()==Item.NONE||getPlayerState().getGold()>=30){
            //if you don't have any item or you have too much, don't need to teleport
            return null;
        }
        else{
            int sum = 0;
            List<PlayerState> playerStateList = Utils.GetDangerousPlayerState(this);
            if(playerStateList == null||playerStateList.size()==0){
                return null;
            }
            for(PlayerState player:playerStateList){
                sum+=player.getStatSet().getDamage();
            }
            if(getPlayerState().getHealth()<=sum){
                return Utility.spawnPoints.get(getPlayerIndex());
            }
        return null;
        }   
    }
}
