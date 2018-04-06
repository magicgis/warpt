<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员充值记录管理</title>
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
		<li><a href="${ctx}/vip/vipUserPay/">会员充值记录列表</a></li>
		<li class="active"><a href="${ctx}/vip/vipUserPay/form?id=${vipUserPay.id}">会员充值记录<shiro:hasPermission name="vip:vipUserPay:edit">${not empty vipUserPay.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="vip:vipUserPay:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="vipUserPay" action="${ctx}/vip/vipUserPay/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">选择会员：</label>
			<div class="controls">
				<sys:treeselect id="vip" name="vipId" value="${vipUserPay.vipId}" labelName="vipName" labelValue="${vipUserPay.vipName}"
					title="选择会员" url="/vip/vipUserBase/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">会员手机：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="vipPhone" htmlEscape="false" maxlength="20" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="control-group">
			<label class="control-label">充值金额：</label>
			<div class="controls">
				<form:input path="payMoeny" htmlEscape="false" class="required  number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">获得积分：</label>
			<div class="controls">
				<form:input path="getScore" htmlEscape="false" class="number"/>
			</div>
		</div>
		<!-- <div class="control-group">
			<label class="control-label">充值时间：</label>
			<div class="controls">
				<form:input path="payTime" htmlEscape="false" maxlength="19" class="input-xlarge "/>
			</div>
		</div> -->
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="vip:vipUserPay:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>