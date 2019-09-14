package cn.bestsort.bbslite.enums;

/**
 * @ClassName CommentTypeEnum
 * @Description 用于判断用户回复的类型(对问题回复/对评论回复/都不是)
 * @Author bestsort
 * @Date 19-9-13 下午4:54
 * @Version 1.0
 */

public enum  CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Byte type;
    CommentTypeEnum(Integer type){
        this.type = type.byteValue();
    }

    public static boolean isExist(Byte type) {
        for(CommentTypeEnum commentTypeEnum:CommentTypeEnum.values()){
            if(commentTypeEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public Byte getType(){
        return this.type;
    }
}
