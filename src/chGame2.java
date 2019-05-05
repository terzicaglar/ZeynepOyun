import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


import javax.swing.*;


public class chGame2 extends JFrame implements MouseListener, ActionListener, Runnable{
	private static chGame2 game;
	private static JLabel[] l;
	private static JLabel music,left,right,ref,sp,tingo,barney;
	private static int numberOfCards , selectCount, matchCount,clicked, imageNo,musicNo,totalMusicNumber;
	private static boolean isClosed, mouse,refClicked,finished;
	private static boolean[] selected;
	private static JButton b;
	private static Random r;
	private static ArrayList<Integer> numbers;
	private static Thread t;
	private static MP3 mp3;
	private static String imageFolder;
	public chGame2()
	{
		
		numberOfCards = 6;
		t = new Thread(this);

		setTitle("Oyun");
		//setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(200,5, 1000, 730);
		getContentPane().setBackground(new Color(200,220,220));
		getContentPane().setLayout(null);
		
		init();
		
		for(int i = 0; i < 2*numberOfCards; i++)
		{
			l[i].addMouseListener(this);
			add(l[i]);
		}
		music.addMouseListener(this);
		add(music);
		left.addMouseListener(this);
		add(left);
		right.addMouseListener(this);
		add(right);
		ref.addMouseListener(this);
		add(ref);
		sp.addMouseListener(this);
		add(sp);
		tingo.addMouseListener(this);
		add(tingo);
		barney.addMouseListener(this);
		add(barney);


		
		b.addActionListener(this);
		add(b);	
	
		/*b3.setBounds(610, 10, 178, 35);
		b3.addActionListener(this);
		add(b3);	*/

		
		repaint();
		setVisible(true);
		t.start();
	}
	
	public static void init()
	{
		musicNo = 6;
		try {
			BufferedReader br = new BufferedReader(new FileReader("C:\\zeynepOyun/Sesler/en son ses no.txt"));
			try {
				totalMusicNumber = Integer.parseInt(br.readLine())+1;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imageFolder = "Sesame";
		imageNo = 0;
		isClosed = true;
		selected = new boolean[2*numberOfCards];
		r = new Random();
		l = new JLabel[2*numberOfCards];
		music = new JLabel();
		right = new JLabel();
		left = new JLabel();
		ref = new JLabel();
		sp = new JLabel();
		barney = new JLabel();
		tingo = new JLabel();
		b = new JButton();
		b.setBounds(230, 10, 70, 70);
		if( isClosed)
			b.setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/back3.png"));
		else
			b.setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/back4.png"));

		//b3 = new JButton("Resimleri Deðiþtir");
		
		for(int i = 0; i < 2*numberOfCards;i++)
			l[i] = new JLabel();
		
		restart();

	}
	
	public static void restart()
	{
		

		if(imageNo%3 == 0)
		{
			imageFolder = "Sesame";
		}
		else if( imageNo%3 == 1)
		{
			imageFolder = "South Park";
		}
		else if( imageNo%3 == 2)
		{
			imageFolder = "Hanny";
		}
		mouse = true;
		selectCount = 0;
		matchCount = 0;
		finished = false;
		clicked = -1;
		selectCount = 0;
		matchCount = 0;
		b.setEnabled(true);

		
		for(int i = 0; i < 2*numberOfCards; i++)
		{
			selected[i] = false;
		}
		
		numbers = new ArrayList<Integer>();
		for(int i = 0; i < 2*numberOfCards; i = i+2)
		{
			numbers.add(i/2);
			numbers.add(i/2);
		}
			
		int temp, rand;
		for(int i = 0; i < 1000; i++)
		{
			for(int j = 0; j < 2*numberOfCards;j++)
			{
				temp = numbers.get(j);
				rand = r.nextInt(6);
				numbers.set(j, numbers.get(rand));
				numbers.set(rand,temp);
			}
		}
		
		for(int i = 0; i <2*numberOfCards; i++)
		{		
			if(isClosed)
				l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/back.png"));
			else
				l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/" + numbers.get(i) + ".png"));
			l[i].setBounds(130+ 200*(i/3), 85+ 200*(i%3), 128, 128);
			l[i].setEnabled(true);
			l[i].setVisible(true);
		}	
		music.setIcon(new ImageIcon("c:\\zeynepOyun/m.gif"));
		music.setBounds(467, 617,70, 83);
		left.setIcon(new ImageIcon("c:\\zeynepOyun/left.gif"));
		left.setBounds(405, 637, 50, 28);
		right.setIcon(new ImageIcon("c:\\zeynepOyun/right.gif"));
		right.setBounds(545, 637, 50, 28);
		ref.setIcon(new ImageIcon("c:\\zeynepOyun/ref.png"));
		ref.setBounds(455, 15, 128, 50);
		sp.setIcon(new ImageIcon("c:\\zeynepOyun/sp.png"));
		sp.setBounds(698, 5,75, 75);
		tingo.setIcon(new ImageIcon("c:\\zeynepOyun/tingo.png"));
		tingo.setBounds(610, 5,75, 75);
		barney.setIcon(new ImageIcon("c:\\zeynepOyun/barney.png"));
		barney.setBounds(786, 5,75, 75);

		

		if(imageNo%3 == 0)
		{
			tingo.setIcon(new ImageIcon("c:\\zeynepOyun/tingo2.png"));
		}
		else if( imageNo%3 == 1)
		{
			sp.setIcon(new ImageIcon("c:\\zeynepOyun/sp2.png"));
		}
		else if( imageNo%3 == 2)
		{
			barney.setIcon(new ImageIcon("c:\\zeynepOyun/barney2.png"));
		}

	}
	
	public static void main(String args[])
	{
		game = new chGame2();

		play();
		
		//Thread.currentThread();
		/*do
		{
			try {
					Thread.sleep(209000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new MP3("C:\\zeynepOyun/Sesler/6.mp3");
		}while(true);*/

  

	}
	
	public static void play()
	{

		if(mp3 != null)
		{
			mp3.close();
		}
		mp3 = new MP3("C:\\zeynepOyun/Sesler/" +(musicNo%totalMusicNumber) + ".mp3");

		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == b)
		{
			for(int i = 0; i < 2*numberOfCards; i++)
			{
				selected[i] = false;
				selectCount = 0;
				clicked = -1;
			}
			for(int i = 0; i < 2*numberOfCards; i++)
			{
				if(!isClosed)
					l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/back.png"));
				else 
					l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/" + numbers.get(i) + ".png"));
					
			}
			
			
			if(isClosed)
			{
				b.setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/back4.png"));
			}
			else
			{
				b.setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/back3.png"));
			}
			isClosed = !isClosed;
		}
		/*else if(e.getSource() == b3)
		{
			imageNo++;
			if(imageNo%3 == 0)
			{
				imageFolder = "Sesame";
			}
			else if(imageNo%3 == 1)
			{
				imageFolder = "South Park";
			}
			else if(imageNo%3 == 2)
			{
				imageFolder = "Hanny";
			}
			for(int i = 0; i < 2*numberOfCards; i++)
			{
				if(isClosed)
					l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/back.png"));
				else 
					l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/" + numbers.get(i) + ".png"));
					
			}
		}*/
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getSource() == music)
		{
			
		}
		else if( e.getSource() == right)
		{
			musicNo++;
			play();
		}
		else if( e.getSource() == barney || e.getSource() == sp || e.getSource() == tingo)
		{
		
			sp.setIcon(new ImageIcon("c:\\zeynepOyun/sp.png"));
			barney.setIcon(new ImageIcon("c:\\zeynepOyun/barney.png"));
			tingo.setIcon(new ImageIcon("c:\\zeynepOyun/tingo.png"));
			if(e.getSource() == sp)
			{
				sp.setIcon(new ImageIcon("c:\\zeynepOyun/sp2.png"));
				imageFolder = "South Park";
				imageNo = 1;
			}
			if(e.getSource() == tingo)
			{
				tingo.setIcon(new ImageIcon("c:\\zeynepOyun/tingo2.png"));
				imageFolder = "Sesame";
				imageNo = 0;
			}
			if(e.getSource() == barney)
			{
				barney.setIcon(new ImageIcon("c:\\zeynepOyun/barney2.png"));
				imageFolder = "Hanny";
				imageNo = 2;
			}
			for(int i = 0; i < 2*numberOfCards; i++)
			{

				if(selected[i] == true)
				{
					if(isClosed)
						l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/" + numbers.get(i) + ".png"));
					else
						l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/" + numbers.get(i) + "a.png"));
				}
				else if(isClosed)
					l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/back.png"));
				else
					l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/" + numbers.get(i) + ".png"));
					
			}
			if(!isClosed)
			{
				b.setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/back4.png"));
			}
			else
			{
				b.setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/back3.png"));
			}

		}
		else if( e.getSource() == left)
		{
			musicNo--;
			if(musicNo == -1)
				musicNo = totalMusicNumber;
			play();
			
		}
		else if( e.getSource() == ref)
		{
			restart();
			refClicked = !refClicked;
			if(!refClicked)
				ref.setIcon(new ImageIcon("c:\\zeynepOyun/ref.png"));
			else
				ref.setIcon(new ImageIcon("c:\\zeynepOyun/ref2.png"));
			
		}
		else if(mouse)
		{
			for(int i = 0; i < 2*numberOfCards; i++)
			{
				if(e.getSource() == l[i])
				{
					
					clicked = i;
				}
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run()  {
		// TODO Auto-generated method stub
		
		Thread a = Thread.currentThread();
		int tempClicked = -1;
		while(true)
		{
			
			
			if(clicked > -1 && selected[clicked] == false )
			{
				b.setEnabled(false);
				selectCount ++;
				if(clicked > -1)
				{
					b.setEnabled(false);
					if(isClosed)
						l[clicked].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/" + numbers.get(clicked) + ".png"));
					else
						l[clicked].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/" + numbers.get(clicked) + "a.png"));
				}
				if(clicked > -1)
						selected[clicked] = true;
				if(selectCount == 2)
				{
					if(numbers.get(clicked) == numbers.get(tempClicked))
					{
						matchCount++;
						
						if(matchCount < numberOfCards)
						{
							
							new AePlayWave("c:\\zeynepOyun/" + imageFolder + "/woohoo.wav").start();
							b.setEnabled(false);
							selectCount = 0;
							mouse = false;
							try {
								Thread.sleep(1000);		
								

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(clicked >-1 && tempClicked > -1)
							{
								l[tempClicked].setEnabled(false);
								l[tempClicked].setVisible(false);
								l[clicked].setEnabled(false);
								l[clicked].setVisible(false);
							}
							mouse = true;
							b.setEnabled(true);
							
						}							
						else
						{
							
							new AePlayWave("c:\\zeynepOyun/" + imageFolder + "/applause.wav").start();
							finished = true;
							imageNo++;
								
							try {
								Thread.sleep(1000);								
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}

					}
					else
					{
						new AePlayWave("c:\\zeynepOyun/" + imageFolder + "/doh.wav").start();
						selectCount = 0;
						mouse = false;
						b.setEnabled(false);
						try {
							Thread.sleep(1000);	
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(clicked >-1 && tempClicked > -1)
						{
							selected[clicked] = false;
							selected[tempClicked] = false;
							if(isClosed)
							{
								l[clicked].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/back.png"));
								l[tempClicked].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/back.png"));
							}
							else
							{
								l[clicked].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/" + numbers.get(clicked) + ".png"));
								l[tempClicked].setIcon(new ImageIcon("c:\\zeynepOyun/" + imageFolder + "/" + numbers.get(tempClicked) + ".png"));
							}
						}
						selectCount = 0;
						clicked = -1;
						tempClicked = -1;
						mouse = true;
						b.setEnabled(true);
					}
					
				}
				if(selectCount == 1)
					tempClicked = clicked;
			}
			
			if(finished)
				restart();

		}
		
	}

}
