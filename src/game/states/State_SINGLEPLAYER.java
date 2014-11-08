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
 * A state that handles how the singleplayer mode functions
 */
package game.states;

import game.graphics.GUI_Layer;
import game.graphics.GUI_Stats;
import game.level.entities.mobs.Player;
import game.level.map.TileMap;
import game.level.map.TileMapGenerator;
import game.states.Game.GameState;
import game.util.GameBrowser;
import game.util.GameSave;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class State_SINGLEPLAYER extends State {

	private TileMap[] Worlds;
	private TileMap t;
	private int PlayerGridX;
	private int PlayerGridY;
	private Player player;
	private float PlayerX = -6;
	private float PlayerY = 52;
	private float PlayerZ = 0;
	private float MapRotatef = 180;
	private long InputTimePassed = 0;
	private final long InputDelayMili = 100;
	private int FPS;
	private long LastSecond;
	private final float CameraDisplacement = TileMapGenerator.CUBE_LENGTH;
	private int WorldNumber = 0;
	private GUI_Layer MainGUILayer;
	private GUI_Layer MenuGUILayer;
	private GUI_Stats StatRenderer;
	private GameSave CurrentSave;
	private boolean CreateNewGame = true;

	public void Reset() {
		Initialised = false;
	}

	@Override
	protected void Init() {
		if (CreateNewGame) {
			CurrentSave = new GameSave();
			Worlds = new TileMap[] { new TileMap(), new TileMap(),
					new TileMap(), new TileMap(), new TileMap(), new TileMap(),
					new TileMap(), new TileMap(), new TileMap() };
			t = Worlds[0];
			CreateGUI();
			System.out.println("Loading worlds");
			for (int i = 0; i < Worlds.length; i++) {
				Worlds[i].Load(i == Worlds.length - 1, i == 0, i);
				CurrentSave.setIntMapData(TileMapGenerator.Map, i);
			}
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
		if (CreateNewGame) {
			player = t.SpawnPlayer();
			PlayerGridX = player.getX();
			PlayerGridY = player.getY();
			PlayerY = 52 - CameraDisplacement * (PlayerGridY - 1);
			PlayerX = -6 + CameraDisplacement * (PlayerGridX - 1);
			PlayerZ = 0;
			SetGUI();
		}
		CreateNewGame = true;

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	private void SetGUI() {
		StatRenderer = new GUI_Stats(player);
		MainGUILayer.AddConsole(0, 50, 600, 400, "DIGITALDREAM", t, player);
		MainGUILayer.AddStatDisplay(440, 250, 500, 300, "ABEAKRG", player);
		MainGUILayer.AddInvDisplay(440, 250, 500, 300, "ABEAKRG", player,
				Worlds);
	}

	private void CreateGUI() {
		MainGUILayer = new GUI_Layer();
		MenuGUILayer = new GUI_Layer();
		MenuGUILayer.SetEnabled(false);
		int ButtonStartX = 405;
		int ButtonStartY = 650;
		float xScale = 0.8f;
		float yScale = 0.8f;
		int Spacing = 140;
		int menuButtonStartX = 640 - 128;
		int menuButtonStartY = 150;
		float menuXScale = 1f;
		float menuYScale = 1f;
		int menuSpacing = 110;
		try {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			BackGroundImage = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/UI/GUIBaseLayer.png"));
			Texture button1 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/UI/OptionsIconButton.png"),
							false, GL11.GL_NEAREST);
			MainGUILayer.AddButton(ButtonStartX + Spacing * 0, ButtonStartY,
					(int) (button1.getImageWidth() * xScale),
					(int) (button1.getImageHeight() * yScale), button1);
			Texture button2 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/UI/InvIconButton.png"),
							false, GL11.GL_NEAREST);
			MainGUILayer.AddButton(ButtonStartX + Spacing * 1, ButtonStartY,
					(int) (button2.getImageWidth() * xScale),
					(int) (button2.getImageHeight() * yScale), button2);
			Texture button3 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/UI/StatsIconButton.png"),
							false, GL11.GL_NEAREST);
			MainGUILayer.AddButton(ButtonStartX + Spacing * 2, ButtonStartY,
					(int) (button3.getImageWidth() * xScale),
					(int) (button3.getImageHeight() * yScale), button3);
			Texture button4 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/UI/ConsoleIconButton.png"),
							false, GL11.GL_NEAREST);
			MainGUILayer.AddButton(ButtonStartX + Spacing * 3, ButtonStartY,
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

	}

	@Override
	protected void Update() {

		FPS++;
		if (System.currentTimeMillis() - LastSecond > 1000) {
			LastSecond = System.currentTimeMillis();
			System.out
					.println("Running SINGLEPLAYER: " + Integer.toString(FPS));
			FPS = 0;
		}

	}

	private void DisableAllMenus() {
		MainGUILayer.setObjectEnabled(1, false);
		MainGUILayer.setObjectEnabled(0, false);
		MenuGUILayer.SetEnabled(false);
		MainGUILayer.setConsoleEnabled(false);
	}

	private void DisableNonConsole() {
		MainGUILayer.setObjectEnabled(1, false);
		MainGUILayer.setObjectEnabled(0, false);
		MenuGUILayer.SetEnabled(false);

	}

	@Override
	protected void ProcessInput() {
		CheckPlayerWorld();
		MainGUILayer
				.ProcessInput(MouseLastX, MouseLastY, Mouse.isButtonDown(0));
		MenuGUILayer
				.ProcessInput(MouseLastX, MouseLastY, Mouse.isButtonDown(0));
		if (MainGUILayer.isButtonDown(0)) {
			DisableAllMenus();
			MenuGUILayer.toggleEnabled();
		}
		if (MainGUILayer.isButtonDown(1)) {
			DisableAllMenus();
			MainGUILayer.toggleObjectEnabled(1);
		}
		if (MainGUILayer.isButtonDown(2)) {
			DisableAllMenus();
			MainGUILayer.toggleObjectEnabled(0);
		}
		if (MainGUILayer.isButtonDown(3)) {
			DisableNonConsole();
			MainGUILayer.toggleConsole();
		}

		if (MenuGUILayer.isButtonDown(0)) {
			DisableAllMenus();
		}
		// Load
		if (MenuGUILayer.isButtonDown(1)) {
			LoadGame();
		}
		// Save
		if (MenuGUILayer.isButtonDown(2)) {
			SaveGame();

		}
		if (MenuGUILayer.isButtonDown(3)) {
			DisableAllMenus();
			GameState.SwitchToState(GameState.State_SinglePlayer,
					GameState.State_Menu_SinglePlayer);
		}
		if (!MainGUILayer.getConsoleEnabled()) {
			if (System.currentTimeMillis() - InputTimePassed >= InputDelayMili) {
				if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
					MovePlayer(0, -1);
					PlayerMoved();

				} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {

					MovePlayer(-1, 0);
					PlayerMoved();
				} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {

					MovePlayer(0, 1);
					PlayerMoved();
				} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
					MovePlayer(1, 0);
					PlayerMoved();
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {

					PlayerMoved();
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_F2)) {
					InputTimePassed = System.currentTimeMillis();
				}
			} else {
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				PlayerZ -= 1f;
				PlayerX -= 0.01f;
				PlayerY -= 0.2f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
				PlayerZ += 1f;
				PlayerX += 0.01f;
				PlayerY += 0.2f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
				MapRotatef += 0.6f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
				MapRotatef -= 0.6f;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {

			t.DebugMode = !t.DebugMode;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
		}

	}

	private void LoadGame() {
		GameBrowser gb = new GameBrowser();
		try {

			String path = gb.GetOpenPath();
			if (path != null) {
				File f = new File(path);
				FileInputStream fileIn = new FileInputStream(f);

				ObjectInputStream in = new ObjectInputStream(fileIn);
				GameSave g = (GameSave) in.readObject();
				LoadGameSave(g);
				in.close();
				fileIn.close();
				gb.DestroyMe();

			}
		} catch (IOException | ClassNotFoundException e) {

			System.out.println("ERROR: Failed to load image @ Filebrowser");
			e.printStackTrace();
		}

	}

	public void LoadGameSave(GameSave g) {
		if (Worlds != null) {
			for (int i = 0; i < Worlds.length; i++) {
				Worlds[i].DeleteVBOFromMemory();
			}
		}
		Worlds = g.getWorld();
		player = g.getPlayer();

		t = Worlds[player.WorldNumber];
		for (int i = 0; i < Worlds.length; i++) {
			Worlds[i].RebuildHandles(g.getIntMapData(i));
		}
		WorldNumber = player.WorldNumber;
		PlayerGridX = player.getX();
		PlayerGridY = player.getY();
		PlayerY = 52 - CameraDisplacement * (PlayerGridY - 1);
		PlayerX = -6 + CameraDisplacement * (PlayerGridX - 1);
		CreateGUI();
		SetGUI();
		CurrentSave = g;
	}

	private void CheckPlayerWorld() {
		if (player.WorldNumber == WorldNumber) {

		} else {
			SwitchWorld(player.WorldNumber);
		}

	}

	private void SwitchWorld(int number) {
		t.RemovePlayer(PlayerGridX, PlayerGridY);
		if (WorldNumber > number) {
			WorldNumber = number;

			t = Worlds[WorldNumber];
			InputTimePassed = System.currentTimeMillis();
			t.SpawnPlayerAtNext(player);
		} else {

			WorldNumber = number;

			t = Worlds[WorldNumber];
			InputTimePassed = System.currentTimeMillis();
			t.SpawnPlayerAtPrevious(player);
		}
		PlayerGridX = player.getX();
		PlayerGridY = player.getY();
		PlayerY = 52 - CameraDisplacement * (PlayerGridY - 1);
		PlayerX = -6 + CameraDisplacement * (PlayerGridX - 1);
	}

	private void MovePlayer(int x, int y) {
		if (t.MovePlayer(PlayerGridX, PlayerGridY, PlayerGridX + x, PlayerGridY
				+ y)) {
			PlayerY -= CameraDisplacement * y;
			PlayerX += CameraDisplacement * x;
			PlayerGridX += x;
			PlayerGridY += y;
			player.setX(PlayerGridX);
			player.setY(PlayerGridY);
		}

	}

	private void PlayerMoved() {
		InputTimePassed = System.currentTimeMillis();
		t.MapUpdate(PlayerGridX, PlayerGridY);

	}

	@Override
	protected void Render() {

		OrthoMode();
		ModelMode();

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		DrawBackground();
		MainGUILayer.Render();
		MenuGUILayer.Render();
		StatRenderer.DrawBars();

		ProjectionMode();
		ModelMode();
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glTranslatef(PlayerX, PlayerY, -20f + PlayerZ); // Move Right 1.5

		GL11.glTranslatef(9, -45, 0);
		GL11.glRotatef(MapRotatef, 0f, 1.0f, 0f);
		t.Render();

	}

	public void SaveGame() {
		GameBrowser fb = new GameBrowser();
		try {
			String path = fb.GetSavePath();
			if (path != null) {
				GameSave gs = CurrentSave;
				gs.setEntityData(Worlds, player);
				FileOutputStream fileOut = new FileOutputStream(path + ".dat");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(gs);
				out.close();
				fileOut.close();
			}
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	@Override
	public void Unload() {
		player = null;
		t = null;
		Worlds = null;

	}

	/**
	 * @param createNewGame
	 *            the createNewGame to set
	 */
	public void setCreateNewGame(boolean createNewGame) {
		CreateNewGame = createNewGame;
	}

}
