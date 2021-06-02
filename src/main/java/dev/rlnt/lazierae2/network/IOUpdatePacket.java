package dev.rlnt.lazierae2.network;

import dev.rlnt.lazierae2.container.base.ProcessorContainer;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import dev.rlnt.lazierae2.util.IOUtil;
import java.util.function.Supplier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class IOUpdatePacket {

    private int[] ioSettings;

    public IOUpdatePacket(int[] ioSettings) {
        this.ioSettings = ioSettings;
    }

    private IOUpdatePacket() {
        ioSettings = new int[6];
    }

    static IOUpdatePacket fromBytes(PacketBuffer buffer) {
        IOUpdatePacket packet = new IOUpdatePacket();
        packet.ioSettings = buffer.readVarIntArray();
        return packet;
    }

    static void handle(IOUpdatePacket packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        context.get().enqueueWork(() -> handlePacket(packet, player));
        context.get().setPacketHandled(true);
    }

    private static void handlePacket(IOUpdatePacket packet, ServerPlayerEntity player) {
        if (player != null && player.containerMenu instanceof ProcessorContainer) {
            ProcessorTile<?, ?> tile = ((ProcessorContainer<?>) player.containerMenu).getTile();
            tile.setSideConfig(IOUtil.getSideConfigFromArray(packet.ioSettings));
        }
    }

    void toBytes(PacketBuffer buffer) {
        buffer.writeVarIntArray(ioSettings);
    }
}
