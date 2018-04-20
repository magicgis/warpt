<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售订单管理</title>
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
		
		function showAdd(){
        	top.layer.ready(function(){
        		//缓存在打开页面调用
        		top.loadListForm = $('#searchForm');
				var index =	top.layer.open({
				    type: 2,
				    title: '采购入库单',
				    maxmin: false,
				    resize : false, //是否允许拉伸
				    //area: ['600px', '450px'],
				    content: ctx + '/shop/shopSaleOrder/form',
					success : function(layero, index) {
					},
					end : function() {
					}
				});
				top.layer.full(index);
			});
		}
		
		function showView(id){
        	top.layer.ready(function(){
        		//缓存在打开页面调用
        		top.loadListForm = $('#searchForm');
				var index =	top.layer.open({
				    type: 2,
				    title: '采购入库单',
				    maxmin: false,
				    resize : false, //是否允许拉伸
				    //area: ['600px', '450px'],
				    content: ctx + '/shop/shopSaleOrder/form?id='+id,
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
		<li class="active"><a href="${ctx}/shop/shopSaleOrder/">销售订单列表</a></li>
<%-- 		<shiro:hasPermission name="shop:shopSaleOrder:edit"><li><a href="${ctx}/shop/shopSaleOrder/form">销售订单添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="shopSaleOrder" action="${ctx}/shop/shopSaleOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>客户：</label>
				<sys:treeselect id="customer" name="customerId" value="${shopSaleOrder.customerId}" labelName="customerName" labelValue="${shopSaleOrder.customerName}"
					title="选择客户" url="/shop/shopCustomerInfo/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
			</li>
			<li><label>订单号：</label>
				<form:input path="saleNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>销售日期：</label>
				<input name="beginBusinData" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${shopSaleOrder.beginBusinData}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endBusinData" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${shopSaleOrder.endBusinData}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix" style="float: right;">
			<shiro:hasPermission name="shop:shopPurchaseOrder:edit">
				<input class="btn btn-primary" type="button" value="新增销售单" onclick="showAdd()"/>
			</shiro:hasPermission>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单号</th>
				<th>账目类型</th>
				<th>客户名称</th>
				<th>订单总金额</th>
				<th>快递运费</th>
				<th>仓库名称</th>
				<th>销售日期</th>
				<shiro:hasPermission name="shop:shopSaleOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopSaleOrder">
			<tr>
				<td><a href="javascript:void(0)" onclick="showView('${shopSaleOrder.id}')">
					${shopSaleOrder.saleNo}
				</a></td>
				<td>
					<c:if test="${shopSaleOrder.subjectType == '1000'}">销售出货</c:if>
					<c:if test="${shopSaleOrder.subjectType == '1001'}">销售退货</c:if>
				</td>
				<td>
					${shopSaleOrder.customerName}
				</td>
				<td>
					${shopSaleOrder.orderSum}
				</td>
				<td>
					${shopSaleOrder.freightMoney}
				</td>
				<td>
					${shopSaleOrder.stockName}
				</td>
				<td>
					${shopSaleOrder.businData}
				</td>
				<shiro:hasPermission name="shop:shopSaleOrder:edit"><td>
					<a href="${ctx}/shop/shopSaleOrder/delete?id=${shopSaleOrder.id}" onclick="return confirmx('确认要删除该销售订单吗？删除后改变的库存、客户付款等将还原！', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>