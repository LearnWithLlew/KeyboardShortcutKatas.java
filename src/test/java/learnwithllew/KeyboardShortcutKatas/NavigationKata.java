package learnwithllew.KeyboardShortcutKatas;

import learnwithllew.KeyboardShortcutKatas.languages.*;
import org.junit.Test;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.io.File;
import java.util.ArrayList;

public class NavigationKata {
    @Test
    public void code() {
        String text = "I once made a car from wood. " +
                "The chassis was made from wood. " +
                "I made the wheels from wood. " +
                "The doors and hinges, all wooden. " +
                "The engine was tricky to make, but all made from wood, right down to the ebony piston rings." +
                "It was a beautiful piece of craftsmanship. The only problem with it was that it wooden go.";
        text = text.replaceAll("[\\.,]", " ").replaceAll("  ", " ");
        String[] words = text.split(" ");
        ArrayList<Place> places = new ArrayList<>();
        for (int i = 0, wordsLength = words.length; i < wordsLength; i++) {
            String word = words[i];
            Place p = createPlace(places, word);
            places.add(p);
            if (i != 0) {
                places.get(i - 1).next = p;
            }
        }
        print(places, new CppPrinter());


    }

    private void print(ArrayList<Place> places, Printer printer) {
             for (int packages = 1; packages <= 4; packages++) {
                for (int clazz = 1; clazz <= 6; clazz++) {
                    int pCount = packages;
                    int cCount = clazz;
                    Queryable<Place> forClass = Query.where(places, p -> p.packageName == pCount && p.className == cCount);
                    if (0 < forClass.size()) {
                        printer.writeFile(forClass);
                    }
                }
            }
            printer.startAt(places.get(0));

    }

    private Place createPlace(ArrayList<Place> places, String word) {
        Place p = new Place(word);
        while (places.contains(p)) {
            p = new Place(word);

        }
        return p;
    }


}
