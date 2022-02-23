package org.open.job.common.interfaces;

/**
 * @author: 李俊平
 * @Date: 2020-11-03 11:08
 */
public interface CommonEnumConverter<E extends IntValuable> {

  default Integer enumToInt(E e) {
    if (e == null) {
      return null;
    }
    return e.getValue();
  }

}
