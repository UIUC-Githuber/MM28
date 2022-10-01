package starterpack.AI.UseState;

import starterpack.game.GameState;

public class ArcherUseState extends IUseState{

    public ArcherUseState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean Update() {
        return false;
    }

    @Override
    public boolean Use() {
        return true;
        
    }
}
