package cn.bestsort.bbslite.pojo.model;

public class ThumbUp {
    private Long id;

    private Long thumbUpTo;

    private Long thumbUpBy;

    private Byte type;

    private Long gmtCreate;

    private Long gmtModified;

    private Byte status;

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", thumbUpTo=").append(thumbUpTo);
        sb.append(", thumbUpBy=").append(thumbUpBy);
        sb.append(", type=").append(type);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}