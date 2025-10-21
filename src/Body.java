/**
 * Represents a celestial body in the simulation.
 * Encapsulates position (x,y), velocity (vx,vy), and a fixed size.
 * The isStar flag indicates the special central star which the simulation
 * treats differently (it is not removed when it moves off-screen).
 */
public class Body {
    private int x, y;
    private int vx, vy;
    private final int size;
    private final boolean isStar;

    /**
     * Create a body.
     * @param x initial x position
     * @param y initial y position
     * @param vx initial x velocity
     * @param vy initial y velocity
     * @param size diameter when drawn
     * @param isStar whether this body is the central star
     */
    public Body(int x, int y, int vx, int vy, int size, boolean isStar) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.size = size;
        this.isStar = isStar;
    }

    /**
     * Getters and setters.
     * @return
     */
    public int getX() { return x; }
    public int getY() { return y; }
    public int getVx() { return vx; }
    public int getVy() { return vy; }
    public int getSize() { return size; }
    public boolean isStar() { return isStar; }

    public void setVx(int vx) { this.vx = vx; }
    public void setVy(int vy) { this.vy = vy; }

    /**
     * Advance the body's position by its velocity.
     */
    public void move() {
        x += vx;
        y += vy;
    }
}
