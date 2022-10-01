package starterpack.strategy;

import starterpack.game.GameState;
import starterpack.game.CharacterClass;
import starterpack.game.Item;
import starterpack.game.Position;
import starterpack.AI.AttackState.WizardAttackState;
import starterpack.AI.BuyState.WizardBuyState;
import starterpack.AI.MoveState.WizardMoveState;
import starterpack.AI.UseState.WizardUseState;;

public class WizardStrategy implements Strategy {

    /**
     * When the game initializes, you need to decide your starting class!
     * Also, feel free to initialize some variables you need here!
     * @return The CharacterClass you decided when the game starts.
     */
    public CharacterClass strategyInitialize(int myPlayerIndex) {
        if (myPlayerIndex == 0) {

        }
        return CharacterClass.WIZARD;
    }


    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public Position moveActionDecision(GameState gameState, int myPlayerIndex) {
        //Main.LOGGER.info("knight moving");
        //if (myPlayerIndex == 1 || myPlayerIndex == 3)
            return (new WizardMoveState(gameState, myPlayerIndex).Update());
        //else {
        //    if(myPlayerIndex == 0) return (new NaiveKnightMoveState(gameState, myPlayerIndex).Update());
        //    else return (new Naive2KnightMoveState(gameState, myPlayerIndex).Update());
        //}
    }

    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public int attackActionDecision(GameState gameState, int myPlayerIndex) {
        //Main.LOGGER.info("knight attacking");
        WizardAttackState attackState = new WizardAttackState(gameState, myPlayerIndex);
        return attackState.Update();
    }

    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public Item buyActionDecision(GameState gameState, int myPlayerIndex) {
        //Main.LOGGER.info("knight buying");
        WizardBuyState buyState = new WizardBuyState(gameState, myPlayerIndex);
        return buyState.Update();
    }

    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public boolean useActionDecision(GameState gameState, int myPlayerIndex) {
        //Main.LOGGER.info("knight using");
        WizardUseState useState = new WizardUseState(gameState, myPlayerIndex);
        return useState.Update();
    }
}

