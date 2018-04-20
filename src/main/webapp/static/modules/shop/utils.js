
var numberUtil = {
	add : function(arg1, arg2, n) {
		return accAdd(arg1*1, arg2*1, n*1);
	},
	sub : function(arg1, arg2, n) {
		return accSub(arg1*1, arg2*1, n*1);
	},
	mul : function(arg1, arg2, n) {
		return accMul(arg1*1, arg2*1, n*1);
	},
	div : function(arg1, arg2, n) {
		return accDiv(arg1*1, arg2*1, n*1);
	}
}

/** **************************浮点加减,金额计算方法*************************** */
// TODO;浮点计算部分
// 乘法函数，用来得到精确的乘法结果
// 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
// 调用：accMul(arg1,arg2)
// 返回值：arg1乘以arg2的精确结果
function accMul(arg1, arg2, n) {
	if (!n)
		n = 2;
	arg1 = computePre(arg1);
	arg2 = computePre(arg2);
	if (arg1 == "" || arg1 == undefined)
		arg1 = 0;
	if (arg2 == "" || arg2 == undefined)
		arg2 = 0;
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length;
	} catch (e) {
	}
	returnVal = Number(s1.replace(".", "")) * Number(s2.replace(".", ""))
			/ Math.pow(10, m);
	return parseFloat(returnVal).toFixed(n) * 10000 / 10000;
}

// 给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function(arg) {
	return accMul(arg, this);
}

/**
 * 计算处理前方法（暂时去除千分位）
 * 
 * @param {}
 *            arg
 * @return {}
 */
function computePre(arg) {

	if (!arg) {
		return arg;
	}
	if (typeof(arg) == "string") {
		arg = arg.trim();
	} else {
		// 检测金额
		// var re = /^((([1-9]{1}\\d{0,14}))|([0]{1}))([.][0-9]{1,6})?$/;
		// //alert(arg+"==>"+re.test(arg))
		// if (re.test(arg)) {
		// // if (isNaN(arg)) {
		// // return arg;
		// // }
		// return arg;
		// }
		if (!isNaN(arg)) {
			return arg;
		}
	}
	try {
		var str = arg.toString();
		str = str.replaceAll(",", "");
		var i = str.indexOf(".");
		var str1 = str;
		if (i > 0) {
			str1 = str.substr(0, i);
		}
		if (str1.length > 15) {
			alert("输入数值过大");
			return 0;
		}
	} catch (e) {
		return 0;
	}
	return Number(str);
}

// 除法函数，用来得到精确的除法结果
// 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
// 调用：accDiv(arg1,arg2)
// 返回值：arg1除以arg2的精确结果
function accDiv(arg1, arg2, n) {
	if (!n)
		n = 2;
	arg1 = computePre(arg1);
	arg2 = computePre(arg2);
	if (arg1 == "" || arg1 == undefined)
		arg1 = 0;
	if (arg2 == "" || arg2 == undefined)
		arg2 = 0;
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		returnVal = (r1 / r2) * pow(10, t2 - t1);
		return parseFloat(returnVal).toFixed(n);
	}
}

// 加法函数，用来得到精确的加法结果
// 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
// 调用：accAdd(arg1,arg2)
// 返回值：arg1加上arg2的精确结果
function accAdd(arg1, arg2, n) {
	if (!n)
		n = 2;
	arg1 = computePre(arg1);
	arg2 = computePre(arg2);
	if (arg1 == "" || arg1 == undefined)
		arg1 = 0;
	if (arg2 == "" || arg2 == undefined)
		arg2 = 0;
	if (isNaN(n)) {
		n = 0;
	}
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length
	} catch (e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length
	} catch (e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2))
	return parseFloat((arg1 * m + arg2 * m) / m).toFixed(n) * 10000 / 10000;
}

// 给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function(arg) {
	return accAdd(arg, this);
}

// 减法函数
function accSub(arg1, arg2, n) {
	if (!n)
		n = 2;
	arg1 = computePre(arg1);
	arg2 = computePre(arg2);
	if (arg1 == "" || arg1 == undefined)
		arg1 = 0;
	if (arg2 == "" || arg2 == undefined)
		arg2 = 0;
	var r1, r2, m;
	if (isNaN(n)) {
		n = 0;
	}
	try {
		r1 = arg1.toString().split(".")[1].length
	} catch (e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length
	} catch (e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2));
	// last modify by deeka
	// 动态控制精度长度
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}
// /给number类增加一个sub方法，调用起来更加方便
Number.prototype.sub = function(arg) {
	return accSub(arg, this);
};

/** 浮点数乘法运算 */
function FloatMul(arg1, arg2, arg3, choose) {
	var value1 = $('#' + arg1).val();
	var value2 = $('#' + arg2).val();
	if (value1 != "" && value2 != "") {
		if (arg3 == "") {
			return accMul(value1, value2, 2);
		} else {
			returnNum = accMul(value1, value2, 2);
			if (choose == '1') {
				$('#' + arg3).val(returnNum);
			} else {
				var newReturnMoney = moneyFormat2(returnNum);
				$('#' + arg3).val(returnNum);
				$('#' + arg3 + '_v').val(newReturnMoney);
			}
		}
	} else
		return false;
}

/**
 * 浮点除法运算 arg3 为空时直接输出 不为空时根据id输出
 */
function FloatDiv(arg1, arg2, arg3) {
	var value1 = $('#' + arg1).val();
	var value2 = $('#' + arg2).val();
	var t1 = 0, t2 = 0, r1, r2;
	if (value1 != "" && value2 != "") {
		var thisVal = accDiv(value1, value2, 2);
		if (arg3 == "") {
			return thisVal;
		} else {
			var newReturnMoney = moneyFormat2(thisVal);
			$('#' + arg3).val(thisVal);
			$('#' + arg3 + '_v').val(newReturnMoney);
		}
	} else {
		return false;
	}
}

/* 四舍五入 */
function floatRound(floatValue, scale) {
	if (scale == 0) {
		return Math.round(floatValue);
	} else {
		var cutNumber = Math.pow(10, scale);
		return Math.round(floatValue * cutNumber) / cutNumber;
	}
}

//判断是否为整数
function isInteger(chars) {
	var re =/^\d*$/;
	return re.test(chars);
}
// 判断字符串是否为浮点数
function isNumber(chars) {
	return isNaN(chars) ? false : true;
}

