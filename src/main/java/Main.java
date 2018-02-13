import controllers.SphinxSpeechRecognizerController;

public class Main {

    public static void main(String[] args) {
        SphinxSpeechRecognizerController.getInstance().startRecording();
    }

}
