package learnwithllew.KeyboardShortcutKatas.languages;

import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import learnwithllew.KeyboardShortcutKatas.Place;
import org.lambda.actions.Action0;
import org.lambda.query.Queryable;

import javax.swing.*;
import java.io.File;
import java.util.function.Function;

public class CSharpPrinter implements Printer {

   @Override
   public void writeFile(Queryable<Place> methods) {
        Place place = methods.get(0);
        File file = new File("src/main/csharp/_/" + place.getFile(".cs"));
        String text = String.format("namespace _.%s \n" +
                "{\n" +
                "  public class %s \n" +
                "  { %s \n" +

                "  }\n" +
                "}", place.getPackageName(), place.getClassName(), getMethods(methods));
        FileUtils.writeFile(file, text);
        //SimpleLogger.event("Writing " + file);
    }
    private static String getMethods(Queryable<Place> forClass) {
        String text = "";
        for (Place place : forClass) {
            Place next = place.next;
            text += "\n    public static void " + place.word + "()\n" +
                    "    {\n";

            if (next != null) {
                text += "      _."+next.getPackageName() + "." + next.getClassName() + "." + next.word + "();\n";
            }
            text += "\n    }\n";
        }
        return text;
    }

    @Override
    public void startAt(Place place) {
        SimpleLogger.variable("start at ", place);
    }
}


