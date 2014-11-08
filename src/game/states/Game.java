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
 * Creates the display, handles State changes, stores an 
 enum containing the states.
 */
package game.states;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game {

	public static GameState CurrentState = GameState.State_Menu;

	public static final int Width = 1280, Height = 720, MapLength = 64;

	public void Start() {
		try {
			CreateWindow();
			InitGL();
			Run();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	DisplayMode displayMode;

	private void CreateWindow() throws Exception {
		Display.setFullscreen(false);
		DisplayMode d[] = Display.getAvailableDisplayModes();
		for (int i = 0; i < d.length; i++) {
			if (d[i].getWidth() == Width && d[i].getHeight() == Height
					&& d[i].getBitsPerPixel() == 32) {
				displayMode = d[i];
				break;
			}
		}
		Display.setDisplayMode(displayMode);
		Display.setTitle("LWJGL COMP4");
		Display.create();
	}

	private void InitGL() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClearDepth(1.0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);

		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR,
				floatBuffer(1.0f, 1.0f, 1.0f, 1.0f));
		GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 64f);

		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION,
				floatBuffer(-30f, 200.0f, -200f, 0.5f));

		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR,
				floatBuffer(0.5f, 0.5f, 0.5f, 0.5f));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE,
				floatBuffer(0.5f, 0.5f, 0.5f, 0.5f));

		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT,
				floatBuffer(0.1f, 0.1f, 0.1f, 1.0f));

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	}

	private void Run() {
		CurrentState.GetState().Launch();

		while (CurrentState.GetState().LaunchMe) {
			try {

				if (CurrentState.GetState().LaunchMe) {
					CurrentState.GetState().Toggle(true);
				}
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		AL.destroy();
		Display.destroy();
		System.exit(0);

	}

	public static void main(String[] args) throws LWJGLException {
		Game r = new Game();
		r.Start();
	}

	public enum GameState {
		State_Menu(0, new State_MENU()), State_SinglePlayer(1,
				new State_SINGLEPLAYER()), State_Menu_SinglePlayer(4,
				new State_MENU_SINGLEPLAYER()), State_Menu_Multiplayer(5,
				new State_MENU_MULTIPLAYER()), State_Menu_MapEditor(6,
				new State_MENU_PREFAB_EDITOR()), State_MapEditor(7,
				new State_PREFAB_EDITOR()), State_DeathScreen(8,
				new State_MENU_DEATHSCREEN()), State_VictoryScreen(9,
				new State_MENU_VICTORY());
		private final int StateID;
		private State GameState;

		GameState(int i, State state) {
			StateID = i;
			GameState = state;
		}

		public int GetID() {
			return StateID;
		}

		public static void SwitchToState(GameState oldState, GameState newState) {
			oldState.GetState().Toggle(false);
			newState.GetState().Launch();
			Game.CurrentState = newState;
			oldState.GetState().Unload();
		}

		public State GetState() {
			return GameState;
		}

		public void ExitState() {
			GameState.Toggle(false);
		}

		public void RestartSinglePlayer() {
			GameState = new State_SINGLEPLAYER();
		}
	}

	public FloatBuffer floatBuffer(float a, float b, float c, float d) {
		float[] data = new float[] { a, b, c, d };
		FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
		fb.put(data);
		fb.flip();
		return fb;
	}
}
