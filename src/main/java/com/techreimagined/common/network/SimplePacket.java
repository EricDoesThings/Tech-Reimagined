package com.techreimagined.common.network;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;

/* You are free to:
 * 
 * Share — copy and redistribute the material in any medium or format
 * Adapt — remix, transform, and build upon the material
 * for any purpose, even commercially.
 * The licensor cannot revoke these freedoms as long as you follow the license terms.
 * Under the following terms:

 * Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
 * ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.
 * No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.
 * Notices:
 * 
 * You do not have to comply with the license for elements of the material in the public domain or where your use is permitted by an applicable exception or limitation.
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */
public abstract class SimplePacket
{
    protected EntityPlayer player;
    protected byte mode;

    public SimplePacket(EntityPlayer _player)
    {
        player = _player;
    }

    @SuppressWarnings("unused")
    public SimplePacket()
    {
        player = null;
    }

    public static String readString(ByteBuf in) throws IOException
    {
        byte[] stringBytes = new byte[in.readInt()];
        in.readBytes(stringBytes);
        return new String(stringBytes, Charsets.UTF_8);
    }

    public static void writeString(String string, ByteBuf out) throws IOException
    {
        byte[] stringBytes;
        stringBytes = string.getBytes(Charsets.UTF_8);
        out.writeInt(stringBytes.length);
        out.writeBytes(stringBytes);
    }

    public static World readWorld(ByteBuf in) throws IOException
    {
        return DimensionManager.getWorld(in.readInt());
    }

    public static void writeWorld(World world, ByteBuf out) throws IOException
    {
        out.writeInt(world.provider.getDimension());
    }

    public static EntityPlayer readPlayer(ByteBuf in) throws IOException
    {
        if (!in.readBoolean())
            return null;
        World playerWorld = readWorld(in);
        return playerWorld.getPlayerEntityByName(readString(in));
    }

    public static void writePlayer(EntityPlayer player, ByteBuf out) throws IOException
    {
        if (player == null)
        {
            out.writeBoolean(false);
            return;
        }
        out.writeBoolean(true);
        writeWorld(player.worldObj, out);
        writeString(player.getName(), out);
    }

    public static TileEntity readTileEntity(ByteBuf in) throws IOException
    {
        return readWorld(in).getTileEntity(new BlockPos(in.readInt(), in.readInt(), in.readInt()));
    }

    public static void writeTileEntity(TileEntity tileEntity, ByteBuf out) throws IOException
    {
        writeWorld(tileEntity.getWorld(), out);
        out.writeInt(tileEntity.getPos().getX());
        out.writeInt(tileEntity.getPos().getY());
        out.writeInt(tileEntity.getPos().getZ());
    }

    public static Fluid readFluid(ByteBuf in) throws IOException
    {
        return FluidRegistry.getFluid(readString(in));
    }

    public static void writeFluid(Fluid fluid, ByteBuf out) throws IOException
    {
        if (fluid == null)
        {
            writeString("", out);
            return;
        }
        writeString(fluid.getName(), out);
    }

    public void writePacketData(ByteBuf out) throws IOException
    {
        out.writeByte(mode);
        writePlayer(player, out);
        writeData(out);
    }

    public abstract void writeData(ByteBuf out) throws IOException;

    public void readPacketData(ByteBuf in) throws IOException
    {
        mode = in.readByte();
        player = readPlayer(in);
        readData(in);
    }

    public abstract void readData(ByteBuf in) throws IOException;

    public abstract void execute();

    public void sendPacketToServer()
    {
        PacketHandler.sendPacketToServer(this);
    }

    public void sendPacketToPlayer(EntityPlayer player)
    {
        PacketHandler.sendPacketToPlayer(this, player);
    }

    public void sendPacketToAllPlayers()
    {
        PacketHandler.sendPacketToAllPlayers(this);
    }

}
