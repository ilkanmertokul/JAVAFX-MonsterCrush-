package FactoryPack;

import CharacterPack.AbstractCharacter;

/**CharacterFactory to create characters.
 * @author ilkan Mert Okul*/
public abstract class CharacterFactory{

    /**Creates character to given x and y location.
     * @param xLoc x location.
     * @param yLoc y location.*/
    public abstract AbstractCharacter summonRandomCharacter(int xLoc, int yLoc);
}
