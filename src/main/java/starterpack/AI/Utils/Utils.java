package starterpack.AI.Utils;

import starterpack.AI.Utils.range.*;
import starterpack.game.GameState;
import starterpack.game.CharacterClass;
import starterpack.game.Item;
import starterpack.game.Position;
import starterpack.game.PlayerState;
import starterpack.util.*;
import starterpack.Main;

import java.util.ArrayList;
import java.util.List;

import starterpack.AI.AIState;
import starterpack.AI.CharacterAI;
import java.util.Collections;
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
    public static final List<PlayerState> ListFindFatal(AIState state) {
        //Find the enemies that we can K.O. in the range of attack. return a list of them
        int playerindex = state.getPlayerIndex();
        GameState gs = state.getGameState();
        int damage = gs.getPlayerStateByIndex(playerindex).getStatSet().getDamage();
        List<PlayerState> player = new ArrayList<PlayerState>();
        for (int i =0; i < 4; i++) {
            if (i != playerindex && DetectRange(state, i) && gs.getPlayerStateByIndex(i).getHealth() <= damage) {
                player.add( gs.getPlayerStateByIndex(i));
                
            }
        }
        if(player.isEmpty()) return null;
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
    public static final Boolean DetectRangeGameState(GameState gs, int playerindex, int otherplayerindex) {
        //check whether otherplayer is within the range
        // int playerindex = getPlayerIndex();
        // GameState gs = state.getGameState();
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
    public static final List<PlayerState> DetectRangeReturnList(AIState state) {
        //find the player within range
        List<PlayerState> list_ = new ArrayList<PlayerState>();
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
                        list_.add(player);
                    }
                } else {
                    player = gs.getPlayerStateByIndex(i);
                    list_.add(player);
                }
            }
        }
        return list_;
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
        //get player corresponding index
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
        if(DangerPlayer.isEmpty()){
            return null;  //if the list is empty, return null
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

    public static final Position GetPosition(AIState state) {
        //get position of player[index]
        GameState gs = state.getGameState();
        return gs.getPlayerStateByIndex(state.getPlayerIndex()).getPosition();
    }

    public static final Position GetPosition(AIState state, int index) {
        //get position of player[index]
        GameState gs = state.getGameState();
        return gs.getPlayerStateByIndex(index).getPosition();
    }

    //This Method may has problem
    public static final Position GetAttackPositionInRangeArcher(AIState state, int playerindex){
        Position b = GetPosition(state, playerindex); //Enemy Position
        int attackRange = state.getPlayerState().getStatSet().getRange(); //My AttackRange
        int x = b.getX();
        int y = b.getY();

        Position b_min = new Position(x-attackRange, y-attackRange);
        Position b_max = new Position(x+attackRange,  y+attackRange);
        Main.LOGGER.info("b_min: x = "+b_min.getX()+" y = "+b_min.getY());
        Main.LOGGER.info("b_max: x = "+b_max.getX()+" y = "+b_max.getY());
        List<Position> positionList = new ArrayList<Position>();
        for(int i=0;i<=2*attackRange;i++){
            //we use <= because we should also include the right corner
            positionList.add(GetTruePosition(new Position(b_max.getX()-i, b_max.getY())));
            positionList.add(GetTruePosition((new Position(b_max.getX(), b_max.getY()-i))));
        }
        for(int i=0;i<=2*attackRange;i++){
            //we use <= because we should also include the right corner
            positionList.add(GetTruePosition((new Position(b_min.getX()+i, b_min.getY()))));
            positionList.add(GetTruePosition((new Position(b_min.getX(), b_min.getY()+i))));
        }
        for(int i = 0;i<positionList.size();i++){
            Main.LOGGER.info("pos:"+i+" x = "+positionList.get(i).getX()+" y = "+positionList.get(i).getY());
        }
        /* 
        if(Utility.manhattanDistance(a, b_min)<Utility.manhattanDistance(a, b_max)){
            //it implyes that My position is closer to the left-up corner of the enemy position, we use manhattanDistance here
            for(int i=0;i<=attackRange;i++){
                //we use <= because we should also include the right corner
                positionList.add(GetTruePosition((new Position(b_min.getX()+i, b_min.getY()))));
                positionList.add(GetTruePosition((new Position(b_min.getX(), b_min.getY()+i))));
            }
        }
        else{
            //it implyes that My position is closer to the right-down corner of the enemy position
            
        }
        */
        Position resultPosition = GetPosition(state, state.getPlayerIndex()); //My Pos
        resultPosition = GetPositionByLine(state, GetNearestPosition(state, positionList), 0); 
        switch(state.getGameState().getPlayerStateByIndex(playerindex).getCharacterClass()){
            case ARCHER:
                resultPosition = GetPositionByLine(state, GetNearestPosition(state, positionList), 0); 
            case KNIGHT:
                resultPosition = GetPositionByLine(state, GetNearestPosition(state, positionList), -1); 
            break;
            case WIZARD:
                resultPosition = GetPositionByLine(state, GetNearestPosition(state, positionList), 0); 
            break;
        }
        return resultPosition;
        //this nearest position that can be reach by your character on once. 
        //Main.LOGGER.info("resultPos: x = "+GetNearestPosition(state, positionList).getX()+" y = "+GetNearestPosition(state, positionList).getY());
        //return GetNearestPosition(state, positionList);
        
    }

      //This Method may has problem
      public static final Position GetAttackPositionInRangeWizard(AIState state, int playerindex){
        Position b = GetPosition(state, playerindex); //Enemy Position
        int attackRange = state.getPlayerState().getStatSet().getRange(); //My AttackRange
        int x = b.getX();
        int y = b.getY();

        Position b_min = new Position(x-attackRange, y-attackRange);
        Position b_max = new Position(x+attackRange,  y+attackRange);
        Main.LOGGER.info("b_min: x = "+b_min.getX()+" y = "+b_min.getY());
        Main.LOGGER.info("b_max: x = "+b_max.getX()+" y = "+b_max.getY());
        List<Position> positionList = new ArrayList<Position>();
        for(int i=0;i<=2*attackRange;i++){
            //we use <= because we should also include the right corner
            positionList.add(GetTruePosition(new Position(b_max.getX()-i, b_max.getY())));
            positionList.add(GetTruePosition((new Position(b_max.getX(), b_max.getY()-i))));
        }
        for(int i=0;i<=2*attackRange;i++){
            //we use <= because we should also include the right corner
            positionList.add(GetTruePosition((new Position(b_min.getX()+i, b_min.getY()))));
            positionList.add(GetTruePosition((new Position(b_min.getX(), b_min.getY()+i))));
        }
        for(int i = 0;i<positionList.size();i++){
            Main.LOGGER.info("pos:"+i+" x = "+positionList.get(i).getX()+" y = "+positionList.get(i).getY());
        }
        /* 
        if(Utility.manhattanDistance(a, b_min)<Utility.manhattanDistance(a, b_max)){
            //it implyes that My position is closer to the left-up corner of the enemy position, we use manhattanDistance here
            for(int i=0;i<=attackRange;i++){
                //we use <= because we should also include the right corner
                positionList.add(GetTruePosition((new Position(b_min.getX()+i, b_min.getY()))));
                positionList.add(GetTruePosition((new Position(b_min.getX(), b_min.getY()+i))));
            }
        }
        else{
            //it implyes that My position is closer to the right-down corner of the enemy position
            
        }
        */
        Position resultPosition = GetPosition(state, state.getPlayerIndex()); //My Pos
        resultPosition = GetPositionByLine(state, GetNearestPosition(state, positionList), 0); 
        switch(state.getGameState().getPlayerStateByIndex(playerindex).getCharacterClass()){
            case ARCHER:
                resultPosition = GetPositionByLine(state, GetNearestPosition(state, positionList), 0); 
            case KNIGHT:
                resultPosition = GetPositionByLine(state, GetNearestPosition(state, positionList), 0); 
            break;
            case WIZARD:
                resultPosition = GetPositionByLine(state, GetNearestPosition(state, positionList), 0); 
            break;
        }
        return resultPosition;
        //this nearest position that can be reach by your character on once. 
        //Main.LOGGER.info("resultPos: x = "+GetNearestPosition(state, positionList).getX()+" y = "+GetNearestPosition(state, positionList).getY());
        //return GetNearestPosition(state, positionList);
        
    }
    
    //return the maximum range position you can achieve with a certain line that is given by your pos and the targetPos
    public static final Position GetPositionByLine(AIState state, Position targetPos, int speedAdd){
        Position myPos = GetPosition(state, state.getPlayerIndex()); //My Postion
        int ydiff = Math.abs(targetPos.getY() - myPos.getY());
        int xdiff = Math.abs(targetPos.getX() - myPos.getX());
        double slope = Math.abs(ydiff / (double) xdiff);
        int rawYMov = Math.min(ydiff, (int)Math.round(slope * (Utils.GetSpeed(state)+speedAdd)));
        int rawXMov = Math.min(xdiff, (Utils.GetSpeed(state)+speedAdd) - rawYMov);
        if(myPos.getY() > targetPos.getY()) {rawYMov *= -1;}
        if(myPos.getX() > targetPos.getX()) {rawXMov *= -1;}
        return new Position(myPos.getX()+rawXMov, myPos.getY()+rawYMov);
    }

    public static final List<RangeClass> GetEscapePath (AIState state, Position cPos, Position ePos, int eIdx) {
        final RangeClass aa = new RangeClass(RangeFlag.WALL, state.getPlayerState(), GetPosition(state).getY(), Direction.UP);
        final RangeClass bb = new RangeClass(RangeFlag.WALL, state.getPlayerState(), 9-GetPosition(state).getX(), Direction.RIGHT);
        final RangeClass cc = new RangeClass(RangeFlag.WALL, state.getPlayerState(), 9-GetPosition(state).getY(), Direction.DOWN);
        final RangeClass dd = new RangeClass(RangeFlag.WALL, state.getPlayerState(), GetPosition(state).getX(), Direction.LEFT);
        RangeClass a, b, c, d;
        if (state.getPlayerIndex() == eIdx) {
            a=aa;b=bb;c=cc;d=dd;
        }
        else {
            //Position cPos = GetPosition(state);
            //Position ePos = GetPosition(state, eIdx);
            List<Integer> info = GetEnemyInfo(eIdx, state);
            int range = info.get(1);
            int minX = Math.max(0, ePos.getX()-range);
            int maxX = Math.min(9, ePos.getX()+range);
            int minY = Math.max(0, ePos.getY()-range);
            int maxY = Math.min(9, ePos.getY()+range);
            if(!(cPos.getX() > minX && cPos.getX() < maxX && cPos.getY() > minY && cPos.getY() < maxY)) { // not dangered
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

    public static final List<RangeClass> GetEscapePath (AIState state, int eIdx) {
        return GetEscapePath(state, GetPosition(state), GetPosition(state, eIdx), eIdx);
    }

    public static final List<RangeClass> GetEscapePath(AIState state, List<List<RangeClass>> e) {
        List<RangeClass> res = GetEscapePath(state, state.getPlayerIndex());
        for(int i = 0; i < 3; ++i) { //exclude self
            List<RangeClass> lrc = e.get(i);
            for(int j = 0; j < 4; ++j)
                if(res.get(j).dist > lrc.get(j).dist)
                    res.set(j, lrc.get(j));
        }
        return res;
    }

    public static final List<RangeClass> GetEscapePath(AIState state) {
        List<List<RangeClass>> e = new ArrayList<>();
        for(int i : GetEnemiesIndex(state)) { //exclude self
            e.add(GetEscapePath(state, i));
        }
        return GetEscapePath(state, e);
    }

    public static final PlayerState maxScore(List<PlayerState> players) {
        //return player with maxscore
        int max_ = players.get(0).getScore();
        PlayerState maxplayer = players.get(0);
        for (int i = 1; i < players.size(); i++) {
            if(players.get(i).getScore() > max_) {
                max_ = players.get(i).getScore();
                maxplayer = players.get(i);
            }
        }
        return maxplayer;
    }

    public static final PlayerState maxDamage(List<PlayerState> players) {
        //return maxDamage player in a playerlist
        int max_ = players.get(0).getStatSet().getDamage();
        PlayerState maxplayer = players.get(0);
        for (int i = 1; i < players.size(); i++) {
            if(players.get(i).getStatSet().getDamage() > max_) {
                max_ = players.get(i).getStatSet().getDamage();
                maxplayer = players.get(i);
            }
        }
        return maxplayer;
    }
    public static final PlayerState minDamage(List<PlayerState> players) {
        //return player with minRange
        int min_ = players.get(0).getStatSet().getDamage();
        PlayerState minplayer = players.get(0);
        for (int i = 1; i < players.size(); i++) {
            if(players.get(i).getStatSet().getDamage() < min_) {
                min_ = players.get(i).getStatSet().getDamage();
                minplayer = players.get(i);
            }
        }
        return minplayer;
    }


    // This method may have issue
    public static final PlayerState GetNearestPlayerState(AIState state){
        PlayerState nearestChoice;  
        //Following are used to record the information of current-NearestPlayer
        List<Integer> EnemyIndex = GetEnemiesIndex(state);
        int MinDistance = 100;
        int IndexOfNearestPlayer;
        int HPofNearestPlayer = 100;
        //Initializing IndexOfNearestPlayer to something other than our own index.
        //Initialization is used here to prevent no-initialization in future.
        if(state.getPlayerIndex()!=0){
            IndexOfNearestPlayer = 0;
        }
        else{
            IndexOfNearestPlayer = 1;
        }
        
        //If there are multiple players having same Dist, HP, they will be added to this list.
        //The list is used to pass to the GetLeastDangerousEnemy() Method 
        //to find the least dangerous enemy.
        List<PlayerState> CandidatesOfLeastDangerousPlayer = new ArrayList<>();
        //If something is added to the list, we NeedToFindLeastDangerous Enemy.
        boolean NeedToFindLeastDangerous = false;

        //Go through every enemy, get their info, and compare.
        for(int i=0; i<3; i++){
            List<Integer> Info = GetEnemyInfo(EnemyIndex.get(i), state); //Current Enemy's info is stored in this list
            int CurrentDistance  = Info.get(0); //current enemy's distance
            int CurrentHP = Info.get(3);


            if(CurrentDistance<MinDistance && !NeedToFindLeastDangerous){ //if current distance is smaller than the smallest distance,
                MinDistance = CurrentDistance; //set the smallest distance to current value
                IndexOfNearestPlayer = EnemyIndex.get(i); //store the index of current enemy.
                HPofNearestPlayer = CurrentHP;          // also store the HP
            }
            //fix1: when first two players are the same but the third has smaller dist, we again set NeedToFind to false;
            else if(CurrentDistance<MinDistance && NeedToFindLeastDangerous){
                NeedToFindLeastDangerous = false;
            }
            else if(CurrentDistance == MinDistance){ //if distance are the same, compare HP
                //HaveEqualDistance = true;
                if(CurrentHP<HPofNearestPlayer && NeedToFindLeastDangerous){
                    IndexOfNearestPlayer = EnemyIndex.get(i);
                    HPofNearestPlayer = CurrentHP;
                }
                //fix1: when first two players are the same but the third has smaller HP, we again set NeedToFind to false;
                else if(CurrentHP<HPofNearestPlayer && NeedToFindLeastDangerous){
                    NeedToFindLeastDangerous = false;
                }
                else if(CurrentHP == HPofNearestPlayer){ //If HP are the same, find the least dangerous one by first
                    //adding them to candidate
                    CandidatesOfLeastDangerousPlayer.add(
                    state.getGameState().getPlayerStateByIndex(IndexOfNearestPlayer));
                    
                    CandidatesOfLeastDangerousPlayer.add(
                    state.getGameState().getPlayerStateByIndex(EnemyIndex.get(i)));

                    NeedToFindLeastDangerous = true;
                }

            }
        }

        if(NeedToFindLeastDangerous){ //find the least dangerous one if needed.
            Main.LOGGER.info("if in loop: " + NeedToFindLeastDangerous);
            return GetLeastDangerousEnemy(CandidatesOfLeastDangerousPlayer);
        }

        nearestChoice = state.getGameState().
        getPlayerStateByIndex(IndexOfNearestPlayer);

        return nearestChoice;
    }
    
    public static final int[] GetXandYRange(AIState state, int index) {
        //get position diff between you and other player
        int myx = GetPosition(state).getX();
        int myy = GetPosition(state).getY();
        int otherx = GetPosition(state, index).getX();
        int othery = GetPosition(state, index).getY();
        return new int[] {myx-otherx, myy- othery};
    }
    public static final List<PlayerState> GetEnemies(AIState state) {
        List<PlayerState> otherplayers = new ArrayList<PlayerState>();
        for (int i = 0; i < 4; i++) {
            if(i != state.getPlayerIndex()) {
                otherplayers.add(state.getGameState().getPlayerStateByIndex(i));
            }
        
        }
        return otherplayers;
    }
    public static final PlayerState minRange(List<PlayerState> players) {
        //return player with minRange
        int min_ = players.get(0).getStatSet().getRange();
        PlayerState minplayer = players.get(0);
        for (int i = 1; i < players.size(); i++) {
            if(players.get(i).getStatSet().getRange() < min_) {
                min_ = players.get(i).getStatSet().getRange();
                minplayer = players.get(i);
            }
        }
        return minplayer;
    }
    public static final PlayerState minHealth(List<PlayerState> players) {
        //return player with minRange
        int min_ = players.get(0).getHealth();
        PlayerState minplayer = players.get(0);
        for (int i = 1; i < players.size(); i++) {
            if(players.get(i).getHealth() < min_) {
                min_ = players.get(i).getHealth();
                minplayer = players.get(i);
            }
        }
        return minplayer;
    }
    public static final PlayerState maxRange(List<PlayerState> players) {
        //return player with maxscore
        int max_ = players.get(0).getStatSet().getRange();
        PlayerState maxplayer = players.get(0);
        for (int i = 1; i < players.size(); i++) {
            if(players.get(i).getStatSet().getRange() > max_) {
                max_ = players.get(i).getStatSet().getRange();
                maxplayer = players.get(i);
            }
        }
        return maxplayer;
    }

    public static final PlayerState GetLeastDangerousEnemy(List<PlayerState> otherplayers) {
        //get least dangerous
        //get least range, if equal range, get least damage,
        //if damage same, get maxscore
        List<Integer> range = new ArrayList<Integer>();
        List<Integer> damage_ = new ArrayList<Integer>();
        List<PlayerState> damagePlayers = new ArrayList<PlayerState>();
        for (int i = 0; i< otherplayers.size(); i++) {
            range.add(otherplayers.get(i).getStatSet().getRange());
        }
        int min_ = Collections.min(range);
        int count = 0;
        for(int i = 0; i< otherplayers.size() ; i++) {
            if(range.get(i) == min_)  {
                count++;
                damage_.add(otherplayers.get(i). getStatSet().getDamage());
                damagePlayers.add(otherplayers.get(i));
            }
        }
        if(count > 1) {
            int countdamage = 0;
            int minDam = Collections.min(damage_);
            for(int i = 0; i < count; i++) {
                if(damage_.get(i) == minDam)  
                    countdamage++;
            }
            if(countdamage >1) {
                return maxScore(damagePlayers);
            } else {
                return minDamage(damagePlayers);
            }
        } else  {
            return minRange(otherplayers);
        }


    }
    public static final PlayerState GetMostDangerousEnemy(List<PlayerState> otherplayers) {
        //get most dangerous enemy
        // if range greatest, if equal range, choose greatest damage
        //if equal damage, choose maxscore

        List<Integer> range = new ArrayList<Integer>();
        List<Integer> damage_ = new ArrayList<Integer>();
        List<PlayerState> damagePlayers = new ArrayList<PlayerState>();
        for (int i = 0; i<  otherplayers.size(); i++) {
            range.add(otherplayers.get(i).getStatSet().getRange());
        }
        int max_ = Collections.max(range);
        int count = 0;
        for(int i = 0; i<  otherplayers.size(); i++) {
            if(range.get(i) == max_)  {
                count++;
                damage_.add(otherplayers.get(i). getStatSet().getDamage());
                damagePlayers.add(otherplayers.get(i));
            }
        }
        if(count > 1) {
            int countdamage = 0;
            int maxDam = Collections.max(damage_);
            for(int i = 0; i < count; i++) {
                if(damage_.get(i) == maxDam)  
                    countdamage++;
            }
            if(countdamage >1) {
                return maxScore(damagePlayers);
            } else {
                return maxDamage(damagePlayers);
            }
        } else  {
            return maxRange(otherplayers);
        }


    }

    public static final Position GetNearestPosition (AIState state, List<Position> otherpositions) {
        //get nearest position out of a list of positions
        int min_ = Utility.manhattanDistance(state.getPlayerState().getPosition(), otherpositions.get(0));
        Position p  = otherpositions.get(0);
        for (int i = 1; i < otherpositions.size(); i++) {
            if (Utility.manhattanDistance(state.getPlayerState().getPosition(), otherpositions.get(i)) < min_) {
                min_ = Utility.manhattanDistance(state.getPlayerState().getPosition(), otherpositions.get(i));
                p = otherpositions.get(i);
            }
        }
        return p;
    }

    public static final Position GetTruePosition(Position pos) {
        //get true position if player could potentially go out of range
        int x= pos.getX();
        int y = pos.getY();
        Position resultPos = new Position(x,y);
        if(x<0) resultPos.setX(0);
        if(x>9) resultPos.setX(9);
        if(y<0) resultPos.setY(0);
        if(y>9) resultPos.setY(9);
        return resultPos;
    
    }
    public static final boolean IfCenterInRange(AIState state) {
        //get true position if player could potentially go out of range
        int range = state.getPlayerState().getStatSet().getRange();
        int x = state.getPlayerState().getPosition().getX();
        int y = state.getPlayerState().getPosition().getY();
        if ((x-range<=4 && x+range>=5) && (y-range<=4 && y+range>=5)  ) return true;
        return false;
    
    }
    public static final boolean IfEnemiesInCenter(AIState state) {
        List<PlayerState> list_ = GetEnemies(state);
        for(int i = 0 ; i < list_.size(); i++) {
            int x = list_.get(i).getPosition().getX();
            int y = list_.get(i).getPosition().getY();
            if((x ==4 || x == 5) && (y==4 && y==5)) return true;
        }
        return false;

    }
    public static final List<PlayerState> IfEnemiesInCenterRetureList(AIState state) {
        if(!IfEnemiesInCenter(state)) return null;
        List<PlayerState> list_ = GetEnemies(state);
        List<PlayerState> mylist_ = new ArrayList<PlayerState>();
        for(int i = 0 ; i < list_.size(); i++) {
            int x = list_.get(i).getPosition().getX();
            int y = list_.get(i).getPosition().getY();
            if((x ==4 || x == 5) && (y==4 && y==5)) mylist_.add(list_.get(i));
        }
        return mylist_;

    }

    public static final PlayerState GetNearestPlayerState2(AIState state){
        List<PlayerState> otherplayers = GetEnemies(state);
        List<Integer> range = new ArrayList<Integer>();
        List<Integer> blood = new ArrayList<Integer>();
        List<PlayerState> bloodplayers = new ArrayList<PlayerState>();
        for (int i = 0; i< otherplayers.size(); i++) {
            range.add(otherplayers.get(i).getStatSet().getRange());
        }
        int min_ = Collections.min(range);
        int count = 0;
        for(int i = 0; i< otherplayers.size() ; i++) {
            if(range.get(i) == min_)  {
                count++;
                blood.add(otherplayers.get(i).getHealth());
                bloodplayers.add(otherplayers.get(i));
            }
        }
        if(count > 1) {
            int countdamage = 0;
            int minblood = Collections.min(blood);
            for(int i = 0; i < count; i++) {
                if(blood.get(i) == minblood)  
                    countdamage++;
            }
            if(countdamage >1) {
                return minHealth(bloodplayers);
            } else {
                return minDamage(bloodplayers);
            }
        } else  {
            return minRange(otherplayers);
        }
    }

    public static final Position GetNearSpawnPosition(int playerindex){
        Position nearSpawnPoint = new Position();
        if(playerindex==0){
            nearSpawnPoint.setX(2);
            nearSpawnPoint.setY(2);
        }
        else if(playerindex==1){
            nearSpawnPoint.setX(7);
            nearSpawnPoint.setY(2);
        }
        else if(playerindex==2){
            nearSpawnPoint.setX(7);
            nearSpawnPoint.setY(7);
        }
        else{
            nearSpawnPoint.setX(2);
            nearSpawnPoint.setY(7);
        }

        return  nearSpawnPoint;

    }

    static final List<PlayerState> GetAllPlayerStateInRange(AIState state){
        List<PlayerState> PlayerInRange = new ArrayList<>();
        List<Integer> EnemiesIndex =  GetEnemiesIndex(state);
        int currentIndex;
        int currentDist;

        for(int i=0; i<3; i++){
            currentIndex = EnemiesIndex.get(i);
            List<Integer> info = GetEnemyInfo(currentIndex, state);
            currentDist = info.get(0);
            if(currentDist <= state.getPlayerState().getStatSet().getRange()){
                PlayerInRange.add(state.getGameState().getPlayerStateByIndex(currentIndex));
            }
        }
        
        return PlayerInRange;        
    }
    
}