const { json } = require('express');
var request=require('request');
let access_token;

module.exports.settk=function(token){
    access_token=token;
    console.log(access_token)
}

function getdata(){
    return new Promise((resolve, reject) => {
        var options={
            method:'GET',
            url:'https://platform.fatsecret.com/rest/food-sub-categories/v2',
            headers:{
                'Content-type':'application/json',
                'Authorization':'Bearer '+access_token
            },
            qs:{
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