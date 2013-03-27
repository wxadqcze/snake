import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class SnakePanel extends JPanel{
	private int appXPos;
	private int appYPos;
	private int direction = KeyEvent.VK_RIGHT;
	private int[] xpos = new int[200];
	private int[] ypos = new int[200];
	private int bodyLength = 5;
	private boolean gameStatus = false;
	private boolean flag = true;
	private boolean wall = true;
	private boolean collision = true;
	private long startTime = 0;
    private long stopTime = 0;
    private int score = 0;
    private int level = 1;
    private int speed;
    private int radius = 10;
    private Color[] color;
    private int scoreMultiplier = 100;
    private int appleCount;
    private boolean confuse = false;
    private Rectangle head;
    private Color col;
    private JLabel label1;
    private Icon apple;
	Random random = new Random();
	public SnakePanel()
	{
		setBackground(Color.BLACK);
		setSize(300,300);
		setLayout(null);
		setVisible(true);
		xpos[0]= -50;
		ypos[0] = 10;
		setFocusable(true);
		label1 = new JLabel();
		apple = new ImageIcon(getClass().getResource("apple.png"));
		label1 = new JLabel("",apple,SwingConstants.CENTER);
		col = color(getLevel());
		apple();
		score = 0;
		for(int i= 0; i < 5; i++)
			direction(direction);
		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent event)
			{	
				int direction1 = 0;
				if(confuse)
				{
					switch(event.getKeyCode())
				  	{
				  	case KeyEvent.VK_UP:
				  			
				  		event.setKeyCode(KeyEvent.VK_DOWN);
				  	break;
				  	case KeyEvent.VK_DOWN:
				  	
				  		event.setKeyCode(KeyEvent.VK_UP);
				  	break;
				  		case KeyEvent.VK_LEFT:
				  			
				  			event.setKeyCode(KeyEvent.VK_RIGHT);
				  	break;
				  		case KeyEvent.VK_RIGHT:
				  			
				  			event.setKeyCode(KeyEvent.VK_LEFT);
				  	break;
				  	}
				}
				switch(direction)
				{
				case KeyEvent.VK_UP:
					if(event.getKeyCode() != KeyEvent.VK_DOWN)
					{
						direction1 = event.getKeyCode();
					}	
					break;
				case KeyEvent.VK_DOWN:
					if(event.getKeyCode() != KeyEvent.VK_UP)
					{
						direction1 = event.getKeyCode();
					}
						
					break;
				case KeyEvent.VK_LEFT:
					if(event.getKeyCode() != KeyEvent.VK_RIGHT)
					{
						direction1 = event.getKeyCode();
					}
						
					break;
				case KeyEvent.VK_RIGHT:
					if(event.getKeyCode() != KeyEvent.VK_LEFT)
					{
						direction1 = event.getKeyCode();
					}
						
					break;
				default:
					break;
				}
				
				if(direction1 == KeyEvent.VK_LEFT || direction1 == KeyEvent.VK_RIGHT || 
						direction1 == KeyEvent.VK_UP ||direction1 == KeyEvent.VK_DOWN )
				{
					if(flag)
					{
						startTime = System.currentTimeMillis();
						flag = false;
					}
						
					else
					{
						stopTime = System.currentTimeMillis();
						flag = true;
					}
						
					if(Math.abs(stopTime - startTime) > 75-speed*3)
						direction = direction1;
					
				}
			
				
			}
			
		});
	}
	public boolean getWall()
	{
		return wall;
	}
	public int getSpeed()
	{
		return speed;
	}
	public int getDirection()
	{
		return direction;
	}
	public void setDirection(int dir)
	{
		direction = dir;
	}
	public void setSpeed(int a)
	{
		speed = a;
	}
	public int getLevel()
	{
		return level;
	}
	public void setConfuse(boolean con)
	{
		confuse = con;
	}
	public void paintComponent(Graphics g)
	{
		
		try{Thread.sleep(100-speed*5);}catch(Exception e){};
		super.paintComponent(g);
		direction(direction);
		throughWall(wall);
		head = new Rectangle(xpos[0],ypos[0],radius,radius);
		if(eatStuff(appXPos,appYPos,10,10))
		{
			bodyLength++;
			apple();
			if(appleCount == level+4)
			{
				level++;
				score *= 2;
				appleCount = 0;
			}		
		}
		collision(collision);
		if(wall)
		{
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, getWidth(), getHeight());
			
		}
		else
		{
			g.setColor(Color.RED);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		g.setColor(Color.BLACK);
		g.fillRect(10, 10, getWidth()-20, getHeight()-20);
		label1.setBounds(appXPos, appYPos, 10, 10);
		label1.setVisible(true);
		add(label1);	
		g.setColor(Color.PINK);
		g.drawString("Level: "+ level, getWidth()-50, getHeight()-20);
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 12));
		g.drawString("Score: "+ score, getWidth()-80, 20);
		g.setColor(col);
		
		for(int i = 0; i < bodyLength; i++)
		{
			g.fillOval(xpos[i],ypos[i],radius,radius);
		}	
		gameLoop();
	}
	public void direction(int key)
	{
		switch(key)
		{
		case KeyEvent.VK_UP:
			
			for(int i = bodyLength-1; i>0; i--)
			{
				ypos[i] = ypos[i-1];
				xpos[i] = xpos[i-1];
			}
			ypos[0] -= radius;
			break;
		case KeyEvent.VK_DOWN:
			
			for(int i = bodyLength-1; i>0; i--)
			{
				ypos[i] = ypos[i-1];
				xpos[i] = xpos[i-1];
			}
			ypos[0] += radius;
			break;
		case KeyEvent.VK_LEFT:
			
			for(int i = bodyLength-1; i>0; i--)
			{
				xpos[i] = xpos[i-1];
				ypos[i] = ypos[i-1];
			}
			xpos[0] -= radius;
			break;
		case KeyEvent.VK_RIGHT:
			
			for(int i = bodyLength-1; i>0; i--)
			{
				xpos[i] = xpos[i-1];
				ypos[i] = ypos[i-1];
			}
			xpos[0] += radius;
			break;
		}
	}
	public void setScore( int score)
	{
		scoreMultiplier = score;
	}
	public int getScore()
	{
		return scoreMultiplier;
	}
	public void setCollision(boolean col)
	{
		collision = col;
	}
	public boolean getCollsion()
	{
		return collision;
	}
	public void setColo(Color colr)
	{
		col = colr;
	}
	public void apple()
	{
		appXPos = random.nextInt(this.getWidth()-30)+ 10;
		appYPos = random.nextInt(this.getHeight()-70) + 50;
		direction(direction);
		appleCount++;
		score += level*scoreMultiplier;
	}
	public void collision(boolean collide)
	{
		if(collide)
		{
			for(int i = 2; i < bodyLength; i++)
			{
				if(head.intersects(new Rectangle(xpos[i],ypos[i],radius,radius)))
					gameStatus = true;
			
			}
		}
		
	}
	public boolean eatStuff(int x, int y, int width, int height)
	{
		return((head.intersects(new Rectangle(x,y,width,height))));
	}
	public void gameLoop()
	{
		if(!gameStatus)
			repaint();
		else
		{
			try
			{
				File file = new File("data.txt");
				PrintWriter pw = new PrintWriter(new FileWriter( file, true));
				pw.print(score);
				pw.println();
				pw.close();
			}
			catch (IOException e)
			{};
					
			int option = JOptionPane.showConfirmDialog(null, String.format("Your Score: %d\nRestart?", score)
			, "Restart?", JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION)
			{
				new Home();
			}
			else
				System.exit(0);
		}
	}
	public void setWall(boolean lol)
	{
		wall = lol;
	}
	public void setRadius(int r)
	{
		radius = r;
	}
	public int getRadius()
	{
		return radius;
	}
	public Rectangle getHead()
	{
		return head;
	}
	public void throughWall(boolean flag1)
	{
		if(flag1)
		{
			if(xpos[0] <= 0 && direction == KeyEvent.VK_LEFT)
				xpos[0] = getWidth()-20;
			if(xpos[0] >= getWidth()-15 && direction == KeyEvent.VK_RIGHT)
				xpos[0] = 10;
			if(ypos[0] <= 0 && direction == KeyEvent.VK_UP)
				ypos[0] = getHeight()-20;
			if(ypos[0] >= getHeight()-15 && direction == KeyEvent.VK_DOWN)
				ypos[0] = 10;
		}
		else
		{
			if(xpos[0] <= 0 || xpos[0] >= getWidth()-15 || ypos[0] <= 0 || ypos[0] >= getHeight()-15)
				gameStatus = true;
		}
		}

	public Color color(int a)
	{
		color = new Color[21];
		color[0] =  new Color(255, 245, 230);
		color[1] = new Color(238, 232, 170);
		color[2] = new Color(255, 219, 88);
		color[3] = new Color(251, 236, 93);
		color[4] = new Color(255, 186, 0);
		color[5] = new Color(250, 218, 221);
		color[6] = new Color(255, 183, 213);
		color[7] = new Color(234, 84, 128);
		color[8] = new Color(251, 96, 127);
		color[9] = new Color(226, 80, 155);
		color[10] = new Color(255, 0, 56);
		color[11] = new Color(201, 0, 22);
		color[12] = new Color(168, 28, 7);
		color[13] = new Color(128, 0, 0);
		color[14] = new Color(112, 28, 28);
		color[15] = new Color(214, 202, 221);
		color[16] = new Color(224, 176, 255);
		color[17] = new Color(201, 160, 220);
		color[18] = new Color(223, 115, 255);
		color[19] = new Color(153, 85, 187);
		color[20] = new Color(33,100,0,40);
		return color[a];
	}
}
