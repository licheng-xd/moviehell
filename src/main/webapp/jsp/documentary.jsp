<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="java.util.Iterator" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="icon" href="../../img/favicon.ico">

  <title><%=request.getAttribute("title")%> - Movie Hell</title>

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

  <!-- Custom styles for this template -->
  <link rel="stylesheet" href="../../css/jumbotron-narrow.css">

  <script src="/js/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"/>
  <script src="/js/movie.js"></script>

  <script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
              (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-71702178-2', 'auto');
    ga('send', 'pageview');

  </script>
    
</head>

<body>
<a class="moviehell-icon" href="http://www.lchml.com" target="_blank">站长主页</a>
<a class="jandan-icon" href="http://jandan.net" target="_blank">煎蛋网</a>
<a class="lofter-icon" href="http://licheng-xd.lofter.com/" target="_blank">Lofter摄影</a>
<div class="container">
  <div class="header clearfix">
    <nav>
      <ul class="nav nav-pills pull-right">
        <li role="presentation"><a href="/index.html">电影</a></li>
        <li role="presentation"><a href="/ustv.html">美剧</a></li>
        <li role="presentation"><a href="/documentary.html">纪录片</a></li>
        <li role="presentation"><a href="/about.html">关于</a></li>
      </ul>
    </nav>
    <h3 class="text-muted">Movie Hell</h3>
  </div>

  <div class="jumbotron">
    <h3 class="title"><%=request.getAttribute("title")%></h3>
    <div class="imagediv"><img class="image" src="<%=request.getAttribute("img")%>"/></div>
    <%--<a class="intro" target="_blank" href="http://movie.douban.com/subject_search?search_text=<%=request.getAttribute("name")%>">豆瓣电影</a>--%>
    <p class="intro"><%=request.getAttribute("intro").toString().replace("\r\n",
            "<br/>")%></p>

    <!--<p><a class="btn btn-info" href="" role="button">下载地址</a></p>-->
    <button type="button" class="btn btn-default btn-lg" onclick="show_documentary_download()">
      <span class="glyphicon glyphicon-circle-arrow-down" aria-hidden="true"></span> 下载地址
    </button>
    <br/><br/>
    <div id="documentary_download" style="font-size: 12px; display: block;">
      <%
        JSONArray hrefs = JSONArray.parseArray((String)request.getAttribute("hrefs"));
        Iterator<Object> iter = hrefs.iterator();
        while (iter.hasNext()) {
          String name = (String) iter.next();
          String href = (String) iter.next();
      %>
      <a href="<%=href%>"><%=name%></a><br/>
      <%
        }
      %>
    <br><label>注：右键复制链接地址到迅雷中进行下载。</label>
    </div>
  </div>

  <!-- 多说评论框 start -->
  <div class="ds-thread" data-thread-key="<%=request.getAttribute("id")%>" data-title="<%=request.getAttribute("title")%>" data-url="http://www.moviehell.net/documentary/view/<%=request.getAttribute("id")%>"></div>
  <!-- 多说评论框 end -->

  <footer class="footer">
    <p>&copy; LC 2016</p>
  </footer>

</div> <!-- /container -->
<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1256273064'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s4.cnzz.com/z_stat.php%3Fid%3D1256273064%26show%3Dpic1' type='text/javascript'%3E%3C/script%3E"));</script>
<script>
function show_documentary_download() {
  document.getElementById("documentary_download").style.visibility = "visible";
}
</script>

<!-- 多说公共JS代码 start (一个网页只需插入一次) -->
<script type="text/javascript">
  var duoshuoQuery = {short_name:"moviehell"};
  (function() {
    var ds = document.createElement('script');
    ds.type = 'text/javascript';ds.async = true;
//    ds.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') + '//static.duoshuo.com/embed.js';
    ds.src = '../../js/embed.js'
    ds.charset = 'UTF-8';
    (document.getElementsByTagName('head')[0]
    || document.getElementsByTagName('body')[0]).appendChild(ds);
  })();
</script>
<!-- 多说公共JS代码 end -->
</body>
</html>

