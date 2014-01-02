//View Update Not Working
package view;

import model.*;

import javax.swing.*;
import java.awt.event.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.Random;

public class MessageView extends JPanel {
	private JottoModel model;

	public MessageView(JottoModel jModel) {
		super();
		this.model = jModel;
	}

	//HowtoPlay Message on StartView(Triggered by "How to Play" button)
	public void showHowtoPlay() {
		JOptionPane.showMessageDialog(null,
                     "Basic rules:\n" +
                     "\t\t* 5-letter word ONLY\n" +
                     "\t\t* Correct letter at Correct poision: 1 Exact Match (1,0)\n" +
                     "\t\t* Correct letter at Incorrect position: 1 Partial Match (0,1)\n" +
                     "\t\t* Default Difficulty Level is Easy.\n" +
                     "\t\t* You can select your level with 'Setting'\n\n" +
                     "Example) Answer: 'TRUCK'\n"+
                     "\t\t* Your 1st Guess:  'WORKS'  -->  'WORKS(0,2)'\n" +
                     "\t\t* Your 2nd Guess:  'TOOLS'  -->  'TOOLS(1,0)'\n\n" +
                     "Good Luck!!! Hope you make a right guess!",
                     "How to play??",
                     JOptionPane.PLAIN_MESSAGE);
	}

	public void showOption(JottoModel model) {
		final String[] difficulty = {"Easy", "Medium", "Hard", "Any Difficulty"};
		String lvl = (String)JOptionPane.showInputDialog(null,
										  "Please select the level of difficulty",
										  "Option",
										  JOptionPane.QUESTION_MESSAGE,
										  null,
										  difficulty,
										  difficulty[0]);
		if(lvl.equals("Easy")) {
			model.defaultlvl = 0;
			model.nGuessLeft = 20;
		}
		else if(lvl.equals("Medium")) {
			model.defaultlvl = 1;
			model.nGuessLeft = 15;
		}
		else if(lvl.equals("Hard")) {
			model.defaultlvl = 2;
			model.nGuessLeft = 10;
		}
		else if(lvl.equals("Any Difficulty")) {
			Random rdGen = new Random();
			model.defaultlvl = rdGen.nextInt(3);
			if(model.defaultlvl == 0) {
				model.nGuessLeft = 20;
			}
			else if(model.defaultlvl == 1) {
				model.nGuessLeft = 15;
			}
			else if(model.defaultlvl == 2) {
				model.nGuessLeft = 10;
			}
		}
	}

	public void showInfo(JottoModel model) {
		int tmplvl = model.defaultlvl;
		String tmp = "";

		if(tmplvl == 0) {
			tmp = "Easy";
		}
		else if(tmplvl == 1) {
			tmp = "Medium";
		}
		else if(tmplvl == 2) {
			tmp = "Hard";
		}

		JOptionPane.showMessageDialog(null,
                     "Your Setting:\n" +
                     "\t\t* Difficulty Level:  " + tmp + "\n" +
                     "\t\t* Number of Guesses Allowed:  " + model.nGuessLeft + "\n" +
                     "\t\t* Remeber! Only 5-letter word!\n" +
                     "Good Luck~!!",
                     "Information",
                     JOptionPane.PLAIN_MESSAGE);	
	}

	//Winning Congrats on GameView(Triggered when the player guess the answer correctly)
	public void showWinning(JottoModel model) {
		int tryAgain = JOptionPane.showConfirmDialog(null, 
                                    "The answer was " + model.targetWord + "\n" +
                                    "You got the answer in " + model.nGuess + " tries!!\n" +
                                    "Play again?",
                                    "Congratulation!!!", 
                                    JOptionPane.YES_NO_OPTION);
        if(tryAgain == JOptionPane.YES_OPTION) {
            model.reset();
        }
        else if(tryAgain == JOptionPane.NO_OPTION) {
            System.exit(0);
		}
	}

	//GameOver Message on GameView(Triggered when the player uses all of chances; fails to win)
	public void showGameOver(JottoModel model) {
		int tryAgain = JOptionPane.showConfirmDialog(null,
                                    "The correct answer was " +
                                    model.targetWord + ".\n" +
                                    "Play again???",
                                    "Game Over...", 
                                    JOptionPane.YES_NO_OPTION);
       	if(tryAgain == JOptionPane.YES_OPTION) {
         	model.tryAgain();
      	}
      	else if(tryAgain == JOptionPane.NO_OPTION) {
         	System.exit(0);
        }
	}

	//Error Message on GameView(Triggered when the player guess invalid word(not 5-letter word))
	public void showError() {
		JOptionPane.showMessageDialog(null,
                     "A 5-letter word only!!",
                     "Invalid Guess ",
                     JOptionPane.PLAIN_MESSAGE);
	}


}