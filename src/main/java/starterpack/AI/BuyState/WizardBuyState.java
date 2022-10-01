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
        return Buy();
        
    }

    @Override
    public Item Buy() {
        if(getPlayerState().getGold()>=8 && getPlayerState().getItem()==Item.NONE){
            
            return MoreRange();
        }
        return Item.NONE;
        /* 
         Choose a stretagy from MoreSpeed, MoreRange, MoreDamage
        if(getPlayerState().getGold()>=8){
            Main.LOGGER.info("RALLY_BANNER = " + MoreDamage().getStatSet().getDamage());
            getPlayerState().setGold(getPlayerState().getGold()-8);
            return MoreDamage();
        }
        else{
            Main.LOGGER.info("NonE item");
            return Item.NONE;
        }        
        */
    }

    private final Item MoreSpeed(){
        return Item.ANEMOI_WINGS;
    }
    private final Item MoreRange(){
        return Item.HUNTER_SCOPE;
    }
    private final Item MoreDamage(){
        return Item.STRENGTH_POTION;
    }
}
