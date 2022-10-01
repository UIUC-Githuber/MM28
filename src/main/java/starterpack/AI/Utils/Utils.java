package starterpack.AI.Utils;

import starterpack.AI.Utils.range.*;
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

    public static final Position GetPosition(AIState state) {
        return GetPosition(state, state.getPlayerIndex());
    }

    public static final List<RangeClass> GetEscapePath (AIState state, int eIdx) {
        final RangeClass aa = new RangeClass(RangeFlag.WALL, state.getPlayerState(), GetPosition(state).getY(), Direction.UP);
        final RangeClass bb = new RangeClass(RangeFlag.WALL, state.getPlayerState(), 9-GetPosition(state).getX(), Direction.RIGHT);
        final RangeClass cc = new RangeClass(RangeFlag.WALL, state.getPlayerState(), 9-GetPosition(state).getY(), Direction.DOWN);
        final RangeClass dd = new RangeClass(RangeFlag.WALL, state.getPlayerState(), GetPosition(state).getX(), Direction.LEFT);
        RangeClass a, b, c, d;
        if (state.getPlayerIndex() == eIdx) {
            a=aa;b=bb;c=cc;d=dd;
        }
        else {
            Position cPos = GetPosition(state);
            Position ePos = GetPosition(state, eIdx);
            List<Integer> info = GetEnemyInfo(eIdx, state);
            int range = info.get(1);
            int minX = Math.max(0, ePos.getX()-range);
            int maxX = Math.min(9, ePos.getX()+range);
            int minY = Math.max(0, ePos.getY()-range);
            int maxY = Math.min(9, ePos.getY()+range);
            if(!(cPos.getX() <= maxX && cPos.getX() >= minX && cPos.getY() <= maxY && cPos.getY() >= minY)) { // not dangered
                if((cPos.getX() < minX || cPos.getX() > maxX) && cPos.getY() < minY) {
                    a = aa; b = bb; c = cc; d = dd;
                }
                else if(cPos.getX() >= minX && cPos.getX() <= maxX && cPos.getY() < minY) {
                    a = aa; b = bb; d = dd;
                    c = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), minY - cPos.getY(), Direction.DOWN);
                }
                else if(cPos.getX() < minX && cPos.getY() >= minY && cPos.getY() <= maxY) {
                    a = aa; c = cc; d = dd;
                    b = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), minX - cPos.getX(), Direction.RIGHT);
                }
                else if(cPos.getX() == minX && cPos.getY() >= minY && cPos.getY() <= maxY) {
                    a = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), 0, Direction.UP);
                    b = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), 0, Direction.RIGHT);
                    c = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), 0, Direction.DOWN);
                    d = dd;
                }
                else if(cPos.getX() > minX && cPos.getX() < maxX && cPos.getY() == minY) {
                    a = aa;
                    b = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), 0, Direction.RIGHT);
                    c = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), 0, Direction.DOWN);
                    d = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), 0, Direction.LEFT);
                }
                else if(cPos.getX() > minX && cPos.getX() < maxX && cPos.getY() == maxY) {
                    a = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), 0, Direction.UP);
                    b = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), 0, Direction.RIGHT);
                    c = cc;
                    d = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), 0, Direction.LEFT);
                }
                else if(cPos.getX() == maxX && cPos.getY() >= minY && cPos.getY() <= maxY) {
                    a = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), 0, Direction.UP);
                    b = bb;
                    c = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), 0, Direction.DOWN);
                    d = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), 0, Direction.LEFT);
                }
                else if(cPos.getX() > maxX && cPos.getY() >= minY && cPos.getY() <= maxY) {
                    a = aa; c = cc; b = bb;
                    d = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), cPos.getX() - maxX, Direction.RIGHT);
                }
                else if((cPos.getX() < minX || cPos.getX() > maxX) && cPos.getY() > maxY) {
                    a = aa; b = bb; c = cc; d = dd;
                }
                else {
                    b = bb; c = cc; d = dd;
                    a = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), cPos.getY() - maxY, Direction.UP);
                }
            }
            else {
                a = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), cPos.getY()-maxY, Direction.UP);
                c = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), minY-cPos.getY(), Direction.DOWN);
                b = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), minX-cPos.getX(), Direction.RIGHT);
                d = new RangeClass(RangeFlag.PLAYER, state.getGameState().getPlayerStateByIndex(eIdx), cPos.getX()-maxX, Direction.LEFT);
            }
        }
        List<RangeClass> paths = new ArrayList<>();
        paths.add(a);paths.add(b);paths.add(c);paths.add(d);
        return paths;
    }

    public static final List<RangeClass> GetEscapePath(AIState state) {
        List<RangeClass> res = GetEscapePath(state, state.getPlayerIndex());
        for(int i : GetEnemiesIndex(state)) {
            List<RangeClass> lrc = GetEscapePath(state, i);
            for(int j = 0; j < 4; ++j)
                if(res.get(j).dist > lrc.get(j).dist)
                    res.set(j, lrc.get(j));
        }
        return res;
    }

}

