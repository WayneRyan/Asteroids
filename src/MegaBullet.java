import java.awt.Color;
import java.awt.Graphics;

public class MegaBullet {
	private double x, y, vx, vy;

	public MegaBullet(double direction) {
		x = MainClass.WIDTH / 2;
		y = MainClass.HEIGHT / 2;
		this.vx = Math.sin(direction) * 20;
		this.vy = -Math.cos(direction) * 20;
		update(0, 0);
		update(0, 0);
	}

	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillOval((int) x - 30, (int) y - 30, 60, 60);
	}

	public void update(double dx, double dy) {
		x += vx + dx * 2;
		y += vy + dy * 2;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}



