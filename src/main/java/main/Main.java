package main;

import main.fileParsers.FileReader;
import main.fileParsers.TextProcessor;
import window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException {
        // available fonts
//        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//        for ( int i = 0; i < fonts.length; i++ ) System.out.println(fonts[i]);
        try {
            // Roboto Mono SemiBold
            // Roboto Mono Medium
            // Roboto Mono Regular
            // Roboto Mono Thin
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/RobotoMono-SemiBold.ttf")));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/RobotoMono-Medium.ttf")));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/RobotoMono-Regular.ttf")));
        
            // Source Sans Pro
            // Source Sans Pro Bold
            // Source Sans Pro Light
            // Source Sans Pro Semibold
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/SourceSansPro-Light.ttf")));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/SourceSansPro-Regular.ttf")));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/SourceSansPro-SemiBold.ttf")));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/SourceSansPro-Bold.ttf")));
        
        } catch (Exception e) {
            System.out.println("Error loading fonts.");
            e.printStackTrace();
        }
        
        FileReader reader = new FileReader("./texts/SherlockHolmes-AStudyInScarlet.txt");
        reader.readFile();

        TextProcessor textProc = new TextProcessor(reader.getChaptersMap());
        textProc.processText();

        SwingUtilities.invokeLater(() -> {
            new MainWindow(textProc.getProcessedChapters());
        });
    }
}

// example use for OpenNLP model, taken from https://www.tutorialspoint.com/opennlp/opennlp_sentence_detection.htm
//class SentenceDetectionME {
//
//    public static void main(String args[]) throws Exception {
//
//        String sentence = new FileReader("./texts/SherlockHolmes-AStudyInScarlet.txt").getText();
//
//        //Loading sentence detector model
//        InputStream inputStream = new FileInputStream("./da-sent.bin");
//        SentenceModel model = new SentenceModel(inputStream);
//
//        //Instantiating the SentenceDetectorME class
//        SentenceDetectorME detector = new SentenceDetectorME(model);
//
//        //Detecting the sentence
//        String sentences[] = detector.sentDetect(sentence);
//
//        //Printing the sentences
//        for (String sent : sentences)
//            System.out.println(sent);
//    }
//}

//class SentenceBoundaryDemo {
//
//    static final TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
//    static final SentenceModel SENTENCE_MODEL  = new MedlineSentenceModel();
//
//    public static void main(String[] args) throws IOException {
//        File file = new File("./texts/SherlockHolmes-AStudyInScarlet.txt");
//        String text = Files.readFromFile(file,"ISO-8859-1");
//        System.out.println("INPUT TEXT: ");
////        System.out.println(text);
//
//        List<String> tokenList = new ArrayList<String>();
//        List<String> whiteList = new ArrayList<String>();
//        Tokenizer tokenizer = TOKENIZER_FACTORY.tokenizer(text.toCharArray(),0,text.length());
//        tokenizer.tokenize(tokenList,whiteList);
//
//        System.out.println(tokenList.size() + " TOKENS");
//        System.out.println(whiteList.size() + " WHITESPACES");
//
//        String[] tokens = new String[tokenList.size()];
//        String[] whites = new String[whiteList.size()];
//        tokenList.toArray(tokens);
//        whiteList.toArray(whites);
//        int[] sentenceBoundaries = SENTENCE_MODEL.boundaryIndices(tokens,whites);
//
//        System.out.println(sentenceBoundaries.length
//                + " SENTENCE END TOKEN OFFSETS");
//
//        if (sentenceBoundaries.length < 1) {
//            System.out.println("No sentence boundaries found.");
//            return;
//        }
//        int sentStartTok = 0;
//        int sentEndTok = 0;
//        for (int i = 0; i < sentenceBoundaries.length; ++i) {
//            sentEndTok = sentenceBoundaries[i];
//            System.out.println("SENTENCE "+(i+1)+": ");
//            for (int j=sentStartTok; j<=sentEndTok; j++) {
//                System.out.print(tokens[j]+whites[j+1]);
//            }
//            System.out.println();
//            sentStartTok = sentEndTok+1;
//        }
//    }
//}
