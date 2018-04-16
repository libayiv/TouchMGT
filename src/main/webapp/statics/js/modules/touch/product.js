$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'touch/product/list',
        datatype: "json",
        colModel: [			
			{ label: '编号', name: 'product_code', width: 45, editable:true },
			{ label: '图标', name: 'product_photo', width: 40 ,formatter: function(value, options, row){
				return '<img src="'+ value +'" width="50px;" />';
			} },
			{ label: '所属分类', name: 'product_cate', width: 60 },
			{ label: '产品名称', name: 'product_name', width: 60 },
			{ label: '操作', name: 'operation', formatter: function(value, options, row){
				if(hasPermission == null){
					$.ajax({
		                type: "GET",
		                url: baseURL + "sys/permitted/hasPermission?permission=touch:product:update",
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
					var starStr=row.is_star==0?'<a class="btn btn-success btn-sm" style="margin-right:5px;" onclick="vm.updateType(\'' + row.product_id + '\',\'' + row.product_code + '\',1)"><i class="fa fa-thumbs-up"></i>&nbsp;设为明星产品</a>':
						'<a class="btn btn-warning btn-sm" style="margin-right:5px;" onclick="vm.updateType(\'' + row.product_id + '\',\'' + row.product_code + '\',0)" ><i class="fa fa-thumbs-up"></i>&nbsp;下架明星产品</a>'
						if(row.is_star==null){
							starStr='';
						}
					var str= '<a class="btn btn-primary btn-sm" style="margin-right:5px;" onclick="vm.update(\'' + row.product_id + '\',\'' + row.product_code + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑图文信息</a>';
					str+='<a class="btn btn-info btn-sm" style="margin-right:5px;" onclick="vm.updateWeb(\'' + row.product_id + '\',\'' + row.product_code + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑图文信息（web官网）</a>';
					str+='<a class="btn btn-info btn-sm" style="margin-right:5px;" onclick="vm.updateImg(\'' + row.product_id + '\',\'' + row.product_code + '\',\'' + row.product_name + '\')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑产品封面</a>';

					return str+starStr;
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
    
    new AjaxUpload('#product_upload', {
        action: baseURL + "touch/fileupload/upload?modularName=product",
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
            	vm.uploadImages.push(r.url);
            	vm.postImages.push(r.uploadName);
                alert("图片上传成功");
            }else{
                alert(r.msg);
            }
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
        showWeb:false,
        showImage:false,
        title:null,
        product:{},
        images:[],
        uploadImages:new Array(),
        postImages:new Array(),
      
    },
    mounted: function() { 
    	 UE.getEditor('editor',{ 
			BaseUrl: '', 
			UEDITOR_HOME_URL: '../../statics/ueditor/', 
		}); 
    	UE.getEditor('editor1',{ 
			BaseUrl: '', 
			UEDITOR_HOME_URL: '../../statics/ueditor/', 
		}); 
    	UE.getEditor('part1',{ 
			BaseUrl: '', 
			UEDITOR_HOME_URL: '../../statics/ueditor/', 
		}); 
    	UE.getEditor('part2',{ 
			BaseUrl: '', 
			UEDITOR_HOME_URL: '../../statics/ueditor/', 
		}); 
    	UE.getEditor('part3',{ 
			BaseUrl: '', 
			UEDITOR_HOME_URL: '../../statics/ueditor/', 
		}); 
    	UE.getEditor('part4',{ 
			BaseUrl: '', 
			UEDITOR_HOME_URL: '../../statics/ueditor/', 
		}); 
    	UE.getEditor('part5',{ 
			BaseUrl: '', 
			UEDITOR_HOME_URL: '../../statics/ueditor/', 
		}); 
	},
    methods: {
        query: function () {
            vm.reload();
        },
        update: function (pid,code) {
            vm.showList = false;
            vm.showWeb=false;
            vm.showImage=false;
            UE.getEditor('editor').setContent("");
            UE.getEditor('editor1').setContent("");
            vm.title = "编辑";
            vm.product={"product_id":pid,"product_code":code};
            vm.getProduct(pid,code);
        },
        updateWeb: function (pid,code) {
            vm.showList = false;
            vm.showWeb=true;
            vm.showImage=false;
            UE.getEditor('part1').setContent("");
            UE.getEditor('part2').setContent("");
            UE.getEditor('part3').setContent("");
            UE.getEditor('part4').setContent("");
            UE.getEditor('part5').setContent("");
            vm.title = "产品资料编辑";
            vm.product={"product_id":pid,"product_code":code};
            vm.getProduct(pid,code);
        },
        updateImg: function (pid,code,name) {
            vm.showList = false;
            vm.showWeb=false;
            vm.showImage=true;
            vm.title = "产品图片编辑 -"+name;
            vm.uploadImages=new Array();
            vm.postImages=new Array();
            vm.getProductImg(pid,code);
        },
        updateImgStatus:function(img,type){
        	//1.设为封面，0.删除
        	var status=null;
        	var obj=null;
        	var str=null;
        	if(img.image_id==null){
        		return false;
        	}
        	if(type==1){
        		img.is_coversrc='1';
        		obj=img;
        		str="你确定要将该图片设为封面？";
        	}else{
        		obj={"status":'0',"image_id":img.image_id};
        		str="确定删除该图片？";
        	}
        	confirm(str, function(){
        		var url = "touch/product/imgUpdate";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(obj),
                    success: function(r){
                        if(r.code === 0){
                        	if(type==1){
                        		alert('操作成功');
                        	}else{
                        		alert('操作成功', function(){
                                    var title=vm.title.split("-");
                                	vm.updateImg(img.product_id,img.product_code,title[1]);
                                });
                        	}
                        	

                        }else{
                            alert(r.msg);
                        }
                    }
                });
			});
        	
        },
        saveOrUpdate: function () {
        	var product_intro =  UE.getEditor('editor').getContent();
        	var product_instruction =  UE.getEditor('editor1').getContent();
        	$.base64.utf8encode = true;  
        	vm.product.product_intro=$.base64.btoa(product_intro);
        	vm.product.product_instruction=$.base64.btoa(product_instruction);
        	vm.product.part1=null;
        	vm.product.part2=null;
          	vm.product.part3=null;
        	vm.product.part4=null;
          	vm.product.part5=null;
            var url = "touch/product/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.product),
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
        saveWeb: function () {
        	var part1 =  UE.getEditor('part1').getContent();
        	var part2 =  UE.getEditor('part2').getContent();
        	var part3 =  UE.getEditor('part3').getContent();
        	var part4 =  UE.getEditor('part4').getContent();
        	var part5 =  UE.getEditor('part5').getContent();
        	$.base64.utf8encode = true;  
        	vm.product.part1=$.base64.btoa(part1);
        	vm.product.part2=$.base64.btoa(part2);
          	vm.product.part3=$.base64.btoa(part3);
        	vm.product.part4=$.base64.btoa(part4);
          	vm.product.part5=$.base64.btoa(part5);
          	vm.product.product_intro=null;
          	vm.product.product_instruction=null;
          
            var url = "touch/product/web";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.product),
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
        saveImage: function () {
        	var postStr="";
        	var obj={};
        	var product_id="";
        	var product_code="";
        	if(vm.postImages.length==0){
        		alert("请选择图片");
        		return false;
        	}
        	for ( var i in vm.postImages) {
        		postStr+=vm.postImages[i]+",";
			}
        	for ( var a in vm.images) {
        		product_id=vm.images[a].product_id;
        		product_code=vm.images[a].product_code;
			}
        	obj={"product_id":product_id,"product_code":product_code,"product_photo":postStr.substring(0,postStr.length-1)};
            var url = "touch/product/imgInsert";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(obj),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            var title=vm.title.split("-");
                        	vm.updateImg(product_id,product_code,title[1]);
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        getProduct: function(pid,code){
            $.post(baseURL + "touch/product/info",{"pid":pid,"code":code}, function(r){
            	if(r.product!=null){
            		vm.product = r.product;
                    UE.getEditor('editor').setContent(r.product.product_intro?r.product.product_intro:"");
                    UE.getEditor('editor1').setContent(r.product.product_instruction?r.product.product_instruction:"");
                    UE.getEditor('part1').setContent(r.product.part1?r.product.part1:"");
                    UE.getEditor('part2').setContent(r.product.part2?r.product.part2:"");
                    UE.getEditor('part3').setContent(r.product.part3?r.product.part3:"");
                    UE.getEditor('part4').setContent(r.product.part4?r.product.part4:"");
                    UE.getEditor('part5').setContent(r.product.part5?r.product.part5:"");
            	}     
            });
        },
        getProductImg: function(pid,code){
            $.post(baseURL + "touch/product/image",{"pid":pid,"code":code}, function(r){
            	if(r.images.length!=0){
            		vm.images = r.images;          
            	}  else{
            		vm.images = r.images; 
            		vm.images[0]={"product_id":pid,"product_code":code,"product_photo":""};
            	}   
            });
        },
        reload: function () {
            vm.showList = true;
            vm.showWeb=false;
            vm.showImage=false;

            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'product_name': vm.q.title},
                page:page
            }).trigger("reloadGrid");
        },
        search: function () {
            vm.showList = true;
            vm.showWeb=false;
            vm.showImage=false;

            var page = 1;
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'product_name': vm.q.title},
                page:page
            }).trigger("reloadGrid");
        },
    	updateType: function(pid,code,status){
			var data = {"product_id":pid,"product_code":code,"is_star":status};
			confirm('是否确认该修改操作？',function(){
				$.ajax({
					type: "POST",
					url: baseURL + "touch/product/star",
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
		deleteImg:function(obj){
			var src=$(obj).next()[0].src;
			var list=vm.uploadImages;
			var polist=vm.postImages;
			var newList=new Array();
			var newPost=new Array();
			for ( var i in list) {
				if(src.indexOf(list[i])<0){
					newList.push(list[i]);
				}
			}
			for ( var a in polist) {
				if(src.indexOf(list[a])<0){
					newPost.push(list[a]);
				}
			}
			vm.uploadImages=newList;
			vm.postImages=newPost;
		}
    }
});



