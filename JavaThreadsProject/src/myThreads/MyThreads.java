package myThreads;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyThreads extends JFrame implements ActionListener {
	
	// Define named-constants
	private static final int CANVAS_WIDTH = 750;
	private static final int CANVAS_HEIGHT = 750;
	
	// Attributes of moving object
	private int size = 100; // width and height
	private int rad = 15;
	
	//Object types
	Canvas canvas;
	JButton setRadius;
	JButton clear;
	JButton save;
	
	Integer[] options = {5, 10, 15, 20};
	JComboBox<Integer> dropdown = new JComboBox<>(options);
	
	Thread thread3;
	
	ArrayList<myCircle> myCircles;
	Timer timer;
	Graphics2D g2;
	
	private static final long serialVersionUID = -1989891553971170424L;
	
	// Constructor to setup the GUI components, event handlers, and threads
		
	MyThreads() {
		
		// GUI components and event handlers
		
		canvas= new Canvas();
		addCircle();
		
		setRadius = new JButton("Set Radius");
		setRadius.addActionListener(this);
		
		save=new JButton("SAVE");
		save.addActionListener(this);
		
		clear = new JButton("CLEAR");
		clear.addActionListener(this);
		
		JPanel  pnl1 = new JPanel();
		JPanel  pnl2 = new JPanel();
		
		pnl1.add(setRadius);
		pnl1.add(dropdown);
		pnl2.add(save);
		pnl2.add(clear);
		
		
		this.getContentPane().add(pnl1, BorderLayout.NORTH);
		this.getContentPane().add(canvas, BorderLayout.CENTER);
		this.getContentPane().add(pnl2, BorderLayout.SOUTH);
		
		this.setSize(750, 750);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Inflating Disks");
		this.setVisible(true);
		this.setResizable(true);
		
		
		// Threads
		
				
		Runnable newcircle = new NewCircle("NewCircle");
		Thread thread1= new Thread(newcircle);
		thread1.setName("newcircle");
		System.out.println("Starting newcircle thread...");
		
		thread1.start();
		
		
		System.out.println("Starting thread2...");
		Thread  thread2 = new Thread(canvas);
		thread2.start();
		
		
		Runnable information = new Information("INFORMATION");
		Thread thread3 = new Thread(information);
		thread3.setPriority(Thread.MIN_PRIORITY);
		thread3.setName("information");
		System.out.println("Starting information thread...");
		thread3.start();
		
	}
	
	
	// For thread 2 (paint circles motion)
	@SuppressWarnings("serial")
	// Define inner class Canvas, which is a JPanel used for custom drawing
	class Canvas extends JPanel implements Runnable {
		
		public Canvas() {
			myCircles = new ArrayList<>();
		}
		
		// Draw a circle
		
		public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;
	   	        
	        for (myCircle circle: myCircles) {
	            int x = circle.getX();
	            int y = circle.getY();
	       
	           Ellipse2D ellipse = new Ellipse2D.Double(x-rad, y-rad, 2*rad, 2*rad);
		       g2.fill(ellipse); 
	        }
	    }
	
	    
		@Override
	    public void run() {
			
			while(true) {
				
				collision();
				repaint();								// Refresh the JFrame, callback paintComponent()
				
				try {
					Thread.sleep(100); 					//Time for next repaint
				}
				catch (InterruptedException e) {
					System.out.println("interrupted");
				}
			
			}   
		}
	
	}
	
	
	// Collision with borders check for moving objects and direction update
    
	public void collision() {
        for (myCircle circle: myCircles) {
            int x = circle.getX();
            int y = circle.getY();

            if(x < 0 || x >  CANVAS_WIDTH - size) {
            	circle.reverseX();
            }
            
            if(y < 0 || y > CANVAS_HEIGHT - size) {
            	circle.reverseY();
            }

            circle.move();
        }
    }
	
	
	public void addCircle() {
    
	    int x = (int )(Math.random() * 560);
	    int y = (int )(Math.random() * 360);
	    int velX = -5;
	    int velY = 5;
	    //int rad=10;
	    myCircles.add(new myCircle(x, y, velX, velY, rad));
	}


	public void removeCircles() {
		myCircles.clear();
	}
		
		
	class myCircle implements Serializable {

		private static final long serialVersionUID = 1L;
		
		int x;
	    int y;
	    int velX;
	    int velY;
	    int rad1;

	    public myCircle(int x, int y, int velX, int velY, int rad) {
	        this.x = (int )(Math.random() * 560);
	        this.y = (int )(Math.random() * 360);
	        this.velX = velX;											// Actually, it's displacement on the x axis
	        this.velY = velY;											// Similarly on the y axis
	        this.rad1=rad;
	    }

	    public void move() {
	        x+=velX;
	        y+=velY;
	    }

	    public int getX() {
	        return x;
	    }

	    public int getY() {
	        return y;
	    }

	    public void reverseX() {
	        velX = -velX;
	    }

	    public void reverseY() {
	        velY = -velY;
	    }
	    
	    
	    // "Formatter"
	    @Override
	    public String toString() {
	        return "myCircle{" +
	                "X='" + x + '\'' +
	                ",Y='" + y + '\'' +
	                ",velX='" + velX +'\'' +
	                ", velY='" + velY +'\'' +
	                ", RAD='" + rad1 +'\''+
	                "}\n";
	    }
	}
	
	
	// handler ActionListener
	@Override
	
	public void actionPerformed(ActionEvent ev) {
		
		if (ev.getSource() == clear) {
			
			System.out.println("BUTTON CLEAR  !!!!");
			removeCircles();
			
			rad=15;
			addCircle();
			repaint();
		}
		
		else if (ev.getSource() == save) {
			System.out.println("BUTTON SAVE  !!!!");	
			saveList();
		}
		else if (ev.getSource() == setRadius) {
			System.out.println("Radius Update: Successfully updated radius!!!!");	
			Integer selectedOption = (Integer) dropdown.getSelectedItem();
			rad = selectedOption;
			addCircle();
			repaint();
			
		}
     }
	 
	 
	// The entry main method
	public static void main(String args[]) {
		new MyThreads();
	} 
	 
	 //For thread 3 (print saved list)
	 class Information implements Runnable {
		 
		 private String message;
		 public Information(String message) {
		    this.message = message;
		 }

		 public void run() {
							     
		    while(true) {
		    	try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		    	System.out.println(message);
		       
	 	       readList();
	    
		    }
		 }
	
	 }
	 
	 
	 // For thread 1 (paint new circles)
	 class NewCircle implements Runnable {
			 
		private String message;
		
		public NewCircle(String message) {
			this.message = message;
		}
		
		public void run() {
			
			while(true) {
				
				try {
					Thread.sleep(4000);
				}
				
				catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				
				System.out.println(message);
				      
			    rad =rad +10;
				addCircle();
				canvas.repaint();
				 
			    System.out.println ("myCircles:  " + myCircles.size());
				System.out.println("\nUsing Iterator");
				
				Iterator<MyThreads.myCircle> itr = myCircles.iterator();
				while(itr.hasNext()) {
			    	  MyThreads.myCircle obj = itr.next();
			          System.out.println(obj);
			    }
			
			}
		
		}
	
	 }
	 	 
	 private void saveList() {
		 // TODO Auto-generated method stub
		 try {
			 FileOutputStream writeData = new FileOutputStream("rocknroll.ser");
			 ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
			 
			 writeStream.writeObject(myCircles);
			 writeStream.flush();
			 writeStream.close();
		}
		 catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	 private void readList() {
		 
		 try{
			 FileInputStream readData = new FileInputStream("rocknroll.ser");
			 ObjectInputStream readStream = new ObjectInputStream(readData);
			 
			 @SuppressWarnings("unchecked")
			 ArrayList<myCircle> myCircles2 = (ArrayList<myCircle>) readStream.readObject();
			 
			 readStream.close();
			 
			 System.out.println(myCircles2);  //.toString());
		}
		
		catch (IOException | ClassNotFoundException e) {
		            e.printStackTrace();
		}
		 
	}
		
}