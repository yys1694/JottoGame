import javax.swing.JFrame;

import model.JottoModel;
import model.Word;
import view.*;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class Main {

	public static void main(String[] args) {
		model.JottoModel model = new JottoModel();
		StartView vStart = new StartView(model);
		WordView vWord = new WordView(model);
		ListView vList = new ListView(model);
		Boolean openning = true;

		JFrame frame = new JFrame("Jotto Game");

		//Frame for Main Menu(StartView)
		frame.setLayout(new BorderLayout());
		frame.add(vStart, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(300,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		//Wait for game start!
		while(openning) {
			if(model.gameStarted) {
				frame.setVisible(false);
				openning = false;
			}
		}

		frame.dispose();
		//Game View
		JFrame frame2 = new JFrame("Let's Play~!!");
		frame2.setLayout(new BorderLayout());
		frame2.add(vWord, BorderLayout.PAGE_START);
		frame2.add(vList, BorderLayout.CENTER);
		frame2.pack();
		frame2.setSize(370,500);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setVisible(true);

	}
}
