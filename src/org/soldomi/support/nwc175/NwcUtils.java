package org.soldomi.support.nwc175;

public class NwcUtils {

  public static byte subByte(byte b, int start, int end) {
    return (byte) ((b >> start) & (0xFF >> (8 - end + start)));
  } 


} 
