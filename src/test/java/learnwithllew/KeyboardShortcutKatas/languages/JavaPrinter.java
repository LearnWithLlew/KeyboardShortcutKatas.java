package learnwithllew.KeyboardShortcutKatas.languages;

import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import learnwithllew.KeyboardShortcutKatas.Place;
import org.lambda.query.Queryable;

import java.io.File;

public class JavaPrinter implements Printer {

   @Override
   public void writeFile(Queryable<Place> methods) {
        Place place = methods.get(0);
        File file = new File("src/main/java/_/" + place.getFile(".java"));
        String text = String.format("package _.%s;\n" +
                "public class %s { %s" +

                "}", place.getPackageName(), place.getClassName(), getMethods(methods));
        FileUtils.writeFile(file, text);
        //SimpleLogger.event("Writing " + file);
    }
    private static String getMethods(Queryable<Place> forClass) {
        String text = "";
        for (Place place : forClass) {
            Place next = place.next;
            text += "\n public static void " + place.word + "(){\n";

            if (next != null) {
                text += "_."+next.getPackageName() + "." + next.getClassName() + "." + next.word + "();\n";
            }
            text += "}\n";
        }
        return text;
    }

    @Override
    public void startAt(Place place) {
        SimpleLogger.variable("start at ", place);
    }
}
