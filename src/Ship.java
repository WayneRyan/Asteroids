import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Ship implements KeyListener {

	double direction;
	double speed;
	double health;
	double dv;
	public double Shield;
	private boolean isdead, LeftPressed, RightPressed, UpPressed, DownPressed;

	public Ship() {
		dv = 0;
		health = 10;
		speed = 0.0;
		direction = Math.PI / 4;
		Shield = 240;

	}

	public List<Bullet> explode() {
		List<Bullet> retVal = new ArrayList<Bullet>();
		for (int i = 0; i < 1000; i++) {
			Bullet b = new Bullet(Math.random() * Math.PI * 2);
			b.setColor(Color.yellow);
			retVal.add(b);
		}
		return retVal;
	}

	public void takedamage() {
		if (Shield < 240 && Shield >= 0) {
			health -= 1;
		} else {
			Shield = 0;
		}
	}

	public boolean justdied() {
		if (health < 0 && !isdead) {
			return isdead = true;
		}
		return false;
	}

	public void update() {
		if (LeftPressed)
			dv -= 0.1;
		if (RightPressed)
			dv += 0.1;
		direction += dv;
		dv *= 0.6;
		if (UpPressed)
			speed += 0.5;
		if (DownPressed)
			speed -= 0.5;
		speed *= .95;
		if (isdead()) {
			speed = 0;
		}
	}

	public void draw(Graphics g) {
		if (health < 0)
			return;
		if (health > 5)
			g.setColor(Color.green);
		if (health <= 5 && health > 3)
			g.setColor(Color.yellow);
		if (health <= 3)
			g.setColor(Color.red);
		g.fillRect(MainClass.WIDTH / 2 + 30, MainClass.HEIGHT / 2, 5, (int) (health * 4));
		g.setColor(Color.BLUE);
		g.fillRect(MainClass.WIDTH / 2 + 40, MainClass.HEIGHT / 2, 5, (int) ((Shield * 4)/24));
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform old, at;
		old = g2.getTransform();
		at = new AffineTransform();
		int centerX = MainClass.WIDTH / 2;
		int centerY = MainClass.HEIGHT / 2;
		at.translate(centerX, centerY);
		at.rotate(direction);
		at.translate(-centerX, -centerY);
		g2.setTransform(at);
		int[] x = { centerX - 20, centerX, centerX + 20 };
		int[] y = { centerY + 24, centerY - 30, centerY + 24 };
		g2.setColor(Color.white);
		g2.drawPolygon(x, y, 3);
		g2.setTransform(old);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D && !isdead) {
			RightPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_A && !isdead) {
			LeftPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_W && !isdead) {
			UpPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_S && !isdead) {
			DownPressed = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) {
			RightPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			LeftPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			UpPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			DownPressed = false;
		}
	}
	
			

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public double getDirection() {
		return direction;
	}

	public double getSpeed() {
		return speed;
	}

	public boolean isdead() {
		return isdead;
	}

	public double Shield(double d) {
		return Shield;
		
	}

}
