package starterpack.AI.AttackState;

import starterpack.game.GameState;
import starterpack.AI.Utils.Utils;

public class KnightAttackState extends IAttackState{


    @Override
    public int Update() {
        // TODO Auto-generated method stub
        return Attack();
        
    }

    @Override

        // TODO Auto-generated method stub
        GameState gs = getGameState();
        if(Utils.DetectRange(this) != null) {
            return Utils.Getplayerindex (Utils.DetectRange(this), gs);
        }
        return -1;

        
    }
}
