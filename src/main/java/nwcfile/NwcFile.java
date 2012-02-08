package nwcfile;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.util.zip.Deflater;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.zip.Inflater;
import java.util.zip.DataFormatException;
import java.io.CharArrayReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;

public class NwcFile implements NwcItem {
  private static final String HEADER_NWZ = "[NWZ]";

  private String m_company;
  private String m_product;

  private int m_minorVersion;
  private int m_majorVersion;

  private String m_name1;
  private String m_name2;

  private String m_title;
  private String m_author;
  private String m_copyright1;
  private String m_copyright2;
  private String m_comment;

  private int m_measureStart;
  private String m_margins;

  private List<Font> m_fonts = new ArrayList<Font>();
  private List<Staff> m_staves = new ArrayList<Staff>();

  public NwcFile() {
  }

  public void setCompany(String company) {
    m_company = company;
  }

  public void setProduct(String product) {
    m_product = product;
  }

  public void setMinorVersion(int minorVersion) {
    m_minorVersion = minorVersion;
  }

  public void setMajorVersion(int majorVersion) {
    m_majorVersion = majorVersion;
  }

  public void setName1(String name1) {
    m_name1 = name1;
  }

  public void setName2(String name2) {
    m_name2 = name2;
  }

  public void setTitle(String title) {
    m_title = title;
  }

  public void setAuthor(String author) {
    m_author = author;
  }

  public void setCopyright1(String copyright1) {
    m_copyright1 = copyright1;
  }
  
  public void setCopyright2(String copyright2) {
    m_copyright2 = copyright2;
  }

  public void setComment(String comment) {
    m_comment = comment;
  }

  public void setMeasureStart(int measureStart) {
    m_measureStart = measureStart;
  }

  public void setMargins(String margins) {
    m_margins = margins;
  }
  
  public void addFont(Font font) {
    m_fonts.add(font);
  }

  public void addStaff(Staff staff) {
    m_staves.add(staff);
  }

  public static NwcFile fromInputStream(InputStream in) throws NwcFileException {
    int length;
    byte[] buffer;
    byte[] bytes;
    try {
      bytes = new byte[in.available()];
      in.read(bytes, 0, bytes.length);
    } catch (IOException e) {
      throw new NwcFileException(e);
    }
    if (HEADER_NWZ.equals(new String(bytes, 0, HEADER_NWZ.length()))) {
      buffer  = new byte[bytes.length * 5];
      Inflater inflater = new Inflater();
      inflater.setInput(bytes, HEADER_NWZ.length() + 1, bytes.length - HEADER_NWZ.length() - 1);
      try {
	length = inflater.inflate(buffer);
      } catch (DataFormatException e) {
	throw new NwcFileException(e);
      }
    } else {
      throw new NwcFileException("Bad header.");
    }

    try {
      FileOutputStream fos = new FileOutputStream("uncompressed.nwc");
      fos.write(buffer, 0, length);
      fos.close();
    } catch (IOException e) {
      System.out.println("Could not write uncompressed file.");
    }

    InputStream input = new ByteArrayInputStream(buffer, 0, length);
    NwcFileReader reader = new NwcFileReader(input);

    return new NwcFile().unmarshall(reader);
  }

  public NwcFile marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcFile unmarshall(NwcFileReader reader)
    throws NwcFileException {
    setCompany(reader.readString());
    setProduct(reader.readString());

    String version = reader.readString();
    setMinorVersion(version.getBytes()[0]);
    setMajorVersion(version.getBytes()[1]);

    setName1(reader.readString());
    setName2(reader.readString());

    reader.readString();

    setTitle(reader.readString());
    setAuthor(reader.readString());
    setCopyright1(reader.readString());
    setCopyright2(reader.readString());
    setComment(reader.readString());

    reader.readString();
    reader.readString();

    setMeasureStart(reader.readString().getBytes()[0]);
    setMargins(reader.readString());

    reader.readString();
    reader.readString();
    reader.readString();

    for (int i = 0; i < 12; i++) {
      addFont(new Font().unmarshall(reader));
    }

    reader.readString();

    byte staffCount = reader.readString().getBytes()[0];
    for (int i = 0; i < staffCount; i++) {
      addStaff(new Staff().unmarshall(reader));
    }

    return this;
  }

  @Override
  public String toString() {
    String endl = System.getProperty("line.separator");
    StringBuilder builder = new StringBuilder();
    builder.append("Company : " + m_company + endl);
    builder.append("Product : " + m_product + endl);
    builder.append("Version : " + m_majorVersion + "." + m_minorVersion + endl);
    builder.append("Name 1 : " + m_name1 + endl);
    builder.append("Name 2 : " + m_name2 + endl);
    builder.append("Title : " + m_title + endl);
    builder.append("Author : " + m_author + endl);
    builder.append("Copyright 1 : " + m_copyright1 + endl);
    builder.append("Copyright 2 : " + m_copyright2 + endl);
    builder.append("Comment : " + m_comment + endl);
    builder.append("Measure start : " + m_measureStart + endl);
    builder.append("Margins : " + m_margins + endl);
    builder.append(endl);
    builder.append("***** Fonts *****" + endl);
    int i = 1;
    for(Font font : m_fonts) {
      builder.append("Font " + i + " :" + endl); 
      builder.append(font.toString());
      i++;
    }
    builder.append("***** End fonts *****" + endl);

    builder.append("***** Staves *****" + endl);
    i = 1;
    for(Staff staff : m_staves) {
      builder.append(staff.toString());
      i++;
    }
    builder.append("***** End staves *****" + endl);

    return builder.toString();
  }

  public static void main (String[] args) {
    if (args.length != 1) {
      System.out.println("Usage : nwcfile <file.nwc>");
      System.exit(0);
    }
    String filePath = args[0];
    try {
      NwcFile nwcFile = fromInputStream(new FileInputStream(filePath));
      System.out.println("***** File info *****");
      System.out.println(nwcFile.toString());
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
    } catch (NwcFileException e) {
      System.out.println(e.toString());
    }
  }

}