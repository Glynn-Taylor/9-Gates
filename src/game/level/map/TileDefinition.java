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
 * Returns colours from tile IDs
 */
package game.level.map;

import game.graphics.Colour;

import java.util.Random;

import org.lwjgl.opengl.GL11;

public final class TileDefinition {

	private static Random rGen = new Random();
	private static int floorOffset = 64;
	// REMEMBER TO CHANGE THE OFFSET IF YOU CHANGE THE ARRAYS, OTHERWISE THINGS
	// WILL CRASH
	private static Colour[] BasicFloorColors = { new Colour(0.9f, 0.9f, 0.9f),
			new Colour(166f / 256f, 112f / 256f, 78f / 256f),
			new Colour(100f / 256f, 222f / 256f, 84f / 256f) };
	// REMEMBER TO CHANGE THE OFFSET IF YOU CHANGE THE ARRAYS, OTHERWISE THINGS
	// WILL CRASH
	private static Colour[] BasicWallColors = { new Colour(0, 0.33f, 0),
			new Colour(0.5f, 0.2f, 0.9f), new Colour(0.7f, 0.4f, 0.2f),
			new Colour(1, 1, 1), new Colour(0.5f, 0.5f, 0.5f) };

	public static int[] getFloorList() {
		int[] ia = new int[BasicFloorColors.length];
		for (int i = 0; i < ia.length; i++) {
			ia[i] = -(floorOffset + i);
		}
		return ia;

	}

	public static int[] getWallList() {
		int[] ia = new int[BasicWallColors.length];
		for (int i = 0; i < ia.length; i++) {
			ia[i] = i + 1;
		}
		return ia;

	}

	public static void setColor(int i) {
		if (i < 0) {
			setBasicFloorColor(i);
		} else {
			setBasicWallColor(i);
		}
	}

	/**
	 * @return the basicFloorColors
	 */
	private static void setBasicFloorColor(int i) {
		Colour c = BasicFloorColors[Math.abs(i) - floorOffset];
		GL11.glColor3f(c.getR(), c.getG(), c.getB());
	}

	/**
	 * @return the basicFloorColors
	 */
	private static void setBasicWallColor(int i) {
		Colour c = BasicWallColors[i - 1];
		GL11.glColor3f(c.getR(), c.getG(), c.getB());
	}

	/**
	 * @return the basicFloorColors
	 */
	public static Colour getBasicFloorColor(int i) {
		return BasicFloorColors[i];
	}

	/**
	 * @return the basicFloorColors
	 */
	public static Colour getBasicWallColor(int i) {
		return BasicWallColors[i];
	}

}
