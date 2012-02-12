package nwcfile;

public class Tempo extends Symbol {
  public enum Base {
    EIGHTH,
    DOTTED_EIGHTH,
    QUARTER,
    DOTTED_QUARTER,
    HALF,
    DOTTED_HALF;
  }

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


  private final NwcItem BYTE5 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      setBpm(reader.readByte());
      return this;
    }
  };

  private final NwcItem BYTE6 = new NwcItem() {
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

  private final NwcItem BYTE7 = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      setBase(Base.values()[reader.readByte()]);
      return this;
    }
  };

  private final NwcItem LAST_STRING = new NwcItem() {
    public NwcItem marshall(NwcFileWriter writer)
      throws NwcFileException {
      return this;
    }

    public NwcItem unmarshall(NwcFileReader reader)
      throws NwcFileException {
      setText(reader.readString());
      return this;
    }
  };


  private byte m_bpm;
  private Base m_base;
  private String m_text;

  public Tempo() {
  }

  public void setBpm(byte bpm) {
    m_bpm = bpm;
  }

  public void setBase(Base base) {
    m_base = base;
  }

  public void setText(String text) {
    m_text = text;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Tempo *****" + endl);
    builder.append("Bpm : " + m_bpm + endl);
    builder.append("Base : " + m_base + endl);
    builder.append("Text : " + m_text + endl);
    builder.append("***** End tempo *****" + endl);
    return builder.toString();
  }

  public Tempo marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public Tempo unmarshall(NwcFileReader reader)
    throws NwcFileException {
    BYTE1.unmarshall(reader);
    BYTE2.unmarshall(reader);
    BYTE3.unmarshall(reader);
    BYTE4.unmarshall(reader);
    BYTE5.unmarshall(reader);
    BYTE6.unmarshall(reader);
    BYTE7.unmarshall(reader);
    LAST_STRING.unmarshall(reader);
    return this;
  }

}