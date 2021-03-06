var app = null;
jQuery(document).ready(function() {
	app = new Vue({
		el : '#app',
		data : {
			fullscreenLoading : false,
			// 是否新增视图
			isAddView : true,
			// 是否能保存数据，如果存在非法数据不能保存数据
			isSaveFn : true,
			// 仓库列表
			stockList : [],
			// 供应商列表
			customerList : [],
			// 产品列表
			productList : [],
			addForm : {
				sendSum : 0.0,
				orderSum : 0.0,
				freightMoney : 0.0,
				shopSaleOrderItemList : []
			},
			addFormRules : {
				businData : [{
							required : true,
							message : '请选择日期',
							trigger : 'change'
						}],
				sendSum : [{
							required : true,
							validator : function(rule, value, callback) {
								// if (!value) {
								// return callback(new Error('金额不能为空'));
								// }
								if (!isNumber(value)) {
									callback(new Error('请输入金额'));
								} else {
									callback();
								}
							},
							trigger : 'change'
						}],
				freightMoney : [{
							validator : function(rule, value, callback) {
								if (!value) { // 不判断非空
									return callback();
								}
								if (!isNumber(value)) {
									callback(new Error('请输入金额'));
								} else {
									callback();
								}
							},
							trigger : 'change'
						}]
			}
		},
		methods : {
			handleMouseEnter : function(row, column, cell, event) {
				// cell.children[0].children[1].style.color = "black";
			},
			handleMouseOut : function(row, column, cell, event) {
				// cell.children[0].children[1].style.color = "#ffffff";
			},
			// 加载查询条件
			loadingForm : function(orderId) {
				var _self = this;
				_self.fullscreenLoading = true;
				$.post(mypath + '/shop/shopSaleOrder/loadingForm', {
							id : orderId
						}, function(data) {
							if (data.success) {
								_self.stockList = data.obj.stockList;
								_self.customerList = data.obj.customerList;
								_self.productList = data.obj.productList;
								delete data.obj.stockList;
								delete data.obj.customerList;
								delete data.obj.productList;
								_self.addForm = data.obj;
								if (!_self.addForm.id) { // 新增情况下初始化6行
									_self.addForm.shopSaleOrderItemList = [];
									for (var i = 0; i < 3; i++) {
										_self.addForm.shopSaleOrderItemList
												.push({
															index : i
														});
									}
									_self.addForm.orderSum = 0.0;
									_self.addForm.sendSum = 0.0;
									_self.addForm.freightMoney = 0.0;
									//
									_self.addForm.subjectType = _self.addForm.subjectType
											+ '';
									// 遮罩还原
									_self.fullscreenLoading = false;
								} else {
									_self.isAddView = false;
								}

							} else {
								alert(data.msg);
								top.layer.closeAll();
							}

						});
			},
			// 提交验证保存
			submitInfo : function() {
				var _self = this;
				// 验证判断
				_self.$refs.addForm.validate(function(result) {
					if (result && _self.isSaveFn) {
						if (String(_self.addForm.sendSum) == '') {
							_self.$message.error('请输入实付金额');
							return;
						}
						_self.fullscreenLoading = true;
						// 保存操作
						$.post(mypath + '/shop/shopSaleOrder/saveOrderForm', {
									saveJson : JSON.stringify(_self.addForm)
								}, function(data) {
									if (data.success) {
										_self
												.$alert(
														'<strong>销售单保存成功[已出库]</strong>',
														{
															dangerouslyUseHTMLString : true
														});
										// 更新列表
										top.loadListForm.submit();
										// 关闭
										top.layer.closeAll();
									} else {
										_self.$message.error(data.msg);
									}
									_self.fullscreenLoading = false;
								});
					} else {
						_self.$message.error('提交验证不合法，请检查输入项！');
					}
				}.bind(this));
			},
			addTRow : function() {
				var _self = this;
				var getLength = _self.addForm.shopSaleOrderItemList.length;
				var maxData = _self.addForm.shopSaleOrderItemList[getLength - 1];
				_self.addForm.shopSaleOrderItemList.push({
							index : maxData.index + 1
						});
			},
			deleteTRow : function(index) {
				var _self = this;
				// 删除数组对象
				_self.addForm.shopSaleOrderItemList.splice(index, 1);
				// 求合计
				_self.sumRowMoney();
			},
			//选择仓库
			selectStockObj : function() {
				var _self = this;
				// 更新名称
				for (var i = 0; i < _self.stockList.length; i++) {
					var stock = _self.stockList[i];
					if(stock.id == _self.addForm.stockId){
						_self.addForm.stockName = stock.stockName;
					}
				}
				_self.clearObj();
			},
			//选择客户
			selectCustomerObj : function() {
				var _self = this;
				// 更新名称
				for (var i = 0; i < _self.customerList.length; i++) {
					var customer = _self.customerList[i];
					if(customer.id == _self.addForm.customerId){
						_self.addForm.customerName = customer.customerName;
					}
				}
				_self.clearObj();
			},
			clearObj : function() {
				var _self = this;
				// 重新清空添加
				_self.addForm.shopSaleOrderItemList.splice(0,
						_self.addForm.shopSaleOrderItemList.length);
				for (var i = 0; i < 3; i++) {
					_self.addForm.shopSaleOrderItemList.push({
								index : i
							});
				}
				// 由于不更新所以克隆TO.
				_self.addForm = Object.assign({}, _self.addForm, {
							orderSum : 0.0,
							sendSum : 0.0
						})
			},
			sumRowMoney : function() {
				var _self = this, orderSum = 0;
				var itemList = _self.addForm.shopSaleOrderItemList;
				for (var i = 0; i < itemList.length; i++) {
					if (itemList[i].countMoney) {
						orderSum = numberUtil.add(orderSum,
								itemList[i].countMoney, 2);
					}
				}
				// 由于不更新所以克隆TO.
				// _self.$set(_self, 'addForm', {orderSum :
				// orderSum,sendSum : orderSum});
				_self.addForm = Object.assign({}, _self.addForm, {
							orderSum : orderSum,
							sendSum : orderSum
						});
			},
			// 选择产品项
			selectProductObj : function(index) {
				var _self = this;
				_self.fullscreenLoading = true;
				var selectObj = _self.addForm.shopSaleOrderItemList[index]; // 选择对象
				$.post(mypath + '/shop/shopSaleOrder/addOrderItem', {
							productId : selectObj.productId,
							stockId : _self.addForm.stockId,
							customerId : _self.addForm.customerId
						}, function(data) {
							if (data.success) {
								// 设置绑定和存储值
								_self.addForm.shopSaleOrderItemList.splice(
										index, 1, data.obj);
								// 求合计
								_self.sumRowMoney();
							} else {
								_self.$message.error(data.msg);
							}
							_self.fullscreenLoading = false;
						});
			},
			// 扫描添加(回车)
			queryAddProduct : function(ev) {
				var _self = this;
				_self.fullscreenLoading = true;
				$.post(mypath + '/shop/shopSaleOrder/addOrderItem', {
							productNo : _self.addForm.productNo,
							stockId : _self.addForm.stockId,
							customerId : _self.addForm.customerId
						}, function(data) {
							if (data.success) {
								// 查找是否存在此商品,存在累加,不存在新增
								var itemList = _self.addForm.shopSaleOrderItemList, appendRow = true;
								// 追加已经存在的
								for (var i = 0; i < itemList.length; i++) {
									if (itemList[i].productNo == _self.addForm.productNo) {
										// 更新库存和总价更新
										itemList[i].saleNum++;
										itemList[i].allMoney = numberUtil.mul(
												itemList[i].saleNum,
												itemList[i].saleMoney, 2);
										itemList[i].countMoney = numberUtil
												.mul(
														numberUtil
																.mul(
																		itemList[i].saleNum,
																		itemList[i].saleMoney,
																		2),
														itemList[i].discount
																* 0.01, 2);
										appendRow = false;
										_self.addForm.shopSaleOrderItemList
												.splice(i, 1, itemList[i]);
										break;
									}
								}
								if (appendRow) {
									// 先查找是否有空行，插入，否则追加
									for (var i = 0; i < itemList.length; i++) {
										if (!itemList[i].productNo) {
											_self.addForm.shopSaleOrderItemList
													.splice(i, 1, data.obj);
											appendRow = false;
											break;
										}
									}
									if (appendRow) { // 满了需要追加行
										var getLength = _self.addForm.shopSaleOrderItemList.length;
										var maxData = _self.addForm.shopSaleOrderItemList[getLength
												- 1];
										data.obj.index = maxData.index + 1;
										_self.addForm.shopSaleOrderItemList
												.push(data.obj);
									}
								}
								// 求合计
								_self.sumRowMoney();
							} else {
								_self.$message.error(data.msg);
							}
							_self.fullscreenLoading = false;
						});
			},
			// 改变某行值更新
			changeSetRow : function(index) {
				var _self = this;
				var selectObj = _self.addForm.shopSaleOrderItemList[index]; // 选择对象
				// 判断输入是否合法
				if (!isInteger(selectObj.saleNum)) {
					_self.$message.error('输入的数量不合法，请输入整数!');
					_self.isSaveFn = false;
					return;
				}
				if (!isNumber(selectObj.saleMoney)) {
					_self.$message.error('输入的单价不合法，请输入金额!');
					_self.isSaveFn = false;
					return;
				}
				if (!isNumber(selectObj.discount)) {
					_self.$message.error('输入的折扣不合法，请输入整数!');
					_self.isSaveFn = false;
					return;
				}
				if (!isNumber(selectObj.disMoney)) {
					_self.$message.error('输入的折后单价不合法，请输入金额!');
					_self.isSaveFn = false;
					return;
				}
				_self.isSaveFn = true;
				if (selectObj.productId && selectObj.productId != '') {
					selectObj.disMoney = numberUtil.mul(selectObj.saleMoney,
							selectObj.discount * 0.01, 2);
					selectObj.allMoney = numberUtil.mul(selectObj.saleNum,
							selectObj.saleMoney, 2);
					selectObj.countMoney = numberUtil.mul(numberUtil.mul(
									selectObj.saleNum, selectObj.saleMoney, 2),
							selectObj.discount * 0.01, 2);
					_self.addForm.shopSaleOrderItemList.splice(index, 1,
							selectObj);
					// 求合计
					_self.sumRowMoney();
				}
			}
		}
	});

	app.loadingForm(orderId);
});

// 金额合法验证
function checkMoney(rule, value, callback) {
	if (!value) {
		return callback(new Error('金额不能为空'));
	}
	if (!isNumber(value)) {
		callback(new Error('请输入金额'));
	} else {
		callback();
	}
}
// 数量合法验证
function checkNum(rule, value, callback) {
	if (!value) {
		return callback(new Error('数量不能为空'));
	}
	if (!isInteger(value)) {
		callback(new Error('请输入数量'));
	} else {
		callback();
	}
}