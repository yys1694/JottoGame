
package view;

import model.*;

import javax.swing.*;
import java.awt.event.*;
import java.lang.String;
import java.util.ArrayList;


public class ListView extends JPanel {
	private JottoModel model;
	private MessageView vMess = new MessageView(model);
	private JLabel countLB = new JLabel();
	private JLabel listLB = new JLabel();
	private String wordlst;

	//For Game Result
	private String answer;
	private String lvl;
	private int totalSpent;

	public ListView(JottoModel jModel) {
		super();
		this.model = jModel;
		this.layoutView();

		this.model.addView(new IView() {
			public void updateView() {
				if(model.guesslst.isEmpty()) {
					countLB.setText("List of Guesses will be shown here!");
					listLB.setText("No Guesses Yet!");
					wordlst = "<html>";
					if(!model.veryFirstGame) {
						vMess.showInfo(model);
					}
				}
				else{
					String tmp = model.guesslst.get(model.guesslst.size() - 1);
					String ntmp1 = String.valueOf(model.nExact);
					String ntmp2 = String.valueOf(model.nPartial);
					wordlst += tmp;
					wordlst += "(";
					wordlst += ntmp1;
					wordlst += " , ";
					wordlst += ntmp2;
					wordlst += ")";
					if(model.guesslst.size()%4 == 0) {
						wordlst += "<br />";
					}
					//Show Game Result(Store it in table)
					if(model.gameWin) {
						countLB.setText("CORRECT!!!!!");
						if(model.defaultlvl == 0) {
							lvl = "Easy";
						}
						else if(model.defaultlvl == 1) {
							lvl = "Medium";
						}
						else {
							lvl = "Hard";
						}
						answer = model.targetWord;
						totalSpent = model.nGuess;
						Object[] winResult = {answer, lvl, totalSpent, "Win"};
						model.addResult(winResult);
					}
					else if(model.nGuessLeft == 1) {
						countLB.setText("This is your last chance!! Think carefully!!");
					}
					//Show Game Result(Store it in table)
					else if(model.nGuessLeft == 0) {
						countLB.setText("No More Guesses..");
						answer = model.targetWord;
						if(model.defaultlvl == 0) {
							lvl = "Easy";
						}
						else if(model.defaultlvl == 1) {
							lvl = "Medium";
						}
						else {
							lvl = "Hard";
						}
						totalSpent = model.nGuess;
						Object[] loseResult = {answer, lvl, totalSpent, "Lose"};
						model.addResult(loseResult);
					}
					else {
						countLB.setText("You have " + model.nGuessLeft + " guesses left.");
					}

					listLB.setText(wordlst + "</html>");
				}
			}
		});
	}

	private void layoutView() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.countLB);
		this.add(this.listLB);
		this.add(Box.createVerticalGlue());
		this.add(model.jScroll);
	}
}