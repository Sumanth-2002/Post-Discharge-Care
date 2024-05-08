package com.example.androidd;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class english {
    private TranslatorOptions translatorOptions;
    private Translator translator;
    private String sourceLanguageCode; // Set default source language code
    private String destinationLanguageCode;
    private Context context;

    public english(Context context, String sourceLanguageCode, String destinationLanguageCode) {
        this.context = context;
        this.sourceLanguageCode = sourceLanguageCode;
        this.destinationLanguageCode = destinationLanguageCode;
    }

    public void translateText(String sourceLanguageText, TranslationCallback translationCallback) {
        translatorOptions = new TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguageCode)
                .setTargetLanguage(destinationLanguageCode)
                .build();
        translator = Translation.getClient(translatorOptions);
        DownloadConditions downloadConditions = new DownloadConditions.Builder()

                .build();
        translator.downloadModelIfNeeded(downloadConditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        translator.translate(sourceLanguageText)
                                .addOnSuccessListener(new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(String translatedText) {
                                        translationCallback.onTranslationCompleted(translatedText);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed to translate due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        translationCallback.onTranslationFailed();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to ready model due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        translationCallback.onTranslationFailed();
                    }
                });
    }
}
