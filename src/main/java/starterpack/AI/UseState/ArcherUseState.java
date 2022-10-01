package starterpack.AI.UseState;

import starterpack.AI.Utils.Utils;
import starterpack.game.GameState;
import starterpack.game.Position;
import starterpack.util.Utility;

public class ArcherUseState extends IUseState{

    public ArcherUseState(GameState gameState, int playerIndex) {
        super(gameState, playerIndex);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean Update() {
        return Use();
    }

    @Override
    public boolean Use() {
        Position myPosition = Utils.GetPosition(this);
        if(Utility.spawnPoints.get(getPlayerIndex()).equals(myPosition)){
            return false;
        }
        return true;
    }
}
