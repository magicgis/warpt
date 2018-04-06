/**
 * 选择商品事件封装
 */
(function(window, undefined) {
	var selectEditProMap = {
		/** 已选商品缓存* */
		selectProductMap : {},
		productType : null,
		/** 商品总数量* */
		countNum : 0,
		/** 选择商品下拉构造* */
		queryFromInit : function($) {
			var busying = false;
			var menuWrapper = document.getElementById("menu-wrapper");
			var menu = document.getElementById("menu");
			var menuWrapperClassList = menuWrapper.classList;
			var backdrop = document.getElementById("menu-backdrop");
			var contents = document.getElementById("segmentedControlContents");
			var selectTypeInpt = document.getElementById('selectType');
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
					// 显示主界面
					var mainContent = document.getElementById("main_content");
					var saveDiv = document.getElementById("save_Div");
					mainContent.style.display = '';
					if (saveDiv != null) {
						saveDiv.style.display = '';
					}					
					//更新
					selectEditProMap.refreshAll();
				} else {
					document.body.classList.add('menu-open');
					menuWrapper.className = 'menu-wrapper fade-in-down animated mui-active';
					menu.className = 'menu bounce-in-down animated';
					backdrop.style.opacity = 1;
					// 隐藏主界面
					var mainContent = document.getElementById("main_content");
					var saveDiv = document.getElementById("save_Div");
					mainContent.style.display = 'none';
					if (saveDiv != null) {
						saveDiv.style.display = 'none';
					}					
					//更新
					selectEditProMap.refreshAll();				
				}
				setTimeout(function() {
					busying = false;
				}, 500);
			}
			// 加载
			backdrop.addEventListener('tap', toggleMenu);
//			document.getElementById("showSelect").addEventListener('tap',
//					function(event) {
//						// 切换查询选择页面
//						selectEditProMap.selectOrEditShow('select');
//						toggleMenu();
//						// 刷新总数
//						selectEditProMap.refreshSelectCount();
//					});
			if(document.getElementById("showEdit")!=null){
				document.getElementById("showEdit").addEventListener('tap',
						function(event) {
						var customId = document.getElementById("customId").value;
						if(!customId || customId == ''){
							alert('请先选择客户!')
							return;
						}
							//更新
							selectEditProMap.refreshAll();
							toggleMenu();
						});
			}
			// 加载分类联动菜单
			this.productType = new $.PopPicker({
				layer : 2
			});
			//productType.setData(parentData);
			selectTypeInpt.addEventListener('tap', function(event) {
				selectEditProMap.productType.show(function(items) {
					if (items[1].value) {
						selectTypeInpt.value = items[1].text;
						var dataId = items[1].value;
						if (!selectEditProMap.selectProductMap[dataId]) {
							selectEditProMap.selectProductMap[dataId] = {
								name : items[1].text,
								num : 0
							};
						}
						//更新
						selectEditProMap.refreshAll();
					} else {
						return false;
					}
				});
			});

			// ************************事件处理方法
			// 点击选好了按钮(编辑界面)
			document.getElementById("editOkBtn").addEventListener('tap',
					function() {
						// 更新编辑修改数据
						//selectEditProMap.refreshProductData();
						// 更新界面列表数据
						selectEditProMap.productShowList();
						selectTypeInpt.value = '';
						//contents.innerHTML = '';
						toggleMenu();
						// 刷新总数
						//selectEditProMap.refreshSelectCount();
					});
		},
		/** 商品编辑加载* */
		refreshEditProduct : function() {
			var editContents = document.getElementById("editControlContents");
			editContents.innerHTML = '';
			var html = [];
			html.push('<ul class="mui-table-view">');
			for (key in selectEditProMap.selectProductMap) {
				var showObj = selectEditProMap.selectProductMap[key];
				//if (showObj.num > 0) {
					var appendStr = '<li class="mui-table-view-cell mui-media"><a href="javascript:;" class="mui-slider-handle">';
					appendStr += '<span class="delEditBtn mui-icon mui-icon-trash mui-pull-left" data-value="'
							+ key + '" ></span>';
					appendStr += '<div class="mui-floatbox" data-floatbox-step=10 data-floatbox-min=0 style="float:right;">';
					appendStr += '<button class="mui-btn mui-btn-floatbox-minus" type="button">-</button>';
					appendStr += '<input value='
							+ showObj.num
							+ ' class="edit_price mui-input-floatbox" type="number" style="font-size:10px;" data-value="'
							+ key + '" />';
					appendStr += '<button class="mui-btn mui-btn-floatbox-plus" type="button">+</button>';
					appendStr += '</div>';
					appendStr += '<div class="mui-media-body" style="font-size:12px;">'
							+ showObj.name + '</div>';
					appendStr += '</a></li>';
					html.push(appendStr);
				//}
			}
			html.push('</ul>');
			editContents.innerHTML = html.join('');
			// 重新渲染下事件，否则无效了
			mui('.mui-floatbox').floatbox();
			// 删除按钮
			mui('#editControlContents').on('tap', '.delEditBtn',
					function(event) {
						var dataId = this.getAttribute("data-value");
						delete selectEditProMap.selectProductMap[dataId];
						selectEditProMap.refreshEditProduct();
					});
		},
		/** 更新编辑情况下商品数据* */
		refreshProductData : function() {
			var setVal, setKey, editInput = mui('.edit_price');
			for (var i = 0; i < editInput.length; i++) {
				setVal = editInput[i].value;
				setKey = editInput[i].getAttribute("data-value");
				selectEditProMap.selectProductMap[setKey].num = setVal * 1;
			}
		},
		/** 刷新选中商品总数* */
		refreshSelectCount : function() {
			selectEditProMap.countNum = 0;
			for (key in selectEditProMap.selectProductMap) {
				var showObj = selectEditProMap.selectProductMap[key];
				selectEditProMap.countNum += showObj.num;
			}
			// 总数量
			document.getElementById("orderSum").value = selectEditProMap.countNum;
		},
		/** 刷新选中商品总数* */
		refreshAll : function() {
			//更新
			selectEditProMap.refreshProductData();
			selectEditProMap.refreshSelectCount();	
			selectEditProMap.refreshEditProduct();
			selectEditProMap.productShowList();
			document.getElementById('selectType').value = "";
		},
		/** 已选商品显示* */
		productShowList : function() {
			var productContentUl = document.getElementById("productContentUl");
			productContentUl.innerHTML = '';
			var html = [];
			for (key in selectEditProMap.selectProductMap) {
				var showObj = selectEditProMap.selectProductMap[key];
				if (showObj.num > 0) {
					var appendStr = '<li class="mui-table-view-cell mui-media"><a href="javascript:;" class="mui-slider-handle">';
					appendStr += '<div class="mui-media-body">' + showObj.name;
					appendStr += '<p class="mui-ellipsis" style="color: #000000;font-size:12px;">选择数量：'
							+ showObj.num + '</p>';
					appendStr += '</div></a></li>';
					html.push(appendStr);
				}
			}
			productContentUl.innerHTML = html.join('');
		},
		savePurchaseOrder : function(paramId,isSuccess) {
			// 选择商品构造
			var itemList = [];
			for (key in selectEditProMap.selectProductMap) {
				var setMap = selectEditProMap.selectProductMap[key];
				if (setMap.num > 0) {
					itemList.push({
						productId : key,
						saleNum : setMap.num
					});
				}
			}
			if(itemList.length == 0){
				mui.toast('请选择商品再保存。');
				return;
			}
			var customId = document.getElementById("customId").value;
			if(customId ==null || customId == ''){
				mui.toast('请选择客户。');
				return;
			}
			var senduserId = document.getElementById("senduserId").value;
			if(senduserId ==null || senduserId == ''){
				mui.toast('请选择送货人。');
				return;
			}
			var driverId = document.getElementById("driverId").value;
			if(driverId ==null || driverId == ''){
				mui.toast('请选择司机。');
				return;
			}
			var cusman = document.getElementById("cusman").value;
			if(cusman ==null || cusman == ''){
				mui.toast('请录入酒店负责人。');
				return;
			}
			var pramMap = {
				id : paramId,
				orderSumqut : document.getElementById("orderSum").value,
				businData : document.getElementById("businData").value,
				customId : customId,
				customName : document.getElementById("customName").value,
				sendUsername : document.getElementById("sendUsername").value,
				senduserId : senduserId,
				driverName : document.getElementById("driverName").value,
				driverId : driverId,
				remarks : document.getElementById("remarks").value,
				isSuccess : isSuccess,
				itemJson : JSON.stringify(itemList),
				cusman : cusman
			};

			mui.post(mypath + '/xy/saleOrder/saveOrderLease', pramMap, function(
					response) {
				if (response.success) {
					window.location.href = mypath
							+ "/xy/saleOrder/listLeaseApp";
				} else {
					alert(response.message)
				}
			}, 'json');
		}
	};
	window.selectEditProMap = selectEditProMap;
})(window);
