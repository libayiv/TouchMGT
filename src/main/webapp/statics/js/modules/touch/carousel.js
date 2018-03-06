$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'hyb/carousel/list',
        datatype: "json",
        colModel: [			
			{ label: '编号', name: 'pid', index: "pid", width: 45, key: true, hidden: true},
			{ label: '序号', name: 'sortNum', width: 45, editable:true },
			{ label: '图片', name: 'fileName', formatter: function(value, options, row){
				return '<img src="'+ localStorage.fileUrlPath + value +'" width="100px;" height="100px;" />';
			} },
			{ label: '标题', name: 'title' },
            /*{ label: '开始时间', name: 'startTime', width: 75 },
			{ label: '截止时间', name: 'endTime', width: 75 },*/
			{ label: '链接类型', name: 'linkType', formatter:function(value, options, row){
				return value == 1 ? '内部链接' : '外部链接';
			} },
			{ label: '链接地址', name: 'linkUrl' },
			{ label: '是否显示', name: 'status', width: 60, formatter: function(value, options, row){
				var id='checked_' + row.pid;
				return value == 1 ? 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.disable(\'' + row.pid + '\')" checked/><label for="'+id+'" class="newlabel"></label>' : 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.enable(\'' + row.pid + '\')"/><label for="'+id+'" class="newlabel"></label>';
			} },
			{ label: '所属平台', name: 'platform', hidden: true },
			{ label: '所属平台', name: 'platformStr', width: 60, },
			{ label: '描述信息', name: 'carouselDesc'},
			{ label: '操作', name: 'operation', width: 80, formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
		                type: "GET",
		                url: baseURL + "sys/permitted/hasPermission?permission=hyb:carousel:save",
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
					return '<a class="btn btn-primary" onclick="vm.update(\'' + row.pid + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>';
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
        multiselect: true,
        cellEdit:true,
        cellurl: baseURL +"hyb/carousel/updateSortNum",
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
    
    new AjaxUpload('#carousel_upload', {
        action: baseURL + "hyb/fileupload/uploadimg?modularName=carousel",
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
            	vm.carousel.fileName = r.fileName;
            	vm.carousel.oriFileName = r.oriFileName;
/*            	$("#carousel_img").removeAttr("width");
            	$("#carousel_img").removeAttr("height");
*/            	$("#carousel_img").attr("src", r.url);
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
        carousel:{
            status:1,
            deptId:null,
            deptName:null,
            roleIdList:[]
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
        	$("#carousel_img").removeAttr("src");
            vm.showList = false;
            vm.title = "新增";
            vm.carousel = {status:1, linkType:1, platform:1, fileName:'', oriFileName:'', sortNum:0, playbackLength:10};

        },
        update: function (pid) {
            vm.showList = false;
            vm.title = "编辑";
            vm.getCarousel(pid);
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
        	if(vm.carousel.sortNum==null || vm.carousel.sortNum==''){    
        		alert("请填写序号!");
       		 	return;
        	} 
        	if(!re.test(vm.carousel.sortNum)){    
        		alert("序号请填写正整数!");
       		 	return;
        	}         	
        	if(vm.carousel.title==null || vm.carousel.title==''){
        		alert("请填写标题！");
        		return;
        	}else if(vm.carousel.title.length > 50){
        		alert("标题不能大于50字！");
        		return;
        	}
        	if(vm.carousel.fileName == null || vm.carousel.fileName==''){
        		alert("请上传图片！");
        		return;
        	}
            var url = vm.carousel.pid == null ? "hyb/carousel/save" : "hyb/carousel/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.carousel),
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
        getCarousel: function(pid){
        	$("#carousel_img").removeAttr("src");
            $.get(baseURL + "hyb/carousel/info/"+pid, function(r){
                vm.carousel = r.carousel;
                if(r.carousel.fileName != null && r.carousel.fileName != ''){
                	$("#carousel_img").removeAttr("width");
                	$("#carousel_img").removeAttr("height");
                	$("#carousel_img").attr("src", localStorage.fileUrlPath + r.carousel.fileName);
                } else {
                	/*$("#carousel_img").attr("width", "100px");
                	$("#carousel_img").attr("height", "100px");*/
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
                url: baseURL + "hyb/carousel/updateStatus",
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
        	vm.updateStatus(pid, "2");
        },
    }
});



