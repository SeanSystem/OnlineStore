<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>订单详情</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plugins/bootstrap/dist/css/bootstrap.min.css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		
		<script src="${pageContext.request.contextPath}/js/plugins/jquery/dist/jquery.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/plugins/angular/angular.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/selectAddress2.js" type="text/javascript"></script>
	    <script src="${pageContext.request.contextPath}/js/index.js"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
			td /*设置表格文字左右和上下居中对齐*/ 
			{  
			    vertical-align: middle!important;
			    text-align: center;  
			}
			
			.Caddress .add_mi{height: 106px;float: left;margin-right: 5px; background: url(${pageContext.request.contextPath}/img/mail.jpg) no-repeat;padding: 6px 17px;}
			.Caddress .add_mi p{font-size: 12px;line-height: 20px;margin-bottom: 5px;color: #666;width: 203px;}
			.open_new{width: 100%;text-align: right;}
			.open_btn{height: 30px; width: 120px; border-radius: 10px; background: red; color: white; outline: 0px; margin-bottom: 10px; border: 0px ;}
		
			
	.shade{width: 100%; height: 100%; overflow-y: hidden; position: fixed; z-index: 100; left:0; top: 0; background: black; opacity: 0.5;}
	.shade_content{width: 800px; border-radius: 10px; height: 500px; position: fixed; z-index: 101; top: 50%; left: 50%; margin-top: -250px; margin-left: -400px; background: #FFFFFF; display: none; }
	.shade_content_div{margin: 0px auto; text-align: center; height: 100%; width: 80%; margin-top: 20px;}
	.shade_title{font-size: 30px; text-align: center; font-weight: bold; font: "微软雅黑";}
	.btn_remove{border-radius: 5px; background: #31B0D5; color: white; width: 150px; height: 40px; border: 0px; outline: 0px; font-size: 15px;}
	.sub_set{border-radius: 5px; background: #31B0D5; color: white; width: 150px; height: 40px; border: 0px; outline: 0px; font-size: 15px;}
	.input_style{height: 30px; border-radius: 5px; width: 250px; outline: 0px; border: 1px solid #CCCCCC;}
	.span_style{font-size: 18px;}
	.shade_from{margin-top: 20px;}
	.shade_from .col-xs-12{margin-top: 20px;}
	.shade_colse{text-align: right; margin-top: 10px; }
	.shade_colse button{font-size: 20px; color: white; outline: 0px; margin-right: 10px; border-radius: 50px; background: red; height: 30px; width: 30px; border: 0px;}
</style>
		<script type="text/javascript">
			
			$(function bindClick(){
				$(".shade_content").hide();
				$(".shade").hide();
				$('.Caddress .add_mi').click(
						function() {
						$(this).css('background', 'url("${pageContext.request.contextPath}/img/mail_1.jpg") no-repeat').siblings('.add_mi').css('background', 'url("${pageContext.request.contextPath}/img/mail.jpg") no-repeat')
						$("#did").val($(this).attr("did"));		
				})
				
				
			});
			
			function onclick_close() {
				var shade_content = $(".shade_content");
				var shade = $(".shade");
				shade_content.hide();
				shade.hide();
			}

			function onclick_open() {
				$(".shade_content").show();
				$(".shade").show();
				var input_out = $(".input_style");
				for (var i = 0; i <= input_out.length; i++) {
					if ($(input_out[i]).val() != "") {
						$(input_out[i]).val("");
					}
				}
			}
			
		</script>
	</head>

	<body ng-controller="appCtrl">
	
	<div class="container-fluid">
	
			<!--
            	描述：菜单栏
            -->
			<jsp:include page="../jsp/head.jsp"></jsp:include>
		<div class="container">
			<div class="row">

				<div style="margin:0 auto;margin-top:10px;width:950px;">
					<strong>订单详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th colspan="5">订单编号:${order.oid } </th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach items="${order.orderitems}" var="item">
								<tr class="active">
									<td width="60" width="40%">
										<input type="hidden" name="id" value="22">
										<img src="${pageContext.request.contextPath}/${item.product.image}" width="70" height="60">
									</td>
									<td width="30%">
										<a target="_blank">${item.product.pname }</a>
									</td>
									<td width="20%">
										￥${item.product.shopPrice }
									</td>
									<td width="10%">
										${item.count }
									</td>
									<td width="15%">
										<span class="subtotal">￥${item.subtotal}</span>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div style="text-align:right;margin-right:120px;">
					订单金额: <strong style="color:#ff6600;">￥${order.totalprice }元</strong>
				</div>

			</div>

			<div class="container">
				<hr/>
				<form id="orderForm" class="form-horizontal" style="margin-top:5px;margin-left:150px;" action="${pageContext.request.contextPath }/order/pay" method="post">
					<input type="hidden" name="oid" value="${order.oid }">
					<input type="hidden" name="totalPrice" value="${order.totalprice }">
				<div id="Caddress" class="Caddress row">
						<div class="open_new">
							<a class="open_btn btn" href="javascript:void(0);" onclick="onclick_open();">使用新地址</a>
						</div>
						
					<c:if test="${empty ads }">
						<h1 id="topic">收货地址为空！请先添加收货地址~~</h1>
					</c:if>
					
					<c:if test="${not empty ads }">
						<c:forEach var="ad" items="${ads}" >
							<div id="add_mi" class="add_mi" did="${ad.id}">
								<%-- <input type="radio" name="did" value="${ad.id}" hidden="hidden"> --%>
								<p style="border-bottom:1px dashed #ccc;line-height:28px;">${ad.dname}(收)</p>
								<p>${ad.daddress }</p>
								<p>${ad.dtelphone }</p>
							</div>
						</c:forEach>
					</c:if>
					<input type="text" id="did" name="did" hidden="hidden"/>
				</div>
				
				<hr/>

				<div class="row" style="margin-top:5px;margin-left:150px;">
				
					<strong>选择银行：</strong>
					<p>
						<br/>
						<input type="radio" name="pd_FrpId" value="ICBC-NET-B2C" checked="checked" />工商银行
						<img src="${pageContext.request.contextPath}/bank_img/icbc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="BOC-NET-B2C" />中国银行
						<img src="${pageContext.request.contextPath}/bank_img/bc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="ABC-NET-B2C" />农业银行
						<img src="${pageContext.request.contextPath}/bank_img/abc.bmp" align="middle" />
						<br/>
						<br/>
						<input type="radio" name="pd_FrpId" value="BOCO-NET-B2C" />交通银行
						<img src="${pageContext.request.contextPath}/bank_img/bcc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="PINGANBANK-NET" />平安银行
						<img src="${pageContext.request.contextPath}/bank_img/pingan.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="CCB-NET-B2C" />建设银行
						<img src="${pageContext.request.contextPath}/bank_img/ccb.bmp" align="middle" />
						<br/>
						<br/>
						<input type="radio" name="pd_FrpId" value="CEB-NET-B2C" />光大银行
						<img src="${pageContext.request.contextPath}/bank_img/guangda.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="pd_FrpId" value="CMBCHINA-NET-B2C" />招商银行
						<img src="${pageContext.request.contextPath}/bank_img/cmb.bmp" align="middle" />

					</p>
					<hr/>
					<p style="text-align:right;margin-right:100px;">
						<a href="javascript:document.getElementById('orderForm').submit();">
							<img src="${pageContext.request.contextPath}/img/finalbutton.gif" width="204" height="51" border="0" />
						</a>
					</p>
				</form>
					
					<hr/>
				</div>
			</div>

		<!-- 引入底部文件 -->
		<jsp:include page="../jsp/foot.jsp"></jsp:include>
		<!-- 
        	描述：shade 遮罩层
        -->
     
		<div class="shade">
		</div>
		<!--
        	
        	描述：shade_content
        -->
		<div class="shade_content">
			<div class="col-xs-12 shade_colse">
				<button onclick="javascript:onclick_close();">x</button>
			</div>
			<div class="nav shade_content_div">
				<div class="col-xs-12 shade_title">
					新增收货地址
				</div>
				<div class="col-xs-12 shade_from">
					<form id="formData" action="#" method="post">
						<div class="col-xs-12">
							<!-- <span class="span_style" id="">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区</span>
							<input class="input_style" type="" name="" id="region" value="" placeholder="&nbsp;&nbsp;请输入您的所在地区" /> -->
						<span class="span_style">收货地址</span>
	                    <input select-address p="p" c="c" a="a" d="d" ng-model="x" placeholder="请选择所在地" name="daddress" type="text" class="input_style" />
	                   
						</div>
				
						<div class="col-xs-12">
							<span class="span_style" class="span_sty" id="">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
							<input class="input_style" type="" name="dname" id="name_" value="" placeholder="&nbsp;&nbsp;请输入您的姓名" />
						</div>
						<div class="col-xs-12">
							<span class="span_style" id="">手机号码</span>
							<input class="input_style" type="" name="dtelphone" id="phone" value="" placeholder="&nbsp;&nbsp;请输入您的手机号码" />
						</div>
						<div class="col-xs-12">
							<input class="btn_remove" type="button" id="" onclick="onclick_close();" value="取消" />
							<input type="button" class="sub_set" id="sub_setID" onclick="formSubmit()" value="提交" />
							
						</div>
					</form>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			function formSubmit(){
				//$("#formData").submit();
				var url = "${pageContext.request.contextPath}/address/addAddress";
				$.post(url,$("#formData").serialize(),function(data){
					if(data!=null && data!=""){	
						//关闭弹出层
						onclick_close();
						$("#topic").remove();
						//添加地址
						$("#Caddress").append("<div id='add_mi' class='add_mi' did='"+data.id+"'><p style='border-bottom:1px dashed #ccc;line-height:28px;'>"+data.dname+"(收)<p>"+data.daddress+"</p><p>"+data.dtelphone+"</p></div>");
						//重新绑定
						$('.Caddress .add_mi').click(
							function() {
							$(this).css('background', 'url("${pageContext.request.contextPath}/img/mail_1.jpg") no-repeat').siblings('.add_mi').css('background', 'url("${pageContext.request.contextPath}/img/mail.jpg") no-repeat');
							$("#did").val($(this).attr("did"));
							}
						);
					}
				},"json");
			}
		
		</script>
		</div>
	</body>

</html>