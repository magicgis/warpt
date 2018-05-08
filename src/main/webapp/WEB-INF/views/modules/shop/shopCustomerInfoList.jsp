<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售客户管理</title>
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
		<li class="active"><a href="${ctx}/shop/shopCustomerInfo/">销售客户列表</a></li>
		<shiro:hasPermission name="shop:shopCustomerInfo:edit"><li><a href="${ctx}/shop/shopCustomerInfo/form">销售客户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopCustomerInfo" action="${ctx}/shop/shopCustomerInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>优惠级别：</label>
				<sys:treeselect id="level" name="levelId" value="${shopCustomerInfo.levelId}" labelName="levelName" labelValue="${shopCustomerInfo.levelName}"
					title="选择优惠级别" url="/shop/shopCustomerLevel/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
			</li>
			<li><label>客户名称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>电话：</label>
				<form:input path="phone" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>手机：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>客户名称</th>
				<th>折扣名称</th>
				<th>折上折(%)</th>
				<th>电话</th>
				<th>手机</th>
				<th>邮箱</th>
				<th>更新日期</th>
				<th>备注</th>
				<shiro:hasPermission name="shop:shopCustomerInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopCustomerInfo">
			<tr>
				<td><a href="${ctx}/shop/shopCustomerInfo/form?id=${shopCustomerInfo.id}">
					${shopCustomerInfo.customerName}
				</td></a>
				<td>
					${shopCustomerInfo.levelName}
				</td>
				<td>
					${shopCustomerInfo.discount}
				</td>
				<td>
					${shopCustomerInfo.phone}
				</td>
				<td>
					${shopCustomerInfo.mobile}
				</td>
				<td>
					${shopCustomerInfo.email}
				</td>
				<td>
					<fmt:formatDate value="${shopCustomerInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shopCustomerInfo.remarks}
				</td>
				<shiro:hasPermission name="shop:shopCustomerInfo:edit"><td>
    				<a href="${ctx}/shop/shopCustomerInfo/form?id=${shopCustomerInfo.id}">修改</a>
					<a href="${ctx}/shop/shopCustomerInfo/delete?id=${shopCustomerInfo.id}" onclick="return confirmx('确认要删除该销售客户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>