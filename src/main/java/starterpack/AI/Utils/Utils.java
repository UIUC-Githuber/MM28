package starterpack.AI.Utils;

import starterpack.game.GameState;
import starterpack.game.CharacterClass;
import starterpack.game.Item;
import starterpack.game.Position;
import starterpack.game.PlayerState;
import starterpack.AI.CharacterAI;

public final class Utils {
    // TODO
    public static final PlayerState MinHealthPlayer(GameState gs, int playerindex) {
        int minPH;
        int index;
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
    public static final Boolean IfExistFatal(GameState gs, int playerindex, int otherplayerindex) {
        int damage = gs.getPlayerStateByIndex(playerindex).getStatSet().getDamage();
        
        if (gs.getPlayerStateByIndex(otherplayerindex).getHealth() <= damage) {
            return true;
        }
        
        return false;
    }
    public static final PlayerState FindFatal(GameState gs, int playerindex) {
        int damage = gs.getPlayerStateByIndex(playerindex).getStatSet().getDamage();
        PlayerState player = gs.getPlayerStateByIndex(0);
        for (int i =0; i < 4; i++) {
            if (i != playerindex && gs.getPlayerStateByIndex(i).getHealth() <= damage) {
                player = gs.getPlayerStateByIndex(i);
            }
        }
        return player;
    }
    
}

