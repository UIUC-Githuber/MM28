package starterpack.AI.UseState;

import starterpack.game.GameState;

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
        return false;
        
    }
}
