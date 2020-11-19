package crimsonfluff.crimsongenerators.util;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigBuilder {
    public final ForgeConfigSpec CLIENT;

    public ForgeConfigSpec.IntValue tierONE;
    public ForgeConfigSpec.IntValue tierTWO;
    public ForgeConfigSpec.IntValue tierTHREE;

    public ConfigBuilder() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("General Settings");
        builder.push("upgrades");

        tierONE = builder
            .defineInRange("tierONE", 16, 5, 48);
        tierTWO = builder
            .defineInRange("tierTWO", 16, 5, 48);
        tierTHREE = builder
            .defineInRange("tierTHREE", 16, 5, 48);

        builder.pop();

        CLIENT = builder.build();
    }
}
