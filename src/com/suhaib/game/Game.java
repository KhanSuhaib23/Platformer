package com.suhaib.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.suhaib.game.entity.mobs.Player;
import com.suhaib.game.graphics.Display;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.input.Keyboard;
import com.suhaib.game.level.Level;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 900 / 3;
	private static final int HEIGHT = WIDTH / 16 * 9;
	private static final int SCALE = 3;

	private int x = 0, y = 24 * 16;
	private final int Y = 9 * 16;

	private boolean running = false;

	private boolean check = true;
	private long base_time = 0;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private Thread thread;
	private JFrame frame;

	private Display display;
	private Keyboard key;
	private Level level;
	private Player player;

	public Game() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		frame = new JFrame();
		display = new Display(WIDTH, HEIGHT);
		level = new Level("/textures/level.png");
		player = new Player(10 * 16, Y, Sprite.mario, level);
		key = new Keyboard();
		player.initializeKey(key);
		addKeyListener(key);
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	public synchronized void stop() {
		running = true;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void update() {
		key.update();
		player.update();
		
	}

	private void render() {
		BufferStrategy buffer = getBufferStrategy();
		if (buffer == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = buffer.getDrawGraphics();
		display.clear();
		level.render(player.x - WIDTH / 2, Y - (HEIGHT - 2 * 16), display);
		player.render(display);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = display.pixels[i];
		}
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		buffer.show();
	}

	public void run() {
		requestFocus();
		while (running) {
			if (check) {
				base_time = System.nanoTime();
				check = false;
			}
			long curr_time = System.nanoTime();
			long wait = 1_000_000_000 / 60;
			if (curr_time - base_time >= wait) {
				update();
				render();
				check = true;
			}
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setTitle("Mario");
		game.frame.setResizable(false);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}
}
