var downloadSingleFileUrl = "/admin/apply/downloadFile";
/**
 * @Title: download
 * @Description: 文件下载
 * @throws:
 */
function download(url, filePath,fileName){ // 获得url和data
    if( url){
        var inputs = '';
        inputs+='<input type="hidden" name="filePath" value="'+ filePath+'" />'
        if (fileName){
            inputs+='<input type="hidden" name="fileName" value="'+fileName+'" />'
        }
        jQuery('<form action="'+ url +'" method="'+ ('post') +'">'+inputs+'</form>').appendTo('body').submit().remove();
    };
};
/**
 * @Title: download
 * @Description: 单文件下载
 * @throws:
 */
function downloadSingleFile(filePath, fileName){
    if (filePath){
        download(downloadSingleFileUrl,filePath,fileName);
    }
};
