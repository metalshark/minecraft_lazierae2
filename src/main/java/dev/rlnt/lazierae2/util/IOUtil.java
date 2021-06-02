package dev.rlnt.lazierae2.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import dev.rlnt.lazierae2.util.TypeEnums.IO_SETTING;
import dev.rlnt.lazierae2.util.TypeEnums.IO_SIDE;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class IOUtil {

    private static final Map<Integer, IO_SIDE> IO_SIDE_MAP = new HashMap<>();
    private static final BiMap<Integer, IO_SETTING> IO_SETTINGS_MAP = HashBiMap.create(IO_SETTING.values().length);
    private static final EnumMap<IO_SIDE, String> IO_SIDE_TRANSLATIONS = new EnumMap<>(IO_SIDE.class);
    private static final EnumMap<IO_SETTING, String> IO_SETTINGS_TRANSLATIONS = new EnumMap<>(IO_SETTING.class);

    static {
        // fill maps with initial values
        IO_SIDE[] sides = IO_SIDE.values();
        for (int i = 0; i < sides.length; i++) {
            IOUtil.IO_SIDE_MAP.put(i + 1, sides[i]);
        }
        IO_SETTING[] ioSettings = IO_SETTING.values();
        for (int i = 0; i < ioSettings.length; i++) {
            IOUtil.IO_SETTINGS_MAP.put(i, ioSettings[i]);
        }
        // translations
        IO_SIDE_TRANSLATIONS.put(IO_SIDE.TOP, "top");
        IO_SIDE_TRANSLATIONS.put(IO_SIDE.LEFT, "left");
        IO_SIDE_TRANSLATIONS.put(IO_SIDE.FRONT, "front");
        IO_SIDE_TRANSLATIONS.put(IO_SIDE.RIGHT, "right");
        IO_SIDE_TRANSLATIONS.put(IO_SIDE.BOTTOM, "bottom");
        IO_SIDE_TRANSLATIONS.put(IO_SIDE.BACK, "back");
        IO_SETTINGS_TRANSLATIONS.put(IO_SETTING.NONE, "none");
        IO_SETTINGS_TRANSLATIONS.put(IO_SETTING.INPUT, "input");
        IO_SETTINGS_TRANSLATIONS.put(IO_SETTING.OUTPUT, "output");
        IO_SETTINGS_TRANSLATIONS.put(IO_SETTING.IO, "io");
    }

    private IOUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Converts an integer array of io settings to a valid side configuration.
     * The integer array only contains the actual io settings, no sides.
     * This integer array ususally comes from syncing the information from
     * the server to the client via an IIntArray.
     * @param ioSettings the side settings in an integer array
     */
    public static Map<IO_SIDE, IO_SETTING> getSideConfigFromArray(int[] ioSettings) {
        EnumMap<IO_SIDE, IO_SETTING> config = new EnumMap<>(IO_SIDE.class);
        for (int i = 0; i < ioSettings.length; i++) {
            IO_SIDE side = IO_SIDE_MAP.get(i + 1);
            IO_SETTING setting = IO_SETTINGS_MAP.get(ioSettings[i]);
            config.put(side, setting);
        }
        return config;
    }

    /**
     * Converts a side config map to a serializable integer
     * array while only respecting the io settings.
     * @param sideConfig the side config map to convert
     * @return the serializable integer array of io settings
     */
    public static int[] serializeSideConfig(Map<IO_SIDE, IO_SETTING> sideConfig) {
        int[] ioSettings = new int[sideConfig.size()];
        int[] i = { 0 };
        sideConfig
            .values()
            .forEach(
                setting -> {
                    ioSettings[i[0]] = IOUtil.getIOSettingsMap().inverse().get(setting);
                    i[0]++;
                }
            );
        return ioSettings;
    }

    /**
     * Checks if the passed in io configuration was modified.
     * @param sideConfig the side configuration to check
     * @return true if it was modified, false otherwise
     */
    public static boolean isChanged(Map<IO_SIDE, IO_SETTING> sideConfig) {
        int[] converted = serializeSideConfig(sideConfig);
        int[] unchanged = { 0, 0, 0, 0, 0, 0 };
        return !Arrays.equals(converted, unchanged);
    }

    public static BiMap<Integer, IO_SETTING> getIOSettingsMap() {
        return IO_SETTINGS_MAP;
    }

    static Map<IO_SETTING, String> getIoSettingsTranslations() {
        return IO_SETTINGS_TRANSLATIONS;
    }

    static EnumMap<IO_SIDE, String> getIoSideTranslations() {
        return IO_SIDE_TRANSLATIONS;
    }
}
