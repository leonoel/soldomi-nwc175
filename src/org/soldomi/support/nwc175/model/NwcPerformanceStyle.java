package org.soldomi.support.nwc175.model;

import org.soldomi.support.nwc175.NwcFileReader;
import org.soldomi.support.nwc175.NwcFileWriter;
import org.soldomi.support.nwc175.NwcItem;
import org.soldomi.support.nwc175.NwcFileException;

public class NwcPerformanceStyle extends NwcSymbol {

  public enum PerformanceStyleType {
    AD_LIB,
    ANIMATO,
    CANTABILE,
    CON_BRIO,
    DOLCE,
    ESPRESSIVO,
    GRAZIOSO,
    LEGATO,
    MAESTOSO,
    MARCATO,
    MENO_MOSSO,
    POCO_A_POCO,
    PIU_MOSSO,
    SEMPLICE,
    SIMILE,
    SOLO,
    SOSTENUTO,
    SOTTO_VOCE,
    STACCATO,
    SUBITO,
    TENUTO,
    TUTTI,
    VOLTA_SUBITO;
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
	setType(PerformanceStyleType.values()[reader.readByte()]);
	return this;
      }
  };

  private PerformanceStyleType m_type;
  
  public void setType(PerformanceStyleType type) {
    m_type = type;
  }

  public NwcPerformanceStyle marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcPerformanceStyle unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    return this;
  }

}
