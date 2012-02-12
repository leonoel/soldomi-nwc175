package nwcfile;

public class TempoVariance extends Symbol {

  public enum TempoVarianceType {
    BREATHE,
    FERMATA,
    ACCEL,
    ALLARG,
    RALL,
    RITARD,
    RIT,
    RUBATO,
    STRING;
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
	setTempoVarianceType(TempoVarianceType.values()[reader.readByte()]);
	return this;
      }
  };

  private final NwcItem BYTE6 = new NwcItem() {

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

  private TempoVarianceType m_type;

  public void setTempoVarianceType(TempoVarianceType type) {
    m_type = type;
  }

  public TempoVariance marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public TempoVariance unmarshall(NwcFileReader reader)
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