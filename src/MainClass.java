import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;


public class MainClass extends JFrame implements KeyListener, Runnable{
	
	public static final int HEIGHT = 600;
	public static final int WIDTH = 800;
	
	BufferedImage offscreen;
	Graphics bg;
	
	Ship myShip;
	List<Bullet> allBullets;
	List<Asteroid> allAsteroids;
	private int canShoot;
	private boolean spacePressed;
	private int time;

	public MainClass(){
		spacePressed = false;
		canShoot = 0;
		allBullets = new ArrayList<Bullet>();
		allAsteroids = new ArrayList<Asteroid>();
		myShip = new Ship();
		this.addKeyListener(myShip);
		offscreen = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		bg = offscreen.getGraphics();
		Font f = bg.getFont().deriveFont(30f);
		bg.setFont(f);
		this.addKeyListener(this);
		new Thread(this).start();
	}
	
	public static void main(String[] args) {
		MainClass mc = new MainClass();
		mc.setSize(WIDTH, HEIGHT);
		mc.setResizable(false);
		mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mc.setVisible(true);
	}
	
	public void paint(Graphics g){
		bg.setColor(Color.black);
		bg.fillRect(0,0,WIDTH, HEIGHT);
		// use bg to draw all other stuff
		myShip.draw(bg);
		for(Bullet b : allBullets)b.draw(bg);
		for(Asteroid a : allAsteroids)a.draw(bg);
		bg.setColor(Color.green);
		bg.drawString(""+(time/30), 50, 80);
		if(canShoot==0){
			bg.setColor(Color.green);
		}else if(canShoot<100){
			bg.setColor(Color.yellow);
		}else{
			bg.setColor(Color.red);
		}
		if(!myShip.isDead())bg.fillOval(WIDTH/2-2, HEIGHT/2-2, 4, 4);
		g.drawImage(offscreen, 0, 0, null);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(myShip.isDead())return;
		if(e.getKeyCode()==KeyEvent.VK_SPACE)spacePressed = true;
	}
	
	public void shoot(){
		if(myShip.isDead())return;
		if(canShoot<100){
			canShoot+=10;
			allBullets.add(new Bullet(myShip.getDirection()-0.2));
			allBullets.add(new Bullet(myShip.getDirection()-0.1));
			allBullets.add(new Bullet(myShip.getDirection()));
			allBullets.add(new Bullet(myShip.getDirection()+0.1));
			allBullets.add(new Bullet(myShip.getDirection()+0.2));
		}
		
	}

	
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE)spacePressed = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	
	}

	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(30);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(!myShip.isDead())time++;
			// update all objects
			myShip.update();
			if(Math.random()<.01)allAsteroids.add(new Asteroid());			
			double dX = -Math.sin(myShip.getDirection())*myShip.getSpeed();
			double dY = Math.cos(myShip.getDirection())*myShip.getSpeed();
			for(int i=0 ; i<allAsteroids.size() ; i++){
				Asteroid a = allAsteroids.get(i);
				a.update(dX,dY);
				for(int j=0 ; j<allBullets.size() ; j++){
					Bullet b = allBullets.get(j);
					if(a.hit(b)){
						allAsteroids.remove(a);
						allBullets.remove(b);
						allAsteroids.addAll(a.breakApart());
					}
					if(b.getAge()>45)allBullets.remove(b);
				}
				if(a.hitship()){
					allAsteroids.remove(a);
					myShip.takeDamage();
					if(myShip.justDied())allBullets.addAll(myShip.explode());
				}
			}
			if(canShoot>0)canShoot--;
			if(spacePressed)shoot();
			for(Bullet b : allBullets)b.update(dX,dY);
			repaint();
		}
		
	}

}
