package nz.pactifylauncher.plugin.bukkit.util;

import pactify.client.api.mcprotocol.model.NotchianBlockPos;
import pactify.client.api.mcprotocol.model.NotchianItemStack;
import pactify.client.api.mcprotocol.model.NotchianNbtTagCompound;

public interface NotchianPacketBuffer<R> {
    R writeBytes(byte[] var1);

    R writeBytes(byte[] var1, int var2, int var3);

    R readBytes(byte[] var1);

    R readBytes(byte[] var1, int var2, int var3);

    boolean readBoolean();

    R writeBoolean(boolean var1);

    byte readByte();

    R writeByte(int var1);

    short readShort();

    R writeShort(int var1);

    int readInt();

    R writeInt(int var1);

    long readLong();

    R writeLong(long var1);

    float readFloat();

    R writeFloat(float var1);

    double readDouble();

    R writeDouble(double var1);

    int readVarInt();

    R writeVarInt(int var1);

    long readVarLong();

    R writeVarLong(long var1);

    byte[] readByteArray();

    byte[] readByteArray(int var1);

    R writeByteArray(byte[] var1);

    int[] readVarIntArray();

    int[] readVarIntArray(int var1);

    R writeVarIntArray(int[] var1);

    long[] readLongArray();

    long[] readLongArray(int var1);

    R writeLongArray(long[] var1);

    NotchianChatComponent readNotchianChatComponent();

    R writeNotchianChatComponent(NotchianChatComponent var1);

    NotchianBlockPos readNotchianBlockPos();

    R writeNotchianBlockPos(NotchianBlockPos var1);

    NotchianItemStack readNotchianItemStack();

    R writeNotchianItemStack(NotchianItemStack var1);

    NotchianNbtTagCompound readNotchianNbtTagCompound();

    R writeNotchianNbtTagCompound(NotchianNbtTagCompound var1);
}
