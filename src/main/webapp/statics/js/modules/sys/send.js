$(function () {
	$("#jqGrid").jqGrid({
		url: baseURL + 'sys/send/list',
		datatype: "json",
		colModel: [			
			{ label: 'id', name: 'id', width: 20, key: true ,hidden:true},
			{ label: '操作人', name: 'operation_name', width: 100 },
			{ label: '推送简介', name: 'remark', width: 120 },
			{ label: '是否推送', name: 'status', width: 60, formatter: function(value, options, row){
				var id='checked_' + row.id;
				return value == 1 ? 
						'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.disable(\'' + row.id + '\')" checked/><label for="'+id+'" class="newlabel"></label>' : 
							'<input id="' + id + '" type="checkbox" class="switch" onclick="vm.enable(\'' + row.id + '\')"/><label for="'+id+'" class="newlabel"></label>';
			} },
			{ label: '控制标识', name: 'auth_code', width: 120 },
			{ label: '操作', name: 'operation', width: 40, formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
						type: "GET",
						url: baseURL + "sys/permitted/hasPermission?permission=sys:send:all",
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



});
var hasPermission;

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
	
		  title: null
		},
		showList: true,
		title: null,
		send:{status:1}
	},

	methods: {
		query: function () {
			vm.reload();
		},	
		updateStatus: function(pids, status){
			var data = {'pids':pids, 'status': status};
			$.ajax({
				type: "POST",
				url: baseURL + "sys/send/updateStatus",
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
        	
			var url = vm.send.id == null ? "sys/send/save" : "sys/send/update";
			$.ajax({
				type: "POST",
				url: baseURL + url,
				contentType: "application/json",
				data: JSON.stringify(vm.send),
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
			vm.send = {status:1};

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
		getFile: function(pid){
			$.get(baseURL + "sys/send/info/"+pid, function(r){
				vm.send = r.send;

			});
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				page:page
			}).trigger("reloadGrid");
		}
		
	}
});