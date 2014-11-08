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
 * A state that handles how the prefab editor functions
 */
package game.states;

import game.graphics.GUI_Layer;
import game.graphics.GUI_PrefabMap;
import game.graphics.GUI_PrefabPalette;
import game.level.map.Prefab;
import game.level.map.TileDefinition;
import game.level.map.TileMapGenerator;
import game.states.Game.GameState;
import game.util.PrefabBrowser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class State_PREFAB_EDITOR extends State {
	private final int RoomWidth = 100;
	private final int RoomHeight = 75;
	private Prefab prefab = new Prefab(RoomWidth, RoomHeight);
	private final int ScreenHeight = Game.Height;
	private float CameraX = -16;
	private float CameraY = 30;
	private float CameraZ = 0;

	private final float MapRotatef = 180;
	private final long InputTimePassed = 0;
	private final long InputDelayMili = 100;
	private int FPS;
	private long LastSecond;
	private final float CameraDisplacement = TileMapGenerator.CUBE_LENGTH;
	private GUI_Layer ButtonGUILayer;
	private GUI_Layer MenuGUILayer;
	private final GUI_PrefabMap PrefabMap = new GUI_PrefabMap(90, 68, 493, 389,
			prefab);
	private final GUI_PrefabPalette PrefabPalette = new GUI_PrefabPalette(53,
			533, 558, 62, 634, 558, 62);

	public void Create(int xLimit, int yLimit) {
		prefab = new Prefab(xLimit, yLimit);
		PrefabMap.setPrefab(prefab);
	}

	@Override
	protected void Init() {
		ButtonGUILayer = new GUI_Layer();
		ButtonGUILayer.SetEnabled(true);
		MenuGUILayer = new GUI_Layer();
		MenuGUILayer.SetEnabled(false);
		int ButtonStartX = 712;
		int ButtonStartY = 600;
		float xScale = 1.5f;
		float yScale = 1.5f;
		int Spacing = 145;
		int menuButtonStartX = 640 - 128;
		int menuButtonStartY = 150;
		float menuXScale = 1f;
		float menuYScale = 1f;
		int menuSpacing = 110;
		try {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			BackGroundImage = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("res/Materials/GUI/UI/GUIPrefab.png"));
			Texture button1 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/UI/SaveIconButton.png"),
							false, GL11.GL_NEAREST);
			ButtonGUILayer.AddButton(ButtonStartX + Spacing * 0, ButtonStartY,
					(int) (button1.getImageWidth() * xScale),
					(int) (button1.getImageHeight() * yScale), button1);
			Texture button2 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/UI/LoadIconButton.png"),
							false, GL11.GL_NEAREST);
			ButtonGUILayer.AddButton(ButtonStartX + Spacing * 1, ButtonStartY,
					(int) (button2.getImageWidth() * xScale),
					(int) (button2.getImageHeight() * yScale), button2);
			Texture button3 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/UI/OptionsIconButton.png"),
							false, GL11.GL_NEAREST);
			ButtonGUILayer.AddButton(ButtonStartX + Spacing * 2, ButtonStartY,
					(int) (button3.getImageWidth() * xScale),
					(int) (button3.getImageHeight() * yScale), button3);
			Texture button4 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/UI/HelpIconButton.png"),
							false, GL11.GL_NEAREST);
			ButtonGUILayer.AddButton(ButtonStartX + Spacing * 3, ButtonStartY,
					(int) (button4.getImageWidth() * xScale),
					(int) (button4.getImageHeight() * yScale), button4);

			Texture mButton0 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/ReturnButton.png"),
							false, GL11.GL_NEAREST);
			MenuGUILayer.AddButton(menuButtonStartX, menuButtonStartY
					+ menuSpacing * 0,
					(int) (mButton0.getImageWidth() * menuXScale),
					(int) (mButton0.getImageHeight() * menuYScale), mButton0);
			Texture mButton1 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/LoadButton.png"),
							false, GL11.GL_NEAREST);
			MenuGUILayer.AddButton(menuButtonStartX, menuButtonStartY
					+ menuSpacing * 1,
					(int) (mButton1.getImageWidth() * menuXScale),
					(int) (mButton1.getImageHeight() * menuYScale), mButton1);
			Texture mButton2 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/SaveButton.png"),
							false, GL11.GL_NEAREST);
			MenuGUILayer.AddButton(menuButtonStartX, menuButtonStartY
					+ menuSpacing * 2,
					(int) (mButton2.getImageWidth() * menuXScale),
					(int) (mButton2.getImageHeight() * menuYScale), mButton2);
			Texture mButton3 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/BackButton.png"),
							false, GL11.GL_NEAREST);
			MenuGUILayer.AddButton(menuButtonStartX, menuButtonStartY
					+ menuSpacing * 3,
					(int) (mButton3.getImageWidth() * menuXScale),
					(int) (mButton3.getImageHeight() * menuYScale), mButton3);

		} catch (IOException e) {
			e.printStackTrace();
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GLU.gluPerspective(45.0f,
				(float) Display.getWidth() / (float) Display.getHeight(), 0.1f,
				600.0f);
		GLU.gluLookAt(0, 20, 50, 0, -2, -100, 0, -1, 0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	protected void Update() {

		FPS++;
		if (System.currentTimeMillis() - LastSecond > 1000) {
			LastSecond = System.currentTimeMillis();
			System.out.println("Running PREFAB EDITOR: "
					+ Integer.toString(FPS));
			FPS = 0;
		}

	}

	private void DisableAllMenus() {
		MenuGUILayer.SetEnabled(false);
	}

	@Override
	protected void ProcessInput() {

		MenuGUILayer
				.ProcessInput(MouseLastX, MouseLastY, Mouse.isButtonDown(0));
		ButtonGUILayer.ProcessInput(MouseLastX, MouseLastY,
				Mouse.isButtonDown(0));
		PrefabMap.ProcessInput(MouseLastX, ScreenHeight - MouseLastY,
				Mouse.isButtonDown(0), PrefabPalette.getSelectedTile());
		PrefabPalette.ProcessInput(MouseLastX, ScreenHeight - MouseLastY,
				Mouse.isButtonDown(0));
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			DisableAllMenus();
			MenuGUILayer.toggleEnabled();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
			System.out.println(CameraX);
			System.out.println(CameraY);
			System.out.println(CameraZ);
		}
		if (ButtonGUILayer.isButtonDown(2)) {
			DisableAllMenus();
			MenuGUILayer.toggleEnabled();
		}
		if (ButtonGUILayer.isButtonDown(1))
			LoadPrefab();

		if (ButtonGUILayer.isButtonDown(0))
			SavePrefab();

		if (MenuGUILayer.isButtonDown(0)) {
			DisableAllMenus();
		}
		// Save
		if (MenuGUILayer.isButtonDown(1)) {

		}
		// Load
		if (MenuGUILayer.isButtonDown(2)) {

		}
		if (MenuGUILayer.isButtonDown(3)) {
			DisableAllMenus();
			GameState.SwitchToState(GameState.State_MapEditor,
					GameState.State_Menu_MapEditor);
		}
		if (ButtonGUILayer.isButtonDown(3)) {
			JOptionPane
					.showMessageDialog(
							null,
							"Use the squares of color to the bottom left to select a color,"
									+ System.getProperty("line.separator")
									+ "then click on a tile on the top left to alter the prefab. View user manual for reference.",
							"Help", JOptionPane.PLAIN_MESSAGE);

		}

		if (System.currentTimeMillis() - InputTimePassed >= InputDelayMili) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				MoveCamera(0, -1);

			} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {

				MoveCamera(0, 1);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {

				MoveCamera(-1, 0);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				MoveCamera(1, 0);
			}

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			CameraZ -= 1f;
			CameraX -= 0.01f;
			CameraY -= 0.2f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			CameraZ += 1f;
			CameraX += 0.01f;
			CameraY += 0.2f;
		}

	}

	private void SavePrefab() {
		if (CheckBoundary(prefab.getMap()) & CheckMiddle(prefab.getMap())
				& CheckConnected(prefab.getMap())) {
			PrefabBrowser fb = new PrefabBrowser();
			try {
				String path = fb.GetSavePath();
				if (path != null) {
					FileOutputStream fileOut = new FileOutputStream(path
							+ ".prefab");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(prefab);
					out.close();
					fileOut.close();
				}
			} catch (IOException i) {
				i.printStackTrace();
			}
		} else {

			JOptionPane
					.showMessageDialog(
							null,
							"A tile is not connected, the center of the room is a wall, or a boundary tile is floor.",
							"Room not valid", JOptionPane.PLAIN_MESSAGE);
		}

	}

	private boolean CheckConnected(int[][] map) {
		int xRef = 0;
		int yRef = 0;
		int count = 0;
		String[][] refMap = new String[map.length][map[0].length];
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] < 0) {
					count++;
					xRef = x;
					yRef = y;
					refMap[y][x] = "O";
				} else {
					refMap[y][x] = "X";
				}
			}
		}
		int[] burnedCount = { 0 };
		BurnNode(refMap, xRef, yRef, burnedCount);
		if (burnedCount[0] == count) {
			return true;
		} else {
			System.out.println("Unconnected tile");
			return false;
		}

	}

	private void BurnNode(String[][] refMap, int xRef, int yRef,
			int[] burnedCount) {

		if (refMap[yRef][xRef] == "O") {
			refMap[yRef][xRef] = "X";
			burnedCount[0]++;
			if (yRef + 1 < refMap.length)
				BurnNode(refMap, xRef, yRef + 1, burnedCount);
			if (yRef - 1 >= 0)
				BurnNode(refMap, xRef, yRef - 1, burnedCount);
			if (xRef + 1 < refMap[0].length)
				BurnNode(refMap, xRef + 1, yRef, burnedCount);
			if (xRef - 1 >= 0)
				BurnNode(refMap, xRef - 1, yRef, burnedCount);
		}
	}

	private boolean CheckMiddle(int[][] map) {
		if (map[map.length / 2][map[0].length / 2] >= 0) {
			System.out.println("Middle tile");
			return false;
		}
		return true;
	}

	private boolean CheckBoundary(int[][] map) {
		boolean valid = true;
		for (int y = 0; y < map.length; y++) {
			if (map[y][0] < 0 || map[y][map[0].length - 1] < 0)
				valid = false;
		}
		for (int x = 0; x < map[0].length; x++) {
			if (map[0][x] < 0 || map[map.length - 1][x] < 0)
				valid = false;
		}
		if (!valid)
			System.out.println("Boundary");
		return valid;
	}

	public void LoadPrefab() {
		PrefabBrowser fb = new PrefabBrowser();
		try {

			String path = fb.GetOpenPath();
			if (path != null) {
				File f = new File(path);
				FileInputStream fileIn = new FileInputStream(f);

				ObjectInputStream in = new ObjectInputStream(fileIn);
				Prefab p = (Prefab) in.readObject();
				prefab = p;
				PrefabMap.setPrefab(prefab);
				in.close();
				fileIn.close();
				fb.DestroyMe();

			}
		} catch (IOException | ClassNotFoundException e) {

			System.out.println("ERROR: Failed to load image @ Filebrowser");
			e.printStackTrace();
		}

	}

	private void MoveCamera(int x, int y) {
		CameraY -= CameraDisplacement * y;
		CameraX += CameraDisplacement * x;

	}

	@Override
	protected void Render() {

		OrthoMode();
		ModelMode();

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		DrawBackground();

		ButtonGUILayer.Render();
		if (PrefabMap != null)
			PrefabMap.Render();
		PrefabPalette.Render();
		Color.white.bind();
		MenuGUILayer.Render();

		ProjectionMode();
		ModelMode();
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glTranslatef(CameraX, CameraY, -20f + CameraZ); // Move Right 1.5

		GL11.glTranslatef(9, -45, 0);
		GL11.glRotatef(MapRotatef, 0f, 1.0f, 0f);
		RenderPrefab();

	}

	private void RenderPrefab() {

		for (int y = 0; y < prefab.GetHeight(); y++) {
			for (int x = 0; x < prefab.GetWidth(); x++) {
				GL11.glTranslatef(x * TileMapGenerator.CUBE_LENGTH + 0.7f, y
						* TileMapGenerator.CUBE_LENGTH + 0.7f, 0);

				if (prefab.getColorRef(x, y) < 0) {
					try {

						DrawFloor(x, y, prefab.getColorRef(x, y));
					} catch (Exception e) {

					}

				} else {
					DrawWall(x, y, prefab.getColorRef(x, y));
				}
				GL11.glTranslatef(-x * TileMapGenerator.CUBE_LENGTH - 0.7f, -y
						* TileMapGenerator.CUBE_LENGTH - 0.7f, 0);
			}
		}
	}

	private void DrawWall(int x, int y, int i) {
		// BOTTOM
		GL11.glBegin(GL11.GL_QUADS);
		TileDefinition.setColor(i);
		GL11.glNormal3f(0, -1, 0);
		GL11.glVertex3f(-1f, -1f, 0f);
		GL11.glVertex3f(1f, -1f, 0);
		GL11.glVertex3f(1f, 1f, 0f);
		GL11.glVertex3f(-1f, 1f, 0f);

		// SIDES
		GL11.glNormal3f(1, 0, 0);
		GL11.glVertex3f(-1f, -1f, 0f);
		GL11.glVertex3f(1f, -1f, 0f);
		GL11.glVertex3f(1f, -1f, -4f);
		GL11.glVertex3f(-1f, -1f, -4f);

		GL11.glNormal3f(0, 0, 1);
		GL11.glVertex3f(1f, -1f, 0f);
		GL11.glVertex3f(1f, 1f, 0f);
		GL11.glVertex3f(1f, 1f, -4f);
		GL11.glVertex3f(1f, -1f, -4f);

		GL11.glNormal3f(1, 0, 0);
		GL11.glVertex3f(1f, 1f, 0f);
		GL11.glVertex3f(-1f, 1f, 0f);
		GL11.glVertex3f(-1f, 1f, -4f);
		GL11.glVertex3f(1f, 1f, -4f);

		GL11.glNormal3f(0, 0, 1);
		GL11.glVertex3f(-1f, 1f, 0f);
		GL11.glVertex3f(-1f, -1f, 0f);
		GL11.glVertex3f(-1f, -1f, -4f);
		GL11.glVertex3f(-1f, 1f, -4f);

		// BOTTOM
		GL11.glNormal3f(0, 1, 0);
		GL11.glVertex3f(-1f, -1f, -4f);
		GL11.glVertex3f(1f, -1f, -4f);
		GL11.glVertex3f(1f, 1f, -4f);
		GL11.glVertex3f(-1f, 1f, -4f);
		GL11.glEnd();

	}

	private void DrawFloor(int x, int y, int i) {
		GL11.glBegin(GL11.GL_QUADS);
		TileDefinition.setColor(i);
		GL11.glNormal3f(0, 1, 0);
		GL11.glVertex3f(-1f, -1f, 0f);
		GL11.glVertex3f(1f, -1f, 0f);
		GL11.glVertex3f(1f, 1f, 0f);
		GL11.glVertex3f(-1f, 1f, 0f);
		GL11.glEnd();

	}

	@Override
	public void Unload() {

	}

}
