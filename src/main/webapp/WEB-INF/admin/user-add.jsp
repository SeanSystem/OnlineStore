<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div style="padding:10px 10px 10px 10px">
	<form id="categoryAddForm" class="itemForm" method="post">
	    <table cellpadding="5">
	        <tr>
	            <td>用户名:</td>
	            <td>
	            	<input class="easyui-textbox" type="text" name="username" data-options="required:true" style="width: 280px;"></input>
	            </td>
	        </tr>
	       
	        <tr>
	            <td>密码:</td>
	            <td>
	            <input class="easyui-textbox" type="password" name="password" data-options="required:true" style="width: 280px;"></input>
	            </td>
	        </tr>
	        <tr>
	            <td>确认密码:</td>
	            <td>
	            <input class="easyui-textbox" type="password" name="repassword" data-options="required:true" style="width: 280px;"></input>
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
		var password = $("input[name='password']").val();
		var repassword = $("input[name='repassword']").val();
		if(password != repassword){
			$.messager.alert("提示","两次填写的密码不一致！");
			return ;
		}
		//ajax的post方式提交表单
		//$("#categoryAddForm").serialize()将表单序列号为key-value形式的字符串
		$.post("${pageContext.request.contextPath}/admin/addUser",$("#categoryAddForm").serialize(), function(data){
			if(data == "ok"){
				$('#categoryAddForm').form('reset');
				$.messager.alert('提示','添加成功!');
			}else{
				$.messager.alert('提示','添加失败，请稍后再试');
			}
		});
	}
	
	function clearForm(){
		$('#categoryAddForm').form('reset');
		
	}
</script>
