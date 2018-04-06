mui.init({
	swipeBack : true
// 启用右滑关闭功能
		});

(function($) {
	//加载修改更新商品
	selectEditProMap.productShowList();
	//刷新总数
	selectEditProMap.refreshSelectCount();
	
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
		selectEditProMap.savePurchaseOrder(paramId);
	});
	$('body').on('tap', '#saveOk_btn', function(event) {
		$.confirm('完成收货单将生成送货单,确定完成吗？', '操作确认', ['确认', '取消'], function(e) {
			if (e.index == 0) {
				selectEditProMap.savePurchaseOrder(paramId,true);
			} else {
			}
		});
	});

})(mui);

// 选择商品下拉构造

