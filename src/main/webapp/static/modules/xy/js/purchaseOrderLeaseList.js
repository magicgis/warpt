mui.init({
	// 启用右滑关闭功能
	swipeBack : true,
	// 刷新翻页
	pullRefresh : {
		container : '#pullrefresh',
		// down : {
		// callback : pulldownRefresh
		// },
		up : {
			contentrefresh : '正在加载...',
			callback : pullupRefresh
		}
	}
});

var customNameInpt = document.getElementById('customName');
var strDayInpt = document.getElementById('strDay');
var endDayInpt = document.getElementById('endDay');
var pageNo = 1; // 翻页系数
(function($) {
	// 选择商品下拉构造
	queryFromInit($);
	pageNo = 1
	// 进来初始化
	if (mui.os.plus) {
		mui.plusReady(function() {
			setTimeout(function() {
				mui('#pullrefresh').pullRefresh().pullupLoading();
			}, 1000);
		});
	} else {
		mui.ready(function() {
			mui('#pullrefresh').pullRefresh().pullupLoading();
		});
	}
})(mui);

// 选择商品下拉构造
function queryFromInit($) {
	var busying = false;
	var menuWrapper = document.getElementById("menu-wrapper");
	var menu = document.getElementById("menu");
	var menuWrapperClassList = menuWrapper.classList;
	var backdrop = document.getElementById("menu-backdrop");
	// 查询下拉菜单事件
	function toggleMenu() {
		if (busying) {
			return;
		}
		busying = true;
		if (menuWrapperClassList.contains('mui-active')) {
			document.body.classList.remove('menu-open');
			menuWrapper.className = 'menu-wrapper fade-out-up animated';
			menu.className = 'menu bounce-out-up animated';
			setTimeout(function() {
				backdrop.style.opacity = 0;
				menuWrapper.classList.add('hidden');
			}, 500);
		} else {
			document.body.classList.add('menu-open');
			menuWrapper.className = 'menu-wrapper fade-in-down animated mui-active';
			menu.className = 'menu bounce-in-down animated';
			backdrop.style.opacity = 1;
		}
		setTimeout(function() {
			busying = false;
		}, 500);
	}
	// 加载
	backdrop.addEventListener('tap', toggleMenu);
	document.getElementById("showQuery").addEventListener('tap', toggleMenu);
	// 日期控件
	var myPicker = new $.DtPicker({
		type : "date"
	});
	strDayInpt.addEventListener('tap', function(event) {
		myPicker.show(function(rs) {
			strDayInpt.value = rs.text;
		});
	});
	endDayInpt.addEventListener('tap', function(event) {
		myPicker.show(function(rs) {
			endDayInpt.value = rs.text;
		});
	});
	// 加载客户
	var customPicker = new $.PopPicker({
		layer : 1
	});
	customPicker.setData(customData);
	customNameInpt.addEventListener('tap', function(event) {
		customPicker.show(function(items) {
			if (items[0].value) {
				customNameInpt.value = items[0].text;
			} else {
				return false;
			}
		});
	});

	// 搜索按钮
	document.getElementById("queryBtn").addEventListener('tap', function() {
		pageNo = 1; // 初始化翻页
		loadOrder(true, {
			customName : customNameInpt.value,
			strDay : strDayInpt.value,
			endDay : endDayInpt.value,
			pageNo : pageNo
		})
		toggleMenu();
	});
	// 取消按钮
	document.getElementById("cancelBtn").addEventListener('tap', function() {
		toggleMenu();
	});
}

/**
 * 上拉加载数据
 */
function pullupRefresh() {
	setTimeout(function() {
		loadOrder(false, {
			customName : customNameInpt.value,
			strDay : strDayInpt.value,
			endDay : endDayInpt.value,
			pageNo : pageNo
		});
	}, 1500);
}

/**
 * 加载采购订单 isNew 重新加载
 * 
 * @param {}
 *            paramMap
 */
function loadOrder(isNew, paramMap) {
	var table = document.getElementById("table-list_ul");
	if (isNew) {
		table.innerHTML = '';
	}
	mui.post(mypath + '/xy/purchaseOrder/findOrderLease', paramMap, function(
			response) {
		// var html = [];
		pageNo++; // 加载完毕翻页增加1
		var dataList = response.list;
		var lastPage = Boolean(response.isLastPage);
		if(dataList){
			for (var i = 0; i < dataList.length; i++) {
				var li = document.createElement('li');
				li.className = 'ui_edit mui-table-view-cell mui-media';
				li.id = dataList[i].id;
				var statusName = dataList[i].status == 1 ? '已完成' : '未完成';
				var businData = '';
				if(dataList[i].businData == undefined){
					businData = '';
				}else{
					businData = dataList[i].businData;
				}
				// appendStr = '<li class="mui-table-view-cell mui-media">';
				var appendStr = '<div class="mui-slider-right mui-disabled">';
//				appendStr += '<a href="javascript:void(0)" data-id="'
//						+ dataList[i].id
//						+ '" class="ui_edit mui-btn mui-btn-yellow mui-icon mui-icon-compose" btn_type="edit" ></a>';
//				if (dataList[i].status == 0) {
//					appendStr += '<a href="javascript:void(0)" data-id="'
//							+ dataList[i].id
//							+ '" class="ui_del mui-btn mui-btn-red mui-icon mui-icon-trash" btn_type="del" onclick=""></a>';
//				}
				appendStr += '</div>';
				appendStr += '<a href="javascript:void(0);" class="mui-slider-handle">';
				appendStr += '<div class="mui-media-body">订单号：'
						+ dataList[i].orderNo
						+ '<p class="mui-ellipsis" style="color: #000000;font-size:12px;">发货总数：'
						+ dataList[i].saleSumqut
						+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户：'
						+ dataList[i].customName
						+ '</p>'
						+ '<p class="mui-ellipsis" style="color: #000000;font-size:12px;">状态：'
						+ statusName + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收货时间：'
						+ businData + '</p>';
				appendStr += '</div></a>';
				li.innerHTML = appendStr;
				table.appendChild(li);
			}
		}
		// 去掉已有事件，再重新绑定
		mui("#table-list_ul").off("tap", ".ui_edit");
		mui("#table-list_ul").off("tap", ".ui_del");
		// 绑定按钮事件
		mui('#table-list_ul').on('tap', '.ui_edit', function(event) {
			var dataId = this.id
			editClick(dataId);
		});
		// 绑定按钮事件
		mui('#table-list_ul').on('tap', '.ui_del', function(event) {
			var dataId = this.getAttribute("data_id")
			deletClick(dataId);
		});
		// 参数为true代表没有更多数据了。
		mui('#pullrefresh').pullRefresh().endPullupToRefresh(lastPage);
	}, 'json');
}

function editClick(id) {
	window.location.href = mypath + "/xy/purchaseOrder/editLease?id=" + id;
};
