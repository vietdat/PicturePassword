package com.example.vietdat.picturepassword;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.vietdat.picturepassword.MyDatabaseHelper;

public class MainActivity extends Activity {
    int _xDelta;
    int _yDelta;
    int dropZone1_X, dropZone2_X, dropZone3_X, dropZone1_Y, dropZone2_Y,
            dropZone3_Y, movingCoordinateLeft = 0, movingCoordinateTop = 0;
    int leftMargin, topMargin, bottomMargin, rightMargin;
    ImageView img1, img2;

    MyDatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MyDatabaseHelper(this);

        img1 = (ImageView) findViewById(R.id.myimage1);
        img2 = (ImageView) findViewById(R.id.myimage2);

        img1.setOnTouchListener(dragt);
        img2.setOnTouchListener(dragt);

    }

    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            final int X = (int) motionEvent.getRawX();
            final int Y = (int) motionEvent.getRawY();
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                    // LinearLayout.LayoutParams lParams =
                    // (LinearLayout.LayoutParams) view
                    // .getLayoutParams();

                    FrameLayout.LayoutParams lParams = (LayoutParams) view
                            .getLayoutParams();

                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;

                    ClipData data = ClipData.newPlainText("", "");
                    DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                            view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.INVISIBLE);

                    break;
                case MotionEvent.ACTION_UP:

                    // code to set to a position

                    LinearLayout.LayoutParams dropLayoutParams = (LinearLayout.LayoutParams) view
                            .getLayoutParams();
                    dropLayoutParams.leftMargin = leftMargin;
                    dropLayoutParams.topMargin = topMargin;
                    dropLayoutParams.rightMargin = topMargin;
                    dropLayoutParams.bottomMargin = bottomMargin;
                    view.setLayoutParams(dropLayoutParams);
                    view.setVisibility(View.VISIBLE);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    leftMargin = layoutParams.leftMargin;

                    layoutParams.topMargin = Y - _yDelta;
                    topMargin = layoutParams.topMargin;

                    layoutParams.rightMargin = -250;
                    rightMargin = layoutParams.rightMargin;
                    layoutParams.bottomMargin = -250;
                    bottomMargin = layoutParams.bottomMargin;
                    view.setLayoutParams(layoutParams);

                    break;
            }
            return true;
        }
    }

    class MyDragListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }
            return true;
        }
    }

    // onCreate
    OnTouchListener dragt = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            FrameLayout.LayoutParams par = (LayoutParams) v.getLayoutParams();
            switch (v.getId()) {// What is being touched
                /***
                 *
                 * Answer option 1
                 *
                 * ***/
                case R.id.myimage1: {
                    // Which action is being taken
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE: {
                            par.topMargin = (int) event.getRawY()
                                    - (v.getHeight() + 22);
                            par.leftMargin = (int) event.getRawX()
                                    - (v.getWidth() / 2 + 350);

                            movingCoordinateLeft = (int) event.getRawX()
                                    - (v.getWidth() / 2 + 0);
                            movingCoordinateTop = par.topMargin;

                            System.out.println("Answer 1 --- left"
                                    + movingCoordinateLeft + "---top"
                                    + movingCoordinateTop);

                            v.setLayoutParams(par);

                            break;
                        }// inner case MOVE
                        case MotionEvent.ACTION_UP: {
                            System.out.println("left" + event.getRawX());
                            System.out.println("top" + event.getRawY());

                            //gan vao database
                            db.addToado(event.getRawX(), event.getRawY());
                            //show dialog please return type password

                            img1.setVisibility(View.INVISIBLE);
                            img2.setVisibility(View.VISIBLE);

                            break;
                        }// inner case UP
                        case MotionEvent.ACTION_DOWN: {
                            break;
                        }// inner case UP
                    }// inner switch
                    break;
                }

                case R.id.myimage2: {// Which action is being taken
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE: {

                            par.topMargin = (int) event.getRawY()
                                    - (v.getHeight() + 22);
                            par.leftMargin = (int) event.getRawX()
                                    - (v.getWidth() / 2 + 150);

                            movingCoordinateLeft = (int) event.getRawX()
                                    - (v.getWidth() / 2 + 0);
                            movingCoordinateTop = par.topMargin;

                            v.setLayoutParams(par);

                            break;
                        }// inner case MOVE
                        case MotionEvent.ACTION_UP: {
                            String[] str = new String[2];
                            str = db.getLastRecordOfTable();

                            if(Math.abs(event.getRawX() - Float.valueOf(str[0])) > 5 &&
                                    Math.abs(event.getRawY() - Float.valueOf(str[1])) > 5){
                                Intent intent = new Intent(MainActivity.this, TextPasswordActivity.class);
                                startActivity(intent);
                            }

                            break;
                        }// inner case UP
                        case MotionEvent.ACTION_DOWN: {

                            break;
                        }// inner case UP
                    }// inner switch
                    break;
                }// case pawn2
            }// switch
            return true;
        }
    };

}