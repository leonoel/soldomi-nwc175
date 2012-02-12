package nwcfile;

public class Pedal extends Symbol {
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

  public Pedal marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public Pedal unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    return this;
  }
}