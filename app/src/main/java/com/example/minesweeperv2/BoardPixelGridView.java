package com.example.minesweeperv2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class BoardPixelGridView extends View {
    private Paint paint;
    private int numColumns, numRows;
    private Canvas canvas;
    private OnGridTouchedListener listener = null;
    private GestureDetector gestureDetector = null;

    public BoardPixelGridView(Context context) {
        super(context);

        init(null);
    }

    public BoardPixelGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public BoardPixelGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }



    //accounts for size, orientation changes to view
    private void init(@Nullable AttributeSet set){
        //declare instance variables here instead of onDraw() so that they are not continuously created
        //make paint drawing smoother
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //gesture detector is used to handle long and short clicks
        setClickable(true);
        setFocusable(true);
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            public void onLongPress(MotionEvent e) {
                Toast.makeText(getContext(), "Long click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Toast.makeText(getContext(), "short click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void setSize(int numColumns, int numRows){
        this.numColumns = numColumns;
        this.numRows = numRows;
    }


    //TODO: figure out a way to randomize bomb placement on the canvas
        //idk how to do


    //call postInvalidate() after updating canvas to redraw the view
    @Override
    protected void onDraw(Canvas canvas) {

        //TODO: set the canvas passed as the canvas instance variable
        //canvas = this.canvas;

        int cellWidth = getWidth() / numColumns, cellHeight = getHeight() / numRows;

        if (numColumns == 0 || numRows == 0) {
            return;
        }

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        paint.setColor(Color.rgb(118,255,3));

                        //TODO: create tile obj and call draw method in the tile class on the object
                        //TODO: do canvas.tile.draw( variables )
                        /*Tile tile = new Tile(i,j);*/

                        canvas.drawRect(i * cellWidth, j * cellHeight,
                                (i + 1) * cellWidth, (j + 1) * cellHeight, paint);
                    }
                    else{
                        paint.setColor(Color.rgb(118,212,3));
                        canvas.drawRect(i * cellWidth, j * cellHeight,
                                (i + 1) * cellWidth, (j + 1) * cellHeight, paint);
                    }
                }else{
                    if (j % 2 == 0) {
                        paint.setColor(Color.rgb(118,212,3));
                        canvas.drawRect(i * cellWidth, j * cellHeight,
                                (i + 1) * cellWidth, (j + 1) * cellHeight, paint);
                    }
                    else{
                        paint.setColor(Color.rgb(118,255,3));
                        canvas.drawRect(i * cellWidth, j * cellHeight,
                                (i + 1) * cellWidth, (j + 1) * cellHeight, paint);
                    }
                }
            }
        }

//
    }

    //get the row and column that was clicked
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int row = 2; //row that the user clicked
        int col = 3; //column that the user clicked
        listener.onTouch(row, col);
        return gestureDetector.onTouchEvent(event);
    }

    //sets listener
    public void onGridTouchedListener(OnGridTouchedListener listener) {
        this.listener = listener;
    }

    //create own interface to handle clicks for grid
    public interface OnGridTouchedListener {
        void onTouch(int row, int col);
    }
}
