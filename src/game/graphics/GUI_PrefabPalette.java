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
 * Contains several buttons that change a tile id number
 */
package game.graphics;

import game.level.map.TileDefinition;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GUI_PrefabPalette {

	private final int X, Y, Y2, Width1, Height1, TileWidth1, TileHeight1,
			Width2, Height2, TileWidth2, TileHeight2;

	private boolean Enabled = true;
	private int SelectedTile = -64;
	private final int[] FloorList = TileDefinition.getFloorList();
	private final int[] WallList = TileDefinition.getWallList();

	public GUI_PrefabPalette(int startX, int startY, int width, int height,
			int startY2, int width2, int height2) {
		X = startX;
		Y = startY;
		Y2 = startY2;
		Width1 = width;
		Height1 = height;
		Width2 = width2;
		Height2 = height2;
		TileWidth1 = width / FloorList.length;
		TileWidth2 = width2 / WallList.length;
		TileHeight1 = height;
		TileHeight2 = height2;
	}

	public void Render() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		if (Enabled) {
			for (int i = 0; i < FloorList.length; i++) {
				RenderTile(X + i * TileWidth1, Y, TileWidth1, TileHeight1,
						FloorList[i]);
			}
			for (int i = 0; i < WallList.length; i++) {
				RenderTile(X + i * TileWidth2, Y2, TileWidth2, TileHeight2,
						WallList[i]);
			}

		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	private void RenderTile(int x, int y, int width, int height, int i) {
		Color.white.bind();
		GL11.glBegin(GL11.GL_QUADS);

		TileDefinition.setColor(i);
		GL11.glVertex3f(x, y, 0);

		GL11.glVertex3f(x, y + height, 0);

		GL11.glVertex3f(x + width, y + height, 0);

		GL11.glVertex3f(x + width, y, 0);

		GL11.glEnd();

	}

	public void ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		if (Enabled && mouseDown) {
			if (mouseX > X && mouseY > Y && mouseX < X + Width1
					&& mouseY < Y + Height1) {
				setSelectedTile(FloorList[(mouseX - X) / TileWidth1]);
			} else if (mouseX > X && mouseY > Y2 && mouseX < X + Width2
					&& mouseY < Y2 + Height2) {
				setSelectedTile(WallList[(mouseX - X) / TileWidth2]);
			}
		}
	}

	public void SetEnabled(boolean i) {
		Enabled = i;
	}

	public void toggleEnabled() {
		Enabled = !Enabled;
	}

	/**
	 * @return the selectedTile
	 */
	public int getSelectedTile() {
		return SelectedTile;
	}

	/**
	 * @param selectedTile
	 *            the selectedTile to set
	 */
	private void setSelectedTile(int selectedTile) {
		SelectedTile = selectedTile;
	}
}
