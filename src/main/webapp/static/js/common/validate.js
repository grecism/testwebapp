
// onkeyup="this.value=this.value.replace(/[^\w\-]/g,'')" onpaste="this.value=this.value.replace(/[^\w\-]/g,'')"  电话验证
// 空
function empty(s){
	if(s==""){
		return false;
	}
	return true;
}
// 常用验证
// EMAIL验证
function isEmail(s){
	// var reg=;
	//var patrn=/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;   
	var patrn = /^\w+([-+.@\w])*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	if (!patrn.exec(s)) return false  ;
	return true;
	}
// 国内电话传真
function isTel(s){   
// var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?(\d){1,12})+$/;
	var patrn=/[^\d-]/;   
	if (patrn.exec(s)) {return false;} ;
	if(s.length<7){return false;}
	return true;  
}   
// 手机
function isPhone(s){ 
	var patrn=/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/;  
	if (!patrn.exec(s)) return false ;
	return true ;
}
//香港手机号码
function isHongKongPhone(s){
	var patrn=/^(00)?852(56|59|6|9)\d{7}$/;
	if (!patrn.exec(s)) return false ;
	return true ;
}
  
// QQ验证
function isQQ(s){
	var patrn=/[1-9][0-9]{4,}/;   
	if (!patrn.exec(s)) return false;
	return true; 
	}
// 邮编
function isZipCode(s){
	var patrn=/^[1-9][0-9]{5}$/;
	if (!patrn.exec(s)&&!isSpace(s)) return false;
	return true;
	}
// 身份证
function isID(s){
	var patrn=/d{15}|d{18}/;   
	if (!patrn.exec(s)) return false;
	return true; 
	}
// 中文
function isChinese(s){
	var patrn=/d{15}|d{18}/;
	if (!patrn.exec(s)) return false;
	return true; 
	}
// 是否全数字
function isDigit(s) 
{ 
	var patrn=/^[0-9]{1,20}$/; 
	if (!patrn.exec(s)) return false ;
	return true ;
}
//是否数字和.
function isRate(s) 
{ 
	var patrn=/^[0-9]{1,20}.?[0-9]*$/; 
	if (!patrn.exec(s)) return false ;
	return true ;
} 
// 是否中文
function isZw(s){
	 var patrn=/[\u4e00-\u9fa5]/; 
	if (!patrn.exec(s)) return false; 
	return true; 

	}
// 匹配帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)：
function isUserName(s){
	 var patrn=/^[a-zA-Z][a-zA-Z0-9_]{4,15}$/; 
	if (!patrn.exec(s)) return false; 
	return true; 

	}
// 验证是否含有^%&’,;=?$”等字符：“”
function isSpecial(s){
	 var patrn=/[\^%&',;=?$x22]+/; 
	if (patrn.exec(s)) return false; 
	return true; 
	}
// 是否是字母数字汉字
function standard(s){
	var path = /^[\u4E00-\u9FA5A-Za-z0-9_]+$/;
	if (!path.exec(s)) {
		return false;
	}
	return true;
}
//是否是字母汉字
function isName(s){
	var path = /^[\u4E00-\u9FA5A-Za-z]+$/;
	if (!path.exec(s)) {
		return false;
	}
	return true;
}
function isSpace(s){
	return (/ /g.test(s));
}
function isLetters(s){
	var path=/^[a-zA-Z]+$/;
	return(path.test(s));
}
//知音卡号
function cardNumber(s){
	if(s.length!=11){
		return false;
	}
	if(s.slice(2,10)%7!=s.slice(10,11)){
		return false;
	}else{
		return true;
	}
}
//转换为大写
function uppercase(obj){
	var val=obj.value.toLocaleUpperCase();
	obj.value=val;
}
//定义检测函数,返回0/1/2/3分别代表无效/差/一般/强
function getResult(s){
 if(s.length < 4){
  return 0;
 }
 var ls = 0;
 if (s.match(/[a-z]/ig)){
  ls++;
 }
 if (s.match(/[0-9]/ig)){
  ls++;
 }
  if (s.match(/(.[^a-z0-9])/ig)){
  ls++;
 }
 if (s.length < 6 && ls > 0){
  ls--;
 }
 return ls;
}
// 身份证
function isIdCardNo(num) {
    num = num.toUpperCase();
    // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
    if (! (/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
       // layer.msg('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。',2,-1);
        return false;
    }
    // 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
    // 下面分别分析出生日期和校验位
    var len, re;
    len = num.length;
    if (len == 15) {
        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
        var arrSplit = num.match(re);
        // 检查生日日期是否正确
        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {
        	//layer.msg('您输入的身份证号不正确请核对后重新输入！',2,-1);
            return false;
        } else {
            // 将15位身份证转成18位
            // 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
            var nTemp = 0,
            i;
            num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
            for (i = 0; i < 17; i++) {
                nTemp += num.substr(i, 1) * arrInt[i];
            }
            num += arrCh[nTemp % 11];
            return num;
        }
    }
    if (len == 18) {
        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
        var arrSplit = num.match(re);
        // 检查生日日期是否正确
        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {
        	//layer.msg('您输入的身份证号不正确请核对后重新输入！',2,-1);
            return false;
        } else {
            // 检验18位身份证的校验码是否正确。 //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
            var valnum;
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
            var nTemp = 0,
            i;
            for (i = 0; i < 17; i++) {
                nTemp += num.substr(i, 1) * arrInt[i];
            }
            valnum = arrCh[nTemp % 11];
            if (valnum != num.substr(17, 1)) {
            	//layer.msg('您输入的身份证号不正确请核对后重新输入！',2,-1);
                return false;
            }
            return num;
        }
    }
    return false;
} // JavaScript Document


var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子   
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X   
function IdCardValidate(idCard) { 
    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格                     
    if (idCard.length == 15) {   
        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
    } else if (idCard.length == 18) {   
        var a_idCard = idCard.split("");                // 得到身份证数组   
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
            return true;   
        }else {   
            return false;   
        }   
    } else {   
        return false;   
    }   
}   
/**  
 * 判断身份证号码为18位时最后的验证位是否正确  
 * @param a_idCard 身份证号码数组  
 * @return  
 */  
function isTrueValidateCodeBy18IdCard(a_idCard) {   
    var sum = 0;                             // 声明加权求和变量   
    if (a_idCard[17].toLowerCase() == 'x') {   
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += Wi[i] * a_idCard[i];            // 加权求和   
    }   
    valCodePosition = sum % 11;                // 得到验证码所位置   
    if (a_idCard[17] == ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
}   
/**  
  * 验证18位数身份证号码中的生日是否是有效生日  
  * @param idCard 18位书身份证字符串  
  * @return  
  */  
function isValidityBrithBy18IdCard(idCard18){   
    var year =  idCard18.substring(6,10);   
    var month = idCard18.substring(10,12);   
    var day = idCard18.substring(12,14);   
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
    // 这里用getFullYear()获取年份，避免千年虫问题   
    if(temp_date.getFullYear()!=parseFloat(year)   
          ||temp_date.getMonth()!=parseFloat(month)-1   
          ||temp_date.getDate()!=parseFloat(day)){   
            return false;   
    }else{   
        return true;   
    }   
}   
  /**  
   * 验证15位数身份证号码中的生日是否是有效生日  
   * @param idCard15 15位书身份证字符串  
   * @return  
   */  
  function isValidityBrithBy15IdCard(idCard15){   
      var year =  idCard15.substring(6,8);   
      var month = idCard15.substring(8,10);   
      var day = idCard15.substring(10,12);   
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
      if(temp_date.getYear()!=parseFloat(year)   
              ||temp_date.getMonth()!=parseFloat(month)-1   
              ||temp_date.getDate()!=parseFloat(day)){   
                return false;   
        }else{   
            return true;   
        }   
  }   
//去掉字符串头尾空格   
function trim(str) {   
    return str.replace(/(^\s*)|(\s*$)/g, "");   
}
/**  
 * 通过身份证判断是男是女  
 * @param idCard 15/18位身份证号码   
 * @return 'female'-女、'male'-男  
 */  
function maleOrFemalByIdCard(idCard){   
    idCard = trim(idCard.replace(/ /g, ""));        // 对身份证号码做处理。包括字符间有空格。   
    if(idCard.length==15){   
        if(idCard.substring(14,15)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else if(idCard.length ==18){   
        if(idCard.substring(14,17)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else{   
        return null;   
    }   
}
/**
 * 
 * 根据身份证提取证件号
 */
 function getBirthdayFromIdCard(idCard) {
  	var birthday = "";
	if(idCard != null && idCard != ""){
		if(idCard.length == 15){
			birthday = "19"+idCard.substr(6,6);
		} else if(idCard.length == 18){
			birthday = idCard.substr(6,8);
		}
	
		birthday = birthday.replace(/(.{4})(.{2})/,"$1-$2-");
	}
	
	return birthday;
  }




/**
 * validate requires jQuery.js Copyright(c) ipanel liaolj version 2.0
 * 
 * 2.0版本不支持1.0版本 请不要用2.0版本去替换掉1.0的版本
 */
 (function($){
	 /**
		 * @param formId
		 *            被验证表单的ID
		 * @param options
		 *            默认的出错信息
		 */
	 $.validate = function(formId,options){
		var defaults = {
			field : '该字段',
			required : '不能为空!',
			email : '邮件格式不合法!',
			phone : '电话格式不合法!',
			regex : '不匹配正则表达!',
			integer : '必须为整数!',
			number : '必须为实数!',
			range : '必须在{1}至{2}之间!',
			max : '最大值为{1}!',
			min :'最小值为{1}!',
			equal : '必须相等!',
			error : '输入类型或验证类型不正确!'
		};
		var flag = true;
		var options = $.extend(defaults,options);
	
		// 判断一个变是否已定义
		var isUndefined = function(value){
			if(typeof(value)!='undefined')
				return false;
			else
				return true;
		};
		var trimed = function(value){
			return jQuery.trim(value);
		};
		// 解析对象的validate和field属性,生个一个object对象
		var parse = function($obj){
			var result = new Object();
			var validate = new Object();
			var validateValue = $obj.attr('validate');
			var fieldValue = $obj.attr('field');

			if(isUndefined(fieldValue)){
				result.field = '';               // 没有时默认为空字符串
			}
			else{
				result.field = trimed(fieldValue);
			}
			if(isUndefined(validateValue)){
				result.valis = '';                 // 不需要验证时，默认为''
			}
			else{
				var validates = trimed(validateValue).split(";");
				result.valis = '';
				for(var i=0;i<validates.length;i++){
					if(validates[i].indexOf(":")!=-1){
						var tempSplit = trimed(validates[i]).split(":");
						if(tempSplit.length==2){
							validate[trimed(tempSplit[0])] = new Object();
							result.valis+=" "+trimed(tempSplit[0]);
							var paramters = trimed(tempSplit[1]).split(",");
							if(paramters.length==1)
								validate[trimed(tempSplit[0])].firstParamter = trimed(paramters[0]);
							else if(paramters.length==2)
							{
								validate[trimed(tempSplit[0])].firstParamter = trimed(paramters[0]);
								validate[trimed(tempSplit[0])].secondParamter = trimed(paramters[1]);
							}
						}
						else if(tempSplit.length==1){
							result.valis+=" "+trimed(tempSplit[0]);
						}
					}
					else
					{
						result.valis = result.valis+" "+trimed(validates[i]);
					}
				}
			}
			result.validate = validate;
			return result;
		};
		// 判断某个字段的值是否为空
		var isNull= function($obj){
			if($obj.attr("type")=='checkbox' || $obj.attr("type")=='radio'){
				var name = $obj.attr("name");
				var flag = true;
				jQuery("#"+id+" *[name="+name+"]").each(function(){
					if($(this).attr("checked"))
					{
						flag = false;
						return false;
					}
				});
				return flag;
			}
			else{
				if(trimed($obj.val())!="")
					return false;
				else
					return true;
			}
		};
		// 获得焦点
		var getFocus = function($obj){
			$obj.focus();
		};
		// 判断是否是email
		var isEmail = function($obj){
			var regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\.\\w+([-.]\\w+)*";
			return isRegex($obj,regex);
		};
		// 判断是否是phone
		var isPhone = function($obj){
			var regex = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
			return isRegex($obj,regex);
		};
		// 判断是否满足正则表达式
		var isRegex = function($obj,regex){
			if(isUndefined(regex)) return false;
			var val = trimed($obj.val());
			if(val.match(regex)!=null) 
				return true;
			else
				return false;
		};
		// 判断是否为整数
		var isInt = function($obj){
			var regex= "^-?[1-9]\\d*$";
			return isRegex($obj,regex);
		};
		// 判断是否为数字
		var isNumbric = function(val){
			var regex = "^(-?\\d+)(\.\\d+)?$";
			return val.match(regex)!=null;
		};
		// 判断是否为实数
		var isNumber = function($obj){
			// var regex = "^-?([1-9]\\d*\.\\d*|0\.\\d*[1-9]\\d*|0?\.0+|0)$";
			var regex = "^(-?\\d+)(\.\\d+)?$";
			return isRegex($obj,regex);
		};
		// 判断是否在某个范围之内
		var isInRange = function($obj,max,min){
			if(isUndefined(max)||!isNumbric(max)) max=Infinity;
			if(isUndefined(min)||!isNumbric(min)) min=(-Infinity);
			if(max<min)
			{
				temp = max;
				max = min;
				min = temp;
			}
			var val = Number(trimed($obj.val()));
			if(val>=min && val<=max)
				return true;
			else
				return false;
		};

		// 判断是否小于最大值
		var isMax = function($obj,max){
			if(isUndefined(max)||!isNumbric(max)) max = Infinity;
			var val = Number(trimed($obj.val()));
			if(val>max)
				return false;
			else
				return true;
		};
		// 判断是否大于最小值
		var isMin = function($obj,min){
			if(isUndefined(min)||!isNumbric(min)) min=(-Infinity);
			var val = Number(trimed($obj.val()));
			if(val<min)
				return false;
			else
				return true;
		};
		// 判断是否相等
		var isEqual = function($obj,name){
			var val = trimed($obj.val());
			var compare = trimed(jQuery("#"+formId+" *[name="+name+"]").val());
			return val==compare;
		};
		// 参数替换
		var paraReplace = function(source,fParam,sParam){
			var temp = source;
			if(!isUndefined(fParam)&&!isUndefined(sParam))
			{
				if(Number(fParam)>Number(sParam)){
					t = fParam;
					fParam = sParam;
					sParam = t;
				}
			}
			if(!isUndefined(fParam))
				temp=temp.replace("{1}",fParam);
			if(!isUndefined(sParam))
				temp=temp.replace("{2}",sParam);
			return temp;
		};
		// 判断是否需要进行判断
		var isRequired = function(val){
			if(isUndefined(val))
				return false;
			if(val.indexOf("required")!=-1)
				return true;
			else
				return false;
		};
		// 去掉required
		var reqReplace = function(val){
			if(!isUndefined(val))
				return val.replace("required","");
			else
				return val;
		};
		// 打印出错信息
		var showError = function(type,val){
			var error ="";
			var fld;
			if(isUndefined(val.field)||trimed(val.field)=='')
				fld = options.field;
			else
				fld = val.field;
			if(type=='required')
				error = fld+options.required;
			else if(type=='email')
				error = options.email;
			else if(type=='phone')
				error = options.phone;
			else if(type=='regex'){
				error = fld+paraReplace(options.regex,val.validate.regex.firstParamter);
			}
			else if(type=='int')
				error = fld+options.integer;
			else if(type=='number')
				error = fld+options.number;
			else if(type=='range')
			{
				error = fld+paraReplace(options.range,val.validate.range.firstParamter,val.validate.range.secondParamter);
			}
			else if(type=='max')
				error = fld+paraReplace(options.max,val.validate.max.firstParamter);
			else if(type=='min')
				error = fld+paraReplace(options.min,val.validate.min.firstParamter);
			else if(type=='equal'){
				if(!isUndefined(val.validate.equal.firstParamter)){
					eq = jQuery("#"+formId+" *[name="+val.validate.equal.firstParamter+"]").attr("field");
					if(isUndefined(eq))
						error = fld+"与"+"另一个字段"+options.equal;
					else
						error = fld+"与"+eq+options.equal;
				}
			}
			else if(type=='error')
				error =options.error;
			else 
				error="未知异常!";
			alert(error);
		};
		// main
		jQuery("#"+formId+" *[name]").each(function(){
			var $this = $(this);
			var result = parse($this);
			var valises = result.valis;
			if(isNull($this)){
				if(isRequired(valises)){
					showError("required",result);
					getFocus($this);
					flag = false;
					return false;
				}					
			}
			else{
				var vals = reqReplace(valises);
				if(!isUndefined(vals)&& trimed(vals)!="")
				{
					var splitVal = trimed(vals).split(" ");
					for(var j=0;j<splitVal.length;j++)
					{
						if(splitVal[j]=='email'){
							if(!isEmail($this))
							{
								showError('email',result);
								getFocus($this);
								flag = false;
								return false;
							}
						}
						else if(splitVal[j]=='phone'){
							if(!isPhone($this))
							{
								showError('phone',result);
								getFocus($this);
								flag = false;
								return false;
							}
						}
						else if(splitVal[j]=='regex'){
							if(!isRegex($this,result.validate.regex.firstParamter))
							{
								showError('regex',result);
								getFocus($this);
								flag = false;
								return false;
							}
						}
						else if(splitVal[j]=='int'){
							if(!isInt($this))
							{
								showError('int',result);
								getFocus($this);
								flag = false;
								return false;
							}
						}
						else if(splitVal[j]=='number'){
							if(!isNumber($this))
							{
								showError('number',result);
								getFocus($this);
								flag = false;
								return false;
							}
						}
						else if(splitVal[j]=='range'){
							if(!isInRange($this,result.validate.range.firstParamter,result.validate.range.secondParamter))
							{
								showError('range',result);
								getFocus($this);
								flag = false;
								return false;
							}
						}
						else if(splitVal[j]=='max'){
							if(!isMax($this,result.validate.max.firstParamter))
							{
								showError('max',result);
								getFocus($this);
								flag = false;
								return false;
							}
						}
						else if(splitVal[j]=='min'){
							if(!isMin($this,result.validate.min.firstParamter))
							{
								showError('min',result);
								getFocus($this);
								flag = false;
								return false;
							}
						}
						else if(splitVal[j]=='equal'){
							if(!isEqual($this,result.validate.equal.firstParamter))
							{
								showError('equal',result);
								getFocus($this);
								flag = false;
								return false;
							}
						}
					}
				}
			}
		});
		return flag;
	 }})(jQuery);
