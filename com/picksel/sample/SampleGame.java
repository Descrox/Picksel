package com.picksel.sample;

import com.picksel.asset.Asset;
import com.picksel.renderer.RenderQueue;
import com.picksel.scene.*;
import com.picksel.Game;

/**
 * Game instance used for testing.
 *
 * @author Noah James Rathman
 */
public final class SampleGame extends Game {
	/**
	 * Creates a new SampleGame.
	 */
	public SampleGame() {
		super("Sample", 400, 350, 2, 60);
	}

	protected void initGame() {
		Scene test = new Scene("testScene", RenderQueue.DEFAULT_SORT);
		Asset.writeObjectData("C:/Users/noahl/Desktop/testScene", test);

		SceneManager.add(test);
		SceneManager.setActiveScene("testScene");
	}

	/**
	 * Temporary main method.
	 *
	 * @param args Unused program arguments
	 */
	public static void main(String[] args) {
		SampleGame game = new SampleGame();
		game.start();
	}
}