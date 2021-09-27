package learnwithllew.KeyboardShortcutKatas;

import java.util.Objects;
import java.util.Random;

public class Place {
    public static Random random = new Random();
    public final int className;
    public final int packageName;
    public static String NAMING = "_____________________";
    public Place next;
    public String word;

    public Place(String word) {

        this.word = word;
        this.className = random.nextInt(4) + 2;
        this.packageName = random.nextInt(2) + 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return className == place.className && packageName == place.packageName && Objects.equals(word.toLowerCase(), place.word.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, packageName, word.toLowerCase());
    }

    @Override
    public String toString() {
        return getPackageName() + "." + getClassName() + "." + word;
    }

    public String getClassName() {
        return NAMING.substring(0, className);
    }

    public String getPackageName() {
        return NAMING.substring(0, packageName);
    }

    public String getFile(String extensionWithDot) {
        return getPackageName() + "/" + getClassName() + extensionWithDot;
    }

    public boolean isSameClassAs(Place that) {
        return  this.packageName == that.packageName && this.className == that.className;
    }
}
