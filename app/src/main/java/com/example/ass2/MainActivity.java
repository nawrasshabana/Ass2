package com.example.ass2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.ViewGroup;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button[] buttons;
    private int[] cardValues;
    private boolean[] cardFlipped;
    private int flippedCount;
    private int firstCardIndex;
    private int secondCardIndex;
    private Button restartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new Button[6];
        cardValues = new int[]{1, 2, 3, 1, 2, 3};
        cardFlipped = new boolean[6];
        flippedCount = 0;
        firstCardIndex = -1;
        secondCardIndex = -1;

        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);
        buttons[4] = findViewById(R.id.button5);
        buttons[5] = findViewById(R.id.button6);

        for (Button button : buttons) {
            button.setOnClickListener(this);
        }
        restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });
}

    @Override
    public void onClick(View view) {
        int buttonIndex = -1;

        switch (view.getId()) {
            case R.id.button1:
                buttonIndex = 0;
                break;
            case R.id.button2:
                buttonIndex = 1;
                break;
            case R.id.button3:
                buttonIndex = 2;
                break;
            case R.id.button4:
                buttonIndex = 3;
                break;
            case R.id.button5:
                buttonIndex = 4;
                break;
            case R.id.button6:
                buttonIndex = 5;
                break;
        }

        if (buttonIndex != -1 && !cardFlipped[buttonIndex]) {
            flipCard(buttonIndex);
        }
    }

    private void flipCard(int index) {
        buttons[index].setText(String.valueOf(cardValues[index]));
        cardFlipped[index] = true;
        flippedCount++;

        if (flippedCount % 2 == 0) {
            secondCardIndex = index;
            checkMatch();
        } else {
            firstCardIndex = index;
        }
    }

    private void checkMatch() {
        if (cardValues[firstCardIndex] == cardValues[secondCardIndex]) {
            buttons[firstCardIndex].setEnabled(false);
            buttons[secondCardIndex].setEnabled(false);
        } else {
            buttons[firstCardIndex].setText("");
            buttons[secondCardIndex].setText("");
            cardFlipped[firstCardIndex] = false;
            cardFlipped[secondCardIndex] = false;
        }

        firstCardIndex = -1;
        secondCardIndex = -1;

        if (flippedCount == cardFlipped.length) {
            // All cards have been matched
            showWinningMessage();
        }
    }

    private void showWinningMessage() {
        Toast.makeText(this, "Congratulations! You won the game!", Toast.LENGTH_SHORT).show();
        ImageView crackersImageView = findViewById(R.id.crackersImageView);
        crackersImageView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.win_animation);
        crackersImageView.startAnimation(animation);
    }
    private void restartGame() {
        flippedCount = 0;
        firstCardIndex = -1;
        secondCardIndex = -1;

        for (int i = 0; i < cardFlipped.length; i++) {
            cardFlipped[i] = false;
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }

        Toast.makeText(this, "Game restarted!",Toast.LENGTH_SHORT).show();

        ImageView crackersImageView = findViewById(R.id.crackersImageView);
        crackersImageView.setVisibility(View.GONE);
    }




}
