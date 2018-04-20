<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户收款管理</title>
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
		<li class="active"><a href="${ctx}/shop/shopCustomerAccount/">客户收款列表</a></li>
		<shiro:hasPermission name="shop:shopCustomerAccount:edit"><li><a href="${ctx}/shop/shopCustomerAccount/form">客户收款添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopCustomerAccount" action="${ctx}/shop/shopCustomerAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>客户id：</label>
				<form:input path="customerId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>业务时间：</label>
				<input name="beginBusinData" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${shopCustomerAccount.beginBusinData}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endBusinData" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${shopCustomerAccount.endBusinData}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>单据编号：</label>
				<form:input path="accountNo" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>业务时间</th>
				<th>单据编号</th>
				<th>账目类型</th>
				<th>应付金额</th>
				<th>实付金额</th>
				<th>欠款金额</th>
				<th>更新日期</th>
				<th>备注</th>
				<shiro:hasPermission name="shop:shopCustomerAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopCustomerAccount">
			<tr>
				<td><a href="${ctx}/shop/shopCustomerAccount/form?id=${shopCustomerAccount.id}">
					${shopCustomerAccount.customerName}
				</a></td>
				<td>
					${shopCustomerAccount.businData}
				</td>
				<td>
					${shopCustomerAccount.accountNo}
				</td>
				<td>
					${shopCustomerAccount.subjectType}
				</td>
				<td>
					${shopCustomerAccount.meetMoney}
				</td>
				<td>
					${shopCustomerAccount.factMoney}
				</td>
				<td>
					${shopCustomerAccount.lessMoney}
				</td>
				<td>
					<fmt:formatDate value="${shopCustomerAccount.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shopCustomerAccount.remarks}
				</td>
				<shiro:hasPermission name="shop:shopCustomerAccount:edit"><td>
    				<a href="${ctx}/shop/shopCustomerAccount/form?id=${shopCustomerAccount.id}">修改</a>
					<a href="${ctx}/shop/shopCustomerAccount/delete?id=${shopCustomerAccount.id}" onclick="return confirmx('确认要删除该客户收款吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>