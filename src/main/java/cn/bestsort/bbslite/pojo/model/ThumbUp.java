package cn.bestsort.bbslite.pojo.model;

public class ThumbUp {
    private Long id;

    private Long thumbUpTo;

    private Long thumbUpBy;

    private Byte type;

    private Long gmtCreate;

    private Long gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThumbUpTo() {
        return thumbUpTo;
    }

    public void setThumbUpTo(Long thumbUpTo) {
        this.thumbUpTo = thumbUpTo;
    }

    public Long getThumbUpBy() {
        return thumbUpBy;
    }

    public void setThumbUpBy(Long thumbUpBy) {
        this.thumbUpBy = thumbUpBy;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
}