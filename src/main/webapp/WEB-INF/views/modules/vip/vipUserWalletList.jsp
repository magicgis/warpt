<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员钱包管理</title>
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
		<li class="active"><a href="${ctx}/vip/vipUserWallet/">会员钱包列表</a></li>
		<shiro:hasPermission name="vip:vipUserWallet:edit"><li><a href="${ctx}/vip/vipUserWallet/form">会员钱包添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="vipUserWallet" action="${ctx}/vip/vipUserWallet/" method="post" class="breadcrumb form-search">
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
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>会员名称</th>
				<th>会员手机</th>
				<th>可用金额</th>
				<th>已消费金额</th>
				<th>可用积分</th>
				<th>已兑积分</th>
				<th>更新日期</th>
				<shiro:hasPermission name="vip:vipUserWallet:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="vipUserWallet">
			<tr>
				<td><a href="${ctx}/vip/vipUserWallet/form?id=${vipUserWallet.id}">
					${vipUserWallet.vipName}
				</a></td>
				<td>
					${vipUserWallet.vipPhone}
				</td>
				<td>
					${vipUserWallet.restMoeny}
				</td>
				<td>
					${vipUserWallet.useMoeny}
				</td>
				<td>
					${vipUserWallet.restScore}
				</td>
				<td>
					${vipUserWallet.useScore}
				</td>
				<td>
					<fmt:formatDate value="${vipUserWallet.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="vip:vipUserWallet:edit"><td>
    				<a href="${ctx}/vip/vipUserWallet/form?id=${vipUserWallet.id}">修改</a>
					<a href="${ctx}/vip/vipUserWallet/delete?id=${vipUserWallet.id}" onclick="return confirmx('确认要删除该会员钱包吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>