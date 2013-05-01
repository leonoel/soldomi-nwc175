package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;

public class NwcRepeat extends NwcSymbol {

  private final NwcItem BYTE3 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      reader.skip(1);
      return this;
    }
  };

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Repeat *****" + endl);
    builder.append("***** End repeat *****" + endl);
    return builder.toString();
  }

  public NwcRepeat marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcRepeat unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    return this;
  }

}
