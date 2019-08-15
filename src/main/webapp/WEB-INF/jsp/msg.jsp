<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>消息提示</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
	type="text/javascript"></script>
</head>

<body>
	

		
		<div class="container-fluid">
		<!--
            	描述：菜单栏
            -->
		<jsp:include page="../jsp/head.jsp"></jsp:include>
			<div class="container">
				<h1>${msg }</h1>
			</div>
			
			<div class="container-fluid">
				<!-- 引入底部文件 -->
				<jsp:include page="../jsp/foot.jsp"></jsp:include>
			</div>
		</div>

</body>

</html>