package starterpack.strategy;

import starterpack.game.GameState;
import starterpack.game.CharacterClass;
import starterpack.game.Item;
import starterpack.game.Position;
import starterpack.Main;
import starterpack.AI.*;
import starterpack.AI.AttackState.ArcherAttackState;
import starterpack.AI.AttackState.KnightAttackState;
import starterpack.AI.AttackState.WizardAttackState;
import starterpack.AI.BuyState.ArcherBuyState;
import starterpack.AI.BuyState.KnightBuyState;
import starterpack.AI.BuyState.WizardBuyState;
import starterpack.AI.MoveState.ArcherMoveState;
import starterpack.AI.MoveState.HunterWizardMoveState;
import starterpack.AI.MoveState.IMoveState;
import starterpack.AI.MoveState.NaiveKnightMoveState;
import starterpack.AI.UseState.ArcherUseState;
import starterpack.AI.UseState.KnightUseState;
import starterpack.AI.UseState.WizardUseState;;

public class HunterWizardStrategy implements Strategy {

    /**
     * When the game initializes, you need to decide your starting class!
     * Also, feel free to initialize some variables you need here!
     * @return The CharacterClass you decided when the game starts.
     */
    public CharacterClass strategyInitialize(int myPlayerIndex) {
        return CharacterClass.WIZARD;
    }


    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public Position moveActionDecision(GameState gameState, int myPlayerIndex) {
        //Main.LOGGER.info("moving");
        HunterWizardMoveState moveState = new HunterWizardMoveState(gameState, myPlayerIndex);
        return moveState.Update();
    }

    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public int attackActionDecision(GameState gameState, int myPlayerIndex) {
        //Main.LOGGER.info("archer attacking");
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
        Main.LOGGER.info("archer buying");
        WizardBuyState buyState = new WizardBuyState(gameState, myPlayerIndex);
        return buyState.Update();
        //return Item.NONE;
    }

    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public boolean useActionDecision(GameState gameState, int myPlayerIndex) {
        //Main.LOGGER.info("archer using");
        WizardUseState useState = new WizardUseState(gameState, myPlayerIndex);
        return useState.Update();
    }
}
