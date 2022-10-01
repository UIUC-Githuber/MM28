package starterpack.strategy;

import starterpack.game.GameState;
import starterpack.game.CharacterClass;
import starterpack.game.Item;
import starterpack.game.Position;
import starterpack.Main;
import starterpack.AI.*;
import starterpack.AI.AttackState.ArcherAttackState;
import starterpack.AI.AttackState.KnightAttackState;
import starterpack.AI.BuyState.ArcherBuyState;
import starterpack.AI.BuyState.KnightBuyState;
import starterpack.AI.MoveState.ArcherMoveState;
import starterpack.AI.MoveState.IMoveState;
import starterpack.AI.MoveState.NaiveArcherMoveState;
import starterpack.AI.MoveState.NaiveKnightMoveState;
import starterpack.AI.UseState.ArcherUseState;
import starterpack.AI.UseState.KnightUseState;;

public class NaiveArcherStrategy implements Strategy {

    /**
     * When the game initializes, you need to decide your starting class!
     * Also, feel free to initialize some variables you need here!
     * @return The CharacterClass you decided when the game starts.
     */
    public CharacterClass strategyInitialize(int myPlayerIndex) {
        return CharacterClass.ARCHER;
    }


    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public Position moveActionDecision(GameState gameState, int myPlayerIndex) {
        Main.LOGGER.info("moving");
        NaiveArcherMoveState moveState = new NaiveArcherMoveState(gameState, myPlayerIndex);
        return moveState.Update();
    }

    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public int attackActionDecision(GameState gameState, int myPlayerIndex) {
        Main.LOGGER.info("attacking");
        ArcherAttackState attackState = new ArcherAttackState(gameState, myPlayerIndex);
        return attackState.Update();
    }

    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public Item buyActionDecision(GameState gameState, int myPlayerIndex) {
        Main.LOGGER.info("buying");
        ArcherBuyState buyState = new ArcherBuyState(gameState, myPlayerIndex);
        return buyState.Update();
    }

    /**
     *
     * @param gameState
     * @param myPlayerIndex
     * @return
     */
    public boolean useActionDecision(GameState gameState, int myPlayerIndex) {
        Main.LOGGER.info("using");
        ArcherUseState useState = new ArcherUseState(gameState, myPlayerIndex);
        return useState.Update();
    }
}
