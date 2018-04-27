import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class MainClass extends JFrame implements KeyListener, Runnable {
	public static final int HEIGHT = 1080;
	public static final int WIDTH = 1920;

	BufferedImage offscreen;
	Graphics bg;

	Ship myShip;
	List<Bullet> allBullets;
	List<Asteroid> allAsteroids;
	List<MegaBullet> allMegaBullets;
	private boolean Shoot;
	private int CanShoot, MegaShoot;
	private int time;

	public MainClass() {
		Shoot = true;
		MegaShoot = 24;
		CanShoot = 15;
		allBullets = new ArrayList<Bullet>();
		allAsteroids = new ArrayList<Asteroid>();
		allMegaBullets = new ArrayList<MegaBullet>();
		myShip = new Ship();
		this.addKeyListener(myShip);
		offscreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.addKeyListener(this);
		bg = offscreen.getGraphics();
		Font f = bg.getFont().deriveFont(30f);
		bg.setFont(f);
		new Thread(this).start();
	}

	public static void main(String[] args) {
		MainClass mc = new MainClass();
		mc.setSize(WIDTH, HEIGHT);
		mc.setResizable(false);
		mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mc.setVisible(true);
	}

	public void paint(Graphics g) {
		bg.setColor(Color.BLACK);
		bg.fillRect(0, 0, WIDTH, HEIGHT);
		myShip.draw(bg);
		for (Bullet b : allBullets) {
			b.draw(bg);
		}
		for (Asteroid a : allAsteroids) {
			a.draw(bg);
		}
		for (MegaBullet m : allMegaBullets) {
			m.draw(bg);
		}
		bg.setColor(Color.white);
		bg.drawString("" + (time / 30), 50, 80);
		if (CanShoot >= -15 && CanShoot <= 0) {
			bg.setColor(Color.GREEN);
		} else {
			bg.setColor(Color.RED);
		}
		if (CanShoot < -15)
			bg.setColor(Color.BLUE);
		if (!myShip.isdead()) {
			bg.fillOval(WIDTH / 2 - 8, HEIGHT / 2 - 8, 16, 16);
		}
		if (MegaShoot >= -24 && MegaShoot < 0) {
			bg.setColor(Color.WHITE);
		} else {
			bg.setColor(Color.RED);
		}
		if(MegaShoot < -24) {
			bg.setColor(Color.CYAN);
		}

		if (!myShip.isdead()) {
			bg.fillOval(WIDTH / 2 - 5, HEIGHT / 2 - 5, 10, 10);
		}
		if(myShip.isdead()) {
			bg.setColor(Color.white);
			bg.drawString("Game_Over", WIDTH/2 - 90, HEIGHT/4 + 200);
		}
		g.drawImage(offscreen, 0, 0, null);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT && CanShoot <= 0) {
			allBullets.add(new Bullet(myShip.getDirection() - 0.2));
			allBullets.add(new Bullet(myShip.getDirection() - 0.1));
			allBullets.add(new Bullet(myShip.getDirection()));
			allBullets.add(new Bullet(myShip.getDirection() + 0.1));
			allBullets.add(new Bullet(myShip.getDirection() + 0.2));
			CanShoot += 15;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE && Shoot == true) {
			Shoot = false;
			allBullets.add(new Bullet(myShip.getDirection()));
		}
		if (e.getKeyCode() == KeyEvent.VK_SLASH && MegaShoot < 0) {
			MegaShoot += 24;
			allMegaBullets.add(new MegaBullet(myShip.getDirection()));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Shoot = true;
			CanShoot--;
			MegaShoot--;
		}
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			Shoot = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SLASH) {
			Shoot = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(30);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!myShip.isdead()) {
				time++;
			}
			if (myShip.Shield < 240) {
				myShip.Shield++;
			}
			// update all objects
			myShip.update();
			if (Math.random() < .08)
				allAsteroids.add(new Asteroid());

			double dx = -Math.sin(myShip.getDirection()) * myShip.getSpeed();
			double dy = Math.cos(myShip.getDirection()) * myShip.getSpeed();
			for (int i = 0; i < allAsteroids.size(); i++) {
				Asteroid a = allAsteroids.get(i);
				a.update(dx, dy);
				if(a.hit(a)) {
					allAsteroids.remove(a);
					allAsteroids.addAll(a.breakApart());
				}
				for (int j = 0; j < allBullets.size(); j++) {
					Bullet b = allBullets.get(j);

					if (a.hit(b)) {
						allAsteroids.remove(a);
						allBullets.remove(b);
						allAsteroids.addAll(a.breakApart());
					}

				}
				for (int mj = 0; mj < allMegaBullets.size(); mj++) {
					MegaBullet m = allMegaBullets.get(mj);

					if (a.hit(m)) {
						allAsteroids.remove(a);
					}

				}
				if (a.hitship()) {

					allAsteroids.remove(a);
					myShip.takedamage();
					if (myShip.justdied())
						allBullets.addAll(myShip.explode());
				}
			}
			for (Bullet b : allBullets) {
				b.update(dx, dy);
			}
			for (MegaBullet m : allMegaBullets) {
				m.update(dx, dy);
			}
			if (myShip.isdead()) {
				this.removeKeyListener(this);
			}

			repaint();
		}

	}

}
