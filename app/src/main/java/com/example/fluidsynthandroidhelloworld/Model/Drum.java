package com.example.fluidsynthandroidhelloworld.Model;

import android.graphics.Bitmap;
import android.view.View;

import com.example.fluidsynthandroidhelloworld.Animacion.DrumAnimator;

public class Drum {
    private String name;
    private int midiResourceId;
    private Bitmap bitmap;
    private float scale;
    private float xPosition;
    private float yPosition;
    private boolean isAnimating;

    public Drum(String name, int midiResourceId, Bitmap bitmap, float scale, float xPosition, float yPosition) {
        this.name = name;
        this.midiResourceId = midiResourceId;
        this.bitmap = bitmap;
        this.scale = scale;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isAnimating = false;
    }

    public String getName() {
        return name;
    }

    public int getMidiResourceId() {
        return midiResourceId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getXPosition() {
        return xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public void setAnimating(boolean animating) {
        isAnimating = animating;
    }

    public void startAnimation(View drumView) {
        DrumAnimator.animateDrum(drumView, this);
    }
}
