package com.picksel.renderer;

import java.awt.image.BufferedImage;

import com.picksel.util.VersionInfo;

/**
 * Represents an ARGB color value.
 *
 * @author Noah James Rathman
 */
@VersionInfo(
	version				= "1.0",
	releaseDate		= "10/2/2021",
	since					= "1.0",
	contributors	= {"Noah J Rathman"}
)
public final class Color {
	/** Default black Color. */
	public static final Color BLACK = new Color();

	/** Default white Color. */
	public static final Color WHITE = new Color(0xffffff);

	/** Default gray Color. */
	public static final Color GRAY = new Color(0x7f7f7f);

	/** Default red Color. */
	public static final Color RED = new Color(0xff0000);

	/** Default orange Color. */
	public static final Color ORANGE = new Color(0xff7f00);

	/** Default yellow Color. */
	public static final Color YELLOW = new Color(0xffff00);

	/** Yellow-ish green Color. */
	public static final Color YGREEN = new Color(0x7fff00);

	/** Default green Color. */
	public static final Color GREEN = new Color(0x00ff00);

	/** Blue-ish green Color. */
	public static final Color BGREEN = new Color(0x00ff7f);

	/** Default cyan Color. */
	public static final Color CYAN = new Color(0x00ffff);

	/** Default azure Color. */
	public static final Color AZURE = new Color(0x007fff);

	/** Default blue Color. */
	public static final Color BLUE = new Color(0x0000ff);

	/** Default violet Color. */
	public static final Color VIOLET = new Color(0x7f00ff);

	/** Default magenta Color. */
	public static final Color MAGENTA = new Color(0xff00ff);

	/** Default rose Color. */
	public static final Color ROSE = new Color(0xff007f);

	private static int adjustChannel(int val) {
		if(val < 0) {return 0;}
		else if(val > 255) {return 255;}

		return val;
	}

	private static int adjustChannel(float val) {
		return adjustChannel((int) val);
	}

	private static long intToLong(int iHex) {
		String sHex = Integer.toHexString(iHex);
		return Long.decode("0x" + sHex);
	}

	/**
	 * Generates a random solid color.
	 *
	 * @return Random Color
	 */
	public static Color random() {
		return new Color(
			255,
			(int) (Math.random() * 256),
			(int) (Math.random() * 256),
			(int) (Math.random() * 256)
		);
	}

	/**
	 * Converts the passed BufferedImage into a 2D array of
	 * Colors.
	 *
	 * @param img Image being converted
	 * @return 2D array of image pixels
	 */
	public static Color[][] toColorArray(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		Color[][] arr = new Color[w][h];

		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				int rgb = img.getRGB(x, y);
				arr[x][y] = new Color(rgb);
			}
		}

		return arr;
	}

	//Class
	private int a, r, g, b;

	/**
	 * Creates a new Color with all channels set to 0.
	 */
	public Color() {
		a = 0;
		r = 0;
		g = 0;
		b = 0;
	}

	/**
	 * Creates a new Color with its alpha channel set to
	 * {@code 255}.
	 *
	 * @param r Red channel value
	 * @param g Green channel value
	 * @param b Blue channel value
	 */
	public Color(int r, int g, int b) {
		this.r = adjustChannel(r);
		this.g = adjustChannel(g);
		this.b = adjustChannel(b);
		a = 255;
	}

	/**
	 * Creates a new Color.
	 *
	 * @param a Alpha channel value
	 * @param r Red channel value
	 * @param g Green channel value
	 * @param b Blue channel value
	 */
	public Color(int a, int r, int g, int b) {
		this.a = adjustChannel(a);
		this.r = adjustChannel(r);
		this.g = adjustChannel(g);
		this.b = adjustChannel(b);
	}

	/**
	 * Creates a new Color with the passed integer
	 * hexadecimal code.<br>
	 *
	 * <b>Note:</b> Color expects codes in length equal to
	 * or smaller than {@code 0xffffffff}, or
	 * {@code 32} bytes per channel.
	 *
	 * @param hex Integer hexadecimal code
	 */
	public Color(int hex) {
		this(intToLong(hex));
	}

	/**
	 * Creates a new Color with the passed integer
	 * hexadecimal code.<br>
	 *
	 * <b>Note:</b> Color expects codes in length equal to
	 * or smaller than {@code 0xffffffff}, or
	 * {@code 32} bytes per channel.
	 *
	 * @param hex Integer hexadecimal code
	 */
	public Color(long hex) {
		a = (int) (hex >> 24);
		r = (int) ((hex >> 16) - (a << 8));
		g = (int) ((hex >> 8) - (a << 16) - (r << 8));
		b = (int) (hex - (a << 24) - (r << 16) - (g << 8));
	}

	/**
	 * Blends this Color with the passed Color.<br>
	 *
	 * <b>Note:</b> passed Color is blended without
	 * this Color's alpha in mind.
	 *
	 * @param other Color blending with this
	 * @return Resulting blended Color
	 */
	public Color blend(Color other) {
		int oA = other.getAlpha(),
				oR = other.getRed(),
				oG = other.getGreen(),
				oB = other.getBlue();

		int nA, nR, nG, nB;
		float oRatio = oA / 255f;
		float tRatio = 1f - oRatio;

		nA = adjustChannel(a + oA);
		nR = adjustChannel((r * tRatio) + (oR * oRatio));
		nG = adjustChannel((g * tRatio) + (oG * oRatio));
		nB = adjustChannel((b * tRatio) + (oB * oRatio));

		return new Color(nA, nR, nG, nB);
	}

	/**
	 * Combines this Color's channels into one {@code int} value.
	 *
	 * @return {@code Integer} representation of Color
	 */
	public int intValue() {
		return (int) longValue();
	}

	/**
	 * Combines this Color's channels into one {@code long} value.
	 *
	 * @return {@code Long} representation of Color
	 */
	public long longValue() {
		return (a << 24) + (r << 16) + (g << 8) + b;
	}

	/**
	 * Gets the value of this Color's alpha channel.
	 *
	 * @return Alpha channel value
	 */
	public int getAlpha() {
		return a;
	}

	/**
	 * Gets the value of this Color's red channel.
	 *
	 * @return Red channel value
	 */
	public int getRed() {
		return r;
	}

	/**
	 * Gets the value of this Color's green channel.
	 *
	 * @return Green channel value
	 */
	public int getGreen() {
		return g;
	}

	/**
	 * Gets the value of this Color's blue channel.
	 *
	 * @return Blue channel value
	 */
	public int getBlue() {
		return b;
	}
}