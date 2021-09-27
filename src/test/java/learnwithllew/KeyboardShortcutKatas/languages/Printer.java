package learnwithllew.KeyboardShortcutKatas.languages;

import learnwithllew.KeyboardShortcutKatas.Place;
import org.lambda.query.Queryable;

public interface Printer {
    void writeFile(Queryable<Place> methods);

    void startAt(Place place);
}
