/**
 * 图标类,图标来源 IconFont.
 * @type {string}
 */
var thumb_up_icon = "icon-zan";
var follow_icon = "icon-shoucang";
var comment_icon = "icon-pinglun";
var un_complete = "此功能正在加班加点完成中...暂时无法使用,请见谅";

var peopel_center_option = {
    ARTICLE: "文章",
    FOLLOW_ARTICLE: "收藏",
    COMMENT : "评论",
    // FOLLOW  : "关注",
    // FANS    : "粉丝",
    PRIVATE: {
        //SETTING : "设置"
    }
};
var request_url = {
    ARTICLE : "/loadArticleList",
    FOLLOW_ARTICLE: "/listFollowArticle",
    COMMENT : "/listCommentCenter"
};