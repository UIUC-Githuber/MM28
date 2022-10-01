package starterpack.strategy;

import starterpack.AI.MoveState.NaiveKnightMoveState;

public class StrategyConfig {

    /**
     * Return the strategy that your bot should use.
     * @param playerIndex A player index that can be used if necessary.
     * @return A Strategy object.
     */
    public static Strategy getStrategy(int playerIndex) {

      return new WizardStrategy();
   
    }

}
