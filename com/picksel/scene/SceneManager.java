package com.picksel.scene;

import java.util.Map;
import java.util.HashMap;

/**
 * Manages every Scene in a Picksel Game.<br>
 *
 * <b>Note:</b> this class is a Singleton, an so
 * only one instance of it exists per JVM.
 *
 * @author Noah James Rathman
 */
public final class SceneManager {
	private static SceneManager INSTANCE;

	/**
	 * Gets this instance of the SceneManager.
	 *
	 * @return SceneManager instance
	 */
	public static SceneManager get() {
		if(INSTANCE == null) {
			INSTANCE = new SceneManager();
		}

		return INSTANCE;
	}

	/**
	 * Adds the passed Scene to the SceneManager.
	 *
	 * @param s Added Scene
	 */
	public static void add(Scene s) {
		get().scenes.put(s.getID(), s);
	}

	/**
	 * Sets the active Scene of the SceneManager to
	 * the Scene with the passed {@code id}.
	 *
	 * <b>Note:</b> if no Scene has the passed
	 * {@code id}, the active Scene is set to
	 * {@code null}.
	 *
	 * @param id Scene identifier
	 */
	public static void setActiveScene(String id) {
		get().activeScene = get().scenes.get(id);
	}

	/**
	 * Gets thie SceneManager's active Scene.
	 *
	 * @return The active Game Scene
	 */
	public static Scene getActiveScene() {
		return get().activeScene;
	}

	//Class
	private Map<String, Scene> scenes;
	private Scene activeScene;

	private SceneManager() {
		scenes = new HashMap<String, Scene>();
		activeScene = null;
	}
}