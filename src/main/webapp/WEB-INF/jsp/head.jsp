<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/head.css" type="text/css"/>

			<!--
            	描述：菜单栏
            -->
			<div class="row">
			
				<div class="col-md-4 col-lg-4 col-sm-6 hidden-xs">
					<img src="${pageContext.request.contextPath}/img/logo22.jpg" />
				</div>
				<div class="col-md-5 col-lg-5 col-sm-6 hidden-xs">
					<img src="${pageContext.request.contextPath}/img/header.png" />
				</div>
				
				
			<div class="myjd col-md-2 col-lg-2 col-sm-2 col-xs-6">
				<div class="mytu">
				</div>
				<a href="#">我的商城</a>
				<div class="jiantou">
				</div>
				<div class="myjdhide">
					<div class="hello">
						<c:set var="username" value="${sessionScope.user.username }"></c:set>
						<c:choose>
							<c:when test="${empty username }">
								<span>您好，请先</span>
								<a href="${pageContext.request.contextPath }/user/loginUI">登录</a>
								<a href="${pageContext.request.contextPath }/user/registerUI">注册</a>
							</c:when>
							<c:otherwise>
								<span>欢迎您：${username}</span>
								<a href="${pageContext.request.contextPath}/user/loginOut">安全退出</a>
							</c:otherwise>
						
						</c:choose>
					</div>
					<div class="hey">
						<div class="heyleft">
							<ul>
								<li><a href="${pageContext.request.contextPath}/order/getOrderByPage?currPage=1">全部订单</a></li>
								<li><a href="#">咨询回复</a></li>
								<li><a href="#">降价商品</a></li>
								<li><a href="#">返修退换货</a></li>
								<li><a href="#">优惠券</a></li>
							</ul>
						</div>
						<div class="heyright">
							<ul>
								<li><a href="#">我的关注></a></li>
								<li><a href="#">我的京豆></a></li>
								<li><a href="#">我的理财></a></li>
								<li><a href="#">我的白条></a></li>
							</ul>
						</div>
					</div>
					<div class="hidebot">
						<div class="bottop">
							<span>最近浏览的商品:</span>
							<a href="#">查看浏览历史></a>
						</div>
						<div class="botdown">
							<span>「暂无数据」</span>
						</div>
					</div>
				</div>
			</div>
			<div id="cart" class="gouwuche col-md-1 col-lg-1 col-sm-1 col-xs-6">
			
				<div class="chetu">
				</div>
				<a href="${pageContext.request.contextPath }/cart/cartUI">去购物车结算</a>
				<div class="jianleft">
				</div>
				
				<div class="num">
					<div class="numright">
					</div>
					<div class="numzi">
						<span>0</span>
					</div>
				</div>
				
			</div>
			</div>
			
		</div>
			</div>
			
			<!--
            	描述：导航条
            -->
			<div class="container-fluid">
				<nav class="navbar navbar-inverse">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="${pageContext.request.contextPath }/">首页</a>
						</div>

						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul id="menuId" class="nav navbar-nav">
								
							</ul>
							<form class="navbar-form navbar-right" role="search" 
							action="${pageContext.request.contextPath}/product/searchProduct?currPage=1" method="post">
								<div class="form-group">
									<input type="text" name="productName" class="form-control" placeholder="商品名">
								</div>
								<button type="submit" class="btn btn-default">搜索</button>
							</form>

						</div>
						<!-- /.navbar-collapse -->
					</div>
					<!-- /.container-fluid -->
				</nav>
			</div>
		
<script type="text/javascript">

	<!-- 通过ajax异步获取分类信息-->
		$(function(){
			
			$.get("${pageContext.request.contextPath}/category/getCategorys",function(data){
				
				if(data!=null && data.length>0){
					
					$(data).each(function(){
						
						$("#menuId").append($("<li><a href='${pageContext.request.contextPath}/product/getProductByPage?currPage=1&cid="+this.cid+"'>"+this.cname+"</a></li>"));
					});
				}
		
			},"json");
			
			$.get("${pageContext.request.contextPath}/cart/getCartNum",function(data){
				
				if(data=="0"){
					$(".numzi span").html(0);
					$("#cart").append("<div class='hideche'><div class='kongche'></div><span>购物车中还没有商品，赶紧选购吧！</span></div>");
				}else{
					$(".numzi span").html(data);
				}
				
			
			});
		});
</script>
