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
 * A state that handles how the prefab editor menu functions
 */
package game.states;

import game.graphics.GUI_Layer;
import game.states.Game.GameState;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class State_MENU_PREFAB_EDITOR extends State {
	private final GUI_Layer GUI = new GUI_Layer();

	@Override
	protected void Init() {
		int ButtonStartX = 930;
		int ButtonStartY = 224;
		int Spacing = 90;
		try {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			Texture button1 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/NewButton.png"),
							false, GL11.GL_NEAREST);
			GUI.AddButton(ButtonStartX, ButtonStartY + Spacing * 0,
					button1.getImageWidth(), button1.getImageHeight(), button1);
			Texture button2 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/LoadButton.png"),
							false, GL11.GL_NEAREST);
			GUI.AddButton(ButtonStartX, ButtonStartY + Spacing * 1,
					button2.getImageWidth(), button2.getImageHeight(), button2);
			Texture button3 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/BackButton.png"),
							false, GL11.GL_NEAREST);
			GUI.AddButton(ButtonStartX, ButtonStartY + Spacing * 2,
					button3.getImageWidth(), button3.getImageHeight(), button3);
			BackGroundImage = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Backgrounds/MenuBackground2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void Update() {
		System.out.println("Running MAP EDITOR MENU");

	}

	@Override
	protected void ProcessInput() {
		GUI.ProcessInput(MouseLastX, MouseLastY, Mouse.isButtonDown(0));
		if (GUI.isButtonDown(0)) {
			State_PREFAB_EDITOR pe = null;
			pe = (State_PREFAB_EDITOR) (GameState.State_MapEditor.GetState());
			String s = "width";
			while (!IsNumber(s)) {
				s = (String) JOptionPane.showInputDialog(null, "Enter width:",
						"New..", JOptionPane.PLAIN_MESSAGE, null, null, "10");
			}
			int w = Integer.parseInt(s);
			s = "height";
			while (!IsNumber(s)) {
				s = (String) JOptionPane.showInputDialog(null, "Enter height:",
						"New..", JOptionPane.PLAIN_MESSAGE, null, null, "10");
			}
			int h = Integer.parseInt(s);
			if (h >= 5 && w >= 5 && h * w < 120) {
				pe.Create(w, h);
				GameState.SwitchToState(GameState.State_Menu_MapEditor,
						GameState.State_MapEditor);
			} else {
				JOptionPane
						.showMessageDialog(
								null,
								"Room width and height must be greater than 5 and total tiles may not exceed 120.",
								"Dimensions not valid",
								JOptionPane.PLAIN_MESSAGE);
			}
		}
		if (GUI.isButtonDown(1)) {
			State_PREFAB_EDITOR pe = null;
			pe = (State_PREFAB_EDITOR) (GameState.State_MapEditor.GetState());
			pe.LoadPrefab();
			GameState.SwitchToState(GameState.State_Menu_MapEditor,
					GameState.State_MapEditor);
		}
		if (GUI.isButtonDown(2)) {
			GameState.SwitchToState(GameState.State_Menu_MapEditor,
					GameState.State_Menu);
		}
	}

	private boolean IsNumber(String string) {
		try {
			Integer.parseInt(string);
		} catch (Exception e) {
			return false;
		}
		return true;
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
