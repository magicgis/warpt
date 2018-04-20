<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商付款管理</title>
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
		<li class="active"><a href="${ctx}/shop/shopSupplierAccount/">供应商付款列表</a></li>
		<shiro:hasPermission name="shop:shopSupplierAccount:edit"><li><a href="${ctx}/shop/shopSupplierAccount/form">供应商付款添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopSupplierAccount" action="${ctx}/shop/shopSupplierAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>业务时间</th>
				<th>单据编号</th>
				<th>账目类型</th>
				<th>应付金额</th>
				<th>实付金额</th>
				<th>欠款金额</th>
				<th>更新日期</th>
				<th>备注</th>
				<shiro:hasPermission name="shop:shopSupplierAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopSupplierAccount">
			<tr>
				<td><a href="${ctx}/shop/shopSupplierAccount/form?id=${shopSupplierAccount.id}">
					${shopSupplierAccount.businData}
				</a></td>
				<td>
					${shopSupplierAccount.accountNo}
				</td>
				<td>
					${shopSupplierAccount.subjectType}
				</td>
				<td>
					${shopSupplierAccount.meetMoney}
				</td>
				<td>
					${shopSupplierAccount.factMoney}
				</td>
				<td>
					${shopSupplierAccount.lessMoney}
				</td>
				<td>
					<fmt:formatDate value="${shopSupplierAccount.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shopSupplierAccount.remarks}
				</td>
				<shiro:hasPermission name="shop:shopSupplierAccount:edit"><td>
    				<a href="${ctx}/shop/shopSupplierAccount/form?id=${shopSupplierAccount.id}">修改</a>
					<a href="${ctx}/shop/shopSupplierAccount/delete?id=${shopSupplierAccount.id}" onclick="return confirmx('确认要删除该供应商付款吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>