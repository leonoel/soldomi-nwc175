package nwcfile;

import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class NwcStaff {
  private String m_name;
  private String m_group;
  private byte m_endBar;
  private boolean m_muted;
  private byte m_channel;
  private byte m_staffType;
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
  private List<NwcStaffSymbol> m_symbols = new ArrayList<NwcStaffSymbol>();

  private NwcStaff() {
    super();
  }

  public void setName(String name) {
    m_name = name;
  }

  public void setGroup(String group) {
    m_group = group;
  }

  public void setEndBar(byte endBar) {
    m_endBar = endBar;
  }

  public void setMuted(byte muted) {
    m_muted = (muted != 0);
  }

  public void setChannel(byte channel) {
    m_channel = channel;
  }
  
  public void setStaffType(byte staffType) {
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

  public void addSymbol(NwcStaffSymbol symbol) {
    m_symbols.add(symbol);
  }

  public void addEndingBar() {
  }

  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
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
    for (NwcStaffSymbol symbol : m_symbols) {
      builder.append(symbol.toString());
    }
    builder.append("***** End symbols *****" + endl);

    return builder.toString();
  }

  public static NwcStaff fromNwcFileReader(NwcFileReader reader) throws NwcFileException {
    NwcStaff staff = new NwcStaff();

    staff.setName(reader.getNextField());
    staff.setGroup(reader.getNextField());

    staff.setEndBar(reader.readByte());
    staff.setMuted(reader.readByte());
    reader.skip(1);
    staff.setChannel(reader.readByte());
    reader.skip(9);
    staff.setStaffType(reader.readByte());
    reader.skip(1);
    staff.setUpperSize(reader.readByte());
    reader.skip(1);
    staff.setLowerSize(reader.readByte());
    reader.skip(1);
    staff.setLineCount(reader.readByte());
    reader.skip(4);
    staff.setPartVolume(reader.readByte());
    reader.skip(1);
    staff.setStereoPan(reader.readByte());
    reader.skip(7);

    short symbolCount = reader.readShort(); 
    staff.setSymbolCount(symbolCount);

    reader.skip(1);

    for (int i = 1; i < symbolCount - 1; i++) {
      staff.addSymbol(NwcStaffSymbol.fromNwcFileReader(reader));
    }
    staff.addEndingBar();

    return staff;
  }

}