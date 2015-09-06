<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="icon" href="../img/favicon.ico">

  <title>Movie Hell</title>

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

  <!-- Custom styles for this template -->
  <link rel="stylesheet" href="../css/jumbotron-narrow.css">

  <script src="/js/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"/>
  <script src="/js/movie.js"></script>
  <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-66789738-1', 'auto');
      ga('send', 'pageview');

    </script>
    <script>
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "//hm.baidu.com/hm.js?2a79b8f2c726d310f748aca10af76b0f";
      var s = document.getElementsByTagName("script")[0]; 
      s.parentNode.insertBefore(hm, s);
    })();
    </script>
</head>

<body>

<div class="container">
  <div class="header clearfix">
    <nav>
      <ul class="nav nav-pills pull-right">
        <li role="presentation"><a href="/index.html">Home</a></li>
        <li role="presentation"><a href="/about.html">About</a></li>
      </ul>
    </nav>
    <h3 class="text-muted">Movie Hell</h3>
  </div>

  <div class="jumbotron">
    <h3 class="title"><%=request.getAttribute("title")%></h3>
    <div class="imagediv"><img class="image" src="<%=request.getAttribute("img")%>"/></div>
    <p class="intro"><%=request.getAttribute("intro").toString().replace("\r\n",
            "<br/>")%></p>

    <!--<p><a class="btn btn-info" href="" role="button">下载地址</a></p>-->
    <button type="button" class="btn btn-default btn-lg" onclick="show_download()">
      <span class="glyphicon glyphicon-circle-arrow-down" aria-hidden="true"></span> 下载地址
    </button>
    <br/><br/>
    <a id="download" style="visibility: hidden;font-size: 12px; display: block;"><%=request.getAttribute("href")%></a>
  </div>


  <footer class="footer">
    <p>&copy; LC 2015</p>
  </footer>

</div> <!-- /container -->

<script>
function show_download() {
  document.getElementById("download").style.visibility = "visible";
}
</script>
</body>
</html>

