package com.kubatov.androidthree.ui.model;

public class OnBoardModel {

    private int image;
    private String text;

    public OnBoardModel(int image, String text) {
        this.image = image;
        this.text = text;

    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }
}
