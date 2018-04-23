jQuery(function() {
	var thumbnailWidth = 100;   //缩略图高度和宽度 （单位是像素），当宽高度是0~1的时候，是按照百分比计算，具体可以看api文档  
	   var thumbnailHeight = 100;
    var $ = jQuery,
        $list = $('#thelist'),
        $btn = $('#ctlBtn'),
        state = 'pending',
        uploader;
    uploader = WebUploader.create({
    	// 不压缩image
        resize: false,
        // swf文件路径，需要用到flash的时候BASE_URL自己根据需要定义 也可写成绝对路径
        swf: '${request.contextPath}/statics/ueditor/third-party/webuploader/Uploader.swf',
        // 文件接收服务端。此处根据自己的框架写，本人用的是SpringMVC
        server:  "../../touch/fileupload/upload?modularName=fileupload",   
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',
        chunked: true,//开启分片上传  
        chunkSize:5*1024*1024,// 如果要分片，分多大一片？默认大小为5M  
        chunkRetry: 2,//如果某个分片由于网络问题出错，允许自动重传多少次  ,
        threads:3,
        accept: {
                title: 'Images',
                extensions: 'wmv,mp4,flv,jpg,bmp,doc,docx,rar,pdf',
        }
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
    	var $li =$('<div id="' + file.id + '" class="file-item thumbnail">' +
       		 '<img>' +
             '<h4 class="info">' + file.name + '</h4>' +
             '<p class="state">等待上传...</p>' +
         '</div>');
    	   $img = $li.find('img');  
        $list.append( $li );  
        
        uploader.makeThumb( file, function( error, src ) {   //webuploader方法  
            if ( error ) {  
                $img.replaceWith('<span>不能预览</span>');  
                return;  
            }  
   
            $img.attr( 'src', src );  
        }, thumbnailWidth, thumbnailHeight );  
    });

  

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress .progress-bar');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress progress-striped active">' +
              '<div class="progress-bar" role="progressbar" style="width: 0%">' +
              '</div>' +
            '</div>').appendTo( $li ).find('.progress-bar');
        }
        if(state=='paused'){
        	return;
        }
        $li.find('p.state').text('上传中');
        $percent.css( 'width', percentage * 100 + '%' );
    });

    uploader.on( 'uploadSuccess', function( file,response ) {
        $( '#'+file.id ).find('p.state').text('已上传');
        parent.vm.fileList.file_path = response.fileName;
    });

    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错');
    });

    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });

    uploader.on( 'all', function( type ) {
        if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
        }

        if ( state === 'uploading' ) {
            $btn.text('暂停上传');
        } else {
            $btn.text('开始上传');
        }
    });

    $btn.on( 'click', function() {
        if ( state === 'uploading' ) {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });
});