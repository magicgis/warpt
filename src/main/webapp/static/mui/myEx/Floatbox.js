/**
 * 浮点数字输入框
 * varstion 1.0.1
 * by swbssd
 * 
 */

(function($) {

	var touchSupport = ('ontouchstart' in document);
	var tapEventName = touchSupport ? 'tap' : 'click';
	var changeEventName = 'change';
	var holderClassName = 'mui-floatbox';
	var plusClassSelector = '.mui-btn-floatbox-plus,.mui-floatbox-btn-plus';
	var minusClassSelector = '.mui-btn-floatbox-minus,.mui-floatbox-btn-minus';
	var inputClassSelector = '.mui-input-floatbox,.mui-floatbox-input';

	var Floatbox = $.Floatbox = $.Class.extend({
		/**
		 * 构造函数
		 **/
		init: function(holder, options) {
			var self = this;
			if (!holder) {
				throw "构造 floatbox 时缺少容器元素";
			}
			self.holder = holder;
			options = options || {};
			options.step = parseFloat(options.step || 1);
			self.options = options;
			self.input = $.qsa(inputClassSelector, self.holder)[0];
			self.plus = $.qsa(plusClassSelector, self.holder)[0];
			self.minus = $.qsa(minusClassSelector, self.holder)[0];
			self.checkValue();
			self.initEvent();
		},
		/**
		 * 初始化事件绑定
		 **/
		initEvent: function() {
			var self = this;
			self.plus.addEventListener(tapEventName, function(event) {
				var val =  numberUtil.add(parseFloat(self.input.value),self.options.step,1);
				self.input.value = val.toString();
				$.trigger(self.input, changeEventName, null);
			});
			self.minus.addEventListener(tapEventName, function(event) {
				var val = numberUtil.sub(parseFloat(self.input.value),self.options.step,1);
				self.input.value = val.toString();
				$.trigger(self.input, changeEventName, null);
			});
			self.input.addEventListener(changeEventName, function(event) {
				self.checkValue();
				var val = parseFloat(self.input.value).toFixed(1);
				//触发顶层容器
				$.trigger(self.holder, changeEventName, {
					value: val
				});
			});
		},
		/**
		 * 获取当前值
		 **/
		getValue: function() {
			var self = this;
			return parseFloat(self.input.value);
		},
		/**
		 * 验证当前值是法合法
		 **/
		checkValue: function() {
			var self = this;
			var val = self.input.value;
			if (val == null || val == '' || isNaN(val)) {
				self.input.value = self.options.min || 0;
				self.minus.disabled = self.options.min != null;
			} else {
				var val = parseFloat(val);
				if (self.options.max != null && !isNaN(self.options.max) && val >= parseFloat(self.options.max)) {
					val = self.options.max;
					self.plus.disabled = true;
				} else {
					self.plus.disabled = false;
				}
				if (self.options.min != null && !isNaN(self.options.min) && val <= parseFloat(self.options.min)) {
					val = self.options.min;
					self.minus.disabled = true;
				} else {
					self.minus.disabled = false;
				}
				self.input.value = val;
			}
		},
		/**
		 * 更新选项
		 **/
		setOption: function(name, value) {
			var self = this;
			self.options[name] = value;
		}
	});

	$.fn.floatbox = function(options) {
		var instanceArray = [];
		//遍历选择的元素
		this.each(function(i, element) {
			if (element.floatbox) {
				return;
			}
			if (options) {
				element.floatbox = new Floatbox(element, options);
			} else {
				var optionsText = element.getAttribute('data-floatbox-options');
				var options = optionsText ? JSON.parse(optionsText) : {};
				options.step = element.getAttribute('data-floatbox-step') || options.step;
				options.min = element.getAttribute('data-floatbox-min') || options.min;
				options.max = element.getAttribute('data-floatbox-max') || options.max;
				element.floatbox = new Floatbox(element, options);
			}
		});
		return this[0] ? this[0].floatbox : null;
	}

	//自动处理 class='mui-locker' 的 dom
	$.ready(function() {
		$('.' + holderClassName).floatbox();
	});

}(mui))



var numberUtil = {
	add : function(arg1, arg2, n) {
		return accAdd(arg1, arg2, n);
	},
	sub : function(arg1, arg2, n) {
		return accSub(arg1, arg2, n);
	},
	mul : function(arg1, arg2, n) {
		return accMul(arg1, arg2, n);
	},
	div : function(arg1, arg2, n) {
		return accDiv(arg1, arg2, n);
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

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
	if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
		return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi" : "g")),
				replaceWith);
	} else {
		return this.replace(reallyDo, replaceWith);
	}
}
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * 只能输入正数+.5
 */
function regIsPointFive(fData) {
	var reg = new RegExp("^[0-9]+$|^[0-9]*\.5+$");
	return (reg.test(fData));
}