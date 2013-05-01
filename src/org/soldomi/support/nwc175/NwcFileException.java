package org.soldomi.support.nwc175;

public class NwcFileException extends Exception {
  public NwcFileException(String str) {
    super(str);
  }

  public NwcFileException(Exception e) {
    super(e);
  }

  public NwcFileException(String str, Exception e) {
    super(str, e);
  }
}
