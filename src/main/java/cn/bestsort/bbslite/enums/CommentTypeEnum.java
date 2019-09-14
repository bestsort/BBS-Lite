package cn.bestsort.bbslite.enums;

import cn.bestsort.bbslite.model.Comment;

/**
 * @ClassName CommentTypeEnum
 * @Description TODO
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
