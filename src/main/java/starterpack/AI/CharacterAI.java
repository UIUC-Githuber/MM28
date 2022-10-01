package starterpack.AI;

public class CharacterAI {
    private IAIState AIState = null;
    public void ChangeAIState(IAIState state){
        this.AIState = state;
    }
}
