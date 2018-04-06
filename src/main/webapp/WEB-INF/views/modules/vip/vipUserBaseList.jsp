<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员基本信息管理</title>
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
		
		function sendMessge(id){
			$.post('${ctx}/vip/vipUserBase/sendMessge', {
				id : id	
			}, function(data) {
				if (data.success) {
					alert('短信提醒成功！');
				} else {
					alert(data.msg);
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/vip/vipUserBase/">会员基本信息列表</a></li>
		<shiro:hasPermission name="vip:vipUserBase:edit"><li><a href="${ctx}/vip/vipUserBase/form">会员基本信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="vipUserBase" action="${ctx}/vip/vipUserBase/" method="post" class="breadcrumb form-search">
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
				<th>会员等级</th>
				<th>可用金额</th>
				<th>已用金额</th>
				<th>可用积分</th>
				<th>已兑积分</th>
				<shiro:hasPermission name="vip:vipUserBase:edit"><th colspan="2" >操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="vipUserBase">
			<tr>
				<td><a href="${ctx}/vip/vipUserBase/form?id=${vipUserBase.id}">
					${vipUserBase.vipName}
				</a></td>
				<td>
					${vipUserBase.vipPhone}
				</td>
				<td>
					${vipUserBase.levelName}
				</td>
				<td>
					${vipUserBase.restMoeny}
				</td>
				<td>
					${vipUserBase.useMoeny}
				</td>	
				<td>
					${vipUserBase.restScore}
				</td>
				<td>
					${vipUserBase.useScore}
				</td>								
				<shiro:hasPermission name="vip:vipUserBase:edit">
				<td><a href="javascript:void(0);" onclick="sendMessge('${vipUserBase.id}')">余额提醒</a></td>
				<td>
    				<a href="${ctx}/vip/vipUserBase/form?id=${vipUserBase.id}">修改</a>
					<a href="${ctx}/vip/vipUserBase/delete?id=${vipUserBase.id}" onclick="return confirmx('确认要删除该会员基本信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>