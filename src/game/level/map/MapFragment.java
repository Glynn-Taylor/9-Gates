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
 * Acts as a temporary part of the map for the dungeon 
 generator to work with
 */
package game.level.map;

import java.util.ArrayList;

public class MapFragment {
	private final int Width, Height, StartY, StartX;
	private int RoomWidth = 0, RoomHeight = 0;
	private final int[][] Fragment;

	private final ArrayList<MapFragment> ConnectedTo = new ArrayList<MapFragment>();

	public void addConnected(MapFragment r) {
		ConnectedTo.add(r);
	}

	public boolean isConnectedTo(MapFragment r) {
		for (MapFragment i : ConnectedTo) {
			if (i.equals(r))
				return true;
		}
		return false;
	}

	public MapFragment(int startX, int startY, int width, int height) {
		this.Width = width;
		this.Height = height;
		this.StartX = startX;
		this.StartY = startY;
		Fragment = new int[height][width];
		for (int y = 0; y < Fragment.length; y++) {
			for (int x = 0; x < Fragment[0].length; x++) {
				Fragment[y][x] = 0;
			}
		}
	}

	public void Split(ArrayList<MapFragment> openList) {
		if (Width % 2 == 0) {

			openList.add(new MapFragment(StartX, StartY, Width / 2, Height / 2));
			openList.add(new MapFragment(StartX + Width / 2, StartY, Width / 2,
					Height / 2));
			openList.add(new MapFragment(StartX, StartY + Height / 2,
					Width / 2, Height / 2));
			openList.add(new MapFragment(StartX + Width / 2, StartY + Height
					/ 2, Width / 2, Height / 2));
		}
	}

	public int getWidth() {
		return Width;
	}

	public int getHeight() {
		return Height;
	}

	public void InsertRoom(Room room) {

		for (int y = 0; y < room.getHeight(); y++) {
			for (int x = 0; x < room.getWidth(); x++) {
				int i = room.getMinLength();
				Fragment[y][x] = room.getIntAt(y, x);
			}
		}
		RoomWidth = room.getWidth();
		RoomHeight = room.getHeight();
	}

	public void WriteOntoMap(int[][] map) {
		for (int y = StartY; y < StartY + Height; y++) {
			for (int x = StartX; x < StartX + Width; x++) {
				map[y][x] = Fragment[y - StartY][x - StartX];
			}
		}
	}

	public int getRoomCenterX() {
		return StartX + RoomWidth / 2;
	}

	public int getRoomCenterY() {
		return StartY + RoomHeight / 2;
	}

	public int getDistance(int xPos, int yPos) {
		return (int) Math.sqrt((getRoomCenterX() - xPos) ^ 2
				+ (getRoomCenterY() - yPos) ^ 2);
	}
}
