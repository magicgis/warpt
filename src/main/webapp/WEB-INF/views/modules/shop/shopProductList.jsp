<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品基本信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var mypath = '${ctx}';
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function impExcelFn(){
			// 保存操作
			$.post(mypath+ '/shop/shopProduct/impExcel',
					{}, function(data) {
								alert('导入成功');
			});
        }		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/shop/shopProduct/">商品基本信息列表</a></li>
		<shiro:hasPermission name="shop:shopProduct:edit"><li><a href="${ctx}/shop/shopProduct/form">商品基本信息添加</a></li></shiro:hasPermission>
		<shiro:hasPermission name="shop:shopProduct:edit"><li><a href="${ctx}/shop/shopProduct/formImp">商品批量导入</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopProduct" action="${ctx}/shop/shopProduct/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品类型：</label>
				<sys:treeselect id="productType" name="productTypeId" value="${shopProduct.productTypeId}" labelName="productTypeName" labelValue="${shopProduct.productTypeName}"
					title="选择商品类型" url="/shop/shopProductType/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
			</li>
			<li><label>商品名称：</label>
				<form:input path="productName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>货号(条码)：</label>
				<form:input path="productNo" htmlEscape="false" maxlength="500" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品类型</th>
				<th>商品名称</th>
				<th>货号(条码)</th>
				<th>采购价</th>
				<th>单位</th>
				<th>规格</th>
				<th>更新日期</th>
				<th>备注</th>
				<shiro:hasPermission name="shop:shopProduct:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopProduct">
			<tr>
				<td><a href="${ctx}/shop/shopProduct/form?id=${shopProduct.id}">
					${shopProduct.productTypeName}
				</a></td>
				<td>
					${shopProduct.pingyinStr}
				</td>
				<td>
					${shopProduct.productNo}
				</td>
				<td>
					${shopProduct.buyPrice}
				</td>
				<td>
					${shopProduct.unit}
				</td>
				<td>
					${shopProduct.spec}
				</td>
				<td>
					<fmt:formatDate value="${shopProduct.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shopProduct.remarks}
				</td>
				<shiro:hasPermission name="shop:shopProduct:edit"><td>
    				<a href="${ctx}/shop/shopProduct/form?id=${shopProduct.id}">修改</a>
					<a href="${ctx}/shop/shopProduct/delete?id=${shopProduct.id}" onclick="return confirmx('确认要删除该商品基本信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>