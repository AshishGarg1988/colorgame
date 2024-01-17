package com.play.colorgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.play.colorgame.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private GameViewModel gameViewModel;
    private View redBox, blueBox, yellowBox, greenBox;
    private TextView scoreTextView;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        // Bind the ViewModel
        binding.setViewModel(gameViewModel);
        binding.setLifecycleOwner(this); // For observing LiveData


        redBox = binding.redBoxTop;
        blueBox = binding.blueBoxTop;
        yellowBox = binding.yellowBoxBottom;
        greenBox = binding.greenBoxBottom;
        scoreTextView = binding.scoreTextView;
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        // Observe changes in the LiveData and update UI accordingly
        gameViewModel.getGameModelLiveData().observe(this, new Observer<GameModel>() {
            @Override
            public void onChanged(GameModel gameModel) {
                // Update UI elements based on the changes in the LiveData
                // For example, update the score TextView, box colors, etc.
                updateBoxColor(redBox, gameModel, 0, Color.RED);
                updateBoxColor(blueBox, gameModel, 1, Color.BLUE);
                updateBoxColor(yellowBox, gameModel, 2, Color.YELLOW);
                updateBoxColor(greenBox, gameModel, 3, Color.GREEN);
                updateScoreTextView(scoreTextView, gameModel);
                if (gameModel.isGameOver()) {
                    showGameOverDialog(gameModel.getScore());
                }
            }
        });

        redBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.onBoxTapped(0);
            }
        });

        blueBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.onBoxTapped(1);
            }
        });

        yellowBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.onBoxTapped(2);
            }
        });

        greenBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.onBoxTapped(3);
            }
        });


        // Start the game
        gameViewModel.startGame();
    }

    private void showGameOverDialog(int score) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over")
                .setMessage("Your score is: " + score)
                .setPositiveButton("Restart", (dialog, which) -> gameViewModel.startGame())
                .setNegativeButton("Exit", (dialog, which) -> finish())
                .setCancelable(false) // Prevent dismissing the dialog by tapping outside of it
                .show();
    }

    private void updateScoreTextView(TextView scoreTextView, GameModel gameModel) {
        if (gameModel != null) {
            scoreTextView.setText("Score: " + gameModel.getScore());
        }
    }

    private void updateBoxColor(View box, GameModel gameModel, int boxIndex, int originalColor) {
        if (gameModel.getGreyedBoxIndex() == boxIndex) {
            // Box is greyed
            box.setBackgroundColor(Color.GRAY);
        } else {
            // Box is not greyed
            box.setBackgroundColor(originalColor);
        }
    }
}