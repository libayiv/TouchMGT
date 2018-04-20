$(function () {
	$("#jqGrid").jqGrid({
		url: baseURL + 'touch/fileupload/list',
		datatype: "json",
		colModel: [			
			{ label: 'id', name: 'file_id', width: 20, key: true ,hidden:true},
			{ label: '文件名', name: 'filename', width: 100 },
			{ label: '文件类型', name: 'file_type', width:40 },
			{ label: '文件简介', name: 'intro', width: 120 },
			{ label: '是否显示', name: 'status', width: 60, formatter: function(value, options, row){
				var id='checked_' + row.file_id;
				return value == 1 ? 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.disable(\'' + row.file_id + '\')" checked/><label for="'+id+'" class="newlabel"></label>' : 
							'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.enable(\'' + row.file_id + '\')"/><label for="'+id+'" class="newlabel"></label>';
			} },	
			{ label: '操作', name: 'operation', width: 40, formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
						type: "GET",
						url: baseURL + "sys/permitted/hasPermission?permission=sys:oss:all",
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
					return '<a class="btn btn-primary" onclick="vm.update(\'' + row.file_id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>';
				}
				return '';
			} },
			],
			viewrecords: true,
			height: 385,
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

	/*new AjaxUpload('#upload', {
		action: baseURL + "touch/fileupload/upload?modularName=fileupload",
		name: 'file',
		autoSubmit:true,
		responseType:"json",
		onSubmit:function(file, extension){
			if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
				vm.fileList.file_type=extension;
				if(extension.indexOf("ppt") != -1){
					vm.fileList.file_type='ppt';
				}else if(extension.indexOf("xls") != -1){
					vm.fileList.file_type='excel';
				}else if(extension.indexOf("pdf") != -1){
					vm.fileList.file_type='pdf';
				}else{
					vm.fileList.file_type='word';
				}
			}else{
				vm.fileList.file_type='image';
			}
		},
		onComplete : function(file, r){
			if(r.code == 0){
				vm.fileList.file_path = r.fileName;
				$("#fileName").val(r.fileName);
				alert("文件上传成功");
			}else{
				alert(r.msg);
			}
		}
	});*/

});
var hasPermission;

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		fileList:{status:1}
	},

	methods: {
		query: function () {
			vm.reload();
		},	
		updateStatus: function(pids, status){
			var data = {'pids':pids, 'status': status};
			$.ajax({
				type: "POST",
				url: baseURL + "touch/fileupload/updateStatus",
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
		saveOrUpdate: function () {
        	if(vm.fileList.intro==null || vm.fileList.intro==''){    
        		alert("请填写描述!");
       		 	return;
        	} 
        	if(vm.fileList.file_path==null || vm.fileList.file_path==''){    
        		alert("请选择文件!");
       		 	return;
        	}      	
        	if(vm.fileList.filename==null || vm.fileList.filename==''){
        		alert("请填写文件名称！");
        		return;
        	}else if(vm.fileList.filename.length > 50){
        		alert("文件名称不能大于50字！");
        		return;
        	}
			var url = vm.fileList.file_id == null ? "touch/fileupload/save" : "touch/fileupload/update";
			$.ajax({
				type: "POST",
				url: baseURL + url,
				contentType: "application/json",
				data: JSON.stringify(vm.fileList),
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
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			$("#url").hide();
			vm.fileList = {status:1};

		},
		update: function (pid) {
			vm.showList = false;
			vm.title = "编辑";
			vm.getFile(pid);
		},
		enable: function(pid){
			vm.updateStatus(pid, "1");
		},
		disable: function(pid){
			vm.updateStatus(pid, "0");
		},
		del: function () {
			var ossIds = getSelectedRows();
			if(ossIds == null){
				return ;
			}

			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
					url: baseURL + "touch/fileupload/delete",
					contentType: "application/json",
					data: JSON.stringify(ossIds),
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
			});
		},
		getFile: function(pid){
			$.get(baseURL + "touch/fileupload/info/"+pid, function(r){
				vm.fileList = r.file;

			});
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				page:page
			}).trigger("reloadGrid");
		},
		showDialog:function(){
			
		    layer.open({  
		        type: 2,  
		        title: '文件上传',  
		        shadeClose: true,  
		        shade: 0.8,  
		        area: ['380px', '60%'],  
		        content: 'image.html', //iframe的url  
		        end : function(index){  
		        	debugger;
		          /*  var retVal = $("#layerResult").val();//返回一个标记用于控制,业务逻辑( 也可不写)  
		            if(retVal=='0'){  
		                                           // 当返回值为  0   执行 ,业务逻辑  
		             }  */
		                         }  
		                });
		}
	}
});