package starterpack.AI.MoveState;

import java.lang.System.Logger;
import java.util.List;

import starterpack.Main;
import starterpack.AI.Utils.Utils;
import starterpack.AI.Utils.range.Direction;
import starterpack.AI.Utils.range.RangeClass;
import starterpack.game.CharacterClass;
import starterpack.game.GameState;
import starterpack.game.Item;
import starterpack.game.PlayerState;
import starterpack.game.Position;
import starterpack.util.Utility;

public class HunterWizardMoveState extends IMoveState {

    public HunterWizardMoveState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Position Update() {
        Position tele = this.Teleport();
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
        PlayerState target = Utils.GetNearestPlayerState(this);
        Main.LOGGER.info("playerid = " + Utils.Getplayerindex(target, getGameState()));
        Position targePosition = Utils.GetPosition(this, Utils.Getplayerindex(target, getGameState()));
        Main.LOGGER.info("moveTo: x = " + targePosition.getX() + " y = " + targePosition.getY());
        Position result = Utils.GetAttackPositionInRangeWizard(this, Utils.Getplayerindex(target, getGameState()));
        return result;
    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub

    }

}
