package cn.bestsort.bbslite.util;

import org.springframework.beans.factory.annotation.Value;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MurmursHash {
    @Value("bbs.encryption.seed:0x3f2a3c7e")
    private static int seed;
    @Value("bbs.encryption.salt:xzcawx")
    private static String salt;
    public static String hashUnsignedWithSalt(String key){return hash((key+salt).getBytes());}
    public static String hashUnsigned(String key) {
        return hash(key.getBytes());
    }
    public static String hashUnsigned(String key,int seed) {
        return hash(key.getBytes(),seed);
    }
    private static String hash(byte[] key,int seed){
        ByteBuffer buf = ByteBuffer.wrap(key);

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);
        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();
            k *= m;
            k ^= k >>> r;
            k *= m;
            h ^= k;
            h *= m;
        }
        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }
        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;
        buf.order(byteOrder);
        return String.valueOf(h);
    }
    private static String hash(byte[] key) {
        return hash(key,seed);
    }
}

