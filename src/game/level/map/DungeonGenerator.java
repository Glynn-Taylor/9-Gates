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
 * Randomly generates an integer dungeon for conversion 
 into map data
 */
package game.level.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;

public class DungeonGenerator {
	private static final int PrefabLimit = 100, WithinLimit = 5,
			RoomConnections = 3;

	private static Room[] Rooms;
	private int[] RoomSizes;
	private static int BiggestRoomSize;
	private static int RemovedTiles = 0;
	private static int SmallestRoomSize;

	public static int[][] Generate(int inX, int inY) {
		int[][] Dungeon;

		System.out.println("GENERATING MAP...");
		GenerateRooms();
		System.out.println("GENERATED ROOMS...");
		do {
			Dungeon = GenerateDungeon(inX, inY);

			System.out.println("CHECKING UNCONNECTED ROOMS...");
			Dungeon = CheckUnconnectedRooms(Dungeon);
		} while (RemovedTiles >= Dungeon[0].length * Dungeon.length / 4);
		return Dungeon;
	}

	private static int[][] CheckUnconnectedRooms(int[][] dungeon) {
		int[][] mapCopy = new int[dungeon.length][dungeon[0].length];
		for (int y = 0; y < mapCopy.length; y++) {
			for (int x = 0; x < mapCopy[0].length; x++) {
				mapCopy[y][x] = dungeon[y][x];
			}
		}
		ArrayList<Point> openList = new ArrayList<Point>();
		openList.add(GetFirstPoint(dungeon));
		mapCopy[openList.get(0).getY()][openList.get(0).getX()] = 0;
		while (!openList.isEmpty()) {
			Point p = openList.get(0);

			if (dungeon[p.getY() + 1][p.getX()] < 0
					&& mapCopy[p.getY() + 1][p.getX()] < 0) {
				openList.add(new Point(p.getX(), p.getY() + 1));
				mapCopy[p.getY() + 1][p.getX()] = 0;
			}
			if (dungeon[p.getY() - 1][p.getX()] < 0
					&& mapCopy[p.getY() - 1][p.getX()] < 0) {
				openList.add(new Point(p.getX(), p.getY() - 1));
				mapCopy[p.getY() - 1][p.getX()] = 0;
			}
			if (dungeon[p.getY()][p.getX() + 1] < 0
					&& mapCopy[p.getY()][p.getX() + 1] < 0) {
				openList.add(new Point(p.getX() + 1, p.getY()));
				mapCopy[p.getY()][p.getX() + 1] = 0;
			}
			if (dungeon[p.getY()][p.getX() - 1] < 0
					&& mapCopy[p.getY()][p.getX() - 1] < 0) {
				openList.add(new Point(p.getX() - 1, p.getY()));
				mapCopy[p.getY()][p.getX() - 1] = 0;
			}
			openList.remove(p);
		}

		return TrimExcessRooms(dungeon, mapCopy);
	}

	private static int[][] TrimExcessRooms(int[][] dungeon, int[][] mapCopy) {
		RemovedTiles = 0;
		for (int y = 0; y < mapCopy.length; y++) {
			for (int x = 0; x < mapCopy[0].length; x++) {
				if (mapCopy[y][x] < 0) {
					RemovedTiles++;
					dungeon[y][x] = 0;
					for (int y1 = -1; y1 <= 1; y1++) {
						for (int x1 = -1; x1 <= 1; x1++) {
							if (x1 != 0 || y1 != 0) {
								if (y + y1 * 2 >= 0
										&& y + y1 * 2 < dungeon.length
										&& x + x1 * 2 >= 0
										&& x + x1 * 2 < dungeon[0].length) {
									if (dungeon[y + y1 * 2][x + x1] <= 0
											&& dungeon[y + y1][x + x1 * 2] <= 0)
										dungeon[y + y1][x + x1] = 0;
								}
							}
						}
					}

				}

			}
		}
		// Second pass
		for (int y = 0; y < dungeon.length; y++) {
			for (int x = 0; x < dungeon[0].length; x++) {
				if (dungeon[y][x] > 0) {
					boolean FloorNearby = false;
					for (int y1 = -1; y1 <= 1; y1++) {
						for (int x1 = -1; x1 <= 1; x1++) {
							if (x1 != 0 || y1 != 0) {
								if (y + y1 >= 0 && y + y1 < dungeon.length
										&& x + x1 >= 0
										&& x + x1 < dungeon[0].length) {
									if (dungeon[y + y1][x + x1] < 0) {
										FloorNearby = true;
									}

								}
							}
						}
					}
					if (!FloorNearby)
						dungeon[y][x] = 0;

				}
			}
		}
		return dungeon;
	}

	private static Point GetFirstPoint(int[][] dungeon) {
		for (int y = 0; y < dungeon.length; y++) {
			for (int x = 0; x < dungeon[0].length; x++) {
				if (dungeon[y][x] < 0)
					return new Point(x, y);
			}
		}
		return null;
	}

	private static int[][] GenerateDungeon(int inX, int inY) {
		ArrayList<MapFragment> openList = new ArrayList<MapFragment>();
		ArrayList<MapFragment> closedList = new ArrayList<MapFragment>();
		MapFragment map = new MapFragment(0, 0, inX, inY);
		Random r = new Random();
		openList.add(map);

		while (openList.size() > 0) {
			MapFragment Quad = openList.get(0);
			if (Quad.getHeight() > SmallestRoomSize
					&& Quad.getWidth() > SmallestRoomSize) {
				ArrayList<Room> eligibleRooms = GetRoomsLessThan(
						Quad.getHeight(), WithinLimit);

				if (r.nextInt(Rooms.length) < eligibleRooms.size()
						&& eligibleRooms.size() > 0) {
					Quad.InsertRoom(eligibleRooms.get(r.nextInt(eligibleRooms
							.size())));
					closedList.add(Quad);
					openList.remove(Quad);
				} else if (Quad.getWidth() / 2 > SmallestRoomSize) {
					Quad.Split(openList);
					openList.remove(Quad);
				} else {
					Quad.InsertRoom(eligibleRooms.get(r.nextInt(eligibleRooms
							.size())));
					closedList.add(Quad);
					openList.remove(Quad);
				}
			} else {

				closedList.add(Quad);
				openList.remove(0);
			}

		}
		int[][] finalisedMap = new int[inY][inX];
		for (MapFragment mapPiece : closedList) {
			mapPiece.WriteOntoMap(finalisedMap);
		}

		for (int i = 0; i < RoomConnections; i++) {
			BuildCorridors(finalisedMap, closedList);
		}

		return finalisedMap;
	}

	private static ArrayList<Room> GetRoomsLessThan(int i, int within) {
		ArrayList<Room> eligibleRooms = new ArrayList<Room>();
		for (int i2 = 0; i2 < Rooms.length; i2++) {
			if (Rooms[i2].getMinLength() < i
					&& Rooms[i2].getMinLength() > i - within) {
				eligibleRooms.add(Rooms[i2]);

			}
		}
		return eligibleRooms;
	}

	private static void GenerateRooms() {
		JFileChooser chooser = new JFileChooser();
		File file = new File(chooser.getFileSystemView()
				.getDefaultDirectory().toString()
				+ "\\9Gates");
		if (!file.exists())
			file.mkdirs();
		File[] playerFiles = new File(chooser.getFileSystemView()
				.getDefaultDirectory().toString()
				+ "\\9Gates").listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".prefab");
			}
		});
		Room[] CustomRooms = getCustomRooms(playerFiles);
		Rooms = new Room[PrefabLimit + CustomRooms.length];
		Random r = new Random();
		int greatestRoom = 0;
		int smallestRoom = -1;
		for (int i = 0; i < PrefabLimit; i++) {
			Rooms[i] = new Room(r.nextInt(10) + 5, r.nextInt(10) + 5);
			if (i == 0) {
				smallestRoom = Rooms[i].getMinLength();
			}
			if (Rooms[i].getMinLength() > greatestRoom) {
				greatestRoom = Rooms[i].getMinLength();
			}
			if (Rooms[i].getMinLength() < smallestRoom) {
				smallestRoom = Rooms[i].getMinLength();
			}

		}
		for (int i = PrefabLimit; i < Rooms.length; i++) {
			Rooms[i] = CustomRooms[i - PrefabLimit];
			if (Rooms[i].getMinLength() > greatestRoom) {
				greatestRoom = Rooms[i].getMinLength();
			}
			if (Rooms[i].getMinLength() < smallestRoom) {
				smallestRoom = Rooms[i].getMinLength();
			}
		}
		BiggestRoomSize = greatestRoom;
		SmallestRoomSize = smallestRoom;

	}

	private static Room[] getCustomRooms(File[] playerFiles) {
		Room[] ra = new Room[playerFiles.length];
		File f;
		FileInputStream fileIn;
		ObjectInputStream in;
		for (int i = 0; i < ra.length; i++) {

			f = playerFiles[i];

			try {
				fileIn = new FileInputStream(f);
				in = new ObjectInputStream(fileIn);
				Prefab p = (Prefab) in.readObject();
				ra[i] = new Room(p.getMap());
				in.close();
				fileIn.close();
			} catch (Exception e) {

			}

		}

		return ra;
	}

	private static void BuildCorridors(int[][] finalisedMap,
			ArrayList<MapFragment> closedList) {
		int distance = 0;
		MapFragment roomRef = null;
		for (MapFragment mapPiece : closedList) {
			for (MapFragment mapPiece2 : closedList) {
				if (!mapPiece.equals(mapPiece2)) {
					if (distance == 0 && !mapPiece2.isConnectedTo(mapPiece)) {
						distance = mapPiece2.getDistance(
								mapPiece.getRoomCenterX(),
								mapPiece.getRoomCenterY());
						roomRef = mapPiece2;
					} else if (mapPiece2.getDistance(mapPiece.getRoomCenterX(),
							mapPiece.getRoomCenterY()) < distance
							&& !mapPiece2.isConnectedTo(mapPiece)) {
						distance = mapPiece2.getDistance(
								mapPiece.getRoomCenterX(),
								mapPiece.getRoomCenterY());
						roomRef = mapPiece2;
					}
				}

			}
			if (distance > 0) {
				int stepAmount = 1;
				if (mapPiece.getRoomCenterX() > roomRef.getRoomCenterX()) {
					stepAmount = -1;
				}

				for (int x = mapPiece.getRoomCenterX(); x != roomRef
						.getRoomCenterX(); x += stepAmount) {
					if (finalisedMap[mapPiece.getRoomCenterY()][x] >= 0)
						finalisedMap[mapPiece.getRoomCenterY()][x] = -64;
					if (finalisedMap[mapPiece.getRoomCenterY() + 1][x] == 0) {
						finalisedMap[mapPiece.getRoomCenterY() + 1][x] = 5;
					}
					if (finalisedMap[mapPiece.getRoomCenterY() - 1][x] == 0) {
						finalisedMap[mapPiece.getRoomCenterY() - 1][x] = 5;
					}
				}
				stepAmount = 1;
				if (mapPiece.getRoomCenterY() > roomRef.getRoomCenterY()) {
					stepAmount = -1;
				}
				for (int y = mapPiece.getRoomCenterY(); y != roomRef
						.getRoomCenterY(); y += stepAmount) {
					if (finalisedMap[y][roomRef.getRoomCenterX()] >= 0)
						finalisedMap[y][roomRef.getRoomCenterX()] = -64;
					if (finalisedMap[y][roomRef.getRoomCenterX() + 1] == 0) {
						finalisedMap[y][roomRef.getRoomCenterX() + 1] = 5;
					}
					if (finalisedMap[y][roomRef.getRoomCenterX() - 1] == 0) {
						finalisedMap[y][roomRef.getRoomCenterX() - 1] = 5;
					}
				}
				distance = 0;
				mapPiece.addConnected(roomRef);
				roomRef.addConnected(mapPiece);
			}
		}
	}

}
