package codebase;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;
import javax.swing.JFrame;
import static java.lang.Math.abs;

public class GameController extends JFrame implements MouseListener {

    boolean runGame = true;
    int boardSize_X = 600;
    int boardSize_Y = 600;
    final long cycleRate = 60;
    static java.util.Timer timer = new java.util.Timer();
    private GameCanvas gameCanvas = new GameCanvas();

    static Ball mainBall;
    static Target target;

    GameController() {

	super();
	this.setSize(boardSize_X, boardSize_Y + 30);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
	this.setResizable(false);
	this.addMouseListener(this);

	gameCanvas.setBounds(0, 0, boardSize_X, boardSize_Y);
	add(gameCanvas);
	gameCanvas.loadImages();


	mainBall = new Ball();
	mainBall.xPosition = 300;
	mainBall.yPosition = 300;
	mainBall.size = 50;

	target = new Target();
	target.xPosition = 40;
	target.yPosition = 100;
	target.size = 70;

	TimerTask timerTask = new TimerTask() {

	    public void run() {

		if (runGame) {

		    physicsCalculations();

		    if (gameIsOver()) {
			runGame = false;
		    }

		    repaint();
		}
	    }
	};
	timer.schedule(timerTask, 0, 1000 / cycleRate);
    }

    private void physicsCalculations() {

	// Section 1
	if (target.xPosition > boardSize_X / 2) {
	    target.xVelocity -= target.directionModifier;
	} else if (target.xPosition < boardSize_X / 2) {
	    target.xVelocity += target.directionModifier;
	}
	target.xPosition += target.xVelocity;

	// Section 2
	double gravityAcceleration = 0.5;

	mainBall.yVelocity += gravityAcceleration;

	// Section 3
	mainBall.xVelocity *= 0.99;
	mainBall.yVelocity *= 0.99;

	// Section 4
	int leftWall = 0;
	int rightWall = boardSize_X;
	int topWall = 0;
	int bottomWall = boardSize_Y;
	int ballRadius = mainBall.size / 2;
	if (mainBall.xPosition - ballRadius < leftWall) {

	    mainBall.xVelocity = abs(mainBall.xVelocity) * mainBall.bounciness;
	    mainBall.xPosition = leftWall + ballRadius;
	}
	if (mainBall.xPosition + ballRadius > rightWall) {

	    mainBall.xVelocity = -abs(mainBall.xVelocity) * mainBall.bounciness;
	    mainBall.xPosition = rightWall - ballRadius;
	}
	if (mainBall.yPosition - ballRadius < topWall) {

	    mainBall.yVelocity = abs(mainBall.yVelocity) * mainBall.bounciness;
	    mainBall.yPosition = topWall + ballRadius;
	}
	if (mainBall.yPosition + ballRadius > bottomWall) {

	    mainBall.yVelocity = -abs(mainBall.yVelocity) * mainBall.bounciness;
	    mainBall.yPosition = bottomWall - ballRadius;
	}

	// Section 5
	mainBall.xPosition += mainBall.xVelocity;
	mainBall.yPosition += mainBall.yVelocity;
    }

    private boolean gameIsOver() {

	double distanceThreshold = 15;

	double distanceFromBallToGoal_X = mainBall.xPosition - target.xPosition;
	double distanceFromBallToGoal_Y = mainBall.yPosition - target.yPosition;

	double absoluteDistance = Math.sqrt((distanceFromBallToGoal_X * distanceFromBallToGoal_X)
		+ (distanceFromBallToGoal_Y * distanceFromBallToGoal_Y));

	if (absoluteDistance < distanceThreshold) {
	    return true;
	}

	return false;
    }

    public static void main(String[] args) {
	new GameController();
    }

    @Override
    public void mousePressed(MouseEvent e) {

	if (runGame) {

	    double distanceX = (float) e.getX() - mainBall.xPosition;
	    double distanceY = (float) e.getY() - mainBall.yPosition;

	    double pushX = distanceX / 10;
	    double pushY = distanceY / 10;

	    mainBall.xVelocity += pushX;
	    mainBall.yVelocity += pushY;
	} else {
	    mainBall.xPosition = 300;
	    mainBall.yPosition = 300;
	    mainBall.xVelocity = 5;
	    mainBall.yVelocity = 0;
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
}
