/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage;

import java.io.Serializable;
import java.util.Objects;

import mage.constants.ColoredManaSymbol;
import mage.constants.ManaType;

import static mage.constants.ManaType.COLORLESS;

import mage.filter.FilterMana;
import mage.util.Copyable;
import mage.util.ThreadLocalStringBuilder;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Mana implements Comparable<Mana>, Serializable, Copyable<Mana> {

    private static final transient Logger logger = Logger.getLogger(Mana.class);

    protected int red;
    protected int green;
    protected int blue;
    protected int white;
    protected int black;
    protected int colorless;
    protected int any;
    protected boolean flag;

    //TODO  These are unsafe and mutable
    public static final Mana RedMana = RedMana(1);
    public static final Mana GreenMana = GreenMana(1);
    public static final Mana BlueMana = BlueMana(1);
    public static final Mana WhiteMana = WhiteMana(1);
    public static final Mana BlackMana = BlackMana(1);
    public static final Mana ColorlessMana = ColorlessMana(1);


    /**
     * Default constructor. Creates a {@link Mana} object with 0 values.
     */
    public Mana() {
    }


    /**
     * Creates a {@link Mana} object with the passed in values. Values can
     * not be less than 0. Any values less than 0 will be logged and set to 0.
     *
     * @param red       total Red mana to have.
     * @param green     total Green mana to have.
     * @param blue      total Blue mana to have.
     * @param white     total White mana to have.
     * @param black     total Black mana to have.
     * @param colorless total Colorless mana to have.
     * @param any       total Any mana to have.
     */
    public Mana(final int red, final int green, final int blue, final int white,
                final int black, final int colorless, final int any) {
        this.red = notNegative(red, "Red");
        this.green = notNegative(green, "Green");
        this.blue = notNegative(blue, "Blue");
        this.white = notNegative(white, "White");
        this.black = notNegative(black, "Black");
        this.colorless = notNegative(colorless, "Colorless");
        this.any = notNegative(any, "Any");
    }


    /**
     * Copy constructor. Creates a {@link Mana} object from existing {@link Mana}
     *
     * @param mana object to create copy from
     */
    public Mana(final Mana mana) {
        Objects.requireNonNull(mana, "The passed in mana can not be null");
        this.red = mana.red;
        this.green = mana.green;
        this.blue = mana.blue;
        this.white = mana.white;
        this.black = mana.black;
        this.colorless = mana.colorless;
        this.any = mana.any;
        this.flag = mana.flag;
    }


    /**
     * Creates {@link Mana} object from {@link ColoredManaSymbol}.
     * Created {@link Mana} will have a single mana of the passed in {@link ColoredManaSymbol} color.
     *
     * @param color The color to create the {@link Mana} object with.
     */
    public Mana(final ColoredManaSymbol color) {
        Objects.requireNonNull(color, "The passed in ColoredManaSymbol can not be null");
        switch (color) {
            case G:
                green = 1;
                break;
            case R:
                red = 1;
                break;
            case B:
                black = 1;
                break;
            case U:
                blue = 1;
                break;
            case W:
                white = 1;
                break;
            default:
                throw new IllegalArgumentException("Unknown mana color: " + color);
        }
    }


    /**
     * Creates a {@link Mana} object with the passed in {@code num} of Red mana.
     * {@code num} can not be a negative value. Negative values will be logged
     * and set to 0.
     *
     * @param num value of Red mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of Red mana.
     */
    public static Mana RedMana(int num) {
        return new Mana(notNegative(num, "Red"), 0, 0, 0, 0, 0, 0);
    }


    /**
     * Creates a {@link Mana} object with the passed in {@code num} of Green mana.
     * {@code num} can not be a negative value. Negative values will be logged
     * and set to 0.
     *
     * @param num value of Green mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of Green mana.
     */
    public static Mana GreenMana(int num) {
        return new Mana(0, notNegative(num, "Green"), 0, 0, 0, 0, 0);
    }


    /**
     * Creates a {@link Mana} object with the passed in {@code num} of Blue mana.
     * {@code num} can not be a negative value. Negative values will be logged
     * and set to 0.
     *
     * @param num value of Blue mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of Blue mana.
     */
    public static Mana BlueMana(int num) {
        return new Mana(0, 0, notNegative(num, "Blue"), 0, 0, 0, 0);
    }


    /**
     * Creates a {@link Mana} object with the passed in {@code num} of White mana.
     * {@code num} can not be a negative value. Negative values will be logged
     * and set to 0.
     *
     * @param num value of White mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of White mana.
     */
    public static Mana WhiteMana(int num) {
        return new Mana(0, 0, 0, notNegative(num, "White"), 0, 0, 0);
    }


    /**
     * Creates a {@link Mana} object with the passed in {@code num} of Black mana.
     * {@code num} can not be a negative value. Negative values will be logged
     * and set to 0.
     *
     * @param num value of Black mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of Black mana.
     */
    public static Mana BlackMana(int num) {
        return new Mana(0, 0, 0, 0, notNegative(num, "Black"), 0, 0);
    }


    /**
     * Creates a {@link Mana} object with the passed in {@code num} of Colorless mana.
     * {@code num} can not be a negative value. Negative values will be logged
     * and set to 0.
     *
     * @param num value of Colorless mana to create.
     * @return a {@link Mana} object with the passed in {@code num} of Colorless mana.
     */
    public static Mana ColorlessMana(int num) {
        return new Mana(0, 0, 0, 0, 0, notNegative(num, "Colorless"), 0);
    }


    /**
     * Adds mana from the passed in {@link Mana} object to this object.
     *
     * @param mana mana to add to this object.
     */
    public void add(final Mana mana) {
        red += mana.getRed();
        green += mana.getGreen();
        blue += mana.getBlue();
        white += mana.getWhite();
        black += mana.getBlack();
        colorless += mana.getColorless();
        any += mana.getAny();
    }


    /**
     * Increases the Red mana by one.
     */
    public void increaseRed() {
        red++;
    }


    /**
     * Increases the Green mana by one.
     */
    public void increaseGreen() {
        green++;
    }


    /**
     * Increases the Blue mana by one.
     */
    public void increaseBlue() {
        blue++;
    }


    /**
     * Increases the White mana by one.
     */
    public void increaseWhite() {
        white++;
    }


    /**
     * Increases the Black mana by one.
     */
    public void increaseBlack() {
        black++;
    }


    /**
     * Increases the Colorless mana by one.
     */
    public void increaseColorless() {
        colorless++;
    }


    /**
     * Subtracts the passed in mana values from this instance.
     *
     * @param mana mana values to subtract
     */
    public void subtract(final Mana mana) {
        red -= mana.red;
        green -= mana.green;
        blue -= mana.blue;
        white -= mana.white;
        black -= mana.black;
        colorless -= mana.colorless;
        any -= mana.any;
    }


    /**
     * Subtracts the passed in mana values from this instance. The difference between this and
     * {@code subtract()} is that if we do not have the available colorless
     * mana to pay, we take mana from our colored mana pools.
     *
     * @param mana mana values to subtract
     * @throws ArithmeticException thrown if there is not enough available
     *                             colored mana to pay the colorless cost
     */
    public void subtractCost(final Mana mana) throws ArithmeticException {
        red -= mana.red;
        green -= mana.green;
        blue -= mana.blue;
        white -= mana.white;
        black -= mana.black;
        any -= mana.any;
        colorless -= mana.colorless;

        while (colorless < 0) {
            int oldColorless = colorless;
            if (red > 0) {
                red--;
                colorless++;
                continue;
            }
            if (green > 0) {
                green--;
                colorless++;
                continue;
            }
            if (blue > 0) {
                blue--;
                colorless++;
                continue;
            }
            if (white > 0) {
                white--;
                colorless++;
                continue;
            }
            if (black > 0) {
                black--;
                colorless++;
            }
            if (any > 0) {
                any--;
                colorless++;
            }
            if (oldColorless == colorless) {
                throw new ArithmeticException("Not enough mana to pay colorless");
            }
        }
    }


    /**
     * Returns the total count of all combined mana.
     *
     * @return the total count of all combined mana.
     */
    public int count() {
        return red + green + blue + white + black + colorless + any;
    }


    /**
     * Returns the total count of all colored mana.
     *
     * @return the total count of all colored mana.
     */
    public int countColored() {
        return red + green + blue + white + black + any;
    }


    /**
     * Returns the count of filtered mana provided by the passed in {@link FilterMana}.
     * If {@link FilterMana} is null, the total mana count is returned via {@link #count() count}.
     *
     * @param filter the colors of mana to return the count for.
     * @return the count of filtered mana provided by the passed in {@link FilterMana}.
     */
    public int count(final FilterMana filter) {
        if (filter == null) {
            return count();
        }
        int count = 0;
        if (filter.isBlack()) {
            count += black;
        }
        if (filter.isBlue()) {
            count += blue;
        }
        if (filter.isWhite()) {
            count += white;
        }
        if (filter.isGreen()) {
            count += green;
        }
        if (filter.isRed()) {
            count += red;
        }
        if (filter.isColorless()) {
            count += colorless;
        }
        return count;
    }


    /**
     * Sets all mana to 0.
     */
    public void clear() {
        red = 0;
        green = 0;
        blue = 0;
        white = 0;
        black = 0;
        colorless = 0;
        any = 0;
    }


    /**
     * Returns this objects values as a {@link String}.
     *
     * @return this objects values as a {@link String}.
     */
    @Override
    public String toString() {
        StringBuilder sbMana = new StringBuilder();
        if (colorless > 0) {
            sbMana.append("{").append(Integer.toString(colorless)).append("}");
        }
        for (int i = 0; i < red; i++) {
            sbMana.append("{R}");
        }
        for (int i = 0; i < green; i++) {
            sbMana.append("{G}");
        }
        for (int i = 0; i < blue; i++) {
            sbMana.append("{U}");
        }
        for (int i = 0; i < white; i++) {
            sbMana.append("{W}");
        }
        for (int i = 0; i < black; i++) {
            sbMana.append("{B}");
        }
        for (int i = 0; i < any; i++) {
            sbMana.append("{Any}");
        }
        return sbMana.toString();
    }


    /**
     * Returns a deep copy of this object.
     *
     * @return a deep copy of this object.
     */
    @Override
    public Mana copy() {
        return new Mana(this);
    }


    /**
     * Returns if there is enough available mana to pay the mana provided by the
     * passed in {@link Mana} object.
     *
     * @param cost the cost to compare too.
     * @return if there is enough available mana to pay.
     */
    public boolean enough(final Mana cost) {
        Mana compare = cost.copy();
        compare.subtract(this);
        if (compare.red < 0) {
            compare.any = compare.getAny() + compare.getRed();
            if (compare.any < 0) {
                return false;
            }
            compare.red = 0;
        }
        if (compare.green < 0) {
            compare.any = compare.getAny() + compare.getGreen();
            if (compare.any < 0) {
                return false;
            }
            compare.green = 0;
        }
        if (compare.blue < 0) {
            compare.any = compare.getAny() + compare.getBlue();
            if (compare.any < 0) {
                return false;
            }
            compare.blue = 0;
        }
        if (compare.black < 0) {
            compare.any = compare.getAny() + compare.getBlack();
            if (compare.any < 0) {
                return false;
            }
            compare.black = 0;
        }
        if (compare.white < 0) {
            compare.any = compare.getAny() + compare.getWhite();
            if (compare.any < 0) {
                return false;
            }
            compare.white = 0;
        }
        if (compare.colorless < 0) {
            int remaining = compare.red + compare.green + compare.black + compare.blue + compare.white + compare.any;
            if (compare.colorless + remaining < 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * Returns the total mana needed to meet the passed in {@link Mana} object.
     *
     * @param cost the mana cost
     * @return the total mana needed to meet the passes in {@link Mana} object.
     */
    public Mana needed(final Mana cost) {
        Mana compare = cost.copy();
        compare.subtract(this);
        if (compare.red < 0 && compare.any > 0) {
            int diff = Math.min(compare.any, Math.abs(compare.red));
            compare.any = compare.getAny() - diff;
            compare.red = compare.getRed() + diff;
        }
        if (compare.green < 0 && compare.any > 0) {
            int diff = Math.min(compare.any, Math.abs(compare.green));
            compare.any = compare.any - diff;
            compare.green = compare.green + diff;
        }
        if (compare.blue < 0 && compare.any > 0) {
            int diff = Math.min(compare.any, Math.abs(compare.blue));
            compare.any = compare.any - diff;
            compare.blue = compare.blue + diff;
        }
        if (compare.black < 0 && compare.any > 0) {
            int diff = Math.min(compare.any, Math.abs(compare.black));
            compare.any = compare.any - diff;
            compare.black = compare.getBlack() + diff;
        }
        if (compare.white < 0 && compare.any > 0) {
            int diff = Math.min(compare.any, Math.abs(compare.white));
            compare.any = compare.any - diff;
            compare.white = compare.white + diff;
        }
        if (compare.colorless < 0) {
            int remaining = 0;
            remaining += Math.min(0, compare.red);
            remaining += Math.min(0, compare.white);
            remaining += Math.min(0, compare.green);
            remaining += Math.min(0, compare.black);
            remaining += Math.min(0, compare.blue);
            remaining += Math.min(0, compare.any);
            if (remaining > 0) {
                int diff = Math.min(remaining, Math.abs(compare.colorless));
                compare.colorless = compare.colorless + diff;
            }
        }
        Mana needed = new Mana();
        if (compare.red < 0) {
            needed.red = Math.abs(compare.red);
        }
        if (compare.white < 0) {
            needed.white = Math.abs(compare.white);
        }
        if (compare.green < 0) {
            needed.green = Math.abs(compare.green);
        }
        if (compare.black < 0) {
            needed.black = Math.abs(compare.black);
        }
        if (compare.blue < 0) {
            needed.blue = Math.abs(compare.blue);
        }
        if (compare.colorless < 0) {
            needed.colorless = Math.abs(compare.colorless);
        }
        return needed;
    }


    /**
     * Returns total Red mana.
     *
     * @return total Red mana.
     */
    public int getRed() {
        return red;
    }


    /**
     * Sets the total Red mana. Can not be negative.
     * Negative values will be logged and set to 0.
     *
     * @param red total Red mana.
     */
    public void setRed(int red) {
        this.red = notNegative(red, "Red");
    }


    /**
     * Returns total Green mana.
     *
     * @return total Green mana.
     */
    public int getGreen() {
        return green;
    }


    /**
     * Sets the total Green mana. Can not be negative.
     * Negative values will be logged and set to 0.
     *
     * @param green total Green mana.
     */
    public void setGreen(int green) {
        this.green = notNegative(green, "Green");
    }


    /**
     * Returns total Blue mana.
     *
     * @return total Blue mana.
     */
    public int getBlue() {
        return blue;
    }


    /**
     * Sets the total Blue mana. Can not be negative.
     * Negative values will be logged and set to 0.
     *
     * @param blue total Blue mana.
     */
    public void setBlue(int blue) {
        this.blue = notNegative(blue, "Blue");
    }


    /**
     * Returns total White mana.
     *
     * @return total White mana.
     */
    public int getWhite() {
        return white;
    }


    /**
     * Sets the total White mana. Can not be negative.
     * Negative values will be logged and set to 0.
     *
     * @param white total White mana.
     */
    public void setWhite(int white) {
        this.white = notNegative(white, "White");
    }


    /**
     * Returns total Black mana.
     *
     * @return total Black mana.
     */
    public int getBlack() {
        return black;
    }


    /**
     * Sets the total Black mana. Can not be negative.
     * Negative values will be logged and set to 0.
     *
     * @param black total Black mana.
     */
    public void setBlack(int black) {
        this.black = notNegative(black, "Black");
    }


    /**
     * Returns total Colorless mana.
     *
     * @return total Colorless mana.
     */
    public int getColorless() {
        return colorless;
    }


    /**
     * Sets the total Colorless mana. Can not be negative.
     * Negative values will be logged and set to 0.
     *
     * @param colorless total Colorless mana.
     */
    public void setColorless(int colorless) {
        this.colorless = notNegative(colorless, "Colorless");
    }


    /**
     * Returns total Any mana.
     *
     * @return total Any mana.
     */
    public int getAny() {
        return any;
    }


    /**
     * Sets the total Any mana. Can not be negative.
     * Negative values will be logged and set to 0.
     *
     * @param any total Any mana.
     */
    public void setAny(int any) {
        this.any = notNegative(any, "Any");
    }


    /**
     * Returns this objects total mana minus the passed in {@link Mana}'s mana.
     *
     * @param o the object to compare to.
     * @return this objects total mana minus the passed in {@link Mana}'s mana.
     */
    @Override
    public int compareTo(final Mana o) {
        return this.count() - o.count();
    }


    /**
     * Returns if this objects mana contains any number of the passed in {@link Mana}'s mana.
     *
     * @param mana the mana to check for
     * @return true if this contains any values that mana has
     */
    public boolean contains(final Mana mana) {
        if (mana.black > 0 && this.black > 0) {
            return true;
        }
        if (mana.blue > 0 && this.blue > 0) {
            return true;
        }
        if (mana.red > 0 && this.red > 0) {
            return true;
        }
        if (mana.white > 0 && this.white > 0) {
            return true;
        }
        if (mana.green > 0 && this.green > 0) {
            return true;
        }
        if (mana.colorless > 0 && this.count() > 0) {
            return true;
        }

        return false;
    }


    /**
     * Returns the total count of mana in this object as specified by the passed in {@link ColoredManaSymbol}.
     *
     * @param color the color to return the count for.
     * @return the total count of mana in this object as specified by the passed in {@link ColoredManaSymbol}.
     */
    public int getColor(final ColoredManaSymbol color) {
        if (color.equals(ColoredManaSymbol.G)) {
            return green;
        }
        if (color.equals(ColoredManaSymbol.R)) {
            return red;
        }
        if (color.equals(ColoredManaSymbol.B)) {
            return black;
        }
        if (color.equals(ColoredManaSymbol.U)) {
            return blue;
        }
        if (color.equals(ColoredManaSymbol.W)) {
            return white;
        }
        return 0;
    }


    /**
     * Returns the total count of mana in this object as specified by the passed in {@link ManaType}.
     *
     * @param manaType the type to return the count for.
     * @return the total count of mana in this object as specified by the passed in {@link ManaType}.
     */
    public int get(final ManaType manaType) {
        switch (manaType) {
            case BLACK:
                return black;
            case BLUE:
                return blue;
            case GREEN:
                return green;
            case RED:
                return red;
            case WHITE:
                return white;
            case COLORLESS:
                return colorless;
        }
        return 0;
    }


    /**
     * Set the color of mana specified by the passed in {@link ManaType} to {@code amount} .
     *
     * @param manaType the color of the mana to set
     * @param amount   the value to set the mana too
     */
    public void set(final ManaType manaType, final int amount) {
        switch (manaType) {
            case BLACK:
                black = amount;
                break;
            case BLUE:
                blue = amount;
                break;
            case GREEN:
                green = amount;
                break;
            case RED:
                red = amount;
                break;
            case WHITE:
                white = amount;
                break;
            case COLORLESS:
                colorless = amount;
                break;
            default:
                throw new IllegalArgumentException("Unknown color: " + manaType);
        }
    }


    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }


    /**
     * Sets this objects mana to that of the passed in {@link Mana}
     *
     * @param mana the mana to set this object to.
     */
    public void setToMana(final Mana mana) {
        this.any = mana.any;
        this.red = mana.red;
        this.green = mana.green;
        this.white = mana.white;
        this.blue = mana.blue;
        this.black = mana.black;
        this.colorless = mana.colorless;
    }


    /**
     * Returns if the passed in {@link Mana} values are equal to this objects.
     *
     * @param mana the {@link Mana} to compare to.
     * @return if the passed in {@link Mana} values are equal to this object.
     */
    public boolean equalManaValue(final Mana mana) {
        return this.any == mana.any
                && this.red == mana.red
                && this.green == mana.green
                && this.white == mana.white
                && this.blue == mana.blue
                && this.black == mana.black
                && this.colorless == mana.colorless;
    }


    /**
     * Returns if this {@link Mana} object has more than or equal values of mana
     * as the passed in {@link Mana} object.
     *
     * @param mana the mana to compare with
     * @return if this object has more than or equal mana to the passed in {@link Mana}.
     */
    public boolean includesMana(Mana mana) {
        return this.green >= mana.green
                && this.blue >= mana.blue
                && this.white >= mana.white
                && this.black >= mana.black
                && this.red >= mana.red
                && (this.colorless >= mana.colorless
                || this.countColored() >= mana.countColored() + mana.colorless);

    }


    /**
     * Returns the mana that is more colored or has a greater amount but does
     * not contain one less mana in any color but colorless if you call with
     * {1}{W}{R} and {G}{W}{R} you get back {G}{W}{R} if you call with {G}{W}{R}
     * and {G}{W}{R} you get back {G}{W}{R} if you call with {G}{W}{B} and
     * {G}{W}{R} you get back null
     *
     * @param mana1
     * @param mana2
     * @return
     */
    public static Mana getMoreValuableMana(final Mana mana1, final Mana mana2) {
        Mana moreMana;
        Mana lessMana;
        if (mana2.countColored() > mana1.countColored() || mana2.getAny() > mana1.getAny() || mana2.count() > mana1.count()) {
            moreMana = mana2;
            lessMana = mana1;
        } else {
            moreMana = mana1;
            lessMana = mana2;
        }
        if (lessMana.getWhite() > moreMana.getWhite()
                || lessMana.getRed() > moreMana.getRed()
                || lessMana.getGreen() > moreMana.getGreen()
                || lessMana.getBlue() > moreMana.getBlue()
                || lessMana.getBlack() > moreMana.getBlack()
                || lessMana.getAny() > moreMana.getAny()) {
            return null;
        }
        return moreMana;
    }


    /**
     * Returns the total count of mana colors that have at least one.
     *
     * @return the total count of mana colors that have at least one.
     */
    public int getDifferentColors() {
        int count = 0;
        if (blue > 0) {
            count++;
        }
        if (black > 0) {
            count++;
        }
        if (green > 0) {
            count++;
        }
        if (white > 0) {
            count++;
        }
        if (red > 0) {
            count++;
        }
        return count;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mana mana = (Mana) o;

        if (red != mana.red) return false;
        if (green != mana.green) return false;
        if (blue != mana.blue) return false;
        if (white != mana.white) return false;
        if (black != mana.black) return false;
        if (colorless != mana.colorless) return false;
        if (any != mana.any) return false;
        return flag == mana.flag;

    }

    @Override
    public int hashCode() {
        int result = red;
        result = 31 * result + green;
        result = 31 * result + blue;
        result = 31 * result + white;
        result = 31 * result + black;
        result = 31 * result + colorless;
        result = 31 * result + any;
        result = 31 * result + (flag ? 1 : 0);
        return result;
    }

    /**
     * Checks that the {@code value} passed in is not less than 0.
     * If the value is negative, it is logged and 0 is returned.
     *
     * @param value the value to check.
     * @param name  the name of the value to check. Used to make logging of
     *              the {@code value} easier
     * @return the {@code value} passed in, unless it is minus, in which case 0 is returned.
     */
    private static int notNegative(int value, final String name) {
        if (value < 0) {
            logger.info(name + " can not be less than 0. Passed in: " + value + " Defaulting to 0.");
            value = 0;
        }
        return value;
    }
}
