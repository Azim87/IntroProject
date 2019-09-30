package com.kubatov.androidthree.ui.model;

public class OnBoardModel {

    private int image;
    private String text;
    private String textOfButton;
    private String textOfskip;

    public OnBoardModel(int image, String text, String textOfButton, String textOfskip) {
        this.image = image;
        this.text = text;
        this.textOfButton = textOfButton;
        this.textOfskip = textOfskip;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public String getTextOfButton() {
        return textOfButton;
    }

    public String getTextOfskip() {
        return textOfskip;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextOfButton(String textOfButton) {
        this.textOfButton = textOfButton;
    }

    public void setTextOfskip(String textOfskip) {
        this.textOfskip = textOfskip;
    }
}
