package starterpack.AI.MoveState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import starterpack.Main;
import starterpack.AI.Utils.Utils;
import starterpack.game.GameState;
import starterpack.game.Position;
import starterpack.util.Utility;

public class KnightMoveState extends IMoveState{

    public KnightMoveState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Position Update() {
        Position tele = Teleport();
        if (tele != null) return tele;
        return Move();
        
    }

    @Override
    public Position Move() {
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
            kbest = new Position(4,5);
            break;
            case 3:
            kbest = new Position(5,5);
            break;
        }
        int ydiff = Math.abs(kbest.getY() - cPos.getY());
        int xdiff = Math.abs(kbest.getX() - cPos.getX());
        double slope = Math.abs(ydiff / (double) xdiff);
        int rawYMov = Math.min(ydiff, (int)Math.round(slope * Utils.GetSpeed(this)));
        int rawXMov = Math.min(xdiff, Utils.GetSpeed(this) - rawYMov);
        if(cPos.getY() > kbest.getY()) rawYMov *= -1;
        if(cPos.getX() > kbest.getX()) rawXMov *= -1;
        Position idealPosChange = new Position(rawXMov, rawYMov);
        // Get Other 3 Players' Position
        List<Integer> safePlayerSet = new ArrayList<>();
        Map<Integer, List<Integer>> infoSet = new HashMap<>();
        Position posMaxMove = new Position(0,0);
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
            int eRawYMov = Math.max(eYdiff, (int)Math.round(slope * Utils.GetSpeed(this, i)));
            int eRawXMov = Math.max(eXdiff, Utils.GetSpeed(this, i) - eRawYMov);
            if (ePos.getY() > cPos.getY()) rawYMov *= -1;
            if (ePos.getX() > cPos.getX()) rawXMov *= -1;
            Position predicted = new Position(ePos.getX() + rawXMov, ePos.getY() + rawYMov);
            Utils.GetEscapePath(this, Utils.GetPosition(this), predicted, i);
        }
        if(!safePlayerSet.isEmpty()) {

        }
        return new Position(0,0); //TODO
    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub
        
    }


}
