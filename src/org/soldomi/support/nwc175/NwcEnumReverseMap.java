package org.soldomi.support.nwc175;

import java.util.HashMap;
import java.util.Map;

public class NwcEnumReverseMap<V extends Enum<V> & NwcEnum> {
  private Map<Integer, V> m_map = new HashMap<Integer, V>();

  public NwcEnumReverseMap(Class<V> type) {
    for (V v : type.getEnumConstants()) {
      m_map.put(v.getCode(), v);
    }
  }

  public V get(int code) {
    return m_map.get(code);
  }

  public boolean contains(int code) {
    return m_map.containsKey(code);
  }
}
