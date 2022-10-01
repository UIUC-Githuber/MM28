package starterpack.AI.Utils;

import starterpack.game.GameState;
import starterpack.game.CharacterClass;
import starterpack.game.Item;
import starterpack.game.Position;
import starterpack.game.PlayerState;
import starterpack.util.*;

import java.util.ArrayList;
import java.util.List;

import starterpack.AI.AIState;
import starterpack.AI.CharacterAI;

public final class Utils {
    // 
    public static final PlayerState MinHealthPlayer(AIState state) {
        //Attack Characters with minimum hp within the range of attack.
        int minPH;
        int index;
        int playerindex = state.getPlayerIndex();
        GameState gs = state.getGameState();
        if(playerindex==0) {
            minPH = gs.getPlayerStateByIndex(1).getHealth();
            index = 1;
        } else {
            minPH = gs.getPlayerStateByIndex(0).getHealth();
            index = 0;
        }
        
        for (int i =0; i < 4; i++) {
            if (i != playerindex && i!= index && gs.getPlayerStateByIndex(i).getHealth() < minPH) {
                minPH = gs.getPlayerStateByIndex(i).getHealth();
                index = i;
            }
        }
        return gs.getPlayerStateByIndex(index);
    }
    public static final Boolean IfExistFatal(AIState state, int otherplayerindex) {
        //Check whether there are enemies that we can K.O. in the range of attack.
        int playerindex = state.getPlayerIndex();
        GameState gs = state.getGameState();
        int damage = gs.getPlayerStateByIndex(playerindex).getStatSet().getDamage();
        
        if (gs.getPlayerStateByIndex(otherplayerindex).getHealth() <= damage) {
            return true;
        }
        
        return false;
    }
    public static final PlayerState FindFatal(AIState state) {
        //Find the enemies that we can K.O. in the range of attack.
        int playerindex = state.getPlayerIndex();
        GameState gs = state.getGameState();
        int damage = gs.getPlayerStateByIndex(playerindex).getStatSet().getDamage();
        PlayerState player = null;
        for (int i =0; i < 4; i++) {
            if (i != playerindex && DetectRange(state, i) && gs.getPlayerStateByIndex(i).getHealth() <= damage) {
                if(player != null) {
                    if(player.getScore() < gs.getPlayerStateByIndex(i).getScore()) {
                        player= gs.getPlayerStateByIndex(i);
                    }
                } else {
                    player = gs.getPlayerStateByIndex(i);
                }
                
            }
        }
        return player;

    }


    public static final List<Integer> GetEnemyInfo(int i, AIState state){
        //Collect the data between enemies[i] and ours.
        List<Integer> EnemyInfo = new ArrayList<>();
        int OurIndex = state.getPlayerIndex();       //find our player Index
        int DistanceFromEnemy    = Utility.chebyshevDistance(
                                   state.getGameState().getPlayerStateByIndex(i).getPosition(),
                                   state.getGameState().getPlayerStateByIndex(OurIndex).getPosition()),
            
            EnemyAttackRange     = state.getGameState().getPlayerStateByIndex(i).getStatSet().getRange(), 
            
            Enemy_Damage         = state.getGameState().getPlayerStateByIndex(i).getStatSet().getDamage(), 

            EnemyHP              = state.getGameState().getPlayerStateByIndex(i).getHealth(),
            
            EnemyGold            = state.getGameState().getPlayerStateByIndex(i).getGold(),
            
            EnemyScore           = state.getGameState().getPlayerStateByIndex(i).getScore();
        EnemyInfo.add(DistanceFromEnemy);
        EnemyInfo.add(Enemy_Damage);
        EnemyInfo.add(EnemyAttackRange);
        EnemyInfo.add(EnemyHP);
        EnemyInfo.add(EnemyGold);
        EnemyInfo.add(EnemyScore);

        return  EnemyInfo;

    }
    public static final Boolean DetectRange(AIState state, int otherplayerindex) {
        //check whether otherplayer is within the range
        int playerindex = state.getPlayerIndex();
        GameState gs = state.getGameState();
        int range = gs.getPlayerStateByIndex(playerindex).getStatSet().getRange();
        int x= gs.getPlayerStateByIndex(playerindex).getPosition().getX();
        int y = gs.getPlayerStateByIndex(playerindex).getPosition().getY();

        int otherx= gs.getPlayerStateByIndex(otherplayerindex).getPosition().getX();
        int othery = gs.getPlayerStateByIndex(otherplayerindex).getPosition().getY();
        
        if (otherx >= x-range && otherx <= x+ range && othery >= y-range && othery <= y + range) {
            return true;
        }
        
        return false;
    }
    public static final PlayerState DetectRange(AIState state) {
        //find the player within range
        int playerindex = state.getPlayerIndex();
        GameState gs = state.getGameState();
        int range = gs.getPlayerStateByIndex(playerindex).getStatSet().getRange();
        int x= gs.getPlayerStateByIndex(playerindex).getPosition().getX();
        int y = gs.getPlayerStateByIndex(playerindex).getPosition().getY();
        PlayerState player = null;
        for (int i =0; i < 4; i++) {
            int otherx = gs.getPlayerStateByIndex(i).getPosition().getX();
            int othery = gs.getPlayerStateByIndex(i).getPosition().getY();
            if (i != playerindex && (otherx >= x-range && otherx <= x+ range && othery >= y-range && othery <= y + range)) {
                if(player != null) {
                    if(player.getStatSet().getDamage() < gs.getPlayerStateByIndex(i).getStatSet().getDamage()) {
                        player= gs.getPlayerStateByIndex(i);
                    }
                } else {
                    player = gs.getPlayerStateByIndex(i);
                }
            }
        }
        return player;
    }

    public static final List<Integer> GetEnemiesIndex (AIState state) {
        //Return the index list of people with index != our index
        int index = state.getPlayerIndex();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            if(i != index) {
                list.add(i);
            }
        }
        return list;

    }
    public static final int Getplayerindex (PlayerState player, GameState gs) {
        int index =-1;
        for (int i =0; i < 4; i++) {
            if(player == (gs.getPlayerStateByIndex(i))) index = i;
        }
        return index;
    }

    public static final List<PlayerState> GetDangerousPlayerState(AIState state){
        List<PlayerState> DangerPlayer = new ArrayList<>();
        
        //Enemy's attack distance >= Distance between me and enemy = Add enemy to list
        List<Integer> EnemyIndex = GetEnemiesIndex(state);
        
        
        for(int i=0; i<3; i++){
            //get attack range of the enemy with index x = EnemyIndex.get(i);
            int Range = state.getGameState().getPlayerStateByIndex(EnemyIndex.get(i)).getStatSet().getRange();
            List<Integer> Info = GetEnemyInfo(EnemyIndex.get(i), state);
            int Dist  = Info.get(0);       // distance between two players
            if(Range>=Dist){
                DangerPlayer.add(state.getGameState().getPlayerStateByIndex(EnemyIndex.get(i)));
            }
        }

        return DangerPlayer;
    }

    //Get speed of player with index i
    public static final int GetSpeed(AIState state, int i){
        int SpeedOfPlayer = state.getGameState().getPlayerStateByIndex(i).getStatSet().getSpeed();
        return SpeedOfPlayer;
    }

    //Get speed of current player
    public static final int GetSpeed(AIState state){
        int SpeedOfPlayer = state.getGameState().getPlayerStateByIndex(state.getPlayerIndex()).getStatSet().getSpeed();
        return SpeedOfPlayer;
    }

    public static final Position GetPosition(AIState state, int index) {
        //get position of player[index]
        GameState gs = state.getGameState();
        return gs.getPlayerStateByIndex(index).getPosition();
    }

    //This Method may has problem
    public static final Position GetAttackPositionInRange(AIState state, int playerindex){
        GameState gs = state.getGameState();
        Position a = GetPosition(state, state.getPlayerIndex()); //My Postion
        Position b = GetPosition(state, playerindex); //Enemy Position
        int attackRange = state.getPlayerState().getStatSet().getRange(); //My AttackRange
        int x = b.getX();
        int y = b.getY();
        int index = 0;
        Position b_min = new Position(x-attackRange, y-attackRange);
        Position b_max = new Position(x+attackRange, y+attackRange);
        List<Position> postionList = new ArrayList<Position>();
        if(Utility.manhattanDistance(a, b_min)<Utility.manhattanDistance(a, b_max)){
            //it implyes that My position is closer to the left-up corner of the enemy position, we use manhattanDistance here
            for(int i=0;i<=attackRange;i++){
                //we use <= because we should also include the right corner
                postionList.add(GetTruePosition((new Position(b_min.getX()+i, b_min.getY()))));
                postionList.add(GetTruePosition((new Position(b_min.getX(), b_min.getY()+i))));
            }
        }
        else{
            //it implyes that My position is closer to the right-down corner of the enemy position
            for(int i=0;i<=attackRange;i++){
                //we use <= because we should also include the right corner
                postionList.add(GetTruePosition(new Position(b_max.getX()-i, b_max.getY())));
                postionList.add(GetTruePosition((new Position(b_max.getX(), b_max.getY()-i))));
            }
        }
        return GetPositionByLine(state, GetNearestPosition(positionList)); //this nearest position that can be reach by your character on once. 
    }

    //return the maximum range position you can achieve with a certain line that is given by your pos and the targetPos
    public static final Position GetPositionByLine(AIState state, Position targetPos){
        Position myPos = GetPosition(state, state.getPlayerIndex()); //My Postion
        int ydiff = Math.abs(targetPos.getY() - myPos.getY());
        int xdiff = Math.abs(targetPos.getX() - myPos.getX());
        double slope = Math.abs(ydiff / (double) xdiff);
        int rawYMov = Math.min(ydiff, (int)Math.round(slope * Utils.GetSpeed(state)));
        int rawXMov = Math.min(xdiff, Utils.GetSpeed(state) - rawYMov);
        if(myPos.getY() > targetPos.getY()) {rawYMov *= -1;}
        if(myPos.getX() > targetPos.getX()) {rawXMov *= -1;}
        return new Position(myPos.getX()+rawXMov, myPos.getY()+rawYMov);
    }
    
}

