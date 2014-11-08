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
 * Creates VBO data for a TileMap and places entities on 
 creation
 */
package game.level.map;

import game.level.entities.mobs.Mob;
import game.level.entities.mobs.enemies.Bandit;
import game.level.entities.mobs.enemies.Demon;
import game.level.entities.mobs.enemies.Dwarf;
import game.level.entities.mobs.enemies.Ghost;
import game.level.entities.mobs.enemies.Orc;
import game.level.entities.mobs.enemies.Skeleton;
import game.level.entities.mobs.enemies.Vampire;
import game.level.entities.mobs.enemies.Werewolf;
import game.level.entities.mobs.enemies.Zombie;
import game.level.tiles.BlankTile;
import game.level.tiles.FloorTile;
import game.level.tiles.MapTile;
import game.level.tiles.WallTile;
import game.states.Game;

import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

public class TileMapGenerator {
	public static final float CUBE_LENGTH = 2;
	private static final float WALL_SCALAR = 1.5f;
	private static final float DIVISIONS = 3;
	public static int[][] Map = { { 1, 2 } };
	public static Mob[][] LevelMobs = { { new Zombie(), new Skeleton() },
			{ new Zombie(), new Skeleton(), new Bandit() },
			{ new Skeleton(), new Bandit(), new Ghost() },
			{ new Bandit(), new Ghost(), new Orc() },
			{ new Vampire(), new Orc() }, { new Vampire(), new Demon() },
			{ new Demon(), new Dwarf() },
			{ new Dwarf(), new Werewolf(), new Demon() }, { new Werewolf() }

	};

	public static Mob getRandomMob(int level, Random r)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		return (Mob) Class.forName(
				LevelMobs[level][r.nextInt(LevelMobs[level].length)].getClass()
						.getName()).newInstance();

	}

	public static int[] CreateTemplateMesh(int startX, int startY,
			float startZ, int WIDTH, int HEIGHT, MapTile[][] tiles) {
		long startTime;
		long endTime;
		startTime = System.currentTimeMillis();
		Map = DungeonGenerator.Generate(Game.MapLength, Game.MapLength);

		endTime = System.currentTimeMillis();
		System.out.println("Took "
				+ Long.toString((endTime - startTime) / 1000)
				+ " seconds to generate a map.");
		int count[] = CountWalls_Floors();
		int[] Handles = new int[3];
		int VBOVertexHandle = GL15.glGenBuffers();
		int VBOColorHandle = GL15.glGenBuffers();
		int VBONormalHandle = GL15.glGenBuffers();
		Random rGen = new Random();
		startTime = System.currentTimeMillis();
		FloatBuffer VertexPositionData = BufferUtils
				.createFloatBuffer((int) ((count[0] + count[1] * 6 * DIVISIONS
						* DIVISIONS * DIVISIONS) * 4 * 3));
		FloatBuffer VertexColorData = BufferUtils
				.createFloatBuffer((int) ((count[0] + count[1] * 6 * DIVISIONS
						* DIVISIONS * DIVISIONS) * 4 * 3));
		FloatBuffer VertexNormalData = BufferUtils
				.createFloatBuffer((int) ((count[0] + count[1] * 6 * DIVISIONS
						* DIVISIONS * DIVISIONS) * 4 * 3));
		for (float y = 0; y < Map.length; y++) {
			for (float x = 0; x < Map[0].length; x++) {
				if (Map[(int) y][(int) x] > 0) {
					tiles[(int) y][(int) x] = new WallTile();
					for (float x1 = 0; x1 < DIVISIONS; x1++) {
						for (float y1 = 0; y1 < DIVISIONS; y1++) {
							for (float z1 = 0; z1 < DIVISIONS; z1++) {
								if (y1 == 0 || x1 == 0 || z1 == 0
										|| y1 == DIVISIONS - 1
										|| x1 == DIVISIONS - 1
										|| z1 == DIVISIONS - 1) {
									VertexPositionData.put(CreateCube(x
											* CUBE_LENGTH + CUBE_LENGTH
											/ DIVISIONS * x1, y * CUBE_LENGTH
											+ CUBE_LENGTH / DIVISIONS * y1,
											startZ - z1 * WALL_SCALAR,
											CUBE_LENGTH / DIVISIONS));
									GenerateDefaultCubeData(VertexColorData,
											VertexNormalData,
											Map[(int) y][(int) x], rGen);
								}
							}
						}
					}
				} else if (Map[(int) y][(int) x] == 0) {
					tiles[(int) y][(int) x] = new BlankTile();
				} else {
					tiles[(int) y][(int) x] = new FloorTile();
					VertexPositionData.put(CreateFloor(x * CUBE_LENGTH
							+ CUBE_LENGTH / DIVISIONS, y * CUBE_LENGTH
							+ CUBE_LENGTH / DIVISIONS, startZ));
					GenerateDefaultFloorData(VertexColorData, VertexNormalData,
							Map[(int) y][(int) x], rGen);
				}

			}
		}

		// GEN CUBES HERE
		VertexPositionData.flip();
		VertexColorData.flip();
		VertexNormalData.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOVertexHandle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertexPositionData,
				GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOColorHandle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertexColorData,
				GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBONormalHandle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertexNormalData,
				GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		Handles[0] = VBOVertexHandle;
		Handles[1] = VBOColorHandle;
		Handles[2] = VBONormalHandle;
		endTime = System.currentTimeMillis();
		System.out.println("Took "
				+ Long.toString((endTime - startTime) / 1000)
				+ " seconds to build a map.");
		return Handles;

	}

	public static int[] RebuildMesh(int[][] map, int startZ) {
		long startTime;
		long endTime;
		Map = map;

		int count[] = CountWalls_Floors();
		int[] Handles = new int[3];
		int VBOVertexHandle = GL15.glGenBuffers();
		int VBOColorHandle = GL15.glGenBuffers();
		int VBONormalHandle = GL15.glGenBuffers();
		Random rGen = new Random();
		startTime = System.currentTimeMillis();
		FloatBuffer VertexPositionData = BufferUtils
				.createFloatBuffer((int) ((count[0] + count[1] * 6 * DIVISIONS
						* DIVISIONS * DIVISIONS) * 4 * 3));
		FloatBuffer VertexColorData = BufferUtils
				.createFloatBuffer((int) ((count[0] + count[1] * 6 * DIVISIONS
						* DIVISIONS * DIVISIONS) * 4 * 3));
		FloatBuffer VertexNormalData = BufferUtils
				.createFloatBuffer((int) ((count[0] + count[1] * 6 * DIVISIONS
						* DIVISIONS * DIVISIONS) * 4 * 3));
		for (float y = 0; y < Map.length; y++) {
			for (float x = 0; x < Map[0].length; x++) {
				if (Map[(int) y][(int) x] > 0) {

					for (float x1 = 0; x1 < DIVISIONS; x1++) {
						for (float y1 = 0; y1 < DIVISIONS; y1++) {
							for (float z1 = 0; z1 < DIVISIONS; z1++) {
								if (y1 == 0 || x1 == 0 || z1 == 0
										|| y1 == DIVISIONS - 1
										|| x1 == DIVISIONS - 1
										|| z1 == DIVISIONS - 1) {
									VertexPositionData.put(CreateCube(x
											* CUBE_LENGTH + CUBE_LENGTH
											/ DIVISIONS * x1, y * CUBE_LENGTH
											+ CUBE_LENGTH / DIVISIONS * y1,
											startZ - z1 * WALL_SCALAR,
											CUBE_LENGTH / DIVISIONS));
									GenerateDefaultCubeData(VertexColorData,
											VertexNormalData,
											Map[(int) y][(int) x], rGen);
								}
							}
						}
					}
				} else if (Map[(int) y][(int) x] == 0) {

				} else {

					VertexPositionData.put(CreateFloor(x * CUBE_LENGTH
							+ CUBE_LENGTH / DIVISIONS, y * CUBE_LENGTH
							+ CUBE_LENGTH / DIVISIONS, startZ));
					GenerateDefaultFloorData(VertexColorData, VertexNormalData,
							Map[(int) y][(int) x], rGen);
				}

			}
		}

		// GEN CUBES HERE
		VertexPositionData.flip();
		VertexColorData.flip();
		VertexNormalData.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOVertexHandle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertexPositionData,
				GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOColorHandle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertexColorData,
				GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBONormalHandle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertexNormalData,
				GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		Handles[0] = VBOVertexHandle;
		Handles[1] = VBOColorHandle;
		Handles[2] = VBONormalHandle;
		endTime = System.currentTimeMillis();
		System.out.println("Took "
				+ Long.toString((endTime - startTime) / 1000)
				+ " seconds to build a map.");
		return Handles;

	}

	private static void GenerateDefaultCubeData(FloatBuffer vertexColorData,
			FloatBuffer vertexNormalData, int i, Random rGen) {
		vertexNormalData.put(new float[] {
				// BOTTOM
				0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0,
				// TOP
				0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0,
				// FRONT
				0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,
				// BOTTOM
				0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,
				// LEFT QUAD
				1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0,
				// RIGHT QUAD
				-1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, });
		vertexColorData.put(CreateCubeVertexCol(GetCubeColor(i, rGen)));
	}

	private static float[] CreateCubeVertexCol(float[] CubeColorArray) {
		float[] cubeColors = new float[CubeColorArray.length * 4 * 6];
		for (int i = 0; i < cubeColors.length; i++) {
			cubeColors[i] = CubeColorArray[i % CubeColorArray.length];
		}
		return cubeColors;
	}

	private static float[] CreateFloorVertexCol(float[] CubeColorArray) {
		float[] cubeColors = new float[CubeColorArray.length * 4];
		for (int i = 0; i < cubeColors.length; i++) {
			cubeColors[i] = CubeColorArray[i % CubeColorArray.length];
		}
		return cubeColors;
	}

	private static float[] GetCubeColor(int block, Random rGen) {
		float randomFloat = rGen.nextFloat() + 0.2f;
		switch (block) {

		case 1:
			return new float[] { 0, 1 - randomFloat / 3f, 0 };

		case 2:
			return new float[] { 0.5f, 0.2f + randomFloat / 3f, 0.9f };
		case 3:
			return new float[] { 0.7f, 0.4f, 0.2f + randomFloat / 3f };
		case 5:
			return new float[] { 0.5f + randomFloat / 3f,
					0.5f + randomFloat / 3f, 0.5f + randomFloat / 3f };
		}
		return new float[] { 0, 1, 1 };
	}

	private static float[] GetFloorColor(int block, Random rGen) {
		float randomFloat = rGen.nextFloat() + 0.2f;
		switch (block) {
		case 64:
			return new float[] { 1 - randomFloat / 10f, 1 - randomFloat / 10f,
					1 - randomFloat / 10f, 1 - randomFloat / 10f,
					1 - randomFloat / 10f, 1 - randomFloat / 10f,
					1 - randomFloat / 10f, 1 - randomFloat / 10f,
					1 - randomFloat / 10f, 1 - randomFloat / 10f,
					1 - randomFloat / 10f, 1 - randomFloat / 10f };

		case 65:
			return new float[] { 166f / 256f, 112f / 256f, 78f / 256f,
					166f / 256f, 112f / 256f, 78f / 256f, 0 + randomFloat / 4f,
					0 + randomFloat / 4f, 0 + randomFloat / 4f, 166f / 256f,
					112f / 256f, 78f / 256f };
		case 66:
			return new float[] { 100f / 256f + randomFloat / 10f,
					222f / 256f + randomFloat / 10f,
					84f / 256f + randomFloat / 10f, 100f / 256f, 222f / 256f,
					84f / 256f, 100f / 256f, 222f / 256f, 84f / 256f,
					100f / 256f, 222f / 256f, 84f / 256f };
		}
		return new float[] { 255f / 256f, 31f / 256f, 100f / 256f, 255f / 256f,
				31f / 256f, 100f / 256f, 255f / 256f, 31f / 256f, 100f / 256f,
				255f / 256f, 31f / 256f, 100f / 256f };
	}

	private static void GenerateDefaultFloorData(FloatBuffer vertexColorData,
			FloatBuffer vertexNormalData, int i, Random rGen) {
		vertexNormalData
				.put(new float[] { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1 });

		vertexColorData.put(GetFloorColor(Math.abs(i), rGen));
	}

	public static int[] CountWalls_Floors() {
		int[] count = { 0, 0 };
		for (int y = 0; y < Map.length; y++) {
			for (int x = 0; x < Map[0].length; x++) {
				if (Map[y][x] > 0) {
					count[1]++;
				} else {
					count[0]++;
				}

			}
		}
		return count;

	}

	public static float[] CreateCube(float x, float y, float z, float l) {
		float offset = l / 2;
		return new float[] {
				// BOTTOM QUAD(DOWN=+Y)
				x + offset, y + offset,
				z,
				x - offset,
				y + offset,
				z,
				x - offset,
				y + offset,
				z - WALL_SCALAR,
				x + offset,
				y + offset,
				z - WALL_SCALAR,
				// TOP!
				x + offset, y - offset, z - WALL_SCALAR, x - offset,
				y - offset,
				z - WALL_SCALAR,
				x - offset,
				y - offset,
				z,
				x + offset,
				y - offset,
				z,
				// FRONT QUAD
				x + offset, y + offset, z - WALL_SCALAR, x - offset,
				y + offset, z - WALL_SCALAR, x - offset,
				y - offset,
				z - WALL_SCALAR,
				x + offset,
				y - offset,
				z - WALL_SCALAR,
				// BACK QUAD
				x + offset, y - offset, z, x - offset, y - offset, z,
				x - offset, y + offset, z,
				x + offset,
				y + offset,
				z,
				// LEFT QUAD
				x - offset, y + offset, z - WALL_SCALAR, x - offset,
				y + offset, z, x - offset, y - offset, z, x - offset,
				y - offset,
				z - WALL_SCALAR,
				// RIGHT QUAD
				x + offset, y + offset, z, x + offset, y + offset,
				z - WALL_SCALAR, x + offset, y - offset, z - WALL_SCALAR,
				x + offset, y - offset, z };

	}

	public static float[] CreateFullCube(float x, float y, float z,
			float lengthOffset, float heightOffset) {
		float offset = lengthOffset / 2;
		float[] returnArray = new float[72 * 3];
		// BOTTOM QUAD(DOWN=+Y)
		for (int i = 1; i <= 3; i++) {
			float[] fa = new float[] { x + offset,
					y + offset,
					z,
					x - offset,
					y + offset,
					z,
					x - offset,
					y + offset,
					z - WALL_SCALAR * 3,
					x + offset,
					y + offset,
					z - WALL_SCALAR * 3,
					// TOP!
					x + offset, y - offset, z - WALL_SCALAR * 3,
					x - offset,
					y - offset,
					z - WALL_SCALAR * 3,
					x - offset,
					y - offset,
					z,
					x + offset,
					y - offset,
					z,
					// FRONT QUAD
					x + offset, y + offset, z - WALL_SCALAR * 3, x - offset,
					y + offset, z - WALL_SCALAR * 3,
					x - offset,
					y - offset,
					z - WALL_SCALAR * 3,
					x + offset,
					y - offset,
					z - WALL_SCALAR * 3,
					// BACK QUAD
					x + offset, y - offset, z, x - offset, y - offset, z,
					x - offset, y + offset, z,
					x + offset,
					y + offset,
					z,
					// LEFT QUAD
					x - offset, y + offset, z - WALL_SCALAR * 3, x - offset,
					y + offset, z, x - offset, y - offset, z, x - offset,
					y - offset,
					z - WALL_SCALAR * 3,
					// RIGHT QUAD
					x + offset, y + offset, z, x + offset, y + offset,
					z - WALL_SCALAR * 3, x + offset, y - offset,
					z - WALL_SCALAR * 3, x + offset, y - offset, z };
			for (int i2 = 0; i2 < fa.length; i2++) {
				returnArray[72 * (i - 1) + i2] = fa[i2];
			}
		}
		return returnArray;

	}

	public static float[] CreateFloor(float x, float y, float z) {
		float offset = CUBE_LENGTH / 2;
		return new float[] { x + offset, y - offset, z, x - offset, y - offset,
				z, x - offset, y + offset, z, x + offset, y + offset, z, };

	}

}
