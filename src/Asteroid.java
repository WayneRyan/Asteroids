import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class Asteroid {
	private double x,y,vX,vY,size;

	public Asteroid(){
		size = 50;
		x = Math.random()*MainClass.WIDTH;
		y = Math.random()*MainClass.HEIGHT;
		vX = Math.random()*5-2.5;
		vY = Math.random()*5-2.5;
		int temp = (int)(Math.random()*4);
		switch(temp){
		case 0 : y=-size; if(vY<0) vY*=-1; break;
		case 1 : x=-size; if(vX<0) vX*=-1; break;
		case 2 : y=MainClass.HEIGHT; if(vY>0) vY*=-1; break;
		case 3 : x=MainClass.WIDTH; if(vX>0) vX*=-1; break;
		}
	}
	
	public boolean hit(Bullet b){
		double dX = size/2+ x - b.getX();
		double dY = size/2+ y - b.getY();
		return Math.sqrt(dX*dX+dY*dY)<size/2;
	}
	
	public List<Asteroid> breakApart(){
		ArrayList<Asteroid> retVal = new ArrayList<Asteroid>();
		if(size<15)return retVal;
		Asteroid a = new Asteroid();
		a.x = this.x;
		a.y = this.y;
		a.size = this.size/2;		
		retVal.add(a);
		 a = new Asteroid();
		a.x = this.x;
		a.y = this.y;
		a.size = this.size/2;		
		retVal.add(a);
		
		return retVal;
	}

	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.drawOval((int)x, (int)y, (int)size, (int)size);
	}
	
	public void update(double dX, double dY){
		x += vX+dX;
		y += vY+dY;
		if(x<-size)x = MainClass.WIDTH;
		if(x>MainClass.WIDTH)x = -size;
		if(y<-size)y = MainClass.HEIGHT;
		if(y>MainClass.HEIGHT)y = -size;
	}
	
	public boolean hitship(){
		double dX = size/2+ x - MainClass.WIDTH/2;
		double dY = size/2+ y - MainClass.HEIGHT/2;
		return Math.sqrt(dX*dX+dY*dY)< size/2+15;
	}
}
