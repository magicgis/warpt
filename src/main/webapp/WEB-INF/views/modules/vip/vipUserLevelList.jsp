<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员等级管理</title>
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
		<li class="active"><a href="${ctx}/vip/vipUserLevel/">会员等级列表</a></li>
		<shiro:hasPermission name="vip:vipUserLevel:edit"><li><a href="${ctx}/vip/vipUserLevel/form">会员等级添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="vipUserLevel" action="${ctx}/vip/vipUserLevel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>等级名称：</label>
				<form:input path="levelName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>等级名称</th>
				<th>折扣比例</th>
				<th>等级描述</th>
				<th>是否启用折扣</th>
				<th>排序</th>
				<th>更新日期</th>
				<shiro:hasPermission name="vip:vipUserLevel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="vipUserLevel">
			<tr>
				<td><a href="${ctx}/vip/vipUserLevel/form?id=${vipUserLevel.id}">
					${vipUserLevel.levelName}
				</a></td>
				<td>
					${fns:getDictLabel(vipUserLevel.discount, 'discount_no', '')}
				</td>
				<td>
					${vipUserLevel.remark}
				</td>
				<td>
					${fns:getDictLabel(vipUserLevel.isDiscount, 'yes_no', '')}
				</td>
				<td>
					${vipUserLevel.listNo}
				</td>
				<td>
					<fmt:formatDate value="${vipUserLevel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="vip:vipUserLevel:edit"><td>
    				<a href="${ctx}/vip/vipUserLevel/form?id=${vipUserLevel.id}">修改</a>
					<a href="${ctx}/vip/vipUserLevel/delete?id=${vipUserLevel.id}" onclick="return confirmx('确认要删除该会员等级吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>