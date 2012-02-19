package nwcfile;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.List;
import java.util.ArrayList;


public class NwcFile implements NwcItem {
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

  public String getTitle() {
    return m_title;
  }

  public List<Staff> getStaves() {
    return m_staves;
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

  public NwcFile marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public NwcFile unmarshall(NwcFileReader reader)
    throws NwcFileException {
    setCompany(reader.readString());
    reader.skip(2);
    setProduct(reader.readString());

    setMinorVersion(reader.readByte());
    setMajorVersion(reader.readByte());

    reader.skip(4);

    setName1(reader.readString());
    setName2(reader.readString());

    reader.skip(10);

    setTitle(reader.readString());
    setAuthor(reader.readString());
    setCopyright1(reader.readString());
    setCopyright2(reader.readString());
    setComment(reader.readString());

    reader.readString();
    reader.readString();

    reader.skip(2);
    setMeasureStart(reader.readByte());
    reader.skip(1);
    setMargins(reader.readString());

    reader.skip(38);

    for (int i = 0; i < 12; i++) {
      addFont(new Font().unmarshall(reader));
    }

    reader.skip(4);
    byte staffCount = reader.readByte();
    reader.skip(1);

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
    for(Font font : m_fonts) {
      builder.append(font.toString());
    }
    builder.append("***** End fonts *****" + endl);

    builder.append("***** Staves *****" + endl);
    for(Staff staff : m_staves) {
      builder.append(staff.toString());
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
    NwcFile nwcFile = new NwcFile();
    try {
      NwcFileReader reader = new NwcFileReader(new FileInputStream(filePath));
      nwcFile.unmarshall(reader);
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
    } catch (NwcFileException e) {
      e.printStackTrace();
    }
    System.out.println("***** File info *****");
    System.out.println(nwcFile.toString());
  }

}