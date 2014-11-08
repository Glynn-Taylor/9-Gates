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
 * A state that handles how the death screen functions
 */
package game.states;

import game.graphics.GUI_Layer;
import game.states.Game.GameState;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class State_MENU_DEATHSCREEN extends State {
	private final GUI_Layer GUI = new GUI_Layer();

	@Override
	protected void Init() {
		int ButtonStartX = 930;
		int ButtonStartY = 224;
		int Spacing = 90;
		try {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			Texture button2 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/MenuButton.png"),
							false, GL11.GL_NEAREST);
			GUI.AddButton(ButtonStartX, ButtonStartY + Spacing * 1,
					button2.getImageWidth(), button2.getImageHeight(), button2);
			Texture button3 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/QuitButton.png"),
							false, GL11.GL_NEAREST);
			GUI.AddButton(ButtonStartX, ButtonStartY + Spacing * 2,
					button3.getImageWidth(), button3.getImageHeight(), button3);
			BackGroundImage = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Backgrounds/DeathScreen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void Update() {
		System.out.println("Running MENU");

	}

	@Override
	protected void ProcessInput() {
		GUI.ProcessInput(MouseLastX, MouseLastY, Mouse.isButtonDown(0));
		if (GUI.isButtonDown(0)) {
			GameState.SwitchToState(GameState.State_DeathScreen,
					GameState.State_Menu);
		}
		if (GUI.isButtonDown(1)) {
			GameState.State_DeathScreen.ExitState();
		}

	}

	@Override
	protected void Render() {

		OrthoMode();
		ModelMode();
		DrawBackground();
		GUI.Render();
		ProjectionMode();
		ModelMode();

	}

	@Override
	public void Unload() {

	}

}
