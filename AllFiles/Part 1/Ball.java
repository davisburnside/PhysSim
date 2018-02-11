package codebase;

public class Ball {

    double xPosition = 0;
    double yPosition = 0;

    // The speed is used to compute an Entity'snew position
    double xVelocity = 0;
    double yVelocity = 0;

    // 1 = bounces with 100% energy retention, 0 = no bounce at all
    double bounciness= .8;
    
    // How quickly the entity loses speed. 0 = no friction, 1 = complete friction (cannot retain velocity)
    double friction = 0;
    
    int size;
}
