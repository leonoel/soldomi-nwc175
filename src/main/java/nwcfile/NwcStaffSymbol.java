package nwcfile;

import java.util.HashMap;
import java.util.Map;

public class NwcStaffSymbol {
  public enum NwcStaffSymbolEnum {
    CLEF(0x00) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return NwcClef.fromNwcFileReader(reader);
      }
    },
    KEY_SIGNATURE(0x01) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    BAR_LINE(0x02) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    REPEAT(0x03) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    INSTRUMENT_PATCH(0x04) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    TIME_SIGNATURE(0x05) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    TEMPO(0x06) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    DYNAMICS(0x07) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    NOTE(0x08) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    REST(0x09) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    CHORD_STARTING_WITH_REST(0x12) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    CHORD(0x0A) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    PEDAL(0x0B) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    MPC(0x0D) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    FERMATA(0x0E) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    DYNAMIC_VARIANCE(0x0F) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    PERFORMANCE_STYLE(0x10) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    },
    TEXT(0x11) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    };

    private static Map<Integer, NwcStaffSymbolEnum> FROM_ID = initReverseEnum();

    private static Map<Integer, NwcStaffSymbolEnum> initReverseEnum () {
      Map<Integer, NwcStaffSymbolEnum> map = new HashMap<Integer, NwcStaffSymbolEnum>();
      for(NwcStaffSymbolEnum item : values()) {
	map.put(item.ID, item);
      }
      return map;
    }

    public static NwcStaffSymbolEnum fromId(int id) throws NwcFileException {
      if (!FROM_ID.containsKey(id)) {
	throw new NwcFileException("Unknown staff symbol id");
      }
      return FROM_ID.get(id);
    }

    public int ID;

    NwcStaffSymbolEnum(int id) {
      this.ID = id;
    }

    public abstract NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
      throws NwcFileException;
  }

  public static NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
    throws NwcFileException {
    byte symbolId = reader.readByte();
    return NwcStaffSymbolEnum.fromId(symbolId).fromNwcFileReader(reader);
  }

  private NwcStaffSymbolEnum m_type;

  public NwcStaffSymbol(NwcStaffSymbolEnum type) {
    super();
    m_type = type;
  }

}