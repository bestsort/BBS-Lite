package cn.bestsort.bbslite.cache;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * TODO
 *
 * @author bestsort
 * @version 1.0
 * @date 12/21/19 10:10 AM
 */
@Data
public class CacheModel {
    String key;
    String value;
    long time;
    String strategy;
}
