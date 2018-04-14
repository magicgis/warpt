var app = null;
jQuery(document).ready(function() {
	app = new Vue({
				el : '#app',
				data : {
					addForm : {
						stockList : [],
						supplierList : [],
						shopPurchaseOrderItemList : []
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
						$.post(mypath + '/shop/shopPurchaseOrder/loadingForm', {
							id : id
						}, function(data) {
							alert(data.id)
							_self.addForm = data;
							if(!_self.addForm.id){ //新增初始化
								_self.addForm.shopPurchaseOrderItemList = [{},{},{},{},{},{}];
							}
						});
					},
					addRow : function(row) {
						
					},
					deleteRow : function(row) {
						
					}
				}
			});
		
		app.loadingForm(null);
});