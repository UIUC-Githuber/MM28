package starterpack.AI.BuyState;

import starterpack.game.GameState;
import starterpack.game.Item;

public class KnightBuyState extends IBuyState{

    public KnightBuyState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Item Update() {
        return Buy();
        
    }

    @Override
    public Item Buy() {
        if(getPlayerState().getGold()>=8&&getPlayerState().getItem()==Item.NONE){
            return Item.HUNTER_SCOPE;
        }
        else{
            return Item.NONE;
        }
        
    }
}
