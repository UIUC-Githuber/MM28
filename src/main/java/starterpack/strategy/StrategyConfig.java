package starterpack.strategy;

import starterpack.AI.MoveState.NaiveKnightMoveState;

public class StrategyConfig {

    /**
     * Return the strategy that your bot should use.
     * @param playerIndex A player index that can be used if necessary.
     * @return A Strategy object.
     */
    public static Strategy getStrategy(int playerIndex) {

    //if (playerIndex == 0 ||playerIndex == 1) return new KnightStrategy();
    //else 

      if(playerIndex == 0){
        return new WizardStrategy();
     }

     if(playerIndex == 1){
<<<<<<< HEAD
=======

>>>>>>> ef2205f41fd38d7c96518af18b19ff1941194db3
        return new KnightStrategy();
     }

     if(playerIndex == 2){
<<<<<<< HEAD
        return new CleverArcherStrategy();
=======
        return new WizardStrategy();

>>>>>>> ef2205f41fd38d7c96518af18b19ff1941194db3
     }

     if(playerIndex == 3){
        return new Naive2KnightStrategy();
     }
   return null;
     }

}
