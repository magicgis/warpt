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
				    content: ctx + '/shop/shopPurchaseOrder/form',
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
				    content: ctx + '/shop/shopPurchaseOrder/form?id='+id,
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
		<li class="active"><a href="${ctx}/shop/shopPurchaseOrder/">采购单列表</a></li>
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
				<input id="beginBusinData" name="beginBusinData" type="text" readonly="readonly" class="input-medium Wdate"
				value="${shopPurchaseOrder.beginBusinData}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				-
				<input id="endBusinData" name="endBusinData" type="text" readonly="readonly" class="input-medium Wdate"
				value="${shopPurchaseOrder.endBusinData}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix" style="float: right;">
			<shiro:hasPermission name="shop:shopPurchaseOrder:edit">
				<input class="btn btn-primary" type="button" value="新增采购单" onclick="showAdd()"/>
			</shiro:hasPermission>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>采购单号</th>
				<th>账目类型</th>
				<th>订单总金额</th>
				<th>额外运费</th>
				<th>采购日期</th>
				<th>进入仓库</th>
				<th>创建日期</th>
				<shiro:hasPermission name="shop:shopPurchaseOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopPurchaseOrder">
			<tr>
				<td><a href="javascript:void(0)" onclick="showView('${shopPurchaseOrder.id}')">
					${shopPurchaseOrder.orderNo}
				</a></td>
				<td>
					<c:if test="${shopPurchaseOrder.subjectType == '1002'}">采购进货</c:if>
					<c:if test="${shopPurchaseOrder.subjectType == '1003'}">采购退货</c:if>
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
					${shopPurchaseOrder.stockName}
				</td>
				<td>
					<fmt:formatDate value="${shopPurchaseOrder.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="shop:shopPurchaseOrder:edit"><td>
<%--     				<a href="${ctx}/shop/shopPurchaseOrder/form?id=${shopPurchaseOrder.id}">复制退货</a> --%>
					<a href="${ctx}/shop/shopPurchaseOrder/delete?id=${shopPurchaseOrder.id}" onclick="return confirmx('确认要删除该采购单吗？删除后改变的库存、供应商付款等将扣减！', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>