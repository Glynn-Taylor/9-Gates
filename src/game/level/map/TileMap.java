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
 * Stores and handles map data and interaction, e.g. 
 movement
 */
package game.level.map;

import game.level.entities.furniture.Furniture;
import game.level.entities.furniture.Pillar;
import game.level.entities.furniture.Portal;
import game.level.entities.mobs.Player;
import game.level.entities.mobs.enemies.Lich;
import game.level.entities.pickup.Health;
import game.level.entities.pickup.Pickup;
import game.level.tiles.FloorTile;
import game.level.tiles.MapTile;
import game.pathfinding.NodeMap;
import game.pathfinding.Point;
import game.resources.AudioHandler.AudioPiece;
import game.states.Game.GameState;

import java.io.Serializable;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class TileMap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1122039168147138300L;
	private int VBOVertexHandle;
	private int VBOColorHandle;
	private int VBONormalHandle;
	private int Quads;

	public boolean DebugMode = false;
	public MapTile[][] tiles;
	private NodeMap PathFinder;
	private boolean Loaded = false;
	private int PreviousTeleY, PreviousTeleX, NextTeleY, NextTeleX;

	public void RebuildHandles(int[][] map) {
		int[] handles = TileMapGenerator.RebuildMesh(map, 0);
		VBOVertexHandle = handles[0];
		VBOColorHandle = handles[1];
		VBONormalHandle = handles[2];
	}

	public void Load(boolean endLevel, boolean startLevel, int level) {
		int[] count = TileMapGenerator.CountWalls_Floors();
		Quads = count[0] + count[1] * 6 * 27;
		tiles = new MapTile[TileMapGenerator.Map.length][TileMapGenerator.Map[0].length];
		int[] handles = TileMapGenerator.CreateTemplateMesh(0, 0, 0, 16, 16,
				tiles);
		VBOVertexHandle = handles[0];
		VBOColorHandle = handles[1];
		VBONormalHandle = handles[2];
		Random rGen = new Random();
		for (int y = 2; y < tiles.length; y++) {
			for (int x = 2; x < tiles[0].length; x++) {
				if (tiles[y][x].IsFloor) {
					FloorTile t = (FloorTile) tiles[y][x];
					if (t.GetActiveEntity() == null) {
						if (rGen.nextFloat() > 0.85f) {

							t.AddPickup(new Health());
						} else if (rGen.nextFloat() > 0.96) {
							SpawnMob(x, y, level, rGen);
						} else if (rGen.nextFloat() > 0.98) {
							if (CanPlaceFurniture(x, y))
								SpawnFurniture(x, y, new Pillar());
						}

					}
				}
			}
		}
		SpawnRandomPortals(new Portal(1), new Portal(-1), startLevel, endLevel,
				rGen);
		if (endLevel)
			SpawnLich(rGen);
		PathFinder = new NodeMap();
		PathFinder.GenerateNodeMap(tiles);
		setLoaded(true);
	}

	private void SpawnLich(Random rGen) {
		boolean placed = false;
		while (!placed) {
			for (int y = 2; y < tiles.length; y++) {
				for (int x = 2; x < tiles[0].length; x++) {
					if (!placed && tiles[y][x].IsFloor
							&& rGen.nextInt(100) + 1 > 50) {
						FloorTile t = (FloorTile) tiles[y][x];
						if (t.GetActiveEntity() == null
								&& CanPlaceFurniture(x, y)) {

							if (Math.sqrt((PreviousTeleX - x)
									* (PreviousTeleX - x) + (PreviousTeleY - y)
									* (PreviousTeleY - y)) > 50) {
								t.SetActiveEntity(new Lich());
								placed = true;
							}

						}
					}
				}
			}
		}

	}

	private void SpawnRandomPortals(Portal nextPortal, Portal previousPortal,
			boolean startLevel, boolean endLevel, Random rGen) {
		int portalX = -1, portalY = -1;
		if (!endLevel) {
			while (portalX < 0) {
				for (int y = 2; y < tiles.length; y++) {
					for (int x = 2; x < tiles[0].length; x++) {
						if (portalX < 0 && tiles[y][x].IsFloor
								&& rGen.nextInt(100) + 1 > 90) {
							FloorTile t = (FloorTile) tiles[y][x];
							if (t.GetActiveEntity() == null
									&& CanPlaceFurniture(x, y)) {
								SpawnFurniture(x, y, nextPortal);
								portalX = x;
								portalY = y;
								NextTeleX = x;
								NextTeleY = y;
							}
						}
					}
				}
			}
		}
		boolean placed = false;
		if (!startLevel) {
			while (!placed) {
				for (int y = 2; y < tiles.length; y++) {
					for (int x = 2; x < tiles[0].length; x++) {
						if (!placed && tiles[y][x].IsFloor
								&& rGen.nextInt(100) + 1 > 90) {
							FloorTile t = (FloorTile) tiles[y][x];
							if (t.GetActiveEntity() == null
									&& CanPlaceFurniture(x, y)) {
								if (endLevel) {
									SpawnFurniture(x, y, previousPortal);
									placed = true;
									PreviousTeleX = x;
									PreviousTeleY = y;
								} else {
									if (Math.sqrt((portalX - x) * (portalX - x)
											+ (portalY - y) * (portalY - y)) > 25) {
										SpawnFurniture(x, y, previousPortal);
										placed = true;
										PreviousTeleX = x;
										PreviousTeleY = y;
									}

								}

							}
						}
					}
				}
			}
		}
	}

	private boolean CanPlaceFurniture(int x, int y) {
		boolean valid = true;
		for (int y2 = y - 1; y2 <= y + 1; y2++) {
			for (int x2 = x - 1; x2 <= x + 1; x2++) {
				if (!(y2 == y && x2 == x)) {
					try {
						if (!tiles[y2][x2].IsFloor
								|| ((FloorTile) tiles[y2][x2]).isObstacle())
							valid = false;
					} catch (Exception e) {
						System.out
								.println("Caught a invalid tile @CanPlaceFurniture");
					}
				}
			}
		}
		return valid;
	}

	public void DeleteVBOFromMemory() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(VBOVertexHandle);
		GL15.glDeleteBuffers(VBOColorHandle);
		GL15.glDeleteBuffers(VBONormalHandle);
		VBOVertexHandle = 0;
		VBOColorHandle = 0;
		VBONormalHandle = 0;
	}

	public void Render() {
		GL11.glPushMatrix();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOVertexHandle);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOColorHandle);
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0L);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBONormalHandle);
		GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0L);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		if (DebugMode) {
			GL11.glDrawArrays(GL11.GL_LINE_LOOP, 0, Quads * 16);
		} else {
			GL11.glDrawArrays(GL11.GL_QUADS, 0, Quads * 16);
		}

		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[0].length; x++) {
				if (tiles[y][x].IsFloor) {
					try {
						tiles[y][x].Render(x, y);
					} catch (Exception e) {

					}

				}
			}
		}
		GL11.glPopMatrix();
		// }
	}

	public void DropItem(int x, int y, Pickup p) {
		if (tiles[y][x].IsFloor) {
			FloorTile t = (FloorTile) tiles[y][x];
			if (t != null) {
				if (p != null) {
					t.AddPickup(p);
				} else {
					System.out.println("NULL PICKUP: TILEMAP{DropItem}");
				}
			} else {
				System.out.println("NULL TILE: TILEMAP{DropItem}");
			}

		}
	}

	public Player SpawnPlayer(int x, int y) {
		Player p = new Player(x, y);
		if (tiles[y][x].IsFloor) {
			FloorTile t = (FloorTile) tiles[y][x];
			t.AddActiveEntity(p);
		}
		return p;
	}

	public void SpawnPlayerAtPrevious(Player p) {
		FloorTile t = (FloorTile) tiles[PreviousTeleY][PreviousTeleX + 1];
		t.SetActiveEntity(p);
		p.setX(PreviousTeleX + 1);
		p.setY(PreviousTeleY);
	}

	public void SpawnPlayerAtNext(Player p) {
		FloorTile t = (FloorTile) tiles[NextTeleY][NextTeleX + 1];
		t.SetActiveEntity(p);
		p.setX(NextTeleX + 1);
		p.setY(NextTeleY);
	}

	public Player SpawnPlayer(int x, int y, Player p) {

		if (tiles[y][x].IsFloor) {
			FloorTile t = (FloorTile) tiles[y][x];
			t.AddActiveEntity(p);
		}
		return p;
	}

	public boolean MovePlayer(int oldX, int oldY, int newX, int newY) {
		if (newX < tiles[0].length && newX >= 0 && newY >= 0
				&& newY < tiles.length) {
			if (tiles[newY][newX].IsFloor && tiles[oldY][oldX].IsFloor) {

				FloorTile oldT = (FloorTile) tiles[oldY][oldX];
				FloorTile newT = (FloorTile) tiles[newY][newX];
				int direction = (newX > oldX ? 3 : newX < oldX ? 1
						: newY > oldY ? 0 : newY < oldY ? 2 : 0);
				oldT.GetActiveMob().setFacing(direction);
				if (newT.GetActiveEntity() != null) {
					if (newT.GetActiveEntity().IsMob) {
						return MovePlayerToFloor(oldT, newT, newX, newY);
					} else {
						newT.Interact(oldT.GetActiveEntity());
					}
				} else {
					return MovePlayerToFloor(oldT, newT, newX, newY);
				}

			}
		}
		return false;

	}

	private boolean MovePlayerToFloor(FloorTile oldTile, FloorTile newTile,
			int newX, int newY) {
		if (newTile.AddActiveEntity(oldTile.GetActiveMob())) {

			oldTile.RemoveActiveEntity();

			if (newTile.Pickup((Player) newTile.GetActiveEntity()))
				AudioPiece.PickupEffect.play();

			AudioPiece.PlayerWalkEffect.play();
			return true;
		}
		AudioPiece.EnemyHitEffect.play();
		return false;
	}

	public void SpawnMob(int x, int y, int level, Random r) {

		if (tiles[y][x].IsFloor) {
			FloorTile t = (FloorTile) tiles[y][x];
			try {
				t.AddActiveEntity(TileMapGenerator.getRandomMob(level, r));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

	public void SpawnFurniture(int x, int y, Furniture f) {

		if (tiles[y][x].IsFloor) {
			FloorTile t = (FloorTile) tiles[y][x];
			t.SetActiveEntity(f);
		}

	}

	public void MapUpdate(int pX, int pY) {
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[0].length; x++) {
				if (tiles[y][x].IsFloor) {
					FloorTile t = (FloorTile) tiles[y][x];
					if (t.GetActiveEntity() != null) {
						if (t.GetActiveEntity().IsMob) {
							if (!t.GetActiveMob().IsPlayer) {
								if (!t.GetActiveMob().HasMoved
										&& Math.sqrt((pX - x) * (pX - x)
												+ (pY - y) * (pY - y)) < 10) {
									try {
										Point p = PathFinder.FindPath2(x, y,
												pX, pY);
										if (p != null) {
											FloorTile t2 = (FloorTile) tiles[y][x];
											if (p.Y > y || p.X > x)
												;
											t2.GetActiveMob().HasMoved = true;
											MoveMob(x, y, p.X, p.Y);

										}

									} catch (Exception e) {
										System.out.println(e.getMessage());
										System.out.println("TileMap:205");
									}
								}
							} else {
							}
						}
					}
				}
			}
		}
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[0].length; x++) {
				if (tiles[y][x].IsFloor) {
					FloorTile t = (FloorTile) tiles[y][x];
					if (t.GetActiveEntity() != null
							&& t.GetActiveEntity().IsMob) {
						if (!t.GetActiveMob().IsPlayer) {
							t.GetActiveMob().HasMoved = false;
						}
					}
				}
			}
		}

	}

	public boolean MoveMob(int oldX, int oldY, int newX, int newY) {
		if (newX < tiles[0].length && newX >= 0 && newY >= 0
				&& newY < tiles.length) {
			if (tiles[newY][newX].IsFloor && tiles[oldY][oldX].IsFloor) {

				FloorTile oldT = (FloorTile) tiles[oldY][oldX];
				FloorTile newT = (FloorTile) tiles[newY][newX];
				int direction = (newX > oldX ? 3 : newX < oldX ? 1
						: newY > oldY ? 0 : newY < oldY ? 2 : 0);
				oldT.GetActiveMob().setFacing(direction);
				if (newT.GetActiveEntity() == null
						|| newT.GetActiveEntity().IsMob)
					return MoveMobToFloor(oldT, newT);

			}
		}
		return false;

	}

	private boolean MoveMobToFloor(FloorTile oldTile, FloorTile newTile) {
		if (newTile.AddActiveEntity(oldTile.GetActiveMob())) {
			oldTile.RemoveActiveEntity();

			return true;
		}
		if (newTile.GetActiveMob().IsPlayer)
			AudioPiece.PlayerHitEffect.play();
		return false;
	}

	public void RemovePlayer(int x, int y) {
		if (tiles[y][x].IsFloor) {
			FloorTile t = (FloorTile) tiles[y][x];
			if (t.GetActiveEntity() != null && t.GetActiveEntity().IsMob) {
				if (((Player) t.GetActiveEntity()).IsDead())
					GameState.SwitchToState(GameState.State_SinglePlayer,
							GameState.State_DeathScreen);
				t.RemoveActiveEntity();
			}

		}
	}

	public Player SpawnPlayer() {
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[0].length; x++) {
				if (tiles[y][x].IsFloor) {
					System.out.println("Created new player "
							+ Integer.toString(x) + ":" + Integer.toString(y));
					Player p = new Player(x, y);
					FloorTile t = (FloorTile) tiles[y][x];
					t.AddActiveEntity(p);
					return p;
				}
			}
		}
		return null;

	}

	/**
	 * @return the loaded
	 */
	public boolean isLoaded() {
		return Loaded;
	}

	/**
	 * @param loaded
	 *            the loaded to set
	 */
	private void setLoaded(boolean loaded) {
		Loaded = loaded;
	}

}
