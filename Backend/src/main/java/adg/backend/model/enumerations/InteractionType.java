package adg.backend.model.enumerations;

public enum InteractionType {

    VIEW(1),
    CLICK(2),
    FAV(3);

    public final int strength;

    InteractionType(int strength) {
        this.strength = strength;
    }
}
