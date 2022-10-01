package starterpack.AI.BuyState;

import starterpack.game.GameState;
import starterpack.game.Item;

public class WizardBuyState extends IBuyState{

    public WizardBuyState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Item Update() {
        return Item.NONE;
        
    }

    @Override
    public Item Buy() {
        return Item.NONE;
    }
}
