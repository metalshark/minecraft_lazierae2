package dev.rlnt.lazierae2.network;

import dev.rlnt.lazierae2.container.base.ProcessorContainer;
import dev.rlnt.lazierae2.tile.base.ProcessorTile;
import java.util.function.Supplier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class EnergyResetPacket {

    private int energy = 0;

    static EnergyResetPacket fromBytes(PacketBuffer buffer) {
        EnergyResetPacket packet = new EnergyResetPacket();
        packet.energy = buffer.readInt();
        return packet;
    }

    static void handle(EnergyResetPacket packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        context.get().enqueueWork(() -> handlePacket(packet, player));
        context.get().setPacketHandled(true);
    }

    private static void handlePacket(EnergyResetPacket packet, ServerPlayerEntity player) {
        if (player != null && player.containerMenu instanceof ProcessorContainer) {
            ProcessorTile<?, ?> tile = ((ProcessorContainer<?>) player.containerMenu).getTile();
            tile.getEnergyStorage().setEnergy(packet.energy);
        }
    }

    void toBytes(PacketBuffer buffer) {
        buffer.writeInt(energy);
    }
}
