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
import starterpack.AI.MoveState.Naive2KnightMoveState;
import starterpack.AI.MoveState.KnightMoveState;
import starterpack.AI.MoveState.NaiveKnightMoveState;
import starterpack.AI.UseState.KnightUseState;;

public class KnightStrategy implements Strategy {

    /**
     * When the game initializes, you need to decide your starting class!
     * Also, feel free to initialize some variables you need here!
     * @return The CharacterClass you decided when the game starts.
     */
    public CharacterClass strategyInitialize(int myPlayerIndex) {
        if (myPlayerIndex == 0) {

        }
        return CharacterClass.KNIGHT;
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
        return (new KnightMoveState(gameState, myPlayerIndex).Update());
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
        //Main.LOGGER.info("knight buying");
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
        //Main.LOGGER.info("knight using");
        KnightUseState useState = new KnightUseState(gameState, myPlayerIndex);
        return useState.Update();
    }
}
