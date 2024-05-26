package nz.pactifylauncher.plugin.bukkit.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import pactify.client.api.mcprotocol.util.DecoderException;
import pactify.client.api.mcprotocol.util.EncoderException;

public final class NotchianPacketUtil {
  public static <T extends Enum<T>> T readEnumValue(NotchianPacketBuffer buf, Class<T> enumClass) {
    return (T)((Enum[])enumClass.getEnumConstants())[buf.readVarInt()];
  }
  
  public static void writeEnumValue(NotchianPacketBuffer buf, Enum value) {
    buf.writeVarInt(value.ordinal());
  }
  
  public static UUID readUuid(NotchianPacketBuffer buf) {
    return new UUID(buf.readLong(), buf.readLong());
  }
  
  public static void writeUuid(NotchianPacketBuffer buf, UUID uuid) {
    buf.writeLong(uuid.getMostSignificantBits());
    buf.writeLong(uuid.getLeastSignificantBits());
  }
  
  public static String readString(NotchianPacketBuffer buf, int maxLength) {
    return readString(buf, maxLength, false);
  }
  
  public static String readString(NotchianPacketBuffer buf, int maxLength, boolean allowNull) {
    int i = buf.readVarInt();
    if (allowNull) {
      if (i == 0)
        return null; 
      i--;
    } 
    if (i > maxLength * 4)
      throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + (maxLength * 4) + ")"); 
    if (i < 0)
      throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!"); 
    byte[] arrayOfByte = new byte[i];
    buf.readBytes(arrayOfByte);
    String str = new String(arrayOfByte, StandardCharsets.UTF_8);
    if (str.length() > maxLength)
      throw new DecoderException("The received string length is longer than maximum allowed (" + str
          .length() + " > " + maxLength + ")"); 
    return str;
  }
  
  public static void writeString(NotchianPacketBuffer buf, String str, int maxLength) {
    writeString(buf, str, maxLength, false);
  }
  
  public static void writeString(NotchianPacketBuffer buf, String str, int maxLength, boolean allowNull) {
    if (allowNull && str == null) {
      buf.writeVarInt(0);
      return;
    } 
    if (str.length() > maxLength)
      throw new EncoderException("String too big (was " + str
          .length() + " chars encoded, max " + maxLength + ")"); 
    byte[] arrayOfByte = str.getBytes(StandardCharsets.UTF_8);
    if (arrayOfByte.length > maxLength * 4)
      throw new EncoderException("String too big (was " + arrayOfByte.length + " bytes encoded, max " + (maxLength * 4) + ")");
    if (allowNull) {
      buf.writeVarInt(arrayOfByte.length + 1);
    } else {
      buf.writeVarInt(arrayOfByte.length);
    } 
    buf.writeBytes(arrayOfByte);
  }
  
  public static NotchianChatComponent readNullableChatComponent(NotchianPacketBuffer buf) {
    if (buf.readBoolean())
      return buf.readNotchianChatComponent(); 
    return null;
  }
  
  public static void writeNullableChatComponent(NotchianPacketBuffer buf, NotchianChatComponent component) {
    if (component == null) {
      buf.writeBoolean(false);
    } else {
      buf.writeBoolean(true);
      buf.writeNotchianChatComponent(component);
    } 
  }
}
