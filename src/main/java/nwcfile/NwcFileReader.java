package nwcfile;

import java.io.InputStream;
import java.io.IOException;
import java.io.DataInputStream;

public class NwcFileReader {
  private DataInputStream m_input;

  public NwcFileReader(InputStream input) {
    m_input = new DataInputStream(input);
  }

  public String readString() throws NwcFileException {
    StringBuffer buffer = new StringBuffer();
    int r;
    try {
      while((r = m_input.read()) == 0);
      do {
	buffer.append((char) r);
      } while((r = m_input.read()) != 0);
      return buffer.toString();
    } catch (IOException e) {
      throw new NwcFileException(e);
    }
  }
  
  public byte readByte() throws NwcFileException {
    try {
      return m_input.readByte();
    } catch (IOException e) {
      throw new NwcFileException(e);
    }
  }

  public short readShort() throws NwcFileException {
    try {
      return m_input.readShort();
    } catch (IOException e) {
      throw new NwcFileException(e);
    }
  }

  public void skip(int byteCount) throws NwcFileException {
    try {
      m_input.skipBytes(byteCount);
    } catch (IOException e) {
      throw new NwcFileException(e);
    }
  }
}