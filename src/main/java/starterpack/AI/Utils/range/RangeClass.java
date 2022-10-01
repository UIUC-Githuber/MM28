package starterpack.AI.Utils.range;

import starterpack.game.PlayerState;

public class RangeClass {
    public RangeFlag flag;
    public PlayerState ps; // Note: if flag == RangeFlag.Wall, then ps == myself, otherwise ps == enemy
    public int dist; // Note: if self within at least one box (not on edge), then dist < 0, otherwise dist >= 0.
    public Direction dir;
    public RangeClass(RangeFlag flag, PlayerState ps, int dist, Direction dir) {
        this.flag = flag;
        this.ps = ps;
        this.dist = dist;
        this.dir = dir;
    }
}
