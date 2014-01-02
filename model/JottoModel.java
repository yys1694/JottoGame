//From SampleCode provided
package model;

import java.util.ArrayList;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.lang.String;
import java.lang.Character;
import java.util.Vector;
import java.lang.String;
import java.util.Random;

public class JottoModel extends Object {
	public static int NUM_LETTERS = 5;
	public static final String[] LEVELS = {
      "Easy", "Medium", "Hard", "Any Difficulty"};

    //Number of Guesses(Different by Difficulty)
    public int nGuess = 0;
    //Initially 20, but differ by difficulty
    //Medium level is 18, Hard level is 15;
    public int nGuessLeft;

    //Indicator of Status of Game
    public Boolean gameStarted = false;
    //For showing setting of game before start new game
    public Boolean veryFirstGame = true;

    //Indicator of Winning of Game
    public Boolean gameWin = false;
    public Boolean gameLose = false;

    //Default Level of Game
    public int defaultlvl = 0;

    //List of views
    private ArrayList<IView> views = new ArrayList<IView>();

    //List of Words guessed by users
    public ArrayList<String> guesslst = new ArrayList<String>();

    //Vector for checking if any letter matches with guessing word exactly
    public Vector<Integer> posExact = new Vector<Integer>(5);

 	//Reading a word list from words.txt file
    private WordList wLst = new WordList("model/words.txt");
    public Word target;
    public String targetWord = "";
    public String guessWord = "";
    public int nExact = 0;
    public int nPartial = 0;

    //Building JTable (Data Manipulation)
    public String[] columnNames = {"Answer",
                                    "Difficulty",
                                    "Number of Tries",
                                    "Game Result"};
    public Object[][] data = {};
    public DefaultTableModel dTableModel = new DefaultTableModel(data, columnNames);
    public JTable resultTable = new JTable(dTableModel);
    public JScrollPane jScroll = new JScrollPane(resultTable);



    public JottoModel(){
    }

    public void setTargetWord(int lvl) {
    	target = wLst.randomWord(lvl);
    	targetWord = target.getWord();
    }

    public void addList(String word) {
    	this.guesslst.add(word);
    	for(IView view : this.views) {
    		view.updateView();
    	}
    }

    public void addResult(Object[] result) {
        dTableModel.addRow(result);
    }

    //Add all 0 in posExact vector
    public void initVec() {
        posExact.add(0);
        posExact.add(0);
        posExact.add(0);
        posExact.add(0);
        posExact.add(0);
    }

    public int checkExactMatch(String tWord, String gWord) {
        nExact = 0;
        initVec();
        nGuessLeft--;

        for(int i = 0; i < tWord.length(); i++) {
            char tchar = Character.toLowerCase(tWord.charAt(i));
            char gchar = Character.toLowerCase(gWord.charAt(i));
            if(tchar == gchar) {
                posExact.set(i,1);
                nExact++;
            }
        }
        return nExact;
    }

    public int checkPartialMatch(String tWord, String gWord) {
        nPartial = 0;
        
        for(int i = 0; i < tWord.length(); i++) {
            if(posExact.get(i) == 0) {
                char tchar = Character.toLowerCase(tWord.charAt(i));
                for(int j = 0; j < gWord.length(); j++) {
                    char gchar = Character.toLowerCase(gWord.charAt(j));
                    if(tchar == gchar && posExact.get(j) == 0) {
                        nPartial++;
                        break;
                    }
                }
            }
        }
        posExact.clear();
        return nPartial;
    }

    //Return True if game ends; return false otherwise
    public Boolean ifOverGuesses(int lvl) {
        if((lvl == 0 && nGuess == 20) ||
           (lvl == 1 && nGuess == 15) ||
           (lvl == 2 && nGuess == 10)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setNumGuess(int lvl) {
        if(lvl == 3) {
            Random rdGen = new Random();
            defaultlvl = rdGen.nextInt(3);
        }
        if(lvl == 0) {
            nGuessLeft = 20;
        }
        else if(lvl == 1) {
            nGuessLeft = 15;
        }
        else if(lvl == 2) {
            nGuessLeft = 10;
        }
    }

    //Reset the game for Winning(Try with higher difficulty)
    public void reset() {
        this.guesslst.clear();
        this.posExact.clear();
        defaultlvl++;
        nGuess = 0;
        this.setNumGuess(defaultlvl);
        gameWin = false;
        this.setTargetWord(this.defaultlvl);
        this.updateAllViews();
    }

    //Reset the game for Lost(Try with the same difficulty)
    public void tryAgain() {
        this.guesslst.clear();
        this.posExact.clear();
        nGuess = 0;
        this.setNumGuess(defaultlvl);
        gameWin = false;
        gameLose = false;
        this.setTargetWord(this.defaultlvl);
        this.updateAllViews();
    }

	/** Add a new view of this triangle. */
	public void addView(IView view) {
		this.views.add(view);
		view.updateView();
	}

	/** Remove a view from this triangle. */
	public void removeView(IView view) {
		this.views.remove(view);
	}

	/** Update all the views that are viewing this triangle. */
	public void updateAllViews() {
		for (IView view : this.views) {
			view.updateView();
		}
	}
}