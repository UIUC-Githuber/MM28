package starterpack.AI.UseState;

import starterpack.AI.Utils.Utils;
import starterpack.game.GameState;
import starterpack.game.Item;

public class WizardUseState extends IUseState{

    public WizardUseState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }
    @Override
    public boolean Update() {
        // return Use();
        return false;
    }

    @Override
    public boolean Use() {
        // if(getPlayerState().getItem() != Item.NONE) {
        //     if(!Utils.DetectRangeReturnList(this).isEmpty()) {
        //         return true;

        //     }
        // }
            
        return false;
        
    }
}
