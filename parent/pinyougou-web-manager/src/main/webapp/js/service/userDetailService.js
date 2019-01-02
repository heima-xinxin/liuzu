//服务层
app.service('userDetailService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../userDetail/findAll.do');
	}

	//冻结
    this.freeze=function(ids){
        return $http.get('../userDetail/freeze.do?ids='+ids);
    }
    //数据导入
    this.outExcel=function(ids){
        return $http.get('../userDetail/outExcel.do');
    }
    this.unfreeze=function(ids){
        return $http.get('../userDetail/unfreeze.do?ids='+ids);
    }
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../userDetail/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
});
