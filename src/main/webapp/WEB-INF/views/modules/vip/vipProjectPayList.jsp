<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员项目充值管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/vip/vipProjectPay/">会员项目充值列表</a></li>
		<shiro:hasPermission name="vip:vipProjectPay:edit"><li><a href="${ctx}/vip/vipProjectPay/form">会员项目充值添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="vipProjectPay" action="${ctx}/vip/vipProjectPay/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员手机：</label>
				<form:input path="vipPhone" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>会员名称：</label>
				<form:input path="vipName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>项目名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>会员手机</th>
				<th>会员名称</th>
				<th>项目名称</th>
				<th>项目金额</th>
				<th>总次数</th>
				<th>可用次数</th>
				<th>已用次数</th>
				<th>备注</th>
				<th>充值日期</th>
				<!--<shiro:hasPermission name="vip:vipProjectPay:edit"><th>操作</th></shiro:hasPermission>-->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="vipProjectPay">
			<tr>
				<td><a href="${ctx}/vip/vipProjectPay/form?id=${vipProjectPay.id}">
					${vipProjectPay.vipPhone}
				</a></td>
				<td>
					${vipProjectPay.vipName}
				</td>
				<td>
					${vipProjectPay.projectName}
				</td>
				<td>
					${vipProjectPay.projectMoeny}
				</td>
				<td>
					${vipProjectPay.allNum}
				</td>
				<td>
					${vipProjectPay.restNum}
				</td>
				<td>
					${vipProjectPay.useNum}
				</td>
				<td>
					${vipProjectPay.remarks}
				</td>
				<td>
					<fmt:formatDate value="${vipProjectPay.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<!--<shiro:hasPermission name="vip:vipProjectPay:edit"><td>
    				<a href="${ctx}/vip/vipProjectPay/form?id=${vipProjectPay.id}">修改</a>
					<a href="${ctx}/vip/vipProjectPay/delete?id=${vipProjectPay.id}" onclick="return confirmx('确认要删除该会员项目充值吗？', this.href)">删除</a>
				</td></shiro:hasPermission>-->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>