package starterpack.AI.MoveState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import starterpack.Main;
import starterpack.AI.Utils.Utils;
import starterpack.AI.Utils.range.RangeClass;
import starterpack.game.CharacterClass;
import starterpack.game.GameState;
import starterpack.game.Item;
import starterpack.game.PlayerState;
import starterpack.game.Position;
import starterpack.util.Utility;

public class KnightMoveState extends IMoveState{

    public KnightMoveState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Position Update() {
        if(getPlayerState().getGold() >= 8 && getPlayerState().getItem() == Item.NONE && getPlayerState().getPosition().equals(Utility.spawnPoints.get(getPlayerIndex()))) {
            return Utility.spawnPoints.get(getPlayerIndex());
        }
        return Move();
    }

    @Override
    public Position Teleport() {
        if(getPlayerState().getItem()==Item.NONE && getPlayerState().getGold()>=8){
            return Utility.spawnPoints.get(getPlayerIndex());
        }
        return super.Teleport();
    }

    @Override
    public Position Move() {
        Main.LOGGER.info("\n\n\n");
        //LogManager.getLogger(Main.class.getName()).info("move");
        // TODO Auto-generated method stub
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
            kbest = new Position(5,5);
            break;
            case 3:
            kbest = new Position(4,5);
            break;
        }
        int ydiff = Math.abs(kbest.getY() - cPos.getY());
        int xdiff = Math.abs(kbest.getX() - cPos.getX());
        int rawYMov, rawXMov;
        Main.LOGGER.info("player Position = " + cPos.toString());
        Main.LOGGER.info("Diffs = (" + String.valueOf(xdiff) + "," + String.valueOf(ydiff) + ")");

        if (xdiff == 0) {
            Main.LOGGER.info("ydiff = " + String.valueOf(ydiff));
            rawYMov = Math.min(ydiff, Utils.GetSpeed(this));
            rawXMov = 0;
        }
        else{
            double slope = Math.abs(ydiff / (double) xdiff);
            Main.LOGGER.info("slope=" + String.valueOf(slope));
            Main.LOGGER.info("speed=" + Utils.GetSpeed(this));
            rawYMov = Math.min(ydiff, (int)Math.floor(slope * Utils.GetSpeed(this)));
            //Main.LOGGER.info("rawYMov=" + String.valueOf(rawYMov));
            rawXMov = Math.min(xdiff, Utils.GetSpeed(this) - rawYMov);
            //Main.LOGGER.info("rawXMov=" + String.valueOf(rawXMov));
            //Main.LOGGER.info("rawYMov=" + String.valueOf(rawYMov));
        }
        rawYMov = Math.abs(rawYMov);
        rawXMov = Math.abs(rawXMov);
        if(cPos.getY() > kbest.getY()) rawYMov = -1 * rawYMov;
        if(cPos.getX() > kbest.getX()) rawXMov = -1 * rawXMov;
        Main.LOGGER.info("rawXMov=" + String.valueOf(rawXMov));
        Main.LOGGER.info("rawYMov=" + String.valueOf(rawYMov));
        PlayerState target = Utils.GetNearestPlayerState(this);
        //Position resultPosition = Utils.GetAttackPositionInRangeKnight(this, Utils.Getplayerindex(target, getGameState()));
        //return resultPosition;
        return new Position(cPos.getX() + rawXMov, cPos.getY() + rawYMov);
        /*
        //Position idealPosChange = new Position(rawXMov, rawYMov);
        // Get Other 3 Players' Position
        Map<Integer, List<Integer>> infoSet = new HashMap<>();
        List<List<RangeClass>> rcList = new ArrayList<>();
        // Predict the next movement of other players and find the escape path
        
        for(int i : Utils.GetEnemiesIndex(this)) {
            Position ePos = Utils.GetPosition(this, i);
            List<Integer> info = Utils.GetEnemyInfo(i, this);
            infoSet.put(i, info);
            int atk = info.get(1);
            int range = info.get(2);
            // get the slope
            int eYdiff = ePos.getY() - cPos.getY();
            int eXdiff = ePos.getX() - cPos.getX();
            double eSlope = Math.abs(eYdiff / (double) (eXdiff));
            int eRawYMov = Math.max(eYdiff, (int)Math.round(eSlope * Utils.GetSpeed(this, i)));
            int eRawXMov = Math.max(eXdiff, Utils.GetSpeed(this, i) - eRawYMov);
            if (ePos.getY() > cPos.getY()) eRawYMov *= -1;
            if (ePos.getX() > cPos.getX()) eRawXMov *= -1;
            Position predicted = new Position(ePos.getX() + eRawXMov, ePos.getY() + eRawYMov);
            List<RangeClass> ep = Utils.GetEscapePath(this, Utils.GetPosition(this), predicted, i);
            rcList.add(ep);
        }
        List<RangeClass> rcfinal = Utils.GetEscapePath(this);
        //List<RangeClass> rcfinal = Utils.GetEscapePath(this, rcList);
        
        //Main.LOGGER.info(rcfinal.get(0).dist);
        //Main.LOGGER.info(rcfinal.get(1).dist);
        //Main.LOGGER.info(rcfinal.get(2).dist);
        //Main.LOGGER.info(rcfinal.get(3).dist);
        // Unstable Move: (Prob 0.4) Ignore the dangers
        //if (Math.random() * 10 <= 20) {
        //    //Main.LOGGER.info("idealPos : " + idealPosChange.toString());
        //    Main.LOGGER.info("cPosX: " + String.valueOf(cPos.getX()) + "rxm: " + String.valueOf(rawXMov) + " udt: " + String.valueOf(cPos.getX() + rawXMov));
        //    return new Position(cPos.getX() + rawXMov, cPos.getY() + rawYMov);
        //}
        // Stable Move: Follow the boarder first, then the target
        //Main.LOGGER.info("HERE STABLE MOVE");
        String msg;
        if (rawXMov > 0) { // Compare b
            msg = "Compare b: " + String.valueOf(rawXMov) + " and ";
            if(rcfinal.get(1).dist + rcfinal.get(3).dist <= 0) {
                msg += String.valueOf(-rcfinal.get(3).dist);
                if(getPlayerState().getHealth() / 2.0 <= rcfinal.get(3).ps.getStatSet().getDamage())
                    rawXMov = Math.min(rawXMov, -rcfinal.get(3).dist);
            }
            else {
                msg += String.valueOf(rcfinal.get(1).dist);
                if(getPlayerState().getHealth() / 2.0 <= rcfinal.get(1).ps.getStatSet().getDamage())
                    rawXMov = Math.min(rawXMov, rcfinal.get(1).dist);
            }
        }
        else { // Compare d
            msg = "Compare d: " + String.valueOf(rawXMov) + " and ";
            if(rcfinal.get(1).dist + rcfinal.get(3).dist <= 0) {
                msg += String.valueOf(rcfinal.get(1).dist);
                if(getPlayerState().getHealth() / 2.0 <= rcfinal.get(1).ps.getStatSet().getDamage())
                    rawXMov = Math.max(rawXMov, rcfinal.get(1).dist);
            }
            else {
                msg += String.valueOf(-rcfinal.get(3).dist);
                if(getPlayerState().getHealth() / 2.0 <= rcfinal.get(3).ps.getStatSet().getDamage())
                    rawXMov = Math.max(rawXMov, -rcfinal.get(3).dist);
            }
        }

        if (rawYMov > 0) { // Compare c
            msg += " ; Compare c: " + String.valueOf(rawYMov) + " and ";
            if(rcfinal.get(0).dist + rcfinal.get(2).dist <= 0) {
                msg += String.valueOf(-rcfinal.get(0).dist);
                if(getPlayerState().getHealth() / 2.0 <= rcfinal.get(0).ps.getStatSet().getDamage())
                    rawYMov = Math.min(rawYMov, -rcfinal.get(0).dist);
            }
            else {
                msg += String.valueOf(rcfinal.get(2).dist);
                if(getPlayerState().getHealth() / 2.0 <= rcfinal.get(2).ps.getStatSet().getDamage())
                    rawYMov = Math.min(rawYMov, rcfinal.get(2).dist);
            }
        }
        else { // Compare a
            msg += " ; Compare a: " + String.valueOf(rawYMov) + " and ";
            if(rcfinal.get(1).dist + rcfinal.get(3).dist <= 0) {
                msg += String.valueOf(rcfinal.get(2).dist);
                if(getPlayerState().getHealth() / 2.0 <= rcfinal.get(2).ps.getStatSet().getDamage())
                    rawYMov = Math.max(rawYMov, rcfinal.get(2).dist);
            }
            else {
                msg += String.valueOf(-rcfinal.get(0).dist);
                if(getPlayerState().getHealth() / 2.0 <= rcfinal.get(0).ps.getStatSet().getDamage())
                    rawYMov = Math.max(rawYMov, -rcfinal.get(0).dist);
            }
        }
        
        Main.LOGGER.info("x, y = " + String.valueOf(rawXMov) + " " + String.valueOf(rawYMov));

        return new Position(cPos.getX() + rawXMov, cPos.getY() + rawYMov);
        */
    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub
        
    }


}
