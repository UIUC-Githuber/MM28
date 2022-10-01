package starterpack.AI.UseState;

import starterpack.game.GameState;
import starterpack.game.Item;

public class WizardUseState extends IUseState{

    public WizardUseState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }
    @Override
    public boolean Update() {
        return false;
    }

    @Override
    public boolean Use() {
        if(getPlayerState().getItem() != Item.NONE)
            return true;
        return false;
        
    }
}
