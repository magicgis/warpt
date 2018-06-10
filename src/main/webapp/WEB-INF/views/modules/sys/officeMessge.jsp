<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<!-- 转义jstl字符串无法输出解决 -->
	<c:set value="${'${name}'}" var="name" />
	<c:set value="${'${code}'}" var="code" />
	<c:set value="${'${msg}'}" var="msg" />
	<c:set value="${'${msg1}'}" var="msg1" />
	<c:set value="${'${msg2}'}" var="msg2" />
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
		<li><a href="${ctx}/sys/office/list?id=${office.parent.id}&parentIds=${office.parentIds}">机构列表</a></li>
		<li ><a href="${ctx}/sys/office/form?id=${office.id}&parent.id=${office.parent.id}">机构<shiro:hasPermission name="sys:office:edit">${not empty office.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:office:edit">查看</shiro:lacksPermission></a></li>
		<shiro:hasPermission name="sys:office:edit">
		<c:if test="${not empty office.id}">
			<li class="active"><a href="${ctx}/sys/office/messge?id=${office.id}">短信设置</a></li>
		</c:if>
		</shiro:hasPermission>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysMessage" action="${ctx}/sys/office/saveMessge" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="officeId"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">短信签名:</label>
			<div class="controls">
				<form:input path="snKey" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">accessKey:</label>
			<div class="controls">
				<form:input type="password" path="accessKeyId" htmlEscape="false" maxlength="50" class="required" autocomplete="off"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">accessKeySecret:</label>
			<div class="controls">
				<form:input type="password" path="accessKeySecret" htmlEscape="false" maxlength="50" class="required" autocomplete="off"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册模板:</label>
			<div class="controls">
				<form:input path="registerCode" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*&nbsp;尊敬的[${name}]您已注册成为XX会员！</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">充值模板:</label>
			<div class="controls">
				<form:input path="payCode" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*&nbsp;尊敬的会员[${name}]，您已充值成功！账户余额[${msg1}],积分余额：[${msg2}]</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消费模板:</label>
			<div class="controls">
				<form:input path="costCode" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*&nbsp;尊敬的会员[${name}]，您已成功消费[${msg}]；账户余额[${msg1}],积分余额：[${msg2}]</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">余额模板:</label>
			<div class="controls">
				<form:input path="walletCode" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*&nbsp;尊敬的会员[${name}]，您的账户余额${msg1},积分余额：${msg2}。</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目消费模板:</label>
			<div class="controls">
				<form:input path="projectCode" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*&nbsp;尊敬的会员[${name}]，您已成功消费${msg}项目${msg1}次,剩余次数：${msg2}</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">小程序注册绑定模板:</label>
			<div class="controls">
				<form:input path="wechatCode" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*&nbsp;您正在申请手机绑定小程序，验证码为：${code}，5分钟内有效！</font> </span>
			</div>
		</div>			
		<div class="form-actions">
			<shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>