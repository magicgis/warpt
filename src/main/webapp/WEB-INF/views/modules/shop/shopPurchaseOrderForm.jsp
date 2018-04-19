<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<!-- 引入样式 -->
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
	<title>采购入库单</title>
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
		<el-form ref="addForm" :model="addForm" :rules="addFormRules" label-width="80px">
		<el-row :gutter="24" style="margin-bottom: 15px;border-bottom:1px dashed #ddd;padding:0 0 10px;">
		<el-col :span="16">
		&nbsp;
		</el-col>
		<el-col :span="6">
				<el-input type='text' v-model="addForm.productNo" placeholder="扫描商品条形码" prefix-icon="el-icon-search" @keyup.enter.native="queryAddProduct()" ></el-input>
		</el-col>
		<el-col :span="2">
		<el-button v-if="isSaveFn" type="primary" icon="el-icon-check" @click="submitInfo">采购入库</el-button>
		<el-button v-if="!isSaveFn" type="primary" icon="el-icon-check" disabled="true">采购入库</el-button>
		</el-col>
		</el-row>
		
		 	<el-row type="flex">
		 	  <el-col :span="6">
			  <el-form-item label="仓库">
			  	<el-col :span="16">
			    <el-select  v-model="addForm.stockId" placeholder="请先选择仓库" @change="selectClearObj()">
			      <el-option v-for="(item, index) in stockList" v-bind:label="item.stockName" v-bind:value="item.id"></el-option>
			    </el-select>
			    </el-col>
			  </el-form-item>
		 	  </el-col>
		 	  <el-col :span="6">
			  <el-form-item label="供应商">
			  	<el-col :span="16">
			    <el-select  v-model="addForm.supplierId" placeholder="请选择供应商" @change="selectClearObj()">
			      <el-option v-for="(item, index) in supplierList" v-bind:label="item.supplierName" v-bind:value="item.id"></el-option>
			    </el-select>
			    </el-col>
			  </el-form-item>
			  </el-col>
			  <el-col :span="6">
			  <el-form-item label="采购日期" prop="businData">
			  	<el-col :span="16">
			    <el-date-picker type="date" placeholder="选择日期" v-model="addForm.businData" style="width: 100%;" ></el-date-picker>
			    </el-col>
			  </el-form-item>
			  </el-col>
			  <el-col :span="6">
			  <el-form-item label="快递运费" prop="freightMoney">
			  	<el-col :span="16">
			    <el-input type='text' v-model="addForm.freightMoney" placeholder="请输入快递运费"></el-input> 
			    </el-col>
			  </el-form-item>
			  </el-col>			  
			 </el-row>
			 <el-row type="flex">
				<el-col :span="12">
				  <el-form-item label="备注">
				  <el-input type='text' v-model="addForm.remarks" ></el-input> 
				  </el-form-item>
				</el-col>
				 <el-col :span="6">
				  <el-form-item label="账目类型" >
				  	<el-col :span="16">
					    <el-select v-model="addForm.subjectType" placeholder="请选择">
				      		<el-option label="采购支出" value="1002"></el-option>
				      		<el-option label="采购退货" value="1003"></el-option>
				    	</el-select>
				    </el-col>
				  </el-form-item>
				</el-col>
				 <el-col :span="6">
				  <el-form-item label="实付金额" prop="sendMoney">
				  	<el-col :span="16">
				    <el-input type='text' v-model="addForm.sendMoney" placeholder="请输入实付金额"></el-input> 
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
					      	<i class="el-icon-plus" @click="addTRow()" style="cursor: pointer;color: #008cba;font-weight:bold;"></i>
					      	&nbsp;
					        <i class="el-icon-delete" @click="deleteTRow(scope.$index)" style="cursor: pointer;color: #008cba;font-weight:bold;"></i>
					      </template>
					 </el-table-column>
					<el-table-column 
					      align="center"
					      label="商品"
					      prop="productName"
					      width="200">
					      <template slot-scope="scope">
							  <el-select v-model="scope.row.productId" filterable placeholder="点击选择" @change="selectProductObj(scope.$index)">
							    <el-option v-for="(item, index) in productList" v-bind:label="item.pingyinStr" v-bind:value="item.id"></el-option>
							  </el-select>
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
					      <template slot-scope="scope">
					      <span style="width:120px;">
					      	<el-input type='text' v-model="scope.row.purchaseNum" @change="changeSetRow(scope.$index)"></el-input>
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
				        prop="orderMoney"
				        label="单价"
				        width="100">
					      <template slot-scope="scope">
					      <span style="width:80px;">
					      	<el-input type='text' v-model="scope.row.orderMoney" @change="changeSetRow(scope.$index)"></el-input>
					      </span>
					      </template>
				    </el-table-column>
					<el-table-column
						align="center"
				        prop="discount"
				        label="折扣(%)"
				        width="100">
					      <template slot-scope="scope">
					      <span style="width:120px;">
					      	<el-input type='text' v-model="scope.row.discount" @change="changeSetRow(scope.$index)"></el-input>
					      </span>
					      </template>
				    </el-table-column>
					<el-table-column
						align="center"
				        prop="disMoney"
				        label="折后单价"
				        width="100">
					      <template slot-scope="scope">
					      <span style="width:120px;">
					      	<el-input type='text' v-model="scope.row.disMoney" @change="changeSetRow(scope.$index)"></el-input>
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
			 </el-row><br/>
			 <el-row type="flex">
				<el-col :span="20">&nbsp;</el-col>
				 <el-col :span="4">
				 	<el-col :span="10" push="2">
				 	 <span style="font-weight:bold;">总合计：</span>
				 	</el-col>
				 	<el-col :span="10" push="5">
				 	 <span style="font-weight:bold;">{{addForm.orderSum}}</span>
				 	</el-col>
				</el-col>
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
	<script src="${ctxStatic}/modules/shop/utils.js" type="text/javascript"></script>
	<script src="${ctxStatic}/modules/shop/shopPurchaseOrderForm.js" type="text/javascript"></script>
</html>