package com.ultreon.devices.init;

import com.ultreon.devices.Devices;
import com.ultreon.devices.block.entity.LaptopBlockEntity;
import com.ultreon.devices.block.entity.PaperBlockEntity;
import com.ultreon.devices.block.entity.PrinterBlockEntity;
import com.ultreon.devices.block.entity.RouterBlockEntity;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static com.ultreon.devices.Devices.id;

@SuppressWarnings("ConstantConditions")
public class DeviceBlockEntities {
    private static final Registrar<BlockEntityType<?>> REGISTER = Devices.REGISTRIES.get().get(Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<BlockEntityType<PaperBlockEntity>> PAPER = REGISTER.register(id("paper"), () -> BlockEntityType.Builder.of(PaperBlockEntity::new, DeviceBlocks.PAPER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<LaptopBlockEntity>> LAPTOP = REGISTER.register(id("laptop"), () -> BlockEntityType.Builder.of(LaptopBlockEntity::new, DeviceBlocks.getAllLaptops().toArray(new Block[]{})).build(null));
    public static final RegistrySupplier<BlockEntityType<PrinterBlockEntity>> PRINTER = REGISTER.register(id("printer"), () -> BlockEntityType.Builder.of(PrinterBlockEntity::new, DeviceBlocks.getAllPrinters().toArray(new Block[]{})).build(null));
    public static final RegistrySupplier<BlockEntityType<RouterBlockEntity>> ROUTER = REGISTER.register(id("router"), () -> BlockEntityType.Builder.of(RouterBlockEntity::new, DeviceBlocks.getAllRouters().toArray(new Block[]{})).build(null));

    public static void register() {
    }
}
