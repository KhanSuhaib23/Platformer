package com.suhaib.game;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.function.UnaryOperator;

import com.suhaib.game.entity.mobs.Player;
import com.suhaib.game.graphics.Animation;
import com.suhaib.game.graphics.Display;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.graphics.sprite.SpriteSheet;
import com.suhaib.game.input.UserInput;
import com.suhaib.game.input.UserInputDefinition;
import com.suhaib.game.level.Level;
import com.suhaib.game.math.RenderPosition;
import com.suhaib.game.math.Vector2;
import com.suhaib.game.render.Camera;
import com.suhaib.game.render.Renderer;
import com.suhaib.game.render.Window;
import com.suhaib.game.resource.*;

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
	private ResourceIndex index;
	private Renderer renderer;
	private Camera camera;

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


		index = ResourceIndex.builder(Constants.META_BASE + "resource.meta")
				.loader(Level.class, new LevelLoader())
				.loader(TileSet.class, new TileSetLoader())
				.loader(SpriteSheet.class, new SpriteSheetLoader())
				.loader(Animation.class, new AnimationLoader())
				.build();
		level = index.load(Level.class, "level_1_1");

		long threshold = level.spawnLocation().worldPosition().y + 2 * 32 * 16;

		UnaryOperator<Long> relu = v -> v <= threshold ? threshold : v;

		camera = new Camera(new RenderPosition(WIDTH, HEIGHT).worldPosition(), v -> new Vector2(v.x, relu.apply(v.y)));

		renderer = new Renderer(display, camera);

		player = new Player(level.spawnLocation().worldPosition(), index.load(Animation.class, "mario_walk"), level, keys);
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
		camera.setCameraFollow(player.position());
		renderer.render(level);
		player.render(renderer);
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
