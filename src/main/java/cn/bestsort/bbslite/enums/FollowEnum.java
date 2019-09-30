package cn.bestsort.bbslite.enums;

/**
 * @ClassName FollowEnum
 * @Description TODO
 * @Author bestsort
 * @Date 19-9-29 下午2:52
 * @Version 1.0
 */
public enum FollowEnum implements MessageInterface {

    TOPIC(3,"topic"),
    USER(2,"user"),
    QUESTION(1,"question");
    private String name;
    private Integer code;

    FollowEnum(Integer code, String name){
        this.name = name;
        this.code = code;
    }
    public static Integer getKeyByValue(String name)
    {
        for(FollowEnum i : FollowEnum.values()){
            if(i.name.equals(name)) {
                return i.code;
            }
        }
        return null;
    }
    public static String getValByKey(Integer code){
        for(FollowEnum i : FollowEnum.values()){
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
