//shows the actual minesweeper game with the board

package com.example.minesweeperv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MinesweeperFragment extends Fragment {
    public BoardPixelGridView gameView;
    public MinesweeperGame game;

    //creates the view of the fragment
    //basically creates what is to be shown on the fragment screen
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //create options menu in the top right of the fragment screen
        setHasOptionsMenu(true);

        //inflate view in which view will be rendered by creating view object in memory
        //when new BoardPixelGridView is created it automatically calls onDraw()
        View rootView = inflater.inflate(R.layout.fragment_minesweeper, container, false);
        gameView = rootView.findViewById(R.id.boardPixelGridView);
        //implements our own listener for the gameView
        gameView.onGridTouchedListener(new BoardPixelGridView.OnGridTouchedListener() {
            @Override
            public void onTouch(int row, int col) {
                game.onSingleTapClickReveal(row, col);

            }

            @Override
            public void onLongTouch(int row, int col) {

            }
        });

        //receives information from StartScreenFragment about difficulty value
        //checks the KEY
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String difficulty = sharedPref.getString(StartScreenFragment.KEY, "Easy");
        //depending on difficulty, sets size of the gameView
        //creates a new game and sets size of the game
        if(difficulty.equals("Easy")) {
            gameView.setSize(10, 10);
            game = new MinesweeperGame(10, 10);
            game.randomizeBombsAndSetNumbers();
            gameView.setBoard(game.getArray());
        }
        else if(difficulty.equals("Medium")){
            gameView.setSize(12, 12);
            game = new MinesweeperGame(12, 25);
            game.randomizeBombsAndSetNumbers();
            gameView.setBoard(game.getArray());
        }
        else {
            gameView.setSize(15, 15);
            game = new MinesweeperGame(15, 45);
            game.randomizeBombsAndSetNumbers();
            gameView.setBoard(game.getArray());
        }
        return rootView;
    }

    public BoardPixelGridView getGameView() {
        return gameView;
    }

    //adding options to options menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add("Home");
    }

    //when item is selected in the options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //add switch case for multiple options in the options menu in the top right corner
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.container_main, new StartScreenFragment()).commit();

        return true;
    }
}
