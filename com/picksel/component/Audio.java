package com.picksel.component;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import com.picksel.asset.Sound;
import com.picksel.component.property.Property;
import com.picksel.util.Input;
import com.picksel.util.Camera;

/**
 * Component which plays sound.
 * 
 * @author Noah James Rathman
 */
public final class Audio implements Component {
	/**
	 * Causes the Audio to play only once.
	 */
	public static final int PLAY_ONCE			= 0;

	/**
	 * Causes the Audio to play until {@link #pause()} or
	 * {@link close()} is called.
	 */
	public static final int PLAY_LOOPED		= 1;

	//Class
	private final String ID;
	private final Camera CAMERA;
	private Clip clip;
	private Bounds bounds = new Bounds() {
		/**
		 * @param x Amount scaled in the X direction
	 	 * @param y Amount scaled in the Y direction
		 * @deprecated Audios should not have a scale.
		 */
		@Deprecated
		@Override
		public void scale(float x, float y) {}

		/**
		 * @param width Horizontal size
	 	 * @param height Vertical size
		 * @deprecated Audios should not have a scale.
		 */
		@Deprecated
		@Override
		public void setSize(float width, float height) {}
	};
	private boolean global		= false;
	private boolean loop			= false;
	private boolean playing 	= false;
	private boolean closed		= false;
	private float falloff;

	/**
	 * Creates a new spacial Audio Component.<br>
	 *
	 * <b>Note:</b> Audios have special defined Bounds which
	 * deprecate their size.
	 *
	 * @param id 				Identifier for this Component
	 * @param sound 		Sound this Audio uses
	 * @param x 				Horizontal position
	 * @param y 				Vertical position
	 * @param falloff		Distance it takes for sound to become silent
	 * @param camera 		Camera used to calculate spacial volume
	 */
	public Audio(String id, Sound sound, int x, int y, float falloff, Camera camera) {
		this.falloff	= falloff;
		ID 						= id;
		CAMERA 				= camera;
		clip 					= sound.getClip();
		bounds.setPosition(x, y);
	}

	/**
	 * Creates a new global Audio Component.<br>
	 *
	 * <b>Note:</b> Audios have special defined Bounds which
	 * deprecate their size.
	 *
	 * @param id Identifier for this Component
	 * @param sound Sound this Audio uses
	 */
	public Audio(String id, Sound sound) {
		this(id, sound, 0, 0, 1f, null);
		global = true;
	}

	/**
	 * @param p New Component Property
	 * @deprecated Audios currently have no use for
	 * Properties.
	 */
	@Deprecated
	public void addProperty(Property p) {}

	/**
	 * Plays this Audio from the last position it paused at.
	 * If this Audio is already playing, this method sets the
	 * play frame back to {@code 0}.
	 * If this Audio was closed previously, this method
	 * does nothing.<br>
	 *
	 * <b>Note:</b> if this Audio's play style is set to
	 * {@link #PLAY_ONCE} it will automatically close
	 * when it finishes playing.
	 */
	public void play() {
		if(closed) return;
		if(!global) calculateGain();

		if(!playing) {
			playing = true;
		} else {
			clip.setFramePosition(0);
		}
	}

	/**
	 * Paused this Audio at the current position.
	 * If this Audio was closed previously, this method
	 * does nothing.
	 */
	public void pause() {
		if(!closed) playing = false;
	}

	/**
	 * Closes this Audio's Clip. Calling this method is
	 * irreversible, and a new Audio will have to be created
	 * for the associated Sound.
	 */
	public void close() {
		clip.close();
		closed = true;
		playing = false;
	}

	public void update(float dt, Input in) {
		if(playing) {
			if(!global) calculateGain();

			if(!clip.isRunning()) {
				if(loop) {
					clip.setFramePosition(0);
					clip.loop(0);
				} else {
					close();
				}
			}
		}
	}

	/**
	 * Called in {@link #update(float, Input)} when an Audio 
	 * is constructed spacial. Calculates the gain of the
	 * Audio based on the distance from this Audio to the
	 * Camera's center of focus.
	 *
	 * <b>Note:</b> Distance values larger than the
	 * {@code falloff} value will be treated as equal to that
	 * value <i>(a.k.a. muted)</i>.
	 */
	protected void calculateGain() {
		final float GAIN_RANGE = 80f;
		final float DIST_RANGE = falloff;
		FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

		float distance = (float) (Math.sqrt(
			Math.pow(Math.abs(bounds.getX() - CAMERA.getXIgnoreOffset()), 2) +
			Math.pow(Math.abs(bounds.getY() - CAMERA.getYIgnoreOffset()), 2)
		));

		distance = (float) Math.min(falloff, distance);

		float newGain = -((distance * GAIN_RANGE) / DIST_RANGE);
		gain.setValue(newGain);
	}

	/**
	 * Sets this Audio's play style to the passed state.
	 * If the passed style isn't a recognized value,
	 * the style defaults to {@link #PLAY_ONCE}.
	 *
	 * @param style New Audio play style
	 */
	public void setPlayStyle(int style) {
		switch(style) {
			case PLAY_LOOPED:
				loop = true;
				break;

			default:
				loop = false;
				break;
		}
	}

	public String id() {
		return ID;
	}

	public Bounds bounds() {
		return bounds;
	}
}