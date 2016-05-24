package com.techreimagined.common.network;

import com.techreimagined.common.network.messages.PacketButtonClick;
import com.techreimagined.ModInfo;
import com.techreimagined.common.util.LogHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLIndexedMessageToMessageCodec;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;
import java.util.EnumMap;

public class PacketHandler extends FMLIndexedMessageToMessageCodec<SimplePacket> {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MOD_ID.toLowerCase());

    public static void init() {
        INSTANCE.registerMessage(PacketButtonClick.class, PacketButtonClick.class, 0, Side.SERVER);
    }
    private static EnumMap<Side, FMLEmbeddedChannel> channels;
    public int nextDiscriminator = 0;

    public PacketHandler()
    {
        MinecraftForge.EVENT_BUS.post(new AddDiscriminatorEvent(this));
    }

    public static EnumMap<Side, FMLEmbeddedChannel> getChannels()
    {
        return channels;
    }

    public static void setChannels(EnumMap<Side, FMLEmbeddedChannel> _channels)
    {
        channels = _channels;
    }

    public static void sendPacketToServer(SimplePacket packet)
    {
        PacketHandler.getChannels().get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET)
                .set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        PacketHandler.getChannels().get(Side.CLIENT).writeOutbound(packet);
    }

    public static void sendPacketToPlayer(SimplePacket packet, EntityPlayer player)
    {
        PacketHandler.getChannels().get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET)
                .set(FMLOutboundHandler.OutboundTarget.PLAYER);
        PacketHandler.getChannels().get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        PacketHandler.getChannels().get(Side.SERVER).writeOutbound(packet);
    }

    public static void sendPacketToAllPlayers(SimplePacket packet)
    {
        PacketHandler.getChannels().get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET)
                .set(FMLOutboundHandler.OutboundTarget.ALL);
        PacketHandler.getChannels().get(Side.SERVER).writeOutbound(packet);
    }

    public static void sendPacketToAllPlayers(Packet packet, World world)
    {
        if(packet == null){
            return;
        }
        for (Object player : world.playerEntities)
        {
            if (player instanceof EntityPlayerMP)
                if (player != null)
                    ((EntityPlayerMP) player).connection.sendPacket(packet);
        }
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, SimplePacket msg, ByteBuf target) throws Exception
    {
        msg.writePacketData(target);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, SimplePacket msg)
    {
        try
        {
            msg.readPacketData(source);
            msg.execute();
        } catch (IOException e)
        {
            LogHelper.warn("Something caused a Protocol Exception!");
        }
    }

    @Override
    public FMLIndexedMessageToMessageCodec<SimplePacket> addDiscriminator(int discriminator, Class<? extends SimplePacket> type)
    {
        nextDiscriminator++;
        return super.addDiscriminator(discriminator, type);
    }

}