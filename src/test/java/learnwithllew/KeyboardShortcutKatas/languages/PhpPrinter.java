package learnwithllew.KeyboardShortcutKatas.languages;

import com.spun.util.ArrayUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import learnwithllew.KeyboardShortcutKatas.Place;
import org.lambda.query.Queryable;

import java.io.File;
import java.util.stream.Stream;

public class PhpPrinter implements  Printer{


    public void writeFile(Queryable<Place> methods) {
        Place place = methods.get(0);
        File file = new File("src/php/" + place.getFile(".php"));
        String text = String.format("<?php\n" +
                "namespace %s;\n" +
               // getUses(methods) +
                "class %s \n" +
                "{ \n" +
                getMethods(methods) +
                "}", place.getPackageName(), place.getClassName());
        FileUtils.writeFile(file, text);
        //SimpleLogger.event("Writing " + file);
    }

    private String getUses(Queryable<Place> methods) {
        String[] includes = methods.where(m -> m.next != null && !m.next.isSameClassAs(m)).select(m -> m.next.getPackageName() + "\\" + m.next.getClassName()).stream().distinct().toArray(String[]::new);

        String code = "";
        for (String include : includes) {
            code += "use " + include + ";\n";
        }
        return code;
    }

    @Override
    public void startAt(Place place) {
        SimpleLogger.variable("start at ", place);

    }

    private static String getMethods(Queryable<Place> forClass) {
        String text = "";
        for (Place place : forClass) {
            Place next = place.next;
            text += "\n public static function " + place.word + "():void \n" +
                    "{\n";

            if (next != null) {
                text += "\\" + next.getPackageName() +"\\" +next.getClassName() + "::" + next.word + "();\n";
            }
            text += "}\n";
        }
        return text;
    }
}
