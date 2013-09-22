package org.soldomi.support.nwc175;

public class NwcUtils {

  public static int subByte(int b, int start, int end) {
    return (b >> start) & (0xFF >> (8 - end + start));
  } 


} 
