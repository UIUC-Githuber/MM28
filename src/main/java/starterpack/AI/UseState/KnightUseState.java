package starterpack.AI.UseState;

import starterpack.game.GameState;
import starterpack.game.Item;

public class KnightUseState extends IUseState{

    public KnightUseState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean Update() {
        return true;
    }

    @Override
    public boolean Use() {
        if(getPlayerState().getItem()==Item.SHIELD){
            return true;
        }
        else{
            return false;
        }
        
    }
}
