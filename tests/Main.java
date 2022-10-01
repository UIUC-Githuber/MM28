import java.util.*;
enum RangeFlag {
    WALL,
    PLAYER
}
enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}
class RangeClass {
    public RangeFlag flag;
    public PlayerState ps; // Note: if flag == RangeFlag.Wall, then ps == myself, otherwise ps == enemy
    public int dist; // Note: if self within at least one box (not on edge), then dist < 0, otherwise dist >= 0.
    public Direction dir;
    public RangeClass(RangeFlag flag, int dist, Direction dir) {
        this.flag = flag;
        this.ps = ps;
        this.dist = dist;
        this.dir = dir;
    }
}

class Position {
    private int x, y;
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Integer> paths = GetEscapePath(new Position(0, 1), new Position(4,5), 3, true);
        assert (paths.get(0) == 1) && (paths.get(1) == 8) && (paths.get(2) == 9) && (paths.get(3) == 0);
        paths = GetEscapePath(new Position(3, 1), new Position(4,5), 3, true);
        assert (paths.get(0) == 2) && (paths.get(1) == 7) && (paths.get(2) == 2) && (paths.get(3) == 2);
    }

    public static final List<Integer> GetEscapePath (Position cPos, Position ePos, int range, boolean eIdx) {
        final RangeClass aa = new RangeClass(RangeFlag.WALL, GetPosition(state).getY(), Direction.UP);
        final RangeClass bb = new RangeClass(RangeFlag.WALL, 9-GetPosition(state).getX(), Direction.RIGHT);
        final RangeClass cc = new RangeClass(RangeFlag.WALL, 9-GetPosition(state).getY(), Direction.DOWN);
        final RangeClass dd = new RangeClass(RangeFlag.WALL, GetPosition(state).getX(), Direction.LEFT);
        RangeClass a, b, c, d;
        if (eIdx == false) {
            a=aa;b=bb;c=cc;d=dd;
        }
        else {
            int minX = Math.max(0, ePos.getX()-range);
            int maxX = Math.min(9, ePos.getX()+range);
            int minY = Math.max(0, ePos.getY()-range);
            int maxY = Math.min(9, ePos.getY()+range);
            if(!(cPos.getX() <= maxX && cPos.getX() >= minX && cPos.getY() <= maxY && cPos.getY() >= minY)) { // not dangered
                if((cPos.getX() < minX || cPos.getX() > maxX) && cPos.getY() < minY) {
                    a = aa; b = bb; c = cc; d = dd;
                }
                else if(cPos.getX() >= minX && cPos.getX() <= maxX && cPos.getY() < minY) {
                    a = aa; b = bb; d = dd;
                    c = new RangeClass(RangeFlag.PLAYER, minY - cPos.getY(), Direction.DOWN);
                }
                else if(cPos.getX() < minX && cPos.getY() >= minY && cPos.getY() <= maxY) {
                    a = aa; c = cc; d = dd;
                    b = new RangeClass(RangeFlag.PLAYER, minX - cPos.getX(), Direction.RIGHT);
                }
                else if(cPos.getX() == minX && cPos.getY() >= minY && cPos.getY() <= maxY) {
                    a = new RangeClass(RangeFlag.PLAYER, 0, Direction.UP);
                    b = new RangeClass(RangeFlag.PLAYER, 0, Direction.RIGHT);
                    c = new RangeClass(RangeFlag.PLAYER, 0, Direction.DOWN);
                    d = dd;
                }
                else if(cPos.getX() > minX && cPos.getX() < maxX && cPos.getY() == minY) {
                    a = aa;
                    b = new RangeClass(RangeFlag.PLAYER, 0, Direction.RIGHT);
                    c = new RangeClass(RangeFlag.PLAYER, 0, Direction.DOWN);
                    d = new RangeClass(RangeFlag.PLAYER, 0, Direction.LEFT);
                }
                else if(cPos.getX() > minX && cPos.getX() < maxX && cPos.getY() == maxY) {
                    a = new RangeClass(RangeFlag.PLAYER, 0, Direction.UP);
                    b = new RangeClass(RangeFlag.PLAYER, 0, Direction.RIGHT);
                    c = cc;
                    d = new RangeClass(RangeFlag.PLAYER, 0, Direction.LEFT);
                }
                else if(cPos.getX() == maxX && cPos.getY() >= minY && cPos.getY() <= maxY) {
                    a = new RangeClass(RangeFlag.PLAYER, 0, Direction.UP);
                    b = bb;
                    c = new RangeClass(RangeFlag.PLAYER, 0, Direction.DOWN);
                    d = new RangeClass(RangeFlag.PLAYER, 0, Direction.LEFT);
                }
                else if(cPos.getX() > maxX && cPos.getY() >= minY && cPos.getY() <= maxY) {
                    a = aa; c = cc; b = bb;
                    d = new RangeClass(RangeFlag.PLAYER, cPos.getX() - maxX, Direction.RIGHT);
                }
                else if((cPos.getX() < minX || cPos.getX() > maxX) && cPos.getY() > maxY) {
                    a = aa; b = bb; c = cc; d = dd;
                }
                else {
                    b = bb; c = cc; d = dd;
                    a = new RangeClass(RangeFlag.PLAYER, cPos.getY() - maxY, Direction.UP);
                }
            }
            else {
                a = new RangeClass(RangeFlag.PLAYER, cPos.getY()-maxY, Direction.UP);
                c = new RangeClass(RangeFlag.PLAYER, minY-cPos.getY(), Direction.DOWN);
                b = new RangeClass(RangeFlag.PLAYER, minX-cPos.getX(), Direction.RIGHT);
                d = new RangeClass(RangeFlag.PLAYER, cPos.getX()-maxX, Direction.LEFT);
            }
        }
        List<Integer> paths = new ArrayList<>();
        paths.add(a.dist);paths.add(b.dist);paths.add(c.dist);paths.add(d.dist);
        return paths;
    }
}
