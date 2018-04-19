$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'touch/order/list',
        datatype: "json",
        colModel: [			
			{ label: '订单编号', name: 'order_id', index: "pid",  key: true},
			{ label: '下单时间', name: 'order_time', formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
			{ label: '用户编号', name: 'user_id'},
			{ label: '订单金额', name: 'total_price'},
			{ label: '操作', name: 'operation', formatter: function(value, options, row){		
					var str= '<a class="btn btn-primary btn-sm" style="margin-right:5px;" onclick="vm.showDetail(\'' + row.order_id + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;查看订单</a>';
						
						return str;
			} }
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
		url: baseURL + 'touch/order/detail',
		datatype: "json",
		colModel: [			
			{ label: 'ID', name: 'order_id', index: "pid", key: true, hidden: true},
			{ label: '商品编号', name: 'product_id'},
			{ label: '商品图片', name: 'product_coversrc',formatter: function(value, options, row){
				return '<img src="'+ value +'" width="100px;" height="100px;" />';
			}},
			{ label: '商品名称', name: 'product_name' },
			{ label: '商品单价', name: 'product_price' },
			{ label: '商品数量', name: 'product_num'},
			{ label: '商品总价', name: 'final_price'}
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
    
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            title: null
        },
        showList: true,
        title:"订单明细",
        configList:{},
        order:{
            status:1
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        search: function () {
            vm.showList = true;

            var page = 1;
            $("#jqGrid").jqGrid('setGridParam',{
            	postData:{'order_id': vm.q.order_id},
                page:page
            }).trigger("reloadGrid");
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'order_id': vm.q.order_id},
                page:page
            }).trigger("reloadGrid");
        },
        showDetail:function(pid){
			vm.showList=false;
			var page = $("#jqGrid2").jqGrid('getGridParam','page');
			$("#jqGrid2").jqGrid('setGridParam',{
				postData:{'order_id': pid},
				page:page
			}).trigger("reloadGrid");
		}
      
      
    }
});



