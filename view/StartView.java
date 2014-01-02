package view;

import model.*;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class StartView extends JPanel {
	private JottoModel model;
	private MessageView vMess = new MessageView(model);
	private JButton start = new JButton("Start Game");
	private JButton inst = new JButton("How to Play");
	private JButton opt = new JButton("Option");
	private JButton end = new JButton("Quit Game");

	public StartView(JottoModel jModel) {
		super();
		this.model = jModel;

		this.layoutView();
		this.registerControllers();

		this.model.addView(new IView() {
			public void updateView() {
				start.setEnabled(true);
				inst.setEnabled(true);
				opt.setEnabled(true);
				end.setEnabled(true);
			}

		});
	}

	private void layoutView() {
		//this.setLayout(new FormLayout());
		//this.add(Box.createVerticalStrut(100));
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.start, gbc);
		this.add(this.inst, gbc);
		this.add(this.opt, gbc);
		this.add(this.end, gbc);
	}

	private void registerControllers() {
		this.start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.setTargetWord(model.defaultlvl);
				model.setNumGuess(model.defaultlvl);
				model.gameStarted = true;
				model.veryFirstGame = false;
			}
		});

		this.inst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//Show HowToPlay Message on screen
				vMess.showHowtoPlay();
				}
		});

		this.opt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//Show Option Message on screen
				vMess.showOption(model);
				}
		});

		this.end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});
	}

}
