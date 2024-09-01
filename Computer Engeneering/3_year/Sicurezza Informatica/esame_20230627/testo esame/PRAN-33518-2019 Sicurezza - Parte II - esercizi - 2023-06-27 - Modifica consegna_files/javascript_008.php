
/**
 * Javascript library for enableing a drag and drop upload interface
 *
 * @package    moodlecore
 * @subpackage form
 * @copyright  2011 Davo Smith
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */
M.form_dndupload={}
M.form_dndupload.init=function(Y,options){var dnduploadhelper={Y:null,url:M.cfg.wwwroot+'/repository/repository_ajax.php?action=upload',options:{},itemid:null,acceptedtypes:[],maxbytes:0,areamaxbytes:-1,clientid:'',repositoryid:0,container:null,filemanager:null,callback:null,entercount:0,pageentercount:0,progressbars:{},init:function(Y,options){this.Y=Y;if(!this.browser_supported()){Y.one('body').addClass('dndnotsupported');return}
this.repositoryid=this.get_upload_repositoryid(options.repositories);if(!this.repositoryid){Y.one('body').addClass('dndnotsupported');return}
Y.one('body').addClass('dndsupported');this.options=options;this.acceptedtypes=options.acceptedtypes;this.clientid=options.clientid;this.maxbytes=options.maxbytes;this.areamaxbytes=options.areamaxbytes;this.itemid=options.itemid;this.author=options.author;this.container=this.Y.one('#'+options.containerid);if(options.filemanager){this.filemanager=options.filemanager}else if(options.formcallback){this.callback=options.formcallback}else{if(M.cfg.developerdebug){alert('dndupload: Need to define either options.filemanager or options.formcallback')}
return}
this.init_events();this.init_page_events()},browser_supported:function(){if(typeof FileReader=='undefined'){return!1}
if(typeof FormData=='undefined'){return!1}
return!0},get_upload_repositoryid:function(repositories){for(var i in repositories){if(repositories[i].type=="upload"){return repositories[i].id}}
return!1},init_events:function(){this.Y.on('dragenter',this.drag_enter,this.container,this);this.Y.on('dragleave',this.drag_leave,this.container,this);this.Y.on('dragover',this.drag_over,this.container,this);this.Y.on('drop',this.drop,this.container,this)},init_page_events:function(){this.Y.on('dragenter',this.drag_enter_page,'body',this);this.Y.on('dragleave',this.drag_leave_page,'body',this)},is_disabled:function(){return(this.container.ancestor('.fitem.disabled')!=null)},drag_enter_page:function(e){if(this.is_disabled()){return!1}
if(!this.has_files(e)){return!1}
this.pageentercount++;if(this.pageentercount>=2){this.pageentercount=2;return!1}
this.show_drop_target();return!1},drag_leave_page:function(e){this.pageentercount--;if(this.pageentercount==1){return!1}
this.pageentercount=0;this.hide_drop_target();return!1},check_drag:function(e){if(this.is_disabled()){return!1}
if(!this.has_files(e)){return!1}
e.preventDefault();e.stopPropagation();return!0},drag_enter:function(e){if(!this.check_drag(e)){return!0}
this.entercount++;if(this.entercount>=2){this.entercount=2;return!1}
this.pageentercount=2;this.show_drop_target();this.show_upload_ready();return!1},drag_leave:function(e){if(!this.check_drag(e)){return!0}
this.entercount--;if(this.entercount==1){return!1}
this.entercount=0;this.hide_upload_ready();return!1},drag_over:function(e){if(!this.check_drag(e)){return!0}
return!1},drop:function(e){if(!this.check_drag(e,!0)){return!0}
this.entercount=0;this.pageentercount=0;this.hide_upload_ready();this.hide_drop_target();var files=e._event.dataTransfer.files;if(this.filemanager){var options={files:files,options:this.options,repositoryid:this.repositoryid,currentfilecount:this.filemanager.filecount,currentfiles:this.filemanager.options.list,callback:Y.bind('update_filemanager',this),callbackprogress:Y.bind('update_progress',this),callbackcancel:Y.bind('hide_progress',this)};this.clear_progress();this.show_progress();var uploader=new dnduploader(options);uploader.start_upload()}else{if(files.length>=1){options={files:[files[0]],options:this.options,repositoryid:this.repositoryid,currentfilecount:0,currentfiles:[],callback:Y.bind('update_filemanager',this),callbackprogress:Y.bind('update_progress',this),callbackcancel:Y.bind('hide_progress',this)};this.clear_progress();this.show_progress();uploader=new dnduploader(options);uploader.start_upload()}}
return!1},has_files:function(e){var types=e._event.dataTransfer.types||[];for(var i=0;i<types.length;i++){if(types[i]=='Files'){return!0}}
return!1},show_drop_target:function(){this.container.addClass('dndupload-ready')},hide_drop_target:function(){this.container.removeClass('dndupload-ready')},show_upload_ready:function(){this.container.addClass('dndupload-over')},hide_upload_ready:function(){this.container.removeClass('dndupload-over')},show_progress:function(){this.container.addClass('dndupload-inprogress')},hide_progress:function(){this.container.removeClass('dndupload-inprogress')},update_filemanager:function(params){this.hide_progress();if(this.filemanager){this.filemanager.filepicker_callback()}else if(this.callback){this.callback(params)}},clear_progress:function(){var filename;for(filename in this.progressbars){if(this.progressbars.hasOwnProperty(filename)){this.progressbars[filename].progressouter.remove(!0);delete this.progressbars[filename]}}},update_progress:function(filename,percent){if(this.progressbars[filename]===undefined){var dispfilename=filename;if(dispfilename.length>50){dispfilename=dispfilename.substr(0,49)+'&hellip;'}
var progressouter=this.container.create('<div>'+dispfilename+'<div class="progress">'+'   <div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">'+'       <span class="sr-only"></span>'+'   </div>'+'</div></div>');var progressinner=progressouter.one('.progress-bar');var progressinnertext=progressinner.one('.sr-only');var progresscontainer=this.container.one('.dndupload-progressbars');progresscontainer.appendChild(progressouter);this.progressbars[filename]={progressouter:progressouter,progressinner:progressinner,progressinnertext:progressinnertext}}
this.progressbars[filename].progressinner.setStyle('width',percent+'%');this.progressbars[filename].progressinner.setAttribute('aria-valuenow',percent);this.progressbars[filename].progressinnertext.setContent(percent+'% '+M.util.get_string('complete','moodle'))}};var dnduploader=function(options){dnduploader.superclass.constructor.apply(this,arguments)};Y.extend(dnduploader,Y.Base,{api:M.cfg.wwwroot+'/repository/repository_ajax.php',options:{},callback:null,callbackprogress:null,callbackcancel:null,files:null,repositoryid:0,currentfiles:null,currentfilecount:0,currentareasize:0,uploadqueue:[],renamequeue:[],queuesize:0,overwriteall:!1,renameall:!1,filemanagerhelper:null,initializer:function(params){this.options=params.options;this.repositoryid=params.repositoryid;this.callback=params.callback;this.callbackprogress=params.callbackprogress;this.callbackcancel=params.callbackcancel;this.currentfiles=params.currentfiles;this.currentfilecount=params.currentfilecount;this.currentareasize=0;this.filemanagerhelper=this.options.filemanager;for(var i=0;i<this.currentfiles.length;i++){this.currentareasize+=this.currentfiles[i].size};if(!this.initialise_queue(params.files)){if(this.callbackcancel){this.callbackcancel()}}
require(['core_form/events'],function(FormEvent){FormEvent.triggerUploadStarted(this.filemanagerhelper.filemanager.get('id'))}.bind(this))},start_upload:function(){this.process_renames()},print_msg:function(msg,type){var header=M.util.get_string('error','moodle');if(type!='error'){type='info';header=M.util.get_string('info','moodle')}
if(!this.msg_dlg){this.msg_dlg_node=Y.Node.create(M.core_filepicker.templates.message);this.msg_dlg_node.generateID();this.msg_dlg=new M.core.dialogue({bodyContent:this.msg_dlg_node,centered:!0,modal:!0,visible:!1});this.msg_dlg.plug(Y.Plugin.Drag,{handles:['#'+this.msg_dlg_node.get('id')+' .yui3-widget-hd']});this.msg_dlg_node.one('.fp-msg-butok').on('click',function(e){e.preventDefault();this.msg_dlg.hide()},this)}
this.msg_dlg.set('headerContent',header);this.msg_dlg_node.removeClass('fp-msg-info').removeClass('fp-msg-error').addClass('fp-msg-'+type)
this.msg_dlg_node.one('.fp-msg-text').setContent(msg);this.msg_dlg.show()},initialise_queue:function(files){this.uploadqueue=[];this.renamequeue=[];this.queuesize=0;var i;for(i=0;i<files.length;i++){if(this.options.maxbytes>0&&files[i].size>this.options.maxbytes){var maxbytesdisplay=this.display_size(this.options.maxbytes);this.print_msg(M.util.get_string('maxbytesfile','error',{file:files[i].name,size:maxbytesdisplay}),'error');this.uploadqueue=[];return}
if(this.has_name_clash(files[i].name)){this.renamequeue.push(files[i])}else{if(!this.add_to_upload_queue(files[i],files[i].name,!1)){return!1}}
this.queuesize+=files[i].size}
return!0},display_size:function(size){var gb=M.util.get_string('sizegb','moodle'),mb=M.util.get_string('sizemb','moodle'),kb=M.util.get_string('sizekb','moodle'),b=M.util.get_string('sizeb','moodle');if(size>=1073741824){size=Math.round(size/1073741824*10)/10+gb}else if(size>=1048576){size=Math.round(size/1048576*10)/10+mb}else if(size>=1024){size=Math.round(size/1024*10)/10+kb}else{size=parseInt(size,10)+' '+b}
return size},add_to_upload_queue:function(file,filename,overwrite){if(!overwrite){this.currentfilecount++}
if(this.options.maxfiles>=0&&this.currentfilecount>this.options.maxfiles){this.uploadqueue=[];this.renamequeue=[];this.print_msg(M.util.get_string('maxfilesreached','moodle',this.options.maxfiles),'error');return!1}
if(this.options.areamaxbytes>-1){var sizereached=this.currentareasize+this.queuesize+file.size;if(sizereached>this.options.areamaxbytes){this.uploadqueue=[];this.renamequeue=[];this.print_msg(M.util.get_string('maxareabytesreached','moodle'),'error');return!1}}
this.uploadqueue.push({file:file,filename:filename,overwrite:overwrite});return!0},process_renames:function(){if(this.renamequeue.length==0){this.do_upload();return}
var multiplefiles=(this.renamequeue.length>1);var file=this.renamequeue.shift();var newname=this.generate_unique_name(file.name);if(this.overwriteall){this.add_to_upload_queue(file,file.name,!0);this.process_renames();return}
if(this.renameall){this.add_to_upload_queue(file,newname,!1);this.process_renames();return}
var self=this;var process_dlg_node;if(multiplefiles){process_dlg_node=Y.Node.create(M.core_filepicker.templates.processexistingfilemultiple)}else{process_dlg_node=Y.Node.create(M.core_filepicker.templates.processexistingfile)}
var node=process_dlg_node;node.generateID();var process_dlg=new M.core.dialogue({bodyContent:node,headerContent:M.util.get_string('fileexistsdialogheader','repository'),centered:!0,modal:!0,visible:!1});process_dlg.plug(Y.Plugin.Drag,{handles:['#'+node.get('id')+' .yui3-widget-hd']});node.one('.fp-dlg-butoverwrite').on('click',function(e){e.preventDefault();process_dlg.hide();self.add_to_upload_queue(file,file.name,!0);self.process_renames()},this);node.one('.fp-dlg-butrename').on('click',function(e){e.preventDefault();process_dlg.hide();self.add_to_upload_queue(file,newname,!1);self.process_renames()},this);node.one('.fp-dlg-butcancel').on('click',function(e){e.preventDefault();process_dlg.hide();if(self.callbackcancel){self.callbackcancel()}},this);if(this.currentfilecount==this.options.maxfiles){node.one('.fp-dlg-butrename').setStyle('display','none');if(multiplefiles){node.one('.fp-dlg-butrenameall').setStyle('display','none')}}
if(multiplefiles){node.one('.fp-dlg-butoverwriteall').on('click',function(e){e.preventDefault();process_dlg.hide();this.overwriteall=!0;self.add_to_upload_queue(file,file.name,!0);self.process_renames()},this);node.one('.fp-dlg-butrenameall').on('click',function(e){e.preventDefault();process_dlg.hide();this.renameall=!0;self.add_to_upload_queue(file,newname,!1);self.process_renames()},this)}
node.one('.fp-dlg-text').setContent(M.util.get_string('fileexists','moodle',file.name));process_dlg_node.one('.fp-dlg-butrename').setContent(M.util.get_string('renameto','repository',newname));process_dlg.after('visibleChange',function(e){if(!process_dlg.get('visible')){process_dlg.destroy(!0)}});process_dlg.show()},has_name_clash:function(filename){var i;for(i=0;i<this.currentfiles.length;i++){if(filename==this.currentfiles[i].filename){return!0}}
for(i=0;i<this.uploadqueue.length;i++){if(filename==this.uploadqueue[i].filename){return!0}}
return!1},generate_unique_name:function(filename){while(this.has_name_clash(filename)){filename=increment_filename(filename)}
return filename},do_upload:function(lastresult){if(this.uploadqueue.length>0){var filedetails=this.uploadqueue.shift();this.upload_file(filedetails.file,filedetails.filename,filedetails.overwrite)}else{this.uploadfinished(lastresult)}},uploadfinished:function(lastresult){require(['core_form/events'],function(FormEvent){FormEvent.triggerUploadCompleted(this.filemanagerhelper.filemanager.get('id'))}.bind(this));this.callback(lastresult)},upload_file:function(file,filename,overwrite){var xhr=new XMLHttpRequest();var self=this;xhr.upload.addEventListener('progress',function(e){if(e.lengthComputable&&self.callbackprogress){var percentage=Math.round((e.loaded*100)/e.total);self.callbackprogress(filename,percentage)}},!1);xhr.onreadystatechange=function(){if(xhr.readyState==4){if(xhr.status==200){var result=JSON.parse(xhr.responseText);if(result){if(result.error){self.print_msg(result.error,'error');self.uploadfinished()}else{if(result.event=='fileexists'){result.file=result.newfile.filename;result.url=result.newfile.url}
result.client_id=self.options.clientid;if(self.callbackprogress){self.callbackprogress(filename,100)}}}
self.do_upload(result)}else{self.print_msg(M.util.get_string('serverconnection','error'),'error');self.uploadfinished()}}};var formdata=new FormData();formdata.append('repo_upload_file',file);formdata.append('sesskey',M.cfg.sesskey);formdata.append('repo_id',this.repositoryid);formdata.append('itemid',this.options.itemid);if(this.options.author){formdata.append('author',this.options.author)}
if(this.options.filemanager){formdata.append('savepath',this.options.filemanager.currentpath)}
formdata.append('title',filename);if(overwrite){formdata.append('overwrite',1)}
if(this.options.contextid){formdata.append('ctx_id',this.options.contextid)}
if(this.options.acceptedtypes.constructor==Array){for(var i=0;i<this.options.acceptedtypes.length;i++){formdata.append('accepted_types[]',this.options.acceptedtypes[i])}}else{formdata.append('accepted_types[]',this.options.acceptedtypes)}
var uploadUrl=this.api;if(uploadUrl.indexOf('?')!==-1){uploadUrl+='&action=upload'}else{uploadUrl+='?action=upload'}
xhr.open("POST",uploadUrl,!0);xhr.send(formdata);return!0}});dnduploadhelper.init(Y,options)}