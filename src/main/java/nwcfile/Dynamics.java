package nwcfile;

public class Dynamics extends SymbolAbstract {
  public enum DynamicsEnum {
    PIANISSIMO_POSSIBILE,
    PIANISSIMO,
    PIANO,
    MEZZO_PIANO,
    MEZZO_FORTE,
    FORTE,
    FORTISSIMO,
    FORTISSIMO_POSSIBILE;
  }

  private static final int MASK = Integer.parseInt("111", 2);

  private DynamicsEnum m_dynamicsEnum;

  public Dynamics() {
  }

  public void setDynamicsEnum(DynamicsEnum dynamicsEnum) {
    m_dynamicsEnum = dynamicsEnum;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Dynamics *****" + endl);
    builder.append("Value : " + m_dynamicsEnum + endl);
    builder.append("***** End dynamics *****" + endl);
    return builder.toString();
  }

  public Dynamics marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public Dynamics unmarshall(NwcFileReader reader)
    throws NwcFileException {
    reader.skip(4);
    setDynamicsEnum(DynamicsEnum.values()[reader.readByte() & MASK]);
    reader.skip(4);
    return this;
  }

}