var request=require('request');
let access_token;
let foods_name;

module.exports.settk=function(token){
    access_token=token;
    console.log(access_token)
}
module.exports.setfn=function(fname){
    foods_name=fname;
    console.log(foods_name)
}

function getdata(){
    return new Promise((resolve, reject) => {
        var options={
            method:'GET',
            url:'https://platform.fatsecret.com/rest/foods/search/v3',
            headers:{
                'Content-type':'application/json',
                'Authorization':'Bearer '+access_token
            },
            qs:{
                'search_expression':foods_name,
                'max_results': 15,
                'region':'KR',
                'language':'ko',
                'format':'json'
            },
            json:true
        }
        request(options,function(error,response,body){
            console.log(body);
            resolve(body);
        })
    })
}

module.exports.getdata=getdata;
