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
 * Abstract base class for states, defines how they loop
 */
package game.states;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public abstract class State {
	protected boolean Active = false;
	protected boolean Initialised = false;
	public boolean LaunchMe = false;
	protected Texture BackGroundImage;
	protected int MouseLastX, MouseLastY;

	protected abstract void Init();

	public void Launch() {
		LaunchMe = true;
	}

	public void Toggle(boolean active) {
		Active = active;
		if (Active) {
			if (!Initialised) {
				Init();
				Initialised = true;
			}
			Start();
		}
		LaunchMe = false;
	}

	protected void Start() {
		while (Active && !Display.isCloseRequested()) {
			UpdateMouse();
			ProcessInput();
			Update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();
			Render();
			Display.update();
			Display.sync(60);
		}

	}

	protected void UpdateMouse() {
		if (MouseLastX != Mouse.getX()) {
			MouseLastX = Mouse.getX();
		}
		if (MouseLastY != Mouse.getY()) {
			MouseLastY = Mouse.getY();
		}
	}

	protected abstract void Update();

	protected abstract void ProcessInput();

	protected abstract void Render();

	protected void DrawBackground() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		if (BackGroundImage != null) {
			BackGroundImage.bind();
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(0, 0, 0);

			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(0, BackGroundImage.getTextureHeight() * 0.94f, 0);

			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(BackGroundImage.getTextureWidth() * 0.95f,
					BackGroundImage.getTextureHeight() * 0.94f, 0);

			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(BackGroundImage.getTextureWidth() * 0.95f, 0, 0);

			GL11.glEnd();
		}
	}

	public abstract void Unload();

	private final int Width = Game.Width, Height = Game.Height;

	protected void OrthoMode() {
		GL11.glEnable(GL11.GL_ALPHA_TEST); // allows alpha channels or
											// transperancy
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f); // sets aplha function
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Width, Height, 0, -1, 1);
		Color.white.bind();
	}

	protected void ProjectionMode() {
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GLU.gluPerspective(45.0f,
				(float) Display.getWidth() / (float) Display.getHeight(), 0.1f,
				600.0f);
		GLU.gluLookAt(0, 20, 50, 0, -2, -100, 0, -1, 0);
	}

	protected void ModelMode() {
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
}
