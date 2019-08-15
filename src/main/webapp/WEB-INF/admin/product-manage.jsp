<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="Nested Panel" data-options="width:'100%',minHeight:500,noheader:true,border:false" style="padding:10px;">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west',split:false" style="width:250px;padding:5px">
            <ul id="contentCategoryTree" class="easyui-tree" data-options="url:'http://localhost:8080/store/adminCategory/getCategorys',animate: true,method : 'GET'">
            </ul>
        </div>
        <div data-options="region:'center'" style="padding:5px">
            <table class="easyui-datagrid" id="contentList" data-options="toolbar:contentListToolbar,singleSelect:false,collapsible:true,pagination:true,method:'get',pageSize:10,url:'http://localhost:8080/store/adminProduct/getProductList',queryParams:{categoryId:0}">
		    <thead>
		        <tr>
		            <th data-options="field:'pid',width:100,align:'center'">ID</th>
		            <th data-options="field:'pname',width:100,align:'center'">商品标题</th>
		            <th data-options="field:'sellpoint',width:100">商品卖点</th>
		            <th data-options="field:'marketPrice',width:100,align:'center',formatter:TAOTAO.formatPrice">市场价</th>
		            <th data-options="field:'shopPrice',width:100,align:'center',formatter:TAOTAO.formatPrice">商城价</th>
		            <th data-options="field:'count',width:100,align:'center'">商品数量</th>
		            <th data-options="field:'pDesc',width:100">商品描述</th>
		            <th data-options="field:'isHot',width:100,align:'center',formatter:TAOTAO.formatIsHot">是否热门</th>
		            <th data-options="field:'pflag',width:100,align:'center',formatter:TAOTAO.formatItemStatus">商品状态</th>
		            <th data-options="field:'image',width:50,align:'center',formatter:TAOTAO.formatUrl">商品图片</th>
		            <th data-options="field:'pdate',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
		        </tr>
		    </thead>
		</table>
        </div>
    </div>
</div>
<script type="text/javascript">
$(function(){
	var tree = $("#contentCategoryTree");
	var datagrid = $("#contentList");
	tree.tree({
		onClick : function(node){
			if(tree.tree("isLeaf",node.target)){
				datagrid.datagrid('reload', {
					categoryId :node.id
		        });
			}
		}
	});
});
var contentListToolbar = [{
    text:'编辑',
    iconCls:'icon-edit',
    handler:function(){
    	var ids = TT.getSelectionsIds("#contentList");
    	if(ids.length == 0){
    		$.messager.alert('提示','必须选择一个内容才能编辑!');
    		return ;
    	}
    	if(ids.indexOf(',') > 0){
    		$.messager.alert('提示','只能选择一个内容!');
    		return ;
    	}
		TT.createWindow({
			url : "product-edit",
			onLoad : function(){
				var data = $("#contentList").datagrid("getSelections")[0];
				$("#contentEditForm").form("load",data);
				
				// 实现图片
				if(data.image){
					$("#contentEditForm [name=image]").after("<a href='"+data.image+"' target='_blank'><img src='"+data.image+"' width='80' height='50'/></a>");	
				}
				
				contentEditEditor.html(data.pDesc);
			}
		});    	
    }
},{
    text:'删除',
    iconCls:'icon-cancel',
    handler:function(){
    	var ids = TT.getSelectionsIds("#contentList");
    	if(ids.length == 0){
    		$.messager.alert('提示','未选中商品!');
    		return ;
    	}
    	$.messager.confirm('确认','确定删除ID为 '+ids+' 的商品吗？',function(r){
    	    if (r){
    	    	var params = {"ids":ids};
            	$.post("${pageContext.request.contextPath}/adminProduct/deleteProduct",params, function(data){
        			if(data == "ok"){
        				$.messager.alert('提示','删除内容成功!',undefined,function(){
        					$("#contentList").datagrid("reload");
        				});
        			}else if(date == "error"){
        				$.messager.alert("提示","删除失败，请稍后再试！");
        			}
        		});
    	    }
    	});
    }
},{
    text:'上架',
    iconCls:'icon-remove',
    handler:function(){
    	var ids = TT.getSelectionsIds("#contentList");
    	if(ids.length == 0){
    		$.messager.alert('提示','未选中商品!');
    		return ;
    	}
    	$.messager.confirm('确认','确定上架ID为 '+ids+' 的内容吗？',function(r){
    	    if (r){
    	    	var params = {"ids":ids,"operator":"put"};
            	$.post("${pageContext.request.contextPath}/adminProduct/putOrdownProduct",params, function(data){
        			if(data == "ok"){
        				$.messager.alert('提示','商品上架成功!',undefined,function(){
        					$("#contentList").datagrid("reload");
        				});
        			}else if(data == "error"){
        				$.messager.alert("提示","商品上架失败，请稍后再试！");
        			}
        		});
    	    }
    	});
    }
},{
    text:'下架',
    iconCls:'icon-remove',
    handler:function(){
    	var ids = TT.getSelectionsIds("#contentList");
    	if(ids.length == 0){
    		$.messager.alert('提示','未选中商品!');
    		return ;
    	}
    	$.messager.confirm('确认','确定下架ID为 '+ids+' 的商品吗？',function(r){
    	    if (r){
    	    	var params = {"ids":ids,"operator":"down"};
            	$.post("${pageContext.request.contextPath}/adminProduct/putOrdownProduct",params, function(data){
        			if(data == "ok"){
        				$.messager.alert('提示','商品下架成功!',undefined,function(){
        					$("#contentList").datagrid("reload");
        				});
        			}else if(data == "error"){
        				$.messager.alert("提示","商品下架失败，请稍后再试！");
        			}
        		});
    	    }
    	});
    }
}

];
</script>