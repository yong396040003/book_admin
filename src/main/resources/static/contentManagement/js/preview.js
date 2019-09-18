
var number;

function getNumber(number_){
    number = number_;
}

layui.use(['laypage', 'jquery', 'layer'], function () {
    var laypage = layui.laypage;
    var $ = layui.jquery;
    var layer = layui.layer;

    var catalogueList;

    var limit = 30;
    var curr = 1;
    $.ajax({
        url: 'selectCatalog.do?number=' + number + '&limit=' + limit + '&curr=' + curr,
        sync: true,
        dataType: 'json',
        method: 'GET',
        success: function (data) {
            laypage.render({
                elem: page
                , pre: '上一页'
                , next: '下一页'
                , first: '首页'
                , last: '尾页'
                , limit: limit
                , count: data.catalogueCount
                , theme: '#1e9fff'
                , layout: ['first', 'prev', 'page', 'next', 'last', 'count']
                , jump: function (obj, first) {
                    catalogueList = data.catalogueList;
                    var li_l = '';
                    var li_r = '';
                    for (var i = 0, j = (limit / 2); i < (limit / 2); i++, j++) {
                        var l = (obj.curr - 1) * 30 + i;
                        var r = (obj.curr - 1) * 30 + j;
                        if (obj.count <= l) {
                            break;
                        }

                        if (catalogueList[l] != null) {
                            if (i % 2 == 0) {
                                li_l += '       <li class="odd m"><a class="contents-tab"\n' + '                                   href="' + catalogueList[l].url + '" target="_blank"><span>' + (l + 1) + '</span>' + catalogueList[l].name + '</a></li>';
                            } else {
                                li_l += '       <li class="odd"><a class="contents-tab"\n' +
                                    '                                   href="' + catalogueList[l].url + '" target="_blank"><span>' + (l + 1) + '</span>' + catalogueList[l].name + '</a></li>';
                            }
                        } else {
                            if (i % 2 == 0) {
                                li_l += '       <li class="odd m"><a class="contents-tab error"\n' +
                                    '                                   href="javascript:void(0);" target="_blank"><span>' + (l + 1) + '</span>该章节异常，请联系管理员</a></li>';
                            } else {
                                li_l += '       <li class="odd"><a class="contents-tab error"\n' +
                                    '                                   href="javascript:void(0);" target="_blank"><span>' + (l + 1) + '</span>该章节异常，请联系管理员</a></li>';
                            }
                        }

                        if (obj.count > r) {
                            if (catalogueList[r] != null) {
                                if (i % 2 == 0) {
                                    li_r += '       <li class="odd m"><a class="contents-tab"\n' + '                                   href="' + catalogueList[r].url + '" target="_blank"><span>' + (r + 1) + '</span>' + catalogueList[r].name + '</a></li>';
                                } else {
                                    li_r += '       <li class="odd"><a class="contents-tab"\n' +
                                        '                                   href="' + catalogueList[r].url + '" target="_blank"><span>' + (r + 1) + '</span>' + catalogueList[r].name + '</a></li>';
                                }
                            } else {
                                if (i % 2 == 0) {
                                    li_r += '       <li class="odd m"><a class="contents-tab error"\n' +
                                        '                                   href="javascript:void(0);" target="_blank"><span>' + (r + 1) + '</span>该章节异常，请联系管理员</a></li>';
                                } else {
                                    li_r += '       <li class="odd"><a class="contents-tab error"\n' +
                                        '                                   href="javascript:void(0);" target="_blank"><span>' + (r + 1) + '</span>该章节异常，请联系管理员</a></li>';
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                    $('.contents-lst-left')[0].innerHTML = li_l;
                    $('.contents-lst-right')[0].innerHTML = li_r;
                }
            });
        },
        error: function () {
            layer.msg("哎呦，网络开小差了");
        }
    });

    $('.startRead').on('click', function () {
        window.open(catalogueList[0].url);
    });

    $('.collect').on('click', function () {
        layer.msg("收藏失败");
    });
});