package com.kubatov.androidthree.data.model.onBoard_model;

public class OnBoardModel {

    private int image;

    private int text;

    public OnBoardModel(int image, int text) {
        this.image = image;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public int getText() {
        return text;
    }

}
