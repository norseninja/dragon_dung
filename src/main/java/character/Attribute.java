package character;

import static java.lang.Math.floor;
import static java.lang.Math.round;

public class Attribute {
    private final String name;
    private final String description;
    private int value;
    private int mod;

    public Attribute(String name, int value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
        updateMod();
    }

    private void updateMod() {
        if (this.value - 10 > 0) {
            this.mod = (int) floor((this.value - 10) / 2f);
        } else {
            this.mod = round((this.value - 10) / 2f);
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getValue() {
        return this.value;
    }

    public int getMod() {
        return this.mod;
    }

    public void setValue(int value) {
        this.value = value;
        updateMod();
    }
}
