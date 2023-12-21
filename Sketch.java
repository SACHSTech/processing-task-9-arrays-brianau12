import processing.core.PApplet;
import processing.core.PImage;

/**
 * A program where the player dodges snowflakes and can melt/slow them down
 * @author Brian Au
 */

public class Sketch extends PApplet {
	
  // Declare images
  PImage imgBackground;
  PImage imgLives;
  PImage imgGameOver;

  // Storing snowflakes into arrays and if they can be seen or not
  float[] fltSnowY = new float[30];
  float[] fltSnowX = new float[30];
  boolean [] blnBallHide = new boolean[30];

  // Snowflake contact and player navigation
  boolean blnSnowHit = false;
  boolean upPressed = false;
  boolean downPressed = false;
  boolean wPressed = false;
  boolean aPressed = false;
  boolean sPressed = false;
  boolean dPressed = false;

  // Snowflake that make contact, snowfall speed, player spawn location, player lives
  int intWhichSnow = 0;
  int intSnowSpeed = 2;
  int intCircleX = 200;
  int intCircleY = 380;
  int intLives = 3;

  public void settings() {
	  // Screen size
    size(400, 400);
    
    // Images
    imgBackground = loadImage("sakura.jpg");
    imgLives = loadImage("lives.png");
    imgGameOver = loadImage("endgame.jpg");
  }

  public void setup() {
    // Background image
    image(imgBackground, 0, 0, 400, 400);
    
    // Random y-coordinates location of snowflakes
    for (int intSnowY = 0; intSnowY < fltSnowY.length; intSnowY++) {
      fltSnowY[intSnowY] = random(-400);
    }
    
    // Random x-coordinates location of snowflakes
    for (int intSnowX = 0; intSnowX < fltSnowX.length; intSnowX++) {
      fltSnowX[intSnowX] = random(width);
    }

  }

  public void draw() {
    // Recenter image
    imageMode(CENTER);
    // Draw image
	  image(imgBackground, 200, 200, 400, 400);
    
    // Draw snowflakes for every loop of the array
    for (int i = 0; i < fltSnowY.length; i++) {
      // blnBallHide for each snowflake is false
      if (blnBallHide[i] == false) {
        stroke(0);
        fill(255);
        ellipse(fltSnowX[i], fltSnowY[i], 25, 25);

        // Snowfall speed
        fltSnowY[i] += intSnowSpeed;

        // Snowflake returns to the top at diff x-coordinate after passing bottom
        if (fltSnowY[i] > height) {
          fltSnowY[i] = 0;
          fltSnowX[i] = random(width);
        }    
        
        // Stop if lives if 0
        if (intLives != 0) {
          // Lose a life if hit by snowflake and snowflake disappears
          if (dist(intCircleX, intCircleY, fltSnowX[i],fltSnowY[i]) <= 30) {
            intLives -= 1;
            blnBallHide[i] = true;
          }
        }
  
      }
    }
    // change speed of snowflake via up-down keys
    if (downPressed) {
      intSnowSpeed = intSnowSpeed + 1;
      if (intSnowSpeed > 3) {
        intSnowSpeed = 3;
      }
    }
    else if (upPressed) {
      intSnowSpeed = intSnowSpeed - 1;
      if (intSnowSpeed < 1) {
        intSnowSpeed = 1;
      }
    }
    
    // wasd keys for movement
    if (wPressed) {
      intCircleY -= 3;
    }
    if (sPressed) {
      intCircleY += 3;
    }
    if (aPressed) {
      intCircleX -= 3;
    }
    if (dPressed) {
      intCircleX += 3;
    }
    
    // player circle.
    stroke(0);
    fill(147, 50, 168);
    ellipse(intCircleX, intCircleY, 30, 30);

    // circle detection
    if (intCircleX > width) {
      intCircleX = width;
    }
    else if (intCircleX < 0) {
      intCircleX = 0;
    }

    if (intCircleY > height) {
      intCircleY = height;
    }
    else if (intCircleY < 0) {
      intCircleY = 0;
    }

    // player lives
    if (intLives == 3) {
      image(imgLives, 320, 20, 40, 40);
      image(imgLives, 350, 20, 40, 40);
      image(imgLives, 380, 20, 40, 40);
    }
    else if (intLives == 2) {
      image(imgLives, 320, 20, 40, 40);
      image(imgLives, 350, 20, 40, 40);
    }
    else if (intLives == 1) {
      image(imgLives, 320, 20, 40, 40);
    }
    else {
      image(imgGameOver, 200, 200, 400, 400);
    }
  }
    
  /**
   * Method for keys pressed and sets to true it they are
   */
  public void keyPressed() {
    // set to true if up-down keys pressed
    if (keyCode == UP) {
      upPressed = true;
    }
    else if (keyCode == DOWN) {
      downPressed = true;
    }
    
    // set to true if wasd keys pressed
    if (key == 'w') {
      wPressed = true;
    }
    else if (key == 'a') {
      aPressed = true;
    }
    else if (key == 's'){
      sPressed = true;
    }
    else if (key == 'd'){
      dPressed = true;
    }
  }

  /**
   * Method for keys released and sets to false it they are
   */
  public void keyReleased() {
    // set to false if up-down keys released
    if (keyCode == UP) {
      upPressed = false;
    }
    else if (keyCode == DOWN) {
      downPressed = false;
    }
    
    // set to false if wasd keys released
    if (key == 'w') {
      wPressed = false;
    }
    else if (key == 'a') {
      aPressed = false;
    }
    else if (key == 's'){
      sPressed = false;
    }
    else if (key == 'd'){
      dPressed = false;
    }
  }

  /**
   * Method to make snowflake disappear if clicked
   */
  public void mouseClicked(){
    float mouseSnowX[] = new float[30];
    float mouseSnowY[] = new float[30];

    for(int i = 0; i < mouseSnowX.length; i++){
      mouseSnowX[i] = fltSnowX[i];
    }

    for(int i = 0; i < mouseSnowY.length; i++){
      mouseSnowY[i] = fltSnowY[i];
    }

    for(int i = 0; i < mouseSnowX.length; i++){
      if(dist(mouseX, mouseY, mouseSnowX[i], mouseSnowY[i]) < 30){
        blnBallHide[i] = true;
      }
    }
  }
}