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
 * Abstract base class for any item that cannot be 
 equipped, but may be usable.
 */
package game.level.entities.mobs.player.items;

import game.resources.GraphicsHandler.ItemArt;

import org.lwjgl.opengl.GL11;

public abstract class StandardItem extends InventoryItem {
	private static final long serialVersionUID = -6954815167926398203L;
	protected static float TEXTURE_QUAD_LENGTH;

	public StandardItem() {

		TEXTURE_QUAD_LENGTH = 16f / ItemArt.Items.getTextureWidth();

	}

	@Override
	public void Render(int ScreenX, int ScreenY) {

		ItemArt.Items.bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(TEXTURE_QUAD_LENGTH * XTextureRef,
				TEXTURE_QUAD_LENGTH * YTextureRef);
		GL11.glVertex3f(ScreenX, ScreenY, 0);

		GL11.glTexCoord2f(TEXTURE_QUAD_LENGTH * XTextureRef,
				TEXTURE_QUAD_LENGTH * (YTextureRef + 1));
		GL11.glVertex3f(ScreenX, ScreenY + IconWidth, 0);

		GL11.glTexCoord2f(TEXTURE_QUAD_LENGTH * (XTextureRef + 1),
				TEXTURE_QUAD_LENGTH * (YTextureRef + 1));
		GL11.glVertex3f(ScreenX + IconWidth, ScreenY + IconWidth, 0);

		GL11.glTexCoord2f(TEXTURE_QUAD_LENGTH * (XTextureRef + 1),
				TEXTURE_QUAD_LENGTH * YTextureRef);
		GL11.glVertex3f(ScreenX + IconWidth, ScreenY, 0);

		GL11.glEnd();
	}
}
