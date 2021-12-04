package FactoryPack;

import CharacterPack.AbstractCharacter;
import CharacterPack.Character;
import CharacterPack.CharacterStyles;
import CharacterPack.CharacterTypes;

/**CharacterFactory to create characters.
 * This class generates characters somewhat organized,
 * because our heroes are unique (both type and style),
 * but since they are ambushed,
 * their locations and roles mixed up!
 * sets the created characters as "ENEMY = false"
 * @author ilkan Mert Okul*/
public class PlayerFactory extends CharacterFactory{

    /**to keep all types unique*/
    private int currentTypes;

    /**to keep all styles unique*/
    private int currentStyles;

    public PlayerFactory(){
        currentTypes = (int) Math.floor(Math.random()*(CharacterTypes.values().length + 1));
        currentStyles = (int) Math.floor(Math.random()*(CharacterStyles.values().length + 1));
    }

    public AbstractCharacter summonRandomCharacter(int xLoc, int yLoc) {

        currentTypes++;
        currentStyles++;

        CharacterTypes a = CharacterTypes.values()[currentTypes % (CharacterTypes.values().length)];
        CharacterStyles b = CharacterStyles.values()[currentStyles % (CharacterStyles.values().length)];

        return new Character(a,b,false,xLoc,yLoc);
    }
}
