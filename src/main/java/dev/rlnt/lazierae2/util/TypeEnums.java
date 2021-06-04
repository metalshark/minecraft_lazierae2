package dev.rlnt.lazierae2.util;

public class TypeEnums {

    private TypeEnums() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Defines the type of the progress bar inside
     * a processor screen.
     */
    public enum PROGRESS_BAR_TYPE {
        PRIMARY,
        SECONDARY,
        SINGLE
    }

    /**
     * Defines the type of the translation to
     * identify its key inside the lang file.
     */
    public enum TRANSLATE_TYPE {
        CONTAINER,
        TOOLTIP,
        BUTTON,
        IO_SIDE,
        IO_SETTING,
        EXTRACT_SETTING,
        JEI
    }

    /**
     * Defines the basic math operation type to
     * pass it to different calculation methods.
     */
    public enum OPERATION_TYPE {
        ADD,
        MULTI,
        DIV
    }

    /**
     * Defines the possible sides of a processor
     * to set the IO setting for.
     */
    public enum IO_SIDE {
        TOP,
        LEFT,
        FRONT,
        RIGHT,
        BOTTOM,
        BACK
    }

    /**
     * Defines the different possible IO settings
     * for processor sides.
     */
    public enum IO_SETTING {
        NONE,
        INPUT,
        OUTPUT,
        IO
    }
}
