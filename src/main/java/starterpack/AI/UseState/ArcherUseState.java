package starterpack.AI.UseState;

import starterpack.AI.Utils.Utils;
import starterpack.game.GameState;

public class ArcherUseState extends IUseState{

    public ArcherUseState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean Update() {
        return Use();
    }

    @Override
    public boolean Use() {
        if(Utils.GetDangerousPlayerState(this)==null || Utils.GetDangerousPlayerState(this).size()==0){
            return false;
        }
        return true;
    }
}
