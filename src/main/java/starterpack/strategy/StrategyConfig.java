package starterpack.strategy;

public class StrategyConfig {

    /**
     * Return the strategy that your bot should use.
     * @param playerIndex A player index that can be used if necessary.
     * @return A Strategy object.
     */
    public static Strategy getStrategy(int playerIndex) {

    //if (playerIndex == 0 ||playerIndex == 1) return new KnightStrategy();
    //else 

    return new ArcherStrategy();
    }
}
