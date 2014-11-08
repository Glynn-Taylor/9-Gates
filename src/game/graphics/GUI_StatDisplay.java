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
 * A GUI element that displays player statistics, as well as 
 handling mouse input for increasing stats using skill 
 points
 */
package game.graphics;

import game.level.entities.mobs.Player;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GUI_StatDisplay extends GUI_Object {

	UnicodeFont font;
	private static long LastClickTime;
	private final long RegisterClickDelay = 300;
	private final Player player;
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
			Buttons.get(0).Render();
			if (player.getLevelUpPoints() > 0) {
				for (int i = 1; i < Buttons.size(); i++) {
					Buttons.get(i).Render();
				}
			}
			font.drawString(ScreenX + 30, ScreenY + 10, "Player stats",
					Color.black);
			font.drawString(ScreenX + 30, ScreenY + 30, player.GetStats()
					.getPrimaryString(), Color.black);
			font.drawString(ScreenX + 150, ScreenY + 30, player.GetStats()
					.getPrimaryStringValues(), Color.black);
			font.drawString(ScreenX + 250, ScreenY + 30, player.GetStats()
					.getSecondaryString(), Color.black);
			font.drawString(ScreenX + 420, ScreenY + 30, player.GetStats()
					.getSecondaryStringValues(), Color.black);
			font.drawString(ScreenX + 30, ScreenY + 250, "Level up points: "
					+ Integer.toString(player.getLevelUpPoints()), Color.black);

		}

	}

	@Override
	public void ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		if (Enabled) {
			for (int i = 0; i < Buttons.size(); i++) {
				if (Buttons.get(i).InsideButton(mouseX, mouseY)
						&& mouseDown
						&& System.currentTimeMillis() - LastClickTime > RegisterClickDelay) {
					if (player.getLevelUpPoints() > 0) {
						if (i >= 1) {
							player.GetStats().AddToPrimaryStat(i - 1, 1);
							player.setLevelUpPoints(player.getLevelUpPoints() - 1);
						}
						LastClickTime = System.currentTimeMillis();
					}
					if (i == 0) {
						Enabled = false;
					}
				} else {
				}
			}
		}
	}

	public GUI_StatDisplay(int startX, int startY, int width, int height,
			String fontName, Player p) {

		ScreenX = startX;
		ScreenY = startY;
		ScreenButtonWidth = width;
		ScreenButtonHeight = height;

		player = p;
		try {
			texture = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/UI/TempBacking.png"));
			Texture addTex = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("res/Materials/GUI/UI/AddButton.png"));
			Texture closeTex = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/UI/CloseButton.png"));
			Buttons.add(new GUI_Button(ScreenX + ScreenButtonWidth,
					startY - 30, 30, 30, closeTex));
			for (int i = 0; i < 6; i++) {
				Buttons.add(new GUI_Button(ScreenX + 190, startY + 25
						+ (int) (i * 17.5), 15, 15, addTex));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		font = LoadFont(fontName);

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
