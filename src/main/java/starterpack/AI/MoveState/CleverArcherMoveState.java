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

public class CleverArcherMoveState extends IMoveState {

    public CleverArcherMoveState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Position Update() {
        return Move();
    }

    @Override
    public Position Move() {
        Position myPosition = Utils.GetPosition(this);
        if (myPosition.equals(Utility.spawnPoints.get(getPlayerIndex()))) {
            // if you are in the spawnPoint
            if (getPlayerState().getItem() == Item.NONE && getPlayerState().getGold() >= 8) {
                return myPosition;
            } else {
                return Utils.GetNearSpawnPosition(getPlayerIndex());
            }
        } else {
            Position kbest = null;
            Position cPos = Utils.GetPosition(this, getPlayerIndex());
            switch (getPlayerIndex()) {
                case 0:
                    kbest = new Position(4, 4);
                    break;
                case 1:
                    kbest = new Position(5, 4);
                    break;
                case 2:
                    kbest = new Position(5, 5);
                    break;
                case 3:
                    kbest = new Position(4, 5);
                    break;
            }
            int ydiff = Math.abs(kbest.getY() - cPos.getY());
            int xdiff = Math.abs(kbest.getX() - cPos.getX());
            if (ydiff + xdiff <= Utils.GetSpeed(this))
                return kbest;
            int x = cPos.getX();
            int y = cPos.getY();
            double slope = Math.abs(ydiff / (double) xdiff);
            int rawYMov = 0;
            int rawXMov = 0;
            if (Math.abs(slope) >= 1) {
                rawYMov = Math.min(ydiff, (int) Math.round(ydiff / (double) (xdiff + ydiff) * Utils.GetSpeed(this)));
                rawXMov = Math.min(xdiff, Utils.GetSpeed(this) - rawYMov);
            } else {
                rawXMov = Math.min(ydiff, (int) Math.round(xdiff / (double) (xdiff + ydiff) * Utils.GetSpeed(this)));
                rawYMov = Math.min(xdiff, Utils.GetSpeed(this) - rawXMov);
            }

            // int rawXMov = Math.min(xdiff, Utils.GetSpeed(this) - rawYMov);
            if (cPos.getY() > kbest.getY())
                rawYMov *= -1;
            if (cPos.getX() > kbest.getX())
                rawXMov *= -1;
            Position idealPosChange = new Position(x + rawXMov, y + rawYMov);
            // Position idealPosChange = new Position(newx, newy);
            return idealPosChange;
        }

    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub

    }
}
