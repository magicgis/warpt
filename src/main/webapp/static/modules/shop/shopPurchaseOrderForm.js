var app = null;
jQuery(document).ready(function() {
	app = new Vue({
				el : '#app',
				data : {
					addForm : {
						stockList : [],
						supplierList : [],
						shopPurchaseOrderItemList : [],
						productList : [],
						discount : null //供应商折扣
					},
					addFormRules : {}
				},
				methods : {
					handleMouseEnter : function(row, column, cell, event) {
						// cell.children[0].children[1].style.color = "black";
					},
					handleMouseOut : function(row, column, cell, event) {
						// cell.children[0].children[1].style.color = "#ffffff";
					},
					// 加载查询条件
					loadingForm : function(id) {
						var _self = this;
						$.post(mypath + '/shop/shopPurchaseOrder/loadingForm',
								{
									id : id
								}, function(data) {
									if(data.success){
										_self.addForm = data.obj;
										if (!_self.addForm.id) { // 新增情况下初始化6行
											_self.addForm.shopPurchaseOrderItemList = [];
											for (var i = 0; i < 3; i++) {
												_self.addForm.shopPurchaseOrderItemList
														.push({
																	index : i
																});
											}
	
										}
									}else{
										alert(data.msg);
										top.layer.closeAll();
									}
								});
					},
					addTRow : function() {
						var _self = this;
						var getLength = _self.addForm.shopPurchaseOrderItemList.length;
						var maxData = _self.addForm.shopPurchaseOrderItemList[getLength
								- 1];
						_self.addForm.shopPurchaseOrderItemList.push({
									index : maxData.index + 1
								});
					},
					deleteTRow : function(index) {
						var _self = this;
						// 删除数组对象
						_self.addForm.shopPurchaseOrderItemList
								.splice(index, 1);
					},
					selectStockOb : function(){
						
					},
					selectSupplierObj : function(){
						
					},
					selectProductObj : function(index){
						var _self = this;
						var selectIndex = _self.addForm.shopPurchaseOrderItemList[index].selectIndex;
						var selectProductObj = _self.addForm.productList[selectIndex];
						//设置绑定和存储值
						_self.addForm.shopPurchaseOrderItemList[index].productId = selectProductObj.id;
						_self.addForm.shopPurchaseOrderItemList[index].productName = selectProductObj.productName;
						_self.addForm.shopPurchaseOrderItemList[index].productNo = selectProductObj.productNo;
						_self.addForm.shopPurchaseOrderItemList[index].unit = selectProductObj.unit;
						_self.addForm.shopPurchaseOrderItemList[index].spec = selectProductObj.spec;
						_self.addForm.shopPurchaseOrderItemList[index].purchaseNum = 1;
						//_self.addForm.shopPurchaseOrderItemList[index].stockNum = ;
						_self.addForm.shopPurchaseOrderItemList[index].orderMoney = selectProductObj.buyPrice;
						//_self.addForm.shopPurchaseOrderItemList[index].discount;
						//_self.addForm.shopPurchaseOrderItemList[index].disMoney
						//_self.addForm.shopPurchaseOrderItemList[index].allMoney
						//_self.addForm.shopPurchaseOrderItemList[index].countMoney
					}
				}
			});

	app.loadingForm(null);
});