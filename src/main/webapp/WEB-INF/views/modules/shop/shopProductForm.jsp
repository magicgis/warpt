<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品基本信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					if(!form.shopProductPriceList0_levelName){
						alert('请录入销售价格表再保存.');
						return false;
					}
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
		<li><a href="${ctx}/shop/shopProduct/">商品基本信息列表</a></li>
		<li class="active"><a href="${ctx}/shop/shopProduct/form?id=${shopProduct.id}">商品基本信息<shiro:hasPermission name="shop:shopProduct:edit">${not empty shopProduct.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shop:shopProduct:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shopProduct" action="${ctx}/shop/shopProduct/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		

		<div class="control-group">
			<label class="control-label">商品类型：</label>
			<div class="controls">
				<sys:treeselect id="productType" name="productTypeId" value="${shopProduct.productTypeId}" labelName="productTypeName" labelValue="${shopProduct.productTypeName}"
					title="选择商品类型" url="/shop/shopProductType/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品名称：</label>
			<div class="controls">
				<form:input path="productName" htmlEscape="false" maxlength="200" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">货号(条码)：</label>
			<div class="controls">
				<form:input path="productNo" htmlEscape="false" maxlength="500" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购价：</label>
			<div class="controls">
				<form:input path="buyPrice" htmlEscape="false" class="required  number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">库存预警数：</label>
			<div class="controls">
				<form:input path="warnStock" htmlEscape="false" maxlength="11" class="input-xlarge  digits" value='10'/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格：</label>
			<div class="controls">
				<form:input path="spec" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品描述：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="listNo" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">销售价格：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>价格名称</th>
								<th>折扣比例</th>
								<th>折扣价格</th>
								<th>排序</th>
								<th>备注</th>
								<shiro:hasPermission name="shop:shopProduct:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="shopProductPriceList">
						</tbody>
						<shiro:hasPermission name="shop:shopProduct:edit"><tfoot>
							<tr><td colspan="7"><a href="javascript:" onclick="addRow('#shopProductPriceList', shopProductPriceRowIdx, shopProductPriceTpl);shopProductPriceRowIdx = shopProductPriceRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="shopProductPriceTpl">//<!--
						<tr id="shopProductPriceList{{idx}}">
							<td class="hide">
								<input id="shopProductPriceList{{idx}}_id" name="shopProductPriceList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="shopProductPriceList{{idx}}_delFlag" name="shopProductPriceList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="shopProductPriceList{{idx}}_levelName" name="shopProductPriceList[{{idx}}].levelName" type="text" value="{{row.levelName}}" maxlength="64" class="input-small required"/><span class="help-inline"><font color="red">*</font> </span>
							</td>
							<td>
								<input id="shopProductPriceList{{idx}}_discount" name="shopProductPriceList[{{idx}}].discount" type="text" value="{{row.discount}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<input id="shopProductPriceList{{idx}}_discountPrice" name="shopProductPriceList[{{idx}}].discountPrice" type="text" value="{{row.discountPrice}}" class="input-small required number"/><span class="help-inline"><font color="red">*</font> </span>
							</td>
							<td>
								<input id="shopProductPriceList{{idx}}_listNo" name="shopProductPriceList[{{idx}}].listNo" type="text" value="{{row.listNo}}" maxlength="11" class="input-small  digits"/>
							</td>
							<td>
								<textarea id="shopProductPriceList{{idx}}_remarks" name="shopProductPriceList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="shop:shopProduct:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#shopProductPriceList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var shopProductPriceRowIdx = 0, shopProductPriceTpl = $("#shopProductPriceTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(shopProduct.shopProductPriceList)};
							for (var i=0; i<data.length; i++){
								addRow('#shopProductPriceList', shopProductPriceRowIdx, shopProductPriceTpl, data[i]);
								shopProductPriceRowIdx = shopProductPriceRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:shopProduct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>