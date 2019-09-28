package cn.bestsort.bbslite.bean.model;

public class Follow {
    private Long id;

    private Long followBy;

    private Long followTo;

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

    public Long getFollowBy() {
        return followBy;
    }

    public void setFollowBy(Long followBy) {
        this.followBy = followBy;
    }

    public Long getFollowTo() {
        return followTo;
    }

    public void setFollowTo(Long followTo) {
        this.followTo = followTo;
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
}