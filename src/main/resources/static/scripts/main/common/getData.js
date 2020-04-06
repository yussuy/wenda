
$(function () {
    /*初始化*/
    var userId = $("#userId").val();
    var counter = 0;    // 计数器
    var pageStart = 0;  // offset
    var pageSize = 10;   // limit

    /*首次加载*/
    getData(userId, pageStart, pageSize);

    /*监听加载更多*/
    $(document).on('click', '#zh-load-more', function(){
        debugger
        counter++;
        pageStart = counter * pageSize;

        getData(userId, pageStart, pageSize);
    });

})

function getData(userId, offset, limit) {
    debugger;
    $.ajax({
        type: 'POST',
        url: '/getQuestions',
        data:{
            userId: userId,
            offset: offset,
            limit: limit
        },
        dataType: 'json',
        success: function (vos) {

            var count = vos.length;

            var result = '';
            for(var i= 0; i< count; i++){
                result += '<div class="feed-item folding feed-item-hook feed-item-2\n' +
                    '                " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">\n' +
                    '                <meta itemprop="ZReactor" data-id="389034" data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">\n' +
                    '                    <div class="feed-item-inner">\n' +
                    '                    <div class="avatar">\n' +
                    '                    <a title="' + vos[i].maps.user.name + '" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="/user/' + vos[i].maps.user.id + '">\n' +
                    '                    <img src="' + vos[i].maps.user.headUrl + '" class="zm-item-img-avatar"></a>\n' +
                    '                    </div>\n' +
                    '                    <div class="feed-main">\n' +
                    '                    <div class="feed-content" data-za-module="AnswerItem">\n' +
                    '                    <meta itemprop="answer-id" content="389034">\n' +
                    '                    <meta itemprop="answer-url-token" content="13174385">\n' +
                    '                    <h2 class="feed-title">\n' +
                    '                    <a class="question_link" target="_blank" href="/question/'+ vos[i].maps.question.id + '">' + vos[i].maps.question.title + '</a></h2>\n' +
                    '                <div class="feed-question-detail-item">\n' +
                    '                    <div class="question-description-plain zm-editable-content"></div>\n' +
                    '                    </div>\n' +
                    '                    <div class="expandable entry-body">\n' +
                    '                    <div class="zm-item-vote">\n' +
                    '                    <a class="zm-item-vote-count js-expand js-vote-count" href="javascript:;" data-bind-votecount="">' + vos[i].maps.followCount + '</a></div>\n' +
                    '                <div class="zm-item-answer-author-info">\n' +
                    '                    <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${vo.maps.user.id!}">' + vos[i].maps.user.name + '</a>\n' +
                    '                                            ，' + dateFormat(vos[i].maps.question.createdDate, "yyyy年MM月dd日 hh:mm:ss") + '</div>\n' +
                    '                <div class="zm-item-vote-info" data-votecount="4168" data-za-module="VoteInfo">\n' +
                    '                    <span class="voters text">\n' +
                    '                    <a href="#" class="more text">\n' +
                    '                    <span class="js-voteCount">4168</span>&nbsp;人赞同</a></span>\n' +
                    '                </div>\n' +
                    '                <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="123114" data-action="/answer/content" data-author-name="李淼" data-entry-url="/question/19857995/answer/13174385">\n' +
                    '                    <div class="zh-summary summary clearfix">'+ vos[i].maps.question.content + '</div>\n' +
                    '                    </div>\n' +
                    '                    </div>\n' +
                    '                    <div class="feed-meta">\n' +
                    '                    <div class="zm-item-meta answer-actions clearfix js-contentActions">\n' +
                    '                    <div class="zm-meta-panel">\n' +
                    '                    <a data-follow="q:link" class="follow-link zg-follow meta-item" href="javascript:;" id="sfb-123114">\n' +
                    '                    <i class="z-icon-follow"></i>关注问题</a>\n' +
                    '                <a href="#" name="addcomment" class="meta-item toggle-comment js-toggleCommentBox">\n' +
                    '                    <i class="z-icon-comment"></i>'+ vos[i].maps.question.commentCount + ' 条评论</a>\n' +
                    '                <button class="meta-item item-collapse js-collapse">\n' +
                    '                    <i class="z-icon-fold"></i>收起</button>\n' +
                    '                </div>\n' +
                    '                </div>\n' +
                    '\n' +
                    '                </div>\n' +
                    '                </div>\n' +
                    '                </div>\n' +
                    '                </div>\n' +
                    '                </div>';
            }

            $('#js-home-feed-list').append(result);

            if (count < 10) {
                $("#zh-load-more").hide();
            } else {
                $("#zh-load-more").show();
            }

        },
        error: function (xhr, type) {
            alert("error!");
        }
    })
}


