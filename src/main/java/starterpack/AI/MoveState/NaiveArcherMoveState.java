package starterpack.AI.MoveState;

import java.lang.System.Logger;
import java.util.List;

import starterpack.Main;
import starterpack.AI.Utils.Utils;
import starterpack.AI.Utils.range.Direction;
import starterpack.AI.Utils.range.RangeClass;
import starterpack.game.CharacterClass;
import starterpack.game.GameState;
import starterpack.game.PlayerState;
import starterpack.game.Position;

public class NaiveArcherMoveState extends IMoveState {

    public NaiveArcherMoveState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Position Update() {
        return Move();

    }

    @Override
    public Position Move() {
        PlayerState target = Utils.GetNearestPlayerState(this);
        Main.LOGGER.info("playerid = " + Utils.Getplayerindex(target, getGameState()));
        Position targePosition = Utils.GetPosition(this, Utils.Getplayerindex(target, getGameState()));
        Main.LOGGER.info("moveTo: x = " + targePosition.getX() + " y = " + targePosition.getY());
        Position result = Utils.GetAttackPositionInRange(this, Utils.Getplayerindex(target, getGameState()));
        return result;
    }


    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub

    }

}
