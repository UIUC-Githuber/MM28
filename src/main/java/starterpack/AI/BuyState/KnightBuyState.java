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
        if(getPlayerState().getGold() >= 8){
            return Item.ANEMOI_WINGS;
        }
        else{
            return Item.NONE;
        }
        
    }
}
