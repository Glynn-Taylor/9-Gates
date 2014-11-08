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
 * A GUI element that displays an integer map and can 
 change parts of it via mouse clicks
 */
package game.graphics;

import game.level.map.Prefab;
import game.level.map.TileDefinition;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class GUI_PrefabMap {
	private final ArrayList<GUI_Button> Buttons = new ArrayList<GUI_Button>();
	private boolean[] ButtonClicked;
	private static long LastClickTime;
	private final long RegisterClickDelay = 300;
	private final int X, Y, Width, Height;
	private int TileWidth;
	private int TileHeight;

	private Prefab prefab;
	private boolean Enabled = true;

	public void setPrefab(Prefab p) {
		prefab = p;
		TileWidth = Width / p.GetWidth();
		TileHeight = Height / p.GetHeight();
	}

	public GUI_PrefabMap(int startX, int startY, int width, int height, Prefab p) {
		prefab = p;
		X = startX;
		Y = startY;
		Width = width;
		Height = height;
		TileWidth = width / p.GetWidth();
		TileHeight = height / p.GetHeight();
	}

	public void Render() {
		if (Enabled && prefab != null) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			for (int y = 0; y < prefab.GetHeight(); y++) {
				for (int x = 0; x < prefab.GetWidth(); x++) {
					Color.white.bind();
					RenderTile(X + x * TileWidth, Y + y * TileHeight,
							prefab.getColorRef(x, y));
				}
			}
			GL11.glEnable(GL11.GL_TEXTURE_2D);

		}

	}

	private void RenderTile(int x, int y, int i) {
		GL11.glBegin(GL11.GL_QUADS);
		TileDefinition.setColor(i);
		GL11.glVertex3f(x, y, 0);

		GL11.glVertex3f(x, y + TileHeight, 0);

		GL11.glVertex3f(x + TileWidth, y + TileHeight, 0);

		GL11.glVertex3f(x + TileWidth, y, 0);

		GL11.glEnd();

	}

	public void ProcessInput(int mouseX, int mouseY, boolean mouseDown,
			int TileSelected) {
		if (Enabled) {
			if (mouseDown && mouseX > X && mouseY > Y
					&& mouseX < X + prefab.GetWidth() * TileWidth
					&& mouseY < Y + prefab.GetHeight() * TileHeight) {
				prefab.setTile((mouseX - X) / TileWidth, (mouseY - Y)
						/ TileHeight, TileSelected);
			}

		}
	}

	public void AddButton(int startX, int startY, int width, int height,
			Texture t) {
		Buttons.add(new GUI_Button(startX, startY, width, height, t));
		ButtonClicked = new boolean[Buttons.size()];
	}

	public void SetEnabled(boolean i) {
		Enabled = i;
	}

	public void toggleEnabled() {
		Enabled = !Enabled;
	}

	public boolean isButtonDown(int i) {
		if (Enabled) {
			if (ButtonClicked[i] == true) {
				ButtonClicked[i] = false;
				return true;
			}
		}
		return false;
	}
}
