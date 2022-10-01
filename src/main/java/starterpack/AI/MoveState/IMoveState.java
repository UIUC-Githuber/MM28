package starterpack.AI.MoveState;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

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
        for(PlayerState player:Utils.GetDangerousPlayerState(this)){
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
        return Utils.GetPosition(this, this.getPlayerIndex());
    }
}
