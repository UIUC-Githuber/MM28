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
import starterpack.util.Utility;

public class ArcherMoveState extends IMoveState {

    public ArcherMoveState(GameState gameState, int playerIndex) {
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
        if(getPlayerState().getGold()>=8){
            return Utility.spawnPoints.get(getPlayerIndex());
        }
        return super.Teleport();

    }

    @Override
    public Position Move() {
        Position myPosition = Utils.GetPosition(this);
        Position resultPosition = myPosition;
        List<PlayerState> dangerousPlayerStateList = Utils.GetDangerousPlayerState(this);
        if (dangerousPlayerStateList == null || dangerousPlayerStateList.size() == 0) {
            //it shows that Archer is safe now and can actively find target to kill
            PlayerState target = Utils.GetNearestPlayerState(this);
            resultPosition = Utils.GetAttackPositionInRange(this, Utils.Getplayerindex(target, getGameState()));
        } else {
            //it shows that Archer is in danger now and need to be away from the enemies
            List<RangeClass> rangeClassList = Utils.GetEscapePath(this);
            int max = Integer.MIN_VALUE;
            RangeClass resultRangeClass = rangeClassList.get(0);
            for (RangeClass rangeClass : rangeClassList) {
                if (rangeClass.dist > max) {
                    max = rangeClass.dist;
                    resultRangeClass = rangeClass;
                }
            }
            int speed = getPlayerState().getStatSet().getSpeed();
            int distance = resultRangeClass.dist >= 0 ? resultRangeClass.dist + 1 : resultRangeClass.dist - 1;
            // IF It's postive, add 1, if it's negative minus one. So its abs value must
            // increase 1.
            switch (resultRangeClass.dir) {
                case UP:
                    resultPosition = new Position(myPosition.getX(),
                            Math.min(myPosition.getY() - speed, myPosition.getY() - distance));
                    break;
                case RIGHT:
                    resultPosition = new Position(Math.min(myPosition.getX() + speed, myPosition.getX() + distance),
                            myPosition.getX());
                    break;
                case DOWN:
                    resultPosition = new Position(myPosition.getX(),
                            Math.min(myPosition.getY() + speed, myPosition.getY() + distance));
                    break;
                case LEFT:
                    resultPosition = new Position(Math.min(myPosition.getX() - speed, myPosition.getX() - distance),
                            myPosition.getX());
                    break;
            }
            // List<PlayerState> playerStateList = Utils.GetDangerousPlayerState(this);
        }

        return resultPosition;
    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub

    }

}
