<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售明细表</title>
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

		function exportExcel(){
			$.post('${ctx}/shop/shopReport/exportExcel', {
				customerId : $("#customerId").val(),
				saleNo : $("#saleNo").val(),
				beginBusinData : $("#beginBusinData").val(),
				endBusinData : $("#endBusinData").val(),
				type : 1
			}, function(data) {
				if (data.success) {
					window.open(data.urlPath,'_blank');
				} else {
					alert(data.msg)
				}
			});
		}		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/shop/shopReport/saleReportList">销售明细</a></li>
		<li><a href="${ctx}/shop/shopReport/saleProductSumReport">按商品汇总</a></li>
		<li><a href="${ctx}/shop/shopReport/saleCustomerSumReport">按客户汇总</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="shopSaleOrderItem" action="${ctx}/shop/shopReport/saleReportList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>客户：</label>
				<sys:treeselect id="customer" name="customerId" value="${shopSaleOrderItem.customerId}" labelName="customerName" labelValue="${shopSaleOrderItem.customerName}"
					title="选择客户" url="/shop/shopCustomerInfo/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
			</li>
			<li><label>订单号：</label>
				<form:input path="saleNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>销售日期：</label>
				<input name="beginBusinData" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${shopSaleOrderItem.beginBusinData}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endBusinData" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${shopSaleOrderItem.endBusinData}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"><input class="btn btn-primary" type="button" value="导出Excel" onclick="exportExcel()"/></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div style="margin:10px 0px 10px 20px;font-size: 15px;" >
		<c:if test="${!empty shopSaleOrderItem.beginBusinData && !empty shopSaleOrderItem.endBusinData}">
		<span >日期：${shopSaleOrderItem.beginBusinData}至${shopSaleOrderItem.endBusinData}</span>
		</c:if>
		<span style="margin-left: 100px;color: red;">销售商品数：${shopSaleOrderItem.sumProduct}</span>
		<span style="margin-left: 100px;color: red;">销售额：${shopSaleOrderItem.sumMoney}</span>
		<span style="margin-left: 100px;color: red;">销售毛利：${shopSaleOrderItem.sumProfit}</span>
		<span style="margin-left: 100px;color: red;">销售毛利率(%)：${shopSaleOrderItem.percentage}</span>
	</div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品名称</th>
				<th>条码</th>
				<th>单位</th>
				<th>规格</th>
				<th>销售数量</th>
				<th>销售单价</th>
				<th>折扣(%)</th>
				<th>折后单价</th>
				<th>原总金额</th>
				<th>折后总额</th>
				<th>销售客户</th>
				<th>订单号</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopSaleOrderItem">
			<tr>
				<td>
					${shopSaleOrderItem.productName}
				</td>
				<td>
					${shopSaleOrderItem.productNo}
				</td>
				<td>
					${shopSaleOrderItem.unit}
				</td>
				<td>
					${shopSaleOrderItem.spec}
				</td>
				<td>
					${shopSaleOrderItem.saleNum}
				</td>
				<td>
					${shopSaleOrderItem.saleMoney}
				</td>
				<td>
					${shopSaleOrderItem.discount}
				</td>
				<td>
					${shopSaleOrderItem.disMoney}
				</td>
				<td>
					${shopSaleOrderItem.allMoney}
				</td>
				<td>
					${shopSaleOrderItem.countMoney}
				</td>
				<td>
					${shopSaleOrderItem.shopSaleOrder.customerName}
				</td>
				<td>
					${shopSaleOrderItem.shopSaleOrder.saleNo}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>