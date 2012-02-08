package nwcfile;

public interface NwcItem {
  public NwcItem marshall(NwcFileWriter writer) throws NwcFileException;
  public NwcItem unmarshall(NwcFileReader reader) throws NwcFileException;
}