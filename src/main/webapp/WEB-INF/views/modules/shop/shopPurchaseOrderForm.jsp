<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品采购单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/shop/shopPurchaseOrder/">商品采购单列表</a></li>
		<li class="active"><a href="${ctx}/shop/shopPurchaseOrder/form?id=${shopPurchaseOrder.id}">商品采购单<shiro:hasPermission name="shop:shopPurchaseOrder:edit">${not empty shopPurchaseOrder.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shop:shopPurchaseOrder:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shopPurchaseOrder" action="${ctx}/shop/shopPurchaseOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">供应商：</label>
			<div class="controls">
				<sys:treeselect id="supplier" name="supplierId" value="${shopProduct.supplierId}" labelName="supplierName" labelValue="${shopProduct.supplierName}"
					title="选择供应商" url="/shop/shopPurchaseSupplier/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>			
		<div class="control-group">
			<label class="control-label">仓库：</label>
			<div class="controls">
				<sys:treeselect id="stock" name="stockId" value="${shopProduct.stockId}" labelName="stockName" labelValue="${shopProduct.stockName}"
					title="选择仓库" url="/shop/shopStockInfo/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">快递运费：</label>
			<div class="controls">
				<form:input path="freightMoney" htmlEscape="false" class="input-xlarge  number" value='0.00'/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购日期：</label>
			<div class="controls">
				<form:input path="businData" htmlEscape="false" maxlength="19" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">商品明细：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>商品id</th>
								<th>商品名称</th>
								<th>货号(条码)</th>
								<th>采购数量</th>
								<th>采购单价</th>
								<th>备注</th>
								<shiro:hasPermission name="shop:shopPurchaseOrder:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="shopPurchaseOrderItemList">
						</tbody>
						<shiro:hasPermission name="shop:shopPurchaseOrder:edit"><tfoot>
							<tr><td colspan="8"><a href="javascript:" onclick="addRow('#shopPurchaseOrderItemList', shopPurchaseOrderItemRowIdx, shopPurchaseOrderItemTpl);shopPurchaseOrderItemRowIdx = shopPurchaseOrderItemRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="shopPurchaseOrderItemTpl">//<!--
						<tr id="shopPurchaseOrderItemList{{idx}}">
							<td class="hide">
								<input id="shopPurchaseOrderItemList{{idx}}_id" name="shopPurchaseOrderItemList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="shopPurchaseOrderItemList{{idx}}_delFlag" name="shopPurchaseOrderItemList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="shopPurchaseOrderItemList{{idx}}_productId" name="shopPurchaseOrderItemList[{{idx}}].productId" type="text" value="{{row.productId}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="shopPurchaseOrderItemList{{idx}}_productName" name="shopPurchaseOrderItemList[{{idx}}].productName" type="text" value="{{row.productName}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<input id="shopPurchaseOrderItemList{{idx}}_productNo" name="shopPurchaseOrderItemList[{{idx}}].productNo" type="text" value="{{row.productNo}}" maxlength="500" class="input-small "/>
							</td>
							<td>
								<input id="shopPurchaseOrderItemList{{idx}}_purchaseNum" name="shopPurchaseOrderItemList[{{idx}}].purchaseNum" type="text" value="{{row.purchaseNum}}" maxlength="11" class="input-small  digits"/>
							</td>
							<td>
								<input id="shopPurchaseOrderItemList{{idx}}_orderMoney" name="shopPurchaseOrderItemList[{{idx}}].orderMoney" type="text" value="{{row.orderMoney}}" class="input-small  number"/>
							</td>
							<td>
								<textarea id="shopPurchaseOrderItemList{{idx}}_remarks" name="shopPurchaseOrderItemList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="shop:shopPurchaseOrder:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#shopPurchaseOrderItemList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var shopPurchaseOrderItemRowIdx = 0, shopPurchaseOrderItemTpl = $("#shopPurchaseOrderItemTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(shopPurchaseOrder.shopPurchaseOrderItemList)};
							for (var i=0; i<data.length; i++){
								addRow('#shopPurchaseOrderItemList', shopPurchaseOrderItemRowIdx, shopPurchaseOrderItemTpl, data[i]);
								shopPurchaseOrderItemRowIdx = shopPurchaseOrderItemRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:shopPurchaseOrder:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>