import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

interface AConstants
{
	int fieldSizex=550, fieldSizey=500;
	int brkLen=53, brkHt=20,brkPosx=9,brkPosy=9;
	int brkGap=0, rowGap=3;
	int radius=30,touch=25;
	int rodjump=10, rodLen=60, rodHt=10, arcLen=5, arcHt=5;
	int noOfRows=4,noOfBricks=10;
}//interface

public class Game2 extends Frame implements AConstants
{
	Ball ballAr[]= new Ball[10];
	int numBalls = 0 ;
	Ball b,b1;
	Graphics gr;
	Rod r;
	Brick brick_obj_array[][];
    boolean touchBallBrick[][];
    Score score_obj;
    boolean ballMove,dirChange,gameOver,gamePause;
    Level level_obj;
    Chance chance_obj;
    int ballspeed=8;

    public void addBall(Ball b)
    {
		ballAr[numBalls++ ] = b;
	}

	public static void main(String args[])
	{
		StartFrame2 srt=new StartFrame2();
	}//main

    static void Start()
    {
		Game2 gm=new Game2();
		Toolkit tk=gm.getToolkit();
		Dimension dim=tk.getScreenSize();
		gm.setBounds(dim.width/4,dim.height/4,fieldSizex,fieldSizey);
		gm.setVisible(true);
		Graphics gr=gm.getGraphics();
		boolean touchBallBrick[][]=new boolean[noOfRows+1][noOfBricks+1];

		Ball b=new Ball(215,400-radius,gr,gm);
		gm.b = b;
		b.show();
		b.move();

		Rod r=new Rod(200,400,gr);
		gm.r=r;
		r.show();

		for(int i=0;i<noOfRows+1;i++)
		{
			for(int j=0;j<noOfBricks+1;j++)
			{
				touchBallBrick[i][j]=false;
			}
		}

		Brick brick_obj_array[][]=new Brick[noOfRows+1][noOfBricks+1];
		for(int i=1;i<=noOfRows;i++)
		{
			for(int j=0;j<noOfBricks;j++)
			{
				Brick brick_obj1=new Brick(brkPosx,brkPosy,i,j,gr);
				brick_obj_array[i][j]=brick_obj1;
				brick_obj_array[i][j].brickShow();
				touchBallBrick[i][j]=false;
			}
		}
		gm.brick_obj_array=brick_obj_array;
		gm.touchBallBrick=touchBallBrick;

		Score score_obj=new Score(gr,gm);
		gm.score_obj=score_obj;

		boolean ballMove=false;
		boolean dirChange=false;
		boolean gameOver=false;
		gm.gamePause=false;

		Level level_obj=new Level(gm,gr);
		gm.level_obj=level_obj;
		level_obj.show();

        Chance chance_obj=new Chance(gr,gm);
		gm.chance_obj=chance_obj;
		chance_obj.show();
		score_obj.show();
	}//start
	public Game2()
	{
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				Frame f=(Frame)we.getSource();
				f.setVisible(false);
			}
		});
		addKeyListener(new KeyAdapter()
					{

						public void keyPressed(KeyEvent ke)
						{

							int k=ke.getKeyCode();
							switch(k)
							{
								case KeyEvent.VK_LEFT:
								int rodposx=r.getRodPosx();
								if(rodposx>=10)
								{
									if(!gameOver)
									{

							  			r.clear();
							  			r.rodposx+=-rodjump;
							  			r.show();
									}//if
						     	}//if
							 	else
							 	rodposx=0;
							 	break;

								case KeyEvent.VK_RIGHT:
								rodposx=r.getRodPosx();
								if(rodposx<=fieldSizex-50)
								{
									if(!gameOver)
									{
										r.clear();
										r.rodposx+=+rodjump;
										r.show();
									}//if
								}//if
							 	else
								rodposx=fieldSizex-50;
								break;

								case KeyEvent.VK_SPACE:
								if(!gameOver)
								{
									r.show();
									if(dirChange==false)
									{
										ballMove=true;
									    b.ydir=-1;
									    dirChange=true;
									}//if
								 }//if
								 break;

								 case KeyEvent.VK_ENTER:
								 if(!gamePause)
								 {
								 	gamePause=true;
								  }
								 else
								 gamePause=false;
								 break;
						}//switch
					}//keypressed
		});
      }
 }//class Game

/*** This is the class Rod ***/
class Rod extends Frame implements AConstants
{
	int rodposx;
	int rodposy,ydir=1;
	Graphics dm_graphics;

	Rod(int rodposx,int rodposy,Graphics local_graphics)
	{
		this.rodposx=rodposx;
		this.rodposy=rodposy;
		dm_graphics=local_graphics;
	}
	public void show()
	{

        dm_graphics.setColor(Color.RED);
        dm_graphics.fillRoundRect(rodposx,rodposy,rodLen,rodHt,arcLen,arcHt);
    }
    public void clear()
    {

		dm_graphics.setColor(Color.WHITE);
        dm_graphics.fillRoundRect(rodposx,rodposy,rodLen,rodHt,arcLen,arcHt);
	}
    int getRodPosx()
    {
		return rodposx;
	}
	int getRodPosy()
	{
		return rodposy;
	}
}//Rod Class

//  This is the brick class
class Brick implements AConstants
{
	int brkposx,brkposy;
	Graphics dm_gr;
	public  Brick(int lo_brkposx,int lo_brkposy,int rowno,int brkno,Graphics local_gr)
	{
		this.brkposx=lo_brkposx+brkno*brkLen;//+brkGap;
		this.brkposy=lo_brkposy+rowno*brkHt;//+rowGap;
		dm_gr=local_gr;
	}
	void brickShow()
	{
		dm_gr.setColor(Color.orange);
		dm_gr.fillRect(brkposx,brkposy,brkLen,brkHt);
		dm_gr.setColor(Color.BLACK);
		dm_gr.drawRect(brkposx,brkposy,brkLen,brkHt);
	}
	void brickCreate(int local_brkposx,int local_brkposy)
	{
		dm_gr.setColor(Color.orange);
		dm_gr.fillRect(local_brkposx,local_brkposy,brkLen,brkHt);
		dm_gr.setColor(Color.BLACK);
		dm_gr.drawRect(local_brkposx,local_brkposy,brkLen,brkHt);
	}
	void brickDisappear(int local_brkposx,int local_brkposy)
	{
		dm_gr.setColor(Color.white);
		dm_gr.drawRect(local_brkposx,local_brkposy,brkLen,brkHt);
		dm_gr.fillRect(local_brkposx,local_brkposy,brkLen,brkHt);
	}
	int getBrickPosx()
	{
		return brkposx;
	}
	int getBrickPosy()
	{
		return brkposy;
	}
}//class Brick

//This is a class Ball having
class Ball implements AConstants
{
	int posx,posy;
	Graphics gm;
	int xdir=1,ydir=1;
	Game2 game_obj;
	Brick brick;
	Ball(int posx,int posy,Graphics gm,Game2 game_obj)
	{
		this.posx=posx;
		this.posy=posy;
		this.gm=gm;
		this.game_obj=game_obj;
	}
	public void move()
	{
		BallMover ballMover = new BallMover();
		Thread t = new Thread(ballMover);
		t.start();
	}
	void show()
	{
		if(game_obj.gameOver==false)
		{
			gm.setColor(Color.blue);
			gm.fillOval(posx,posy,radius,radius);
		}
	}
	void clear()
	{
		gm.setColor(Color.white);
		gm.fillOval(posx,posy,radius,radius);
	}
	void positionChange()
	{
		if(posx<=0 || posx>=fieldSizex-24)
		{
			xdir=-1*xdir;
		}//if
		if(posy<=20 || posy>=fieldSizey-24)
		{
			ydir=-1*ydir;
		}//if
		posx=posx+xdir;
		posy=posy+ydir;
		if(posy>=375)
		{
			if(game_obj.chance_obj.chance>0)
			{
				game_obj.chance_obj.clear();
				game_obj.chance_obj.chanceDec();
				game_obj.chance_obj.show();
				game_obj.ballMove=false;
				game_obj.dirChange=false;
				game_obj.r.clear();
				posx=200;
				posy=370;
				game_obj.r.rodposx=200;
				game_obj.r.rodposy=400;
				game_obj.r.show();
			}//if
			if(game_obj.chance_obj.chance==0)
			{
				game_obj.gameOver=true;
				game_obj.chance_obj.gameOver();
			}
		}//if
	}//positionChange
	int getBallPosx()
	{
		return posx;
	}
	int getBallPosy()
	 {
	    return posy;
	 }

/* Inner class controlling the movements of ball */
	  class BallMover implements Runnable
      {
		boolean move=true;
    	public void run()
		{
			while(true)
			{
				if(game_obj.ballMove==true)
			    {
					if(game_obj.gamePause==false)
					{
				   		try
				   		{

							 Thread.sleep(game_obj.ballspeed);
					 		 touchBallRod();
							 ballbricktouch();
					 		 clear();
					 		 positionChange();
					 		 show();
				  		}//try

						catch(Exception e)
						{
						  System.out.println(e);
						}//catch
					}//if
			   }//if

			}//while
		}//run

		public void touchBallRod()
	 	 {
		  	int currentpos_rod=game_obj.r.getRodPosx();
		  	if((posy+radius)==game_obj.r.getRodPosy())
		  	{
		  		if(posx+radius>=currentpos_rod-arcHt && (posx)<=currentpos_rod+rodLen+arcHt)
		  		{
		  			ydir=-1;
		  		}//if
      		}
      	 }
	   public void ballbricktouch()
	  {
		 int score;
         for(int i=noOfRows;i>0;i--)
         {
			 for(int j=0;j<noOfBricks;j++)
			 {
				 int lo_brkposy=game_obj.brick_obj_array[i][j].getBrickPosy();
				 int lo_brkposx=game_obj.brick_obj_array[i][j].getBrickPosx();
				 if(game_obj.touchBallBrick[i][j]==false)
				 {
					 if((posy+radius)<=lo_brkposy+brkHt+touch&&(posy+radius)>=lo_brkposy)
					 {
						 if((posx+radius)>lo_brkposx&&(posx)<=lo_brkposx+brkLen)
						 {
							 ydir=ydir*-1;
							 game_obj.touchBallBrick[i][j]=true;
							 game_obj.brick_obj_array[i][j].brickDisappear(lo_brkposx,lo_brkposy);
							 game_obj.score_obj.scoreInc();
							 game_obj.score_obj.show();
							 int current_score=game_obj.score_obj.getScore();
							 boolean levelChange=game_obj.score_obj.scoreCheck(noOfRows,noOfBricks);
							 if(levelChange==true)
							 {
								 game_obj.level_obj.levelNew();
								 game_obj.level_obj.show();
							 }
						 }//if
					 }//if
					 else
					 {
						 game_obj.brick_obj_array[i][j].brickCreate(lo_brkposx,lo_brkposy);
					 }
				 }//if
			 }//for
		 }//for
	  }//ballbricktouch
   }//class BallMover
 }//class Ball

  //this is the class Score
class Score
{
	int value;
    Graphics dm_graphic;
    Game2 game_obj;

	//constructor
	public Score(Graphics local_graphic,Game2 game_obj)
	{
		dm_graphic=local_graphic;
		this.game_obj=game_obj;
		value=00;
	}

	public void scoreInc()
	{
		value++;
	}
	public void scoreReset()
	{
		value=00;
	}
	public void scoreShow(int score)
	{
		dm_graphic.setColor(Color.GREEN);
		dm_graphic.drawString("Score:"+score,300,475);
	}
	public int getScore()
	{
		return value;
	}
	public void show()
	{
		int score=value;
		Font f=new Font("Serif",Font.BOLD,20);
		dm_graphic.setColor(Color.WHITE);
		dm_graphic.setFont(f);
		dm_graphic.drawString("Score:"+(value-1),400,450);
		dm_graphic.setColor(Color.RED);
		dm_graphic.drawString("Score:"+value,400,450);
		if(game_obj.gamePause==true)
		{
		   dm_graphic.setColor(Color.RED);
		   dm_graphic.drawString("Game Paused:"+value,200,250);
		}
	}
	public void clear()
	{
		dm_graphic.setColor(Color.WHITE);
		dm_graphic.fillRect(400,450,50,40);
	}
	public void gameOver()
	{
			game_obj.r.clear();
			game_obj.b.clear();
			dm_graphic.setColor(Color.red);
			dm_graphic.drawString("GAME OVER",200,200);
    }//gameOver
    public boolean scoreCheck(int noOfRows,int noOfBricks)
    {
		if(value==(noOfRows*noOfBricks))
		{
			return true;
		}
		else
		return false;
	}
}//class Score

/*** This is the class chance***/
class Chance
{
	int chance;
    Graphics dm_graphic;
    Game2 game_obj;

	//constructor
	public Chance(Graphics local_graphic,Game2 game_obj)
	{
		this.dm_graphic=local_graphic;
		chance=3;
		this.game_obj=game_obj;
	}
	public void chanceDec()
	{
		chance--;
	}
	public void show()
	{
		Font f=new Font("Serif",Font.BOLD,20);
		dm_graphic.setColor(Color.WHITE);
		dm_graphic.setFont(f);
		dm_graphic.drawString("Chance:"+(chance+1),400,470);
		dm_graphic.setColor(Color.RED);
		dm_graphic.drawString("Chance:"+chance,400,470);
	}
	public void clear()
	{
		dm_graphic.setColor(Color.white);
		dm_graphic.fillRect(400,470,100,100);
	}//clear
	public void gameOver()
	{
		Font f=new Font("Serif",Font.BOLD,30);
		dm_graphic.setFont(f);
		game_obj.r.clear();
		game_obj.b.clear();
		dm_graphic.setColor(Color.red);
		dm_graphic.drawString("GAME OVER",200,200);
    }//gameOver
}//class Chance

/*** This is the class Level***/
class Level implements AConstants
{
	Game2 dm_gameobj;
	int levelno;
	Graphics gr;

	//constructor
	Level(Game2 local_gameobj,Graphics gr)
	{
		dm_gameobj=local_gameobj;
		levelno=1;
		this.gr=gr;
	}
	public void show()
	{
		Font f=new Font("Serif",Font.BOLD,20);
		gr.setColor(Color.WHITE);
		gr.setFont(f);
		gr.drawString("Level:"+(levelno-1),50,470);
		gr.setColor(Color.RED);
		gr.drawString("Level:"+levelno,50,470);
	}
	public void levelNew()
	{
        levelno++;

		for(int i=0;i<noOfRows+1;i++)
		 {
			for(int j=0;j<noOfBricks+1;j++)
			{
				 dm_gameobj.touchBallBrick[i][j]=false;

			}
		 }
		 dm_gameobj.b.clear();
		 dm_gameobj.r.clear();
		 dm_gameobj.ballMove=false;
		 dm_gameobj.dirChange=false;
		 dm_gameobj.b.posx=200;
		 dm_gameobj.b.posy=370;
		 dm_gameobj.b.show();
		 dm_gameobj.r.rodposx=200;
		 dm_gameobj.r.rodposy=400;
		 dm_gameobj.r.show();
		 dm_gameobj.score_obj.scoreReset();

		 levelCheck();
	}//levelNew
	void levelCheck()
	{
		switch(levelno)
		{
			case 2:
			dm_gameobj.ballspeed=dm_gameobj.ballspeed-2;
			break;

			case 3:
			Ball b1=new Ball(215,200-radius,gr,dm_gameobj);
			dm_gameobj.b1 = b1;
			b1.show();
		    b1.move();
		    dm_gameobj.ballspeed=dm_gameobj.ballspeed+2;
		    break;
		}
	}//levelCheck
}

class StartFrame2 implements ActionListener
{
	JFrame Strt;
	JButton Start,Quit,Help;

	StartFrame2()
	{
        Font f=new Font("comic sans ms",Font.BOLD,14);
		Strt=new JFrame("Arcanoid");
		Toolkit tk=Strt.getToolkit();
		Dimension dim=tk.getScreenSize();
		JPanel p=new JPanel();
		Start=new JButton("Start");
		Quit=new JButton("Quit");
		Start.setFont(f);
        Quit.setFont(f);
		p.add(Start);
		p.add(Quit);

		Label l=new Label("------------------------------------Welcome to Arcanoid--------------------------------------------------------");
		l.setFont(f);
		Strt.setLayout(new BorderLayout());
		Strt.add(l,BorderLayout.NORTH);
		Strt.add(p,BorderLayout.CENTER);
		JPanel p2=new JPanel();
		p2.setLayout(new GridLayout(3,2));
		Label l2=new Label();
		l2.setText("Click on Start to Play");
		Label l3=new Label("Click on quit to Exit");
		l2.setFont(f);
		l3.setFont(f);
		p2.add(l2);
		p2.add(l3);
		Help=new JButton("HELP");
		p2.add(Help);
		Strt.add(p2,BorderLayout.SOUTH);

		Strt.setBounds(dim.width/4,dim.height/4,dim.width/2,dim.height/2);
		Strt.getContentPane().setBackground(Color.PINK);
		p2.setBackground(Color.PINK);
		Strt.setVisible(true);
	    Start.addActionListener(this);
	    Quit.addActionListener(this);
	    Help.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae)
	{
		JButton jb=(JButton)ae.getSource();
		if(jb==Quit)
		{
			FrameQuit fq=new FrameQuit(this);
		}

		if(jb==Start)
		{

			Game2.Start();
		}

		if(jb==Help)
		{
               HelpFrame hp=new HelpFrame();
		}
	}//action Performed
}//class StartFrame2

class FrameQuit implements ActionListener
{
	JFrame QuitFrame_obj;
    JButton Yes_btn,No_btn;
    StartFrame2 Strtframe_obj;

	FrameQuit(StartFrame2 srt)
	{
		Strtframe_obj=srt;
		Font f=new Font("comic sans ms",Font.BOLD,14);
		QuitFrame_obj=new JFrame();
		Toolkit tk=QuitFrame_obj.getToolkit();
		Dimension dim=tk.getScreenSize();
		QuitFrame_obj.setBounds(dim.width/4,dim.height/4,dim.width/2,dim.height/2);
		QuitFrame_obj.setLayout(new GridLayout(10,1));
        Yes_btn=new JButton("YES");
        No_btn=new JButton("NO");
        JPanel jp=new JPanel();
        jp.add(Yes_btn);
        jp.add(No_btn);
        JPanel jp2=new JPanel();
        Label l=new Label("Are You Sure You Want To Quit");
        l.setFont(f);
        jp2.add(l,BorderLayout.NORTH);
        QuitFrame_obj.add(jp2);
        QuitFrame_obj.add(jp);
        jp2.setBackground(Color.PINK);
        Yes_btn.addActionListener(this);
        No_btn.addActionListener(this);
		QuitFrame_obj.setVisible(true);
	}//Constructor

	public void actionPerformed(ActionEvent ae)
	{
		JButton jb=(JButton)ae.getSource();
		if(jb==Yes_btn)
		{
			QuitFrame_obj.setVisible(false);
			Strtframe_obj.Strt.setVisible(false);
			System.exit(1);
		}
		else
		{
	       QuitFrame_obj.setVisible(false);
		   Strtframe_obj.Strt.setVisible(true);
		}
	}
}//class FrameQuit

class HelpFrame
{
	JFrame helpframe_obj;
	static JTextArea jta;
	static String str="\n";

    HelpFrame()
     {
		 helpframe_obj=new JFrame("HELP");
		 Toolkit tk=helpframe_obj.getToolkit();
		 Dimension dim=tk.getScreenSize();
		 helpframe_obj.setBounds(dim.width/4,dim.height/4,dim.width/2,dim.height/2);
		 helpframe_obj.setVisible(true);
		 jta=new JTextArea();
		 helpframe_obj.add(jta);
		 Font f=new Font("comic sans ms",Font.BOLD,14);
		 jta.setFont(f);
		 jta.setBackground(Color.PINK);
		 readHelpFile();
	 }

	 void readHelpFile()
	 {
         try
         {
			File f=new File("Help.txt");
			Scanner sc=new Scanner(f);
			 while(sc.hasNext())
			     {
				   str=str+sc.nextLine()+"\n";
			     }
				jta.setText(str);
				jta.setEditable(false);
		}//try
 		catch(IOException e){}
	 }//readHelpFile
 }
