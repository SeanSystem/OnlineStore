<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>用户登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
		<script src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<style>

  body{
   margin-top:20px;
   margin:0 auto;
 }
 
 .carousel-inner .item img{
	 width:100%;
	 height:300px;
 }
 .container .row div{ 
	 /* position:relative;
	 float:left; */
 }
 
font {
    color: #666;
    font-size: 22px;
    font-weight: normal;
    padding-right:17px;
}
 </style>
  <script type="text/javascript">
				
				$(function(){
					
					$("#form").validate({
						onsubmit:true,// 是否在提交时验证
						onfocusout:false,// 是否在获取焦点时验证
						onkeyup :false,// 是否在敲击键盘时验证
					   rules:{
							username:"required",
							password:"required",
							randomStr:{
								required:true,
								remote:{
									url:"${pageContext.request.contextPath}/user/checkCode",
									type:"post",
									data: {                     //要传递的数据
										randomStr:function() 
										{
									          return $("#randomStr").val();
									     }
								     }
								}
								
							}
							},
							
						messages:{
							username:"用户名不能为空！",
							password:"密码不能为空！",
							randomStr:{
								required:"验证码不能为空！",
								remote:"验证码错误，如看不清请点击刷新！"
								
							}
						},
						
						submitHandler: function(form) { //通过之后回调
							//进行ajax传值
							$.ajax({
								url : "${pageContext.request.contextPath}/user/login",
								type : "post",
								data: {
								username: $("#username").val(),
								password: $("#password").val(),
								remeber: $("#remeber").val(),
								autologin: $("#autologin").val() 
								},
								success : function(msg) {
									//要执行的代码
									if(msg == "false"){
										alert("用户名或密码错误！");
									}else if(msg == "true"){
										window.location.href="${pageContext.request.contextPath}/";  
									}
								}
							});
							},
							
						invalidHandler: function(form, validator) {return false;}
						
					});
					
				});
			
			function changeImg(obj){
				
				obj.src = obj.src+"?time="+(new Date());
				var random = document.getElementById("random");
			}
		
			function changeRemeberState(){
				
				if($("#remeber").attr("value") == "false"){
					
					$("#remeber").attr({'value':'true'});
				}else{
					
					$("#remeber").attr({'value':'false'});
				}
				
				
			}
			
			function changeAutoState(){
				
				if($("#autologin").attr("value") == "false"){
					
					$("#autologin").attr({'value':'true'});
				}else{
					
					$("#autologin").attr({'value':'false'});
				}
			}
			
			
	</script>
</head>
<body>
	
<div class="container-fluid">	
	
	<!--
            	描述：菜单栏
            -->
			<jsp:include page="../jsp/head.jsp"></jsp:include>

<div class="container"  style="width:100%;height:460px;background:#FF2C4C url('${pageContext.request.contextPath}/images/loginbg.jpg') no-repeat;">
		
<div class="row"> 
	<div class="col-md-7">
		<!--<img src="${pageContext.request.contextPath}/image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
	</div>
	
	<div class="col-md-5">
				<div style="width:440px;border:1px solid #E7E7E7;padding:20px 0 20px 30px;border-radius:5px;margin-top:60px;background:#fff;">
				<font>会员登录</font>USER LOGIN

				<div>&nbsp;</div>
<form  id="form" class="form-horizontal" action="" method="post">
  
 <div class="form-group">
 	
    <label for="username" class="col-sm-2 control-label">用户名</label>
    <div class="col-sm-6">
	    <c:set var="name" value="${cookie.username.value }"></c:set>
	    
	 	<input name="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
    </div>
  </div>
   <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
    <div class="col-sm-6">
      <input name="password" id="password" type="password" class="form-control" placeholder="请输入密码">
    </div>
  </div>
   <div class="form-group">
        <label for="inputPassword3" class="col-sm-2 control-label">验证码</label>
    <div class="col-sm-4">
      <input name="randomStr" id="randomStr" type="text" class="form-control"  placeholder="请输入验证码">
    </div>
    <div class="col-sm-4">
      <img  src="${pageContext.request.contextPath}/code" onclick="changeImg(this)"/>
    </div>
    
  </div>
   <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <div class="checkbox">
        <label>
          <input type="checkbox" name="autologin" id="autologin" value="false" onclick="changeAutoState()"> 自动登录
        </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <label>
          <input type="checkbox" name="remeber"  id="remeber" value="false" onclick="changeRemeberState()"> 记住用户名
        </label>
      </div>
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
    <input type="submit"  width="100" value="登录" name="submit" border="0"
    style="background: url('${pageContext.request.contextPath}/img/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
    height:35px;width:100px;color:white;">
    </div>
  </div>
</form>
</div>			
	</div>
</div>
</div>	

		<!-- 引入底部文件 -->
		<jsp:include page="../jsp/foot.jsp"></jsp:include>
		
		<script type="text/javascript">
			window.onload = function(){
				
				var user = decodeURI("${name}");
				if(user!=null && user!=""){	
					document.getElementsByName("username")[0].value = user;
				}
			}
		</script>
		</div>
</body>

</html>