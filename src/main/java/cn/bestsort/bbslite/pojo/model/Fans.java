package cn.bestsort.bbslite.pojo.model;

public class Fans {
    private Long id;

    private Long fansById;

    private Long fansToId;

    private Byte status;

    private Long gmtCreated;

    private Long gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFansById() {
        return fansById;
    }

    public void setFansById(Long fansById) {
        this.fansById = fansById;
    }

    public Long getFansToId() {
        return fansToId;
    }

    public void setFansToId(Long fansToId) {
        this.fansToId = fansToId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Long gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fansById=").append(fansById);
        sb.append(", fansToId=").append(fansToId);
        sb.append(", status=").append(status);
        sb.append(", gmtCreated=").append(gmtCreated);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append("]");
        return sb.toString();
    }
}