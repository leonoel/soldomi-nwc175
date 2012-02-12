package nwcfile;

public class Mpc extends Symbol {

  public Mpc marshall(NwcFileWriter writer) 
    throws NwcFileException {
    // TODO
    return this;
  }

  public Mpc unmarshall(NwcFileReader reader)
    throws NwcFileException {
    reader.skip(36);
    return this;
  }


}