$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'touch/exchange/list',
        datatype: "json",
        colModel: [			
			{ label: 'Order number', name: 'order_id', index: "pid",  key: true},
			{ label: 'Country', name: 'counrty', width: 60},
			{ label: 'Shop name', name: 'shopname',width: 60 },
			{ label: 'Member account', name: 'user_id' ,width: 60},
			{ label: 'Member name', name: 'user_name'},
			{ label: 'Purchase quantity', name: 'buy_count', width: 60 },
			{ label: 'Total Credits', name: 'total_integral', width: 60 },
			{ label: 'Order status', name: 'status', width: 60 , formatter: function(value, options, row){
				return value == 1 ? 'Submitted' : 'Others';
			} },
			{ label: 'Order time', name: 'insert_date',formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
			{ label: '操作', name: 'operation',  formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
		                type: "GET",
		                url: baseURL + "sys/permitted/hasPermission?permission=touch:exchange:detail",
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
					var str='<a class="btn btn-primary" onclick="vm.update(\'' + row.order_id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;details</a>&nbsp';
					str+='<a class="btn btn-danger" onclick="vm.send(\'' + row.order_id + '\')"><i class="fa fa-share-square"></i>&nbsp;send message</a>';
					return str;
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
        order:{}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        
        getbanner: function(pid){
            $.get(baseURL + "touch/exchange/info/"+pid, function(r){
                vm.order = r.order;
            });
        },
        update: function (pid) {
            vm.showList = false;
            vm.title = "details";
            vm.getbanner(pid);
        },
        send:function(pid){
        	var data = {'order_id':pid};
        	confirm("Do you confirm to send the message to the customer?",function(){
        		$.ajax({
                    type: "POST",
                    url: baseURL + "touch/exchange/send",
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
        	})
        	
        },
        exportExcel:function(){
            var columns=$("#jqGrid").jqGrid('getGridParam','colNames');
            var params = {
					'colList':columns
				 };
            var fileurl=localStorage.fileUrlPath+"/excel/";

            $.ajax({
                type: "POST",
                url: baseURL + "touch/exchange/export",
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
                postData:{'user_id': vm.q.title},
                page:page
            }).trigger("reloadGrid");
        }
       
        
    }
});



