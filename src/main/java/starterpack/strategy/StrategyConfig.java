package starterpack.strategy;

public class StrategyConfig {

    /**
     * Return the strategy that your bot should use.
     * @param playerIndex A player index that can be used if necessary.
     * @return A Strategy object.
     */
    public static Strategy getStrategy(int playerIndex) {
<<<<<<< HEAD
    return new ArcherStrategy();
=======
    //    if (playerIndex == 0) return new OnlyBuyAndUseShieldStrategy();
    //    else return new OnlyGoto00AndAttackStrategy();
    if(playerIndex==0){
        return new KnightStrategy();
>>>>>>> 252042b0819fb9fe473d54c1293cbb98bd04add0
    }
    
    return new RandomStrategy();
}
