//where everything runs

package com.example.minesweeperv2;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MinesweeperFragment.IfGameOverListener {
    private TextView textViewTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //replaces container main with start screen fragment
        //when app starts
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container_main, new StartScreenFragment()).commit();

        //todo: get info about game done or not by using the checkGameDone() method in the MinsweeperFragment class
            //if method returns true then the endScreenDialog is shown
            //else do nothing
    }

    //if game is lost
    //then show game over dialog
    private void showGameOverDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //need for creating custom view
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_lost_screen, null);

        alertDialogBuilder.setView(view);
        final AlertDialog dialog = alertDialogBuilder.create();

        //initalize variables
        TextView textView = view.findViewById(R.id.textView_frag_lost_screen_game_over);
        Button buttonHome = view.findViewById(R.id.button_frag_lost_screen_home);
        textViewTime = view.findViewById(R.id.textView_frag_lost_screen_time);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.container_main, new StartScreenFragment()).commit();
            }
        });
        dialog.show();
    }

    //if game is won
    //then show congratulations dialog
    private void showGameWonDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //need for creating custom view
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_won_screen, null);

        alertDialogBuilder.setView(view);
        final AlertDialog dialog = alertDialogBuilder.create();

        //initalize variables
        TextView textView = view.findViewById(R.id.textView_frag_won_screen_congratulations);
        Button buttonHome = view.findViewById(R.id.button_frag_won_screen_home);
        textViewTime = view.findViewById(R.id.textView_frag_won_screen_time);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.container_main, new StartScreenFragment()).commit();
            }
        });
        dialog.show();
    }


    @Override
    public void ifGameLost(boolean ifLost) {
        //if game lost
        //the show game lost dialog
        if(ifLost){
            showGameOverDialog();
        }

    }

    @Override
    public void ifGameWon(boolean ifWon) {
        //if game won
        //then show game won dialog
        if(ifWon){
            showGameWonDialog();
        }
    }

    @Override
    public void showTime(int time) {
        time = time / 1000;
        textViewTime.setText(time + "  seconds");
    }

}
