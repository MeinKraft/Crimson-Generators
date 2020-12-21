package crimsonfluff.crimsongenerators.init;

import crimsonfluff.crimsongenerators.CrimsonGenerators;
import crimsonfluff.crimsongenerators.containers.GeneratorContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class containersInit {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, CrimsonGenerators.MOD_ID);

    public static final RegistryObject<ContainerType<GeneratorContainer>> GENERATOR = CONTAINERS.register("generator_chest",
            () -> new ContainerType<>(GeneratorContainer::new));
}
