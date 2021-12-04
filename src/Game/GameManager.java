package Game;

import CharacterPack.*;
import CharacterPack.Character;
import ColorBoard.ColorEnum;
import ColorBoard.ColoredNode;
import ColorBoard.Direction;
import FactoryPack.CharacterFactory;
import FactoryPack.EnemyFactory;
import FactoryPack.PlayerFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**ALL GAME STUFF REVOLVES AROUND HERE..
 * A class to keep game components and manages screen manipulations.
 * @author ilkan Mert Okul*/
public class GameManager {

    //These are the scene elements to manipulate.
    /**Root of screen.*/
    private Group root;

    /**Scene of screen.*/
    private Scene scene;

    /**Stage of screen.*/
    private Stage stage;

    public GameManager(Stage stage) {

        this.root = new Group();
        this.scene = new Scene(root);
        this.stage = stage;

        generateGameBoard();
    }

    //COMPONENTS OF THE SCENE:
    /**X_DIM is x dimension of board */
    private final int X_DIM = 9; //DO NOT CHANGE THIS, OR AT LEAST MAKE IT DIVIDABLE BY 3.

    /**Y_DIM is y dimension of board */
    private final int Y_DIM = 6;

    /**WIDTH of the screen*/
    private final int WIDTH = 450; //DO NOT CHANGE THIS, OR AT LEAST MAKE IT DIVIDABLE BY 3.

    /**HEIGHT of the screen*/
    private final int HEIGHT = 800;

    /**This helps for placing the board to screen.*/
    private final int OFFSET_OF_BOARD = 300;

    /**The board that we are going to interact.*/
    private ColoredNode[][] theBoard;

    /**Old chosen block to swap, we need to keep reference to it.*/
    private ColoredNode chosenOldBlock;

    /**Message log to log all messages that comes from the game and actions.*/
    private MessageLog messageLog;

    /**This game has 3 enemies and 3 players.*/
    private final int CHAR_COUNT = 3; //It will be both 3 enemy and 3 player.

    /**The make charactes look better.*/
    private Rectangle[] characterBox;

    /**Characters of the game to play.*/
    private AbstractCharacter[] characters;

    /**The make charactes look better.*/
    private Rectangle[] enemyBox;

    /**Characters of the enemies to get and give attacks..*/
    private AbstractCharacter[] enemies;

    /**Enemies attack with a queue, this helps to keep track of it.*/
    private int currentEnemy = 0;

    /**Enemies attack players in a row, this helps to keep track of it.*/
    private int currentPlayerTarget = 0;

    /**ABSTRACT FACTORY PATTERN used for this game to create characters!!*/
    private CharacterFactory factory;

    //SwapController's global variables.
    private Queue<Integer> lastFoundX = new ConcurrentLinkedQueue<Integer>();
    private Queue<Integer> lastFoundY = new ConcurrentLinkedQueue<Integer>();
    private Queue<Direction> lastFoundDir = new ConcurrentLinkedQueue<Direction>();

    /**Damage rule to keep damaging scripts in one place.*/
    private DamageRule rule = new DamageRule(true);

    /**Moving actions are used each 2 click, this is why we keep track of it.*/
    private int movingAction = 0;

    /**Gameover label that pops up if game is over.*/
    private Label gameOver = new Label("Game Over!");

    /**To check if game is over or not.*/
    private boolean gameStatus = true;

    /**Refreshes all characters, clears messages, a clean new start...*/
    private Button restartButton = new Button("RESTART");

    /**Generates the board components.*/
    private void generateGameBoard(){

        theBoard = new ColoredNode[Y_DIM][X_DIM];
        characters = new Character[CHAR_COUNT];
        enemies = new Character[CHAR_COUNT];

        for(int i=0 ; i < Y_DIM; i++){
            for(int j=0; j<X_DIM; j++){

                //Initialize the block, then insert in the scene.
                theBoard[i][j] = new ColoredNode(getRandomColor(),getCoordinatesX(j), getCoordinatesY(i),WIDTH/X_DIM,WIDTH/X_DIM);
                root.getChildren().add(theBoard[i][j]);

                //Add listener to these blocks.
                theBoard[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(gameStatus){
                            swapController((ColoredNode) mouseEvent.getTarget());
                            movingAction++;
                            if(movingAction%2 == 0) afterMovingActions();
                        }
                    }
                });
            }
        }

        //Set some outlines to characters to make it more understandable.
        factory = new PlayerFactory();
        characterBox = new Rectangle[CHAR_COUNT];
        for(int i=0; i< CHAR_COUNT;i++){
            characterBox[i] = new Rectangle(40+i*140,20,100,100);
            characterBox[i].setFill(Color.TRANSPARENT);
            characterBox[i].setStroke(Color.BLACK);
            root.getChildren().add(characterBox[i]);

            characters[i] = factory.summonRandomCharacter(90+i*140,50);
            root.getChildren().add(characters[i]);
            root.getChildren().add(characters[i].information);
            root.getChildren().add(characters[i].health);
        }

        factory = new EnemyFactory();
        enemyBox = new Rectangle[CHAR_COUNT];
        for(int i=0; i< CHAR_COUNT;i++){
            enemyBox[i] = new Rectangle(40+i*140,162,100,100);
            enemyBox[i].setFill(Color.TRANSPARENT);
            enemyBox[i].setStroke(Color.BLACK);
            root.getChildren().add(enemyBox[i]);

            enemies[i] = factory.summonRandomCharacter(90+i*140,192);
            root.getChildren().add(enemies[i]);
            root.getChildren().add(enemies[i].information);
            root.getChildren().add(enemies[i].health);
        }

        //The game over label.
        gameOver.setTranslateX(WIDTH/2-40);
        gameOver.setTranslateY(WIDTH/2);
        gameOver.setScaleX(4);
        gameOver.setScaleY(4);
        gameOver.setStyle("-fx-font-weight: bold");
        gameOver.setTextFill(Color.TRANSPARENT);
        root.getChildren().add(gameOver);

        //The restar button.
        restartButton.setFocusTraversable(false);

        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStatus = true;
                gameOver.setTextFill(Color.TRANSPARENT);
                messageLog.clearLog();

                for(int i=0 ; i< CHAR_COUNT;i++){
                    characters[i].refreshStats();
                    enemies[i].refreshStats();
                }
            }
        });

        root.getChildren().add(restartButton);

        //The message log that notifies the user about the game.
        messageLog = new MessageLog(5,605);
        root.getChildren().add(messageLog);

        //Dimensions of stage.
        stage.setWidth(WIDTH + 16);
        stage.setHeight(HEIGHT);
        stage.setResizable(false);

        //Last works of stage.
        stage.setTitle("Candyy-Crush-like");
        stage.setScene(scene);
        stage.show();

        for(int i= 0; i < 5 ;i++) clearStartUp();
    }

    /*----------------------------------------------------------------------*/

    /**This method gets called after every swapping*/
    private void afterMovingActions(){

        //After move, lets check triples!
        comboController();
        while(!lastFoundX.isEmpty()){

            int currX = lastFoundX.poll();
            int currY = lastFoundY.poll();
            Direction currDir = lastFoundDir.poll();

            ColorEnum currColor = theBoard[currY][currX].blockColor;

            characters[currX/CHAR_COUNT].attack(rule,enemies[currX/CHAR_COUNT],currColor);
            messageLog.logMessage("You hit triple and "+enemies[currX/CHAR_COUNT].toString() + " attacked!");

            //Refresh those blocks.
            if(currDir == Direction.HORIZONTAL){
                for (int i = 0 ; i < 3 ; i++){
                    theBoard[currY][currX+i].changeColor(getRandomColor());
                }
            }else{
                for (int i= 0; i < 3 ; i++){
                    theBoard[currY+i][currX].changeColor(getRandomColor());
                }
            }
        }

        //Clean any new already triples.
        for(int i= 0; i < 5 ;i++) clearStartUp();

        //Now it is time for enemy to attack.

        int aliveEnemyCount=0;
        for (int i=0 ; i < CHAR_COUNT;i++){
            if(enemies[i].getIsAlive()) aliveEnemyCount++;
         }
        if(aliveEnemyCount == 0){

            messageLog.logMessage("All enemies died, new stage created.");

            //Refresh Stats.
            for (int i=0 ; i < CHAR_COUNT;i++){
                characters[i].refreshStats();
            }

            //Summon new set of enemies.
            for (int i=0 ; i< CHAR_COUNT ;i++){
                enemies[i].setDisabled();

                enemies[i] = factory.summonRandomCharacter(90+i*140,192);

                root.getChildren().add(enemies[i]);
                root.getChildren().add(enemies[i].information);
                root.getChildren().add(enemies[i].health);
            }

            return;
        }

        int alivePlayerCount=0;
        for (int i=0 ; i < CHAR_COUNT;i++){
            if(characters[i].getIsAlive()) alivePlayerCount++;
        }
        if(alivePlayerCount == 0){
            //GAME OVER!
            gameStatus = false;
            gameOver.setTextFill(Color.BLACK);
            return;
        }

        for(int i =0 ; i < CHAR_COUNT; i++){
            if(enemies[++currentEnemy % CHAR_COUNT].getIsAlive()) break;
        }
        for(int i =0 ; i < CHAR_COUNT; i++){
            if(characters[++currentPlayerTarget % CHAR_COUNT].getIsAlive()) break;
        }

        enemies[currentEnemy%CHAR_COUNT].attack(rule,characters[currentPlayerTarget%CHAR_COUNT],enemies[currentEnemy%CHAR_COUNT].getType().getColorEnum());

        alivePlayerCount=0;
        for (int i=0 ; i < CHAR_COUNT;i++){
            if(characters[i].getIsAlive()) alivePlayerCount++;
        }
        if(alivePlayerCount == 0){
            //GAME OVER!
            gameStatus = false;
            gameOver.setTextFill(Color.BLACK);
        }
    }

    /*----------------------------------------------------------------------*/

    /**Clears the occurences of triple color combos start of the round.*/
    private void clearStartUp(){
        //This piece clears most of the unluckily spawned triples.
        comboController();
        while(!lastFoundX.isEmpty()){

            int currX = lastFoundX.poll();
            int currY = lastFoundY.poll();
            Direction currDir = lastFoundDir.poll();

            ColorEnum newColor = ColorEnum.BLUE;
            switch (theBoard[currY][currX].blockColor){
                case BLUE:
                    newColor = ColorEnum.RED;
                    break;
                case RED:
                    newColor = ColorEnum.GREEN;
                    break;
                case GREEN:
                    newColor = ColorEnum.BLUE;
                    break;
            }

            if(currDir == Direction.HORIZONTAL){
                theBoard[currY][currX+1].changeColor(newColor);
            }else{
                theBoard[currY+1][currX].changeColor(newColor);
            }
        }
    }

    /*----------------------------------------------------------------------*/

    /**If there is a 3 block combo, it adds to a contanier(queue as chosen)
     * to hand over handler methods.*/
    private void comboController(){

        boolean horizontalFlag;
        boolean verticalFlag;

        for(int i = 0; i< Y_DIM ; i++){
            for (int j = 0 ; j< X_DIM ;j++){

                ColorEnum currentColor = theBoard[i][j].blockColor;
                horizontalFlag = true;
                verticalFlag = true;

                //Horizontal Control:
                int k = j;
                if( k <= X_DIM - 3){
                    for(k= 1 ; k<3 ; k++){
                        if(!(theBoard[i][j+k].blockColor == currentColor)){
                            horizontalFlag = false;
                            break;
                        }
                    }
                }else horizontalFlag = false;

                if(horizontalFlag){
                    System.out.println("Found one horizontal combo at" + " x:" + j + " y:" + i);
                    lastFoundX.add(j);
                    lastFoundY.add(i);
                    lastFoundDir.add(Direction.HORIZONTAL);
                    j = j + 2;
                }

                //Vertical Control:
                int l = i;
                if(i <= Y_DIM -3){
                    for (l = 1 ; l <3 ; l++){
                        if(!theBoard[i+l][j].blockColor.equals(currentColor)){
                            verticalFlag = false;
                            break;
                        }
                    }
                }else verticalFlag = false;

                if(verticalFlag){
                    System.out.println("Found one vertical combo at" + " x:" + j + " y:" + i);
                    lastFoundX.add(j);
                    lastFoundY.add(i);
                    lastFoundDir.add(Direction.VERTICAL);
                }

            }
        }

        return;
    }

    /**This method controls the avaiability of swapping of chosen nodes.
     * It prints some information if it is not possible to swap two nodes.
     * @param node chosen node*/
    private void swapController(ColoredNode node){

        System.out.println(node.getX() + " "+ node.getY());

        //If chosen old block is empty, assign new one to it.
        if(chosenOldBlock == null){
            messageLog.logMessage("You choose first block to swap: "  +
                                " x= " + getIndexX((int)node.getX()) +
                                " y= " + getIndexY((int)node.getY()));
            chosenOldBlock = node;
            chosenOldBlock.toggleChosen();
            return;
        }

        //Inform the player about chosen block.
        messageLog.logMessage("You choose second block to swap: "  +
                " x= " + getIndexX((int)node.getX()) +
                "\ty= " + getIndexY((int)node.getY()));

        //Cannot swap because not neighbour.
        if(!chosenOldBlock.isNeighbour(node)){
            chosenOldBlock.toggleChosen();
            chosenOldBlock = null;
            messageLog.logMessage("Blocks you choose are not neighbour.");
            return;
        }

        //Cannot swap because equal color.
        if(node.isEqualColor(chosenOldBlock)){
            chosenOldBlock.toggleChosen();
            chosenOldBlock = null;
            messageLog.logMessage("Blocks you choose are SAME color!");
            return;
        }

        //ELSE, SUCCESS

        //Swap the attributes.
        theBoard[getIndexY((int)chosenOldBlock.getY())][getIndexX((int)chosenOldBlock.getX())].swapAttributes(
                theBoard[getIndexY((int)node.getY())][getIndexX((int)node.getX())]
        );

        //Swap the indexes.
        ColoredNode temp  = theBoard[getIndexY((int)chosenOldBlock.getY())][getIndexX((int)chosenOldBlock.getX())];
        theBoard[getIndexY((int)chosenOldBlock.getY())][getIndexX((int)chosenOldBlock.getX())]=
                theBoard[getIndexY((int)node.getY())][getIndexX((int)node.getX())];
        theBoard[getIndexY((int)node.getY())][getIndexX((int)node.getX())] = temp;

        //Reset color picker.
        chosenOldBlock = null;
    }

    /*-----------------------------------------------------------*/

    /**This gives the coordinates of Y according to given index.
     * @param i as coordinates of Y
     * @return converted Y as location*/
    private int getCoordinatesY(int i){
        return (i*WIDTH/X_DIM) + OFFSET_OF_BOARD;
    }

    /**This gives the INDEX of Y according to given coord.
     * @param coordY coordinates as Y
     * @return converted Y index.*/
    private int getIndexY(int coordY){
        return (coordY-OFFSET_OF_BOARD) * X_DIM / WIDTH;
    }

    /**This gives the coordinates of X according to given index.
     * @param i as coordinates of X
     * @return converted X as location*/
    private int getCoordinatesX(int i){
        return i*WIDTH/X_DIM;
    }

    /**This gives the INDEX of X according to given coord.
     * @param coordX coordinates as X
     * @return converted x index.*/
    private int getIndexX(int coordX){
        return (coordX *X_DIM / WIDTH);
    }

    /**This class gives a random enum color.
     * @return randomly generated ColorEnum*/
    static private ColorEnum getRandomColor(){

        int totalColors = ColorEnum.values().length;
        int randomColor = new Random().nextInt(totalColors);

        return ColorEnum.values()[randomColor];
    }
}
