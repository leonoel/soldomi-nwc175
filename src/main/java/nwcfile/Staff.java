package nwcfile;

import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Staff implements NwcItem {
  public enum EndBar {
    SECTION_CLOSE,
    MASTER_REPEAT_CLOSE,
    SINGLE,
    DOUBLE,
    OPEN_HIDDEN;
  }

  public enum StaffType {
    STANDARD,
    UPPER_GRAND_STAFF,
    LOWER_GRAND_STAFF,
    ORCHESTRA;
  }

  private String m_name;
  private String m_group;
  private EndBar m_endBar;
  private boolean m_muted;
  private byte m_channel;
  private StaffType m_staffType;
  private byte m_upperSize;
  private byte m_lowerSize;
  private byte m_lineCount;
  private byte m_layer;
  private byte m_partVolume;
  private byte m_stereoPan;
  private int m_lyrics;
  private int m_lyricsCount;
  private byte m_color;
  private int m_symbolCount;
  private List<SymbolContainer> m_symbols = new ArrayList<SymbolContainer>();

  public Staff() {
  }

  public String getName() {
    return m_name;
  }

  public void setName(String name) {
    m_name = name;
  }

  public List<SymbolContainer> getSymbols() {
    return m_symbols;
  }

  public void setGroup(String group) {
    m_group = group;
  }

  public void setEndBar(EndBar endBar) {
    m_endBar = endBar;
  }

  public void setMuted(boolean muted) {
    m_muted = muted;
  }

  public void setChannel(byte channel) {
    m_channel = channel;
  }
  
  public void setStaffType(StaffType staffType) {
    m_staffType = staffType;
  }

  public void setUpperSize(byte upperSize) {
    m_upperSize = upperSize;
  }

  public void setLowerSize(byte lowerSize) {
    m_lowerSize = lowerSize;
  }

  public void setLineCount(byte lineCount) {
    m_lineCount = lineCount;
  }

  public void setLayer(byte layer) {
    m_layer = layer;
  }

  public void setPartVolume(byte partVolume) {
    m_partVolume = partVolume;
  }

  public void setStereoPan(byte stereoPan) {
    m_stereoPan = stereoPan;
  }

  public void setColor(byte color) {
    m_color = color;
  }

  public void setSymbolCount(short symbolCount) {
    m_symbolCount = symbolCount;
  }

  public void addSymbol(SymbolContainer symbol) {
    m_symbols.add(symbol);
  }

  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("***** Staff *****" + endl);
    builder.append("Name : " + m_name + endl);
    builder.append("Group : " + m_group + endl);
    builder.append("End bar : " + m_endBar + endl);
    builder.append("Muted : " + m_muted + endl);
    builder.append("Channel : " + m_channel + endl);
    builder.append("Staff type : " + m_staffType + endl);
    builder.append("Upper size : " + m_upperSize + endl);
    builder.append("Lower size : " + m_lowerSize + endl);
    builder.append("Line count : " + m_lineCount + endl);
    builder.append("Layer : " + m_layer + endl);
    builder.append("Part volume : " + m_partVolume + endl);
    builder.append("Stereo pan : " + m_stereoPan + endl);
    builder.append("Lyrics count : " + m_lyricsCount + endl);
    builder.append("Color : " + m_color + endl);
    builder.append("Symbol count : " + m_symbolCount + endl);

    builder.append("***** Symbols *****" + endl);
    for (SymbolContainer symbol : m_symbols) {
      builder.append(symbol.toString());
    }
    builder.append("***** End Symbols *****" + endl);
    builder.append("***** End Staff *****" + endl);

    return builder.toString();
  }

  public Staff marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public Staff unmarshall(NwcFileReader reader)
    throws NwcFileException {
    setName(reader.readString());
    setGroup(reader.readString());

    try {
      setEndBar(EndBar.values()[NwcUtils.subByte(reader.readByte(), 0, 3)]);
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new NwcFileException(e);
    }

    setMuted(NwcUtils.subByte(reader.readByte(), 0, 1) != 0);

    reader.skip(1);
    setChannel(reader.readByte());
    reader.skip(9);

    try {
      setStaffType(StaffType.values()[NwcUtils.subByte(reader.readByte(), 0, 2)]);
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new NwcFileException(e);
    }

    reader.skip(1);
    setUpperSize(reader.readByte());
    reader.skip(1);
    setLowerSize(reader.readByte());
    reader.skip(1);
    setLineCount(reader.readByte());
    reader.skip(4);
    setPartVolume(reader.readByte());
    reader.skip(1);
    setStereoPan(reader.readByte());
    reader.skip(8);

    short symbolCount = reader.readShort(); 
    setSymbolCount(symbolCount);

    for (int i = 1; i < symbolCount - 1; i++) {
      addSymbol(new SymbolContainer().unmarshall(reader));
    }

    return this;
  }

}