<%--
  Created by IntelliJ IDEA.
  User: lc
  Date: 15/8/21
  Time: 下午1:58
  To change this template use File | Settings | File Templates.
--%>
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

  <title>404 - Movie Hell</title>

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

  <!-- Custom styles for this template -->
  <link rel="stylesheet" href="../css/jumbotron-narrow.css">

  <script src="/js/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"/>

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
<div class="container" id="container">
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

  <h1 align="center">掉沟里了</h1>

  <div align="center">
    <canvas id="q" height="350">...</canvas><br/><br/>
  </div>

  <footer class="footer">
    <p>&copy; LC 2016</p>
  </footer>

</div> <!-- /container -->
<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1256273064'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s4.cnzz.com/z_stat.php%3Fid%3D1256273064%26show%3Dpic1' type='text/javascript'%3E%3C/script%3E"));</script>
<script>
  $(document).ready(function(){
    var s=window.screen;
    var ml = document.getElementById("container");
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

  });
</script>
</body>
</html>

