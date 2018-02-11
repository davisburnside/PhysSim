package codebase;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

class GameCanvas extends JComponent {

    Graphics2D g2;
    BufferedImage ballImage;

    @Override
    public void paintComponent(Graphics g) {

	g2 = (Graphics2D) g;

	g2.setColor(new Color(220, 230, 230));
	g2.fill(new Rectangle2D.Double(
		0,
		0,
		this.getWidth(),
		this.getHeight()));

	Shape border = new Rectangle2D.Double(
		2,
		2,
		this.getWidth() - 4,
		this.getHeight() - 4);
	g2.setColor(Color.DARK_GRAY);
	g2.draw(border);

	// Section 1
	Ball ball = GameController.mainBall;
	Target target = GameController.target;

// Section 2
	if (target != null) {
	    g2.setColor(Color.LIGHT_GRAY);
	    g2.fillArc(
		    (int) (target.xPosition - (target.size) / 2),
		    (int) (target.yPosition - (target.size) / 2),
		    (int) target.size,
		    (int) target.size,
		    0,
		    360);
	} else {
	    System.out.println("Target could not be referenced");
	}

	if (ball != null) {
	    if (ballImage != null) {

		g2.drawImage(ballImage,
			(int) (ball.xPosition - (ball.size / 2)),
			(int) (ball.yPosition - (ball.size / 2)),
			(int) ball.size,
			(int) ball.size,
			null);
	    } else {
		Shape circle = new Ellipse2D.Double(
			(int) (ball.xPosition - (ball.size / 2)),
			(int) (ball.yPosition - (ball.size / 2)),
			(int) ball.size,
			(int) ball.size);
		g2.fill(circle);
	    }
	} else {
	    System.out.println("Ball could not be referenced");
	}
    }

    void loadImages() {

	try {

	    URL url = this.getClass().getResource("Ball.png");
	    if (url != null) {
		ballImage = ImageIO.read(url);
	    }
	} catch (Exception e) {
	    System.out.println("Failed to load ball Image");
	}
    }
}
