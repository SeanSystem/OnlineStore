<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="Nested Panel" data-options="width:'100%',minHeight:500,noheader:true,border:false" style="padding:10px;">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west',split:false" style="width:250px;padding:5px">
            <ul id="orderCategoryTree" class="easyui-tree" data-options="url:'http://localhost:8080/store/adminOrder/getOrderStates',animate: true,method : 'GET'">
            </ul>
        </div>
        <div data-options="region:'center'" style="padding:5px">
            <table class="easyui-datagrid" id="orderList" data-options="toolbar:orderListToolbar,singleSelect:false,collapsible:true,pagination:true,method:'get',pageSize:10,url:'http://localhost:8080/store/adminOrder/getOrderList',queryParams:{orderState:0}">
		    <thead>
		        <tr>
		           	<th data-options="field:'oid',width:100,align:'center',formatter:TAOTAO.formatOrderUrl">订单详情</th>
     		        <th data-options="field:'state',width:100,align:'center',formatter:TAOTAO.formatOrderStatus">订单状态</th>
		            <th data-options="field:'ordertime',width:130,align:'center',formatter:TAOTAO.formatDateTime">下单日期</th>
		        </tr>
		    </thead>
		</table>
        </div>
    </div>
</div>
<script type="text/javascript">
$(function(){
	var tree = $("#orderCategoryTree");
	var datagrid = $("#orderList");
	tree.tree({
		onClick : function(node){
			if(tree.tree("isLeaf",node.target)){
				datagrid.datagrid('reload', {
					orderState:node.id
		        });
			
			}
			
		}
	});
});
var orderListToolbar = [{
    text:'确认发货',
    iconCls:'icon-ok',
    handler:function(){
    	var ids = TT.getSelectionsOrderIds("#orderList");
    	var states = TT.getSelectionsOrderState("#orderList");
    	if(states.indexOf("1") < 0){
    		$.messager.alert('提示','只能对已付款的订单进行发货！');
    		return ;
    	}
    	if(ids.length == 0){
    		$.messager.alert('提示','未选中订单!');
    		return ;
    	}
    	if(ids.length > 32){
    		$.messager.alert('提示','只能选中一个订单!');
    		return ;
    	}
    	
    	$.messager.confirm('确认','确定发货ID为 '+ids+' 的订单吗？',function(r){
    	    if (r){
    	    	var params = {"oid":ids,"operator":"put"};
            	$.post("${pageContext.request.contextPath}/adminOrder/sendOrder",params, function(data){
        			if(data == "ok"){
        				$.messager.alert('提示','订单发货成功!',undefined,function(){
        					$("#orderList").datagrid("reload");
        				});
        			}else if(data == "error"){
        				$.messager.alert("提示","订单发货失败，请稍后再试！");
        			}
        		});
    	    }
    	});
    }
}

];
</script>