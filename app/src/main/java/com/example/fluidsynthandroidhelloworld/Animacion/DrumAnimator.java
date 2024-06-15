package com.example.fluidsynthandroidhelloworld.Animacion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;

import com.example.fluidsynthandroidhelloworld.Model.Drum;

public class DrumAnimator {
    private static final long ANIMATION_DURATION = 100; // Duración de la animación en milisegundos

    public static void animateDrum(View drumView, final Drum drum) {
        if (!drum.isAnimating()) {
            final float initialScale = drum.getScale(); // Escala inicial del tambor

            drum.setAnimating(true);
            ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.8f, 1.0f);
            animator.setDuration(ANIMATION_DURATION);
            animator.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                drum.setScale(animatedValue * initialScale); // Escala multiplicada por el valor inicial
                drumView.invalidate(); // Invalidamos la vista
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    drum.setAnimating(false);
                    drum.setScale(initialScale); // Restablecer la escala original al finalizar la animación
                    drumView.invalidate(); // Invalidamos la vista
                }
            });
            animator.start();
        }
    }
}
