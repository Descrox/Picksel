package com.picksel.renderer;

import com.picksel.component.Bounds;
import com.picksel.util.Camera;

/**
 * Gives classes the ability to define how they're drawn
 * to the screen.
 *
 * @author Noah James Rathman
 */
public interface Renderable {
	/**
	 * Draw type used for Renderables which don't offset
	 * with Camera motion.
	 */
	public static final int STATIC_DRAW = 0;

	/**
	 * Draw type used for Renderables which offset with
	 * Camera motion.
	 */
	public static final int DYNAMIC_DRAW = 1;

	/**
	 * Calls the passed Renderable's {@code render} method
	 * with no Camera offset.
	 *
	 * @param rend Renderable being rendered
	 * @param renderer Target Renderer to draw this Object
	 */
	public static void STATIC_RENDER(Renderable rend, Renderer renderer) {
		rend.render(renderer, new Camera());
	}

	/**
	 * Calls the passed Renderable's {@code render} method
	 * with no Camera offset.
	 *
	 * @param rend Renderable being rendered
	 * @param renderer Target Renderer to draw this Object
	 * @param camera Game Camera offset
	 */
	public static void DYNAMIC_RENDER(Renderable rend, Renderer renderer, Camera camera) {
		rend.render(renderer, camera);
	}

	//Interface Methods

	/**
	 * Gets the ID of this Renderable.
	 *
	 * @return Renderable String ID
	 */
	String id();

	/**
	 * Gets the layer index of this Renderable. Tells the
	 * RenderQueue how to sort this Object by layer.
	 *
	 * @return Renderable layer
	 */
	int layer();

	/**
	 * Sets the layer index of this Renderable to the passed
	 * index.<br>
	 * <b>Note:</b> calling this does not update the
	 * associated RenderQueue.
	 *
	 * @param layer New layer index
	 */
	void setLayer(int layer);

	/**
	 * Determines if this Renderable if rendered or not.
	 *
	 * @return Visibility state of this Renderable
	 */
	boolean visible();

	/**
	 * Sets the visibility of this Renderable to the passed
	 * state.
	 *
	 * @param visible New visibility state
	 */
	void setVisible(boolean visible);

	/**
	 * Gets the bounding box of this Renderable. Tells the
	 * RenderQueue how to sort this Object by Y position.
	 *
	 * @return Renderable bounding box
	 */
	Bounds bounds();

	/**
	 * Returns the draw type of this Renderable. Tells
	 * the RenderQueue how to draw the Renderable.
	 *
	 * @return Renderable draw type
	 */
	int drawType();

	/**
	 * Sets the draw type of this Renderable to the passed
	 * type.
	 *
	 * @param drawType New draw type
	 */
	void setDrawType(int drawType);

	/**
	 * Draws this Renderable Object to the target Renderer.
	 *
	 * @param renderer Target Renderer to draw this Object
	 * to.
	 * @param camera Game Camera offset
	 */
	void render(Renderer renderer, Camera camera);
}