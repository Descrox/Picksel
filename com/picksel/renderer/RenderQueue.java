package com.picksel.renderer;

import static com.picksel.renderer.Renderable.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

import com.picksel.util.VersionInfo;
import com.picksel.util.Camera;

/**
 * Orders render calls for a list of Renderable Objects.
 *
 * @author Noah James Rathman
 */
@VersionInfo(
	version				= "1.0",
	releaseDate		= "",
	since					= "1.0",
	contributors	= {"Noah J Rathman"}
)
public final class RenderQueue {
	/** Objects will render in the order they're added. */
	public static final int DEFAULT_SORT = 0;

	/** Objects will render based on their defined Y position. */
	public static final int Y_SORT = 1;

	/** Objects will render based on their defined layer position. */
	public static final int LAYERED_SORT = 2;

	//Class
	private List<Renderable> items;
	private RenderableSorter sorter;
	private int sortType;

	/**
	 * Creates a new RenderQueue with the assigned sort type.
	 *
	 * @param sortType Type of sort used when adding Renderable
	 * Objects
	 */
	public RenderQueue(int sortType) {
		items = new ArrayList<Renderable>();
		sorter = new RenderableSorter(sortType);
		this.sortType = sortType;
	}

	/**
	 * Creates a new RenderQueue which uses the {@link #DEFAULT_SORT}.
	 */
	public RenderQueue() {
		this(DEFAULT_SORT);
	}

	/**
	 * Adds the passed Renderable Object to this RenderQueue. Items
	 * are resorted after calling this.
	 *
	 * @param obj Added Renderable Object
	 */
	public void add(Renderable obj) {
		items.add(obj);
		Collections.sort(items, sorter);
	}

	/**
	 * Removes the first Renderable Object found with the passed ID.
	 *
	 * @param id Target Renderable ID
	 */
	public void remove(String id) {
		Renderable targ = null;
		for(Renderable obj : items) {
			if(obj.id().equals(id)) {
				targ = obj;
				break;
			}
		}

		if(targ != null) {
			items.remove(targ);
			Collections.sort(items, sorter);
		}
	}

	/**
	 * Resorts every item in this Queue.
	 */
	public void update() {
		Collections.sort(items, sorter);
	}

	/**
	 * Renders all visible items in this Queue.
	 *
	 * @param renderer Target Renderer to draw this Queue's
	 * items to.
	 * @param camera Game Camera offset
	 */
	public void render(Renderer renderer, Camera camera) {
		for(Renderable i : items) {
			if(i.visible()) {
				switch(i.drawType()) {
					case DYNAMIC_DRAW:
						DYNAMIC_RENDER(i, renderer, camera);
						break;
					default:
						STATIC_RENDER(i, renderer);
				}
			}
		}
	}

	/**
	 * Gets the sort type of this RenderQueue.
	 *
	 * @return RenderQueue sort type
	 */
	public int getType() {
		return sortType;
	}

	/**
	 * Class defined per RenderQueue which sorts Renderable items based
	 * on the assigned {@code sortType}.
	 *
	 * @author Noah James Rathman
	 */
	@VersionInfo(
	version				= "1.0",
	releaseDate		= "",
	since					= "1.0",
	contributors	= {"Noah J Rathman"}
)
	protected static class RenderableSorter implements Comparator<Renderable> {
		private int sortType;

		/**
		 * Creates a new RenderableSorter with the assigned sort type.
		 *
		 * @param sortType Type of sort used when adding Renderable
	 	 * Objects
		 */
		public RenderableSorter(int sortType) {
			this.sortType = sortType;
		}

		public int compare(Renderable a, Renderable b) {
			switch(sortType) {
				case Y_SORT:
					return (b.bounds().getY() + b.bounds().getHeight()) < 
								 (a.bounds().getY() + a.bounds().getHeight()) ? 1 : -1;

				case LAYERED_SORT:
					return b.layer() < a.layer() ? 1 : b.layer() == a.layer() ? 0 : -1;

				default:
					return 0;
			}
		}
	}
}