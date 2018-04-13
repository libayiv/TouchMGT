$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'touch/activity/list',
        datatype: "json",
        colModel: [			
			{ label: '编号', name: 'id', index: "pid",  key: true, hidden: true},
			{ label: '序号', name: 'rank', width: 30, editable:true },
			{ label: '封面', name: 'coversrc', formatter: function(value, options, row){
				return '<img src="'+ localStorage.fileUrlPath + value +'" width="100px;" height="100px;" />';
			} },
			{ label: '标题', name: 'title' },
			{ label: 'Status阶段', name: 'stage',width: 50},
			{ label: '是否显示', name: 'status',width: 50, formatter: function(value, options, row){
				var id='checked_' + row.id;
				return value == 1 ? 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.disable(\'' + row.id + '\')" checked/><label for="'+id+'" class="newlabel"></label>' : 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.enable(\'' + row.id + '\')"/><label for="'+id+'" class="newlabel"></label>';
			} },
			{ label: '操作', name: 'operation', width: 150, formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
		                type: "GET",
		                url: baseURL + "sys/permitted/hasPermission?permission=touch:activity:save",
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
					var activityStr=row.stage=='notice'?'<a class="btn btn-info btn-sm" style="margin-right:5px;" onclick="vm.update(\'' + row.id + '\',\'progress\')"><i class="fa fa-reply"></i>&nbsp;进阶至下一阶段</a>':
						'<a class="btn btn-success btn-sm" style="margin-right:5px;" onclick="vm.reloadFeed(\'' + row.id + '\')"><i class="fa fa-reply"></i>&nbsp;查看反馈信息</a>'
					 var str='<a class="btn btn-primary btn-sm" style="margin-right:5px;" onclick="vm.update(\'' + row.id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>';
					var appStr='<a class="btn btn-warning btn-sm"  onclick="vm.reloadApp(\'' + row.id + '\')"><i class="fa fa-heart"></i>&nbsp;查看报名情况</a>'
					return str+activityStr+appStr;
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
        cellurl: baseURL +"touch/activity/updateSortNum",
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
    
    $("#jqGrid2").jqGrid({
		url: baseURL + 'touch/activity/feedback',
		datatype: "json",
		colModel: [			
			{ label: '编号', name: 'id', index: "pid", width: 45, key: true, hidden: true},
			{ label: '用户头像', name: 'memb_photo',width: 60, formatter: function(value, options, row){
				return '<img src="'+ localStorage.fileUrlPath + value +'" width="50px;" />';
			} },
			{ label: '用户编号',width: 60, name: 'user_id' },
			{ label: '用户名称',width: 60, name: 'memb_name' },
			{ label: '活动名称',width: 60, name: 'title' },
			{ label: '反馈内容',width: 200, name: 'content' },
			{ label: '是否显示', name: 'status', width: 60, formatter: function(value, options, row){
				var id='checked_' + row.id;
				return value == 1 ? 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.feeDisable(\'' + row.id + '\',\'' + row.activity_id + '\')" checked/><label for="'+id+'" class="newlabel"></label>' : 
							'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.feeEnable(\'' + row.id + '\',\'' + row.activity_id+ '\')"/><label for="'+id+'" class="newlabel"></label>';
			} }
			],
			viewrecords: true,
			height: 'auto',
			rowNum: 10,
			rowList : [10,30,50],
			rownumWidth: 25, 
			multiselect: true,
			pager: "#jqGridPager2",
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
				$("#jqGrid2").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
			}
	});
    
    $("#jqGrid3").jqGrid({
		url: baseURL + 'touch/activity/app',
		datatype: "json",
		colModel: [			
			{ label: '编号', name: 'id', index: "pid", width: 45, key: true, hidden: true},
			{ label: '用户头像', name: 'memb_photo',width: 60, formatter: function(value, options, row){
				return '<img src="'+ localStorage.fileUrlPath + value +'" width="50px;" />';
			} },
			{ label: '用户名称',width: 60, name: 'memb_name'},
			{ label: '活动名称',width: 60, name: 'title' },
			{ label: '活动状态',width: 200, name: 'stage' }
			],
			viewrecords: true,
			height: 'auto',
			rowNum: 10,
			rowList : [10,30,50],
			rownumWidth: 25, 
			multiselect: true,
			pager: "#jqGridPager3",
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
				$("#jqGrid2").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
			}
	});
    new AjaxUpload('#activity_upload', {
        action: baseURL + "touch/fileupload/upload?modularName=activity",
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
            	vm.activity.coversrc = r.fileName;
            	vm.activity.oriFileName = r.oriFileName;
           	$("#activity_img").attr("src", r.url);
                alert("图片上传成功");
            }else{
                alert(r.msg);
            }
        }
    });
    
 /*   $("#datetimeStart").datetimepicker({
    	format: 'yyyymm',  
        weekStart: 1,  
        autoclose: true,  
        startView: 3,  
        minView: 3,  
        forceParse: false,  
        language: 'zh-CN'  
    }).on("changeDate",function(){
    	vm.activity.start_time=$("#datetimeStart").val();
    });
    $("#datetimeEnd").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
    }).on("changeDate",function(){
    	vm.activity.end_time=$("#datetimeEnd").val();
    });*/
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
		showFeed:false,
		showApp:false,
        title:null,
        platformList:platformList,
        activity:{
            status:1
            }
    },
	mounted: function() { 
		this.ue = UE.getEditor('editor',{ 
			BaseUrl: '', 
			UEDITOR_HOME_URL: '../../statics/ueditor/', 
		}); 
	},
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
        	$("#activity_img").removeAttr("src");
            vm.showList = false;
            vm.showFeed = false;
            vm.showApp = false;
            vm.title = "新增";
            UE.getEditor('editor').setContent("");
            vm.activity = {status:1,stage:'notice',views:0,fileName:'', oriFileName:'', rank:0, playbackLength:10};

        },
        update: function (pid,type) {
            vm.showApp = false;
            vm.showList = false;
            vm.showFeed = false;
            vm.title = "编辑";
            vm.getActivity(pid,type);
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
        	if(vm.activity.rank==null || vm.activity.rank==''){    
        		alert("请填写序号!");
       		 	return;
        	} 
        	if(!re.test(vm.activity.rank)){    
        		alert("序号请填写正整数!");
       		 	return;
        	}         	
        	if(vm.activity.title==null || vm.activity.title==''){
        		alert("请填写标题！");
        		return;
        	}else if(vm.activity.title.length > 50){
        		alert("标题不能大于50字！");
        		return;
        	}
        	if(vm.activity.coversrc == null || vm.activity.coversrc==''){
        		alert("请上传图片！");
        		return;
        	}
        	var content =  UE.getEditor('editor').getContent();
        	$.base64.utf8encode = true;  
        	vm.activity.content=$.base64.btoa(content);
            var url = vm.activity.id == null ? "touch/activity/save" : "touch/activity/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.activity),
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
        getActivity: function(pid,type){
        	$("#activity_img").removeAttr("src");
            $.get(baseURL + "touch/activity/info/"+pid, function(r){
                vm.activity = r.activity;
                /*$("#datetimeStart").val(r.activity.start_time);
                $("#datetimeEnd").val(r.activity.end_time);*/
                if(type!=null){
              		 vm.activity.stage=type;
               	}
                if(vm.activity.content != null && vm.activity.content != ''){
                    UE.getEditor('editor').setContent(vm.activity.content);
                }else{
                	 UE.getEditor('editor').setContent('');
                }
                if(r.activity.coversrc != null && r.activity.coversrc != ''){
                	$("#activity_img").attr("src", localStorage.fileUrlPath + r.activity.coversrc);
                } 
               
            });
        },
        reload: function () {
            vm.showList = true;
            vm.showFeed = false;
            vm.showApp = false;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'title': vm.q.title},
                page:page
            }).trigger("reloadGrid");
        },
        reloadFeed: function (pid) {
            vm.showList = false;
            vm.showFeed = true;
            vm.showApp = false;

            var page = $("#jqGrid2").jqGrid('getGridParam','page');
            $("#jqGrid2").jqGrid('setGridParam',{
                postData:{'activity_id': pid},
                page:page
            }).trigger("reloadGrid");
        },
        reloadApp: function (pid) {
            vm.showList = false;
            vm.showFeed = false;
            vm.showApp = true;

            var page = $("#jqGrid3").jqGrid('getGridParam','page');
            $("#jqGrid3").jqGrid('setGridParam',{
                postData:{'activity_id': pid},
                page:page
            }).trigger("reloadGrid");
        },
        updateStatus: function(pids, status){
        	var data = {'pids':pids, 'status': status};
            $.ajax({
                type: "POST",
                url: baseURL + "touch/activity/updateStatus",
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
       updateFeed: function(pids, status,activity_id){
        	var data = {'pids':pids, 'status': status};
            $.ajax({
                type: "POST",
                url: baseURL + "touch/activity/updateFeedStatus",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function(r){
                    if(r.code == 0){
                        alert('操作成功', function(){
                            vm.reloadFeed(activity_id);
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        feeEnable: function(pid,activity_id){
        	vm.updateFeed(pid, "1",activity_id);
        },
        feeDisable: function(pid,activity_id){
        	vm.updateFeed(pid, "0",activity_id);
        }
    
    }
});



