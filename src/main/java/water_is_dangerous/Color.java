package water_is_dangerous;

/**
 * @author 启梦
 */
@SuppressWarnings("unused")
public enum Color {
    BLACK(0, 0, 0),
    DARK_BLUE(0, 0, 170),
    DARK_GREEN(0, 170, 0),
    DARK_AQUA(0, 170, 170),
    DARK_RED(170, 0, 0),
    DARK_PURPLE(170, 0, 170),
    GOLD(255, 170, 0),
    GRAY(170, 170, 170),
    DARK_GRAY(85, 85, 85),
    BLUE(85, 85, 255),
    GREEN(85, 255, 85),
    AQUA(85, 255, 255),
    RED(255, 85, 85),
    LIGHT_PURPLE(255, 85, 255),
    YELLOW(255, 255, 85),
    WHITE(255, 255, 255),
    MINECOIN_GOLD(221, 214, 5),
    MATERIAL_QUARTZ(217, 212, 209),
    MATERIAL_IRON(206, 202, 202),
    MATERIAL_NETHERITE(68, 58, 59),
    MATERIAL_REDSTONE(151, 22, 7),
    MATERIAL_COPPER(180, 104, 77),
    MATERIAL_GOLD(222, 177, 45),
    MATERIAL_EMERALD(17, 160, 54),
    MATERIAL_DIAMOND(44, 186, 168),
    MATERIAL_LAPIS(33, 73, 123),
    MATERIAL_AMETHYST(154, 92, 198);
    public final int R, G, B;
    Color(int red, int green, int blue) {
        this.R = red;
        this.G = green;
        this.B = blue;
    }
}
