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
 * Abstract base class for GUI elements
 */
package game.graphics;

import org.newdawn.slick.opengl.Texture;

public abstract class GUI_Object {
	protected int ScreenX, ScreenY, ScreenButtonWidth, ScreenButtonHeight;
	protected Texture texture;
	protected boolean Enabled = false;

	public abstract void ProcessInput(int mouseX, int mouseY, boolean mouseDown);

	public abstract void Render();

	public void SetEnabled(boolean i) {
		Enabled = i;
	}

	public void toggleEnabled() {
		Enabled = !Enabled;
		if (Enabled)
			System.out.println("Debug Console enabled");
	}
}
