<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="Nested Panel" data-options="width:'100%',minHeight:500,noheader:true,border:false" style="padding:10px;">
    <div class="easyui-layout" data-options="fit:true">
        <!-- <div data-options="region:'west',split:false" style="width:250px;padding:5px">
            <ul id="contentCategoryTree" class="easyui-tree" data-options="url:'/content/category/list',animate: true,method : 'GET'">
            </ul>
        </div> -->
        <div data-options="region:'center'" style="padding:5px">
            <table class="easyui-datagrid" id="contentList" data-options="toolbar:contentListToolbar,singleSelect:false,collapsible:true,pagination:true,method:'get',pageSize:20,url:'http://localhost:8080/store/admin/getProduct'">
		    <thead>
		        <tr>
		            <th data-options="field:'pid',width:120,align:'center'">商品ID</th>
		            <th data-options="field:'pname',width:120,align:'center'">商品名</th>
		            <th data-options="field:'image',width:120,align:'center',formatter:TAOTAO.formatUrl">商品图片</th>
		            <th data-options="field:'price',width:100,align:'center',formatter:TAOTAO.formatPrice">价格</th>
		            <th data-options="field:'status',width:100,align:'center',formatter:TAOTAO.formatItemStatus">商品状态</th>
		            <th data-options="field:'type',width:100,align:'center',formatter:TAOTAO.formatType">商品类型</th>
		            <th data-options="field:'createTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
		        </tr>
		    </thead>
		</table>
        </div>
    </div>
</div>
<script type="text/javascript">
/* $(function(){
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
}); */
var contentListToolbar = [{
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
            	$.post("${pageContext.request.contextPath}/admin/putOrdownProduct",params, function(data){
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
            	$.post("${pageContext.request.contextPath}/admin/putOrdownProduct",params, function(data){
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
}];
</script>