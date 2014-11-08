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
/*Acts as an on screen button (generic), can be set-up with
 different textures, registers mouse clicks if updated.
 */
package game.graphics;

import game.states.Game;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class GUI_Button {
	int ScreenWidth, ScreenHeight;
	int ScreenX, ScreenY, ScreenButtonWidth, ScreenButtonHeight;
	Texture texture;
	private final float MouseOverMultiplier = 1.1f;
	private boolean MouseInsideMe;

	public void Render() {
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(ScreenX, ScreenY, 0);

		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(ScreenX, ScreenY + ScreenButtonHeight
				* (MouseInsideMe ? MouseOverMultiplier : 1), 0);

		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(ScreenX + ScreenButtonWidth
				* (MouseInsideMe ? MouseOverMultiplier : 1), ScreenY
				+ ScreenButtonHeight
				* (MouseInsideMe ? MouseOverMultiplier : 1), 0);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(ScreenX + ScreenButtonWidth
				* (MouseInsideMe ? MouseOverMultiplier : 1), ScreenY, 0);

		GL11.glEnd();

	}

	public GUI_Button(int startX, int startY, int width, int height, Texture t) {

		int gameWidth = Game.Width;
		int gameHeight = Game.Height;
		ScreenX = startX;
		ScreenY = startY;
		ScreenButtonWidth = width;
		ScreenButtonHeight = height;

		ScreenWidth = gameWidth;
		ScreenHeight = gameHeight;
		texture = t;

	}

	public int NextPowerOf2(int num) {
		int returnNumber = 1;
		while (returnNumber < num) {
			returnNumber *= 2;
		}
		return returnNumber;

	}

	public boolean InsideButton(int mouseX, int mouseY) {
		if (mouseX > ScreenX && mouseX < ScreenX + ScreenButtonWidth) {
			if (ScreenHeight - mouseY > ScreenY
					&& ScreenHeight - mouseY < ScreenY + ScreenButtonHeight) {
				MouseInsideMe = true;
				return true;
			}

		}
		MouseInsideMe = false;
		return false;
	}
}
