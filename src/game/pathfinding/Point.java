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
package game.pathfinding;

public class Point {
	public int X;
	public int Y;
	public void Set(int x, int y){
		X=x;
		Y=y;
	}
	public Point(int x, int y){
		X=x;
		Y=y;
	}
}
