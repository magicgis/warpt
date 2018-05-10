<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品导入</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
	    function saveImp(){
	        var formData = new FormData($("#inputForm")[0]);  
	        var ajaxUrl = "${ctx}/shop/shopProduct/saveImpProduct";
	        //$('#uploadPic').serialize() 无法序列化二进制文件，这里采用formData上传
	        //需要浏览器支持：Chrome 7+、Firefox 4+、IE 10+、Opera 12+、Safari 5+。
	        $.ajax({
	            type: "POST",
	            //dataType: "text",
	            url: ajaxUrl,
	            data: formData,
	            async: false,  
	            cache: false,  
	            contentType: false,  
	            processData: false,
	            success: function (data) {
	            	alert(data);
	            },
	            error: function(data) {
	                alert(data.responseText);
	             }
	        });
	        return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/shop/shopProduct/">商品基本信息列表</a></li>
		<shiro:hasPermission name="shop:shopProduct:edit"><li><a href="${ctx}/shop/shopProduct/form">商品基本信息添加</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/shop/shopProduct/formImp">商品批量导入</a></li>
	</ul><br/>
	<form:form id="inputForm" action="#" enctype="multipart/form-data" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">导入模板（点击可下载）：</label>
			<div class="controls">
				<a href="${ctxStatic}/modules/shop/file/商品信息模板.xls" target="_blank">商品信息模板.xls</a>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选择附件：</label>
			<div class="controls">
				<input type="file" name="impfile">
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="导 入" onclick="saveImp()"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>

</html>