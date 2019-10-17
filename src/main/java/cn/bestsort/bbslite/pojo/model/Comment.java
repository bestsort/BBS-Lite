package cn.bestsort.bbslite.pojo.model;

public class Comment {
    private Long id;

    private Long commentTo;

    private Long commentBy;

    private Long gmtModified;

    private Long gmtCreate;

    private String content;

    private Long likeCount;

    private Long pid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentTo() {
        return commentTo;
    }

    public void setCommentTo(Long commentTo) {
        this.commentTo = commentTo;
    }

    public Long getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(Long commentBy) {
        this.commentBy = commentBy;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", commentTo=").append(commentTo);
        sb.append(", commentBy=").append(commentBy);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", content=").append(content);
        sb.append(", likeCount=").append(likeCount);
        sb.append(", pid=").append(pid);
        sb.append("]");
        return sb.toString();
    }
}