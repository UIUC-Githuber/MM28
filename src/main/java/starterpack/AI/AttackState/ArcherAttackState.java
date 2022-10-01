package starterpack.AI.AttackState;

import starterpack.game.GameState;
import starterpack.AI.Utils.Utils;

import starterpack.game.PlayerState;

import java.util.List;
import java.util.ArrayList;

public class ArcherAttackState extends IAttackState{

    public ArcherAttackState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public int Update() {
        return Attack();
        
    }

    @Override
    public int Attack() {
        GameState gs = getGameState();
        // if multiple ExistFatal: 
        ///if in multiple other's range, attack max score; 
        ///elif in one other's range, do something ; 
        ///else attack greatest damage
        List<PlayerState> fatalbots = Utils.ListFindFatal(this);
        List<PlayerState> killingbots = new ArrayList<PlayerState>();
        for (int i =0; i< fatalbots.size(); i++) {
            if(Utils.DetectRangeGameState(getGameState(), Utils.Getplayerindex(fatalbots.get(i), gs), getPlayerIndex())) {
                killingbots.add(fatalbots.get(i));
            }
        }

        if(fatalbots.size()>1) {
            if(killingbots.size()>1) {
                //if in multiple other's range, attack max score; 
                return Utils.Getplayerindex(Utils.maxScore(killingbots), getGameState()); 
            } else if (killingbots.size()==1) {
                //need to change
                return Utils.Getplayerindex(killingbots.get(0), getGameState()); 
            } else {
                //attack greatest damage if not in other's range
                return Utils.Getplayerindex(Utils.maxDamage(killingbots), getGameState()); 
            }
            
            
        }


        // if one existFatal: attack 
        else if(fatalbots.size() == 1)  {
            return Utils.Getplayerindex(fatalbots.get(0), getGameState()); 
        }

        // if none existFatal: attack greatest damage
        else {
            List<PlayerState> otherplayers = new ArrayList<PlayerState>();
            for (int i = 0; i < 4; i++) {
                if(i != getPlayerIndex()) {
                    otherplayers.add(getGameState().getPlayerStateByIndex(i));
                }
            }
            return Utils.Getplayerindex(Utils.maxDamage(killingbots), getGameState()); 
        }


        
    }
}
