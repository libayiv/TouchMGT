$(function () {
	$("#jqGrid").jqGrid({
		url: baseURL + 'touch/blacklist/list',
		datatype: "json",
		colModel: [			
			{ label: '编号', name: 'id', index: "id", width: 45, key: true, hidden: true},
			{ label: '人员ID', name: 'membId', width: 45},
			{ label: '备注', name: 'remark', width: 45 },
			{ label: '创建时间', name: 'createDate', width: 45 },
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
			},
			afterSubmitCell : function(serverresponse, rowid,
					cellname, value, iRow, iCol) {
				// 修改失败
				if (serverresponse.responseJSON.code == 500) {
					alert(serverresponse.responseJSON.msg);
				} else {
					alert('操作成功', function() {
						vm.reload();
					});
				}
			}
	});

});

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
        	membId: null
        },
        showList: true,
        title:null,
        roleList:{},
        blacklist:{
        	status:1
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.blacklist = {
            	status:1,
            	membId : null,
            	remark:null
            };
        },
        del: function () {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
            	vm.updateStatus(ids.toString(), '0');
            });
        },
        saveOrUpdate: function () {
        	if(isBlank(vm.blacklist.membId)){    
        		alert("请填写人员ID!");
       		 	return;
        	} 
        	if(isBlank(vm.blacklist.remark)){
        		alert("请填写备注！");
        		return;
        	}else if (vm.blacklist.remark.length > 250){
        		alert("备注不能大于250字！");
        		return;
        	}      
            var url = "touch/blacklist/save";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.blacklist),
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
        reload: function () {
            vm.showList = true;
            var membId = String(vm.q.membId);
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'membId': membId.toUpperCase()},
                page:page
            }).trigger("reloadGrid");
        },
        updateStatus: function(ids){
        	var data = {'ids':ids};
            $.ajax({
                type: "POST",
                url: baseURL + "touch/blacklist/updateStatus",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function(r){
                    if(r.code == 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                        var id = 'checked_' + pids;
                   	 	$('#'+id).removeAttr('checked');
                    }
                }
            });
        },
    },
});

//修改操作
function turnToUpdate(pid){
	vm.update(pid);
}


