<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员消费记录管理</title>
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
		<li><a href="${ctx}/vip/vipUserCost/">会员消费记录列表</a></li>
		<li class="active"><a href="${ctx}/vip/vipUserCost/form?id=${vipUserCost.id}">会员消费记录<shiro:hasPermission name="vip:vipUserCost:edit">${not empty vipUserCost.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="vip:vipUserCost:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="vipUserCost" action="${ctx}/vip/vipUserCost/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">选择会员：</label>
			<div class="controls">
				<sys:treeselect id="vip" name="vipId" value="${vipUserCost.vipId}" labelName="vipName" labelValue="${vipUserCost.vipName}"
					title="选择会员" url="/vip/vipUserBase/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消费金额：</label>
			<div class="controls">
				<form:input path="costMoeny" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消费积分：</label>
			<div class="controls">
				<form:input path="costScore" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<!-- <div class="control-group">
			<label class="control-label">消费时间：</label>
			<div class="controls">
				<form:input path="costTime" htmlEscape="false" maxlength="19" class="input-xlarge "/>
			</div>
		</div> -->
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
				<br/>
				<span style="color: red;">&nbsp;&nbsp;*注意：这里直接录入消费，不会扣减进销存商品项</span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="vip:vipUserCost:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>