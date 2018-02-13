package controllers;

import sphinx_model.SphinxSpeechRecognizer;

public class SphinxSpeechRecognizerController {

    // Our controller has the model hardwired in
    private SphinxSpeechRecognizer sphinxSpeechRecognizer;

    private static SphinxSpeechRecognizerController instance;

    public static SphinxSpeechRecognizerController getInstance() {
        if (instance == null) {
            instance = new SphinxSpeechRecognizerController();
        }
        return instance;
    }

    private SphinxSpeechRecognizerController() {
        this.sphinxSpeechRecognizer = new SphinxSpeechRecognizer();
    }

    public void startRecording() {
        sphinxSpeechRecognizer.beginRecording();
    }
}
