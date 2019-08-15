<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div style="padding:10px 10px 10px 10px">
	<form id="categoryAddForm" class="itemForm" method="post">
	    <table cellpadding="5">
	        <tr>
	            <td>商品ID:</td>
	            <td>
	            	<input class="easyui-textbox" type="text" name="pid" data-options="required:true" style="width: 280px;"></input>
	            </td>
	        </tr>
	       
	        <tr>
	            <td>商品类型:</td>
	            <td>
	            	<input type="radio" name="type" value="1" checked>最新商品
	              	<input type="radio" name="type" value="2">热门商品
	            </td>
	        </tr>
	    </table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">添加</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">
	
	//提交表单
	function submitForm(){
		//有效性验证
		if(!$('#categoryAddForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		//ajax的post方式提交表单
		//$("#categoryAddForm").serialize()将表单序列号为key-value形式的字符串
		$.post("${pageContext.request.contextPath}/admin/addProduct",$("#categoryAddForm").serialize(), function(data){
			if(data == "ok"){
				$('#categoryAddForm').form('reset');
				$.messager.alert('提示','添加成功!');
			}else{
				$.messager.alert('提示','添加失败，请确认商品ID是否正确！');
			}
		});
	}
	
	function clearForm(){
		$('#categoryAddForm').form('reset');
		
	}
</script>
