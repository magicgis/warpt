<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品采购单管理</title>
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
		
		function showAddOrder(){
        	top.layer.ready(function(){ 
				var index =	top.layer.open({
				    type: 2,
				    title: '采购单',
				    maxmin: false,
				    resize : false, //是否允许拉伸
				    //area: ['600px', '450px'],
				    content: ctx + '/shop/shopPurchaseOrder/form',
					success : function(layero, index) {
					},
					end : function() {
					}
				});
				top.layer.full(index);
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/shop/shopPurchaseOrder/">商品采购单列表</a></li>
<%-- 		<shiro:hasPermission name="shop:shopPurchaseOrder:edit"><li><a href="${ctx}/shop/shopPurchaseOrder/form">商品采购单添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="shopPurchaseOrder" action="${ctx}/shop/shopPurchaseOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>采购单号：</label>
				<form:input path="orderNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>采购日期：</label>
				<form:input path="businData" htmlEscape="false" maxlength="19" class="input-medium"/>-
				<form:input path="businData" htmlEscape="false" maxlength="19" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix" style="float: right;">
			<shiro:hasPermission name="shop:shopPurchaseOrder:edit">
				<input class="btn btn-primary" type="button" value="新增采购单" onclick="showAddOrder()"/>
			</shiro:hasPermission>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>仓库</th>
				<th>采购单号</th>
				<th>订单总金额</th>
				<th>额外运费</th>
				<th>采购日期</th>
				<th>销售员</th>
				<th>更新日期</th>
				<shiro:hasPermission name="shop:shopPurchaseOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopPurchaseOrder">
			<tr>
				<td><a href="${ctx}/shop/shopPurchaseOrder/form?id=${shopPurchaseOrder.id}">
					${shopPurchaseOrder.stockName}
				</a></td>
				<td>
					${shopPurchaseOrder.orderNo}
				</td>
				<td>
					${shopPurchaseOrder.orderSum}
				</td>
				<td>
					${shopPurchaseOrder.freightMoney}
				</td>
				<td>
					${shopPurchaseOrder.businData}
				</td>
				<td>
					${shopPurchaseOrder.orderName}
				</td>
				<td>
					<fmt:formatDate value="${shopPurchaseOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="shop:shopPurchaseOrder:edit"><td>
    				<a href="${ctx}/shop/shopPurchaseOrder/form?id=${shopPurchaseOrder.id}">修改</a>
					<a href="${ctx}/shop/shopPurchaseOrder/delete?id=${shopPurchaseOrder.id}" onclick="return confirmx('确认要删除该商品采购单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>