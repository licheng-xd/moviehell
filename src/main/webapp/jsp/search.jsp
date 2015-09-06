<%@ page import="com.lc.moviehell.dao.domain.Movie" %>
<%@ page import="java.util.List" %>
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
  <script src="/js/movie.js"></script>
  <script src="/js/Base64.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"/>
  <%--<script src="/js/movie.js"></script>--%>
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

  <div class="row">
    <div class="col-lg-6" style="width: 100%">
      <div class="input-group">
        <input type="text" id="search_key" class="form-control" onkeypress="searchInput()" placeholder="Search Movie ...">
                <span class="input-group-btn">
                    <button class="btn btn-default" onclick="searchMovie()" type="button">搜索</button>
                </span>
      </div><!-- /input-group -->
    </div><!-- /.col-lg-6 -->
  </div><!-- /.row -->
  <br/>

  <div class="list-group" id="movie_list">
    <a class="list-group-item active">搜索结果</a>
    <%
      List<Movie> movies = (List<Movie>) request.getAttribute("result");
      if (movies.size() == 0) {
    %>
    <a class="list-group-item">不存在</a>
    <canvas id="q" height="350px">...</canvas><br><br>
    <%
      } else {
        for (Movie m : movies) {
    %>
          <a class="list-group-item" onclick="get_movie(<%=m.getId()%>)"><%=m.getName()%><label style="float:right;font-weight:normal;"><%=m.getTime()%></label></a>
    <%
        }
      }
    %>
  </div>

  <footer class="footer">
    <p>&copy; LC 2015</p>
  </footer>

</div> <!-- /container -->

<script>
  $(document).ready(function(){
    var qe = document.getElementById("q");
    if (qe == null || qe == undefined) {
      return;
    }
    var ml = document.getElementById("movie_list");
    var width = q.width = ml.offsetWidth;
    var height = q.height;
    var yPositions = Array(300).join(0).split('');
    var ctx=q.getContext('2d');

    var draw = function () {
      ctx.fillStyle='rgba(0,0,0,.05)';
      ctx.fillRect(0,0,width,height);
      ctx.fillStyle='#0F0';
      ctx.font = '10pt Georgia';
      yPositions.map(function(y, index) {
        text = String.fromCharCode(1e2+Math.random()*33);
        x = (index * 10)+10;
        q.getContext('2d').fillText(text, x, y);
        if(y > 100 + Math.random()*1e4) {
          yPositions[index]=0;
        } else {
          yPositions[index] = y + 10;
        }
      });
    };

    RunMatrix();

    function RunMatrix() {
      if(typeof Game_Interval != "undefined") clearInterval(Game_Interval);
      Game_Interval = setInterval(draw, 35);
    }

    function StopMatrix() {
      clearInterval(Game_Interval);
    }
    //setInterval(draw, 33);
    $("button#pause").click(function(){
      StopMatrix();});

    $("button#play").click(function(){
      RunMatrix();
    });
  });

</script>
</body>
</html>

