mui.init({
	swipeBack : true
// 启用右滑关闭功能
		});

(function($) {

	// 选择商品下拉构造
	selectEditProMap.queryFromInit($);
	// 日期控件
	var myPicker = new $.DtPicker({
		type : "date"
	});
	var businDataInpt = document.getElementById('businData');
	// 设置默认订单日期
	businDataInpt.value = defDate;
	businDataInpt.addEventListener('tap', function(event) {
		myPicker.show(function(rs) {
			businDataInpt.value = rs.text;
		});
	});

	// 加载客户
	var customPicker = new $.PopPicker({
		layer : 1
	});
	customPicker.setData(customData);	
	var customNameInpt = document.getElementById('customName');
	var customIdInpt = document.getElementById('customId');
	customNameInpt.addEventListener('tap', function(event) {
		customPicker.show(function(items) {
			if (items[0].value) {
				customNameInpt.value = items[0].text;
				customIdInpt.value = items[0].value;
				var pramMap = {
						customerId : items[0].value,
					};
					mui.post(mypath + '/xy/purchaseOrder/findTypeList', pramMap, function(
							response) {
						if (response) {
							var productTypes = response;
							for(var i = 0 ; i < productTypes.length ; i ++){
								   var productType = productTypes[i];
									tmpMap = {
											value : productType.id,
											text : productType.name,
											levelNo : productType.levelNo
									};
									var  products  = productType.productList;
									productArry = [];
									for(var j = 0 ; j < products.length ; j ++){
										var  product = products[j];
										productArry.push({
											value : product.id,
											text : product.productName,
											product_price : product.productPrice,
											remarks : product.remarks,
											levelNo : '3'
										});
									}
									if(productArry.length > 0){
										tmpMap['children'] = productArry
									}
									if(productArry.length > 0){
										parentData.push(tmpMap);
									}
							}
							selectEditProMap.productType.setData(parentData);
						} else {
							alert('error');
						}
					}, 'json');
			} else {
				return false;
			}
		});
	});
	// 加载用户
	var vipPicker = new $.PopPicker({
		layer : 1
	});
	vipPicker.setData(vipData);	
	var receivingNameInpt = document.getElementById('receivingName');
	var receivingIdInpt = document.getElementById('receivingId');
	var driverNameInpt = document.getElementById('driverName');
	var driverIdInpt = document.getElementById('driverId');	
	receivingNameInpt.addEventListener('tap', function(event) {
		vipPicker.show(function(items) {
			if (items[0].value) {
				receivingNameInpt.value = items[0].text;
				receivingIdInpt.value = items[0].value;
			} else {
				return false;
			}
		});
	});	
	driverNameInpt.addEventListener('tap', function(event) {
		vipPicker.show(function(items) {
			if (items[0].value) {
				driverNameInpt.value = items[0].text;
				driverIdInpt.value = items[0].value;
			} else {
				return false;
			}
		});
	});	
	// 保存事件
	$('body').on('tap', '#save_btn', function(event) {
		selectEditProMap.savePurchaseOrder('');
	});
	$('body').on('tap', '#saveOk_btn', function(event) {
		$.confirm('完成收货单将生成送货单,确定完成吗？', '操作确认', ['确认', '取消'], function(e) {
			if (e.index == 0) {
				selectEditProMap.savePurchaseOrder('',true);
			} else {
			}
		});	
	});

})(mui);

// 选择商品下拉构造

