package org.soldomi.support.nwc175;

public interface NwcItem {
  public NwcItem marshall(NwcFileWriter writer) throws NwcFileException;
  public NwcItem unmarshall(NwcFileReader reader) throws NwcFileException;
}
