package com.picksel.component;

import java.util.List;
import java.util.ArrayList;
import java.awt.Graphics;

import com.picksel.component.property.Property;
import com.picksel.asset.Texture;
import com.picksel.renderer.*;
import com.picksel.util.Input;
import com.picksel.util.Camera;

/**
 * Component which draws a single Texture to the screen.
 *
 * @author Noah James Rathman
 */
public final class Sprite implements Component, Renderable {
	private final String ID;
	private List<Property> properties;
	private Color[][] cArray;
	private Bounds bounds;
	private int layer, drawType;
	private boolean visible;

	/**
	 * Creates a new Sprite with the associated Texture.
	 *
	 * @param id Identifier for this Component
	 * @param texture Texture rendered by this Sprite
	 * @param bounds Bounding box of this Component
	 */
	public Sprite(String id, Texture texture, Bounds bounds) {
		this(id, texture.getColorArray(), bounds);
	}

	/**
	 * Creates a new Sprite with the associated 2D Color array.
	 *
	 * @param id Identifier for this Component
	 * @param cArray 2D Color array rendered by this Sprite
	 * @param bounds Bounding box of this Component
	 */
	public Sprite(String id, Color[][] cArray, Bounds bounds) {
		this.cArray = cArray;
		this.bounds = bounds;
		ID					= id;
		layer				= 0;
		visible			= true;
		drawType		= Renderable.STATIC_DRAW;
		properties	= new ArrayList<Property>();
		
		bounds.setSize(cArray.length, cArray[0].length);
	}

	/**
	 * Adds the passed property to this Component.
	 *
	 * @param p New Property
	 */
	public void addProperty(Property p) {
		properties.add(p);
	}

	public void update(float dt, Input in) {
		for(Property p : properties) {
			p.update(dt, in);
		}
	}

	public void render(Renderer renderer, Camera camera) {
		renderer.drawColorArray(
			(int) (bounds.getX() - camera.getX()),
			(int) (bounds.getY() - camera.getY()),
			cArray
		);
	}

	/**
	 * Sets the texture of this Sprite to the passed
	 * Texture.
	 *
	 * @param tex New Sprite texture
	 */
	public void setTexture(Texture tex) {
		setTexture(tex.getColorArray());
	}

	/**
	 * Sets the texture of this Sprite to the passed
	 * 2D Color array.
	 *
	 * @param tex New Sprite texture
	 */
	public void setTexture(Color[][] tex) {
		cArray = tex;
	}

	public String id() {
		return ID;
	}

	public int layer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public boolean visible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int drawType() {
		return drawType;
	}

	public void setDrawType(int drawType) {
		this.drawType = drawType;
	}

	public Bounds bounds() {
		return bounds;
	}
}