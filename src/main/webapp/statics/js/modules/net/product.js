$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'touch/JProd/list',
        datatype: "json",
        colModel: [			
			{ label: 'pro_id', name: 'pro_id', index: "pid",  key: true,hidden:true},
			{ label: 'SortNum', name: 'order_num' ,width: 60, editable:true},
			{ label: 'ProdName', name: 'pro_name', width: 60},
			{ label: 'Picture', name: 'pro_picture', formatter: function(value, options, row){
				return '<img src="'+ localStorage.fileUrlPath + value +'" width="100px;" height="100px;" />';
			} },
			{ label: 'Current inventory', name: 'buy_count', width: 60 },
			{ label: 'status', name: 'status', width: 60, formatter: function(value, options, row){
				var id='checked_' + row.pro_id;
				return value == 1 ? 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.disable(\'' + row.pro_id + '\')" checked/><label for="'+id+'" class="newlabel"></label>' : 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.enable(\'' + row.pro_id + '\')"/><label for="'+id+'" class="newlabel"></label>';
			} },
			{ label: '操作', name: 'operation', width: 80, formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
		                type: "GET",
		                url: baseURL + "sys/permitted/hasPermission?permission=touch:JProd:save",
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
					return '<a class="btn btn-primary" onclick="vm.update(\'' + row.pro_id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>';
				}
				return '';
			} },
        ],
		viewrecords: true,
        height: 'auto',
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: false, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        cellEdit:true,
        cellurl: baseURL +"touch/JProd/updateSortNum",
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
        }
        
    });
    
    new AjaxUpload('#product_upload', {
        action: baseURL + "touch/fileupload/upload?modularName=jproduct",
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
            	vm.product.pro_picture = r.fileName;
            	$("#product_img").attr("src", r.url);
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
        product:{}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
        	$("#product_img").removeAttr("src");
            vm.showList = false;
            vm.title = "新增";
            vm.product = { status:1};

        },
        update: function (pid) {
            vm.showList = false;
            vm.title = "编辑";
            vm.getbanner(pid);
        },
        getbanner: function(pid){
            $.get(baseURL + "touch/JProd/info/"+pid, function(r){
                vm.product = r.product;
                if(r.product.pro_picture != null && r.product.pro_picture != ''){
                	$("#product_img").attr("src", localStorage.fileUrlPath + r.product.pro_picture);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'title': vm.q.title},
                page:page
            }).trigger("reloadGrid");
        },
        search: function () {
            vm.showList = true;
            var page = 1;
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'pro_name': vm.q.title},
                page:page
            }).trigger("reloadGrid");
        },
        updateStatus: function(pids, status){
        	var data = {'pro_id':pids, 'status': status};
            $.ajax({
                type: "POST",
                url: baseURL + "touch/JProd/update",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function(r){
                    if(r.code == 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        enable: function(pid){
        	vm.updateStatus(pid, "1");
        },
        disable: function(pid){
        	vm.updateStatus(pid, "0");
        },
        saveOrUpdate: function () {
        	var product=vm.product
        	if (product.pro_integral.lastIndexOf(".") == 1) {// If there is no decimal point  
        		alert("积分请填写整数!");
       		 	return;
            }
        	if (product.stock.lastIndexOf(".") == 1) {// If there is no decimal point  
        		alert("库存请填写整数!");
       		 	return;
            }
        	if (product.limit_number.lastIndexOf(".") == 1) {// If there is no decimal point  
        		alert("限购数量请填写整数!");
       		 	return;
            }
            var url = vm.product.pro_id == null ? "touch/JProd/save" : "touch/JProd/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.product),
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
       
        
    }
});



