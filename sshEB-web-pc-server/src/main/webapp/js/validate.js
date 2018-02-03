/**
	用途：检查输入字符串是否为空或者全部都是空格
	输入：str：字符串
	返回：
	如果全是空返回true,否则返回false
*/
function isNull(str) {
    if (str == "") return true;
    var regu = "^[ ]+$";
    return regu.test(str);
}

/**
	用途：检查输入对象的值是否符合E-Mail格式
	输入：email 输入的字符串
	返回：如果通过验证返回true,否则返回false
*/
function isEmail(email){
    var emailReg = /^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
    return emailReg.test(email);
}

/**
	用途：检查输入非负整数（正整数、0）
	输入：integer：字符串
	返回：如果通过验证返回true,否则返回false
*/
function checkInteger(integer) {
	var integerReg = /^\d+$/;
	return integerReg.test(integer);
}

/**
	用途：检查输入正整数
	输入：positiveInteger：字符串
	返回：如果通过验证返回true,否则返回false
*/
function checkPositiveInteger(positiveInteger) {
	var positiveIntegerReg = /^[1-9][0-9]*$/;
	return positiveIntegerReg.test(positiveInteger);
}

/**
	用途：非负浮点数（正浮点数、0）
	输入：positiveFloating：字符串
	返回：如果通过验证返回true,否则返回false
*/
function checkFloating(floating) {
	var floatingReg = /^\d+(\.\d+)?$/;
	return floatingReg.test(floating);
}

/**
	用途：正浮点数
	输入：floating：字符串
	返回：如果通过验证返回true,否则返回false
*/
function checkPositiveFloating(positiveFloating) {
	var positiveFloatingReg = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
	return positiveFloatingReg.test(positiveFloating);
}

/**
	用途：检查输入的Email信箱格式是否正确
	输入：email：字符串
	返回：如果通过验证返回true,否则返回false
*/
function checkEmail(email) {
    var emailReg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
    return emailReg.test(email);
}
 
/**
	用途：检查输入的电话号码格式是否正确
	输入：phone：字符串
	返回：如果通过验证返回true,否则返回false
*/
function checkPhone(phone) {
    var phoneRegWithArea = /^[0][1-9]{2,3}-[0-9]{5,10}$/;
    var phoneRegNoArea = /^[1-9]{1}[0-9]{5,8}$/;
    if (phone.length > 9) {
        return phoneRegWithArea.test(phone);
    } else {
    	return phoneRegNoArea.test(phone);
    }
}

/**
	用途：检查输入手机号码是否正确
	输入：mobile：字符串
	返回：如果通过验证返回true,否则返回false
*/
function checkMobile(mobile) {
    var mobileReg = /^[1][3,4,5,7,8][0-9]{9}$/;
    return mobileReg.test(mobile);
}

/**
	用途：密码（数字、字母、符号至少两种，6~20位，且必须以字母开头）
	输入：password：字符串
	返回：如果通过验证返回true,否则返回false
*/
function checkPassword(password) {
	var passwordReg = /^(?![0-9@_])(?![a-zA-Z]+$)[0-9A-Za-z@_]{6,20}$/;
	return passwordReg.test(password);
}

/**
	用途：中文、数字、字母、-、_
	输入：cn：字符串
	返回：如果通过验证返回true,否则返回false
*/
function checkCN(cn) {
	var cnReg = /^[\u4e00-\u9fa5_a-zA-Z0-9-]+$/;
	return cnReg.test(cn);
}

/**
	检查输入的字符是否具有特殊字符
	输入:quote:字符串
	返回:true 或 flase; true表示包含特殊字符
	主要用于添加地址的时候验证
*/
function checkQuote(quote) {
    var items = new Array("~", "`", "!", "@", "#", "$", "%", "^", "&", "*", "{", "}", "[", "]");
    items.push(":", ";", "'", "|", "\\", "<", ">", "?", "/", "<<", ">>", "||", "//",".");
    items.push("admin", "administrators", "administrator", "管理员", "系统管理员");
    items.push("select", "delete", "update", "insert", "create", "drop", "alter", "trancate");
    str = str.toLowerCase();
    for (var i = 0; i < items.length; i++) {
        if (quote.indexOf(items[i]) >= 0) {
            return true;
        }
    }
    return false;
}

/**
	检查输入的字符是否具有数字
	返回:true包含数字
*/
function checkIsContainNum(num) {
    var reg = /^(?=.*\d.*\b)/;
    return reg.test(num);
}

/**
	身份证号合法性验证
	支持15位和18位身份证号
	支持地址编码、出生日期、校验位验证
*/
function IdentityCodeValid(code) {
	var city = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江 ", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北 ", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏 ", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外 " };
	var pass = true;
	if (!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)) {
	    pass = false;
	} else if (!city[code.substr(0, 2)]) {
	    pass = false;
	} else {
	    //18位身份证需要验证最后一位校验位
	    if (code.length == 18) {
	        code = code.split('');
	        //∑(ai×Wi)(mod 11)
	        //加权因子
	        var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
	        //校验位
	        var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
	        var sum = 0;
	        var ai = 0;
	        var wi = 0;
	        for (var i = 0; i < 17; i++) {
	            ai = code[i];
	            wi = factor[i];
	            sum += ai * wi;
	        }
	        var last = parity[sum % 11];
	        if (parity[sum % 11] != code[17]) {
	            pass = false;
	        }
	    }
	}
	return pass;
}

/**
 	对Date的扩展，将 Date 转化为指定格式的String 
	月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
	年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
	例子： 
	(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
	(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
 */ 
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1,                 //月份 
        "d+": this.getDate(),                    //日 
        "h+": this.getHours(),                   //小时 
        "m+": this.getMinutes(),                 //分 
        "s+": this.getSeconds(),                 //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds()             //毫秒 
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
	调整日期格式为:"年-月-日"格式
 */
function ChangeDateFormat(jsondate) {
    jsondate = jsondate.replace("/Date(", "").replace(")/", "");
    if (jsondate.indexOf("+") > 0) {
        jsondate = jsondate.substring(0, jsondate.indexOf("+"));
    } else if (jsondate.indexOf("-") > 0) {
        jsondate = jsondate.substring(0, jsondate.indexOf("-"));
    }
    var date = new Date(parseInt(jsondate, 10));
    var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    return date.getFullYear() + "-" + month + "-" + currentDate;
}

/**
	将毫秒日期数据转为指定日期格式
	转化为:"月-日"格式
 */
function formatDate(now) {
    now = now.replace("/Date(", "").replace(")/", "");//去掉毫秒格式数据的Date()格式
    now = new Date(parseInt(now));
    var year = now.getYear() + 1900;
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    return month + "-" + date;
}

/**
	将毫秒日期数据转为指定日期格式
	转化为:"年-月-日"格式
 */
function formatDate2(now) {
    now = now.replace("/Date(", "").replace(")/", "");//去掉毫秒格式数据的Date()格式
    now = new Date(parseInt(now));
    var year = now.getYear() + 1900;
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    return year + "-" + month + "-" + date;
}

/**
 * 获取当前时间，格式YYYY-MM-DD
 */
function getNowFormatDate() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + "-" + month + "-" + strDate;
    return currentdate;
}
