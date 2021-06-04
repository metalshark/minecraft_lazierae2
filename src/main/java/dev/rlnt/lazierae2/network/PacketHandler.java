package dev.rlnt.lazierae2.network;

import static dev.rlnt.lazierae2.Constants.MOD_ID;
import static dev.rlnt.lazierae2.Constants.NETWORK;

import dev.rlnt.lazierae2.util.TextUtil;
import java.util.Objects;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@SuppressWarnings("java:S1602")
public class PacketHandler {

    public static final SimpleChannel channel;
    private static final String ID = MOD_ID + "-" + NETWORK;

    static {
        channel =
            NetworkRegistry.ChannelBuilder
                .named(TextUtil.getRL(NETWORK))
                .clientAcceptedVersions(s -> Objects.equals(s, ID))
                .serverAcceptedVersions(s -> Objects.equals(s, ID))
                .networkProtocolVersion(() -> ID)
                .simpleChannel();

        channel
            .messageBuilder(EnergyResetPacket.class, 1)
            .decoder(EnergyResetPacket::fromBytes)
            .encoder(EnergyResetPacket::toBytes)
            .consumer(EnergyResetPacket::handle)
            .add();

        channel
            .messageBuilder(ExtractTogglePacket.class, 2)
            .decoder(ExtractTogglePacket::fromBytes)
            .encoder(ExtractTogglePacket::toBytes)
            .consumer(ExtractTogglePacket::handle)
            .add();

        channel
            .messageBuilder(IOUpdatePacket.class, 3)
            .decoder(IOUpdatePacket::fromBytes)
            .encoder(IOUpdatePacket::toBytes)
            .consumer(IOUpdatePacket::handle)
            .add();
    }

    private PacketHandler() {
        throw new IllegalStateException("Utility class");
    }

    @SuppressWarnings("java:S1186")
    public static void init() {}
}
