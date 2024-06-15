#include <jni.h>
#include <fluidsynth.h>
#include <string>

// Variables globales para el sintetizador y el controlador de audio
fluid_settings_t* settings = nullptr;
fluid_synth_t* synth = nullptr;
fluid_audio_driver_t* adriver = nullptr;

extern "C" JNIEXPORT void JNICALL
Java_com_example_fluidsynthandroidhelloworld_MainActivity_initializeSynth(JNIEnv* env, jobject, jstring jSoundfontPath) {
    const char* soundfontPath = env->GetStringUTFChars(jSoundfontPath, nullptr);

    if (synth == nullptr) {
        settings = new_fluid_settings();
        synth = new_fluid_synth(settings);
        adriver = new_fluid_audio_driver(settings, synth);
        fluid_synth_sfload(synth, soundfontPath, 1);
    }

    env->ReleaseStringUTFChars(jSoundfontPath, soundfontPath);
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_fluidsynthandroidhelloworld_MainActivity_fluidsynthNoteOn(JNIEnv* env, jobject, jint jDrumNote, jint jIntensity) {
    if (synth != nullptr) {
        int drumNote = static_cast<int>(jDrumNote);
        int intensity = static_cast<int>(jIntensity);
        fluid_synth_noteon(synth, 9, drumNote, intensity);
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_fluidsynthandroidhelloworld_MainActivity_cleanupSynth(JNIEnv* env, jobject) {
    if (adriver != nullptr) {
        delete_fluid_audio_driver(adriver);
        adriver = nullptr;
    }
    if (synth != nullptr) {
        delete_fluid_synth(synth);
        synth = nullptr;
    }
    if (settings != nullptr) {
        delete_fluid_settings(settings);
        settings = nullptr;
    }
}
