package nwcfile;

import java.util.HashMap;
import java.util.Map;

public class NwcEnumReverseMap<V extends Enum<V> & NwcEnum> {
  private Map<Byte, V> m_map = new HashMap<Byte, V>();

  public NwcEnumReverseMap(Class<V> type) {
    for (V v : type.getEnumConstants()) {
      m_map.put(v.getCode(), v);
    }
  }

  public V get(byte code) {
    return m_map.get(code);
  }

  public boolean contains(byte code) {
    return m_map.containsKey(code);
  }
}