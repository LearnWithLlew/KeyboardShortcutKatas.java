package learnwithllew.KeyboardShortcutKatas.languages;

import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import learnwithllew.KeyboardShortcutKatas.Place;
import org.lambda.query.Queryable;

import java.io.File;

public class CppPrinter implements Printer {

    @Override
    public void writeFile(Queryable<Place> methods) {
        Place.NAMING = "XXXXXXXXXXXXXXXX";
        writeHeader(methods);
        writeCpp(methods);
        //SimpleLogger.event("Writing " + file);
    }

    private void writeCpp(Queryable<Place> methods) {
        Place place = methods.get(0);
        File file = new File("src/main/cpp/_/" + place.getFile(".cpp"));
        String text = String.format(
                        getIncludes(methods) +
                        "namespace X\n" +
                        "{\n" +
                        "  namespace %s\n" +
                        "  {\n" +
                        "%s \n" +
                        "  } // namespace %s\n" +
                        "} // namespace X" +
                        "", place.getPackageName(), getMethods(methods),place.getPackageName());
        FileUtils.writeFile(file, text);
    }
    private void writeHeader(Queryable<Place> methods) {
        Place place = methods.get(0);
        File file = new File("src/main/cpp/_/" + place.getFile(".h"));
        String text = String.format(
                "#pragma once\n" +
                        "\n" +
                       // getIncludes(methods) +
                        "namespace X\n" +
                        "{\n" +
                        "  namespace %s\n" +
                        "  {\n" +
                        "    class %s\n" +
                        "    {\n" +
                        "    public:" +
                        "%s \n" +
                         "    };\n" +
                        "  } // namespace %s\n" +
                        "} // namespace X" +
                        "", place.getPackageName(), place.getClassName(), getMethodHeaders(methods),place.getPackageName());
        FileUtils.writeFile(file, text);
    }

    private String getIncludes(Queryable<Place> methods) {
        Queryable<Place> dependencies = methods.where(m -> m.next != null).select(m ->  m.next);
        dependencies.add(methods.get(0));
        Queryable<String> fileNames = dependencies.select(p -> "../" + p.getPackageName() + "/" + p.getClassName() + ".h");
        String[] includes = fileNames.stream().distinct().toArray(String[]::new);

        String code = "";
        for (String include : includes) {
            code += "#include \"" + include + "\"\n";
        }
        return code;
    }
    private static String getMethodHeaders(Queryable<Place> forClass) {
        String text = "";
        for (Place place : forClass) {
            text += "\n" +
                    "      static void " + place.word + "();\n";
        }
        return text;
    }
    private static String getMethods(Queryable<Place> forClass) {
        String text = "";
        for (Place place : forClass) {
            Place next = place.next;
            text += "    void " + place.getClassName()+"::"+place.word + "()\n" +
                    "    {\n";

            if (next != null) {
                text += "      ::X::" + next.getPackageName() + "::" + next.getClassName() + "::" + next.word + "();\n";
            }
            text += "    }\n\n";
        }
        return text;
    }

    @Override
    public void startAt(Place place) {
        SimpleLogger.variable("start at ", place);
    }
}


