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
 * Stores a user created room for use in dungeon 
 generation
 */
package game.level.map;

import java.io.Serializable;

public class Prefab implements Serializable {

	private static final long serialVersionUID = 3722925096104768273L;

	private final int[][] colorRef;

	public Prefab(int xLimit, int yLimit) {
		colorRef = new int[yLimit][xLimit];
		for (int y = 0; y < yLimit; y++) {
			for (int x = 0; x < xLimit; x++) {
				if (x * y != 0 && x != xLimit - 1 && y != yLimit - 1) {
					colorRef[y][x] = -65;

				} else {
					colorRef[y][x] = 5;
				}
			}
		}
	}

	public int GetWidth() {
		return colorRef[0].length;
	}

	public int GetHeight() {
		return colorRef.length;
	}

	public int getColorRef(int x, int y) {
		return colorRef[y][x];
	}

	public int[][] getMap() {
		return colorRef;
	}

	public void setTile(int x, int y, int i) {
		colorRef[y][x] = i;
	}
}
