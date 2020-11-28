package com.github.nutyworks.periodicquiz

enum class ElementProperty(val deprecated: Boolean = false) {
    NAME,
    APPEARANCE,
    ATOMIC_MASS,
    BOIL,
    CATEGORY,
    DENSITY,
    MELT,
    MOLAR_HEAT,
    NUMBER,
    PERIOD,
    PHASE,
    SYMBOL,
    SHELLS,

    /**
     * @deprecated non-color and unknown-color element exists.
     */
    COLOR(true),
    /**
     * @deprecated element that cannot know who discovered exists.
     */
    DISCOVERED_BY(true),
    /**
     * @deprecated element that cannot know who named exists.
     */
    NAMED_BY(true);
}
