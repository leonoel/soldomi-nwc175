package nwcfile;

public class BarLine extends SymbolAbstract {
  public enum BarLineEnum {
    SINGLE,
    DOUBLE,
    SECTION_OPEN,
    SECTION_CLOSE,
    MASTER_REPEAT_OPEN,
    MASTER_REPEAT_CLOSE,
    LOCAL_REPEAT_OPEN,
    LOCAL_REPEAT_CLOSE;
  }

  private BarLineEnum m_type;

  public BarLine() {
    super();
  }

  public void setType(BarLineEnum type) {
    m_type = type;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Bar line *****" + endl);
    builder.append("Type : " + m_type + endl);
    builder.append("***** End bar line *****" + endl);
    return builder.toString();
  }

  @Override
  public BarLine marshall(NwcFileWriter writer)
    throws NwcFileException {
    // TODO
    return this;
  }

  @Override
  public BarLine unmarshall(NwcFileReader reader)
    throws NwcFileException {
    reader.skip(2);
    try {
      setType(BarLineEnum.values()[reader.readByte()]);
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new NwcFileException(e);
    }
    reader.skip(1);
    return this;
  }
  
}