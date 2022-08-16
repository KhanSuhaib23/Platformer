package com.suhaib.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.suhaib.game.entity.mobs.Player;
import com.suhaib.game.graphics.Display;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.input.UserInput;
import com.suhaib.game.input.UserInputDefinition;
import com.suhaib.game.level.Level;
import com.suhaib.game.render.Window;

public class Game implements Runnable {
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

	private Display display;
	private Level level;
	private Player player;
	private Window window;

	public Game() {
		UserInputDefinition keys = UserInputDefinition.define()
				.map(KeyEvent.VK_A, UserInput.LEFT)
				.map(KeyEvent.VK_S, UserInput.DOWN)
				.map(KeyEvent.VK_D, UserInput.RIGHT)
				.map(KeyEvent.VK_W, UserInput.UP)
				.map(KeyEvent.VK_K, UserInput.RUN)
				.map(KeyEvent.VK_J, UserInput.JUMP)
				.build();

		window = Window.builder()
				.keyListener(keys)
				.title("Super Mario Bros")
				.width(WIDTH)
				.height(HEIGHT)
				.scale(SCALE)
				.build();


		display = new Display(WIDTH, HEIGHT);
		level = new Level("/res/textures/level.png");
		player = new Player(10 * 16, Y, Sprite.mario, level, keys);
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
		player.update();
	}

	private void render() {
		display.clear();
		level.render(player.x - WIDTH / 2, Y - (HEIGHT - 2 * 16), display);
		player.render(display);
		window.display(display.pixels);
	}

	public void run() {
		window.requestFocus();
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

		game.start();
	}
}
