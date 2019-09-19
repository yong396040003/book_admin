var url; //地址
var method; //请求方式
var category; //小说类型
var sort; //排序
var status;//类型(完本)

var getParam = function (url_, method_, category_, sort_, status_) {
    url = url_;
    method = method_;
    category = category_;
    sort = sort_;
    status = status_;
};

layui.use(['table', 'jquery', 'layer', 'laydate', 'upload', 'form'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var form = layui.form;

    var index = layer.msg('加载中，请稍候', {icon: 16, time: false, shade: 0.8});

    //日期
    laydate.render({
        elem: '#date'
    });

    //渲染表格
    var tableIns = table.render({
        elem: '#book'
        , page: true
        , toolbar: '#toolbar'
        , height: "full-30"
        , method: method
        , url: url
        , where: {
            category: category,
            sort: sort,
            status: status
        }
        , limit: 10
        , limits: [10, 20, 50, 100]
        , text: {
            none: '无书籍数据，请联系管理员添加书籍' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        },
        cols: [
            [
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', width: 80, align: 'center', fixed: true}
                , {field: 'number', title: '书号', width: 80, align: 'center'}
                , {
                field: 'img', title: '封面', templet: '#img', width: 125, align: 'center', templet: function (d) {
                    return '<img src=\"' + d.img + '\" alt=\"加载失败\">';
                }
            }
                , {field: 'category', minWidth: 80, title: '小说类型', align: 'center', hide: true}
                , {field: 'name', minWidth: 80, title: '书名', align: 'center'}
                , {field: 'author', minWidth: 80, title: '作者', align: 'center'}
                , {field: 'status', minWidth: 80, title: '状态', align: 'center'}
                , {field: 'collection', minWidth: 80, title: '收藏', align: 'center', hide: 'true'}
                , {
                field: 'wordNumber', minWidth: 80, title: '字数', align: 'center', templet: function (d) {
                    var num = d.wordNumber.substring(0, d.wordNumber.length - 1);
                    if (num > 10000) {
                        var n = parseInt(num / 10000);
                        return '<span style="color: #c00;">' + n + "万字" + '</span>'
                    } else {
                        return '<span>' + num + '字' + '</span>'
                    }
                }
            }
                , {field: 'data', minWidth: 160, title: '更新时间', align: 'center',hide:'true'}
                , {
                field: 'data', minWidth: 160, title: '添加时间', align: 'center', templet: function (d) {
                    if (d.date == null) {
                        return '无';
                    } else {
                        return d.date;
                    }
                }
            }
                , {field: 'totalHits', minWidth: 80, title: '总点击数', align: 'center', hide: 'true'}
                , {field: 'monthlyClicks', minWidth: 80, title: '月点击数', align: 'center', hide: 'true'}
                , {field: 'weeklyClicks', minWidth: 80, title: '周点击数', align: 'center', hide: 'true'}
                , {field: 'totalRecommendedNumber', minWidth: 80, title: '总推荐数', align: 'center', hide: 'true'}
                , {field: 'monthlyRecommendedNumber', minWidth: 80, title: '月推荐数', align: 'center', hide: 'true'}
                , {field: 'weekRecommendedNumber', minWidth: 80, title: '周推荐数', align: 'center', hide: 'true'}
                , {field: 'synopsis', minWidth: 80, title: '简介', align: 'center', hide: 'true'}
                , {fixed: 'right', title: '操作', align: 'center', width: 160, toolbar: '#bar'}
            ]
        ],
        done: function (res, curr, count) {
            layer.close(index);
        }
    });

    //监听头工具栏事件
    table.on('toolbar(Book)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'search':
                search();
                break;
            case 'add':
                addNews('add');
                break;
            case 'batchAdd':
                batchAdd();
                break;
            case 'batchExport':
                if (data.length === 0) {
                    batchExport();
                } else {
                    batchCheckExport(data);
                }
                break;
            case 'batchDel':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    var datas = '';
                    for (var i = 0; i < data.length; i++) {
                        datas += "data[]=" + data[i].number + "&";
                    }
                    datas.substring(0, datas.length - 1);
                    layer.msg('<br>是否确认删除<br>' + data.length + '条数据', {
                        time: 20000, //20s后自动关闭
                        btn: ['狠心删除', '考虑考虑'],
                        btn1: function (index) {
                            $.ajax({
                                type: 'POST',
                                url: 'bathDeleteBookById.do',
                                data: datas,
                                dataType: 'json',
                                success: function (res) {
                                    if (res.code > 0) {
                                        layer.msg(res.msg);
                                        tableIns.reload();
                                    } else {
                                        layer.msg(res.msg);
                                    }
                                }
                            });
                            layer.close(index);
                        },
                        btn2: function (index) {
                            layer.close(index);
                        }
                    });
                }
                break;
            case 'refresh':
                tableIns.reload();
                break;
        }
    });

    //监听工具条
    table.on('tool(Book)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        switch (layEvent) {
            case 'del':
                layer.msg('<br>是否确认删除<br>' + data.name + '', {
                    time: 20000, //20s后自动关闭
                    btn: ['狠心删除', '考虑考虑'],
                    btn1: function (index) {
                        $.ajax({
                            type: 'POST',
                            url: 'deleteBookById.do',
                            data: 'number=' + data.number,
                            dataType: 'json',
                            success: function (res) {
                                if (res.code > 0) {
                                    obj.del();
                                    layer.msg(res.msg);
                                    tableIns.reload();
                                } else {
                                    layer.msg(res.msg);
                                }
                            }
                        });
                        layer.close(index);
                    },
                    btn2: function (index) {
                        layer.close(index);
                    }
                });
                break;
            case 'edit':
                addNews('edit', data);
                break;
            case 'preview':
                preview(data);
                break;
        }
    });

    //添加 修改
    function addNews(edit, data) {
        if (edit == 'edit') {
            var index = layer.open({
                type: 2,
                title: '图书编辑',
                content: 'addBook.ac',
                shade: 0,
                success: function (layer0, index) {
                    var body = layui.layer.getChildFrame('body', index);
                    if (data) {
                        body.find(".id").val(data.id);
                        body.find(".number").val(data.number);
                        body.find(".name").val(data.name);
                        body.find(".img").val(data.img);
                        body.find(".author").val(data.author);
                        body.find(".status").val(data.status);
                        body.find(".wordNumber").val(data.wordNumber.substring(0, data.wordNumber.length - 1));
                        body.find(".data").val(data.data);
                        body.find(".category").val(data.category);
                        body.find(".collection").val(data.collection);
                        body.find(".totalHits").val(data.totalHits);
                        body.find(".monthlyClicks").val(data.monthlyClicks);
                        body.find(".weeklyClicks").val(data.weeklyClicks);
                        body.find(".totalRecommendedNumber").val(data.totalRecommendedNumber);
                        body.find(".monthlyRecommendedNumber").val(data.monthlyRecommendedNumber);
                        body.find(".weekRecommendedNumber").val(data.weekRecommendedNumber);
                        body.find(".synopsis").val(data.synopsis);
                    }
                },
                end: function () {
                    tableIns.reload();
                }
            });
            layer.full(index);
        } else if (edit == 'add') {
            var index = layer.open({
                type: 2,
                title: '添加图书',
                content: 'addBook.ac',
                shade: 0,
                end: function () {
                    tableIns.reload();
                }
            });
            layer.full(index);
        }
    }

    //批量导出
    function batchExport() {
        var index = layer.open({
            type: 2,
            id: 'layer_batchExport',
            title: '导出数据',
            content: 'batchExport.ac?url=' + url + '&category=' + category + '&sort=' + sort + '&status=' + status + '',
            shade: 0,
            maxmin: true
        });
        layer.full(index);
    }

    //导出选中的数据
    function batchCheckExport(data) {
        layer.open({
            title: '导出数据',
            content: '<p>导出' + data.length + '条数据?</p>' +
            '<p>数据默认名称为：默认</p>' +
            '<p>默认格式：xlsx</p>',
            btn: ['立即导出', '取消'],
            btnAlign: 'c',
            success: function (index, layer0) {
                form.render('select');
                form.render('radio');
            },
            btn1: function (index, layero) {
                // 1. 数组头部新增表头
                data.unshift({
                    number: '书号',
                    name: '书名',
                    img: '图片地址',
                    author: '作者',
                    status: '状态',
                    wordNumber: '字数',
                    data: '最近更新',
                    category: '小说类型',
                    collection: '收藏',
                    totalHits: '总点击',
                    monthlyClicks: '月点击',
                    weeklyClicks: '周点击',
                    totalRecommendedNumber: '总推荐',
                    monthlyRecommendedNumber: '月推荐',
                    weekRecommendedNumber: '周推荐',
                    synopsis: '简介'
                });
                // 2. 如果需要调整顺序，请执行梳理函数
                var data1 = parent.LAY_EXCEL.filterExportData(data, [
                    'number', 'name', 'img', 'author', 'status', 'wordNumber', 'data', 'category',
                    'collection', 'totalHits', 'monthlyClicks', 'weeklyClicks', 'totalRecommendedNumber',
                    'monthlyRecommendedNumber', 'weekRecommendedNumber', 'synopsis'
                ]);
                // console.log(data1);
                var len = data.length - 1;
                // 3. 执行导出函数，系统会弹出弹框
                parent.LAY_EXCEL.exportExcel({sheet1: data1}, '默认.xlsx', 'xlsx', null);
                layer.msg('成功导出' + len + '条数据');
                layer.close(index);
            },
            btn2: function (index, layero) {
                layer.close(index);
            }
        });
    }

    //搜索
    function search() {
        var input = $('#search_input').val();
        if (input == '') {
            parent.layer.msg("请输入搜索关键字");
        } else {
            tableIns.reload({
                    page: {
                        curr: 1
                    },
                    where: {
                        author: input,
                        name: input
                    }
                    , url: 'selectBookLikeAuthorOrName.do'
                    , method: 'get'
                    , text: {
                        none: '没有找到你想要的'
                    }
                    , cols: [
                        [
                            {type: 'checkbox', fixed: 'left'}
                            , {field: 'id', title: 'ID', width: 80, align: 'center', fixed: true}
                            , {field: 'number', title: '书号', width: 80, align: 'center'}
                            , {
                            field: 'img', title: '封面', templet: '#img', align: 'center', templet: function (d) {
                                return '<img src=\"' + d.img + '\" alt=\"加载失败\">';
                            }
                        }
                            , {field: 'category', title: '小说类型', align: 'center', hide: true}
                            , {
                            field: 'name', title: '书名', align: 'center', templet: function (d) {
                                var input1 = input;
                                var i = d.name.indexOf(input1);
                                var j = d.name.indexOf(input1.toLocaleUpperCase());
                                var k = d.name.indexOf(input1.toLocaleLowerCase());

                                if (i != -1 || j != -1 || k != -1) {
                                    if (j != -1) {
                                        i = j;
                                    } else if (k != -1) {
                                        i = k;
                                    }
                                    var s = d.name.substring(0, i);
                                    var e = d.name.substring(i + input.length, d.name.length);
                                    return s + '<span style="color: red">' + d.name.substring(i, i + input.length)
                                        + '</span>' + e;
                                } else {
                                    return d.name;
                                }
                            }
                        }
                            , {
                            field: 'author', title: '作者', align: 'center', templet: function (d) {
                                //大小写对比
                                var i = d.author.indexOf(input);
                                var j = d.author.indexOf(input.toLocaleUpperCase());
                                var k = d.author.indexOf(input.toLocaleLowerCase());

                                if (i != -1 || j != -1 || k != -1) {
                                    if (j != -1) {
                                        i = j;
                                    } else if (k != -1) {
                                        i = k;
                                    }
                                    var s = d.author.substring(0, i);
                                    var e = d.author.substring(i + input.length, d.author.length);
                                    return s + '<span style="color: red">' + d.author.substring(i, i + input.length) + '</span>' + e;
                                } else {
                                    return d.author;
                                }
                            }
                        }
                            , {field: 'status', title: '状态', align: 'center'}
                            , {field: 'collection', title: '收藏', align: 'center', hide: 'true'}
                            , {
                            field: 'wordNumber', title: '字数', align: 'center', templet: function (d) {
                                var num = d.wordNumber.substring(0, d.wordNumber.length - 1);
                                if (num > 10000) {
                                    var n = parseInt(num / 10000);
                                    return '<span style="color: #c00;">' + n + "万字" + '</span>'
                                } else {
                                    return '<span>' + num + '字' + '</span>'
                                }
                            }
                        }
                            , {field: 'data', title: '更新时间', align: 'center'}
                            , {field: 'totalHits', title: '总点击数', align: 'center', hide: 'true'}
                            , {field: 'monthlyClicks', title: '月点击数', align: 'center', hide: 'true'}
                            , {field: 'weeklyClicks', title: '周点击数', align: 'center', hide: 'true'}
                            , {field: 'totalRecommendedNumber', title: '总推荐数', align: 'center', hide: 'true'}
                            , {field: 'monthlyRecommendedNumber', title: '月推荐数', align: 'center', hide: 'true'}
                            , {field: 'weekRecommendedNumber', title: '周推荐数', align: 'center', hide: 'true'}
                            , {field: 'synopsis', title: '简介', align: 'center', hide: 'true'}
                            , {fixed: 'right', title: '操作', align: 'center', width: 160, toolbar: '#bar'}
                        ]
                    ]
                }
            );
        }
    }

    //批量导入
    function batchAdd() {
        var index = layer.open({
            type: 2,
            title: '批量导入',
            content: 'batchAdd.ac',
            shade: 0
        });

        layer.full(index);
    };


    //预览
    function preview(data) {
        var index = layer.open({
            type: 2,
            title: '图书预览',
            content: 'preview.ac?number=' + data.number + '',
            shade: 0,
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (data) {
                    var wordNumber;
                    var num = data.wordNumber.substring(0, data.wordNumber.length - 1);
                    if (num > 10000) {
                        var n = parseInt(num / 10000);
                        wordNumber = n + "万字";
                    } else {
                        wordNumber = num + "字";
                    }

                    body.find(".img").attr("src", data.img);
                    body.find(".name")[0].innerHTML = data.name;
                    body.find(".author")[0].innerHTML = data.author;
                    body.find(".category")[0].innerHTML = data.category;
                    body.find(".status")[0].innerHTML = data.status;
                    body.find(".collection")[0].innerHTML = data.collection;
                    body.find(".wordNumber")[0].innerHTML = wordNumber;
                    body.find(".data")[0].innerHTML = data.data;

                    body.find(".totalHits")[0].innerHTML = data.totalHits;
                    body.find(".monthlyClicks")[0].innerHTML = data.monthlyClicks;
                    body.find(".weeklyClicks")[0].innerHTML = data.weeklyClicks;
                    body.find(".totalRecommendedNumber")[0].innerHTML = data.totalRecommendedNumber;
                    body.find(".monthlyRecommendedNumber")[0].innerHTML = data.monthlyRecommendedNumber;
                    body.find(".weekRecommendedNumber")[0].innerHTML = data.weekRecommendedNumber;
                    body.find(".synopsis")[0].innerHTML = data.synopsis;

                }
            }
        });
        layer.full(index);
    }
});