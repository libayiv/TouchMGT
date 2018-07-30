$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'touch/reward/list',
        datatype: "json",
        colModel: [			
			{ label: 'Member account', name: 'memb_id', width: 60},
			{ label: 'Member name', name: 'memb_name',width: 60 },
			{ label: 'Member contacts', name: 'memb_phone_mobile' ,width: 60},
			{ label: 'Total Credits', name: 'total_integral'},
			{ label: 'Complete time', name: 'insert_date', width: 60 ,formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
			{ label: '操作', name: 'operation', width: 80, formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
		                type: "GET",
		                url: baseURL + "sys/permitted/hasPermission?permission=touch:reward:detail",
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
					return '<a class="btn btn-primary" onclick="vm.update(\'' + row.memb_id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;details</a>';
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
        title:null,
        reward:{},
        memb:{}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        getbanner: function(pid){
            $.get(baseURL + "touch/reward/info/"+pid, function(r){
                vm.memb = r.memb;
            });
        },
        update: function (pid) {
            vm.showList = false;
            vm.title = "details";
            vm.getbanner(pid);
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
                postData:{'memb_id': vm.q.title},
                page:page
            }).trigger("reloadGrid");
        },
        exportExcel:function(){
            var columns=$("#jqGrid").jqGrid('getGridParam','colNames');
            var params = {
					'colList':columns
				 };
            var fileurl=localStorage.fileUrlPath+"/excel/";
					
            $.ajax({
                type: "POST",
                url: baseURL + "touch/reward/export",
                contentType: "application/json",
                data: JSON.stringify(params),
                success: function(r){
                    if(r.code == 0){
                        alert('操作成功', function(){
                            vm.reload();
                            location.href=fileurl+r.result.result;
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        
    }
});



