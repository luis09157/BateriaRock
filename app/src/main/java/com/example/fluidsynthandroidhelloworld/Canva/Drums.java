package com.example.fluidsynthandroidhelloworld.Canva;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.example.fluidsynthandroidhelloworld.Model.Drum;
import com.example.fluidsynthandroidhelloworld.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Drums extends View {

    private List<Drum> drumList;
    private OnDrumsEventListener mListener;
    private static float REFERENCE_SCREEN_SIZE = -1; // Tamaño de pantalla de referencia en píxeles

    public Drums(Context context) {
        super(context);
        //init();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        if (w != 0 && h != 0) {
            // La vista se ha desplegado completamente, puedes realizar la inicialización aquí
            init();
        }
    }


    private void init() {
        drumList = new ArrayList<>();
        setupDrums();
    }

    private void setupDrums() {
        if (REFERENCE_SCREEN_SIZE == -1) {
            // Calcula la referencia solo si no se ha calculado antes
            REFERENCE_SCREEN_SIZE = getReferenceScreenSize();
        }
        float referenceScreenSize = getReferenceScreenSize();

        // Calcula la escala relativa para los tambores
        float scale = referenceScreenSize / REFERENCE_SCREEN_SIZE;

        // Ajusta los tambores con la escala calculada
        drumList.add(new Drum("Kick-Left", 36, BitmapFactory.decodeResource(getResources(), R.drawable.kick), 0.40f, 31f * scale, 95f * scale));
        drumList.add(new Drum("Kick-Right", 36, BitmapFactory.decodeResource(getResources(), R.drawable.kick), 0.40f, 69f * scale, 95f * scale));

        drumList.add(new Drum("Snare", 38, BitmapFactory.decodeResource(getResources(), R.drawable.kick), 0.40f, 50f * scale, 83f * scale));

        drumList.add(new Drum("Tom-H", 50, BitmapFactory.decodeResource(getResources(), R.drawable.kick), 0.30f, 25f * scale, 75f * scale));
        drumList.add(new Drum("Tom-M", 48, BitmapFactory.decodeResource(getResources(), R.drawable.kick), 0.30f, 50f * scale, 70f * scale));
        drumList.add(new Drum("Tom-L", 45, BitmapFactory.decodeResource(getResources(), R.drawable.kick), 0.30f, 75f * scale, 75f * scale));

        drumList.add(new Drum("Splash", 55, BitmapFactory.decodeResource(getResources(), R.drawable.kick), 0.25f, 34f * scale, 64f * scale));
        drumList.add(new Drum("Crash", 57, BitmapFactory.decodeResource(getResources(), R.drawable.kick), 0.25f, 66f * scale, 64f * scale));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Drum drum : drumList) {
            drawDrum(canvas, drum);
        }
    }

    private float getReferenceScreenSize() {
        return Math.min(getWidth(), getHeight());
    }

    private void drawDrum(Canvas canvas, Drum drum) {
        float scaledWidth = getWidth() * drum.getScale();
        float scaledHeight = scaledWidth;
        float left = getXPositionInPixels(drum.getXPosition(), scaledWidth);
        float top = getYPositionInPixels(drum.getYPosition(), scaledHeight);
        canvas.drawBitmap(drum.getBitmap(), null, new RectF(left, top, left + scaledWidth, top + scaledHeight), null);
    }

    private float getXPositionInPixels(float xPosPercentage, float scaledWidth) {
        float canvasWidth = getWidth();
        return (canvasWidth * xPosPercentage / 100) - scaledWidth / 2;
    }

    private float getYPositionInPixels(float yPosPercentage, float scaledHeight) {
        float canvasHeight = getHeight();
        return (canvasHeight * yPosPercentage / 100) - scaledHeight / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerCount = event.getPointerCount();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                for (int i = 0; i < pointerCount; i++) {
                    float x = event.getX(i);
                    float y = event.getY(i);
                    Drum drum = findDrumAtTouchPosition(x, y);
                    if (drum != null) {
                        playDrums(drum.getMidiResourceId());
                        drum.startAnimation(this);
                    }
                }
                break;
        }

        return true;
    }

    private Drum findDrumAtTouchPosition(float touchX, float touchY) {
        // Invertimos el orden de la lista para detectar de arriba hacia abajo
        List<Drum> reversedDrumList = new ArrayList<>(drumList);
        Collections.reverse(reversedDrumList);

        for (Drum drum : reversedDrumList) {
            if (isTouchInsideCircle(touchX, touchY, drum)) {
                return drum;
            }
        }
        return null;
    }

    private boolean isTouchInsideCircle(float touchX, float touchY, Drum drum) {
        float scaledWidth = drum.getBitmap().getWidth() * drum.getScale();
        float scaledHeight = drum.getBitmap().getHeight() * drum.getScale();
        float centerX = getXPositionInPixels(drum.getXPosition(), scaledWidth) + scaledWidth / 2;
        float centerY = getYPositionInPixels(drum.getYPosition(), scaledHeight) + scaledHeight / 2;
        float radius = (scaledWidth / 2) * 0.9f; // Reducir el radio al 90% del ancho escalado

        float distance = (float) Math.sqrt(Math.pow(touchX - centerX, 2) + Math.pow(touchY - centerY, 2));
        return distance <= radius;
    }

    public interface OnDrumsEventListener {
        void onPlayDrums(int midiResourceId);
    }

    public void setOnDrumsEventListener(OnDrumsEventListener listener) {
        mListener = listener;
    }

    public void playDrums(int midiResourceId) {
        if (mListener != null) {
            mListener.onPlayDrums(midiResourceId);
        }
    }
}
