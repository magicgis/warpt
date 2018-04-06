//工具类
var utils = function (){};

/**
 * 导出数据
 * tid : table ID 
 */
 utils.saleOrderExExcel = function(tid){
	var dataObje =  this.FindHtmlData(tid);
	$("#exsums").val(dataObje.lastsum);
	$("#exnames").val(JSON.stringify(dataObje.namearray));
	$("#exdatas").val(JSON.stringify(dataObje.dataarray));
	$("#exexcelForm").submit();
};

/**
 * 账单明细分析数据导出
 */
utils.saleOrderItmeExExcel = function(){
	
	var obdata = this.saleBillingHtml();
	
	$("#exnames").val(obdata.tible);
	$("#exriqi").val(JSON.stringify(obdata.riqi));
	$("#exzhouqi").val(JSON.stringify(obdata.zhouqi));
	$("#exdatas").val(JSON.stringify(obdata.datas));
	$("#exexcelForm").submit();
}

/**
 * 账单明细分析数据导出
 */
utils.purchaseOrderItmeExExcel = function(){
	
	var obdata = this.saleBillingHtml();
	
	$("#exnames").val(obdata.tible);
	$("#exriqi").val(JSON.stringify(obdata.riqi));
	$("#exzhouqi").val(JSON.stringify(obdata.zhouqi));
	$("#exdatas").val(JSON.stringify(obdata.datas));
	$("#exexcelForm").submit();
}


/**
 * 账单明细分析数据导出
 */
utils.saleBillingHtml = function(){
	
	var retObj = new Object();
	
	var name = $("#divnames").find("span").eq(0).text();
	name+=$("#divnames").find("span").eq(1).text();
	
	retObj.tible = name;
	var htmls = $("#contentTable");
	var length = $(htmls).find("thead").find("tr").eq(0).find("th").length; 
	
	var riqi = new Array();
	var zhouqi = new Array();
	var data = new Array();
	for(var j=0;j<2;j++){
		for(var i=0;i<length;i++){
			if(j==1 && i==length-2){
				 break;
			}
			var thname =  $(htmls).find("thead").find("tr").eq(j).find("th").eq(i).text();
			thname = $.trim(thname);
			if(j==0){
				riqi.push(thname);
			}else{
				zhouqi.push(thname);
			}
		}
	}
	
	var tbodylength = $(htmls).find("tbody").find("tr").length;
	for(var j=0; j<tbodylength; j++){
		var tboArray = new Array();
		for(var i=0;i<length;i++){
			var tbname =  $(htmls).find("tbody").find("tr").eq(j).find("td").eq(i).text();
			tbname = $.trim(tbname);
			tboArray.push(tbname);
		}
		data.push(tboArray);
	}
	
	retObj.riqi = riqi;
	retObj.zhouqi = zhouqi;
	retObj.datas = data;
	
	return retObj;
}


/**
 * 查询html上的数据
 * tid : table ID
 */
utils.FindHtmlData = function(tid){
	 var datObje = new Object();
	 var html = $("#"+tid);
	 var theadsum = $(html).find("thead").find("th").length;
	 var tbodytrsum = $(html).find("tbody").find("tr").length;
	 
	 var nameArray = new Array();
	 var dataArray = new Array();
	 
	 var last = false;
	 var lastsum = 0;
	 
	 
	 for(var j=0; j<tbodytrsum; j++){
		 var tboArray = new Array();
		 
		 //最后一行需要做特殊处理
		 if(j == tbodytrsum-1){
			 last = true;
			 lastsum  = $(html).find("tbody").find("tr").eq(j).find("td").eq(0).attr("colspan");
			 
		 }
		 
		 for(var i=0; i <theadsum; i++){
			 var name,data;
				 if(j==0){
					 name =  $(html).find("thead").find("th").eq(i).text();
					 name = $.trim(name);
					 nameArray.push(name);
				 }
				 
				 if(last && i>lastsum){
					 break;
				 }
				 data = $(html).find("tbody").find("tr").eq(j).find("td").eq(i).text();
				 data = $.trim(data);
				 
				 tboArray.push(data);
		 }
		
		 dataArray.push(tboArray);
	 }
	 
	 datObje.namearray = nameArray;
	 datObje.dataarray = dataArray;
	 datObje.lastsum = lastsum;
	 return datObje;
}