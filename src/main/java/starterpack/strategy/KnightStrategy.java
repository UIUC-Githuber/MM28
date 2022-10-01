package starterpack.strategy;

import starterpack.game.GameState;
import starterpack.game.CharacterClass;
import starterpack.game.Item;
import starterpack.game.Position;
import starterpack.Main;
import starterpack.AI.*;
import starterpack.AI.AttackState.KnightAttackState;
import starterpack.AI.BuyState.KnightBuyState;
import starterpack.AI.MoveState.IMoveState;
import starterpack.AI.MoveState.NaiveKnightMoveState;
import starterpack.AI.UseState.KnightUseState;;

public class KnightStrategy implements Strategy {

    /**
     * When the game initializes, you need to decide your starting class!
     * Also, feel free to initialize some variables you need here!
     * @return The CharacterClass you decided when the game starts.
     */
    public CharacterClass strategyInitialize(int myPlayerIndex) {
        return CharacterClass.KNIGHT;
    }


    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public Position moveActionDecision(GameState gameState, int myPlayerIndex) {
        //Main.LOGGER.info("moving");
        NaiveKnightMoveState moveState = new NaiveKnightMoveState(gameState, myPlayerIndex);
        return moveState.Update();
    }

    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public int attackActionDecision(GameState gameState, int myPlayerIndex) {
        //Main.LOGGER.info("attacking");
        KnightAttackState attackState = new KnightAttackState(gameState, myPlayerIndex);
        return attackState.Update();
    }

    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public Item buyActionDecision(GameState gameState, int myPlayerIndex) {
        //Main.LOGGER.info("buying");
        KnightBuyState buyState = new KnightBuyState(gameState, myPlayerIndex);
        return buyState.Update();
    }

    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public boolean useActionDecision(GameState gameState, int myPlayerIndex) {
        //Main.LOGGER.info("using");
        KnightUseState useState = new KnightUseState(gameState, myPlayerIndex);
        return useState.Update();
    }
}
