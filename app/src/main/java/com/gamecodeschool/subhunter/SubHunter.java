package com.gamecodeschool.subhunter;

import android.app.Activity;
import android.os.CancellationSignal;
import android.view.MotionEvent;
import android.view.Window;

import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.util.Log;
import android.widget.ImageView;
import java.util.Random;

public class SubHunter extends Activity {

    //This variable can be "seen" throughout  the subhunter class
    int numberHorizontalPixels;
    int numberVerticalPixels;
    int blockSize;
    int gridWidth = 40;
    int gridHeight;
    float horizontalTouched = -100;
    float verticalTouched = - 100;
    int subHorizontalPosition;
    int subVerticalPosition;
    boolean hit = false;
    int shotsTaken;
    int distanceFromSub;
    boolean debugging = true;

    /* Here are all the objects (instance) of classes that  we need to do some drawing
     */
    ImageView gameView;
    Bitmap blankBitmap;
    Canvas canvas;
    Paint paint;

    /*
    Android runs this code just before the player sees the app.This makes it a good place to add
    the code for the one-time setup phase.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //get the current device's screen resolution
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        //initialize size based variables based on the screen resolution
        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
        blockSize = numberHorizontalPixels / gridWidth;
        gridHeight = numberVerticalPixels / blockSize;

        /* initialize all the objects ready for drawing
         */
        blankBitmap = Bitmap.createBitmap(numberHorizontalPixels, numberVerticalPixels, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(blankBitmap);
        gameView = new ImageView(this);
        paint = new Paint();
        /*
        Tell Android to set our drawing as the view for this app
         */
        setContentView(gameView);



        Log.d("Debugging", "in onCreate"); //This code will print out a message in Android Studio to let us know that we are
                                                    //"Debugging" and that we are "In onCreate"
        newGame();
        draw();
    }
    /*
    This code will execute when a new game needs to be started. It will happen when the app is first started
    and after the player wins a game.
     */
    void newGame() {
        Random random = new Random();
        subHorizontalPosition = random.nextInt(gridWidth);
        subVerticalPosition = random.nextInt(gridHeight);
        shotsTaken = 0;

        Log.d("Debugging", "in newGame");
    }
     /*
     Here we will do all the drawing. The grid lines, the HUD and the touch indicator and the
    "BOOM" when a sub' is hit
     */
    void draw() {
        gameView.setImageBitmap(blankBitmap);

        /*
        Wipe the screen with a white color
         */
        canvas.drawColor(Color.argb(255, 255, 255,255));

        /*
        Chance the Paint color to Black
         */
        paint.setColor(Color.argb(255, 0, 0, 0));

        /*Draw the vertical lines of the grid
         */
        canvas.drawLine(blockSize * 1, 0, blockSize * 1, numberVerticalPixels - 1, paint);

        /*
        Draw the horizontal lines of the grid
         */
        canvas.drawLine(0, blockSize * 1, numberHorizontalPixels - 1, blockSize  * 1, paint);

        /*
        Re-size the text appropriate for the score and distance text
         */
        paint.setTextSize(blockSize * 2);
        paint.setColor(Color.argb(255, 0, 0, 255));
        canvas.drawText("Shots Taken: " + shotsTaken + " Distance: " + distanceFromSub,
                blockSize, blockSize * 1.75f, paint);

        Log.d("Debugging", "in draw");
        printDebuggingText();
    }

    /*
     This part of the code will handle detecting that the player has tapped the screen
    */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.d("Debugging", "in onTouchEvent");

        //Has the player removed their finger from the screen?
        if((motionEvent.getAction() & motionEvent.ACTION_MASK)== motionEvent.ACTION_UP) {
            //Processthe player's shot by passing the coordinates of the player's finger to takeShot
           takeShot(motionEvent.getX(), motionEvent.getY());

        }

        return true;
    }

    /*
     The code here will execute when the player taps the screen. It will calculate the distance from the sub'
    and decide a hit or miss
    */
    void takeShot(float touchX, float touchY) {
        Log.d("Debugging", "in takeShot");
        draw();
    }
    // This code says "BOOM!"
    void boom() {

    }
    // This code prints the debugging text
    void printDebuggingText() {
        paint.setTextSize(blockSize);
        canvas.drawText("numberHorizontalPixels = " + numberHorizontalPixels, 50, blockSize * 3,
                paint);

        canvas.drawText("numberVerticalPixels = " + numberVerticalPixels, 50 ,
                blockSize * 4, paint);

        canvas.drawText("blockSize = " + blockSize, 50, blockSize * 5, paint);

        canvas.drawText("gridWidth = " + gridWidth, 50 , blockSize * 6, paint);

        canvas.drawText("gridHeight = " + gridHeight, 50, blockSize * 7, paint);

        canvas.drawText("horizontalTouched = " + horizontalTouched, 50,
                blockSize * 8, paint);

        canvas.drawText("verticalTouched = " + verticalTouched, 50, blockSize * 9,
                paint);

        canvas.drawText("subHorizontalPosition = " + subHorizontalPosition, 50, blockSize * 10,
                paint);

        canvas.drawText("subVerticalPosition = " + subVerticalPosition, 50,
                blockSize * 11, paint);

        canvas.drawText("hit = " + hit, 50, blockSize  * 12, paint);

        canvas.drawText("shotsTaken = " + shotsTaken, 50, blockSize * 13, paint);

        canvas.drawText("debugging = " + debugging, 50, blockSize * 14, paint);
        
    }
}