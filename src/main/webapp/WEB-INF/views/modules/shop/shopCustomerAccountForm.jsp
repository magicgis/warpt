<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户收款管理</title>
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
		<li><a href="${ctx}/shop/shopCustomerAccount/">客户收款列表</a></li>
		<li class="active"><a href="${ctx}/shop/shopCustomerAccount/form?id=${shopCustomerAccount.id}">客户收款<shiro:hasPermission name="shop:shopCustomerAccount:edit">${not empty shopCustomerAccount.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shop:shopCustomerAccount:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shopCustomerAccount" action="${ctx}/shop/shopCustomerAccount/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">销售客户：</label>
			<div class="controls">
				<sys:treeselect id="customer" name="customerId" value="${shopCustomerAccount.customerId}" labelName="customerName" labelValue="${shopCustomerAccount.customerName}"
					title="选择客户" url="/shop/shopCustomerInfo/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收款时间：</label>
			<div class="controls">
				<input name="businData" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${shopCustomerAccount.businData}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收款金额：</label>
			<div class="controls">
				<form:input path="factMoney" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:shopCustomerAccount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>