package cn.bestsort.bbslite.enums;

/**
 * @ClassName FollowEnum
 * @Description
 * @Author bestsort
 * @Date 19-9-29 下午2:52
 * @Version 1.0
 */
public enum FunctionItem implements MessageInterface {
    COMMENT(4,"comment"),
    TOPIC(3,"topic"),
    USER(2,"user"),
    QUESTION(1,"question");
    private String name;
    private Integer code;

    FunctionItem(Integer code, String name){
        this.name = name;
        this.code = code;
    }
    public static Integer getKeyByValue(String name)
    {
        for(FunctionItem i : FunctionItem.values()){
            if(i.name.equals(name)) {
                return i.code;
            }
        }
        return null;
    }
    public static String getValByKey(Integer code){
        for(FunctionItem i : FunctionItem.values()){
            if(i.getCode().intValue() == code) {
                return i.getName();
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
