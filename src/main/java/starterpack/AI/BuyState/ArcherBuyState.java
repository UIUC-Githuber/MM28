package starterpack.AI.BuyState;

import starterpack.AI.AIState;
import starterpack.game.GameState;
import starterpack.game.Item;

public class ArcherBuyState extends IBuyState {

    public ArcherBuyState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Item Update() {
        return Item.NONE;
    }

    @Override
    public Item Buy() {
        // Choose a stretagy from MoreSpeed, MoreRange, MoreDamage
        if(getPlayerState().getGold()>=8){
            return MoreDamage();
        }
        else{
            return Item.NONE;
        }        
    }

    private final Item MoreSpeed(){
        return Item.ANEMOI_WINGS;
    }
    private final Item MoreRange(){
        return Item.HUNTER_SCOPE;
    }
    private final Item MoreDamage(){
        return Item.RALLY_BANNER;
    }
    
    

}
