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
		<li class="active"><a href="${ctx}/shop/shopSupplierAccount/">供应商付款列表</a></li>
		<shiro:hasPermission name="shop:shopSupplierAccount:edit"><li><a href="${ctx}/shop/shopSupplierAccount/form">供应商付款添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopSupplierAccount" action="${ctx}/shop/shopSupplierAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>供应商：</label>
				<sys:treeselect id="supplier" name="supplierId" value="${shopSupplierAccount.supplierId}" labelName="supplierName" labelValue="${shopSupplierAccount.supplierName}"
					title="选择汇总供应商" url="/shop/shopPurchaseSupplier/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
			</li>
			<li><label>业务日期：</label>
				<input id="beginBusinData" name="beginBusinData" type="text" readonly="readonly" class="input-medium Wdate"
				value="${shopSupplierAccount.beginBusinData}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				-
				<input id="endBusinData" name="endBusinData" type="text" readonly="readonly" class="input-medium Wdate"
				value="${shopSupplierAccount.endBusinData}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
		<span>${shopSupplierAccount.supplierName}</span>
		<c:if test="${!empty shopSupplierAccount.beginBusinData && !empty shopSupplierAccount.endBusinData}">
		<span style="margin-left: 150px;">日期：${shopSupplierAccount.beginBusinData}至${shopSupplierAccount.endBusinData}</span>
		</c:if>
		<span style="margin-left: 150px;color: red;">应付金额：${shopSupplierAccount.sumMeetMoney}</span>
		<span style="margin-left: 150px;color: red;">实付金额：${shopSupplierAccount.sumFactMoney}</span>
		<span style="margin-left: 150px;color: red;">欠款金额：${shopSupplierAccount.sumLessMoney}</span>
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
				<shiro:hasPermission name="shop:shopSupplierAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopSupplierAccount">
			<tr>
				<td>
					${shopSupplierAccount.accountNo}
				</td>
				<td>
					${shopSupplierAccount.businData}
				</td>
				<td>
					<c:if test="${shopSupplierAccount.subjectType == '1002'}">采购进货</c:if>
					<c:if test="${shopSupplierAccount.subjectType == '1003'}">采购退货</c:if>
					<c:if test="${shopSupplierAccount.subjectType == '1004'}">付款供应商</c:if>
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
					${shopSupplierAccount.remarks}
				</td>
				<shiro:hasPermission name="shop:shopSupplierAccount:edit"><td>
					<c:if test="${empty shopSupplierAccount.orderId}">
    					<a href="${ctx}/shop/shopSupplierAccount/form?id=${shopSupplierAccount.id}">修改</a>
						<a href="${ctx}/shop/shopSupplierAccount/delete?id=${shopSupplierAccount.id}" onclick="return confirmx('确认要删除该供应商付款吗？', this.href)">删除</a>
					</c:if>
					<c:if test="${!empty shopSupplierAccount.orderId}">
						<a href="javascript:void(0)" onclick="showView('${shopSupplierAccount.orderId}')">查看单据</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>