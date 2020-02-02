package com.mcdaniel.josh.connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5 , 8},  {0, 4, 8}, {2, 4, 6}};

    //    Yellow = 0, Red = 1. Empty = 0
    int activePlayer = 0;
    boolean isActive = true;
    TextView bannerText;
    String yellowPlayersTurn;
    String redPlayersTurn;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (isActive && gameState[tappedCounter] == 2) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1500);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
                bannerText.setText(redPlayersTurn);
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
                bannerText.setText(yellowPlayersTurn);
            }
            counter.animate().translationYBy(1500).rotationX(3600).setDuration(1000);
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    isActive = false;
                    String winner = "";
                    if (activePlayer == 1) {
                        winner = "Yellow";
                        TextView yellowScoreText = findViewById(R.id.yellowScoreCount);
                        int yellowCount = Integer.parseInt(yellowScoreText.getText().toString());
                        yellowCount++;
                        yellowScoreText.setText(Integer.toString(yellowCount));
                    } else {
                        winner = "Red";
                        TextView redScoreText = findViewById(R.id.redScoreCount);
                        int redCount = Integer.parseInt(redScoreText.getText().toString());
                        redCount++;
                        redScoreText.setText(Integer.toString(redCount));
                    }
                    String winningText = winner + " wins!";
                    bannerText.setText(winningText);
                    Button playAgainButton = findViewById(R.id.playAgainButton);
                    playAgainButton.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view) {
        bannerText.setText(yellowPlayersTurn);
        Button playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i=0; i<gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
        for (int i=0; i<gameState.length; i++) {
            gameState[i] = 2;
        }
        activePlayer = 0;
        isActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yellowPlayersTurn = getString(R.string.yellowPlayersTurn);
        redPlayersTurn = getString(R.string.redPlayersTurn);
        bannerText = findViewById(R.id.bannerText);
        bannerText.setText(yellowPlayersTurn);
    }
}
