package starterpack.AI.MoveState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void Update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Position Move() {
        // TODO Auto-generated method stub
        Position cPos = Utils.GetPosition(this);
        int ydiff = 4 - cPos.getY();
        int xdiff = 4 - cPos.getX();
        double slope = Math.abs(ydiff / (double) xdiff);
        int rawYMov = Math.max(ydiff, (int)Math.round(slope * Utils.GetSpeed(this)));
        int rawXMov = Math.max(xdiff, Utils.GetSpeed(this) - rawYMov);
        if(cPos.getY() > 4) rawYMov *= -1;
        if(cPos.getX() > 4) rawXMov *= -1;
        return new Position(cPos.getX() + rawXMov, cPos.getY() + rawYMov);
        // Get Other 3 Players' Position
        /*
        List<Integer> safePlayerSet = new ArrayList<>();
        Map<Integer, List<Integer>> infoSet = new HashMap<>();
        Position posMaxMove = new Position(0,0);
        for(int i : Utils.GetEnemiesIndex(this)) {
            Position ePos = Utils.GetPosition(i);
            List<Integer> info = Utils.GetEnemyInfo(i, this);
            infoSet.put(i, info);
            int atk = info.get(1);
            int range = info.get(2);
            // get the slope
            int ydiff = ePos.getY() - cPos.getY();
            int xdiff = ePos.getX() - cPos.getX();
            double slope = Math.abs(ydiff / (double) (xdiff));
            int rawYMov = Math.max(ydiff, (int)Math.round(slope * Utils.GetSpeed(i)));
            int rawXMov = Math.max(xdiff, Utils.GetSpeed(i) - enemyMove.getY());
            if (ePos.getY() > cPos.getY()) rawYMov *= -1;
            if (ePos.getX() > cPos.getX()) rawXMov *= -1;
            Position predicted = new Position(ePos.getX() + rawXMov, ePos.getY() + rawYMov);
            //move towards enemy i is a safe action
            if (Utility.chebyshevDistance(cPos, predicted) > range) {
                safePlayerSet.add(i);
            }
            else {

            }
        }
        if(!safePlayerSet.isEmpty()) {

        }
        */
    }

    @Override
    public void DetectTarget() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void Teleport() {
        // TODO Auto-generated method stub
        
    }
}
