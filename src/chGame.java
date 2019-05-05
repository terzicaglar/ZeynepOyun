import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


import javax.swing.*;


public class chGame extends JFrame implements MouseListener, ActionListener, Runnable{
	private static chGame game;
	private static JLabel[] l;
	private static int numberOfCards , selectCount, matchCount;
	private static boolean isClosed;
	private static boolean[] selected;
	private static JButton b,b2;
	private static Random r;
	private static ArrayList<Integer> numbers;
	private static Thread t;
	public chGame()
	{
		numberOfCards = 6;
		t = new Thread(this);
		
		setTitle("Oyun");
		//setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(200,50, 1000, 650);
		getContentPane().setBackground(new Color(200,220,220));
		getContentPane().setLayout(null);
		
		init();
		
		for(int i = 0; i < 2*numberOfCards; i++)
		{
			l[i].addMouseListener(this);
			add(l[i]);
		}
		
		b.setBounds(330, 10, 128, 35);
		b.addActionListener(this);
		add(b);	
		b2.setBounds(530, 10, 128, 35);
		b2.addActionListener(this);
		add(b2);	

		
		repaint();
		setVisible(true);
	}
	
	public static void init()
	{
		selectCount = -1;
		matchCount = 0;
		isClosed = false;
		
		selected = new boolean[2*numberOfCards];
		r = new Random();
		l = new JLabel[2*numberOfCards];
		if( isClosed)
			b = new JButton("Açýk Oyun");
		else
			b = new JButton("Kapalý Oyun");
		b2 = new JButton("Yeni Oyun");
		
		for(int i = 0; i < 2*numberOfCards;i++)
			l[i] = new JLabel();
		
		restart();

	}
	
	public static void restart()
	{
		selectCount = -1;
		matchCount = 0;
		isClosed = false;
		
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

			l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + numbers.get(i) + ".png"));
			l[i].setBounds(130+ 200*(i/3), 60+ 200*(i%3), 128, 128);
			l[i].setEnabled(true);
			l[i].setVisible(true);
		}	

	}
	
	public static void main(String args[])
	{
		game = new chGame();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		boolean finished = false;
		boolean stop = false;
		for(int i = 0; i < 2*numberOfCards; i++)
		{
			if(e.getSource() == l[i])
			{
				if(selected[i] != true)
				{
					stop = true;
					l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + numbers.get(i) + "a.png"));

					if(selectCount < 0)
					{
						selectCount = i;
						selected[i] = true;
					}
					else
					{
						
						if(numbers.get(i) == numbers.get(selectCount))
						{
							
							matchCount++;
							if(matchCount < numberOfCards)
							{
								new AePlayWave("c:\\zeynepOyun/woohoo.wav").start();
								l[i].setEnabled(false);
								l[i].setVisible(false);
								l[selectCount].setEnabled(false);
								l[selectCount].setVisible(false);
							}							
							else
							{
								new AePlayWave("c:\\zeynepOyun/applause.wav").start();
								finished = true;
							}


						}
						else
						{
							new AePlayWave("c:\\zeynepOyun/doh.wav").start();
						}
			
						selected[i] = false;
						selected[selectCount] = false;
						if(isClosed)
						{
							l[i].setIcon(new ImageIcon("c:\\zeynepOyun/back.png"));
							l[selectCount].setIcon(new ImageIcon("c:\\zeynepOyun/back.png"));
						}
						else
						{
							l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + numbers.get(i) + ".png"));
							l[selectCount].setIcon(new ImageIcon("c:\\zeynepOyun/" + numbers.get(selectCount) + ".png"));
						}
						
						selectCount = -1;
						
					}
				}

				
				
			}
		}
		if(finished)
			restart();
		if(stop)
		{
			stop = false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b)
		{
			for(int i = 0; i < 2*numberOfCards; i++)
			{
				selected[i] = false;
				selectCount = -1;
			}
			for(int i = 0; i < 2*numberOfCards; i++)
			{
				if(!isClosed)
					l[i].setIcon(new ImageIcon("c:\\zeynepOyun/back.png"));
				else if(selected[i] == false)
				{
					l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + numbers.get(i) + ".png")); 
				}
				else
					l[i].setIcon(new ImageIcon("c:\\zeynepOyun/" + numbers.get(i) + "a.png"));
			}
			if(isClosed)
			{
				b.setText("Kapalý Oyun");
			}
			else
			{
				b.setText("Açýk Oyun");
			}
			isClosed = !isClosed;
		}
		else if(e.getSource() == b2)
		{
			restart();
		}
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread.currentThread();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
