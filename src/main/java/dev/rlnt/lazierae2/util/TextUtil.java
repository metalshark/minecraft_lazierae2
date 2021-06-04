package dev.rlnt.lazierae2.util;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import dev.rlnt.lazierae2.util.TypeEnums.IO_SETTING;
import dev.rlnt.lazierae2.util.TypeEnums.IO_SIDE;
import dev.rlnt.lazierae2.util.TypeEnums.TRANSLATE_TYPE;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.EnumMap;
import java.util.Locale;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class TextUtil {

    private static final Locale LOCALE = Locale.getDefault();
    private static final DecimalFormat DF = (DecimalFormat) NumberFormat.getInstance(LOCALE).clone();
    private static final String[] UNITS = new String[] { "", "k", "M", "G", "T", "P" };
    private static final EnumMap<TRANSLATE_TYPE, String> TRANSLATION_TYPES = new EnumMap<>(TRANSLATE_TYPE.class);

    static {
        // fill translation types enum
        TRANSLATION_TYPES.put(TRANSLATE_TYPE.CONTAINER, "container");
        TRANSLATION_TYPES.put(TRANSLATE_TYPE.TOOLTIP, "tooltip");
        TRANSLATION_TYPES.put(TRANSLATE_TYPE.BUTTON, "button");
        TRANSLATION_TYPES.put(TRANSLATE_TYPE.IO_SIDE, "io_side");
        TRANSLATION_TYPES.put(TRANSLATE_TYPE.IO_SETTING, "io_setting");
        TRANSLATION_TYPES.put(TRANSLATE_TYPE.EXTRACT_SETTING, "extract_setting");
        TRANSLATION_TYPES.put(TRANSLATE_TYPE.JEI, "jei");

        // adjust decimal format
        DF.setRoundingMode(RoundingMode.DOWN);
    }

    private TextUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Gets a resource location with the given key
     * and the namespace of the mod.
     * @param key the key to generate the resource location with
     * @return the generated resource location
     */
    public static ResourceLocation getRL(String key) {
        return new ResourceLocation(MOD_ID, key);
    }

    /**
     * Formats Forge Energy into a readable String with an easy to read suffix.
     * Can also give back the full formatted energy amount if a true boolean is passed in.
     * @author Chronophylos
     * @param number the energy amount to format
     * @param advanced the boolean to define if full energy amount is used
     * @return the readable string representation of the energy
     */
    public static String formatEnergy(Number number, boolean advanced) {
        if (!advanced) {
            // convert numbers to compact form
            int numberOfDigits = number.intValue() == 0
                ? 0
                : (int) (1 + Math.floor(Math.log10(Math.abs(number.doubleValue()))));
            int base10Exponent = numberOfDigits < 4 ? 0 : 3 * ((numberOfDigits - 1) / 3);
            double normalized = number.doubleValue() / Math.pow(10, base10Exponent);
            return String.format(LOCALE, "%s %sFE", formatNumber(normalized, 1, 2), UNITS[base10Exponent / 3]);
        }
        // normal energy format
        return String.format(LOCALE, "%s FE", formatNumber(number, 1, 3));
    }

    /**
     * Formats numbers with fraction digits into readable strings by
     * adjusting the fraction digit amount.
     * @param input the number to format
     * @param minFraction the minimum amount of fraction digits
     * @param maxFraction the maximum amount of fraction digits
     * @return the readable string representation of the number
     */
    public static String formatNumber(Number input, int minFraction, int maxFraction) {
        DF.setMinimumFractionDigits(minFraction);
        DF.setMaximumFractionDigits(maxFraction);
        return DF.format(input);
    }

    /**
     * Generates a Translation Text Component within the mods namespace
     * with a custom type, key and optional color.
     * @param type the type of the translation
     * @param key the unique key of the translation
     * @param color an optional color
     * @return the translated phrase
     */
    public static TranslationTextComponent translate(TRANSLATE_TYPE type, String key, TextFormatting... color) {
        TranslationTextComponent output = new TranslationTextComponent(getTranslationKey(type, key));
        return color.length == 0 ? output : (TranslationTextComponent) output.withStyle(color[0]);
    }

    /**
     * Colores a given String with the given color.
     * @param input the string to color
     * @param color an optional color
     * @return the colorized string
     */
    public static StringTextComponent colorize(String input, TextFormatting color) {
        return (StringTextComponent) new StringTextComponent(input).withStyle(color);
    }

    /**
     * Gets the translation key from the provided type and key.
     * @param type the type of the translation
     * @param key the unique key of the translation
     * @return the translation key
     */
    private static String getTranslationKey(TRANSLATE_TYPE type, String key) {
        return String.format("%s.%s.%s", TRANSLATION_TYPES.get(type), MOD_ID, key);
    }

    /**
     * Translates the given io side to a string.
     * @param side the io side to translate
     * @return the side as string
     */
    public static String translateIOSide(IO_SIDE side) {
        return IOUtil.getIoSideTranslations().get(side);
    }

    /**
     * Translates the given io setting to a string.
     * @param setting the io setting to translate
     * @return the setting as string
     */
    public static String translateIOSetting(IO_SETTING setting) {
        return IOUtil.getIoSettingsTranslations().get(setting);
    }

    /**
     * Translates the given auto extract mode to a string.
     * @param mode the mode to translate
     * @return the mode as string
     */
    public static TranslationTextComponent translateIOSetting(boolean mode) {
        return TextUtil.translate(TRANSLATE_TYPE.TOOLTIP, mode ? "extract_activated" : "extract_deactivated");
    }
}
