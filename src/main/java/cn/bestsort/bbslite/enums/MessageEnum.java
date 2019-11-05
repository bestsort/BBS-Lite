package cn.bestsort.bbslite.enums;


/**
 * @author bestsort
 */

public enum MessageEnum{
    /**
     * COMMENT: 评论
     * TOPIC: 话题
     * USER: 用户
     * ARTICLE: 问题
     */
    ALL,
    THUMB_UP,
    FOLLOW_USER,
    FOLLOW_ARTICLE,
    COMMENT;
    public static MessageEnum getItem(String str){
        for(MessageEnum i:MessageEnum.values()){
            if (MessageEnum.valueOf(str) == i){
                return i;
            }
        }
        return null;
    }
    public static MessageEnum getByCode(Byte code){
        byte byt = 0;
        for(MessageEnum i:MessageEnum.values()){
            if(byt == code) {
                return i;
            }
            byt++;
        }
        return null;
    }
    public static Byte getCode(MessageEnum item){
        byte byt = 0;
        for(MessageEnum i:MessageEnum.values()){
            if(i == item) {
                return byt;
            }
            byt++;
        }
        return null;
    }
}
