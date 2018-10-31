import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 * David Hau
 * 10/30/2018
 * CS2450.02(F18)
 */
public class Main extends Application
{
	public boolean gameOver = false;
	public int overallScoreCount = 0;
	public int rolls = 3;
	public int currentScoreCount = 0;
	
	public Image diceOneImage = new Image("file:./Dice1.png");
	public Image diceTwoImage = new Image("file:./Dice2.png");
	public Image diceThreeImage = new Image("file:./Dice3.png");
	public Image diceFourImage = new Image("file:./Dice4.png");
	public Image diceFiveImage = new Image("file:./Dice5.png");
	public Image diceSixImage = new Image("file:./Dice6.png");
	
	public Image diceOneHeldImage = new Image("file:./Dice1Held.png");
	public Image diceTwoHeldImage = new Image("file:./Dice2Held.png");
	public Image diceThreeHeldImage = new Image("file:./Dice3held.png");
	public Image diceFourHeldImage = new Image("file:./Dice4held.png");
	public Image diceFiveHeldImage = new Image("file:./Dice5held.png");
	public Image diceSixHeldImage = new Image("file:./Dice6held.png");
	
	public ImageView diceOne = new ImageView(rollDice());
	public ImageView diceTwo = new ImageView(rollDice());
	public ImageView diceThree = new ImageView(rollDice());
	public ImageView diceFour = new ImageView(rollDice());
	public ImageView diceFive = new ImageView(rollDice());
	
	public ImageView[] diceImages = {diceOne, diceTwo, diceThree, diceFour, diceFive};

	public  BorderPane borderPane = new BorderPane();
	
	public MenuBar menuBar = new MenuBar();
	public Menu fileMenu = new Menu("File");
	public Menu helpMenu = new Menu("Help");
	public MenuItem newGame = new MenuItem("New Game");
	public MenuItem exit = new MenuItem("Exit");
	public MenuItem howToPlay = new MenuItem("How to Play");
	
	public Label overallScore = new Label("Overall Score: " + overallScoreCount);
	public Label currentScore = new Label("Your Score: " + currentScoreCount);
	public Label rollsLeft = new Label("Rolls Remaining: " + rolls);
	public Label handCombo = new Label("");
	
	Button actionButton = new Button("Roll Dice");
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)
	{
		
		primaryStage.setTitle("Dice Game");
		
		menuBar.getMenus().addAll(fileMenu, helpMenu);
		fileMenu.getItems().addAll(newGame,  new SeparatorMenuItem(), exit);
		helpMenu.getItems().addAll(howToPlay);

		
		newGame.setOnAction(event -> {
			newGame();
		});
		
		exit.setOnAction(event -> {
			System.exit(0);
		});
		
		diceOne.setPreserveRatio(true);
		diceTwo.setPreserveRatio(true);
		diceThree.setPreserveRatio(true);
		diceFour.setPreserveRatio(true);
		diceFive.setPreserveRatio(true);
		
		diceOne.setFitWidth(100);
		diceTwo.setFitWidth(100);
		diceThree.setFitWidth(100);
		diceFour.setFitWidth(100);
		diceFive.setFitWidth(100);
		
		diceOne.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			changeImage(diceImages[0]);
		});
		
		diceTwo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			changeImage(diceImages[1]);
		});
		
		diceThree.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			changeImage(diceImages[2]);
		});
		
		diceFour.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			changeImage(diceImages[3]);
		});
		
		diceFive.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			changeImage(diceImages[4]);
		});
		
		actionButton.setOnAction(event -> {
			tally();
			
			if(gameOver && rolls < 0)
			{
				newGame();
			}
		});
		
		HBox dices = new HBox(20, diceOne, diceTwo, diceThree, diceFour, diceFive);
		
		overallScore.setStyle("-fx-font-size: 20pt; -fx-text-fill: rgba(225, 225, 225, 1); -fx-font-family: Verdana");
		currentScore.setStyle("-fx-font-size: 20pt; -fx-text-fill: rgba(225, 225, 225, 1); -fx-font-family: Verdana");
		rollsLeft.setStyle("-fx-font-size: 20pt; -fx-text-fill: rgba(225, 225, 225, 1); -fx-font-family: Verdana");
		handCombo.setStyle("-fx-font-size: 20pt; -fx-text-fill: rgba(225, 225, 225, 1); -fx-font-family: Verdana");
		actionButton.setStyle("-fx-font-size: 20pt; ; -fx-font-family: Verdana");
		
		VBox vbox = new VBox(20, overallScore, dices, actionButton, currentScore, rollsLeft, handCombo);
		vbox.setPadding(new Insets(10));
		
		vbox.setAlignment(Pos.CENTER);
		
		borderPane.setTop(menuBar);
		borderPane.setCenter(vbox);
		borderPane.setStyle("-fx-background-color: linear-gradient(#141478,\r\n" + 
				"#1e1e99)");
		
		Scene myScene = new Scene(borderPane);
		
		howToPlay.setOnAction(event -> {
			instructions();
		});
		
		primaryStage.setScene(myScene);
		primaryStage.show();
	}
	
	public void instructions()
	{
		Alert alert = new Alert(AlertType.INFORMATION, showInformation());
		alert.setTitle("How to Play");
		alert.setHeaderText("");
		alert.show();
	}
	
	public String showInformation()
	{
		return "Rules:\r\n"
				+ "\r\n"
				+ "The player has three rolls per turn. \r\n"
				+ "After the first roll, if the user clicks a dice, it will remain "
				+ "\"held\" and not roll the next time the user rolls the dice."
				+ " Its image should update accordingly. \r\n"
				+ "Once three rolls are up, determine what is the best \"hand\" the "
				+ "user has. Inform the user of what hand they have and how many "
				+ "points they received this round.\r\n"
				+ "The \"Roll\" button should say \"Play Again\" once the player's "
				+ "turn is over. This should clear the Dice, allowing the user to start over.\r\n"
				+ "Keep track of how many points overall the user has (i.e. keep a "
				+ "running total of how much they scored each turn)\n\n"
				+ "Hands:\r\n\n" 
				+ "5 of a kind - 10 points\r\n"
				+ "Straight (5 numbers in a row i.e. 1, 2, 3, 4, 5 or 2, 3, 4, 5, 6) - 8 points\r\n"
				+ "Four of a Kind - 7 points\r\n"
				+ "Full House (3 of one kind, 2 of another) - 6 points\r\n"
				+ "3 of a kind - 5 points\r\n"
				+ "2 pair (2 of one kind, 2 of another, and 1 other value) - 4 points\r\n"
				+ "2 of a kind - 1 point";
	}
	
	public void changeImage(ImageView currentImage)
	{
		if(currentImage.getImage().equals(diceOneImage))
		{
			currentImage.setImage(diceOneHeldImage);
		}
		else if (currentImage.getImage().equals(diceOneHeldImage))
		{
			currentImage.setImage(diceOneImage);
		}
		
		if(currentImage.getImage().equals(diceTwoImage))
		{
			currentImage.setImage(diceTwoHeldImage);
		}
		else if (currentImage.getImage().equals(diceTwoHeldImage))
		{
			currentImage.setImage(diceTwoImage);
		}
		
		if(currentImage.getImage().equals(diceThreeImage))
		{
			currentImage.setImage(diceThreeHeldImage);
		}
		else if (currentImage.getImage().equals(diceThreeHeldImage))
		{
			currentImage.setImage(diceThreeImage);
		}
		
		if(currentImage.getImage().equals(diceFourImage))
		{
			currentImage.setImage(diceFourHeldImage);
		}
		else if (currentImage.getImage().equals(diceFourHeldImage))
		{
			currentImage.setImage(diceFourImage);
		}
		
		if(currentImage.getImage().equals(diceFiveImage))
		{
			currentImage.setImage(diceFiveHeldImage);
		}
		else if (currentImage.getImage().equals(diceFiveHeldImage))
		{
			currentImage.setImage(diceFiveImage);
		}
		
		if(currentImage.getImage().equals(diceSixImage))
		{
			currentImage.setImage(diceSixHeldImage);
		}
		else if (currentImage.getImage().equals(diceSixHeldImage))
		{
			currentImage.setImage(diceSixImage);
		}	
	}
	
	public void tally()
	{
		roll();
		
		int[] diceNums = {0, 0, 0, 0, 0};
		
		for(int arrayNum = 0; arrayNum < diceNums.length; arrayNum++)
		{
			if(diceImages[arrayNum].getImage().equals(diceOneImage) || diceImages[arrayNum].getImage().equals(diceOneHeldImage))
			{
				diceNums[arrayNum] = 1;
			}
			else if(diceImages[arrayNum].getImage().equals(diceTwoImage) || diceImages[arrayNum].getImage().equals(diceTwoHeldImage))
			{
				diceNums[arrayNum] = 2;
			}
			else if(diceImages[arrayNum].getImage().equals(diceThreeImage) || diceImages[arrayNum].getImage().equals(diceThreeHeldImage))
			{
				diceNums[arrayNum] = 3;
			}
			else if(diceImages[arrayNum].getImage().equals(diceFourImage) || diceImages[arrayNum].getImage().equals(diceFourHeldImage))
			{
				diceNums[arrayNum] = 4;
			}
			else if(diceImages[arrayNum].getImage().equals(diceFiveImage) || diceImages[arrayNum].getImage().equals(diceFiveHeldImage))
			{
				diceNums[arrayNum] = 5;
			}
			else if(diceImages[arrayNum].getImage().equals(diceSixImage) || diceImages[arrayNum].getImage().equals(diceSixHeldImage))
			{
				diceNums[arrayNum] = 6;
			}
		}
		
		int[] tallyCount = {0, 0, 0, 0, 0, 0};
		
		for(int num = 0; num < diceNums.length; num++)
		{
			if(diceNums[num] == 1)
			{
				tallyCount[0] = tallyCount[0] + 1;
			}
			else if(diceNums[num] == 2)
			{
				tallyCount[1] = tallyCount[1] + 1;
			}
			else if(diceNums[num] == 3)
			{
				tallyCount[2] = tallyCount[2] + 1;
			}
			else if(diceNums[num] == 4)
			{
				tallyCount[3] = tallyCount[3] + 1;
			}
			else if(diceNums[num] == 5)
			{
				tallyCount[4] = tallyCount[4] + 1;
			}
			else if(diceNums[num] == 6)
			{
				tallyCount[5] = tallyCount[5] + 1;
			}
		}
		
		int score = 0;
		String combo = "";
		
		boolean tallying = true;
		while(tallying)
		{
			int straightCount = 0;
			int fullHouseCount = 0;
			int twoPairCount = 0;
			
			// 5 of a kind - 10 points
			for(int num = 0; num < tallyCount.length; num++)
			{
				if(tallyCount[num] >= 5)
				{
					score = 10;
					combo = "Five of a Kind";
					tallying = false;
					break;
				}
			}
			
			if(score != 0)
			{
				break;
			}
			
			// Straight (5 numbers in a row i.e. 1, 2, 3, 4, 5 or 2, 3, 4, 5, 6) - 8 points
			for(int num = 0; num < 5; num++)
			{
				straightCount = 1;
				if(tallyCount[num] < 1)
				{
					straightCount = 0;
					break;
				}
			}
			
			for(int num = 1; num < 6; num++)
			{
				straightCount = 1;
				if(tallyCount[num] < 1)
				{
					straightCount = 0;
					break;
				}
			}
			
			if(straightCount > 0)
			{
				score = 8;
				combo = "Straight";
				tallying = false;
			}
			
			if(score != 0)
			{
				break;
			}
			
			// Four of a Kind - 7 points
			for(int num = 0; num < tallyCount.length; num++)
			{
				if(tallyCount[num] >= 4)
				{
					score = 7;
					combo = "Four of a Kind";
					tallying = false;
					break;
				}
			}
			
			if(score != 0)
			{
				break;
			}
			
			// Full House (3 of one kind, 2 of another) - 6 points
			for(int num = 0; num < tallyCount.length; num++)
			{
				if(tallyCount[num] >= 3)
				{
					fullHouseCount = fullHouseCount + 1;
				}
			}
			
			for(int num = 0; num < tallyCount.length; num++)
			{
				if(tallyCount[num] == 2)
				{
					fullHouseCount = fullHouseCount + 1;
				}
			}
			
			if(fullHouseCount >= 2)
			{
				score = 6;
				combo = "Full House";
				tallying = false;
			}
			
			if(score != 0)
			{
				break;
			}
			
			// 3 of a kind - 5 points
			for(int num = 0; num < tallyCount.length; num++)
			{
				if(tallyCount[num] >= 3)
				{
					score = 3;
					combo = "Three of a Kind";
					tallying = false;
					break;
				}
			}
			
			if(score != 0)
			{
				break;
			}
			
			// 2 pair (2 of one kind, 2 of another, and 1 other value) - 4 points
			for(int num = 0; num < tallyCount.length; num++)
			{
				if(tallyCount[num] >= 2)
				{
					twoPairCount = twoPairCount + 1;
					if(twoPairCount >= 2)
					{
						score = 4;
						combo = "Two Pair";
						tallying = false;
						break;
					}
				}
			}
			
			if(score != 0)
			{
				break;
			}
			
			// 2 of a kind - 1 point
			for(int num = 0; num < tallyCount.length; num++)
			{
				if(tallyCount[num] >= 2)
				{
					score = 1;
					combo = "Two of a Kind";
					tallying = false;
					break;
				}
			}
			
			tallying = false;
			break;
		}
		
		overallScoreCount = overallScoreCount + score;
		currentScoreCount = score;
		rolls = rolls - 1;
		
		handCombo.setText(combo);
		overallScore.setText("Overall Score: " + overallScoreCount);
		currentScore.setText("Your Score: " + currentScoreCount);
		rollsLeft.setText("Rolls Remaining: " + rolls);
		
		if(rolls <= 0)
		{
			actionButton.setText("Play Again");
			gameOver = true;
		}
	}
	
	public void roll()
	{
		Random random = new Random();
		for(int num = 0; num < diceImages.length; num++)
		{
			int rand = (random.nextInt() % 6) + 1;
			if(!((diceImages[num].getImage().equals(diceOneHeldImage)) 
					|| (diceImages[num].getImage().equals(diceTwoHeldImage)) 
					|| (diceImages[num].getImage().equals(diceThreeHeldImage)) 
					|| (diceImages[num].getImage().equals(diceFourHeldImage)) 
					|| (diceImages[num].getImage().equals(diceFiveHeldImage)) 
					|| (diceImages[num].getImage().equals(diceSixHeldImage))))
			{
				if(rand == 1)
				{
					diceImages[num].setImage(diceOneImage);
				}
				else if(rand == 2)
				{
					diceImages[num].setImage(diceTwoImage);
				}
				else if(rand == 3)
				{
					diceImages[num].setImage(diceThreeImage);
				}
				else if(rand == 4)
				{
					diceImages[num].setImage(diceFourImage);
				}
				else if(rand == 5)
				{
					diceImages[num].setImage(diceFiveImage);
				}
				else if(rand == 6)
				{
					diceImages[num].setImage(diceSixImage);
				}
			}
		}
	}
	
	public Image rollDice()
	{
		Random random = new Random();
		int rand = (random.nextInt() % 6) + 1;
		
		if(rand == 1)
		{
			return diceOneImage;
		}
		else if(rand == 2)
		{
			return diceTwoImage;
		}
		else if(rand == 3)
		{
			return diceThreeImage;
		}
		else if(rand == 4)
		{
			return diceFourImage;
		}
		else if(rand == 5)
		{
			return diceFiveImage;
		}
		else if(rand == 6)
		{
			return diceSixImage;
		}
		else
		{
			return diceOneImage;
		}
	}
	
	public void newGame()
	{
		
		for(int num = 0; num < diceImages.length; num++)
		{
			diceImages[num].setImage(rollDice());
		}
		
		gameOver = false;
		overallScoreCount = 0;
		rolls = 3;
		currentScoreCount = 0;
		
		overallScore.setText("Overall Score: " + overallScoreCount);
		currentScore.setText("Your Score: " + currentScoreCount);
		rollsLeft.setText("Rolls Remaining: " + rolls);
		handCombo.setText("");
		actionButton.setText("Roll Dice");
		
	}
}