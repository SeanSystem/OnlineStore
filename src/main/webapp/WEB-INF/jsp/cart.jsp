<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>购物车</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

.container .row div {
	/* position:relative;
	 float:left; */
	
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
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
		<!--
	            	描述：菜单栏
	            -->
		<jsp:include page="../jsp/head.jsp"></jsp:include>
		<div class="row">
			<c:if test="${empty cart.items }">
				<div class="container">
					<h1>购物车中什么都没有，快去浏览商品购买吧~~</h1>
				</div>
			</c:if>

			<c:if test="${not empty cart.items }">
				<div style="margin: 0 auto; margin-top: 10px; width: 950px;">
					<strong style="font-size: 16px; margin: 5px 0;">订单详情</strong>

					<table class="table table-bordered">

						<tbody>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>

							<c:forEach items="${cart.items }" var="item">
								<tr class="active">

									<td width="60" width="40%">
<!-- 									<input type="hidden" name="id" value="22">
 -->										 <img
										src="${pageContext.request.contextPath}/${item.product.image }"
										width="70" height="60">
									</td>
									
									<td width="30%"><a target="_blank">${item.product.pname }</a>
									</td>
									
									<td width="20%">￥${item.product.shopPrice }</td>
									
									<td width="20%">
										<%-- <input type="text" name="quantity" value="${item.count }" maxlength="4" size="10" readonly="readonly">
										 --%>
										 <a  class="btn btn-default" style="width:34px!important;" onclick="reduceCount(this,'${item.pid}')">-</a>
										<input type="text" style="width:30px;text-indent:7px;height:31px;" name="count" maxlength="4" size="10" value="${item.count }" disabled="disabled">
										<a  class="btn btn-default" onclick="addCount(this,'${item.pid}')">+</a>
									</td>
									
									<td width="15%"><span class="subtotal">${item.subtotal }</span>
									</td>
									
									<td><a href="javascript:void(0);" class="delete"
										onclick="removeCarItem('${item.pid}')">删除</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
		</div>

		<div style="margin-right: 130px;">
			<div style="text-align: right;">
				<!-- <em style="color: #ff6600;"> 登录后确认是否享有优惠&nbsp;&nbsp; </em> -->
					 商品数: <em id="totalcount"
					style="color: #ff6600;">${cart.totalcount}</em>&nbsp; 总计: <strong id="totalprice"
					style="color: #ff6600;">￥${cart.totalprice}元</strong>
			</div>
			<div
				style="text-align: right; margin-top: 10px; margin-bottom: 10px;">
				<a href="javascript:void(0);" id="clear" class="clear"
					onclick="clearCart()">清空购物车</a> <a
					href="${pageContext.request.contextPath }/order/makeOrder"> <input
					type="submit" width="100" value="提交订单" name="submit"
					border="0 style=" background:
					url('${pageContext.request.contextPath}/images/register.gif') no-repeatscroll 0 0 rgba(0, 0, 0, 0);
						height:35px;width:100px;color:white;">
				</a>
			</div>
		</div>
		</c:if>
	</div>

	<!-- 引入底部文件 -->
	<jsp:include page="../jsp/foot.jsp"></jsp:include>


	<script type="text/javascript">
		function removeCarItem(pid) {

			if (confirm("确定要移除此项！")) {

				location.href = "${pageContext.request.contextPath}/cart/removeCartitem?pid="
						+ pid;
				
			}
		}

		function clearCart() {

			if (confirm("确定清空购物车！")) {

				location.href = "${pageContext.request.contextPath}/cart/clearCart";
			}
		}
		
		function reduceCount(obj,pid){
			
			//数量框
			$input = $(obj).next();
			$count = parseInt($input.val());
			//小计
			$subtotal = $(obj).parent().next().children();
			
 			if($count > 1){
 				$num = $count-1;
 				$.post("${pageContext.request.contextPath}/cart/updateProductCount",{"pid":pid,"count":$num},
 				function(data){
 					
 					 if(data!=null){
 						
 						 $result = data.split(",");
 						//修改商品数量
 						$input.val($num);
 						//修改商品的总金额
						$("#totalprice").html($result[0]);
						//修改小计金额
						$subtotal.html($result[1]);
						//修改商品总数
						$("#totalcount").html($result[2]);
						
 					}
 					
 					 
 				});
 				
 			}
 		}
 		
		
 		function addCount(obj,pid){
 			
 			//数量框
			$input = $(obj).prev();
			$count = parseInt($input.val());
			
			//小计
			$subtotal = $(obj).parent().next().children();
			
			//修改后的商品数量
			$num = $count+1;
			
			$.post("${pageContext.request.contextPath}/cart/updateProductCount",{"pid":pid,"count":$num},
			function(data){
				
				 if(data!=null){
					
					 $result = data.split(",");
					//修改商品数量
					$input.val($num);
					//修改商品的总金额
					$("#totalprice").html($result[0]);
					//修改小计金额
					$subtotal.html($result[1]);
					//修改商品总数
					$("#totalcount").html($result[2]);
				}	
				 
			});
						
 		}
 		
 		
	</script>
</body>

</html>