<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="modal1" class="modal fade in" tabindex="-1" role="dialog"><!--半透明的黑色遮罩层-->
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <span class="close" data-dismiss="modal">&times;</span>
        <div class="modal-title">权限管理</div>
      </div>
      <div id="qx" class="modal-body">
        <form  action="" >
          <div class="form-group">
            <label for=""><input v-model="arr" value="a" type="checkbox" name="" id="">用户管理</label>
            <div class="checkbox">
            <label>
              <input v-model="arr" type="checkbox" value="a1" name="" id="">用户新增
            </label>
            <label>
              <input v-model="arr" type="checkbox" value="a2" name="" id="">权限管理
            </label>
              </div>
          </div>
          <div class="form-group">
            <label for=""><input v-model="arr" value="b" type="checkbox" name="" id="">商品分类管理</label>
            <div class="checkbox">
              <label>
                <input v-model="arr" value="b1" type="checkbox" name="" id="">分类新增
              </label>
              <label>
                <input v-model="arr" value="b2" type="checkbox" name="" id="">分类管理
              </label>
            </div>
          </div>
          <div class="form-group">
            <label for=""><input v-model="arr" value="c" type="checkbox" name="" id="">商品管理</label>
            <div class="checkbox">
              <label>
                <input v-model="arr" value="c1" type="checkbox" name="" id="">商品新增
              </label>
              <label>
                <input v-model="arr" value="c2" type="checkbox" name="" id="">商品管理
              </label>
            </div>
          </div>
          <div class="form-group">
            <label for=""><input v-model="arr" value="d" type="checkbox" name="" id="">订单管理</label>
            <div class="checkbox">
              <label>
                <input v-model="arr" value="d1" type="checkbox" name="" id="">订单管理
              </label>
            </div>
          </div>
          <div class="form-group">
            <label for=""><input v-model="arr" value="e" type="checkbox" name="" id="">网站内容管理</label>
            <div class="checkbox">
              <label>
                <input v-model="arr" value="e1" type="checkbox" name="" id="">首页商品新增
              </label>
              <label>
                <input v-model="arr" value="e2" type="checkbox" name="" id="">首页商品管理
              </label>
            </div>
          </div>

          <!--<input type="submit">-->
        </form>
        <button @click.prevent.stop="send(arr,id)" id="login" class="btn btn-success" type="submit">确定</button>
      </div>
      <div class="modal-footer">用户管理</div>
    </div>
  </div>
</div>
<div class="content">
<div id="qx_ct" class="table-wrap" v-cloak>
<!-- <div class="th">
<table class="mode_two">
  <colgroup>
    <col class="add-col">
    <col class="del-col">
    <col class="cx-col">
    <col class="up-col">
  </colgroup>
  <thead>
  <tr>
    <td valign="center">
      <a @click="change(re1)" class="btn btn-success btn-sm">用户添加</a>
    </td>
    <td>
    </td>
    <td>
    </td>
    <td>
    </td>
  </tr>
  </thead>
</table>
</div> -->
<table class="table table-striped table-hover">
  <colgroup>
    <col class="num-col">
    <col class="nam-col">
    <col class="tim-col">
    <col class="ct-col">
  </colgroup>
  <thead>
  <tr>
    <th>标号</th>
    <th>用户名</th>
    <th>创建时间</th>
    <th>操作</th>
  </tr>
  </thead>
  <tbody>
  <tr v-for="(item,index) in items">
    <td>{{index+1}}</td>
    <td>{{item.username}}</td>
    <td>{{time2date(item.createTime)}}</td>
    <td>
      <a href="" @click.prevent.stop="post(item.id)"   class="btn btn-success btn-xs">权限</a>
      <a href="" @click.prevent.stop="alert(index,item.id)" class="btn btn-danger btn-xs">删除</a>
    </td>
  </tr>
  </tbody>
</table>
</div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<!-- <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
 --><!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->

</body>
<script>
  $(function () {
   var app = new Vue({
     el:'#qx_ct',
     data:{
       items:[]
     },
     methods:{
       change: function (arr) {
         this.items = arr;
       },
       alert: function (index,id) {
         confirm('是否确定删除？');
         var url = "${pageContext.request.contextPath}/admin/deleteAdmin";
         var params = {"id":id};
         $.post(url,params,function(data){
        	 if(data == "ok"){
        		 $.messager.alert("提示","删除成功!");
        		app.items.splice(index,1); 
        	 }else {
        		 $.messager.alert("提示","删除失败，请稍后再试!");
        	 }
         });
       },
       time2date:function(timestamp){
    	   var oDate = new Date();
    	   oDate.setTime(timestamp);

    	   return oDate.getFullYear()+'-'+toDou(oDate.getMonth()+1)+'-'+toDou(oDate.getDate())+' '+toDou(oDate.getHours())+':'+toDou(oDate.getMinutes())+':'+toDou(oDate.getSeconds());
    	   },
    	   toDou:function (n){
    		   return n<10?'0'+n:''+n;
    		   },
    		   post:function(id){
    			   var url = "${pageContext.request.contextPath}/admin/getAuthority";
    			   app2.id = id;
    			   var params = {"id":id};
    			  $.get(url,params,function(data){
    				  if(data != null){
    				  	app2.arr = data.split(",");
    				  	$('#modal1').modal('show');
    				  }
    			  }); 
    		   }	   
     }
   });
   var app2 = new Vue({
	   el:'#qx',
	   data:{
		   'id':'',
		   arr:[]
	   },
   methods:{
	   send:function(ar,id){
		   var str = ar.join(',');
		   var url = "${pageContext.request.contextPath}/admin/addAuthority";
		   var params = {"authority":str,"id":id};
		   $.post(url,params,function(data){
			   if(data == "ok") {
				   $.messager.alert("提示","添加权限成功！");
			   }else {
				   $.messager.alert("提示","添加权限失败，请稍后再试！");
			   }
			   $('#modal1').modal('hide');
		   });
	   }
   }
   })
   var url = "${pageContext.request.contextPath}/admin/getUsers";
   $.get(url,function(data){
	   if(data != null){
		   app.items = data;
	   }
   });
   
   
   function toDou(n){
	   return n<10?'0'+n:''+n;

	   }

  })
</script>