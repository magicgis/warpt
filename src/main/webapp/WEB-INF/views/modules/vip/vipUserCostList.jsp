<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员消费记录管理</title>
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
		<li class="active"><a href="${ctx}/vip/vipUserCost/">会员消费记录列表</a></li>
		<shiro:hasPermission name="vip:vipUserCost:edit"><li><a href="${ctx}/vip/vipUserCost/form">会员消费记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="vipUserCost" action="${ctx}/vip/vipUserCost/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员手机：</label>
				<form:input path="vipPhone" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>会员名称：</label>
				<form:input path="vipName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"><span style="color: red;">&nbsp;&nbsp;*商品销售推荐使用进销存录入消费</span></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>会员手机</th>
				<th>会员名称</th>
				<th>消费金额</th>
				<th>消费积分</th>
				<th>消费时间</th>
				<th>备注</th>
				<th>更新日期</th>
				<shiro:hasPermission name="vip:vipUserCost:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="vipUserCost">
			<tr>
				<td><a href="${ctx}/vip/vipUserCost/form?id=${vipUserCost.id}">
					${vipUserCost.vipPhone}
				</a></td>
				<td>
					${vipUserCost.vipName}
				</td>
				<td>
					${vipUserCost.costMoeny}
				</td>
				<td>
					${vipUserCost.costScore}
				</td>
				<td>
					${vipUserCost.costTime}
				</td>
				<td>
					${vipUserCost.remarks}
				</td>
				<td>
					<fmt:formatDate value="${vipUserCost.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="vip:vipUserCost:edit"><td>
					<a href="${ctx}/vip/vipUserCost/delete?id=${vipUserCost.id}" onclick="return confirmx('确认要删除该会员消费记录吗？删除后会员钱包将还原。', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>