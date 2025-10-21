import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * MassiveMotion is the main simulation. It reads configuration from a
 * properties file, maintains a single List of Body objects (the first element
 * is the star), and advances the simulation using a Timer.
 */
public class MassiveMotion extends JPanel implements ActionListener {

    protected Timer tm;

    protected int maxX, maxY;

    protected int starX, starY;

    protected int starSize;

    protected int starVx, starVy;

    protected double genX, genY;

    protected int bodySize;
    protected int bodyVelocity;
 
    protected List<Body> bodies;

    protected int timerDelay;

    protected String listImpl;

    /**
     * Create the panel simulation state from a properties file.
     * @param propfile path to a Java properties file containing keys such as
     *                 timer_delay, window_size_x, gen_x, body_size, list, etc.
     */
    public MassiveMotion(String propfile) {
        Properties prop = new Properties();
        try {
            InputStream is = new FileInputStream(propfile);
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try { 
            timerDelay = Integer.parseInt(prop.getProperty("timer_delay", "75"));
            tm = new Timer(timerDelay, this);
            maxX = Integer.parseInt(prop.getProperty("window_size_x", "1024"));
            maxY = Integer.parseInt(prop.getProperty("window_size_y", "768"));

            starX = Integer.parseInt(prop.getProperty("star_position_x", "512"));
            starY = Integer.parseInt(prop.getProperty("star_position_y", "384"));

            starSize = Integer.parseInt(prop.getProperty("star_size", "30"));
            starVx = Integer.parseInt(prop.getProperty("star_velocity_x", "0"));
            starVy = Integer.parseInt(prop.getProperty("star_velocity_y", "0"));

            genX = Double.parseDouble(prop.getProperty("gen_x", "0.06"));
            genY = Double.parseDouble(prop.getProperty("gen_y", "0.06"));

            bodySize = Integer.parseInt(prop.getProperty("body_size", "10"));
            bodyVelocity = Integer.parseInt(prop.getProperty("body_velocity", "3"));

            listImpl = prop.getProperty("list", "arraylist");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("arraylist".equals(listImpl)) {
            bodies = new ArrayList<>();
        } else if ("single".equals(listImpl)) {
            bodies = new LinkedList<>();
        } else if ("double".equals(listImpl)) {
            bodies = new DoublyLinkedList<>();
        } else if ("dummyhead".equals(listImpl)) {
            bodies = new DummyHeadLinkedList<>();
        }

        Body star = new Body(starX, starY, starVx, starVy, starSize, true);
        bodies.add(star);

    }

    /**
     * Paint all bodies. The star (first body) is drawn in red. This method
     * also ensures the timer is started.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < bodies.size(); i++) {
            Body b;
            try {
                b = bodies.get(i);
                g.setColor(b.isStar() ? Color.RED : Color.BLACK);
                g.fillOval(b.getX(), b.getY(), b.getSize(), b.getSize());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tm.start();
    }


    /**
     * Timer callback executed each epoch: spawns new bodies based on genX and genY probabilities,
     * advances all bodies, removes off-screen bodies and requests repaint.
     */
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            if (bodies.size() > 0) {
                Body s = bodies.get(0);
                s.setVx(starVx);
                s.setVy(starVy);
            }
        } catch (Exception e) {}

        if (Math.random() < genX) {
            boolean top = Math.random() < 0.5;
            int x = (int) (Math.random() * maxX);
            int y;
            if (top) y = 0; else y = maxY - bodySize;
            int bv = Math.max(1, bodyVelocity);
            int vx = (int) (Math.random() * (2 * bv + 1)) - bv;
            int vy;
            if (top) {
                // from top: move downward (positive vy)
                vy = 1 + (int) (Math.random() * bv); // 1..bv
            } else {
                // from bottom: move upward (negative vy)
                vy = - (1 + (int) (Math.random() * bv)); // -1..-bv
            }
            bodies.add(new Body(x, y, vx, vy, bodySize, false));
        }

        if (Math.random() < genY) {
            boolean left = Math.random() < 0.5;
            int x;
            if (left) x = 0; else x = maxX - bodySize;
            int y = (int) (Math.random() * maxY);
            int bv = Math.max(1, bodyVelocity);
            int vy = (int) (Math.random() * (2 * bv + 1)) - bv;
            int vx;
            if (left) {
                // from left: move right (positive vx)
                vx = 1 + (int) (Math.random() * bv);
            } else {
                // from right: move left (negative vx)
                vx = - (1 + (int) (Math.random() * bv));
            }
            bodies.add(new Body(x, y, vx, vy, bodySize, false));
        }

        try {
            // Build a new list of survivors with the same implementation type
            List<Body> survivors;
            if (bodies instanceof ArrayList) survivors = new ArrayList<>();
            else if (bodies instanceof LinkedList) survivors = new LinkedList<>();
            else if (bodies instanceof DoublyLinkedList) survivors = new DoublyLinkedList<>();
            else if (bodies instanceof DummyHeadLinkedList) survivors = new DummyHeadLinkedList<>();
            else survivors = new ArrayList<>();

            // keep and update the star (index 0) if present
            if (bodies.size() > 0) {
                Body star = bodies.get(0);
                star.move();
                survivors.add(star);
            }

            for (int i = 1; i < bodies.size(); i++) {
                Body b = bodies.get(i);
                b.move();
                int bx = b.getX();
                int by = b.getY();
                // If the body is outside the view, drop it
                if (!(bx < -bodySize || bx > maxX + bodySize || by < -bodySize || by > maxY + bodySize)) {
                    survivors.add(b);
                }
            }

            bodies = survivors;
        } catch (Exception e) {
            e.printStackTrace();
        }

        repaint();
    }

    /**
     * Takes the properties filename as the first
     * command-line argument. Creates the JFrame and adds the MassiveMotion panel.
     */
    public static void main(String[] args) {
        System.out.println("Massive Motion starting...");
        MassiveMotion mm = new MassiveMotion(args[0]);

        JFrame jf = new JFrame();
        jf.setTitle("Massive Motion");
        jf.setSize(mm.maxX, mm.maxY);
        jf.add(mm);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
