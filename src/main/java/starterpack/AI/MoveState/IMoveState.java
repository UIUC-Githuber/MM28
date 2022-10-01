package starterpack.AI.MoveState;

import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import starterpack.Main;
import starterpack.AI.AIState;
import starterpack.AI.Utils.Utils;
import starterpack.game.GameState;
import starterpack.game.PlayerState;
import starterpack.game.Position;

public abstract class IMoveState extends AIState{
    public IMoveState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }
    public abstract Position Update();
    public abstract Position Move();
    public abstract void DetectTarget();
    public Position Teleport(){   
        int sum = 0;
        List<PlayerState> playerStateList = Utils.GetDangerousPlayerState(this);
        if(playerStateList == null||playerStateList.size()==0){
            return null;
        }
        for(PlayerState player:playerStateList){
            sum+=player.getStatSet().getDamage();
        }
        if(getPlayerState().getHealth()<=sum){
            switch (getPlayerIndex()){
                case 0:
                return new Position(0,0);
                case 1:
                return new Position(9,0);
                case 2:
                return new Position(0,9);
                case 3:
                return new Position(9,9);
            }
        }
        Position res = Utils.GetPosition(this, this.getPlayerIndex());
        if(res != null) Main.LOGGER.info("TELEPORT!");
        return Utils.GetPosition(this, this.getPlayerIndex());
    }
}
