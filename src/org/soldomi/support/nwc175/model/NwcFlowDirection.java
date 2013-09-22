package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;

public class NwcFlowDirection extends NwcSymbol {
  public enum FlowDirectionType {
    CODA,
    SEGNO,
    FINE,
    TO_CODA,
    DA_CAPO,
    DC_AL_CODA,
    DC_AL_FINE,
    DAL_SEGNO,
    DS_AL_CODA,
    DS_AL_FINE;
  } // TODO check order !!

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

      public NwcItem unmarshall(NwcFileReader reader) throws NwcFileException {
	  int b = reader.readByte();
	  try {
	      setType(FlowDirectionType.values()[b]);
	  } catch (ArrayIndexOutOfBoundsException e) {
	      throw new NwcFileException("Unknown flow direction : " + b);
	  }
	  return this;
      }
  };

  private final NwcItem BYTE6 = new NwcItem() {
      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader) throws NwcFileException {
	  reader.skip(1);
	  return this;
      }
  };

  private FlowDirectionType m_type;

  public void setType(FlowDirectionType type) {
    m_type = type;
  }

  public NwcFlowDirection marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcFlowDirection unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    BYTE6.unmarshall(reader);
    return this;
  }
}
