layui.use(['table', 'layer', 'jquery', 'form'], function () {
    var table = layui.table;
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form;

    var index = layer.msg('加载中，请稍候', {icon: 16, time: false, shade: 0.8});

    var count;

    //渲染表格
    var tableIns = table.render({
        elem: '#user'
        , page: true
        , toolbar: '#toolbar'
        , height: 'full-30'
        , method: 'GET'
        , url: 'user.do'
        , limit: 10
        , limits: [10, 20, 50, 100]
        , text: {
            none: '无用户数据，请联系管理员添加用户' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        },
        cols: [
            [
                {type: 'checkbox', fixed: 'left'}
                , {field: 'userId', title: 'ID', width: 80, align: 'center', fixed: true}
                , {field: 'userName', minWidth: 80, title: '用户名', align: 'center'}
                , {field: 'userSex', minWidth: 40, title: '性别', align: 'center'}
                , {field: 'userEmail', minWidth: 160, title: '电子邮箱', align: 'center'}
                , {field: 'userPhone', minWidth: 160, title: '联系电话', align: 'center'}
                , {field: 'userGrade', minWidth: 40, title: '用户等级', align: 'center'}
                , {
                field: 'userStatus', minWidth: 80, title: '用户状态', align: 'center', templet: function (d) {
                    if (d.userStatus == 0) {
                        return '<span>离线</span>';
                    } else if (d.userStatus == 1) {
                        return '<span style="color: #1E9FFF;">在线</span>';
                    } else {
                        return '<span style="color: #FF5722">error</span>';
                    }
                }
            }
                , {field: 'userEndTime', minWidth: 180, title: '最近登陆', align: 'center'}
                , {fixed: 'right', title: '操作', align: 'center', width: 160, toolbar: '#bar'}
            ]
        ]
        , done: function (res, curr, count_) {
            count = count_;
            layer.close(index);
        }
    });

    //监听头工具栏事件
    table.on('toolbar(User)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'search':
                search(data.length);
                break;
            case 'add':
                addNews('add');
                break;
            case 'batchDel':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    var datas = '';
                    for (var i = 0; i < data.length; i++) {
                        datas += "data[]=" + data[i].userId + "&";
                    }
                    datas.substring(0, datas.length - 1);
                    layer.msg('<br>是否确认删除<br>' + data.length + '条数据', {
                        time: 20000, //20s后自动关闭
                        btn: ['狠心删除', '考虑考虑'],
                        btn1: function (index) {
                            $.ajax({
                                type: 'POST',
                                url: 'bathDeleteUserById.do',
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
    table.on('tool(User)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        switch (layEvent) {
            case 'del':
                layer.msg('<br>是否确认删除<br>' + data.userName + '', {
                    time: 20000, //20s后自动关闭
                    btn: ['狠心删除', '考虑考虑'],
                    btn1: function (index) {
                        $.ajax({
                            type: 'POST',
                            url: 'deleteUserById.do',
                            data: 'userId=' + data.userId,
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

    function addNews(pro, data) {
        if (pro == 'edit') {
            var index = layer.open({
                type: 2,
                title: '编辑用户',
                content: 'addUser.ac?sex=' + data.userSex,
                shade: 0,
                success: function (layer0, index) {
                    var body = layui.layer.getChildFrame('body', index);
                    if (data) {
                        body.find('.userId').val(data.userId);
                        body.find('.userName').val(data.userName);
                        body.find('.userEmail').val(data.userEmail);
                        body.find('.userPhone').val(data.userPhone);
                        body.find('.userGrade').val(data.userGrade);
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
                title: '添加用户',
                content: 'addUser.ac',
                shade: 0,
                end: function () {
                    tableIns.reload();
                }
            });
            layer.full(index);
        }
    }

    function search() {
        var input = $('#search_input').val();
        if (input == '') {
            parent.layer.msg("请输入搜索关键字");
        }
        else if (count < 40) {
            layer.msg("用户数量" + count + "低于40,没有必要使用查询！！！");
        } else {
            layer.msg("正在开发中。。。。。");
        }
    }

    function preview(data) {
        var content = '<p>姓名：<span>' + data.userName + '</span></p>' +
            '<p>性别：<span>' + data.userSex + '</span></p>' +
            '<p>邮箱：<span>' + data.userEmail + '</span></p>' +
            '<p>电话：<span>' + data.userPhone + '</span></p>' +
            '<p>等级：<span>' + data.userGrade + '</span></p>';

        layer.open({
            type: 0,
            title: '用户信息',
            content: content,
            btn: '666',
            btnAlign: 'c',
            btn1: function (index, layer0) {
                layer.close(index);
            }
        })
    }
});