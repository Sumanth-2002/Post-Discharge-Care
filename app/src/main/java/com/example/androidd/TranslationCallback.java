package com.example.androidd;

public interface TranslationCallback {
    void onTranslationCompleted(String translatedText);
    void onTranslationFailed();
}
