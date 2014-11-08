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
 * Displays a players inventory, can work for any player 
 object.
 */
package game.graphics;

import game.level.entities.mobs.Player;
import game.level.map.TileMap;
import game.states.Game;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GUI_InvDisplay extends GUI_Object {

	UnicodeFont font;
	private static long LastClickTime;
	private final long RegisterClickDelay = 300;
	private final Player player;
	private final TileMap[] World;
	private final ArrayList<GUI_Button> Buttons = new ArrayList<GUI_Button>();

	@Override
	public void Render() {
		if (Enabled) {

			texture.bind();

			GL11.glBegin(GL11.GL_QUADS);

			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(ScreenX, ScreenY, 0);

			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(ScreenX, ScreenY + ScreenButtonHeight, 0);

			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(ScreenX + ScreenButtonWidth, ScreenY
					+ ScreenButtonHeight, 0);

			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(ScreenX + ScreenButtonWidth, ScreenY, 0);

			GL11.glEnd();

			for (int i = 0; i < Buttons.size(); i++) {
				Buttons.get(i).Render();
			}

			player.getInventory().Render(ScreenX + 230, ScreenY + 30, font);

		}

	}

	private final int Height = Game.Height;

	@Override
	public void ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		if (Enabled) {
			player.getInventory().ProcessInput(mouseX, Height - mouseY,
					mouseDown);
			for (int i = 0; i < Buttons.size(); i++) {
				if (Buttons.get(i).InsideButton(mouseX, mouseY)
						&& mouseDown
						&& System.currentTimeMillis() - LastClickTime > RegisterClickDelay) {
					if (i == 0) {
						// DROP
						if (player.getInventory().ValidSelection()) {
							TileMap level = player.GetLevel(World);

							level.DropItem(player.getX(), player.getY(), player
									.getInventory().ItemDrop());
						}
					} else if (i == 1) {
						// USE
						if (player.getInventory().ValidSelection()) {
							player.getInventory().UseItem(player);
						}
					} else if (i == 2) {
						// UP
						player.getInventory().DecrementStartRow();
					} else if (i == 3) {
						// DOWN
						player.getInventory().IncrementStartRow();

					} else if (i == 4) {
						// Close
						Enabled = false;

					}

					LastClickTime = System.currentTimeMillis();
				} else {
				}
			}
		}

	}

	public GUI_InvDisplay(int startX, int startY, int width, int height,
			String fontName, Player p, TileMap[] t) {

		ScreenX = startX;
		ScreenY = startY;
		ScreenButtonWidth = width;
		ScreenButtonHeight = height;

		player = p;
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("res/Materials/GUI/UI/Inv.png"));
			Texture DropTex = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Items/DropButton.png"));
			Buttons.add(new GUI_Button(ScreenX + 230, startY + 240, 100, 50,
					DropTex));
			Texture UseTex = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Items/UseButton.png"));
			Buttons.add(new GUI_Button(ScreenX + 340, startY + 240, 100, 50,
					UseTex));

			Texture UpTex = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Items/UpButton.png"));
			Buttons.add(new GUI_Button(ScreenX + 464, startY + 40, 30, 30,
					UpTex));
			Texture DownTex = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Items/DownButton.png"));
			Buttons.add(new GUI_Button(ScreenX + 464, startY + 170, 30, 30,
					DownTex));

			Texture CloseTex = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/UI/CloseButton.png"));
			Buttons.add(new GUI_Button(ScreenX + ScreenButtonWidth,
					startY - 30, 30, 30, CloseTex));
		} catch (IOException e) {
			e.printStackTrace();
		}
		font = LoadFont(fontName);
		World = t;

	}

	public UnicodeFont LoadFont(String name) {
		UnicodeFont font1 = null;
		String fontPath = "res/fonts/" + name + ".ttf";
		try {
			font1 = new UnicodeFont(fontPath, 15, true, false);
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		font1.addAsciiGlyphs();
		font1.addGlyphs(400, 600);
		font1.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		try {
			font1.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return font1;
	}

}
