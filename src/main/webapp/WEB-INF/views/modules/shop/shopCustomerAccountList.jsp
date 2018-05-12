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
		
		function showView(id){
        	top.layer.ready(function(){
        		//缓存在打开页面调用
        		top.loadListForm = $('#searchForm');
				var index =	top.layer.open({
				    type: 2,
				    title: '销售出库单',
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
		<li class="active"><a href="${ctx}/shop/shopCustomerAccount/">客户收款列表</a></li>
		<shiro:hasPermission name="shop:shopCustomerAccount:edit"><li><a href="${ctx}/shop/shopCustomerAccount/form">客户收款添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopCustomerAccount" action="${ctx}/shop/shopCustomerAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>客户：</label>
				<sys:treeselect id="customer" name="customerId" value="${shopCustomerAccount.customerId}" labelName="customerName" labelValue="${shopCustomerAccount.customerName}"
					title="选择客户" url="/shop/shopCustomerInfo/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
			</li>
			<li><label>业务时间：</label>
				<input name="beginBusinData" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${shopCustomerAccount.beginBusinData}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endBusinData" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${shopCustomerAccount.endBusinData}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>单据编号：</label>
				<form:input path="accountNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div style="margin:10px 0px 10px 20px;font-size: 15px;" >
		<span>${shopCustomerAccount.customerName}</span>
		<c:if test="${!empty shopCustomerAccount.beginBusinData && !empty shopCustomerAccount.endBusinData}">
		<span style="margin-left: 100px;">日期：${shopCustomerAccount.beginBusinData}至${shopCustomerAccount.endBusinData}</span>
		</c:if>
		<c:if test="${!empty shopCustomerAccount.restMoney}">
		<span style="margin-left: 100px;color: red;">充值余额：${shopCustomerAccount.restMoney}</span>
		</c:if>
		<span style="margin-left: 100px;color: red;">应付金额：${shopCustomerAccount.sumMeetMoney}</span>
		<span style="margin-left: 100px;color: red;">实付金额：${shopCustomerAccount.sumFactMoney}</span>
		<span style="margin-left: 100px;color: red;">欠款金额：${shopCustomerAccount.sumLessMoney}</span>
	</div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				
				<th>单据编号</th>
				<th>业务时间</th>
				<th>账目类型</th>
				<th>应付金额</th>
				<th>实付金额</th>
				<th>欠款金额</th>
				<th>备注</th>
				<shiro:hasPermission name="shop:shopCustomerAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopCustomerAccount">
			<tr>
				<td>
					${shopCustomerAccount.accountNo}
				</td>
				<td>
					${shopCustomerAccount.businData}
				</td>
				<td>
					<c:if test="${shopCustomerAccount.subjectType == '1000'}">销售出货</c:if>
					<c:if test="${shopCustomerAccount.subjectType == '1001'}">销售退货</c:if>
					<c:if test="${shopCustomerAccount.subjectType == '1005'}">客户收款</c:if>
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
					${shopCustomerAccount.remarks}
				</td>
				<shiro:hasPermission name="shop:shopCustomerAccount:edit"><td>
				<c:if test="${empty shopCustomerAccount.saleId}">
    				<a href="${ctx}/shop/shopCustomerAccount/form?id=${shopCustomerAccount.id}">修改</a>
					<a href="${ctx}/shop/shopCustomerAccount/delete?id=${shopCustomerAccount.id}" onclick="return confirmx('确认要删除该客户收款吗？', this.href)">删除</a>
				</c:if>
				<c:if test="${!empty shopCustomerAccount.saleId}">
					<a href="javascript:void(0)" onclick="showView('${shopCustomerAccount.saleId}')">查看单据</a>
				</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>