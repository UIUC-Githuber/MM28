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
        if(getPlayerState().getGold()>=Item.SHIELD.getCost()){
            return Item.SHIELD;
        }
        else{
            return Item.NONE;
        }
        
    }
}
