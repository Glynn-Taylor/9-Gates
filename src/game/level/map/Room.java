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
 * Creates a room for use in dungeon generation
 */
package game.level.map;

import java.util.Random;

public class Room {
	private final int Width, Height;
	private int MinimumLength;
	private final int[][] RoomMap;
	private static final int[][] TEMPLATES = { { 1, -66 }, { 2, -65 },
			{ 3, -64 } };

	public Room(int xLimit, int yLimit) {
		Width = xLimit;
		Height = yLimit;
		if (Width > Height) {
			MinimumLength = Width;
		} else {
			MinimumLength = Height;
		}
		RoomMap = new int[Height][Width];
		Random r = new Random();
		int rn = r.nextInt(TEMPLATES.length);
		for (int y = 0; y < Height; y++) {
			for (int x = 0; x < Width; x++) {

				if (x == 0 || y == 0 || y == Height - 1 || x == Width - 1) {
					RoomMap[y][x] = TEMPLATES[rn][0];
				} else {
					RoomMap[y][x] = TEMPLATES[rn][1];
				}
			}
		}

	}

	public Room(int[][] map) {
		Width = map[0].length;
		Height = map.length;
		if (Width > Height) {
			MinimumLength = Width;
		} else {
			MinimumLength = Height;
		}
		RoomMap = map;
	}

	public int getHeight() {
		return Height;
	}

	public int getWidth() {
		return Width;
	}

	public int getMinLength() {
		return MinimumLength;
	}

	public int getIntAt(int y, int x) {
		return RoomMap[y][x];
	}

}
