package nwcfile;

public abstract class SymbolAbstract implements NwcItem {
  public abstract  SymbolAbstract marshall(NwcFileWriter writer)
    throws NwcFileException;
  public abstract  SymbolAbstract unmarshall(NwcFileReader reader)
    throws NwcFileException;
}