package nwcfile;

import java.util.HashMap;
import java.util.Map;

public class NwcClef extends NwcStaffSymbol {
  public enum Key {
    TREBLE(0x00),
    BASS(0x01),
    ALTO(0x02),
    TENOR(0x03);

    private static Map<Integer, Key> FROM_ID = initReverseEnum();

    private static Map<Integer, Key> initReverseEnum () {
      Map<Integer, Key> map = new HashMap<Integer, Key>();
      for(Key item : values()) {
	map.put(item.ID, item);
      }
      return map;
    }

    public static Key fromId(int id) throws NwcFileException {
      if (!FROM_ID.containsKey(id)) {
	throw new NwcFileException("Unknown Key id");
      }
      return FROM_ID.get(id);
    }

    public int ID;

    Key(int id) {
      this.ID = id;
    }

  }

  public enum Octave {
     NONE(0x00),
     UP(0x01),
     DOWN(0x02);

     private static Map<Integer, Octave> FROM_ID = initReverseEnum();

     private static Map<Integer, Octave> initReverseEnum () {
       Map<Integer, Octave> map = new HashMap<Integer, Octave>();
       for(Octave item : values()) {
   	map.put(item.ID, item);
       }
       return map;
     }

     public static Octave fromId(int id) throws NwcFileException {
       if (!FROM_ID.containsKey(id)) {
   	throw new NwcFileException("Unknown Octave id");
       }
       return FROM_ID.get(id);
     }

     public int ID;

     Octave(int id) {
       this.ID = id;
     }
   }

   private Key m_key;
   private Octave m_octave;

  public NwcClef() {
    super(NwcStaffSymbolEnum.CLEF);
  }

  public void setKey(byte key) throws NwcFileException {
    m_key = Key.fromId(key);
  }

  public void setOctave(byte octave) throws NwcFileException {
    m_octave = Octave.fromId(octave);
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Clef *****" + endl);
    builder.append("Key : " + m_key + endl);
    builder.append("Octave : " + m_octave + endl);
    builder.append("***** End clef *****" + endl);
    return builder.toString();
  }

  public static NwcClef fromNwcFileReader(NwcFileReader reader)
    throws NwcFileException {
    NwcClef clef = new NwcClef();
    reader.readByte();
    reader.readByte();
    clef.setKey(reader.readByte());
    reader.readByte();
    clef.setOctave(reader.readByte());
    reader.readByte();
    return clef;
  }
}