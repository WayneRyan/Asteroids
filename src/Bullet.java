import java.awt.Color;
import java.awt.Graphics;


public class Bullet {
	private double x,y,vX,vY;
	private int age;
	private Color myColor;
	
	public Bullet(double directon){
		myColor = Color.red;
		age = 0;
		x = MainClass.WIDTH/2;
		y = MainClass.HEIGHT/2;
		this.vX = Math.sin(directon)*5;
		this.vY = -Math.cos(directon)*5;
		update(0,0);
		update(0,0);
	}
	
	public void setColor(Color newColor){
		myColor = newColor;
	}
	
	public void adjustAge(int offset){
		age += offset;
	}
	
	public void scaleSpeed(double newSpeed){
		this.vX *= newSpeed;
		this.vY *= newSpeed;
	}
	
	public void draw(Graphics g){
		g.setColor(myColor);
		g.fillOval((int)x-5, (int)y-5, 10, 10);
	}
	
	public void update(double dX, double dY){
		x += vX+dX;
		y += vY+dY;
		age++;
	}
	
	public int getAge(){
		return age;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
