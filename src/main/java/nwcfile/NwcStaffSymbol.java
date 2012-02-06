package nwcfile;

import java.util.HashMap;
import java.util.Map;

public class NwcStaffSymbol {
  public enum Symbol implements NwcEnum {
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
    },
    CHORD_STARTING_WITH_REST(0x12) {
      public NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
	throws NwcFileException {
	return new NwcStaffSymbol(this);
      }
    };

    private byte m_code;

    Symbol(int code) {
      m_code = (byte) code;
    }

    @Override
    public byte getCode() {
      return m_code;
    }

    public abstract NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
      throws NwcFileException;
  }

  private static final NwcEnumReverseMap<Symbol> SYMBOL_REVERSE_MAP = new NwcEnumReverseMap<Symbol>(Symbol.class);

  public static NwcStaffSymbol fromNwcFileReader(NwcFileReader reader)
    throws NwcFileException {
    byte code = reader.readByte();
    return SYMBOL_REVERSE_MAP.get(code).fromNwcFileReader(reader);
  }

  private Symbol m_type;

  public NwcStaffSymbol(Symbol type) {
    super();
    m_type = type;
  }

}