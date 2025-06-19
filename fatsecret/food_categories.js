const { json } = require('express');
var request=require('request');
let access_token='eyJhbGciOiJSUzI1NiIsImtpZCI6IjEwOEFEREZGRjZBNDkxOUFBNDE4QkREQTYwMDcwQzE5NzNDRjMzMUUiLCJ0eXAiOiJhdCtqd3QiLCJ4NXQiOiJFSXJkX19ha2tacWtHTDNhWUFjTUdYUFBNeDQifQ.eyJuYmYiOjE3NTAyOTk4ODAsImV4cCI6MTc1MDM4NjI4MCwiaXNzIjoiaHR0cHM6Ly9vYXV0aC5mYXRzZWNyZXQuY29tIiwiYXVkIjoicHJlbWllciIsImNsaWVudF9pZCI6IjU5YmU0MTIxNjlkODQ1YzFhOGU1ODFiZGViNWMzNzE1Iiwic2NvcGUiOlsicHJlbWllciJdfQ.opiLBZJFnQj4U6NhiHtaAVv0xhHXL0-QT5wPkwo2kBdwEwrNm0T_kwVRA5W4Fzz6fpr625O2divqbiCj8e2EDxObViRHrxO6K3shZ16mGrnCCDADsi5pT7Ra7ObgtIrBTRedDycoREgog_zN_eVTm9VJcZzRdzF2J8Gq36hz7Yk2OQUCoc4FL6f_NLnfBWE6LaRzUfVeCj4VqHJL_wY5rUJMEtbrRa4vTIouZzIqVHebd9B3Ox-X8V_ou08_pEjzP1V2pCycRqPr6Wt9AHiqpd6j5odjtaLukPLdTe735Kokfary95Nx-Mnx19Vu1KPrrRDTTsXkkzADHw0ZpMKQzHK6PX6nCePR6gpm_dhHtLXpYqOfBjNp736RpZDyIauORuyW77i4TuIPTrowPcwPtXC4teSfrt43MWDsVHsXTe3uq2yo0dgMBge0HDmn0xRLytaWCyDOJb47sRL-ZzLlk8_jGnvkTKOfmOBnx6ux5v67lK6WcFu2lAUCyxGUh36eEF124O-9tWMV3iQMP-pH_nHx2j8kYt2docbUZCBS02rI8FeMHD-5V6T2IfwUmRWe8iK32cyisejq_DiEmW3HMOD2jhsHpwX5uQA0H7vfTziHEFfDDSky_WB8TMiywLuEdlh5VfL17VKLF-cGursgZJvde0IY3vWTTjuVnfMBIQw';

module.exports.settk=function(token){
    access_token=token;
    console.log(access_token)
}

function getdata(){
    return new Promise((resolve, reject) => {
        var options={
            method:'GET',
            url:'https://platform.fatsecret.com/rest/food-categories/v2',
            headers:{
                'Content-type':'application/json',
                'Authorization':'Bearer '+access_token
            },
            qs:{
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