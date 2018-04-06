<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员项目充值管理</title>
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
		<li><a href="${ctx}/vip/vipProjectPay/">会员项目充值列表</a></li>
		<li class="active"><a href="${ctx}/vip/vipProjectPay/form?id=${vipProjectPay.id}">会员项目充值<shiro:hasPermission name="vip:vipProjectPay:edit">${not empty vipProjectPay.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="vip:vipProjectPay:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="vipProjectPay" action="${ctx}/vip/vipProjectPay/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">选择会员：</label>
			<div class="controls">
				<sys:treeselect id="vip" name="vipId" value="${vipProjectPay.vipId}" labelName="vipName" labelValue="${vipProjectPay.vipName}"
					title="选择会员" url="/vip/vipUserBase/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:input path="projectName" htmlEscape="false" maxlength="100"  class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目金额：</label>
			<div class="controls">
				<form:input path="projectMoeny" htmlEscape="false"  class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目次数：</label>
			<div class="controls">
				<form:input path="allNum" htmlEscape="false" maxlength="11"  class="required"/>
			</div>
		</div>
		<!-- <div class="control-group">
			<label class="control-label">可用次数：</label>
			<div class="controls">
				<form:input path="restNum" htmlEscape="false" maxlength="11"  class="required"/>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">已用次数：</label>
			<div class="controls">
				<form:input path="useNum" htmlEscape="false" maxlength="11"  class="required"/>
			</div>
		</div>-->
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="vip:vipProjectPay:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>