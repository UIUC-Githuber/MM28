package starterpack.AI;

public class CharacterAI {
    private int playerIndex;
    public CharacterAI(int playerIndex, IAIState state) {
        this.AIState = state;
        this.playerIndex = playerIndex;
    }
    public int getPlayerIndex() {
        return this.playerIndex;
    }
    private IAIState AIState = null;
    public void ChangeAIState(IAIState state){
        this.AIState = state;
    }
}
