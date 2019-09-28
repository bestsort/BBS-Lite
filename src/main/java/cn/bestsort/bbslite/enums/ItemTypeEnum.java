package cn.bestsort.bbslite.enums;

/**
 * @ClassName ItemTypeEnum
 * @Description TODO
 * @Author bestsort
 * @Date 19-9-28 下午6:35
 * @Version 1.0
 */

public enum ItemTypeEnum implements ItemTypeInterface{

    COMMENT(2,"comment"),
    QUESTION(1,"question");


    private String name;
    private Integer code;
    @Override
    public String getName(){
        return name;
    }
    @Override
    public Integer getCode(){
        return code;
    }
    ItemTypeEnum(Integer code, String message){
        this.name =message;
        this.code = code;
    }
}
