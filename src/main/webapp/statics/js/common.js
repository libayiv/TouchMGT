//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

var baseURL = "../../";
var webThumb="http://testafintf.bfsuma.com";
//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//全局配置
$.ajaxSetup({
	dataType: "json",
	cache: false
});

//重写alert
window.alert = function(msg, callback){
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//重写confirm式样框
window.confirm = function(msg, callback){
	parent.layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }
    
    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    return grid.getGridParam("selarrrow");
}

//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

//获取数据字典
function getDictList(dictCode){
	var dictList;
	$.ajax({
        type: "GET",
        url: baseURL + "touch/dict/getDict?dictCode="+dictCode,
        contentType: "application/json",
        async:false,
        success: function(r){
            if(r.code === 0){
            	dictList = r.dictList;
            }else{
            	alert("服务器异常");
            }
        }
    });
	return dictList;
}

//判断是否为非零正整数
function isPositiveInt(val) {
	var re = /^[1-9]\d*$/ ;
	if(!re.test(val)){
		return true;
	}else{
		return false;
	}	 
}


function getUrlQueryString(name) { 
    var reg = new RegExp("(^|&\?)" + name + "=([^&]*)(&|$)", "i"); //这里用了蓝色那个问号是因为有可能获取的是第一个参数
    var r = window.location.search.substr(1).match(reg); 
    if (r != null) return unescape(r[2]); return null; 
}


/**
 * 加密（明文,几次）
 */
function encodeBase64(content,times){
	var code="";    
    var num=1;    
    if(typeof times=='undefined'||times==null||times==""){    
       num=1;    
    }else{    
       var vt=times+"";    
       num=parseInt(vt);    
    }    
    if(typeof content=='undefined'||content==null||content==""){    
    }else{    
        $.base64.utf8encode = true;    
        code=content;    
        for(var i=0;i<num;i++){    
           code=$.base64.btoa(code);    
        }    
    }    
    return code;  
}