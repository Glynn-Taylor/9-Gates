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
 * Abstract base class for mobs and scenery/useable 
 objects
 */
package game.level.entities;

import game.level.map.TileMapGenerator;

import java.io.Serializable;

import org.lwjgl.opengl.GL11;

public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 1378890538919150787L;
	public boolean IsMob;

	public void MapRender(int x, int y) {
		GL11.glTranslatef(x * TileMapGenerator.CUBE_LENGTH + 0.7f, y
				* TileMapGenerator.CUBE_LENGTH + 0.7f, 0);
		Render(x, y);
		GL11.glTranslatef(-x * TileMapGenerator.CUBE_LENGTH - 0.7f, -y
				* TileMapGenerator.CUBE_LENGTH - 0.7f, 0);
	}

	public abstract void Render(int x, int y);

}
