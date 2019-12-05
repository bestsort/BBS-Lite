package cn.bestsort.bbslite.enums;

/**
 * @classname FollowEnum
 * TODO
 * @author bestsort
 * @date 19-9-29 下午2:52
 * @version 1.0
 */

public enum PeopleCenterEnum{
    /**
     * 个人中心相关字段
     */
    ARTICLE,
    FANS,
    FOLLOW,
    FOLLOW_ARTICLE,
    COMMENT,
    SETTING;
    public static PeopleCenterEnum getItem(String str){
        for(PeopleCenterEnum i:PeopleCenterEnum.values()){
            if (PeopleCenterEnum.valueOf(str) == i){
                return i;
            }
        }
        return null;
    }
    public static PeopleCenterEnum getByCode(Byte code){
        byte byt = 0;
        for(PeopleCenterEnum i:PeopleCenterEnum.values()){
            if(byt == code) {
                return i;
            }
            byt++;
        }
        return null;
    }
    public static Byte getCode(PeopleCenterEnum item){
        byte byt = 0;
        for(PeopleCenterEnum i:PeopleCenterEnum.values()){
            if(i == item) {
                return byt;
            }
            byt++;
        }
        return null;
    }
}
