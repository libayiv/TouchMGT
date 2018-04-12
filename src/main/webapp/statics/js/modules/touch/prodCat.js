$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'touch/category/list',
        datatype: "json",
        colModel: [			
			{ label: '编号', name: 'cate_id', width: 45, editable:true },
			{ label: '产品类别名称', name: 'cate_name', width: 100 },
			{ label: '操作', name: 'operation', width: 80, formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
		                type: "GET",
		                url: baseURL + "sys/permitted/hasPermission?permission=touch:category:update",
		                contentType: "application/json",
		                async:false,
		                success: function(r){
		                    if(r.code === 0){
		                    	hasPermission = r.hasPermission;
		                    }else{
		                    	hasPermission = false;
		                    }
		                }
		            });
				}
				if(hasPermission){
					return '<a class="btn btn-primary" onclick="vm.update(\'' + row.cate_id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>';
				}
				return '';
			} },
        ],
		viewrecords: true,
        height: 'auto',
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        //multiselect: true,
        //cellEdit:true,
       // cellurl: baseURL +"touch/banner/updateSortNum",
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        },
        loadComplete: function (res) {
            // res是服务器返回的数据
        /*	vm.configList = res.config;*/
        },
        beforeSubmitCell :function(rowid, cellname, value, iRow, iCol){  
            // 传递参数  
        	var platform= $("#jqGrid").getCell(rowid,'platform');
        	var data = {'pid':rowid, 'sortNum':value, 'platform':platform};  
            return data;
         },
         afterSubmitCell:function(serverresponse, rowid, cellname, value, iRow, iCol){
        	 //修改失败
        	 if(serverresponse.responseJSON.code == 500){
        		 alert(serverresponse.responseJSON.msg);
        	 }else{
                 alert('操作成功', function(){
                     vm.reload();
                 });        		 
        	 }
         }
    });
    
    new AjaxUpload('#cat_upload', {
        action: baseURL + "touch/fileupload/upload?modularName=prodCat",
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
                alert('只支持jpg、png、gif格式的图片！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
            	vm.cat.cate_coversrc = r.fileName;
            	$("#cat_img").attr("src", r.url);
                alert("图片上传成功");
            }else{
                alert(r.msg);
            }
        }
    });
    
});
var hasPermission;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            title: null
        },
        showList: true,
        title:null,
        cat:{}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        update: function (pid) {
            vm.showList = false;
            vm.title = "编辑";
            vm.getProdCat(pid);
        },
        saveOrUpdate: function () {
        	var re = /^[0-9]+$/ ;
        	if(!re.test(vm.cat.cate_id)){    
        		alert("序号请填写正整数!");
       		 	return;
        	}         	
            var url = vm.cat.cate_id == null ? "touch/category/save" : "touch/category/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.cat),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        getProdCat: function(pid){
        	$("#cat_img").removeAttr("src");
            $.get(baseURL + "touch/category/info/"+pid, function(r){
                vm.cat = r.cat;
                if(r.cat.cate_coversrc != null && r.cat.cate_coversrc != ''){
                
                	$("#cat_img").attr("src", localStorage.fileUrlPath + r.cat.cate_coversrc);
                } else {
                	/*$("#banner_img").attr("width", "100px");
                	$("#banner_img").attr("height", "100px");*/
                }
                
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'cate_name': vm.q.title},
                page:page
            }).trigger("reloadGrid");
        }
    }
});



