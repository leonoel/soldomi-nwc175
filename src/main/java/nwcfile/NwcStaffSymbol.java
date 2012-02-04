package nwcfile;

import java.util.HashMap;
import java.util.Map;

public class NwcStaffSymbol {
  public enum NwcStaffSymbolEnum {
    CLEF(0x00),
    KEY_SIGNATURE(0x01),
    BAR_LINE(0x02),
    REPEAT(0x03),
    INSTRUMENT_PATCH(0x04),
    TIME_SIGNATURE(0x05),
    TEMPO(0x06),
    DYNAMICS(0x07),
    NOTE(0x08),
    REST(0x09),
    CHORD_STARTING_WITH_REST(0x12),
    CHORD(0x0A),
    PEDAL(0x0B),
    MPC(0x0D),
    FERMATA(0x0E),
    DYNAMIC_VARIANCE(0x0F),
    PERFORMANCE_STYLE(0x10),
    TEXT(0x11);

    private static Map<Integer, NwcStaffSymbolEnum> FROM_ID = initReverseEnum();

    private static Map<Integer, NwcStaffSymbolEnum> initReverseEnum () {
      Map<Integer, NwcStaffSymbolEnum> map = new HashMap<Integer, NwcStaffSymbolEnum>();
      for(NwcStaffSymbolEnum item : values()) {
	map.put(item.m_id, item);
      }
      return map;
    }

    public static NwcStaffSymbolEnum fromId(int id) throws NwcFileException {
      if (!FROM_ID.containsKey(id)) {
	throw new NwcFileException("Unknown staff symbol id");
      }
      return FROM_ID.get(id);
    }

    private int m_id;

    NwcStaffSymbolEnum(int id) {
      this.m_id = id;
    }

    public int getId() {
      return this.m_id;
    }

    public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
      throws NwcFileException {
      return new NwcStaffSymbol();
    }
  }

  public static NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
    throws NwcFileException {
    byte symbolId = reader.readByte();
    return NwcStaffSymbolEnum.fromId(symbolId).fromNwcFileReader(reader);
  }

  private NwcStaffSymbol() {
    super();
  }
}