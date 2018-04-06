mui.init({
	swipeBack : true
// 启用右滑关闭功能
		});

(function($) {
	//加载已选商品
	selectEditProMap.productShowList();
	//刷新总数
	selectEditProMap.refreshSelectCount();
	// 差异商品构造
	selectEditProMap.diffFromInit($);
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
	// 加载用户
	var vipPicker = new $.PopPicker({
		layer : 1
	});
	vipPicker.setData(vipData);
	var sendUsernameInpt = document.getElementById('sendUsername');
	var senduserIdInpt = document.getElementById('senduserId');
	var driverNameInpt = document.getElementById('driverName');
	var driverIdInpt = document.getElementById('driverId');	
	sendUsernameInpt.addEventListener('tap', function(event) {
		vipPicker.show(function(items) {
			if (items[0].value) {
				sendUsernameInpt.value = items[0].text;
				senduserIdInpt.value = items[0].value;
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
		selectEditProMap.saveSaleOrder(paramId);
	});
	$('body').on('tap', '#saveOk_btn', function(event) {
		$.confirm('完成发货单将生成差异单,确定完成吗？', '操作确认', ['确认', '取消'], function(e) {
			if (e.index == 0) {
				selectEditProMap.saveSaleOrder(paramId,true);
			} else {
			}
		});
	});

})(mui);

// 选择商品下拉构造

