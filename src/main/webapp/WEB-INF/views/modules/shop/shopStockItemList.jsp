<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>仓库库存管理</title>
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
		<li class="active"><a href="${ctx}/shop/shopStockItem/">仓库库存列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="shopStockItem" action="${ctx}/shop/shopStockItem/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>仓库id：</label>
				<form:input path="stockId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品类型ID：</label>
				<form:input path="productTypeId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品id：</label>
				<form:input path="productId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>仓库名称</th>
				<th>商品类型</th>
				<th>商品名称</th>
				<th>条码</th>
				<th>库存量</th>
				<th>库存预警数</th>
				<th>更新日期</th>
				<th>备注</th>
				<shiro:hasPermission name="shop:shopStockItem:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopStockItem">
			<tr>
				<td><a href="${ctx}/shop/shopStockItem/form?id=${shopStockItem.id}">
					${shopStockItem.stockName}
				</a></td>
				<td>
					${shopStockItem.productTypeName}
				</td>
				<td>
					${shopStockItem.productName}
				</td>
				<td>
					${shopStockItem.productNo}
				</td>
				<td>
					${shopStockItem.stockNum}
				</td>
				<td>
					${shopStockItem.warnStock}
				</td>
				<td>
					<fmt:formatDate value="${shopStockItem.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shopStockItem.remarks}
				</td>
				<shiro:hasPermission name="shop:shopStockItem:edit"><td>
    				<a href="${ctx}/shop/shopStockItem/form?id=${shopStockItem.id}">修改</a>
					<a href="${ctx}/shop/shopStockItem/delete?id=${shopStockItem.id}" onclick="return confirmx('确认要删除该仓库库存吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>