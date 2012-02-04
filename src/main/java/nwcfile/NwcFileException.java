package nwcfile;

public class NwcFileException extends Exception {
  public NwcFileException(String str) {
    super(str);
  }

  public NwcFileException(Exception e) {
    super(e);
  }
}