$(function () {
	$("#jqGrid").jqGrid({
		url: baseURL + 'touch/news/list',
		datatype: "json",
		colModel: [			
			{ label: '编号', name: 'id', index: "pid", width: 45, key: true, hidden: true},
			{ label: '封面', name: 'coversrc',width: 60, formatter: function(value, options, row){
				return '<img src="'+ localStorage.fileUrlPath + value +'" width="50px;" />';
			} },
			{ label: '标题', name: 'title' },
			{ label: '是否显示', name: 'status', width: 60, formatter: function(value, options, row){
				var id='checked_' + row.id;
				return value == 1 ? 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.disable(\'' + row.id + '\')" checked/><label for="'+id+'" class="newlabel"></label>' : 
							'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.enable(\'' + row.id + '\')"/><label for="'+id+'" class="newlabel"></label>';
			} },
			{ label: '操作', name: 'operation', width: 150, formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
						type: "GET",
						url: baseURL + "sys/permitted/hasPermission?permission=touch:news:update",
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
					var hotStr=row.is_hot==0?'<a class="btn btn-success btn-sm" style="margin-right:5px;" onclick="vm.updateType(\'' + row.id + '\',\'hot\',1)"><i class="fa fa-thumbs-up"></i>&nbsp;推荐到首页</a>':
						'<a class="btn btn-danger btn-sm" style="margin-right:5px;" onclick="vm.updateType(\'' + row.id + '\',\'hot\',0)" ><i class="fa fa-thumbs-up"></i>&nbsp;取消到首页</a>'
					var activityStr=row.is_activity==0?'<a class="btn btn-success btn-sm" onclick="vm.updateType(\'' + row.id + '\',\'activity\',1)"><i class="fa fa-heart"></i>&nbsp;设为活动咨询</a>':
						'<a class="btn btn-danger btn-sm" onclick="vm.updateType(\'' + row.id + '\',\'activity\',0)"><i class="fa fa-heart"></i>&nbsp;取消活动咨询</a>'
							
					var str= '<a class="btn btn-primary btn-sm" style="margin-right:5px;" onclick="vm.update(\'' + row.id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>';
						str+='<a class="btn btn-warning btn-sm" style="margin-right:5px;" onclick="vm.checkReply(\'' + row.id + '\')"><i class="fa fa-reply"></i>&nbsp;查看回复内容</a>';
						str+=hotStr+activityStr;
						return str;
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
	

	$("#jqGrid2").jqGrid({
		url: baseURL + 'touch/news/reply',
		datatype: "json",
		colModel: [			
			{ label: '编号', name: 'id', index: "pid", width: 45, key: true, hidden: true},
			{ label: '回复人头像', name: 'memb_photo',width: 60, formatter: function(value, options, row){
				return '<img src="'+webThumb+ value +'" width="50px;" />';
			} },
			{ label: '回复人编号',width: 60, name: 'user_id' },
			{ label: '回复人名称',width: 60, name: 'memb_name' },
			{ label: '回复内容',width: 200, name: 'content' },
			{ label: '是否显示', name: 'status', width: 60, formatter: function(value, options, row){
				var id='checked_' + row.id;
				return value == 1 ? 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.replyDisable(\'' + row.id + '\',\'' + row.news_id + '\')" checked/><label for="'+id+'" class="newlabel"></label>' : 
							'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.replyEnable(\'' + row.id + '\',\'' + row.news_id+ '\')"/><label for="'+id+'" class="newlabel"></label>';
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

	new AjaxUpload('#news_upload', {
		action: baseURL + "touch/fileupload/upload?modularName=news",
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
				vm.news.coversrc = r.fileName;
				vm.news.oriFileName = r.oriFileName;
				 $("#news_img").attr("src", r.url);
				 alert("图片上传成功");
			}else{
				alert(r.msg);
			}
		}
	});

	UE.getEditor('editor').addListener("ready", function () {  
        // editor准备好之后才可以使用  
    	 UE.getEditor('editor').setContent("");  

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
		uedata :[],
		showList: true,
		showReply:false,
		title:null,
		platformList:platformList,
		news:{
			status:1,
			is_hot:0,
			is_activity:0
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
			$("#news_img").removeAttr("src");
			vm.showList = false;
			vm.showReply= false;
			vm.title = "新增";
			$("#url").hide();
            UE.getEditor('editor').setContent("");
			vm.news = {status:1,is_hot:0,is_activity:0,pc_valid:1, fileName:'', oriFileName:'',views:0,good:0,store:0,reply:0};

		},
		update: function (pid) {
			vm.showList = false;
			vm.showReply= false;
			vm.title = "编辑";
			vm.getnews(pid);
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
			var is_valid=false;
			$("[requires]").each(function () {
				temp_val = $(this).val();
				if (temp_val.trim().length == 0) {
					is_valid=true;
					alert($(this).attr("msg_txt"));
					return false;
				}
			});
			if(is_valid){
				return false;

			}
			if(vm.news.title.length > 100){
				alert("标题不能大于100字！");
				return;
			}
			if(vm.news.coversrc == null || vm.news.coversrc==''){
				alert("请上传图片！");
				return;
			}
			var content =  UE.getEditor('editor').getContent();
			content=encodeURIComponent(content);
			vm.news.content =content;
			var url = vm.news.id == null ? "touch/news/save" : "touch/news/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.news),
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
		getnews: function(pid){
			$("#news_img").removeAttr("src");
			$.get(baseURL + "touch/news/info/"+pid, function(r){
				vm.news = r.news;
                UE.getEditor('editor').setContent(vm.news.content);
				if(r.news.coversrc != null && r.news.coversrc != ''){
					$("#news_img").attr("src", localStorage.fileUrlPath + r.news.coversrc);
				}
				

			});
		},
		reload: function () {
			vm.showList = true;
			vm.showReply= false;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{'title': vm.q.title},
				page:page
			}).trigger("reloadGrid");
		},
		search: function () {
			vm.showList = true;
			vm.showReply= false;
			var page = 1;
			debugger;
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{'title': vm.q.title},
				page:page
			}).trigger("reloadGrid");
		},
		updateStatus: function(pids, status){
			var data = {'pids':pids, 'status': status};
			$.ajax({
				type: "POST",
				url: baseURL + "touch/news/updateStatus",
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
		updateType: function(pids, type,status){
			var data = {'pids':pids,'type':type,'status': status};
			confirm('是否确认该修改操作？',function(){
				$.ajax({
					type: "POST",
					url: baseURL + "touch/news/updateStatus",
					contentType: "application/json",
					data: JSON.stringify(data),
					success: function(r){
						if(r.code == 0){
							vm.reload();
							alert('操作成功');
						}else{
							alert(r.msg);
						}
					}
				});
			})	
			
		},
		updateReply: function(pids,status,newId){
			var data = {'pids':pids, 'status': status};
			$.ajax({
				type: "POST",
				url: baseURL + "touch/news/updateReply",
				contentType: "application/json",
				data: JSON.stringify(data),
				success: function(r){
					if(r.code == 0){
						alert('操作成功', function(){
							vm.checkReply(newId);
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
		replyEnable: function(pid,newId){
			vm.updateReply(pid, "1",newId);
		},
		replyDisable: function(pid,newId){
			vm.updateReply(pid, "0",newId);
		},
		checkReply:function(pid){
			vm.showReply=true;
			vm.showList=false;
			var page = $("#jqGrid2").jqGrid('getGridParam','page');
			$("#jqGrid2").jqGrid('setGridParam',{
				postData:{'news_id': pid},
				page:page
			}).trigger("reloadGrid");
		}
		
	}
});



