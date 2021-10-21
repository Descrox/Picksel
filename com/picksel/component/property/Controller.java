package com.picksel.component.property;

import java.awt.event.KeyEvent;

import com.picksel.component.Bounds;
import com.picksel.component.Component;
import com.picksel.util.Input;

/**
 * Allows a Component to be controlled by key input.<br>
 * <b>Note:</b> a Controller will only listen to
 * {@code WASD} input, and special input must be
 * implemented on an individual basis. Additionally,
 * Components which return {@code null} when calling
 * {@link com.picksel.component.Component#bounds()} will do
 * nothing, making this Property useless.
 *
 * @author Noah James Rathman
 */
public final class Controller extends Property {
	private float speed;

	/**
	 * Creates a new Component Controller.
	 *
	 * @param parent Property Component
	 * @param speed How many steps the Component makes
	 * when moving
	 */
	public Controller(Component parent, float speed) {
		super(parent);
		this.speed	= speed;
	}

	public void update(float dt, Input in) {
		Bounds b = parent.bounds();

		if(b != null) {
			float xMove = 0f, yMove = 0f;

			if(in.isKey(KeyEvent.VK_W)) {
				yMove -= speed;
			}

			if(in.isKey(KeyEvent.VK_S)) {
				yMove += speed;
			}

			if(in.isKey(KeyEvent.VK_A)) {
				xMove -= speed;
			}

			if(in.isKey(KeyEvent.VK_D)) {
				xMove += speed;
			}

			b.move(xMove, yMove);
		}
	}
}