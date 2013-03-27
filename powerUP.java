import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class powerUP extends SnakePanel {
	private int powerXPos;
	private int powerYPos;
	private int powerUp;
	private boolean effect = false;
	private Timer timer1, timer;
	private long thisTime;
	private long effectTime;
	private JLabel label1;
	private boolean visible;
	private Icon small, rich, fast, wall, ice,confuse, power;
	public powerUP()
	{
		
		super();
		small = new ImageIcon(getClass().getResource("small.png"));
		rich = new ImageIcon(getClass().getResource("ham.png"));
		fast = new ImageIcon(getClass().getResource("fast.png"));
		wall = new ImageIcon(getClass().getResource("wall.png"));
		ice = new ImageIcon(getClass().getResource("ice.png"));
		confuse = new ImageIcon(getClass().getResource("confuse.png"));
		power = new ImageIcon(getClass().getResource("power.png"));
		label1 = new JLabel();
		timer = new Timer();
		timer1 = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
		      public void run() {
		    	  powerUp = random(6); 
		    	  thisTime = System.currentTimeMillis(); 
		    	  switch(powerUp)
		  			{
		  			case 0:
		  				label1 = new JLabel("",small,SwingConstants.CENTER);
		  			break;
		  			case 1:
		  				label1 = new JLabel("",rich,SwingConstants.CENTER);
		  				break;
		  			case 2: 
		  				label1 = new JLabel("",fast,SwingConstants.CENTER);
		  			break;
		  			case 3:
		  				label1 = new JLabel("",wall,SwingConstants.CENTER);
		  			break;
		  			case 4: 	
		  				label1 = new JLabel("",ice,SwingConstants.CENTER);
		  			break;
		  			case 5:
		  				label1 = new JLabel("",confuse,SwingConstants.CENTER);
		  			break;
		  			case 6:
		  				label1 = new JLabel("",power,SwingConstants.CENTER);
		  			break;	
		  		}
		    		
		      }
		    }, 10, 10000);
		
		
		
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if((System.currentTimeMillis() - thisTime)< 5000 && visible)
		{
			label1.setBounds(powerXPos, powerYPos, 10, 10);
			label1.setVisible(visible);
			add(label1);
			
		}
		else
		{
			visible = false;
			powerXPos = 0;
			powerYPos = 0;
			label1.setVisible(visible);
		}
		
		if(eatStuff(powerXPos,powerYPos,getRadius(),getRadius()))
		{
			visible = false;
			if(!effect)
	    	{
				effectTime = System.currentTimeMillis(); 
				
	    	}
			switch(powerUp)
			{
			case 0:
				small();
				break;
			case 1:
				rich();
			case 2: 
				fast();
				break;
			case 3:
				wall();
				break;
			case 4: 	
				freeze();
				break;
			case 5:
				confused();
				break;
			case 6:
				power();
				break;	
			}	
		}
		
	
	}
	public int random(int level)
	{
		if(level > 7)
			level = 7;
		int choice = random.nextInt(level);
		powerXPos = random.nextInt(this.getWidth()-30)+ 10;
		powerYPos = random.nextInt(this.getHeight()-70) + 50;
		visible = true;
		return choice;
	}

	public void small()
	{
		
		if(!effect)
		{
			effect = true;
			
			timer1.scheduleAtFixedRate(new TimerTask() {
			      public void run() {
			    	  int r = getRadius();
			    	  if(r <= 3)
			    		  r = 3;
			    	  else
			    		  r-=.5;
			    	  
			    	  setRadius(r);
			    	  setScore(300);
			    	  setSpeed(10);
			    	  if((System.currentTimeMillis() - effectTime)>= 7000)
			    	  {
			    		 
			    		  r = 10;
			    		  setRadius(r);
			    		  setCollision(false);
			    		  setScore(100);
			    		  setSpeed(getLevel());
			    		  setColo(color(getLevel()));
			    	  }
			    	  if((System.currentTimeMillis() - effectTime)>= 10000)
			    	  {
			    		  effect = false;
			    		  timer1.cancel();
			    		  timer1 = new Timer();
			    	  }
			    	  setCollision(true);
			      }
			    }, 1, 1000);
		}
	}

	public void fast()
{
		
		if(!effect)
		{
			effect = true;
			
			timer1.scheduleAtFixedRate(new TimerTask() {
			      public void run() {
			    	  int sp = 10;
			    	  setScore(500);
			    		  sp+=1;
			    	  setSpeed(sp);
			    	  setColo(new Color(253, 14, 53));
			    	  if((System.currentTimeMillis() - effectTime)>= 5000)
			    	  {
			    		  
			    		  setColo(color(getLevel()));
			    		  setSpeed(getLevel());
			    		 
			    		  setScore(100);
			    	  }
			    	  if((System.currentTimeMillis() - effectTime)>= 10000)
			    	  {
			    		  timer1.cancel();
			    		  effect = false;
			    		  timer1 = new Timer();
			    	  }
			      }
			    }, 1, 1000);
		}
	}
	public void freeze()
	{
		if(!effect)
		{
			effect = true;
			timer1.scheduleAtFixedRate(new TimerTask() {
			      public void run() {
			    	  int sp = -20;
			    	  setScore(500);
			    		  sp-=10;
			    	  setSpeed(sp);
			    	  setColo(new Color(0, 178, 238));
			    	  if((System.currentTimeMillis() - effectTime)>= 5000)
			    	  {
			    		  
			    		  
			    		  setSpeed(getLevel());
			    		  setColo(color(getLevel()));
			    		  setScore(100);
			    	  }
			    	  if((System.currentTimeMillis() - effectTime)>= 10000)
			    	  {
			    		  timer1.cancel();
			    		  effect = false;
			    		  timer1 = new Timer();
			    	  }
			      }
			    }, 1, 1000);
		}
	}
	public void confused()
	{
		if(!effect)
		{
		effect = true;
		timer1.scheduleAtFixedRate(new TimerTask() {
		      public void run() {
		    	  setConfuse(true);
		    	  setScore(750);
		    	  setColo(new Color(186,85,211));
		    	  
		    	  if((System.currentTimeMillis() - effectTime)>= 5000)
		    	  {
		    		  
		    		  setColo(color(getLevel()));
		    		  setConfuse(false);		    		
			    		
			    		setScore(100);
			    		
		    	  }
		    	  if((System.currentTimeMillis() - effectTime)>= 10000)
		    	  {
		    		  timer1.cancel();
		    		  effect = false;
		    		  timer1 = new Timer();
		    	  }
		      }
		    }, 1, 1000);
		}
	}
	public void power()
	{
		if(!effect)
		{
		effect = true;

		timer1.scheduleAtFixedRate(new TimerTask() {
		      public void run() {
		    	  
		    	  setScore(1000);
		    	  setCollision(false);
		    	  setColo(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
		    	  if((System.currentTimeMillis() - effectTime)>= 5000)
		    	  {

		    		    	  setColo(color(getLevel()));

		    	  }
		    	  if((System.currentTimeMillis() - effectTime)>= 7000)
		    	  {
		    		  setCollision(true);		    		
			    	  setScore(100);
			    	  
		    	  }
		    	  if((System.currentTimeMillis() - effectTime)>= 10000)
		    	  {
		    		  timer1.cancel();
		    		  effect = false;
		    		  timer1 = new Timer();
		    	  }
		      }
		    }, 1, 100);
		}
		

	}
	public void wall()
	{
		if(!effect)
		{
		effect = true;
		timer1.scheduleAtFixedRate(new TimerTask() {
		      public void run() {
		    	 effect = true;
					setScore(500);
					setWall(false);
		    	  if((System.currentTimeMillis() - effectTime)>= 5000)
		    	  {
		    		 
		    		 setWall(true);		    		
			    		setScore(100);
			    		
		    	  }
		    	  if((System.currentTimeMillis() - effectTime)>= 10000)
		    	  {
		    		  timer1.cancel();
		    		  effect = false;
		    		  timer1 = new Timer();
		    	  }
		      }
		    }, 1, 1000);
		}
	}
	public void rich()
	{
		if(!effect)
		{
		effect = true;
		timer1.scheduleAtFixedRate(new TimerTask() {
		      public void run() {
		    	 effect = true;
					setScore(500);
					setColo(new Color(255, 127, 80));
		    	  if((System.currentTimeMillis() - effectTime)>= 5000)
		    	  {	    		
		    		  setColo(color(getLevel()));
		    		  setScore(100);	
		    	  }
		    	  if((System.currentTimeMillis() - effectTime)>= 10000)
		    	  {
		    		  timer1.cancel();
		    		  effect = false;
		    		  timer1 = new Timer();
		    		  
		    	  }
		      }
		    }, 1, 1000);
		}
	}

	

}
