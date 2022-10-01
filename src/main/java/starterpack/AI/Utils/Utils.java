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
        //攻击范围内最小血量角色
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
        //判断攻击范围内可被秒杀单位
        int playerindex = state.getPlayerIndex();
        GameState gs = state.getGameState();
        int damage = gs.getPlayerStateByIndex(playerindex).getStatSet().getDamage();
        
        if (gs.getPlayerStateByIndex(otherplayerindex).getHealth() <= damage) {
            return true;
        }
        
        return false;
    }
    public static final PlayerState FindFatal(AIState state) {
        //查找范围内可被秒杀单位
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
        //收集index为i的敌人与我方数据（该敌人与我方距离，该敌人攻击力，该敌人攻击距离，该敌人的血量，敌方金币，敌方分)
        List<Integer> EnemyInfo = new ArrayList<>();
        int OurIndex = state.getPlayerIndex();       //find Our Player Index


        //获取敌方的：与我方距离，攻击力，攻击距离，生命值，金币，分数。
        int DistanceFromEnemy    = Utility.chebyshevDistance(
                                   state.getGameState().getPlayerStateByIndex(i).getPosition(),
                                   state.getGameState().getPlayerStateByIndex(OurIndex).getPosition()),
            
            EnemyAttackRange     = state.getGameState().getPlayerStateByIndex(i).getStatSet().getRange(), 
            
            Enemy_Damage         = state.getGameState().getPlayerStateByIndex(i).getStatSet().getDamage(), 

            EnemyHP              = state.getGameState().getPlayerStateByIndex(i).getHealth(),
            
            EnemyGold            = state.getGameState().getPlayerStateByIndex(i).getGold(),
            
            EnemyScore           = state.getGameState().getPlayerStateByIndex(i).getScore();
            
        //将敌方的：与我方距离，攻击力，攻击距离，生命值，金币，分数。储存在List EnemyInfo里面。
        EnemyInfo.add(DistanceFromEnemy);
        EnemyInfo.add(Enemy_Damage);
        EnemyInfo.add(EnemyAttackRange);
        EnemyInfo.add(EnemyHP);
        EnemyInfo.add(EnemyGold);
        EnemyInfo.add(EnemyScore);

        return  EnemyInfo;   //返还敌方数据。

    }
    public static final Boolean DetectRange(AIState state, int otherplayerindex) {
        //查找otherplayer是否在range里
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
        //查找在range里的player
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
        //返回index不等于我的人（敌人）的index列表
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

    
}

