package physicssimnew;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameController extends JFrame implements MouseListener {

    boolean runGame = true;
    int boardSizeX = 600;
    int boardSizeY = 600;
    final long cycleRate = 60;
    java.util.Timer timer;

    int gameAreaBufferSize = 40;
    int windowBarHeight = 20;

    ImageIcon ballImage;
    JLabel ballLabel;

    ImageIcon targetImage;
    JLabel targetLabel;

    int backgroundWidth = 800;
    int backgroundHeight = 800;
    ImageIcon backgroundImage;
    JLabel backgroundLabel;

    LogicHandler logicHandler = new LogicHandler();


    GameController() {

	super();

	setupJFrame();
	createEntityLabels();
	setLocationsOfEntityLabels();
	loadImages();
	createTimerTask();
    }

    void setupJFrame() {

	int totalBufferSize = (2 * gameAreaBufferSize);

	setSize(boardSizeX + totalBufferSize, boardSizeY + totalBufferSize + windowBarHeight);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	this.setResizable(false);
	addMouseListener(this);
    }

    void createEntityLabels() {

	backgroundLabel = new JLabel();
	this.add(backgroundLabel);

	targetLabel = new JLabel();
	this.add(targetLabel);

	ballLabel = new JLabel();
	this.add(ballLabel);
    }

    void setLocationsOfEntityLabels() {

	int mainBallDiameter = logicHandler.mainBall.size;
	int mainBallRadius = mainBallDiameter / 2;
	int mainBallX = (int) logicHandler.mainBall.xPos
		+ gameAreaBufferSize
		- mainBallRadius
		+ (int) logicHandler.screenShakeEntity.xPos;
	int mainBallY = (int) logicHandler.mainBall.yPos
		+ gameAreaBufferSize
		- mainBallRadius
		+ (int) logicHandler.screenShakeEntity.yPos;
	ballLabel.setBounds(mainBallX, mainBallY, mainBallDiameter, mainBallDiameter);

	int targetDiameter = logicHandler.target.size;
	int targetRadius = targetDiameter / 2;
	int targetX = (int) logicHandler.target.xPos
		+ gameAreaBufferSize
		- targetRadius
		+ (int) logicHandler.screenShakeEntity.xPos;
	int targetY = (int) logicHandler.target.yPos
		+ gameAreaBufferSize
		- targetRadius
		+ (int) logicHandler.screenShakeEntity.yPos;
	targetLabel.setBounds(targetX, targetY, targetDiameter, targetDiameter);

	int baseLocationX = -100;
	int baseLocationY = -100;
	int backgroundX = baseLocationX
		+ (int) logicHandler.screenShakeEntity.xPos
		+ gameAreaBufferSize;
	int backgroundY = baseLocationY
		+ (int) logicHandler.screenShakeEntity.yPos
		+ gameAreaBufferSize;
	backgroundLabel.setBounds(backgroundX, backgroundY, backgroundWidth, backgroundHeight);
    }

    void loadImages() {

	try {
	    URL url;
	    Image image;

	    int mainBallDiameter = logicHandler.mainBall.size;
	    url = this.getClass().getResource("Ball.png");
	    image = ImageIO.read(url).getScaledInstance(mainBallDiameter, mainBallDiameter, Image.SCALE_SMOOTH);
	    ballImage = new ImageIcon(image);
	    ballLabel.setIcon(ballImage);

	    int targetDiameter = logicHandler.target.size;
	    url = this.getClass().getResource("Target.png");
	    image = ImageIO.read(url).getScaledInstance(targetDiameter, targetDiameter, Image.SCALE_SMOOTH);
	    targetImage = new ImageIcon(image);
	    targetLabel.setIcon(targetImage);

	    url = this.getClass().getResource("background.png");
	    image = ImageIO.read(url).getScaledInstance(backgroundWidth, backgroundHeight, Image.SCALE_SMOOTH);
	    backgroundImage = new ImageIcon(image);
	    backgroundLabel.setIcon(backgroundImage);

	} catch (Exception e) {
	    System.out.println("Load failed");
	    System.err.println(e);
	}
    }

    void createTimerTask() {

	TimerTask timerTask = new TimerTask() {

	    public void run() {

		if (runGame) {

		    logicHandler.physics(boardSizeX, boardSizeY);

		    setLocationsOfEntityLabels();

		    if (logicHandler.gameIsOver()) {
			runGame = false;
		    }
		}
	    }
	};
	timer = new java.util.Timer();
	timer.schedule(timerTask, 0, 1000 / cycleRate);
    }

    @Override
    public void mousePressed(MouseEvent e) {

	if (runGame) {

	    int clickX = e.getX() - gameAreaBufferSize;
	    int clickY = e.getY() - gameAreaBufferSize - windowBarHeight;

	    logicHandler.throwBall(clickX, clickY);
	} else {

	    logicHandler.resetAllGameObjects();
	    runGame = true;
	}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {

	GameController gameWindow = new GameController();
    }

}
