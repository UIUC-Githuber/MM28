package starterpack.AI.MoveState;

import starterpack.game.GameState;
import starterpack.game.Position;
import starterpack.AI.Utils.*;
import starterpack.game.Item;
import starterpack.game.PlayerState;
import starterpack.util.Utility;
public class WizardMoveState extends IMoveState{

    public WizardMoveState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Position Update() {
        // return new Position(0,0);
        Position tele = Teleport();
        if (tele != null) return tele;
        return Move();
        
    }
    @Override
    public Position Teleport(){
        if(getPlayerState().getItem()==Item.NONE && getPlayerState().getGold()>=8){
            return Utility.spawnPoints.get(getPlayerIndex());
        }
        return super.Teleport();
    }

    @Override
    public Position Move() {
        Position cPos = Utils.GetPosition(this, getPlayerIndex());
        Position kbest = null;
        switch (getPlayerIndex()){
            case 0:
            kbest = new Position(4,4);
            break;
            case 1:
            kbest = new Position(5,4);
            break;
            case 2:
            kbest = new Position(4,5);
            break;
            case 3:
            kbest = new Position(5,5);
            break;
        }
        int ydiff = Math.abs(kbest.getY() - cPos.getY());
        int xdiff = Math.abs(kbest.getX() - cPos.getX());
        if(ydiff + xdiff <= Utils.GetSpeed(this)) return kbest;
        int x = cPos.getX();
        int y = cPos.getY();
        double slope = Math.abs(ydiff / (double) xdiff);
        int rawYMov = 0;
        int rawXMov = 0;
        if(Math.abs(slope)>=1) {
            rawYMov = Math.min(ydiff, (int)Math.round(ydiff / (double) (xdiff + ydiff) * Utils.GetSpeed(this)));
            rawXMov = Math.min(xdiff, Utils.GetSpeed(this) - rawYMov);
        } else {
            rawXMov = Math.min(ydiff, (int)Math.round(xdiff / (double) (xdiff + ydiff) * Utils.GetSpeed(this)));
            rawYMov = Math.min(xdiff, Utils.GetSpeed(this) - rawXMov);
        }
        
        // int rawXMov = Math.min(xdiff, Utils.GetSpeed(this) - rawYMov);
        if(cPos.getY() > kbest.getY()) rawYMov *= -1;
        if(cPos.getX() > kbest.getX()) rawXMov *= -1;
        Position idealPosChange = new Position(x + rawXMov, y + rawYMov);
        // Position idealPosChange = new Position(newx, newy);
        return idealPosChange;
        
    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub
        
    }

}
