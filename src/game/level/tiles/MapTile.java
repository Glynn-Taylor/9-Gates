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
 * Allows entities and pickups to be stored, as well as 
 having a flag to allow movement onto the tile
 */
package game.level.tiles;

import java.io.Serializable;

public abstract class MapTile implements Serializable {

	private static final long serialVersionUID = -8163061799341646802L;
	public boolean IsFloor;

	public abstract void Render(int x, int y);
}
