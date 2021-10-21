package com.picksel.scene;

import java.util.List;
import java.util.ArrayList;

import com.picksel.component.Component;
import com.picksel.renderer.*;
import com.picksel.util.Camera;
import com.picksel.util.Input;
import com.picksel.util.Data;

/**
 * A collection of Components which update and render
 * together.
 *
 * @author Noah James Rathman
 */
public class Scene implements Data {

	/**
	 * Creates a new Scene from the passed List of String
	 * data.
	 *
	 * @param data Scene data
	 * @return Scene created from data
	 */
	public static Scene createSceneFromData(List<String> data) {
		Scene s = new Scene();
		s.readData(data);

		return s;
	}

	//Class
	private String id;
	private List<Component> components;
	private RenderQueue renderQueue;
	private Camera camera;

	/**
	 * Creates a new Scene.
	 *
	 * @param id				Scene identifier
	 * @param sortType	Scene RenderQueue sort type
	 */
	public Scene(String id, int sortType) {
		this.id 			= id;
		renderQueue 	= new RenderQueue(sortType);
		components 		= new ArrayList<Component>();
		camera 				= new Camera();
	}

	private Scene() {}

	public List<String> writeData() {
		List<String> data = new ArrayList<String>();
		data.add(id);
		data.add(String.valueOf(renderQueue.getType()));

		return data;
	}

	public void readData(List<String> data) {
		id 					= data.get(0);
		renderQueue = new RenderQueue(Integer.parseInt(data.get(1)));
		components 	= new ArrayList<Component>();
		camera 			= new Camera();
	}

	/**
	 * Adds the passed Component to this Scene.
	 *
	 * @param c New Scene Component
	 */
	public void addComponent(Component c) {
		components.add(c);

		if(c instanceof Renderable r) {
			renderQueue.add(r);
		}
	}

	/**
	 * Updates all Components in this Scene, as well as this
	 * Scene's Camera.
	 *
	 * @param dt The amount of time in seconds the last frame
	 * took to finish
	 * @param in User input found this frame
	 */
	public void update(float dt, Input in) {
		for(Component c : components) {
			c.update(dt, in);
		}

		camera.update();
	}

	/**
	 * Renders all Renderable Components in this Scene using this
	 * Scene's Camera.
	 *
	 * @param renderer Target Renderer to draw this Scene to.
	 */
	public void render(Renderer renderer) {
		renderQueue.render(renderer, camera);
	}

	/**
	 * Gets this Scene's identifier.
	 *
	 * @return Scene ID
	 */
	public String getID() {
		return id;
	}
}