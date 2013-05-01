package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;

public class NwcPedal extends NwcSymbol {
  public enum PedalType {
    UP,
    DOWN;
  }

  private final NwcItem BYTE3 = new NwcItem() {
      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	reader.skip(1);
	return this;
      }
  };

  private final NwcItem BYTE5 = new NwcItem() {
      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	setType(PedalType.values()[reader.readByte()]);
	return this;
      }
  };

  private PedalType m_type;

  public void setType(PedalType type) {
    m_type = type;
  }

  public NwcPedal marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcPedal unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    return this;
  }
}
