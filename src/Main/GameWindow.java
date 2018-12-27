package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JPanel;

import GameState.GameStateManager;

@SuppressWarnings("serial")
public class GameWindow extends JPanel implements Runnable, KeyListener {
	
	// Maybe be subjected to change 
	
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final int SCALE = 1;
	
	// Thread 
	private Thread thread; 
	private boolean running; 
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	
	// Graphics 
	private BufferedImage image; 
	private Graphics2D g;
	
	// State Manager 
	private GameStateManager gsm; 
	
	public GameWindow() { 
		super();
		setPreferredSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
		isFocusable();
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	private void init() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) g; 
		
		running = true; 
		
		gsm= new GameStateManager(); 
		
	}
	
	public void run() { 
		
		init(); 
		
		long start;
		long elapsed;
		long wait;
		
	
		//	Game LOOP 
		
		while(running ) {
			
			start = System.nanoTime(); 
			
			update();
			draw();
			drawToScreen(); 
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000; 
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void update() {
		gsm.update();
	}
	public void draw() {
		gsm.draw(g);
	}
	public void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent key) {}
	
	
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
	
	

}
