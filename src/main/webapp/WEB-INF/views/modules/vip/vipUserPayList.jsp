<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员充值记录管理</title>
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
			$.post('${ctx}/vip/vipUserPay/exportExcel', {
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
		<li class="active"><a href="${ctx}/vip/vipUserPay/">会员充值记录列表</a></li>
		<shiro:hasPermission name="vip:vipUserPay:edit"><li><a href="${ctx}/vip/vipUserPay/form">会员充值记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="vipUserPay" action="${ctx}/vip/vipUserPay/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员名称：</label>
				<form:input path="vipName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>会员手机：</label>
				<form:input path="vipPhone" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"><input class="btn btn-primary" type="button" value="导出Excel" onclick="exportExcel()"/></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>会员名称</th>
				<th>会员手机</th>
				<th>充值总额</th>
				<th>充值金额</th>
				<th>赠送金额</th>
				<th>获得积分</th>
				<th>充值时间</th>
				<th>备注</th>
				<shiro:hasPermission name="vip:vipUserPay:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="vipUserPay">
			<tr>
				<td><a href="${ctx}/vip/vipUserPay/form?id=${vipUserPay.id}">
					${vipUserPay.vipName}
				</a></td>
				<td>
					${vipUserPay.vipPhone}
				</td>
				<td>
					${vipUserPay.payMoeny}
				</td>
				<td>
					${vipUserPay.realMoeny}
				</td>
					<td>
					${vipUserPay.giveMoeny}
				</td>
				<td>
					${vipUserPay.getScore}
				</td>
				<td>
					${vipUserPay.payTime}
				</td>
				<td>
					${vipUserPay.remarks}
				</td>
				<shiro:hasPermission name="vip:vipUserPay:edit"><td>
					<a href="${ctx}/vip/vipUserPay/delete?id=${vipUserPay.id}" onclick="return confirmx('确认要撤销该会员充值记录吗？删除后会员钱包将还原(如果有充值赠送，进销存收款负项也会直接删除)', this.href)">撤销充值</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>