package sphinx_model;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SphinxSpeechRecognizer {
    private Logger logger = Logger.getLogger(getClass().getName());
    private String result;
    private LiveSpeechRecognizer liveSpeechRecognizer;
    Thread speechThread;
    Thread resourcesThread;

    private static final String ACOUSTIC_MODEL_PATH = "resource:/edu/cmu/sphinx/models/en-us/en-us";
    private static final String DICTIONARY_PATH = "resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict";
    private static final String GRAMMAR_PATH = "/home/brian/IdeaProjects/speech.sphinx4/resource/grammars/";
    private static final String GRAMMAR = "grammar";

    public SphinxSpeechRecognizer() {
        configureSphinxSpeechRecognizer();
    }

    private void configureSphinxSpeechRecognizer() {
        logger.log(Level.INFO, "Loading..\n");

        Configuration configuration = new Configuration();

        // Load model from the jar
        configuration.setAcousticModelPath(ACOUSTIC_MODEL_PATH);
        configuration.setDictionaryPath(DICTIONARY_PATH);

        // Grammar
        configuration.setGrammarPath(GRAMMAR_PATH);
        configuration.setGrammarName(GRAMMAR);
        configuration.setUseGrammar(true);


        // LiveSpeechRecognizer uses a microphone as the speech source.
        try {
            liveSpeechRecognizer = new LiveSpeechRecognizer(configuration);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        // Start recognition process pruning previously cached data.
        liveSpeechRecognizer.startRecognition(true);
    }

    public void beginRecording() {
        startSpeechThread();
        startResourcesThread();
    }

    /**
     * Starting the main Thread of speech recognition
     */
    protected void startSpeechThread() {
        // alive?
        if (speechThread != null && speechThread.isAlive())
            return;

        speechThread = new Thread(() -> {
            logger.log(Level.INFO, "You can start to speak...\n");
            try {
                while (true) {
                    /*
                     * This method will return when the end of speech is
                     * reached. Note that the end pointer will determine the end
                     * of speech.
                     */
                    SpeechResult speechResult = liveSpeechRecognizer.getResult();
                    if (speechResult != null) {
                        result = speechResult.getHypothesis();
                        System.out.println("You said: [" + result + "]\n");
                        // logger.log(Level.INFO, "You said: " + result + "\n")
                    } else
                        logger.log(Level.INFO, "I can't understand what you said.\n");
                }
            } catch (Exception ex) {
                logger.log(Level.WARNING, null, ex);
            }
            logger.log(Level.INFO, "SpeechThread has exited...");
        });
        speechThread.start();
    }

    /**
     * Starting a Thread that checks if the resources needed to the
     * SpeechRecognition library are available
     */
    protected void startResourcesThread() {

        // alive?
        if (resourcesThread != null && resourcesThread.isAlive())
            return;

        resourcesThread = new Thread(() -> {
            try {
                // Detect if the microphone is available
                while (true) {
                    if (AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
                          // logger.log(Level.INFO, "Microphone is available.\n");
                    } else {
                          // logger.log(Level.INFO, "Microphone is not available.\n");
                    }
                    Thread.sleep(350);
                }
            } catch (InterruptedException ex) {
                logger.log(Level.WARNING, null, ex);
                resourcesThread.interrupt();
            }
        });
        resourcesThread.start();
    }
}
