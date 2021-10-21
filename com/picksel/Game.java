package com.picksel;

import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

import com.picksel.renderer.*;
import com.picksel.scene.SceneManager;
import com.picksel.util.Camera;
import com.picksel.util.VersionInfo;
import com.picksel.util.Input;

/**
 * The base of all Picksel games. This class defines the
 * update and render structure for all Picksel games.
 *
 * @author Noah James Rathman
 */
@VersionInfo(
	version				= "1.0",
	releaseDate		= "",
	since					= "1.0",
	contributors	= {
		"Noah James Rathman"
	}
)
public abstract class Game implements Runnable {
	//Game general information
	private String title;
	private int rX, rY;
	private float scale;

	//Game window information
	private JFrame frame;
	private Canvas canvas;
	private BufferStrategy strategy;

	//Game thread information
	private Thread thread;
	private long startTime = -1L;
	private long frameTime;
	private boolean running = false;

	//Game utility information
	private Renderer renderer;
	private Input in;

	/**
	 * Creates a new Picksel Game with the passed general information.
	 *
	 * @param title		Title displayed on game window
	 * @param rX			The amount of horizontal pixels per vertical pixel
	 * @param rY			The amount of vertical pixels per horizontal pixel
	 * @param scale		The size of each pixel
	 * @param fps			The target amount of frames per second
	 */
	public Game(String title, int rX, int rY, float scale, int fps) {
		this.title = title;
		this.rX = rX;
		this.rY = rY;
		this.scale = scale;
		frameTime = (long) (1E9 / fps);

		Camera.init((int) (rX * scale), (int) (rY * scale));
		renderer = new Renderer(rX, rY, scale);
		in = new Input(scale);

		initWindow();
		initGame();

		if(SceneManager.getActiveScene() == null) {
			frame.dispose();
			throw new RuntimeException("No active Scene set.");
		}
	}

	private void initWindow() {
		final Dimension WINDOW_SIZE = new Dimension((int) (rX * scale), (int) (rY * scale));

		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		canvas = new Canvas();
		canvas.setMinimumSize(WINDOW_SIZE);
		canvas.setPreferredSize(WINDOW_SIZE);
		canvas.setMaximumSize(WINDOW_SIZE);
		canvas.addKeyListener(in);
		canvas.addMouseListener(in);
		canvas.addMouseMotionListener(in);
		canvas.addMouseWheelListener(in);

		frame.add(canvas);
		frame.pack();

		canvas.createBufferStrategy(3);
		strategy = canvas.getBufferStrategy();

		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		frame.setVisible(true);
	}

	/**
	 * Initializes the gameplay components of a Picksel Game.
	 */
	protected abstract void initGame();

	private void update(float dt) {
		SceneManager.getActiveScene().update(dt, in);
		in.update();
	}

	private void render() {
		Graphics g = strategy.getDrawGraphics();

		g.clearRect(0, 0, (int) (rX * scale), (int) (rY * scale));
		renderer.clear();

		SceneManager.getActiveScene().render(renderer);
		g.drawImage(renderer.asImage(), 0, 0, (int) (rX * scale), (int) (rY * scale), null);

		g.dispose();
		strategy.show();
	}

	/**
	 * Starts this Game and its Thread.
	 */
	public synchronized void start() {
		thread = new Thread(this, title);
		startTime = System.nanoTime();
		running = true;

		thread.start();
	}

	/**
	 * Stops this game and attempts to stop its Thread.
	 */
	public synchronized void stop() {
		running = false;

		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The start time of the Game in nanoseconds.
	 * 
	 * @return The System time in nanoseconds when this Game
	 * was started. If the Game hasn't been started yet, this
	 * method returns {@code -1}.
	 */
	public long startTime() {
		return startTime;
	}

	/**
	 * Gets the width of the window in pixels.
	 *
	 * @return Game window width
	 */
	public int getWindowWidth() {
		return (int) (rX * scale);
	}

	/**
	 * Gets the height of the window in pixels.
	 *
	 * @return Game window height
	 */
	public int getWindowHeight() {
		return (int) (rY * scale);
	}

	public final void run() {
		long start = startTime, now = startTime, last = 0, delta = 0;
		int frames = 0;

		while(running) {
			last = now;
			now = System.nanoTime();
			delta += now - last;

			if(delta >= frameTime) {
				update(delta / 1E9f);
				render();
				delta -= frameTime;
				frames++;
			}

			if(now - start >= 1E9) {
				frame.setTitle(title + " | " + frames);
				frames = 0;
				start = now;
			}
		}

		stop();
	}
}