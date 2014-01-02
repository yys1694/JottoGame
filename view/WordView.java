package view;

import model.*;

import javax.swing.*;
import java.awt.event.*;

public class WordView extends JPanel {
	private JottoModel model;
	private MessageView vMess = new MessageView(model);
	private JTextField guessTF = new JTextField(10);

	//Guessing Word
	public String tryWord;

	public WordView(JottoModel jModel) {
		super();
		this.model = jModel;
		this.layoutView();
		this.registerControllers();

		this.model.addView(new IView() {
			public void updateView() {
				guessTF.setText("Guess Here!");
			}
		});
	}

	//Default Flowlayout
	private void layoutView() {
		this.add(new JLabel("Guess:"));
		this.add(this.guessTF);
	}

	private void registerControllers() {
		this.guessTF.addActionListener(new BaseController());
	}

	private class BaseController implements ActionListener {
		public void actionPerformed(ActionEvent evt) {

			//Cheat: See the answer on textfield
			tryWord = guessTF.getText();
			if(tryWord.equalsIgnoreCase("help me plz")) {
				guessTF.setText(model.targetWord);
			}
			else if(tryWord.length() == 5) {
				model.guessWord = tryWord;
				model.nGuess++;
				
				//If you get the answer correctly
				if(tryWord.equalsIgnoreCase(model.targetWord)) {
					guessTF.setText("");
					model.nExact = 5;
					model.nPartial = 0;
					model.gameWin = true;
					model.addList(tryWord);
					//Show Winning Congrats 
					vMess.showWinning(model);
				}
				//If you spend all your chances
				else if(model.ifOverGuesses(model.defaultlvl)) {
					model.checkExactMatch(model.targetWord, tryWord);
					model.checkPartialMatch(model.targetWord, tryWord);
					model.gameLose = true;
					model.addList(tryWord);
					//Show GameOver Message on screen
					vMess.showGameOver(model);
				}
				else {
					//Jotto Game Progress
					model.checkExactMatch(model.targetWord, tryWord);
					model.checkPartialMatch(model.targetWord, tryWord);
					model.addList(tryWord);
					guessTF.setText("");
				}
			}
			else {
				//Show Error Message on screen
				vMess.showError();
			}
		}
	}
}