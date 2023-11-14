package net.ausiasmarch.foxforumserver.helper;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.convert.Jsr310Converters.LocalDateTimeToInstantConverter;

public class DataGenerationHelper {

    private static final String[] aNames = { "Mónica", "José Antonio", "Laura", "Lucas", "Eva", "Eloy", "Jesús", "Alan",
            "Pablo", "Paula", "Raquel", "Nieves", "Elena", "Sergio", "Jaime", "Fernando", "Rafael" };

    private static final String[] aSurnames = {
            "Alcañiz", "Puig", "Ayala", "Farell", "Ferrer", "Esteve", "González", "Rozalén", "Lara", "Velarte",
            "Latorre", "Briones", "Maldonado", "Suárez", "McLure", "Alarcón", "Molero", "Marín", "Muñoz", "García",
            "Navarro", "López", "Navas", "Aguilar", "Ortega", "Fabra", "Romero", "Díaz", "Cano", "Roselló", "Gómez",
            "Serrano", "Quilez", "Aznar", "Aparici"
    };

    public static int getRandomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static String getRadomName() {
        return aNames[(int) (Math.random() * aNames.length)];
    }

    public static String getRadomSurname() {
        return aSurnames[(int) (Math.random() * aSurnames.length)];
    }

    public static String doNormalizeString(String cadena) {
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String cadenaSinAcentos = cadena;
        for (int i = 0; i < original.length(); i++) {
            cadenaSinAcentos = cadenaSinAcentos.replace(original.charAt(i), ascii.charAt(i));
        }
        return cadenaSinAcentos;
    }

    private static Random random = new Random();

    // Define arrays for different word categories
    private static String[] articles = { "the", "a", "one" };
    private static String[] nouns = { "cat", "dog", "book", "birds", "sun", "sandwich", "friend", "car", "house" };
    private static String[] verbs = { "sleeps", "barks", "reads", "fly", "shines", "run", "ate", "is" };
    private static String[] adverbs = { "loudly", "quickly", "brightly", "slowly", "softly" };
    private static String[] conjunctions = { "and", "but", "or" };
    private static String[] subordinatingConjunctions = { "although", "because", "while", "if", "when", "as", "after",
            "before", "since", "until", "unless", "where", "wherever", "whether", "while", "even if", "even though",
            "once", "provided that", "so that", "than", "though", "in order to", "so that", "that", "unless", "until",
            "when", "whenever", "where", "wherever", "whether", "while" };

    public static String generateSentence() {
        // Randomly decide if it's a simple, compound, or complex sentence
        int sentenceType = random.nextInt(3);
        if (sentenceType == 0) {
            return generateSimpleSentence();
        } else if (sentenceType == 1) {
            return generateCompoundSentence();
        } else {
            return generateComplexSentence();
        }
    }

    public static String generateSimpleSentence() {
        String subject = generateNounPhrase();
        String verb = generateVerbPhrase();
        return subject + " " + verb;
    }

    public static String generateCompoundSentence() {
        String simpleSentence1 = generateSimpleSentence();
        String conjunction = conjunctions[random.nextInt(conjunctions.length)];
        String simpleSentence2 = generateSimpleSentence();
        return simpleSentence1 + " " + conjunction + " " + simpleSentence2;
    }

    public static String generateComplexSentence() {
        String subordinatingConjunction = subordinatingConjunctions[random.nextInt(subordinatingConjunctions.length)];
        String subordinateClause = subordinatingConjunction + " " + generateSimpleSentence();
        String mainClause = generateMainClause();
        return subordinateClause + " " + mainClause;
    }

    public static String generateMainClause() {
        if (random.nextBoolean()) {
            return generateSimpleSentence();
        } else {
            return generateCompoundSentence();
        }
    }

    public static String generateNounPhrase() {
        String article = articles[random.nextInt(articles.length)];
        String noun = nouns[random.nextInt(nouns.length)];
        return article + " " + noun;
    }

    public static String generateVerbPhrase() {
        String verb = verbs[random.nextInt(verbs.length)];
        // Randomly decide if it's just a verb or a verb with an adverb
        if (random.nextBoolean()) {
            String adverb = adverbs[random.nextInt(adverbs.length)];
            return verb + " " + adverb;
        } else {
            return verb;
        }
    }

    public static String getSpeech(int amount) {
        String sentences = "";
        for (int i = 0; i < amount; i++) {
            String sentence = generateSentence();
            sentences += sentence.substring(0, 1).toUpperCase() + sentence.substring(1) + ". ";
        }
        return sentences;
    }

    public static LocalDateTime getRadomDate() {
        long minDay = LocalDate.of(2020, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2023, 10, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay).atTime(getRandomInt(0, 23), getRandomInt(0, 59), getRandomInt(0, 59));

    }
}
