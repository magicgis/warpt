<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<!-- 引入样式 -->
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
	<title>采购单</title>
<style type="text/css">
.cell-icon{
  cursor:pointer;
  color:#fff;
}
</style>
<script type="text/javascript">
var mypath = '${ctx}';
</script>
</head>
<body>
	<div id="app">
	<template>
		<el-row :gutter="24" style="margin-bottom: 15px;border-bottom:1px dashed #ddd;padding:0 0 10px;">
		<el-col :span="16">
		&nbsp;
		</el-col>
		<el-col :span="6">
				<el-input type='text' v-model="addForm.orderSum" placeholder="扫描商品条形码(或关键字+Enter)" prefix-icon="el-icon-search"></el-input>
		</el-col>
		<el-col :span="2">
		<el-button type="primary" icon="el-icon-check" @click="saveForm()">保存	</el-button>
		</el-col>
		</el-row>
		<el-form ref="addForm" :model="addForm" :rules="addFormRules" label-width="80px">
		 	<el-row type="flex">
		 	  <el-col :span="6">
			  <el-form-item label="仓库">
			  	<el-col :span="16">
			    <el-select  v-model="addForm.stockId" placeholder="请先选择仓库" >
			      <el-option v-for="(item, index) in addForm.stockList" v-bind:label="item.stockName" v-bind:value="item.id"></el-option>
			    </el-select>
			    </el-col>
			  </el-form-item>
		 	  </el-col>
		 	  <el-col :span="6">
			  <el-form-item label="供应商">
			  	<el-col :span="16">
			    <el-select  v-model="addForm.supplierId" placeholder="请选择供应商">
			      <el-option v-for="(item, index) in addForm.supplierList" v-bind:label="item.supplierName" v-bind:value="item.id"></el-option>
			    </el-select>
			    </el-col>
			  </el-form-item>
			  </el-col>
			  <el-col :span="6">
			  <el-form-item label="采购日期">
			  	<el-col :span="16">
			    <el-date-picker type="date" placeholder="选择时间" v-model="addForm.businData" style="width: 100%;" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd"></el-date-picker>
			    </el-col>
			  </el-form-item>
			  </el-col>
			  <el-col :span="6">
			  <el-form-item label="快递运费">
			  	<el-col :span="16">
			    <el-input type='text' v-model="addForm.freightMoney" placeholder="请输入快递运费"></el-input> 
			    </el-col>
			  </el-form-item>
			  </el-col>			  
			 </el-row>
			 <el-row type="flex">
				<el-table
				    :data="addForm.shopPurchaseOrderItemList"
				    border
				    @cell-mouse-enter="handleMouseEnter"
				    @cell-mouse-leave="handleMouseOut"
				    style="width: 100%">
					<el-table-column 
					      align="center"
					      label="操作"
					      width="80">
					      <template slot-scope="scope">
					      	<i class="el-icon-plus" @click="addRow(scope.row)" style="cursor: pointer;color: #008cba;font-weight:bold;"></i>
					      	&nbsp;
					        <i class="el-icon-delete" @click="deleteRow(scope.row)" style="cursor: pointer;color: #008cba;font-weight:bold;"></i>
					      </template>
					 </el-table-column>
					<el-table-column 
					      align="center"
					      label="商品"
					       prop="productName"
					      width="200">
					      <template slot-scope="scope">
					        <span style="margin-left: 10px">{{ scope.row.productName }}</span>
					      </template>
					 </el-table-column>
				      <el-table-column
				        align="center"
				        prop="productNo"
				        label="条码" width="200">
				      </el-table-column>
				      <el-table-column
				        align="center"
				        prop="spec"
				        label="规格"
				        width="100">
				      </el-table-column>
					  <el-table-column
					    align="center"
				        prop="unit"
				        label="单位"
				        width="100">
				      </el-table-column>
				    <el-table-column
				      label="采购数量"
				      align="center"
				      width="100">
					      <template scope="scope">
					      <span style="width:120px;">
					      	<el-input type='text' v-model="scope.row.purchaseNum" ></el-input>
					      </span>
					      </template>
				    </el-table-column>
				    <el-table-column
				      label="当前库存"
				      align="center"
				      prop="stockNum"
				      width="100">
				    </el-table-column>
					<el-table-column
					    align="center"
				        prop="buyPrice"
				        label="单价"
				        width="100">
					      <template scope="scope">
					      <span style="width:80px;">
					      	<el-input type='text' v-model="scope.row.orderMoney" ></el-input>
					      </span>
					      </template>
				    </el-table-column>
					<el-table-column
						align="center"
				        prop="discount"
				        label="折扣(%)"
				        width="100">
					      <template scope="scope">
					      <span style="width:120px;">
					      	<el-input type='text' v-model="scope.row.discount" ></el-input>
					      </span>
					      </template>
				    </el-table-column>
					<el-table-column
						align="center"
				        prop="discount"
				        label="折后单价"
				        width="100">
					      <template scope="scope">
					      <span style="width:120px;">
					      	<el-input type='text' v-model="scope.row.disMoney" ></el-input>
					      </span>
					      </template>
				    </el-table-column>
					<el-table-column
						align="center"
				        prop="allMoney"
				        label="金额">
				    </el-table-column>
					<el-table-column
						align="center"
				        prop="countMoney"
				        label="折后金额">
				    </el-table-column>
				  </el-table>
			 </el-row>
		</el-form>
	</template>
	</div>
</body>
	<script src="${ctxStatic}/jquery/jquery-1.10.1.min.js" type="text/javascript"></script>
	<!-- 先引入 Vue -->
 	<script src="https://unpkg.com/vue/dist/vue.js"></script>
	<!-- 引入组件库 -->
	<script src="https://unpkg.com/element-ui/lib/index.js"></script>
	<script src="${ctxStatic}/modules/shop/shopPurchaseOrderForm.js" type="text/javascript"></script>
</html>