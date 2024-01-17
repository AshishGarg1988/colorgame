package com.play.colorgame;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class GameModel extends BaseObservable {
    private int score = 0;
    private int currentColorIndex;
    private int greyedBoxIndex;
    private boolean isGameOver = false;

    @Bindable
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        notifyPropertyChanged(BR.score);
    }

    @Bindable
    public int getCurrentColorIndex() {
        return currentColorIndex;
    }

    public void setCurrentColorIndex(int currentColorIndex) {
        this.currentColorIndex = currentColorIndex;
        notifyPropertyChanged(BR.currentColorIndex);
    }

    @Bindable
    public int getGreyedBoxIndex() {
        return greyedBoxIndex;
    }

    public void setGreyedBoxIndex(int greyedBoxIndex) {
        this.greyedBoxIndex = greyedBoxIndex;
        notifyPropertyChanged(BR.greyedBoxIndex);
    }

    @Bindable
    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
        notifyPropertyChanged(BR.gameOver);
    }
}