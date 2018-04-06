<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员项目消费管理</title>
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
		<li class="active"><a href="${ctx}/vip/vipProjectCost/">会员项目消费列表</a></li>
		<shiro:hasPermission name="vip:vipProjectCost:edit"><li><a href="${ctx}/vip/vipProjectCost/form">会员项目消费添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="vipProjectCost" action="${ctx}/vip/vipProjectCost/" method="post" class="breadcrumb form-search">
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
				<th>消费次数</th>
				<th>备注</th>
				<th>更新日期</th>
				<shiro:hasPermission name="vip:vipProjectCost:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="vipProjectCost">
			<tr>
				<td><a href="${ctx}/vip/vipProjectCost/form?id=${vipProjectCost.id}">
					${vipProjectCost.vipPhone}
				</a></td>
				<td>
					${vipProjectCost.vipName}
				</td>
				<td>
					${vipProjectCost.projectName}
				</td>
				<td>
					${vipProjectCost.costNum}
				</td>
				<td>
					${vipProjectCost.remarks}
				</td>
				<td>
					<fmt:formatDate value="${vipProjectCost.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="vip:vipProjectCost:edit"><td>
    				<a href="${ctx}/vip/vipProjectCost/form?id=${vipProjectCost.id}">修改</a>
					<a href="${ctx}/vip/vipProjectCost/delete?id=${vipProjectCost.id}" onclick="return confirmx('确认要删除该会员项目消费吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>