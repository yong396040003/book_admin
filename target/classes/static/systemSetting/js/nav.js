var navName;

function getNavName(navName_) {
    navName = navName_;
}

layui.use(['table', 'jquery', 'layer'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;

    //渲染表格
    var tableIns = table.render({
        elem: '#nav'
        , page: true
        , toolbar: '#toolbar'
        , height: "full-30"
        , method: 'post'
        , url: 'getNav.do'
        , where: {
            name: navName,
        }
        , limit: 10
        , limits: [10, 20, 50, 100]
        , text: {
            none: '数据加载异常' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        },
        cols: [
            [
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', width: 80, align: 'center', fixed: true}
                , {field: 'title', title: '名称', minWidth: 80, align: 'center'}
                , {
                field: 'icon', title: '图标', minWidth: 80, align: 'center', templet: function (d) {
                    if (d.icon == null) {
                        return '<span>无</span>';
                    } else if (d.icon.split("-")[0] == 'layui') {
                        return '<i class="' + d.icon + '"></i>';
                    } else {
                        return '<i class="layui-icon">' + d.icon + '</i>';
                    }
                }
            }
                , {
                field: 'href', title: 'href', minWidth: 80, align: 'center', templet: function (d) {
                    if (d.href == null) {
                        return '<span>无链接</span>';
                    } else {
                        return '<span>' + d.href + '</span>';
                    }
                }
            }
                , {field: 'spread', title: '是否展开', minWidth: 80, align: 'center'}
                , {field: 'pid', title: 'pid', minWidth: 80, align: 'center'}
                , {fixed: 'right', title: '操作', align: 'center', width: 160, toolbar: '#bar'}
            ]
        ]
    });

    //监听头工具栏事件
    table.on('toolbar(nav)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                addNews('add');
                break;
            case 'batchDel':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    var ids = '';
                    for (var i = 0; i < data.length; i++) {
                        ids += "ids[]=" + data[i].id + "&";
                    }
                    layer.msg('<br>是否确认删除<br>' + data.length + '条数据', {
                        time: 20000, //20s后自动关闭
                        btn: ['狠心删除', '考虑考虑'],
                        btn1: function (index) {
                            $.ajax({
                                type: 'POST',
                                url: 'bathDeleteNavById.do',
                                data: ids + "category=" + navName,
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
    table.on('tool(nav)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        switch (layEvent) {
            case 'del':
                layer.msg('<br>是否确认删除<br>' + data.title + '', {
                    time: 20000, //20s后自动关闭
                    btn: ['狠心删除', '考虑考虑'],
                    btn1: function (index) {
                        $.ajax({
                            type: 'POST',
                            url: 'deleteNavById.do',
                            data: 'id=' + data.id + '&category=' + navName,
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
                break;
            case 'edit':
                addNews('edit', data);
                break;
            case 'preview':
                preview(data);
                break;
        }
    });

    function addNews(pro, data) {
        if (pro == 'edit') {
            var index = layer.open({
                type: 2,
                title: '编辑nav',
                content: 'addNav.ac',
                shade: 0,
                success: function (layer0, index) {
                    var body = layui.layer.getChildFrame('body', index);
                    if (data) {
                        body.find('.id').val(data.id);
                        body.find('.title').val(data.title);
                        if (data.icon != null) {
                            body.find('.icon').val(data.icon);
                        }
                        if (data.href != null) {
                            body.find('.href').val(data.href);
                        }
                        body.find('.spread').val(data.spread);
                        body.find('.pid').val(data.pid);
                        console.log(navName);
                        body.find('.category').val(navName);
                    }
                },
                end: function () {
                    tableIns.reload();
                }
            });
            layer.full(index);
        } else if (pro == 'add') {
            var index = layer.open({
                type: 2,
                title: '添加nav',
                content: 'addNav.ac',
                shade: 0,
                success: function (layer0, index) {
                    var body = layui.layer.getChildFrame('body', index);
                    if (navName) {
                        body.find('.category').val(navName);
                    }
                },
                end: function () {
                    tableIns.reload();
                }
            });
            layer.full(index);
        }
    }

    function preview(data) {
        var content = '<p>id：<span>' + data.id + '</span></p>' +
            '<p>名称：<span>' + data.title + '</span></p>' +
            '<p>图标：<span>' + data.icon + '</span></p>' +
            '<p>页面：<span>' + data.href + '</span></p>' +
            '<p>是否展开：<span>' + data.spread + '</span></p>' +
            '<p>父级id：<span>' + data.pid + '</span></p>';
        var index = layer.open({
            type: 0,
            title: 'nav信息',
            btn: '666',
            btnAlign: 'c',
            content: content,
            btn1: function (index, layer0) {
                layer.close(index);
            }
        });
    }
});