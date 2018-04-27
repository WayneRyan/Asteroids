import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Asteroid {
	private double x, y, vx, vy, size;

	public Asteroid() {
		size = 50;
		x = Math.random() * MainClass.WIDTH;
		y = Math.random() * MainClass.HEIGHT;
		vx = Math.random() * 5 - 2.5;
		vy = Math.random() * 5 - 2.5;
		int temp = (int) (Math.random() * 4);
		switch (temp) {
		case 0:
			y = -size;
			if (vy < 0)
				vy *= -1;
			break;
		case 1:
			x = -size;
			if (vx < 0)
				vx *= -1;
			break;
		case 2:
			y = MainClass.HEIGHT;
			if (vy > 0)
				vy *= -1;
			break;
		case 3:
			x = MainClass.WIDTH;
			if (vx > 0)
				vx *= -1;
			break;
		}

	}
	public boolean hit(Bullet b) {
		double dx = size / 2 + this.x - b.getX();
		double dy = size / 2 + this.y - b.getY();
		return Math.sqrt(dx * dx + dy * dy) < size / 2;
	}
	public boolean hit(Asteroid a) {
		double dx = size / 2 + this.x - a.getX();
		double dy = size / 2 + this.y - a.getY();
		return Math.sqrt(dx * dx + dy * dy) < size / 2;
	}
	private double getX() {
		return x;
	}
	private double getY() {
		return y;
	}
	public boolean hit(MegaBullet m) {
		double dx = size / 2 + this.x - m.getX();
		double dy = size / 2 + this.y - m.getY();
		return Math.sqrt(dx * dx + dy * dy) < size;
	}

	public List<Asteroid> breakApart() {
		ArrayList<Asteroid> retVal = new ArrayList<Asteroid>();
		if (size < 15)
			return retVal;
		Asteroid a = new Asteroid();
		a.x = this.x;
		a.y = this.y;
		a.size = this.size / 2;
		retVal.add(a);
		a = new Asteroid();
		a.x = this.x;
		a.y = this.y;
		a.size = this.size / 2;
		retVal.add(a);
		return retVal;
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawOval((int) x, (int) y, (int) size, (int) size);

	}

	public void update(double dx, double dy) {
		x += vx + dx;
		y += vy + dy;
		if (x < -size)
			x = MainClass.WIDTH;
		if (x > MainClass.WIDTH)
			x = -size;
		if (y < -size)
			y = MainClass.HEIGHT;
		if (y > MainClass.HEIGHT)
			y = -size;
	}

	public boolean hitship() {
		double dx = size / 2 + x - MainClass.WIDTH / 2;
		double dy = size / 2 + y - MainClass.HEIGHT / 2;
		return Math.sqrt(dx * dx + dy * dy) < size / 2 + 15;
	}

}
