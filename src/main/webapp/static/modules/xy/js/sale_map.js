/**
 * 选择商品事件封装
 */
(function(window, undefined) {
	var selectEditProMap = {
		/** 已选商品缓存* */
		selectProductMap : {},
		/** 商品总数量* */
		countNum : 0,
		/** 下拉构造* */
		diffFromInit : function($) {
			var busying = false;
			var menuWrapper = document.getElementById("menu-wrapper");
			var menu = document.getElementById("menu");
			var menuWrapperClassList = menuWrapper.classList;
			var backdrop = document.getElementById("menu-backdrop");
			var contents = document.getElementById("segmentedControlContents");
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
					// 差异值存在则获取
					selectEditProMap.setRemarksVal();
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
					// 差异值存在则获取
					selectEditProMap.setRemarksVal();					
				}
				setTimeout(function() {
					busying = false;
				}, 500);
			}
			// 初始化差异类型
			var diffPicker = new $.PopPicker({
				layer : 1
			});
			diffPicker.setData(diffData);
			// 加载
			backdrop.addEventListener('tap', toggleMenu);
			document.getElementById("showDiff").addEventListener('tap',
					function(event) {
						// 切换编辑页面
						selectEditProMap.refreshDiffProduct(diffPicker);
						toggleMenu();
					});
			// ************************事件处理方法
			// 点击选好了按钮(编辑界面)
			document.getElementById("editOkBtn").addEventListener('tap',
					function() {
						toggleMenu();
					});
		},
		/** 刷新选中商品总数* */
		refreshSelectCount : function() {
			selectEditProMap.countNum = 0;
			selectEditProMap.diffSum = 0;
			for (key in selectEditProMap.selectProductMap) {
				var showObj = selectEditProMap.selectProductMap[key];
				selectEditProMap.countNum += showObj.num;
				selectEditProMap.diffSum += showObj.diff;
			}
			// 总数量
			document.getElementById("orderSum").value = selectEditProMap.countNum;
			document.getElementById("diffSum").value = selectEditProMap.diffSum;
		},
		/** 已选商品显示* */
		productShowList : function() {
			var productContentUl = document.getElementById("productContentUl");
			productContentUl.innerHTML = '';
			var html = [];
			for (key in selectEditProMap.selectProductMap) {
				var showObj = selectEditProMap.selectProductMap[key];
				if (showObj.mustNum > 0) {
					var appendStr = '<li class="mui-table-view-cell mui-media">';
					appendStr += '<div class="mui-floatbox" data-floatbox-step=1 data-floatbox-min=0 style="float:right;">';
					appendStr += '<button class="mui-btn mui-btn-floatbox-minus" type="button">-</button>';
					appendStr += '<input value='
							+ showObj.num
							+ ' class="edit_num mui-input-floatbox" type="number" style="font-size:12px;" data-key="'
							+ key + '" data-must="' + showObj.mustNum + '"/>';
					appendStr += '<button class="mui-btn mui-btn-floatbox-plus" type="button">+</button>';
					appendStr += '</div>';
					appendStr += '<div style="float:right;margin-right:1px;line-height:35px;">实发&nbsp;</div>';
					appendStr += '<div class="mui-media-body">' + showObj.name;
					appendStr += '<p class="mui-ellipsis" style="color: #000000;font-size:12px;">'
							+ '<span style="color: #000000;">应发：'
							+ showObj.mustNum
							+ '</span>&nbsp;&nbsp;&nbsp;'
							+ '<span id="'
							+ key
							+ '_diff_span" style="color: #F00;">差异：'
							+ showObj.diff + '</span></p>';
					appendStr += '</div></li>';
					html.push(appendStr);
				}
			}
			productContentUl.innerHTML = html.join('');
			// 计算总数和差异

			mui("#productContentUl").off("change", ".edit_num"); // 去掉已有事件，再重新绑定
			mui('#productContentUl').on('change', '.edit_num', function(event) {
				var setKey = this.getAttribute("data-key");
				var must = this.getAttribute("data-must");
				var setVal = this.value * 1;
				var setDiff = must * 1 - this.value * 1
				if(setDiff >=0){
					selectEditProMap.selectProductMap[setKey].diff = setDiff;
					document.getElementById(setKey + '_diff_span').innerHTML = '差异：'
							+ setDiff;
				}
				selectEditProMap.selectProductMap[setKey].num = setVal;
				// 更新总数
				selectEditProMap.refreshSelectCount();
			});

		},
		/** 差异编辑加载* */
		refreshDiffProduct : function(diffPicker) {
			var editContents = document.getElementById("editControlContents");
			editContents.innerHTML = '';
			var html = [];
			// html.push('<ul class="mui-table-view">');
			for (key in selectEditProMap.selectProductMap) {
				var showObj = selectEditProMap.selectProductMap[key];
				if (showObj.diff != 0) {
					var setDiffTypeName = diffShowMap[showObj.diffType]
							? diffShowMap[showObj.diffType]
							: '';
					var appendStr = '<div class="mui-input-row"><label style="width:50%;text-align:left;">'
							+ showObj.name
							+ '&nbsp;[差异：'
							+ showObj.diff
							+ ']</label>';
					appendStr += '<input class="css_diffType" style="width:50%;font-size:12px;line-height:35px;" id="'
							+ key
							+ '_diffType" name="'
							+ key
							+ '_diffType" value="'
							+ setDiffTypeName
							+ '" data-key="'
							+ key
							+ '" placeholder="选择差异类型" readonly/>';
					appendStr += '</div>';
					appendStr += '<div class="mui-collapse-content mui-input-row" style="margin: 15px 0px;">';
					appendStr += '<input type="text" class="css_diffRemarks" data-key="'
							+ key + '" style="width:90%;font-size:12px;" id="'
							+ key + '_diffRemarks" name="' + key
							+ '_diffRemarks" placeholder="填写[' + showObj.name
							+ ']差异备注" value="' + showObj.remarks + '" />';
					appendStr += '</div>';
					html.push(appendStr);
				}
			}
			// html.push('</ul>');
			editContents.innerHTML = html.join('');
			// 绑定事件
			mui("#editControlContents").off("tap", ".css_diffType"); // 去掉已有事件，再重新绑定
			mui('#editControlContents').on('tap', '.css_diffType',
					function(event) {
						var $this = this;
						var setKey = this.getAttribute("data-key");
						diffPicker.show(function(items) {
							selectEditProMap.selectProductMap[setKey].diffType = items[0].value;
							$this.value = items[0].text;
						});
					});
		},
		setRemarksVal : function(){
			var remarksVal, remarksKey, remarksInput = mui('.css_diffRemarks');
			for (var i = 0; i < remarksInput.length; i++) {
				remarksVal = remarksInput[i].value;
				remarksKey = remarksInput[i].getAttribute("data-key");
				selectEditProMap.selectProductMap[remarksKey].remarks = remarksVal;
			}			
		},
		saveSaleOrder : function(paramId, isSuccess) {
			// 差异值存在则获取
			selectEditProMap.setRemarksVal();
			// 选择商品构造
			var itemList = [];
			for (key in selectEditProMap.selectProductMap) {
				var setMap = selectEditProMap.selectProductMap[key];
				if (setMap.num > 0) {
					if(setMap.diff > 0){
						if(setMap.diffType == "" || setMap.remarks == ""){
							alert('存在差异，请填写差异原因!');
							return;
						}
					}
					itemList.push({
						id : setMap.id,
						saleId : paramId,
						productId : key,
						diffType : setMap.diffType,
						remarks : setMap.remarks,
						mustNum : setMap.mustNum,
						saleNum : setMap.num,
						diffNum : setMap.diff
					});
				}
			}
			var senduserId = document.getElementById("senduserId").value;
			if (senduserId == null || senduserId == '') {
				mui.toast('请选择发货人。');
				return;
			}
			var driverId = document.getElementById("driverId").value;
			if (driverId == null || driverId == '') {
				mui.toast('请选择司机。');
				return;
			}
			var pramMap = {
				id : paramId,
				orderSumqut : document.getElementById("orderSum").value,
				diffSum : document.getElementById("diffSum").value,
				businData : document.getElementById("businData").value,
				sendUsername : document.getElementById("sendUsername").value,
				senduserId : senduserId,
				driverName : document.getElementById("driverName").value,
				driverId : driverId,
				remarks : document.getElementById("remarks").value,
				isSuccess : isSuccess,
				itemJson : JSON.stringify(itemList)
			};

			mui.post(mypath + '/xy/saleOrder/saveOrder', pramMap, function(
					response) {
				if (response.success) {
					window.location.href = mypath + "/xy/saleOrder/listApp";
				} else {
					alert(response.message)
				}
			}, 'json');
		}
	};
	window.selectEditProMap = selectEditProMap;
})(window);
