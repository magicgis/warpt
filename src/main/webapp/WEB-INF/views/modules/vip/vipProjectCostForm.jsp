<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员项目消费管理</title>
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
		<li><a href="${ctx}/vip/vipProjectCost/">会员项目消费列表</a></li>
		<li class="active"><a href="${ctx}/vip/vipProjectCost/form?id=${vipProjectCost.id}">会员项目消费<shiro:hasPermission name="vip:vipProjectCost:edit">${not empty vipProjectCost.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="vip:vipProjectCost:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="vipProjectCost" action="${ctx}/vip/vipProjectCost/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">选择项目：</label>
			<div class="controls">
				<sys:treeselect id="project" name="projectId" value="${vipProjectCost.projectId}" labelName="projectName" labelValue="${vipProjectCost.projectName}"
					title="选择项目" url="/vip/vipProjectPay/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消费次数：</label>
			<div class="controls">
				<form:input path="costNum" htmlEscape="false" maxlength="11"  class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="vip:vipProjectCost:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>