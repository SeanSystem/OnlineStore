<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title>订单详情</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<style>
	.carousel-inner .item img {
		width: 100%;
		height: 300px;
	}
	td /*设置表格文字左右和上下居中对齐*/ 
	{  
	    vertical-align: middle!important;
	    text-align: center;  
	} 
</style>
</head>
<body>
		<div class="container-fluid">
		
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>订单详情</strong>
					<table class="table table-bordered">
							<tbody>
								<tr class="success">
									<th colspan="5">
										订单编号:${order.oid } 
									
									</th>
								</tr>
								<tr class="success">
									<th colspan="5">
									${order.did}
									</th>
								</tr>
								<tr class="warning">
									<th colspan="5">
										状态：
									<c:if test="${order.state==0 }">
										未付款
									</c:if>
									<c:if test="${order.state==1 }">
										已付款
									</c:if>
									<c:if test="${order.state==2 }">
										待收货
									</c:if>
									<c:if test="${order.state==3 }">
										已完成
									</c:if>
									</th>
								</tr>
								<tr class="warning">
									<th>图片</th>
									<th>商品</th>
									<th>价格</th>
									<th>数量</th>
									<th>小计</th>
								</tr>
								<c:forEach items="${order.orderitems }" var="oi">
									<tr class="active">
										<td width="60" width="40%">
											<input type="hidden" name="id" value="22">
											<img src="${pageContext.request.contextPath}/${oi.product.image}" width="70" height="60">
										</td>
										<td width="30%">
											<a target="_blank">${oi.product.pname }</a>
										</td>
										<td width="20%">
											￥${oi.product.shopPrice }
										</td>
										<td width="10%">
											${oi.count }
										</td>
										<td width="15%">
											<span class="subtotal">￥${oi.subtotal }</span>
										</td>
									</tr>
								</c:forEach>
							</tbody>
					</table>
				</div>
			</div>
			</body>
</html>