<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!doctype html>

<html>

<head>
<meta charset="utf-8" />

<title>商品搜索</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/plist20131112.css"
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
	width: 100%;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
<script type="text/javascript">
			$(function(){
				var price = "${price}";
				var order = "${order}";
				if(price == null || price == ""){
					$("#price_id").val("0-999999");
				}else {
					$("#price_id").val(price);
				}
				if(order == null || price == ""){
					$("#order_id").val("up");
				}else {
					$("#order_id").val(order);
				}
			})
		</script>
</head>

<body>
	<div class="container-fluid">
		<!--
           	描述：菜单栏
           -->
		<jsp:include page="../jsp/head.jsp"></jsp:include>

		<div class="row" style="width: 1210px; margin: 0 auto;">

			<div class="col-md-12">
				<form id="searchForm"
					action="${pageContext.request.contextPath }/product/searchProduct"
					method="post">
					<input type="hidden" name="currPage" value='1'>
					<ol class="breadcrumb">
						<li>商品名：<input type="text" name="productName"
							value="${productName }"> 价格区间： <select name="price"
							id="price_id">
								<option value="0-999999">-请选择-</option>
								<option value="0-500">0-499</option>
								<option value="500-1000">500-999</option>
								<option value="1000-2000">1000-1999</option>
								<option value="2000-3000">2000-2999</option>
								<option value="3000-6000">3000-5999</option>
								<option value="6000">6000以上</option>
						</select> 价格排序： <select name="order" id="order_id">
								<option value="up">升序</option>
								<option value="down">降序</option>
						</select> <input type="submit" value="筛选">
						</li>
					</ol>
				</form>
			</div>
			<c:forEach items="${bean.list }" var="p">
				<div class="col-md-2 col-xs-6 col-sm-4">
					<a
						href="${pageContext.request.contextPath }/product/getProductById?pid=${p.pid}">
						<img src="${pageContext.request.contextPath}/${p.image}"
						width="170" height="170" style="display: inline-block;">
					</a>
					<p>
						<a href="product_info.html" style='color: green'>${fn:substring(p.pname,0,12) }...</a>
					</p>
					<p>
						<font color="#FF0000">商城价：&yen;${p.shopPrice}</font>
					</p>
				</div>

			</c:forEach>
		</div>

		<!--分页 -->
		<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
			<c:if test="${empty bean }">
				<div class="container">
					<h2>该分类暂无商品~~</h2>
				</div>
			</c:if>
			<c:if test="${not empty bean  }">
				<ul class="pagination" style="text-align: center; margin-top: 10px;">
					<c:if test="${bean.currPage-1==0 }">
						<li class="disabled"><a href="javascript:void(0);"
							aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
					</c:if>
					<c:if test="${bean.currPage-1!=0 }">
						<li class=""><a href="javascript:void(0);"
							onclick="formSubmit(${currPage - 1})" aria-label="Previous">
								<span aria-hidden="true">&laquo;</span>
						</a></li>
					</c:if>
					<fmt:parseNumber integerOnly="true"
						value="${(bean.currPage/bean.pageSize)}" var="z"></fmt:parseNumber>

					<c:set var="bg"
						value="${bean.currPage%bean.pageSize==0?bean.currPage-bean.pageSize+1:z*bean.pageSize+1}"></c:set>

					<c:set var="ed"
						value="${bg+bean.pageSize-1>bean.totalPage?bean.totalPage:bg+bean.pageSize-1}"></c:set>

					<c:forEach begin="${bg}" end="${ed}" var="n">

						<c:if test="${n==bean.currPage }">
							<li class="active"><a href="javascript:void(0);">${n}</a></li>
						</c:if>
						<c:if test="${n!=bean.currPage }">
							<li class=""><a href="javascript:void(0);"
								onclick="formSubmit(${n})">${n}</a></li>
						</c:if>

					</c:forEach>

					<c:if test="${bean.currPage+1>bean.totalPage }">
						<li class="disabled"><a href="javascript:void(0);"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:if>
					<c:if test="${bean.currPage+1<=bean.totalPage }">
						<li class=""><a href="javascript:void(0);"
							onclick="formSubmit(${currPage + 1})" aria-label="Next"> <span
								aria-hidden="true">&raquo;</span>
						</a></li>
					</c:if>
				</ul>
			</c:if>


		</div>
		<!-- 分页结束=======================>


		<!-- 引入底部文件 -->
		<jsp:include page="../jsp/foot.jsp"></jsp:include>
	</div>
	<script type="text/javascript">
		function formSubmit(currPage){
				$("input[name='currPage']").val(currPage);
				$("#searchForm").submit();
		}
	</script>
</body>

</html>