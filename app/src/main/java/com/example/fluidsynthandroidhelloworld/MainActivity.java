package com.example.fluidsynthandroidhelloworld;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fluidsynthandroidhelloworld.Canva.Drums;
import com.example.fluidsynthandroidhelloworld.Helper.AssetHelper;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Drums.OnDrumsEventListener {

    static {
        System.loadLibrary("native-lib");
    }

    private String soundfontPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            soundfontPath = AssetHelper.copyAssetToTmpFile(this, "Real_Drum.sf2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Drums drums = new Drums(this);
        setContentView(drums);

        // Configura esta actividad como el listener para los eventos de tambores
        drums.setOnDrumsEventListener(this);

        // Inicializa el sintetizador una vez
        initializeSynth(soundfontPath);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpia el sintetizador cuando la actividad se destruya
        cleanupSynth();
    }

    @Override
    public void onPlayDrums(int midiResourceId) {
        // Llama a la función JNI fluidsynthNoteOn
        fluidsynthNoteOn(midiResourceId, 127);
    }

    private native void initializeSynth(String soundfontPath);
    private native void fluidsynthNoteOn(int drumNote, int intensity);
    private native void cleanupSynth();
}
