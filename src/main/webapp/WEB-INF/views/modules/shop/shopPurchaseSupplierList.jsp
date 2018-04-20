<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商管理</title>
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
		<li class="active"><a href="${ctx}/shop/shopPurchaseSupplier/">供应商列表</a></li>
		<shiro:hasPermission name="shop:shopPurchaseSupplier:edit"><li><a href="${ctx}/shop/shopPurchaseSupplier/form">供应商添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopPurchaseSupplier" action="${ctx}/shop/shopPurchaseSupplier/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>供应商名称：</label>
				<form:input path="supplierName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>负责人：</label>
				<form:input path="headName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>供应商名称</th>
				<th>折扣(%)</th>
				<th>负责人</th>
				<th>电话</th>
				<th>手机</th>
				<th>邮箱</th>
				<th>更新日期</th>
				<th>备注</th>
				<shiro:hasPermission name="shop:shopPurchaseSupplier:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopPurchaseSupplier">
			<tr>
				<td><a href="${ctx}/shop/shopPurchaseSupplier/form?id=${shopPurchaseSupplier.id}">
					${shopPurchaseSupplier.supplierName}
				</a></td>
				<td>
					${shopPurchaseSupplier.discount}
				</td>
				<td>
					${shopPurchaseSupplier.headName}
				</td>
				<td>
					${shopPurchaseSupplier.phone}
				</td>
				<td>
					${shopPurchaseSupplier.mobile}
				</td>
				<td>
					${shopPurchaseSupplier.email}
				</td>
				<td>
					<fmt:formatDate value="${shopPurchaseSupplier.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shopPurchaseSupplier.remarks}
				</td>
				<shiro:hasPermission name="shop:shopPurchaseSupplier:edit"><td>
    				<a href="${ctx}/shop/shopPurchaseSupplier/form?id=${shopPurchaseSupplier.id}">修改</a>
					<a href="${ctx}/shop/shopPurchaseSupplier/delete?id=${shopPurchaseSupplier.id}" onclick="return confirmx('确认要删除该供应商吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>