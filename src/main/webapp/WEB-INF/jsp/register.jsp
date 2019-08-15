<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head></head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员注册</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
		<script src="${pageContext.request.contextPath }/js/jquery.validate.js"></script>
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
    color: #3164af;
    font-size: 18px;
    font-weight: normal;
    padding: 0 10px;
}
 </style>
 <script type="text/javascript">
				
				$(function(){
					
					$("#form").validate({
						
							rules:{
								
								username:{
									
									required:true,
										remote:{
												url:"${pageContext.request.contextPath}/user/checkUsername",//用户名重复检查，别跨域调用
												type:"post",
												data: {                     //要传递的数据
													username: function() 
													{
												            return $("#username").val();
												     }
											     }
											} 
								 
									},
								password:"required",
								repassword:{
									
									required:true,
									equalTo:"#password"
								},
								email:{
									
									required:true,
									email:true
								},
								
								name:"required",
								
								birthday:"date",
								
								randomStr:{
									
									required:true,
									
									remote:{  //当返回的值为false显示，true是不显示
										url:"${pageContext.request.contextPath}/user/checkCode",//用户名重复检查，别跨域调用
										type:"post",
										data: {                     //要传递的数据
											randomStr: function() 
											{
										          return $("#randomStr").val();
										     }
									     }
									}
								},
								
								telephone:{
									
									required:true,
									minlength : 11, 
							        isMobile : true  
									
								}
							},
						messages:{
							
							username:{
								
								required:"用户名不能为空！",
								remote:"用户名已存在！"
							},
							
							password:"密码不能为空!",
							
							repassword:{
								
								required:"重复密码不能为空!",
								equalTo:"两次密码不一致!"
							},
							
							email:"邮箱不合法!",
							name:"姓名不能为空！",
							birthday:"请先选择日期",
							
							randomStr:{
								
								required:"请输入验证码!",
								remote:"验证码错误。如看不清，请点击刷新！"  //返回结果为false是显示
							},
							
							telephone:{
								
								required:"请输入手机号！",
								minlength : "不能小于11个字符!",  
							    isMobile : "请正确填写手机号码!"  
							}
						}
						
					
					});
					
					//手机号码验证  

					jQuery.validator.addMethod("isMobile", function(value, element) {  

					 var length = value.length;  

					 var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;  

					 return this.optional(element) || (length == 11 && mobile.test(value));  

					}, "请正确填写手机号码");  
				
						
				});
				
				
			function changeImg(obj){
				
				obj.src = obj.src+"?time="+(new Date());
				var random = document.getElementById("random");
			} 
		
	</script>
</head>
<body>


<div class="container-fluid">

			<!--
            	描述：菜单栏
            -->
			<jsp:include page="../jsp/head.jsp"></jsp:include>

<div class="container" style="width:100%;background:url('${pageContext.request.contextPath}/img/regist_bg.jpg');">
<div class="row"> 

	<div class="col-md-2"></div>
	
	


	<div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc;">
		<font>会员注册</font>USER REGISTER
		<form id="form" class="form-horizontal" style="margin-top:5px;" action="${pageContext.request.contextPath }/user/register" method="post">
						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="username" placeholder="请输入用户名" name="username">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-6">
								<input type="password" class="form-control" id="password" placeholder="请输入密码" name="password">
							</div>
						</div>
						<div class="form-group">
							<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
							<div class="col-sm-6">
								<input name="repassword" type="password" class="form-control" id="confirmpwd" placeholder="请输入确认密码">
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
							<div class="col-sm-6">
								<input type="email" class="form-control" id="inputEmail3" placeholder="Email" name="email">
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">手机号</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="inputTelphone" placeholder="手机号" name="telephone">
							</div>
						</div>
						<div class="form-group">
							<label for="usercaption" class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="usercaption" placeholder="请输入姓名" name="name">
							</div>
						</div>
						<div class="form-group opt">
							<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
							<div class="col-sm-6">
								<label class="radio-inline">
			  <input type="radio" name="sex" id="inlineRadio1" value="男" checked="checked"> 男
			</label>
								<label class="radio-inline">
			  <input type="radio" name="sex" id="inlineRadio2" value="女"> 女
			</label>
							</div>
						</div>
						<div class="form-group">
							<label for="date" class="col-sm-2 control-label">出生日期</label>
							<div class="col-sm-6">
								<input type="date" class="form-control" name="birthday">
							</div>
						</div>

						<div class="form-group">
							<label for="date" class="col-sm-2 control-label">验证码</label>
							<div class="col-sm-3">
								<input id="randomStr" type="text" class="form-control" name="randomStr">
								
							</div>
							<div class="col-sm-2">
								<img id="img"  src="${pageContext.request.contextPath }/code" onclick="changeImg(this)" />
								
							</div>

						</div>

						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" width="100" value="注册" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
				    height:35px;width:100px;color:white;">
				    
							</div>
						</div>
				</form>
	</div>
	
	<div class="col-md-2"></div>
  
</div>
</div>

	<!-- 引入底部的文件 -->
	<jsp:include page="../jsp/foot.jsp"></jsp:include>
</div>
</body></html>




