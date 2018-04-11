$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'touch/banner/list',
        datatype: "json",
        colModel: [			
			{ label: '编号', name: 'id', index: "pid", width: 45, key: true, hidden: true},
			{ label: '序号', name: 'rank', width: 45, editable:true },
			{ label: '封面', name: 'coversrc', formatter: function(value, options, row){
				return '<img src="'+ localStorage.fileUrlPath + value +'" width="100px;" height="100px;" />';
			} },
			{ label: '标题', name: 'title' },
			{ label: '是否显示', name: 'status', width: 60, formatter: function(value, options, row){
				var id='checked_' + row.id;
				return value == 1 ? 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.disable(\'' + row.id + '\')" checked/><label for="'+id+'" class="newlabel"></label>' : 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.enable(\'' + row.id + '\')"/><label for="'+id+'" class="newlabel"></label>';
			} },
			{ label: '跳转地址', name: 'value', width: 60 },
			{ label: '操作', name: 'operation', width: 80, formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
		                type: "GET",
		                url: baseURL + "sys/permitted/hasPermission?permission=touch:banner:save",
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
					return '<a class="btn btn-primary" onclick="vm.update(\'' + row.id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>';
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
        cellurl: baseURL +"touch/banner/updateSortNum",
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
        	vm.configList = res.config;
        },
        beforeSubmitCell :function(rowid, cellname, value, iRow, iCol){  
            // 传递参数  
        	var data = {'id':rowid, 'rank':value};  
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
    
    new AjaxUpload('#banner_upload', {
        action: baseURL + "touch/fileupload/upload?modularName=banner",
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
            	vm.banner.coversrc = r.fileName;
            	vm.banner.oriFileName = r.oriFileName;
/*            	$("#banner_img").removeAttr("width");
            	$("#banner_img").removeAttr("height");
*/            	$("#banner_img").attr("src", r.url);
                alert("图片上传成功");
            }else{
                alert(r.msg);
            }
        }
    });
    
});
var hasPermission;
var platformList = getDictList("PLATFORM");

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            title: null
        },
        showList: true,
        title:null,
        platformList:platformList,
        configList:{},
        banner:{
            status:1,
            is_single:0
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
        	$("#banner_img").removeAttr("src");
            vm.showList = false;
            vm.title = "新增";
        	$("#url").hide();
            vm.banner = {status:1,is_single:0, pc_valid:1, fileName:'', oriFileName:'', rank:0, playbackLength:10};

        },
        update: function (pid) {
            vm.showList = false;
            vm.title = "编辑";
            vm.getbanner(pid);
        },
        del: function () {
            var pids = getSelectedRows();
            if(pids == null){
                return ;
            }
            confirm("确定要删除选中的记录？", function(){
            	vm.updateStatus(pids.toString(), '0');
            });
        },
        saveOrUpdate: function () {
        	var re = /^[0-9]+$/ ;
        	if(vm.banner.rank==null || vm.banner.rank==''){    
        		alert("请填写序号!");
       		 	return;
        	} 
        	if(!re.test(vm.banner.rank)){    
        		alert("序号请填写正整数!");
       		 	return;
        	}         	
        	if(vm.banner.title==null || vm.banner.title==''){
        		alert("请填写标题！");
        		return;
        	}else if(vm.banner.title.length > 50){
        		alert("标题不能大于50字！");
        		return;
        	}
        	if(vm.banner.coversrc == null || vm.banner.coversrc==''){
        		alert("请上传图片！");
        		return;
        	}
        	if(vm.banner.value=='' || vm.banner.value==null){
        		alert("请选择跳转位置！");
        		return;
        	}
        	if(vm.banner.value == "jumpUrl" ){
	        	if(vm.banner.url=='' || vm.banner.url==null){
	        		alert("请填写链接地址！");
	        		return;
	        	}else{
	        		vm.banner.value=vm.banner.url;
	        	}
        	}
            var url = vm.banner.id == null ? "touch/banner/save" : "touch/banner/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.banner),
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
        getbanner: function(pid){
        	$("#banner_img").removeAttr("src");
            $.get(baseURL + "touch/banner/info/"+pid, function(r){
                vm.banner = r.banner;
                if(r.banner.coversrc != null && r.banner.coversrc != ''){
                	$("#banner_img").removeAttr("width");
                	$("#banner_img").removeAttr("height");
                	$("#banner_img").attr("src", localStorage.fileUrlPath + r.banner.coversrc);
                } else {
                	/*$("#banner_img").attr("width", "100px");
                	$("#banner_img").attr("height", "100px");*/
                }
                if(vm.banner.type=='web'){
                	vm.banner.url=vm.banner.value;
                	vm.banner.value='jumpUrl';
               	 	$("#url").show();
               }else{
               		$("#url").hide();
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
        updateStatus: function(pids, status){
        	var data = {'pids':pids, 'status': status};
            $.ajax({
                type: "POST",
                url: baseURL + "touch/banner/updateStatus",
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
        changeType: function (ele) {  
            var optionTxt = $(ele.target).find("option:selected").text();  
            var optionVal = ele.target.value; 
            if(optionVal==''){
            	return false;
            }
            if(optionVal=='jumpUrl'){
            	vm.banner.type='web';
            	 $("#url").show();
            }else{
            	vm.banner.type='app';
            	vm.banner.value=optionVal;
            	$("#url").hide();
            }
           
        }  
    }
});



