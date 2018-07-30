$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'touch/integral/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'recordId', index: "pid",  key: true,hidden:true},
			{ label: 'Member account', name: 'userId', width: 60},
			{ label: 'Member name', name: 'memb_name'},
			{ label: 'Credtis income & outcome', name: 'intEvent' },
			{ label: 'Original Credits', name: 'intFront',width: 60},
			{ label: 'Earn/Consume', name: 'intInfo', width: 60,formatter: function(value, options, row){
				return row.intEventType+value;
			} },
			{ label: 'Balance', name: 'intBack', width: 60 },
			
			{ label: 'Operation time', name: 'insertDate',formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
			{ label: '操作', name: 'operation', width: 80, formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
		                type: "GET",
		                url: baseURL + "sys/permitted/hasPermission?permission=touch:integral:detail",
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
					return '<a class="btn btn-primary" onclick="vm.update(\'' + row.recordId + '\',\'' + row.userId + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;details</a>';
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
        integral:{},
        memb:{}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        getbanner: function(pid,uid){
            $.get(baseURL + "touch/integral/info/"+pid+"/"+uid, function(r){
                vm.integral = r.integral;
                vm.memb=r.memb;
            });
        },
        update: function (pid,uid) {
            vm.showList = false;
            vm.title = "details";
            vm.getbanner(pid,uid);
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
                postData:{'intEvent': vm.q.title},
                page:page
            }).trigger("reloadGrid");
        }
       
        
    }
});



