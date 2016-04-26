/**
 * Created by lc on 15/8/20.
 */
var pageOffset = 0;
var pageCount = 0;

function init() {
    getPageCount();
}

function initUstv() {
    getUstvPageCount();
}

function gotoPage(offset) {
    if (offset > pageCount - 1) offset = pageCount - 1;
    if (offset < 0) offset = 0;
    pageOffset = offset;

    $.ajax({
        type: 'GET',
        url: "page/" + (offset * 20),
        beforeSend: function(XMLHttpRequest){
        },
        success: function(data, textStatus){
            //console.log(data);
            var list = document.getElementById("movie_list");
            list.innerHTML = "<a class=\"list-group-item active\">最近更新</a>";
            var resp = eval("(" + data + ")");
            if (resp["code"] == 200) {
                var movies = resp["obj"];
                for (var idx in movies) {
                    var movie = eval("(" + movies[idx] + ")");
                    list.innerHTML += "<a class=\"list-group-item cursor\" onclick=\"get_movie(" + movie["id"] + ")\">" + movie["name"] + "<label style=\"float:right;font-weight:normal;\">" + movie["time"] + "</label></a>";
                }
            } else {
                list.innerHTML += "<a class=\"list-group-item\">暂无数据</a>";
            }

        },
        complete: function(XMLHttpRequest, textStatus){
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            console.error();
        }
    });

    if (pageCount > 0) {
        var start = pageOffset - 2;
        if (start < 0) start = 0;
        var end = start + 5;
        if (end > pageCount) end = pageCount;
        var pages = document.getElementById("pages");
        pages.innerHTML = "<li><a class='cursor' onclick=\"gotoPage(" + (pageOffset - 1) + ")\" aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>";
        for (var i = start; i < end; i++) {
            if (i == pageOffset) {
                pages.innerHTML += "<li class=\"active\"><a onclick=\"gotoPage(" + i + ")\">" + (i + 1) + "</a></li>"
            } else {
                pages.innerHTML += "<li><a class='cursor' onclick=\"gotoPage(" + i + ")\">" + (i + 1) + "</a></li>"
            }
        }
        pages.innerHTML += "<li><a class='cursor' onclick=\"gotoPage(" + (pageOffset + 1) + ")\" aria-label=\"Previous\"><span aria-hidden=\"true\">&raquo;</span></a></li>";
    }
}

function gotoUstvPage(offset) {
    if (offset > pageCount - 1) offset = pageCount - 1;
    if (offset < 0) offset = 0;
    pageOffset = offset;

    $.ajax({
        type: 'GET',
        url: "ustv/page/" + (offset * 20),
        beforeSend: function(XMLHttpRequest){
        },
        success: function(data, textStatus){
            //console.log(data);
            var list = document.getElementById("ustv_list");
            list.innerHTML = "<a class=\"list-group-item active\">最近更新</a>";
            var resp = eval("(" + data + ")");
            if (resp["code"] == 200) {
                var tvs = resp["obj"];
                for (var idx in tvs) {
                    var tv = eval("(" + tvs[idx] + ")");
                    list.innerHTML += "<a class=\"list-group-item cursor\" onclick=\"get_ustv(" + tv["id"] + ")\">" + tv["name"] + "<label style=\"float:right;font-weight:normal;\">" + tv["time"] + "</label></a>";
                }
            } else {
                list.innerHTML += "<a class=\"list-group-item\">暂无数据</a>";
            }

        },
        complete: function(XMLHttpRequest, textStatus){
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            console.error();
        }
    });

    if (pageCount > 0) {
        var start = pageOffset - 2;
        if (start < 0) start = 0;
        var end = start + 5;
        if (end > pageCount) end = pageCount;
        var pages = document.getElementById("ustv_pages");
        pages.innerHTML = "<li><a class='cursor' onclick=\"gotoUstvPage(" + (pageOffset - 1) + ")\" aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>";
        for (var i = start; i < end; i++) {
            if (i == pageOffset) {
                pages.innerHTML += "<li class=\"active\"><a onclick=\"gotoUstvPage(" + i + ")\">" + (i + 1) + "</a></li>"
            } else {
                pages.innerHTML += "<li><a class='cursor' onclick=\"gotoUstvPage(" + i + ")\">" + (i + 1) + "</a></li>"
            }
        }
        pages.innerHTML += "<li><a class='cursor' onclick=\"gotoUstvPage(" + (pageOffset + 1) + ")\" aria-label=\"Previous\"><span aria-hidden=\"true\">&raquo;</span></a></li>";
    }
}

function getPageCount() {
    $.ajax({
        type: 'GET',
        url: "pages",
        beforeSend: function(XMLHttpRequest){
        },
        success: function(data, textStatus){
            //console.log(data);
            var resp = eval("(" + data + ")");
            if (resp["code"] == 200) {
                pageCount = resp["obj"];
                gotoPage(0);
            }
        },
        complete: function(XMLHttpRequest, textStatus){
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            console.error();
        }
    });
}

function getUstvPageCount() {
    $.ajax({
        type: 'GET',
        url: "ustv/pages",
        beforeSend: function(XMLHttpRequest){
        },
        success: function(data, textStatus){
            //console.log(data);
            var resp = eval("(" + data + ")");
            if (resp["code"] == 200) {
                pageCount = resp["obj"];
                gotoUstvPage(0);
            }
        },
        complete: function(XMLHttpRequest, textStatus){
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            console.error();
        }
    });
}

function get_movie(id) {
    //console.log("movie: " + id);
    window.open("/movie/" + id);
}

function get_ustv(id) {
    //console.log("movie: " + id);
    window.open("/ustv/view/" + id);
}

function searchMovie() {
    var key = document.getElementById("search_key").value;
    if (key.length == 0) {
        return;
    }
    if (key.length < 2) {
        alert("搜索关键字不能少于2个字");
        return;
    }
    key = BASE64.encoder(key);
    window.location.replace("/search/" + key);
}

function searchInput() {
    if (event.keyCode == 13) {
        event.keyCode = 0;//屏蔽回车键
        event.returnvalue = false;
        searchMovie();
    }
}

function setCookie(name,value)//两个参数，一个是cookie的名子，一个是值
{
    var Days = 7; //此 cookie 将被保存 7 天
    var exp  = new Date();    //new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
function getCookie(name)//取cookies函数
{
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    if(arr != null) return unescape(arr[2]); return null;

}
function delCookie(name)//删除cookie
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
