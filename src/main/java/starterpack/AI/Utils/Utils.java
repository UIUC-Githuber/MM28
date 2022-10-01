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

    public static final PlayerState maxScore(List<PlayerState> players) {
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

    // This method may have issue
    public static final PlayerState GetNeareastPlayerState(AIState state){
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


            if(CurrentDistance<MinDistance){ //if current distance is smaller than the smallest distance,
                MinDistance = CurrentDistance; //set the smallest distance to current value
                IndexOfNearestPlayer = EnemyIndex.get(i); //store the index of current enemy.
                HPofNearestPlayer = CurrentHP;          // also store the HP
            }
            else if(CurrentDistance == MinDistance){ //if distance are the same, compare HP
                //HaveEqualDistance = true;
                if(CurrentHP<HPofNearestPlayer){
                    IndexOfNearestPlayer = EnemyIndex.get(i);
                    HPofNearestPlayer = CurrentHP;
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
            return GetLeastDangerousEnemy(CandidatesOfLeastDangerousPlayer);
        }

        nearestChoice = state.getGameState().
        getPlayerStateByIndex(EnemyIndex.get(IndexOfNearestPlayer));

        return nearestChoice;
    }
    
}

