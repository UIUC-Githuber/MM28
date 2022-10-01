package starterpack.AI;

interface IAIState{
    public void ChangeState(AIState state);
    public void SetAttackTarget();
    public void SetMoveTarget();
}
public abstract class AIState implements IAIState{
    public void ChangeState(AIState state) {
        // TODO
    }
    public void SetAttackTarget() {
        // TODO
    }
    public void SetMoveTarget() {
        // TODO
    }
}