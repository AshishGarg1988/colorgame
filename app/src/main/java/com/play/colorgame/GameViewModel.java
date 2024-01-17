package com.play.colorgame;

import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class GameViewModel extends ViewModel {
    private static final long GAME_DURATION = 1000;

    private MutableLiveData<GameModel> gameModelLiveData = new MutableLiveData<>();
    private CountDownTimer gameTimer;

    public LiveData<GameModel> getGameModelLiveData() {
        return gameModelLiveData;
    }


    public void startGame() {
        GameModel gameModel = new GameModel();
        gameModelLiveData.setValue(gameModel);
        if (gameTimer != null) {
            gameTimer.cancel();
        }

        gameTimer = new CountDownTimer(Long.MAX_VALUE, GAME_DURATION) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateGame();
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        };

        gameTimer.start();
    }

    private void updateGame() {
        int previousGreyedBoxIndex = gameModelLiveData.getValue().getGreyedBoxIndex();
        int newGreyedBoxIndex = getRandomBoxIndex();

        gameModelLiveData.getValue().setCurrentColorIndex(previousGreyedBoxIndex);
        gameModelLiveData.getValue().setGreyedBoxIndex(newGreyedBoxIndex);

        // Notify UI about the color changes using LiveData
        gameModelLiveData.postValue(gameModelLiveData.getValue());
    }

    public void onBoxTapped(int tappedBoxIndex) {
        int greyedBoxIndex = gameModelLiveData.getValue().getGreyedBoxIndex();

        if (tappedBoxIndex == greyedBoxIndex) {
            gameModelLiveData.getValue().setScore(gameModelLiveData.getValue().getScore() + 1);
//            updateGame();
        } else {
            gameOver();
        }
    }

    private void gameOver() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }

        // Notify UI about the game over using LiveData
//        gameModelLiveData.postValue(gameModelLiveData.getValue());

        GameModel currentGameModel = gameModelLiveData.getValue();
        if (currentGameModel != null) {
            currentGameModel.setGameOver(true);
            gameModelLiveData.setValue(currentGameModel);
        }


    }

    private int getRandomBoxIndex() {
        Random random = new Random();
        return random.nextInt(4);
    }
}

