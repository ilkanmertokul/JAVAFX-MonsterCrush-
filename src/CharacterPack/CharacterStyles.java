package CharacterPack;

/**CharacterStyles Enum to support charactes.
 * @author ilkan Mert Okul*/
public enum CharacterStyles {
    Atlantis(1.3,0.4,1.3),
    Valhalla(1.3,0.4,1.3),
    UnderWild(1.3,0.4,1.3);

    /**Strength modifier to apply attacks.*/
    private final double strModifier;

    /**Agility modifier to apply defences.*/
    private final double agiModifier;

    /**Health modifier to apply health points.*/
    private final double hpModifier;

    CharacterStyles(double strModifier, double agiModifier, double hpModifier) {
        this.strModifier = strModifier;
        this.agiModifier = agiModifier;
        this.hpModifier = hpModifier;
    }

    public double getStrModifier() {
        return strModifier;
    }

    public double getAgiModifier() {
        return agiModifier;
    }

    public double getHpModifier() {
        return hpModifier;
    }
}
