<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员基本信息管理</title>
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
		<li><a href="${ctx}/vip/vipUserBase/">会员基本信息列表</a></li>
		<li class="active"><a href="${ctx}/vip/vipUserBase/form?id=${vipUserBase.id}">会员基本信息<shiro:hasPermission name="vip:vipUserBase:edit">${not empty vipUserBase.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="vip:vipUserBase:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="vipUserBase" action="${ctx}/vip/vipUserBase/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会员名称：</label>
			<div class="controls">
				<form:input path="vipName" htmlEscape="false" maxlength="20" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员手机：</label>
			<div class="controls">
				<form:input path="vipPhone" htmlEscape="false" maxlength="20" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员微信号：</label>
			<div class="controls">
				<form:input path="vipWechat" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员qq号：</label>
			<div class="controls">
				<form:input path="vipQq" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员等级：</label>
			<div class="controls">
				<sys:treeselect id="level" name="levelId" value="${vipUserBase.levelId}" labelName="levelName" labelValue="${vipUserBase.levelName}"
					title="会员等级" url="/vip/vipUserLevel/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在区域：</label>
			<div class="controls">
				<sys:treeselect id="area" name="area.id" value="${vipUserBase.area.id}" labelName="area.name" labelValue="${vipUserBase.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">住址：</label>
			<div class="controls">
				<form:input path="userAddress" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="vip:vipUserBase:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>