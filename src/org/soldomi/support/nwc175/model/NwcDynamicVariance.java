package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;
import org.soldomi.support.nwc175.NwcUtils;

public class NwcDynamicVariance extends NwcSymbol {

  public enum DynamicVarianceType {
    CRESCENDO,
    DECRESCENDO,
    DIMINUENDO,
    RINFORZANDO,
    SFOZANDO;
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
      private static final int TYPE_START = 0;
      private static final int TYPE_END = 3;

      public NwcItem marshall(NwcFileWriter writer) 
	throws NwcFileException {
	// TODO
	return this;
      }

      public NwcItem unmarshall(NwcFileReader reader)
	throws NwcFileException {
	setType(DynamicVarianceType.values()[NwcUtils.subByte(reader.readByte(),
							      TYPE_START,
							      TYPE_END)]);
	return this;
      }
  };

  private DynamicVarianceType m_type;

  public void setType(DynamicVarianceType type) {
    m_type = type;
  }

  public NwcDynamicVariance marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcDynamicVariance unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    return this;
  }


}
