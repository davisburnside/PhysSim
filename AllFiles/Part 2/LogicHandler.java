package physicssimnew;

import java.awt.Point;
import static java.lang.Math.abs;

public class LogicHandler {

    Ball mainBall;
    Target target;
    Entity screenShakeEntity;

    LogicHandler() {

	// Create the ball
	mainBall = new Ball();
	mainBall.xPos = 200;
	mainBall.yPos = 200;
	mainBall.size = 50;

	// Create the Target
	target = new Target();
	target.xPos = 40;
	target.yPos = 100;
	target.size = 80;

	// Create the ScreenShakeEntity
	screenShakeEntity = new Entity();
	screenShakeEntity.xPos = 0;
	screenShakeEntity.yPos = 0;
	screenShakeEntity.size = 0;
    }

    void physics(int boardSizeX, int boardSizeY) {

	//================================================================
	//Physics for the target 
	//================================================================

	// If on the left half of the board, push right
	if (target.xPos > boardSizeX / 2) {

	    target.xVelocity -= target.velocityModifier;
	} // If on the left half of the board, push right
	else if (target.xPos < boardSizeX / 2) {

	    target.xVelocity += target.velocityModifier;
	}
	target.xPos += target.xVelocity;

	//================================================================
	//Physics for the Ball 
	//================================================================

	double gravityAcceleration = 0.5;

	// gravity
	mainBall.yVelocity += gravityAcceleration;

	// Air resistance
	mainBall.xVelocity *= 0.996;
	mainBall.yVelocity *= 0.996;

	// collision detection of the four walls
	int leftWall = 0;
	int rightWall = boardSizeX;
	int topWall = 0;
	int bottomWall = boardSizeY;

	int ballRadius = mainBall.size / 2;

	// Used for setting the screenShakeEntity velocity
	float velocityBumpX = 0;
	float velocityBumpY = 0;

	if (mainBall.xPos - ballRadius < leftWall) {

	    mainBall.xVelocity = abs(mainBall.xVelocity) * mainBall.bounciness;
	    mainBall.xPos = leftWall + ballRadius;
	    velocityBumpX = (float) mainBall.xVelocity;
	}
	if (mainBall.xPos + ballRadius > rightWall) {

	    mainBall.xVelocity = -abs(mainBall.xVelocity) * mainBall.bounciness;
	    mainBall.xPos = rightWall - ballRadius;
	    velocityBumpX = (float) mainBall.xVelocity;
	}
	if (mainBall.yPos - ballRadius < topWall) {

	    mainBall.yVelocity = abs(mainBall.yVelocity) * mainBall.bounciness;
	    mainBall.yPos = topWall + ballRadius;
	    velocityBumpY = (float) mainBall.yVelocity;
	}
	if (mainBall.yPos + ballRadius > bottomWall) {

	    mainBall.yVelocity = -abs(mainBall.yVelocity) * mainBall.bounciness;
	    mainBall.yPos = bottomWall - ballRadius;
	    velocityBumpY = (float) mainBall.yVelocity;
	}

	float bumpThreshold = 5;
	if (velocityBumpY > -bumpThreshold && velocityBumpY < bumpThreshold) {
	    velocityBumpY = 0;
	}
	if (velocityBumpX > -bumpThreshold && velocityBumpX < bumpThreshold) {
	    velocityBumpX = 0;
	}

	mainBall.xPos += mainBall.xVelocity;
	mainBall.yPos += mainBall.yVelocity;

	//================================================================
	//Physics for the screenShakeEntity
	//================================================================

	// Add the bump from the ball colliding with the wall
	screenShakeEntity.xVelocity += velocityBumpX / 3;
	screenShakeEntity.yVelocity += velocityBumpY / 3;

	// Add the spring effect that moves the screenShakeEntity back to (0, 0)
	screenShakeEntity.xVelocity -= screenShakeEntity.xPos * (0.5);
	screenShakeEntity.yVelocity -= screenShakeEntity.yPos * (0.5);

	screenShakeEntity.xVelocity *= 0.9;
	screenShakeEntity.yVelocity *= 0.9;

	screenShakeEntity.xPos += screenShakeEntity.xVelocity;
	screenShakeEntity.yPos += screenShakeEntity.yVelocity;
    }

    boolean gameIsOver() {

	double distanceFromBallToGoal_X = mainBall.xPos - target.xPos;
	double distanceFromBallToGoal_Y = mainBall.yPos - target.yPos;

	double absoluteDistance = Math.sqrt((distanceFromBallToGoal_X * distanceFromBallToGoal_X)
		+ (distanceFromBallToGoal_Y * distanceFromBallToGoal_Y));

	double threshold = (target.size - mainBall.size) / 2;

	if (absoluteDistance <= threshold) {
	    return true;
	}

	return false;
    }

    void throwBall(int clickX, int clickY) {

	double distanceX = (float) clickX - mainBall.xPos;
	double distanceY = (float) clickY - mainBall.yPos;

	double pushX = distanceX / 10;
	double pushY = distanceY / 10;

	mainBall.xVelocity += pushX;
	mainBall.yVelocity += pushY;
    }

    void resetAllGameObjects() {

	mainBall.xPos = 300;
	mainBall.yPos = 300;
	mainBall.size = 50;

	screenShakeEntity.xPos = 0;
	screenShakeEntity.yPos = 0;
	screenShakeEntity.size = 0;
    }
}
