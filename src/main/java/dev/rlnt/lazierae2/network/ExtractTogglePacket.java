package dev.rlnt.lazierae2.network;

import dev.rlnt.lazierae2.container.base.ProcessorContainer;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import java.util.function.Supplier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

@SuppressWarnings({ "unused", "java:S1172" })
public class ExtractTogglePacket {

    static ExtractTogglePacket fromBytes(PacketBuffer packetBuffer) {
        return new ExtractTogglePacket();
    }

    static void handle(ExtractTogglePacket packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        context.get().enqueueWork(() -> handlePacket(player));
        context.get().setPacketHandled(true);
    }

    private static void handlePacket(ServerPlayerEntity player) {
        if (player != null && player.containerMenu instanceof ProcessorContainer) {
            ProcessorTile<?, ?> tile = ((ProcessorContainer<?>) player.containerMenu).getTile();
            tile.toggleAutoExtract();
        }
    }

    @SuppressWarnings("java:S1186")
    void toBytes(PacketBuffer buffer) {}
}
