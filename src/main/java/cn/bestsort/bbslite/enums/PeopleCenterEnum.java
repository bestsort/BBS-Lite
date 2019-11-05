package cn.bestsort.bbslite.enums;

/**
 * @ClassName FollowEnum
 * @Description
 * @Author bestsort
 * @Date 19-9-29 下午2:52
 * @Version 1.0
 */

public enum PeopleCenterEnum{
    /**
     * 个人中心相关字段
     */
    ARTICLE,
    FANS,
    FOLLOW,
    FOLLOW_ARTICLE,
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
