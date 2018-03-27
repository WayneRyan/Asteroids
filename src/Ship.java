import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;


public class Ship implements KeyListener{
	
	double direction,speed,health,dV;
	private boolean isDead, leftPressed,rightPressed,upPressed;
	
	public Ship(){
		dV = 0;
		health = 1;
		speed = 0.0;
		direction = Math.PI/4;
	}
	
	public List<Bullet> explode(){
		List<Bullet> retVal = new ArrayList<Bullet>();
		for(int i=0 ; i<1000 ; i++){
			Bullet b = new Bullet(Math.random()*Math.PI*2);
			b.setColor(Color.yellow);
			b.scaleSpeed(Math.random()*2);
			b.adjustAge((int)(Math.random()*50-25));
			retVal.add(b);
		}
		return retVal;
	}
	
	public void takeDamage(){
		health -= 0.1;
	}
	
	public boolean justDied(){
		if(health<0 && !isDead){
			return isDead = true;
		}
		return false;
	}
	
	public boolean isDead(){
		return isDead;
	}

	public void update() {
		if(leftPressed)dV-=0.05;
		if(rightPressed)dV+=0.05;
		direction +=dV;
		dV*=0.85;
		if(upPressed)speed+=2;
		speed*=0.9;
	}

	public void draw(Graphics g) {
		if(health<0)return;
		g.setColor(Color.green);
		g.fillRect(MainClass.WIDTH/2+20, MainClass.HEIGHT/2, 4, (int)(health*40));
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform old,at;
		old = g2.getTransform();
		at = new AffineTransform();
		int centerX = MainClass.WIDTH/2;
		int centerY = MainClass.HEIGHT/2;
		at.translate(centerX, centerY);
		at.rotate(direction);
		at.translate(-centerX, -centerY);
		g2.setTransform(at);
		int[] x = {centerX-10,centerX,centerX+10};
		int[] y = {centerY+12,centerY-15,centerY+12};
		g2.setColor(Color.yellow);
		g2.drawPolygon(x, y, 3);
		g2.setTransform(old);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			leftPressed = true;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			rightPressed = true;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP){
			upPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			leftPressed = false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			rightPressed = false;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP){
			upPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	
	
	public double getDirection() {
		return direction;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	

}
