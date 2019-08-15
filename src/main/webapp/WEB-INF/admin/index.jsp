<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
  <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <!-- Bootstrap -->
  <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/userManage.css" rel="stylesheet"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>商城后台管理系统</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/taotao.css" />
<script src="${pageContext.request.contextPath}/js/vue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>	
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
<style type="text/css">
	.content {
		padding: 10px 10px 10px 10px;
	}
	
		body {
		margin-top: 20px;
		margin: 0 auto;
	}
	
	
</style>
</head>
<body class="easyui-layout">
    <div data-options="region:'west',title:'菜单',split:true" style="width:180px;">
    	<ul id="menu" class="easyui-tree" style="margin-top: 10px;margin-left: 5px;">
    		<c:if test="${fn:containsIgnoreCase(admin.authority, 'a')}">
    		<li>
    			<span>用户管理</span>
         		<ul>
         		<c:if test="${fn:containsIgnoreCase(admin.authority, 'a1')}">
	         		<li data-options="attributes:{'url':'user-add'}">用户新增</li>
	         	</c:if>
	         	<c:if test="${fn:containsIgnoreCase(admin.authority, 'a2')}">
	         		<li data-options="attributes:{'url':'user-manage'}">权限管理</li>
	         	</c:if>
	         	</ul>
    		</li>
    		</c:if>
    		<c:if test="${fn:containsIgnoreCase(admin.authority, 'b')}">
    		<li>
    			<span>商品分类管理</span>
         		<ul>
         		<c:if test="${fn:containsIgnoreCase(admin.authority, 'b1')}">
         			<li data-options="attributes:{'url':'category-add'}">分类新增</li>	
         		</c:if> 
         		<c:if test="${fn:containsIgnoreCase(admin.authority, 'b2')}">        		
	         		<li data-options="attributes:{'url':'product-category'}">分类管理</li>
	         	</c:if>	         		
	         	</ul>
    		</li>
    		</c:if>
    		<c:if test="${fn:containsIgnoreCase(admin.authority, 'c')}">
         	<li>
         		<span>商品管理</span>
         		<ul>
         		<c:if test="${fn:containsIgnoreCase(admin.authority, 'c1')}">
	         		<li data-options="attributes:{'url':'product-add'}">商品新增</li>
	         	</c:if>
<!--  	         		<li data-options="attributes:{'url':'item-list'}">查询商品</li>-->
				<c:if test="${fn:containsIgnoreCase(admin.authority, 'c2')}">
 	         		<li data-options="attributes:{'url':'product-manage'}">商品管理</li>
 				</c:if>
<!-- 	         		<li data-options="attributes:{'url':'item-param-list'}">规格参数</li>
 -->	         	</ul>
         	</li>
         	</c:if>
         	<c:if test="${fn:containsIgnoreCase(admin.authority, 'd')}">
         	<li>
         		<span>订单管理</span>
         		<ul>
         		<c:if test="${fn:containsIgnoreCase(admin.authority, 'd1')}">
	         		<li data-options="attributes:{'url':'order-manage'}">订单管理</li>
	         	</c:if>
	         	</ul>
         	</li>
         	</c:if>
         	<c:if test="${fn:containsIgnoreCase(admin.authority, 'e')}">
         	<li>
         		<span>网站内容管理</span>
         		<ul>
         		<c:if test="${fn:containsIgnoreCase(admin.authority, 'e1')}">
	         		<li data-options="attributes:{'url':'index-product-add'}">首页商品新增</li>
	         	</c:if>
	         	<c:if test="${fn:containsIgnoreCase(admin.authority, 'e2')}">
	         		<li data-options="attributes:{'url':'index-product-manage'}">首页商品管理</li>
	         	</c:if>
	         	</ul>
         	</li>
         	</c:if>
         </ul>
    </div>
    <div data-options="region:'center',title:''">
    	<div id="tabs" class="easyui-tabs">
		    <div title="首页" style="padding:20px;text-align:center;">
		     	<c:if test="${empty admin.username}">
		        	<h1>您尚未登陆,请<a href="http://localhost:8080/store/admin">点我</a>登陆！</h1>
		        </c:if>
		    	<c:if test="${not empty admin.username}">
		        	<h1>您好,${admin.username}。欢迎使用商城后台管理系统！</h1>
		        </c:if>
		       
		    </div>
		</div>
    </div>
    
<script type="text/javascript">
$(function(){
	$('#menu').tree({
		onClick: function(node){
			if($('#menu').tree("isLeaf",node.target)){
				var tabs = $("#tabs");
				var tab = tabs.tabs("getTab",node.text);
				if(tab){
					tabs.tabs("select",node.text);
				}else{
					tabs.tabs('add',{
					    title:node.text,
					    href: node.attributes.url,
					    closable:true,
					    bodyCls:"content"
					});
				}
			}
		}
	});
});
</script>
</body>
</html>