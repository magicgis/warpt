<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商付款管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/shop/shopSupplierAccount/">供应商付款列表</a></li>
		<li class="active"><a href="${ctx}/shop/shopSupplierAccount/form?id=${shopSupplierAccount.id}">供应商付款<shiro:hasPermission name="shop:shopSupplierAccount:edit">${not empty shopSupplierAccount.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shop:shopSupplierAccount:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shopSupplierAccount" action="${ctx}/shop/shopSupplierAccount/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">供应商：</label>
			<div class="controls">
				<sys:treeselect id="supplier" name="supplierId" value="${shopSupplierAccount.supplierId}" labelName="supplierName" labelValue="${shopSupplierAccount.supplierName}"
					title="选择供应商" url="/shop/shopPurchaseSupplier/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务时间：</label>
			<div class="controls">
				<form:input path="businData" htmlEscape="false" maxlength="19" class="required" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款金额：</label>
			<div class="controls">
				<form:input path="factMoney" htmlEscape="false" class="required  number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:shopSupplierAccount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>