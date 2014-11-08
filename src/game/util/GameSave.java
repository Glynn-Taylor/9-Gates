/*******************************************************************************
 * Copyright (c) 2013 Glynn Taylor.
 * All rights reserved. This program and the accompanying materials, 
 * (excluding imported libraries, such as LWJGL and Slick2D)
 * are made available under the terms of the GNU Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Glynn Taylor - initial API and implementation
 ******************************************************************************/
/*
 * A serializable container for player and map data
 */
package game.util;

import game.level.entities.mobs.Player;
import game.level.map.TileMap;

import java.io.Serializable;

public class GameSave implements Serializable {

	private static final long serialVersionUID = -4900574192775313636L;
	private final int[][][] IntMap = new int[9][][];
	private Player player;
	private TileMap[] WorldMap;

	public void setIntMapData(int[][] map, int i) {
		IntMap[i] = map;
	}

	public int[][] getIntMapData(int i) {
		return IntMap[i];
	}

	/**
	 * @return the world
	 */
	public TileMap[] getWorld() {
		return WorldMap;
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * @param world
	 *            the world to set
	 */
	public void setEntityData(TileMap[] world, Player p) {
		WorldMap = world;
		player = p;

	}

}
